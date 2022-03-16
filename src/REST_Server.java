import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class REST_Server {
    HttpServer server;
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    public REST_Server(int port, int queueSize) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress("localhost", port), queueSize);;
        this.server.setExecutor(threadPoolExecutor);

        server.createContext("/userService", new  FirstHttpHandler());
    }

    public void start(){
        this.server.start();
        System.out.println("Server stated");
    }

    private class FirstHttpHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String requestParamValue = null;
            if ("GET".equals(httpExchange.getRequestMethod())){
                requestParamValue = handleGetRequest(httpExchange);
            }
            else if ("POST".equals(httpExchange.getRequestMethod())){
                requestParamValue = handlePostRequest(httpExchange);
            }else{
                System.out.println("Unknown request");
                return;
            }
            handleResponse(httpExchange,requestParamValue);
        }

        String handleGetRequest(HttpExchange httpExchange){
            System.out.println("Get Request");
            return loadHtmlFile("rsc/test.html");
            //return httpExchange.getRequestURI().toString();

        }
        String handlePostRequest(HttpExchange httpExchange){
            System.out.println("Post Request");
            return "";
        }
        void handleResponse(HttpExchange httpExchange, String result) throws IOException {
            System.out.println("Answer");
            OutputStream outputStream = httpExchange.getResponseBody();
            httpExchange.sendResponseHeaders(200, result.length());
            outputStream.write(result.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
            return;
        }
        String loadHtmlFile(String filename){
            String line;
            String htmlPage = "";
            try{
                Path currentWorkingDir = Paths.get("").toAbsolutePath();
                System.out.println(currentWorkingDir.toString());
                File file = new File(filename);
                System.out.println("*****Loading file*****");
                System.out.println("filename: " + file.getName());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                while ((line = bufferedReader.readLine()) != null) {
                    htmlPage += line;
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return htmlPage;
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Starting REST server");
        System.out.println("Server host ip:");
        IPUtils.printIpv4Interfaces();
        REST_Server webserver = new REST_Server(8001,1);
        webserver.start();

    }
}


