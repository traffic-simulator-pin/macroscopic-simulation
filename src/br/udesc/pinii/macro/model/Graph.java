package br.udesc.pinii.macro.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Graph {

    private HashMap<String, Node> nodes;
    private HashMap<String, Edge> edges;


    public Graph(HashMap<String, Node> node, HashMap<String, Edge> edges) {
        this.nodes = node;
        this.edges = edges;
    }

    public Node getNodes(String key) {
        return nodes.get(key);
    }

    public List<Edge> getEdges() {
        return new LinkedList<>(edges.values());
    }

    public List<Node> getNodes() {
        return new LinkedList<>(nodes.values());
    }


}
