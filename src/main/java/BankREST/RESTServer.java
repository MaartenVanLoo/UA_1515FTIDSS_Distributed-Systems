package BankREST;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Collections;

// https://spring.io/guides/gs/rest-service/
// Application class
@SpringBootApplication
public class RESTServer {
    public static void main(String... args) {
        SpringApplication app = new SpringApplication(RESTServer.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));  // set the default port to 8083
        app.run(args);
    }
}
