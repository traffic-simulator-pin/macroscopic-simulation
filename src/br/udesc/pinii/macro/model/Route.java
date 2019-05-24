package br.udesc.pinii.macro.model;

import java.util.List;

public class Route {

    private List<Edge> path;
    private float cost;
    private String name;


    public Route(List<Edge> path, float cost) {
        this.path = path;
        this.cost = cost;
        this.name = path.get(0).getSource().toString();
        for (Edge e : path) {
            this.name += e.getTarget().toString();
        }
    }

    public List<Edge> getPath() {
        return path;
    }

}

