package br.udesc.pinii.macro.model;

public class Node {

    private String name; //nome do nodo
    private int dist; //distancia deste nodo em relação ao nodo inicial
    private Node prev; //nodo anterior
    private boolean flag; //flag de acesso, não sei que porra é essa

    public Node(String name, int dist, Node prev, boolean flag) {
        this.name = name;
        this.dist = dist;
        this.prev = prev;
        this.flag = flag;
    }
}
