package br.udesc.pinii.macro.model;

public class RouteEntry {

    private Edge edge;
    private double cost;

    public RouteEntry(Edge edge, double cost) {
        this.edge = edge;
        this.cost = cost;
    }

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
