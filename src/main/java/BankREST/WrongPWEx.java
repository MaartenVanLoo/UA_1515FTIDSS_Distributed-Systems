package BankREST;

public class WrongPWEx extends Exception {
    WrongPWEx(String accName) { super("Wrong password for the following account: " + accName); }
}
