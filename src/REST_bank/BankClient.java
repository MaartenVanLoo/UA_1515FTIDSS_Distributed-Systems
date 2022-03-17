package REST_bank;

import org.json.simple.JSONObject;

import java.io.IOException;

public class BankClient {
    static int getBalanace(String user){
        int balance = 0;
        try {
            balance = Integer.parseInt((String)RestAPI_Client.GET("http://localhost:8001/bank/users/" + user));
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
        return balance;
    }
    static long deposit(String user, long amount){
        long balance = 0;
        try {
            JSONObject body = new JSONObject();
            body.put("method","deposit");
            body.put("amount",amount);

            balance = Long.parseLong((String)RestAPI_Client.PUT("http://localhost:8001/bank/users/" + user,body.toJSONString()));
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
        return balance;
    }

    static long withdraw(String user,long amount){
        long balance = 0;
        try{
            JSONObject body = new JSONObject();
            body.put("method","withdraw");
            body.put("amount",amount);

            balance = Long.parseLong((String)RestAPI_Client.PUT("http://localhost:8001/bank/users/" + user, body.toJSONString()));
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
        return balance;
    }
    static long newBankAccount(String user, long balance){
        try{
            return Long.parseLong((String)RestAPI_Client.POST("http://localhost:8001/bank/users/" + user,Long.toString(balance)));
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    static void deleteBankAccount(String user){
        try {
            RestAPI_Client.DELETE("http://localhost:8001/bank/users/" + user);
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting BankClient");
        System.out.println("Balance Jens: " + BankClient.getBalanace("Jens"));
        for (int i = 0; i < 1000; i++){
            System.out.println("Deposit 100, new balance:" + BankClient.deposit("Jens",100));
            System.out.println("withdraw 50, new balance:" + BankClient.withdraw("Jens",50));
        }
        System.out.println("Balance Jens: " + BankClient.getBalanace("Jens"));
        System.out.println("New Account: " + BankClient.newBankAccount("test_name",42));
        System.out.println("Deposit 100, new balance:" + BankClient.deposit("test_name",100));
        System.out.println("Remove Account"); BankClient.deleteBankAccount("test_name");
    }
}
