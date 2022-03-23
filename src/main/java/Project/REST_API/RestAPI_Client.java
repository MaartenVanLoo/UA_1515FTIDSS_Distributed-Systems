package Project.REST_API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestAPI_Client {
    static Object GET( String _URI) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest get = HttpRequest.newBuilder()
                .uri(URI.create(_URI))
                .GET()
                .build();
        HttpResponse response = client.send(get, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());
        return response.body();
    };
    static Object PUT(String _URI, String object) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest put = HttpRequest.newBuilder()
                .uri(URI.create(_URI))
                .PUT(HttpRequest.BodyPublishers.ofString(object))
                .build();
        HttpResponse response = client.send(put, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
