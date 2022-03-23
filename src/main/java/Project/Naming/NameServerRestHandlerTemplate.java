package Project.Naming;

import Project.REST_API.RestAPI_Handler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class NameServerRestHandlerTemplate extends RestAPI_Handler {
    public NameServerRestHandlerTemplate() {}

    @Override
    public String getURI(String URI) {
        return null;
    }

    @Override
    public String putURI(String URI, String value) {
        return null;
    }

    @Override
    public String postURI(String URI, String value) {
        return null;
    }

    @Override
    public boolean deleteURI(String URI) {
        return false;
    }
}
