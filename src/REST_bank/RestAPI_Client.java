package REST_bank;

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
        HttpResponse<String> response = client.send(get, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());
        return response.body();
    }
    static Object DELETE( String _URI) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest delete = HttpRequest.newBuilder()
                .uri(URI.create(_URI))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(delete, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());
        return response.body();
    }
    static Object POST(String _URI, String object) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest post = HttpRequest.newBuilder()
                .uri(URI.create(_URI))
                .POST(HttpRequest.BodyPublishers.ofString(object))
                .build();
        HttpResponse<String> response = client.send(post, HttpResponse.BodyHandlers.ofString());

        handleResponseCode(response);
        return response.body();
    }
    static Object PUT(String _URI, String object) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest put = HttpRequest.newBuilder()
                .uri(URI.create(_URI))
                .PUT(HttpRequest.BodyPublishers.ofString(object))
                .build();
        HttpResponse<String> response = client.send(put, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    private static void handleResponseCode(HttpResponse<String> response) throws IOException {
        int status = response.statusCode();
        switch (status){
            case 200: //OK
                break;
            default:
                throw new IOException("Errorcode: " + status);
        }
    }
}
