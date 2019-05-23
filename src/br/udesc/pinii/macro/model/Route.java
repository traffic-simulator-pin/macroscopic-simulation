package br.udesc.pinii.macro.model;

import java.util.List;

public class Route {

    private List<Node> path;
    private float cost;
    private String name;

    public Route(List<Node> path, float cost) {
        this.path = path;
        this.cost = cost;
        for (Node node : path) {
            for (Edge edge : node.getEdges()) {
                this.name += edge.getTarget().toString();

            }
        }
    }


    public List<Node> getPath() {
        return path;
    }

    public void setPath(List<Node> path) {
        this.path = path;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
