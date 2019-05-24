package br.udesc.pinii.macro.model;

public class EdgeAux {

    private Edge edge;
    private double cost;

    public EdgeAux(Edge edge, double cost) {
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


}
