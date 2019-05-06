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

    public Edge(String name, Node source, Node target, double constantA, double constantB, int maxSpeed, double roadSize, double roadCapacity) {
        this.name = name;
        this.source = source;
        this.target = target;
        this.constantA = constantA;
        this.constantB = constantB;
        this.maxSpeed = maxSpeed;
        this.length = roadSize;
        this.roadCapacity = roadCapacity;
    }

    public Edge(Node source, Node target, double length) {
        this.source = source;
        this.target = target;
        this.length = length;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public double getConstantA() {
        return constantA;
    }

    public void setConstantA(int constantA) {
        this.constantA = constantA;
    }

    public double getConstantB() {
        return constantB;
    }

    public void setConstantB(int constantB) {
        this.constantB = constantB;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getRoadCapacity() {
        return roadCapacity;
    }

    public void setRoadCapacity(double roadCapacity) {
        this.roadCapacity = roadCapacity;
    }
}
