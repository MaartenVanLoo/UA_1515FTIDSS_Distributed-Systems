package BankREST;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.util.Scanner;

public class BankCustomer {

    public static void main(String[] args) {
        BankCustomer bc = new BankCustomer();

        Scanner sc = new Scanner(System.in);
        String option = "0";
        while (!option.equals("5")) {
            System.out.println("Give your account name: ");
            String name = sc.nextLine();
            System.out.println("Give your password: ");
            String pw = sc.nextLine();
            option = "0";
            while (!option.equals("4") && !option.equals("5")) {
                System.out.println("What do you want to do:\n" +
                        "1) Check your account balance.\n" +
                        "2) Add money to your account.\n" +
                        "3) Withdraw money from your account.\n" +
                        "4) Log out.\n" +
                        "5) Stop.");
                option = sc.nextLine();
                switch (option) {
                    case "1":
                        String bal = bc.getBalance(name, pw);
                        System.out.println("Your account balance is: " + bal);
                        option = "0";
                        break;
                    case "2":
                        System.out.println("How much do you like to add to your account? ");
                        int add = Integer.parseInt(sc.nextLine());
                        String bal1 = bc.addBalance(name, pw, add);
                        System.out.println("Your new balance is: "+bal1);
                        option = "0";
                        break;
                    case "3":
                        System.out.println("How much do you like to withdraw from your account? ");
                        int wd = Integer.parseInt(sc.nextLine());
                        String bal2 = bc.addBalance(name, pw, wd);
                        System.out.println("Your new balance is: "+bal2);
                        option = "0";
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public String getBalance(String accname, String pw) {
        try {
            String url = "http://localhost:8083/bank/bal?accName=" + accname + "&passw=" + pw;
            Unirest.setTimeouts(0, 0);
            String input = Unirest.get(url).asString().getBody();

            Unirest.shutdown();

            return input;
        } catch (UnirestException | IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String addBalance(String accname, String pw, int add) {
        try {
            String url = "http://localhost:8083/bank/add?accName=" + accname + "&passw=" + pw + "&add=" + add;
            Unirest.setTimeouts(0, 0);
            String input = Unirest.put(url).asString().getBody();

            Unirest.shutdown();

            return input;
        } catch (UnirestException | IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String wdBalance(String accname, String pw, int wd) {
        try {
            String url = "http://localhost:8083/bank/wd?accName=" + accname + "&passw=" + pw + "&wd=" + wd;
            Unirest.setTimeouts(10, 0);
            String input = Unirest.put(url).asString().getBody();

            Unirest.shutdown();

            return input;
        } catch (UnirestException | IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
}