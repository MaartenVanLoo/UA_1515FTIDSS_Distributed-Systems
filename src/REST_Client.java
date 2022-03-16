
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

//https://www.baeldung.com/java-9-http-client
public class REST_Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest getBalance = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8001/getBalance/user=Jens"))
                .GET()
                .build();

        HttpRequest addMoney = HttpRequest.newBuilder()                 //deposit money
                .uri(URI.create("http://localhost:8001/addMoney/user=Jens"))
                .PUT(HttpRequest.BodyPublishers.ofString("200"))
                .build();

        HttpRequest getMoney = HttpRequest.newBuilder()                 //widraw money
                .uri(URI.create("http://localhost:8001/getMoney/user=Jens"))
                .PUT(HttpRequest.BodyPublishers.ofString("200"))
                .build();
        IPUtils.printIpv4Interfaces();

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your command: ");
        String inputLine = sc.nextLine();
        //synchronous send
        while (inputLine!= null) {
            if(inputLine.equals("getBalance")){
                HttpResponse balance =
                        client.send(getBalance, BodyHandlers.ofString());
                System.out.println(balance.statusCode());
                System.out.println(balance.body());

            }
            else if(inputLine.equals("addMoney")){
                HttpResponse add =
                        client.send(addMoney, BodyHandlers.ofString());
                System.out.println(add.statusCode());
                System.out.println(add.body());

            }
            else if(inputLine.equals("getMoney")){
                HttpResponse receive =
                        client.send(getMoney, BodyHandlers.ofString());
                System.out.println(receive.statusCode());
                System.out.println(receive.body());

            }
            else if(inputLine.equals("stop")){
                System.out.println("Shutting down");
                break;
            }
            else{
                System.out.println("Unknown Command");

            }
            System.out.print("Enter your command: ");
            inputLine = sc.nextLine();
        }
    }
}
