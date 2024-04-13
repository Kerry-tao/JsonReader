package json_reader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.fasterxml.jackson.databind.JsonNode;

public class BPlusTree {
    public InternalTreeNode root;
    public LeafTreeNode firstLeaf;
    public int count;

    public BPlusTree() {
        this.count = 0;
        this.root = new InternalTreeNode();
        // this.firstLeaf = null;
    }

    public void buildTreeFromJson(JsonNode jsonTree) {

        Stack<InternalTreeNode> stack = new Stack<>();

        // 获取根节点数据
        JsonNode rootJsonNode = jsonTree.get(0);
        root.buildInternalNodeFromJson(rootJsonNode);

        // 将根节点压入栈中
        stack.push(root);
        int count = 1;
        if (jsonTree.isArray()) {
            Iterator<JsonNode> elementsIterator = jsonTree.iterator();

            if (elementsIterator.hasNext()) {
                elementsIterator.next(); // skip the first element
            }
            while (elementsIterator.hasNext()) {
                JsonNode currentNode = elementsIterator.next();
                count++;
                if (stack.size() > 0) {
                    InternalTreeNode parentNode = stack.peek();
                    // LeafTreeNode previous = new LeafTreeNode();
                    if (currentNode.get("isLeaf").asBoolean()) {
                        // 当前节点为叶子节点
                        // 新创建叶子节点
                        LeafTreeNode leafNode = new LeafTreeNode();
                        leafNode = leafNode.buildLeafNodeFromJson(currentNode);
                        // 将叶子节点插入树中
                        if (parentNode.childIndex.contains(leafNode.index)) {
                            parentNode.children.add(leafNode);
                        }
                        // if (this.firstLeaf == null) {
                        // this.firstLeaf = leafNode;
                        // previous.next = leafNode;
                        // } else {
                        // previous.next = leafNode;
                        // previous = leafNode;
                        // }
                    } else {
                        // 当前节点为内部节点
                        // 新建内部节点
                        InternalTreeNode internalNode = new InternalTreeNode();
                        internalNode = internalNode.buildInternalNodeFromJson(currentNode);
                        // 将节点加入树中
                        while (!parentNode.childIndex.contains(internalNode.index) && stack.size() > 0) {
                            stack.pop();
                            parentNode = stack.peek();
                        }
                        if (parentNode.childIndex.contains(internalNode.index)) {
                            parentNode.children.add(internalNode);
                            stack.push(internalNode);
                        }
                    }
                }
            }
        }
        this.count = count;
    }

    public void BFS() {
        if (root != null) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                TreeNode current = queue.poll();
                System.out.print(current.index + " ");
                for (TreeNode child : current.children) {
                    queue.offer(child);
                }
            }
        }
    }

    // 递归绘图方法
    public void drawTree(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename + ".dot");
        writer.write("digraph G {\n"); // 开始DOT文件内容
        // 写入根节点
        writeNode(writer, this.root, "  ");
        writer.write("}\n");
        writer.close();
    }

    private void writeNode(FileWriter writer, TreeNode node, String indent)
            throws IOException {
        // 为当前节点生成DOT语句
        writer.write(indent + "\"" + node.index + "\";\n");
        // 对于每一个子节点递归生成边和子节点
        if (!node.isLeaf) {
            for (TreeNode child : node.children) {
                writer.write(indent + "\"" + node.index + "\" -> \"" + child.index +
                        "\";\n");
                if (!child.isLeaf) {
                    writeNode(writer, child, indent + " "); // 增加缩进以展现层级关系
                }
            }
        }
    }

    @Override
    public String toString() {
        return "BPlusTree{" +
                "has " + count + " nodes" +
                "}";
    }
}
