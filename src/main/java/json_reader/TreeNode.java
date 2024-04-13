package json_reader;

import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonNode;

public class TreeNode {
    protected int index;
    protected boolean isLeaf;
    protected ArrayList<Integer> keys;
    protected ArrayList<TreeNode> children;

    public TreeNode() {
        this.index = -1;
        this.isLeaf = false;
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public TreeNode(int index, boolean isLeaf, ArrayList<Integer> keys) {
        this.index = index;
        this.isLeaf = isLeaf;
        this.keys = keys;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "index=" + index +
                ", isLeaf=" + isLeaf +
                ", keysSize=" + keys.size() +
                '}';
    }
}

/**
 * Internal TreeNode
 */
class InternalTreeNode extends TreeNode {
    protected ArrayList<Integer> childIndex;
    // protected ArrayList<TreeNode> children;

    public InternalTreeNode() {
        super();
        // this.childIndex = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public InternalTreeNode(int index, boolean isLeaf, ArrayList<Integer> keys) {
        super(index, isLeaf, keys);
        this.isLeaf = false;
        this.childIndex = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public InternalTreeNode buildInternalNodeFromJson(JsonNode currentNode) {
        this.index = currentNode.get("index").asInt();
        this.isLeaf = currentNode.get("isLeaf").asBoolean();
        this.keys = new ArrayList<>();
        for (JsonNode key : currentNode.get("keys")) {
            this.keys.add(key.asInt());
        }
        this.childIndex = new ArrayList<>();
        for (JsonNode child : currentNode.get("children")) {
            this.childIndex.add(child.asInt());
        }
        return this;
    }

    @Override
    public String toString() {
        return "InternalTreeNode{" +
                "index=" + index +
                ", isLeaf=" + isLeaf +
                ", keysSize=" + keys.size() +
                ", childIndexSize=" + childIndex.size() +
                ", childrenSize=" + children.size() +
                '}';
    }
}

/**
 * Leaf Tree Node
 */
class LeafTreeNode extends TreeNode {
    protected ArrayList<Double> values;
    protected ArrayList<ArrayList<Double>> datas;
    public LeafTreeNode next;

    public LeafTreeNode() {
        super();
        this.values = new ArrayList<>();
        this.datas = new ArrayList<>();
        this.next = null;
    }

    public LeafTreeNode(int index, boolean isLeaf, ArrayList<Integer> keys, ArrayList<Double> values,
            ArrayList<ArrayList<Double>> datas) {
        super(index, isLeaf, keys);
        this.values = values;
        this.datas = datas;
    }

    public LeafTreeNode buildLeafNodeFromJson(JsonNode currentNode) {
        this.index = currentNode.get("index").asInt();
        this.isLeaf = currentNode.get("isLeaf").asBoolean();
        this.keys = new ArrayList<>();
        for (JsonNode key : currentNode.get("keys")) {
            this.keys.add(key.asInt());
        }
        this.values = new ArrayList<>();
        for (JsonNode value : currentNode.get("values")) {
            this.values.add(value.asDouble());
        }
        this.datas = new ArrayList<>();
        for (JsonNode data : currentNode.get("datas")) {
            ArrayList<Double> dataList = new ArrayList<>();
            for (JsonNode value : data) {
                dataList.add(value.asDouble());
            }
            this.datas.add(dataList);
        }
        return this;
    }

    @Override
    public String toString() {
        return "LeafTreeNode{" +
                "index=" + index +
                ", isLeaf=" + isLeaf +
                ", keysSize=" + keys.size() +
                ", valuesSize=" + values.size() +
                ", datasSize=" + datas.size() +
                '}';
    }
}