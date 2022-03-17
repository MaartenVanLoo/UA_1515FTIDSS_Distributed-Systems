package REST_bank;

import org.json.simple.JSONObject;

import java.io.IOException;

public class BankClient {
    static int getBalanace(String user){
        int balance = 0;
        try {
            balance = Integer.parseInt((String)RestAPI_Client.GET("http://localhost:8001/bank/" + user));
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

            balance = Long.parseLong((String)RestAPI_Client.PUT("http://localhost:8001/bank/" + user,body.toJSONString()));
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

            balance = Long.parseLong((String)RestAPI_Client.PUT("http://localhost:8001/bank/" + user, body.toJSONString()));
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
        return balance;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting BankClient");
        System.out.println("Balance Jens: " + BankClient.getBalanace("Jens"));
        for (int i = 0; i < 1000; i++){
            System.out.println("Deposit 100, new balance:" + BankClient.deposit("Jens",100));
        }
        System.out.println("Balance Jens: " + BankClient.getBalanace("Jens"));

    }
}
