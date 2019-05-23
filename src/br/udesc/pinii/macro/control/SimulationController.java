package br.udesc.pinii.macro.control;

import br.udesc.pinii.macro.control.observer.Observer;
import br.udesc.pinii.macro.model.Edge;
import br.udesc.pinii.macro.model.Node;
import br.udesc.pinii.macro.model.OD;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationController implements ISimulationController {

    private static SimulationController inscante;

    private List<Node> nodes;
    private List<Observer> observers;
    private Double[][] od;
    private List<OD> odPairs;

    //Posso tirar isso aqui
    private final ExecutorService eservice = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //Task executor
    private final CompletionService<Object> cservice = new ExecutorCompletionService<>(eservice);

    private int episode = 0;
    private int step = 0;
    private float phi = 1.0f;
    private int numSteps = 100;

    public static SimulationController getInstance() {
        if (inscante == null)
            inscante = new SimulationController();

        return inscante;
    }

    private SimulationController() {
        this.nodes = new ArrayList<>();
        this.odPairs = new ArrayList<>();
        this.observers = new ArrayList<>();
    }


    @Override
    public void generateGraph(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList nList = doc.getElementsByTagName("node");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                org.w3c.dom.Node nNode = nList.item(temp);
                if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    this.nodes.add(new Node(eElement.getAttribute("name")));
                    this.nodes.get(temp).setX(Integer.parseInt(eElement.getAttribute("x")));
                    this.nodes.get(temp).setY(Integer.parseInt(eElement.getAttribute("y")));
                }
            }


            NodeList edgeList = doc.getElementsByTagName("edge");

            for (int temp = 0; temp < edgeList.getLength(); temp++) {
                org.w3c.dom.Node edgeNode = edgeList.item(temp);
                if (edgeNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element eElement = (Element) edgeNode;
                    this.nodes.get(Integer.parseInt(eElement.getAttribute("source")) - 1).addNeighbour(new Edge(eElement.getAttribute("name"), this.nodes.get(Integer.parseInt(eElement.getAttribute("source")) - 1), this.nodes.get(Integer.parseInt(eElement.getAttribute("target")) - 1), Double.parseDouble(eElement.getAttribute("constantA")), Double.parseDouble(eElement.getAttribute("constantB")), Integer.parseInt(eElement.getAttribute("maxSpeed")), Double.parseDouble(eElement.getAttribute("length")), Double.parseDouble(eElement.getAttribute("capacity"))));
                }
            }

            this.od = new Double[nodes.size()][nodes.size()];

            NodeList od = doc.getElementsByTagName("connection");

            for (int temp = 0; temp < od.getLength(); temp++) {
                org.w3c.dom.Node edgeNode = od.item(temp);
                if (edgeNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element eElement = (Element) edgeNode;
                    this.odPairs.add(new OD(this.nodes.get(Integer.parseInt(eElement.getAttribute("source")) - 1), this.nodes.get(Integer.parseInt(eElement.getAttribute("target")) - 1), Double.parseDouble(eElement.getAttribute("flow"))));
                    // this.od[Integer.parseInt(eElement.getAttribute("source")) - 1][Integer.parseInt(eElement.getAttribute("target")) - 1] = Double.parseDouble(eElement.getAttribute("flow"));
                }
            }

            System.out.println(odPairs.size());
//            for (int l = 0; l < this.od.length; l++) {
//                for (int c = 0; c < this.od[0].length; c++) {
//                    System.out.print(this.od[l][c] + " ");
//                }
//                System.out.println(" ");
//            }

            notifyShowGraph();
            this.msa();

            for (Node node : this.nodes) {
                for (Edge edge : node.getEdges()) {
                    System.out.println("Carros: "+edge.getSource()+"-"+edge.getTarget()+" "+edge.getVehiclesCount());
//
                }
            }

