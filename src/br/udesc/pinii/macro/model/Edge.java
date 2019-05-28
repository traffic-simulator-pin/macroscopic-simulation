package br.udesc.pinii.macro.model;

import br.udesc.pinii.macro.util.Params;


public class Edge implements Comparable<Edge> {

    private String id;
    private boolean undirected;
    private Node source;
    private Node target;
    private float capacity;
    private float freeFlowCost;
    private float constantA;
    private float constantB;
    private int totalFlow;
    private int vehiclesCount;
    private float msaFlow;
    private double cost;

    public String getId() {
        return id;
    }

    public Edge() {

    }

    public Edge(String id, Node source, Node target, float capacity, float length, boolean directed,
                float freeflow, float constantA, float constantB) {
        this.id = id;
        this.undirected = !directed;

        this.source = source;
        this.source.addOutEdge(this);
        this.target = target;
        this.target.addInEdge(this);

        if (this.undirected) {

            this.source.addInEdge(this);
            this.target.addOutEdge(this);
        }

        this.vehiclesCount = 0;

        this.totalFlow = 0;
        this.msaFlow = 0;

        this.capacity = capacity;
        this.cost = length;
        this.freeFlowCost = freeflow;
        this.constantA = constantA;
        this.constantB = constantB;
    }


    public void updateCost() {
        this.cost = this.getFreeFlowCost() * (1 + this.getConstantA() * Math.pow(this.getVehiclesCount() / this.getCapacity(), getConstantB()));
    }

    public float getAcumulatedCost() {
        return (float) (this.cost = this.getFreeFlowCost() * (1 + this.getConstantA() * Math.pow(this.getTotalFlow() / this.getCapacity(), getConstantB())));
    }

    public void incVehiclesHere() {
        this.vehiclesCount++;
        this.totalFlow++;
    }

    public void clearVehiclesHere() {
        this.vehiclesCount = 0;
    }

    public void clearTotalFlow() {
        this.totalFlow = 0;
    }

    public void updateMSAFlow() {
        this.msaFlow = (1 - Params.PHI_MSA) * this.msaFlow + Params.PHI_MSA * (this.totalFlow);
    }

    public int getTotalFlow() {
        return totalFlow;
    }

    public float msaCost() {
        return (float) (this.cost = this.getFreeFlowCost() * (1 + this.getConstantA() * Math.pow(this.getMsaFlow() / this.getCapacity(), getConstantB())));
    }

    public void afterEpisode() {
        this.updateMSAFlow();
    }

    public float getMsaFlow() {
        return msaFlow;
    }

    public float getConstantA() {
        return constantA;
    }

    public float getConstantB() {
        return constantB;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public float getCapacity() {
        return capacity;
    }

    public float getFreeFlowCost() {
        return freeFlowCost;
    }


    public double getCost() {
        return cost;
    }


    public int getVehiclesCount() {
        return vehiclesCount;
    }

    @Override
    public String toString() {
        return source + ((undirected) ? " -- " : " -- ") + target;
    }

    @Override
    public int compareTo(Edge o) {
        try {
            return Float.compare(new Float(this.id), new Float(o.id));
        } catch (Exception e) {
            return this.id.compareTo(o.id);
        }
    }
}
