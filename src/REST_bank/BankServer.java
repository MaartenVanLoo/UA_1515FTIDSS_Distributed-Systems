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
        server.addContext("/bank",new bank_restAPI(bankData));
        server.start();
    }

    private class bank_restAPI extends RestAPI_Handler {
        HashMap<String,BankAccount> bank;

        private bank_restAPI() {};
        public bank_restAPI(HashMap<String, BankAccount> bank) {
            this.bank = bank;
        }

        @Override
        String getURI(String _URI){
            /*
                URI: /bank/username
             */
            String[] URI_tree = _URI.split("/");
            Iterator<String> iterator = Arrays.stream(URI_tree).iterator();
            while (iterator.hasNext()){
                String next = iterator.next();
                if (next.equals("")) continue;

                if (next.equals("bank") && iterator.hasNext()){
                    String user = iterator.next();
                    if (!bank.containsKey(user)) return null;
                    long balance = bank.get(user).getBalance();
                    return Long.toString(balance);
                }
            }
            return "";
        };

        @Override
        String putURI(String _URI, String body) {
            /*
                URI: /bank/username/
             */
            JSONObject jsonBody = new JSONObject();
            JSONParser parser = new JSONParser();
            try{
                jsonBody = (JSONObject) parser.parse(body);
            }catch(ParseException e){e.printStackTrace();}

            _URI = _URI.substring(1); //remove first '/'
            String[] URI_tree = _URI.split("/");
            Iterator<String> iterator = Arrays.stream(URI_tree).iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                if (next.equals("")) continue;
                if (next.equals("bank")) {
                    long balance = 0;
                    String user = iterator.next();
                    if (!bank.containsKey(user)) return null;
                    switch ((String)jsonBody.get("method")){
                        case "deposit":
                            System.out.println("deposit");
                            balance =bank.get(user).deposit((long)jsonBody.get("amount"));
                            break;
                        case "withdraw":
                            System.out.println("withdraw");
                            balance =bank.get(user).withdraw((long)jsonBody.get("amount"));
                            break;
                        default:
                            System.out.println("Uknown method"); //TODO: add correct html return code
                    }
                    return Long.toString(balance);
                }
            }
            return "";
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting BankServer");
        BankServer bankServer = new BankServer();
        bankServer.startServer();
    }
}
