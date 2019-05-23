package br.udesc.pinii.macro.model;

public class Edge {

    private String name;
    private Node source;
    private Node target;
    private double constantA;
    private double constantB;
    private int maxSpeed;
    private double length;
    private double roadCapacity;
    private float freeFlowCost;
    private int totalFlow;
    private int vehiclesCount;
    private float msaFlow;
    private double cost;

    public Edge(String name, Node source, Node target, double constantA, double constantB, int maxSpeed, double roadSize, double roadCapacity) {
        this.name = name;
        this.source = source;
        this.target = target;
        this.constantA = constantA;
        this.constantB = constantB;
        this.maxSpeed = maxSpeed;
        this.length = roadSize;
        this.roadCapacity = roadCapacity;
        this.cost = roadCapacity / maxSpeed * 60;
        this.totalFlow = 0;
        this.msaFlow = 0;
    }

    public Edge(String name, Node source, Node target, double constantA, double constantB, int maxSpeed, double length, double roadCapacity, float freeFlowCost, int totalFlow, int vehiclesCount, double cost) {
        this.name = name;
        this.source = source;
        this.target = target;
        this.constantA = constantA;
        this.constantB = constantB;
        this.maxSpeed = maxSpeed;
        this.length = length;
        this.roadCapacity = roadCapacity;
        this.freeFlowCost = freeFlowCost;
        this.totalFlow = totalFlow;
        this.vehiclesCount = vehiclesCount;
        this.cost = cost;
        this.totalFlow = 0;
        this.msaFlow = 0;
    }

    public Edge(Node source, Node target, double length) {
        this.source = source;
        this.target = target;
        this.length = length;
    }

    public int getVehiclesCount() {
        return vehiclesCount;
    }

    public float getMsaFlow() {
        return msaFlow;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public double getLength() {
        return length;
    }

    public void afterEpisode() {
        this.updateMSAFlow();
    }

    public void clearVehiclesHere() {
        this.vehiclesCount = 0;
    }

    public void clearTotalFlow() {
        this.totalFlow = 0;
    }

    public void updateMSAFlow() {
        this.msaFlow = (1 - 0.5f) * this.msaFlow + 0.5f * (this.totalFlow);
    }

    public void updateCost() {
        this.msaFlow = (1 - 0.5f) * this.msaFlow + 0.5f * (this.totalFlow);
    }

    public void incVehiclesHere() {
        this.vehiclesCount++;
        this.totalFlow++;
    }

    public double msaCost() {
        return this.freeFlowCost * (1 + this.constantA * (float) Math.pow(this.msaFlow / this.roadCapacity, this.constantB));
    }
}
