package br.udesc.pinii.macro.model;

import java.util.ArrayList;
import java.util.HashMap;

public class GraphAdjacencyList implements Graph {

    HashMap<Node, ArrayList<Node>> graph = new HashMap<Node, ArrayList<Node>>();
    HashMap<String, Node> graph2 = new HashMap<String, Node>();

    @Override
    public ArrayList<Node> getAdjacentNode(Node n) {
        if (n != null && this.graph.containsKey(n))
            return this.graph.get(n);
        else
            return null;
    }

    @Override
    public ArrayList<Node> getNodes() {
        ArrayList<Node> keys = new ArrayList<Node>();
        try {
            keys.addAll(graph.keySet());
            return keys;
        } finally {
            keys = null;
        }
    }

    @Override
    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> list = new ArrayList<Edge>();
        ArrayList<Node> keys = new ArrayList<Node>();

        try {
            keys.addAll(this.graph.keySet());
            ArrayList<Node> edges = null;
            for (int i = 0; i < keys.size(); i++) {
                edges = this.graph.get(keys.get(i));
                for (int j = 0; j < edges.size(); j++) {
                    Edge a = new Edge(new Node(keys.get(i).getName()), new Node(edges.get(j).getName()));
                    if (a != null)
                        list.add(a);
                }
            }
            return list;
        } finally {
            list = null;
            keys = null;
        }
    }

    @Override
    public Node getNode(String name) {
        if (this.graph2.containsKey(name)) {
            return graph2.get(name);
        }
        return null;
    }

    @Override
    public void addNode(Node node, Node newNode) {
        if (node != null && newNode != null && this.graph.containsKey(node)) {
            this.graph.get(node).add(newNode);
        }
    }

    @Override
    public void addNode(Node newNode, String name) {
        if (!graph2.containsKey(name)) {
            this.graph.put(newNode, new ArrayList<Node>());
            this.graph2.put(name, newNode);
        } else {
        }
    }

    @Override
    public void addEdge(Edge newEdge) {
        if (graph.containsKey(newEdge.getSource())) {
            if (graph.get(newEdge.getSource()).contains(newEdge.getTarget()))
                System.out.println("O grafo já contem o vertice especificado");
            else
                graph.get(newEdge.getSource()).add(newEdge.getTarget());
        } else {
            ArrayList<Node> list = new ArrayList<Node>();
            list.add(newEdge.getTarget());
            graph.put(newEdge.getSource(), list);
            list = null;
        }

    }
}
