package br.udesc.pinii.macro.model;

import br.udesc.pinii.macro.util.Params;

import java.util.*;
import java.util.concurrent.Callable;

public class MSA<T extends MSA> implements Callable<MSA> {

    protected int id;
    protected Node source;
    protected final Node target;
    protected Graph graph;
    protected Node current_intersection;
    protected Edge current_road;
    protected List<EdgeAux> traveledRoute;
    protected Random random;
    private boolean is_step_a = true;
    protected float travelTime;


    public MSA(int id, Node source, Node target, Graph graph) {
        if (source == target) {
            System.err.println("Erro ao rodar msa");
        }

        this.id = id;
        this.source = source;
        this.target = target;
        this.graph = graph;
        random = new Random(Params.RANDOM.nextLong());
        this.resetAll();
    }

    private Route route;

    public void resetAll() {
        this.reset();
        Dijkstra dijkstra = new Dijkstra();

        Map<String, EdgeAux> edgesMap;
        edgesMap = new Hashtable<>();
        for (Edge e : this.graph.getEdges()) {
            edgesMap.put(e.toString(), new EdgeAux(e, e.msaCost()));
        }

        List<Edge> path = dijkstra.dijkstra(this.graph.getNodes(), edgesMap, this.source, this.target);
        route = new Route(path, dijkstra.calcCostPath(edgesMap, path));
    }

    public void reset() {
        this.current_intersection = source;
        this.current_road = null;
        this.is_step_a = true;
        this.traveledRoute = new LinkedList<>();
        this.travelTime = 0;
        this.travelTime = 0;
    }

    public void step_A() {
        current_road = route.getPath().get(Params.STEP - 1);
        current_intersection = current_road.getTarget();
    }

    public void step_B() {
        this.traveledRoute.add(new EdgeAux(current_road, current_road.msaCost()));
        this.travelTime += current_road.msaCost();

        if (hasArrived()) {
            this.current_road = null;
        }
    }

    public void beforeEpisode() {
        this.resetAll();
    }

    public void afterEpisode() {
    }

    public float getTravelTime() {
        float cost = 0f;
        for (Object entry : this.traveledRoute) {
            cost += ((EdgeAux) entry).getEdge().msaCost();
        }
        return cost;
    }

        public void printRoute() {
        if (traveledRoute.size() < 1) {
            System.out.println(">>>>>>>>>>>>> " + this.toString() + " ---- " + this.getTravelTime());
        }
        String str = this.toString() + ": " + traveledRoute.get(0).getEdge().getSource();
        float sum = 0f;
        for (EdgeAux e : traveledRoute) {
            str += " > " + e.getEdge().getTarget();
            sum += e.getCost();
        }
        str += " (total=" + sum + ")";
        System.out.println(str);
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

    public Edge getCurrent_road() {
        return current_road;
    }


    @Override
    public MSA call() throws Exception {
        if (this.is_step_a) {
            this.step_A();
            this.is_step_a = false;
        } else {
            this.step_B();
            this.is_step_a = true;
        }
        return this;
    }
}
