package br.udesc.pinii.macro.model;

import br.udesc.pinii.macro.control.SimulationController;

import java.util.*;
import java.util.concurrent.Callable;

public class OD implements Callable<OD> {

    private Node suorce;
    private Node target;
    private double flow;

    private int id;
    private Node currentNode;
    private Edge currentEdge;
    private Random random;
    private float time;

    private boolean is_step_a = true;

    private List<Node> nodes;

    private Route route;

    private List<RouteEntry> traveledRoute;

    public OD(Node suorce, Node target, double flow) {
        this.suorce = suorce;
        this.target = target;
        this.flow = flow;
        this.nodes = SimulationController.getInstance().getNodes();
    }

    public Edge getCurrentEdge() {
        return currentEdge;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void reset() {
        this.currentNode = suorce;
        this.currentEdge = null;
        this.traveledRoute = new LinkedList<>();
        this.is_step_a = true;
        this.time = 0;
    }

    public void resetAll() {

        Map<String, EdgeEntry> edgesMap;
        edgesMap = new Hashtable<>();

        for (Node node : this.nodes) {
            for (Edge edge : node.getEdges()) {
                edgesMap.put(edge.toString(), new EdgeEntry(edge, edge.msaCost()));
            }
        }
//        System.out.println("calculando "+this.suorce +"-"+ this.target);
//        List<Node> path = SimulationController.getInstance().dijkstra(this.suorce, this.target);
//        System.out.println(path.toString());
//        route = new Route(path, calcCostPath(edgesMap, path));
    }

    public float calcCostPath(Map<String, EdgeEntry> edges, List<Node> path) {
        float c = 0;
        for (Node node : path) {
            for (Edge edge : node.getEdges()) {
                c += edges.get(edge.toString()).getCost();
            }
        }
        return c;
    }


    public void beforeEpisode() {
        this.resetAll();
    }


    public boolean hasArrived() {
        if (traveledRoute.isEmpty()) {
            return false;
        }
        return traveledRoute.get(traveledRoute.size() - 1).getEdge().getTarget() == target;
    }

    public boolean mustBeProcessed() {
        return true;
    }

    @Override
    public OD call() throws Exception {
        if (this.is_step_a) {
            this.step_A();
            this.is_step_a = false;
        } else {
            this.step_B();
            this.is_step_a = true;
        }
        return this;
    }

    private void step_A() {
        currentNode = route.getPath().get(SimulationController.getInstance().getStep() - 1);
    }

    private void step_B() {
        traveledRoute.add(new RouteEntry(currentEdge, currentEdge.msaCost()));
        this.time += currentEdge.msaCost();
    }
}
