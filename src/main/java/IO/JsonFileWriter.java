package IO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;

public class JsonFileWriter {
    private final String fileLocation;
    private final File file;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ObjectNode jsonNode = objectMapper.createObjectNode();
    public JsonFileWriter(String fileLocation) {
        file = new File(fileLocation);
        if (!file.exists()) {
            try {
                file.createNewFile();
                write(jsonNode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            jsonNode = new JsonFileReader(fileLocation).getJsonNode();
            //System.out.println(jsonNode.toString());
        }
        if (!file.canWrite())
            throw new RuntimeException("This file is not writeable!");

        this.fileLocation = fileLocation;
    }
    public ObjectNode getJsonNode() {
        return jsonNode;
    }
    public ObjectNode getNewJsonNode() {
        return new ObjectMapper().createObjectNode();
    }
    public void write(JsonNode jsonNode) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, jsonNode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}