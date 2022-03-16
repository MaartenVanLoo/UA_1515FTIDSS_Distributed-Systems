package BankREST;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class Bank {

    private final AccountRepo repo;     // database with all accounts

    public Bank(AccountRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/bank/bal")
    int getBalance(@RequestParam String accName, @RequestParam String passw) throws NotFoundEx, WrongPWEx {
        BankAccount bankacc = repo.findById(accName).orElseThrow(() -> new NotFoundEx(accName));

        if (bankacc.getPassw().equals(passw)) return repo.findById(accName).get().getBal();
        else throw new WrongPWEx(accName);
    }

    @PutMapping("/bank/add")
    String addBalance(@RequestParam String accName, @RequestParam String passw, @RequestParam int add) throws NotFoundEx, WrongPWEx {

        BankAccount bankacc = repo.findById(accName).orElseThrow(() -> new NotFoundEx(accName));
        if (bankacc.getPassw().equals(passw)) {
            repo.findById(accName).map(acc ->
            {
                acc.changeBal(add);
                return repo.save(acc);
            });
            return "Your new balance is: "+repo.findById(accName).get().getBal();
        }
        else throw new WrongPWEx(accName);

    }

    @PutMapping("/bank/wd")
    String wdBalance(@RequestParam String accName, @RequestParam String passw, @RequestParam int wd)
            throws NotFoundEx, WrongPWEx, NotEnoughFundsEx {

        BankAccount bankacc = repo.findById(accName).orElseThrow(() -> new NotFoundEx(accName));
        if (bankacc.getPassw().equals(passw)) {
            if (bankacc.getBal() >= wd) {
                repo.findById(accName).map(acc ->
                {
                    acc.changeBal(-wd);
                    return repo.save(acc);
                });
                return "Your new balance is: "+repo.findById(accName).get().getBal();
            }
            else throw new NotEnoughFundsEx(accName, repo.findById(accName).get().getBal());
        }
        else throw new WrongPWEx(accName);
    }

    @ResponseBody
    @ExceptionHandler(NotEnoughFundsEx.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String notEnoughFunds(NotEnoughFundsEx e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NotFoundEx.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String notFound(NotFoundEx e){
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(WrongPWEx.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String wrongPw(WrongPWEx e){
        return e.getMessage();
    }

}
