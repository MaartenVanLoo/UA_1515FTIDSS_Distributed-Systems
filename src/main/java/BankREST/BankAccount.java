package BankREST;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BankAccount {

    private @GeneratedValue Long id;    // generates a unique id value upon creating an object of this class
    private @Id String accName;         // indicates that this is the primary identifier of an object of this class
    private String passw;
    private int bal = 0;

    public BankAccount() {};

    public BankAccount(String name, String passw) {
        this.accName = name;
        this.passw = passw;
    }

    public Long getId() {
        return id;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public int getBal() {
        return bal;
    }

    public void setBal(int bal) {
        this.bal = bal;
    }

    public int changeBal(int addition) {
        bal += addition;
        return bal;
    }

    @Override
    public String toString() {
        return "BankAccount{" + "id=" + this.id + ", accName='" + this.accName + '\'' + ", passw='" + this.passw + '\''
                + ", balance=" + this.bal + '}';
    }
}
