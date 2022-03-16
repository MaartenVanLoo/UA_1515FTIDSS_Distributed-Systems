package BankREST;

public class NotFoundEx extends Exception {

    NotFoundEx(String id) {
        super("Could not find bank account: " + id);
    }
}
