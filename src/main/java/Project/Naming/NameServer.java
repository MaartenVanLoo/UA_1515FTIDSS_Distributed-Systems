package Project.Naming;
import Project.REST_API.RestAPI_Server;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.stream.Collectors;

public class NameServer {
    final String mappingFile = "nameServerMap";
    private HashMap<Integer,String> ipMapping; //id =>ip;
    RestAPI_Server restAPI_server = new RestAPI_Server();

    public NameServer() throws IOException {}

    private void writeMapToFile(String filename) throws IOException {
        JSONObject jsonObject = new JSONObject();
        for (int key : ipMapping.keySet()){
            System.out.println(key);
            jsonObject.put(key, ipMapping.get(key));
        }
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
        jsonObject.writeJSONString(out);
        out.flush();
        out.close();
    }
    private void readMapFromfile(String filename) throws FileNotFoundException, ParseException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(reader.lines().collect(Collectors.joining(System.lineSeparator())));

        this.ipMapping.clear();
        for (Object key : jsonObject.keySet()){
            this.ipMapping.put((int)(long)key,(String)jsonObject.get(key));
        }
    }
    public void addNode(int Id, String ip){
        this.ipMapping.put(Id,ip);
        try {
            writeMapToFile(this.mappingFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    public void removeNode(int Id,String ip){
        if (this.ipMapping.containsKey(Id)){
            this.ipMapping.remove(Id);
            try {
                writeMapToFile(this.mappingFile);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        };
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting NameServer");
    }
}
