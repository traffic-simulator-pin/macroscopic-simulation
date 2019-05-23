package br.udesc.pinii.macro.model;

import java.util.Random;

public class MSA {

    private int id;
    private Node source;
    private Node target;
    private Node currentNode;
    private Edge currentEdge;
    private Random random;
    private Float time;

    public MSA(int id, Node source, Node target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public void runMsa(){

    }
}
