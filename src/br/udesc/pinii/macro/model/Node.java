package br.udesc.pinii.macro.model;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {

    private String name;
    private List<Edge> edges;
    private boolean visited;
    private Node previosNode;
    private double minDistance = Double.MAX_VALUE;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addNeighbour(Edge edge) {
        this.edges.add(edge);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Node getPreviosNode() {
        return previosNode;
    }

    public void setPreviosNode(Node previosNode) {
        this.previosNode = previosNode;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Node node) {
        return Double.compare(this.minDistance, node.minDistance);
    }


}
