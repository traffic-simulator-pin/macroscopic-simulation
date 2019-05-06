package br.udesc.pinii.macro.control;

import br.udesc.pinii.macro.model.Node;

import java.io.File;
import java.util.List;

public interface ISimulationController {
    void generateGraph(File file);

    void msa();

    List<Node> dijkstra(Node sourceNode, Node targetNode);

    void evaluateAssignment();

    void resetGraph();

    void pickSmallestNode();

    void pickEdgesList();

    void printGraph();

    void calcPathLength();

    void getPathAsEdges();

    void getPathAsNodes();

    void printPath();

    void pathToStr();


}
