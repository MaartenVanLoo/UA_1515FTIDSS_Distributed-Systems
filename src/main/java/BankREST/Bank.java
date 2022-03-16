package BankREST;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController // handles the HTTP requests
public class Bank {

    private final AccountRepo repo;     // database with all accounts

    public Bank(AccountRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/bank/bal")    // returns the balance of the account name and password specified in the uri
    int getBalance(@RequestParam String accName, @RequestParam String passw) throws NotFoundEx, WrongPWEx {
        // searches the accountname in the database, and if it can't find it it throws an exception
        BankAccount bankacc = repo.findById(accName).orElseThrow(() -> new NotFoundEx(accName));

        // next it checks if the given password is the same as the one in the database.
        // If they are the same the balance is returned, if not an exception is thrown
        if (bankacc.getPassw().equals(passw)) return repo.findById(accName).get().getBal();
        else throw new WrongPWEx(accName);
    }

    @PutMapping("/bank/add")    // increases the balance of a specified account
    String addBalance(@RequestParam String accName, @RequestParam String passw, @RequestParam int add) throws NotFoundEx, WrongPWEx {

        // checks username and password
        BankAccount bankacc = repo.findById(accName).orElseThrow(() -> new NotFoundEx(accName));
        if (bankacc.getPassw().equals(passw)) {
            repo.findById(accName).map(acc ->
            {
                // adds the balance to the account
                acc.changeBal(add);
                return repo.save(acc);
            });
            return "Your new balance is: "+repo.findById(accName).get().getBal();
        }
        else throw new WrongPWEx(accName);

    }

    @PutMapping("/bank/wd")     // decreases the balance of a specified account
    String wdBalance(@RequestParam String accName, @RequestParam String passw, @RequestParam int wd)
            throws NotFoundEx, WrongPWEx, NotEnoughFundsEx {

        // checks the username and password
        BankAccount bankacc = repo.findById(accName).orElseThrow(() -> new NotFoundEx(accName));
        if (bankacc.getPassw().equals(passw)) {
            if (bankacc.getBal() >= wd) {
                repo.findById(accName).map(acc ->
                {
                    // withdraws the balance from the account
                    acc.changeBal(-wd);
                    return repo.save(acc);
                });
                return "Your new balance is: "+repo.findById(accName).get().getBal();
            }
            else throw new NotEnoughFundsEx(accName, repo.findById(accName).get().getBal());
        }
        else throw new WrongPWEx(accName);
    }

    // exception handlers
    // if this wasn't here the application/server would end and the user/client wouldn't know what's wrond
    @ResponseBody
    @ExceptionHandler(NotEnoughFundsEx.class)
    @ResponseStatus(HttpStatus.CONFLICT)    // sends HTTP 409 with the message: "Not enough funds in bankaccount: "+accName+"; Balance: "+balance
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