//            System.out.println(this.nodes.toString());
//            System.out.println(this.dijkstra(this.nodes.get(0), this.nodes.get(20)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void msa() {
        System.out.println("Executando MSA...");
        float epEpsilionDefault = 1.0f;
        float epEpsilion = epEpsilionDefault;

        int numEpisodes = 150;

        while (episode < numEpisodes) {
            this.runEpisode();
            epEpsilion = epEpsilion * 0.92f;
        }

    }

    private boolean runEpisode() {
        this.episode++;
        this.step = 0;

        this.phi = 1.0f / episode;

        System.out.println("Executando before episode OD");
        for (OD od : odPairs) {
            od.reset();
            od.beforeEpisode();
        }

        System.out.println("Vericicando passos");
        while (step < numSteps) {
            if (!this.step()) {
                break;
            }
        }

        System.out.println("Verificando after eposidoes das edges");
        for (Node node : this.nodes) {
            for (Edge edge : node.getEdges()) {
                edge.afterEpisode();
            }
        }

//        for (OD od : this.odPairs) {
//            od.afterEpisode();
//        }
        return true;

    }

    private boolean step() {
        boolean finished = true;
        List<OD> driversToProcess = new LinkedList<>();

        for (OD od : this.odPairs) {
            if (!od.hasArrived() && od.mustBeProcessed()) {
                finished = false;
                driversToProcess.add(od);
            }
        }
        if (finished) {
            return false;
        }

        this.step++;

        System.out.println("chegou aqui");

//        for (OD od : driversToProcess) {
//            //this.cservice.submit(od);
//        }

//        for (OD od : driversToProcess) {
//            try {
//                boolean result = this.cservice.take().isDone();
//                if (!result) {
//                    System.out.println("step A error");
//                }
//            } catch (InterruptedException ex) {
//                System.out.println(ex);
//            }
//        }

        System.out.println("isso aqui");
        for (Node node : this.nodes) {
            for (Edge edge : node.getEdges()) {
                edge.clearVehiclesHere();
                if (step == 1) {
                    edge.clearTotalFlow();
                }
            }
        }


        for (OD od : driversToProcess) {
            od.getCurrentEdge().incVehiclesHere();
        }

        for (Node node : this.nodes) {
            for (Edge edge : node.getEdges()) {
                edge.updateCost();
            }
        }
//        for (OD driver : driversToProcess) {
//            //this.cservice.submit(driver);
//        }

//        for (OD driver : driversToProcess) {
//            try {
//                boolean result = this.cservice.take().isDone();
//                if (!result) {
//                    System.out.println("step_b error");
//                }
//            } catch (InterruptedException ex) {
//                System.out.println(ex);
//            }
//
//        }
        return true;
    }


    public int getStep() {
        return step;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public List<Node> dijkstra(Node sourceNode, Node targetNode) {
        sourceNode.setMinDistance(0);
        List<Node> path = new ArrayList<>();
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(sourceNode);

        while (!priorityQueue.isEmpty()) {
            Node node = priorityQueue.poll();

            for (Edge edge : node.getEdges()) {
                Node n = edge.getTarget();

                double length = edge.getLength();
                double minDistance = node.getMinDistance() + length;

                if (minDistance < n.getMinDistance()) {
                    priorityQueue.remove(node);
                    n.setPreviosNode(node);
                    n.setMinDistance(minDistance);
                    priorityQueue.add(n);
                }
            }

        }
        for (Node newNode = targetNode; newNode != null; newNode = newNode.getPreviosNode()) {
            path.add(newNode);
        }

        Collections.reverse(path);
        return path;
    }

    @Override
    public void evaluateAssignment() {

    }

    @Override
    public void resetGraph() {

    }

    @Override
    public void pickSmallestNode() {

    }

    @Override
    public void pickEdgesList() {

    }

    @Override
    public void printGraph() {

    }

    @Override
    public void calcPathLength() {

    }

    @Override
    public void getPathAsEdges() {

    }

    @Override
    public void getPathAsNodes() {

    }

    @Override
    public void printPath() {

    }

    @Override
    public void pathToStr() {

    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyShowGraph() {
        for (Observer observer : observers) {
            observer.showGraph(nodes);
        }
    }
}
