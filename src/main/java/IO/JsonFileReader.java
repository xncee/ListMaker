package IO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;

public class JsonFileReader {
    private final String fileLocation;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private JsonNode jsonNode;
    public JsonFileReader(String fileLocation) {
        this.fileLocation = fileLocation;
        read();
    }
    public void read() {
        try {
            jsonNode = objectMapper.readTree(new File(fileLocation));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public ObjectNode getJsonNode() {
        return (ObjectNode) jsonNode;
    }
    public JsonNode get(String... keys) {
        JsonNode value = jsonNode;
        for (String k: keys) {
            value = value.get(k);
        }
        return value;
    }
}
