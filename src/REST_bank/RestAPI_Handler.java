package REST_bank;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpPrincipal;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class RestAPI_Handler implements HttpHandler {
    protected String contextPath ="/";
    public RestAPI_Handler(){};
    public RestAPI_Handler(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()){
            case "GET":
                System.out.println("****BEGIN GET REQUEST****");
                handleGetRequest(exchange);
                System.out.println("****END GET REQUEST****");
                break;
            case "POST":
                System.out.println("****BEGIN POST REQUEST****");
                handlePostRequest(exchange);
                System.out.println("****END POST REQUEST****");
                break;
            case "PUT":
                System.out.println("****BEGIN PUT REQUEST****");
                handlePutRequest(exchange);
                System.out.println("****END PUT REQUEST****");
                break;
            case "DELETE":
                System.out.println("****BEGIN DELETE REQUEST****");
                handleDeleteRequest(exchange);
                System.out.println("****END DELETE REQUEST****");
                break;
        }
    }

    private void handleGetRequest(HttpExchange httpExchange) throws IOException {
        System.out.println(httpExchange.getRequestURI().toString());

        //get data
        String object = getURI(httpExchange.getRequestURI().toString());
        if (object == null) {
            httpExchange.sendResponseHeaders(400,0);
            httpExchange.getResponseBody().close();
            return;
        }
        //send data
        try {
            byte[] data = object.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, data.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            httpExchange.sendResponseHeaders(400,0);
            httpExchange.getResponseBody().close();
        }
    }

    private void handlePostRequest(HttpExchange httpExchange) throws IOException{
        System.out.println(httpExchange.getRequestURI().toString());

        //get body
        String body = inputStreamToString(httpExchange.getRequestBody());

        //get data
        String object = postURI(httpExchange.getRequestURI().toString(),body);

        if (object == null) {
            httpExchange.sendResponseHeaders(400,0);
            httpExchange.getResponseBody().close();
            return;
        }
        //send data
        try {
            byte[] data = object.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, data.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            httpExchange.sendResponseHeaders(400,0);
            httpExchange.getResponseBody().close();
        }
    }

    private void handlePutRequest(HttpExchange httpExchange) throws IOException{
        System.out.println(httpExchange.getRequestURI().toString());

        //get body
        String body = inputStreamToString(httpExchange.getRequestBody());
        //get data
        String object = putURI(httpExchange.getRequestURI().toString(), body);

        if (object == null) {
            httpExchange.sendResponseHeaders(400,0);
            httpExchange.getResponseBody().close();
            return;
        }
        //send data
        try {
            byte[] data = object.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, data.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            httpExchange.sendResponseHeaders(400,0);
            httpExchange.getResponseBody().close();
        }
    }

    private void handleDeleteRequest(HttpExchange httpExchange) throws IOException{
        System.out.println(httpExchange.getRequestURI().toString());

        //get data
        boolean success = deleteURI(httpExchange.getRequestURI().toString());
        if (success){
            httpExchange.sendResponseHeaders(200, 0);
        }else{
            httpExchange.sendResponseHeaders(400,0);
        }
        httpExchange.close();
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

    abstract String postURI(String URI,String value);

    abstract boolean deleteURI(String URI);
}
