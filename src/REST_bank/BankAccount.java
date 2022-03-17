package REST_bank;

public class BankAccount {
    String name;
    long balance;

    public BankAccount(String name, long balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long withdraw(long value){
        this.balance -= value;
        return this.balance;
    }
    public long deposit(long value){
        this.balance += value;
        return this.balance;
    }

    @Override
    public String toString() {
        return "BankAccount:{" +
                "name:'" + name + '\'' +
                ", balance:" + balance +
                '}';
    }
}

