package br.udesc.pinii.macro.model;

import java.util.ArrayList;

public interface Graph {

    ArrayList<Node> getAdjacentNode(Node n);

    ArrayList<Node> getNodes();

    ArrayList<Edge> getEdges();

    Node getNode(String name);

    void addNode(Node node, Node newNode);

    void addNode(Node newNode, String name);

    void addEdge(Edge newEdge);

}
