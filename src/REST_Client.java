
import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
//https://www.baeldung.com/java-9-http-client
public class REST_Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest getBalance = HttpRequest.newBuilder()
                .uri(URI.create("http://openjdk.java.net/"))
                .GET()
                .build();

        HttpRequest addMoney = HttpRequest.newBuilder()                 //deposit money
                .uri(URI.create("http://openjdk.java.net/"))
                .PUT(HttpRequest.BodyPublishers.ofString("200"))
                .build();

        HttpRequest getMoney = HttpRequest.newBuilder()                 //widraw money
                .uri(URI.create("http://google.com/"))
                .PUT(HttpRequest.BodyPublishers.ofString("200"))
                .build();
        IPUtils.printIpv4Interfaces();
        //synchronous
        HttpResponse balance =
                client.send(getBalance, BodyHandlers.ofString());
        System.out.println(balance.statusCode());
        System.out.println(balance.body());

        HttpResponse add =
                client.send(addMoney, BodyHandlers.ofString());
        System.out.println(add.statusCode());
        System.out.println(add.body());

        HttpResponse receive =
                client.send(getMoney, BodyHandlers.ofString());
        System.out.println(receive.statusCode());
        System.out.println(receive.body());

    }

}
