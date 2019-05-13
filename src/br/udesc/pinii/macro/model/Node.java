package br.udesc.pinii.macro.model;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {

    private String name;
    private List<Edge> edges;
    private Node previosNode;
    private int x;
    private int y;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
