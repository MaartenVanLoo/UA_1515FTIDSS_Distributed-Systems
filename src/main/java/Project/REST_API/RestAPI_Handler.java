package Project.REST_API;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class RestAPI_Handler implements HttpHandler {
    public RestAPI_Handler() {}

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())){
            System.out.println("GET REQUEST");
            handleGetRequest(exchange);
        }
        else if("PUT".equals(exchange.getRequestMethod())){
            System.out.println("PUT REQUEST");
            handlePutRequest(exchange);
        }
    }

    void handleGetRequest(HttpExchange httpExchange) throws IOException {
        System.out.println("Get Request");
        System.out.println(httpExchange.getRequestURI().toString());

        //get data
        String object = getURI(httpExchange.getRequestURI().toString());
        if (object == null) {
            httpExchange.sendResponseHeaders(400,0);
            return;
        }
        //send data
        try {
            byte[] data = object.getBytes(StandardCharsets.UTF_8);
            OutputStream outputStream = httpExchange.getResponseBody();
            httpExchange.sendResponseHeaders(200, data.length);
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            httpExchange.sendResponseHeaders(400,0);
        }
    }

    void handlePutRequest(HttpExchange httpExchange) throws IOException{
        System.out.println("Get Request");
        System.out.println(httpExchange.getRequestURI().toString());

        //get body
        String body = inputStreamToString(httpExchange.getRequestBody());
        //get data
        String object = putURI(httpExchange.getRequestURI().toString(), body);

        if (object == null) {
            httpExchange.sendResponseHeaders(400,0);
            return;
        }
        //send data
        try {
            byte[] data = object.getBytes(StandardCharsets.UTF_8);
            OutputStream outputStream = httpExchange.getResponseBody();
            httpExchange.sendResponseHeaders(200, data.length);
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            httpExchange.sendResponseHeaders(400,0);
        }
    }

    private String inputStreamToString(InputStream stream) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(stream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        for (int result = bis.read(); result != -1; result = bis.read()) {
            buf.write((byte) result);
        }
        return buf.toString("UTF-8");
    }

    /**
     * Process the GET request for the given URI.
     * @param URI Universal resource indentification.
     * @return String containing requested resource.
     */
    abstract String getURI(String URI);

    /**
     * Process the PUT request for the given URI and body.
     * @param URI Universal resource indentification.
     * @param value Body data in put request.
     * @return String containing updated resource.
     */
    abstract String putURI(String URI,String value);

}
