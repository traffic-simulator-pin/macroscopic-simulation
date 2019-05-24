package br.udesc.pinii.macro.model;

import java.util.LinkedList;
import java.util.List;

public class Node {

    private final String id;
    private List<Edge> inEdges;
    private List<Edge> outEdges;

    public Node(String id) {
        this.id = id;

        this.inEdges = new LinkedList<>();
        this.outEdges = new LinkedList<>();
    }

    @Override
    public String toString() {
        return id;
    }

    public void addInEdge(Edge edge) {
        this.inEdges.add(edge);
    }

    public void addOutEdge(Edge edge) {
        this.outEdges.add(edge);
    }

    public List<Edge> getOutEdges() {
        return outEdges;
    }


}
