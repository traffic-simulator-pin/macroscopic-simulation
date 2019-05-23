package br.udesc.pinii.macro.model;

public class EdgeEntry {


    private Edge edge;
    private double cost;

    public EdgeEntry(Edge edge, double cost) {
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

    public void setCost(double cost) {
        this.cost = cost;
    }

}
