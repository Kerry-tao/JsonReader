package json_reader;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonNode;

public class TopLayer {

    protected ArrayList<ArrayList<ArrayList<Double>>> weights;
    protected ArrayList<ArrayList<Double>> bias;

    public TopLayer() {
        this.weights = new ArrayList<>();
        this.bias = new ArrayList<>();
    }

    public TopLayer(JsonNode jsonString) {
        JsonNode weightNode = jsonString.get("weights");
        JsonNode biasNode = jsonString.get("bias");

        if (weightNode != null) {
            this.weights = new ArrayList<>();
            for (JsonNode weight : weightNode) {
                // System.out.println(weight);
                ArrayList<ArrayList<Double>> weightArray = new ArrayList<>();
                for (JsonNode node : weight) {
                    ArrayList<Double> nodeArray = new ArrayList<>(node.size());
                    for (JsonNode num : node) {
                        nodeArray.add(num.asDouble());
                    }
                    weightArray.add(nodeArray);
                }
                this.weights.add(weightArray);
            }
        }

        if (biasNode != null) {
            this.bias = new ArrayList<>();
            for (JsonNode node : biasNode) {
                ArrayList<Double> nodeArray = new ArrayList<>();
                for (JsonNode node2 : node) {
                    nodeArray.add(node2.asDouble());
                }
                this.bias.add(nodeArray);
            }
        }
    }

    public void buildWABFromJson(JsonNode jsonString) {
        JsonNode weightNode = jsonString.get("weights");
        JsonNode biasNode = jsonString.get("bias");

        if (weightNode != null) {
            this.weights = new ArrayList<>();
            for (JsonNode weight : weightNode) {
                ArrayList<ArrayList<Double>> weightArray = new ArrayList<>();
                for (JsonNode node : weight) {
                    ArrayList<Double> nodeArray = new ArrayList<>(node.size());
                    for (JsonNode num : node) {
                        nodeArray.add(num.asDouble());
                    }
                    weightArray.add(nodeArray);
                }
                this.weights.add(weightArray);
            }
        }

        if (biasNode != null) {
            this.bias = new ArrayList<>();
            for (JsonNode node : biasNode) {
                ArrayList<Double> nodeArray = new ArrayList<>();
                for (JsonNode node2 : node) {
                    nodeArray.add(node2.asDouble());
                }
                this.bias.add(nodeArray);
            }
        }
    }

    @Override
    public String toString() {
        return "TopLayer [weightsSize=" + weights.size() + ", biasSize=" + bias.size() + "]";
    }
}