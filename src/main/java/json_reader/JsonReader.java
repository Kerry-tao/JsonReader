package json_reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonReader {

    public TopLayer topLayer;
    public ArrayList<RecordCollection> recordCollections;
    public ArrayList<BPlusTree> bPlusTrees;

    public JsonReader() {
        topLayer = new TopLayer();
        recordCollections = new ArrayList<RecordCollection>();
        bPlusTrees = new ArrayList<BPlusTree>();
    }

    public static JsonReader readJsonFile(String path) {
        String jsonFilePath = path;
        JsonReader JsonReader = new JsonReader();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File(jsonFilePath));

            if (rootNode.isArray()) {
                // get the top layer
                JsonNode firstObject = rootNode.get(0);
                // TopLayer topLayer = new TopLayer();
                int firstStage = firstObject.get("stage").asInt();
                if (firstStage == 1) {
                    System.out.println("now, we start to store the first layer!");
                    if (firstObject.get("parameters").isObject()) {
                        JsonNode firstParaNode = firstObject.get("parameters");
                        firstParaNode.fields().forEachRemaining(entry -> {
                            JsonNode fieldValue = entry.getValue();
                            JsonReader.topLayer.buildWABFromJson(fieldValue);
                            System.out.println(JsonReader.topLayer);
                        });
                    }
                }

                JsonNode secondObject = rootNode.get(1);
                int secondStage = secondObject.get("stage").asInt();
                // get the second layer
                if (secondStage == 2) {
                    System.out.println("now, we start to store the second layer!");
                    if (secondObject.get("parameters").isObject()) {
                        JsonNode parametersNode = secondObject.get("parameters");
                        // traverse the parameters
                        parametersNode.fields().forEachRemaining(entry -> {
                            String fieldName = entry.getKey();
                            JsonNode fieldValue = entry.getValue();
                            boolean isNN = fieldValue.has("weights");
                            // whether there is a weights attribute
                            // if so, it is a neural network ouput
                            // else it is a B+ tree
                            if (isNN) {
                                RecordCollection recordCollection = new RecordCollection();
                                recordCollection.buildRecordFromJson(fieldValue);
                                System.out.println("No." + fieldName + " is NN structure: " + recordCollection);
                                JsonReader.recordCollections.add(recordCollection);
                            } else {
                                BPlusTree tree = new BPlusTree();
                                tree.buildTreeFromJson(fieldValue);
                                System.out.println("No." + fieldName + " is B+ tree structure: " + tree);
                                JsonReader.bPlusTrees.add(tree);
                            }
                        });
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading or parsing JSON file: " + e.getMessage());
        }
        return JsonReader;
    }

    @Override
    public String toString() {
        return "there are three parts in this object:\n" +
                "topLayer" + topLayer + '\n' +
                "recordCollections" + recordCollections + '\n' +
                "bPlusTree" + bPlusTrees;

    }

    public static void main(String[] args) throws IOException {
        /**
         * you can use this function in other place just like this,only change the file
         * path
         * and you can get the object you want like
         * AllMyData.topLayer
         * AllMyData.topLayer.weights/bias
         * AllMyData.recordCollections
         * AllMyData.recordCollections.get(0).topPart
         * AllMyData.recordCollections.get(0).records
         * AllMyData.bPlusTrees
         * AllMyData.bPlusTrees.get(0).root
         */
        JsonReader AllMyData = readJsonFile("src/main/resources/100000.json");

        // example
        BPlusTree bPlusTree1 = AllMyData.bPlusTrees.get(0);
        bPlusTree1.drawTree("bPlusTree1");
        // bPlusTree1.BFS();
    }
}
