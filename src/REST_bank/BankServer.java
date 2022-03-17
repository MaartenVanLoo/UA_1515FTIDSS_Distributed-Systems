package REST_bank;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import REST_bank.RestAPI_Handler;
public class BankServer {
    HashMap<String,BankAccount> bankData;

    public BankServer() {
        this.bankData = new HashMap<>();
        this.bankData.put("Jens",new BankAccount("Jens",0));
        this.bankData.put("Leander",new BankAccount("Leander",0));
        this.bankData.put("Koen",new BankAccount("Koen",0));
        this.bankData.put("Maarten",new BankAccount("Maarten",0));
    }

    public void startServer() throws IOException {
        RestAPI_Server server = new RestAPI_Server();
        server.addContext(new bank_restAPI("/bank",bankData));
        server.start();
    }

    private class bank_restAPI extends RestAPI_Handler {
        HashMap<String,BankAccount> bank;

        public bank_restAPI(String contextPath, HashMap<String, BankAccount> bank) {
            super(contextPath);
            this.bank = bank;
        }

        private bank_restAPI() {};


        @Override
        String getURI(String _URI){
            /*
                URI: /bank/users/username
             */
            String[] URI_tree = _URI.split("/");
            Iterator<String> iterator = Arrays.stream(URI_tree).iterator();
            String user = "";
            try {
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    if (next.equals("")) continue;
                    else if (next.equals("bank")) continue;
                    else if (next.equals("users")){
                        user = getUserString(iterator);
                        if (!bank.containsKey(user)) return null;
                        long balance = bank.get(user).getBalance();
                        return Long.toString(balance);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
            return "";
        };

        @Override
        String putURI(String _URI, String body) {
            /*
                URI: /bank/users/username
             */
            JSONObject jsonBody = new JSONObject();
            JSONParser parser = new JSONParser();
            try{
                jsonBody = (JSONObject) parser.parse(body);
            }catch(ParseException e){e.printStackTrace();}

            _URI = _URI.substring(1); //remove first '/'
            String[] URI_tree = _URI.split("/");
            Iterator<String> iterator = Arrays.stream(URI_tree).iterator();

            String user = "";
            try {
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    if (next.equals("")) continue;
                    if (next.equals("bank")) continue;
                    if (next.equals("users")) {
                        long balance = 0;
                        user = getUserString(iterator);
                        if (!bank.containsKey(user)) return null;
                        switch ((String) jsonBody.get("method")) {
                            case "deposit":
                                System.out.println("deposit");
                                balance = bank.get(user).deposit((long) jsonBody.get("amount"));
                                break;
                            case "withdraw":
                                System.out.println("withdraw");
                                balance = bank.get(user).withdraw((long) jsonBody.get("amount"));
                                break;
                            default:
                                System.out.println("Uknown method"); //TODO: add correct html return code
                        }
                        return Long.toString(balance);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
            return "";
        }

        @Override
        String postURI(String _URI, String value) {
            /*
                URI: /bank/users/username
             */
            String[] URI_tree = _URI.split("/");
            Iterator<String> iterator = Arrays.stream(URI_tree).iterator();

            String user;
            try{
                while (iterator.hasNext()){
                    String next = iterator.next();
                    if (next.equals("")) continue;
                    else if (next.equals("bank")) continue;
                    else if (next.equals("users")){
                        user = getUserString(iterator);
                        if (this.bank.containsKey(user)) return null;
                        this.bank.put(user,new BankAccount(user,Long.parseLong(value)));
                        return Long.toString(this.bank.get(user).getBalance());
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        boolean deleteURI(String _URI) {
            /*
                URI: /bank/users/username
             */
            String[] URI_tree = _URI.split("/");
            Iterator<String> iterator = Arrays.stream(URI_tree).iterator();

            String user = "";
            try{
                while (iterator.hasNext()){
                    String next = iterator.next();
                    if (next.equals("")) continue;
                    else if (next.equals("bank")) continue;
                    else if (next.equals("users")){
                        user = getUserString(iterator);
                        if (!this.bank.containsKey(user)) return false;
                        this.bank.remove(user);
                        return true;
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
            return false;
        }

        private String getUserString(Iterator<String> uri) throws IOException {
            if (uri.hasNext()){
                return uri.next();
            }else{
                throw new IOException();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting BankServer");
        BankServer bankServer = new BankServer();
        bankServer.startServer();
    }
}
