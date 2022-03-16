package BankREST;

public class NotEnoughFundsEx extends Exception {

    NotEnoughFundsEx(String accName, int balance){
        super("Not enough funds in bankaccount: "+accName+"; Balance: "+balance);
    }
}
