package Project.REST_API;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RestAPI_Server {
    HttpServer server;
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    int port = 8001;
    int queueSize = 0;

    public RestAPI_Server() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress("localhost", port), queueSize);;
        this.server.setExecutor(threadPoolExecutor);
    }
    public RestAPI_Server(String hostname) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(hostname,port),queueSize);
        this.server.setExecutor(threadPoolExecutor);
    }
    public RestAPI_Server(String hostname, int port) throws IOException {
        this.port = port;
        this.server = HttpServer.create(new InetSocketAddress(hostname,port),queueSize);
        this.server.setExecutor(threadPoolExecutor);
    }
    public void addContext(String path, HttpHandler handler){
        server.createContext(path, handler);
    }

    public void start(){
        this.server.start();
    }
}


