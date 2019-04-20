package br.udesc.pinii.macro.control;

import br.udesc.pinii.macro.model.GraphAdjacencyList;
import br.udesc.pinii.macro.model.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainController {

    private static MainController inscante;

    public static MainController getInstance() {
        if (inscante == null)
            inscante = new MainController();

        return inscante;
    }

    private MainController() {

        /*
         * Primeiramente é necessário gerar um grafo com base no arquivo selecionado;
         * Após isso é chamado o método msa (method of successive averages)
         * Dentro de msa é utilizado os métodos, getPathEdges, pathToStr, dijkstra e evaluateAssigment
         * */

    }

    //Métodos principais
    public void generateGraph() {
    }

    public void msa() {
    }

    public GraphAdjacencyList dijkstra(GraphAdjacencyList graph, String source, HashMap<String, Double> edgeHash) {

        graph.getNode(source).setDist(0);
        HashMap<String, String> listaVerticeHash = new HashMap<String, String>();
        List<String> listaVertice = new ArrayList<String>();
        List<Node> listaVertice2 = new ArrayList<Node>();
        List<String> listaVerticeVisitados = new ArrayList<String>();
        HashMap<String, String> listaVerticeVisitadosHash = new HashMap<String, String>();
        double distVert = 999999999;
        int count = 0;

        listaVertice2 = graph.getNodes();
        for (int i = 0; i < listaVertice2.size(); i++) {
            listaVertice.add(listaVertice2.get(i).getName());
            listaVerticeHash.put(listaVertice2.get(i).getName(), listaVertice2.get(i).getName());
        }
        System.out.println("Quant de v: " + listaVertice.size());
        listaVerticeVisitados.add(source);
        listaVerticeVisitadosHash.put(source, source);

        while (!listaVerticeVisitados.isEmpty()) {
            distVert = 999999999;
            String verticeMenor = "";
            for (int i = 0; i < listaVerticeVisitados.size(); i++) {
                Node u = graph.getNode(listaVerticeVisitados.get(i));
                if (u.getDist() < distVert) {
                    distVert = u.getDist();
                    verticeMenor = listaVerticeVisitados.get(i);
                }
            }
            listaVertice.remove(verticeMenor);
            listaVerticeVisitados.remove(verticeMenor);
            listaVerticeHash.remove(verticeMenor);
            listaVerticeVisitadosHash.remove(verticeMenor);
            List<String> vertAdj = new ArrayList<String>();
            List<Node> vertAdj2 = new ArrayList<Node>();
            Node u = graph.getNode(verticeMenor);
            vertAdj2 = graph.getAdjacentNode(u);
            for (int i = 0; i < vertAdj2.size(); i++) {
                Node v = vertAdj2.get(i);
                vertAdj.add(v.getName());
                if (!listaVerticeVisitadosHash.containsKey(v.getName()) && listaVerticeHash.containsKey(v.getName())) {
                    listaVerticeVisitados.add(v.getName());
                    listaVerticeVisitadosHash.put(v.getName(), v.getName());
                }
            }
            for (int i = 0; i < vertAdj.size(); i++) {
                double alt;
                double distUV;//de u até v
                Node v = graph.getNode(vertAdj.get(i));
                String arestachave = "";
                arestachave = v.getName() + u.getName();
                distUV = edgeHash.get(arestachave); //aresta id de v - id de u
                if (distUV < 0) {
                    distUV = Math.abs(distUV);
                }

                alt = u.getDist() + distUV;
                if (alt < v.getDist()) {
                    graph.getNode(v.getName()).setDist(alt);
                    graph.getNode(v.getName()).setPrev(u.getName());
                }
            }
            count++;
            if (count % 1000 == 0) {
                System.out.println("Count: " + count);
            }
        }

        System.out.println("");

        return graph;
    }

    public void evaluateAssignment() {
    }

    //Métodos de ajuda
    public void resetGraph() {
    }

    public void pickSmallestNode() {
    }

    public void puckEdgesList() {
    }

    public void printGraph() {
    }

    public void calcPathLength() {
    }

    public void getPathAsEdges() {
    }

    public void getPathAsNodes() {
    }

    public void printPath() {
    }

    public void pathToStr() {
    }

}
