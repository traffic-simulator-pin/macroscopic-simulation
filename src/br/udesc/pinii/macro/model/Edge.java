package br.udesc.pinii.macro.model;

public class Edge{

    private String name;
    private Node source;
    private Node target;
    private int constantA;
    private int constantB;
    private int maxSpeed;
    private double roadSize;
    private double roadCapacity;

    public Edge(String name, Node source, Node target, int constantA, int constantB, int maxSpeed, double roadSize, double roadCapacity) {
        this.name = name;
        this.source = source;
        this.target = target;
        this.constantA = constantA;
        this.constantB = constantB;
        this.maxSpeed = maxSpeed;
        this.roadSize = roadSize;
        this.roadCapacity = roadCapacity;
    }

    public Edge(Node n1, Node n2) {
        this.source = n1;
        this.target = n2;
    }

    public String getName() {
        return name;
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getTarget() {
        return target;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public int getConstantA() {
        return constantA;
    }

    public int getConstantB() {
        return constantB;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public double getRoadSize() {
        return roadSize;
    }

    public double getRoadCapacity() {
        return roadCapacity;
    }
}
