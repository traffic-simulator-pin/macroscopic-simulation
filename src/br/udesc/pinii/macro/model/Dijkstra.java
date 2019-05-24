package br.udesc.pinii.macro.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Dijkstra {

    public List<Edge> dijkstra(List<Node> vertices, Map<String, EdgeAux> edges, Node origin, Node destination) {
        return dijkstra(vertices, edges, origin, destination, null);
    }

    public List<Edge> dijkstra(List<Node> vertices, Map<String, EdgeAux> edges, Node origin, Node destination, List<Edge> ignored) {

        List<Node> Q = new ArrayList<>();
        Map<Node, VertexEntryDijkstra> V = new Hashtable<>();
        for (Node v : vertices) {
            V.put(v, new VertexEntryDijkstra(v));
        }

        V.get(origin).dist = 0;
        Q.add(origin);

        Node u = null;
        while (Q.size() > 0) {
            u = null;
            for (Node v : Q) {
                if (u == null) {
                    u = v;
                } else if (V.get(v).dist < V.get(u).dist) {
                    u = v;
                }
            }
            if (u == destination) {
                break;
            }
            Q.remove(u);
            V.get(u).visited = true;

            for (Edge e : u.getOutEdges()) {
                if (ignored != null && ignored.contains(e)) {
                    continue;
                }
                Node v = e.getTarget();

                float reward = (float) Math.abs(edges.get(e.toString()).getCost());

                float alt = V.get(u).dist + reward;
                if (alt < V.get(v).dist) {
                    V.get(v).dist = alt;
                    V.get(v).previous = u;
                    if (!V.get(v).visited) {
                        Q.add(v);
                    }
                }
            }

        }

        List<Edge> path = new ArrayList<>();
        u = destination;
        while (V.get(u).previous != null) {
            path.add(0, edges.get(V.get(u).previous.toString() + " -- " + u.toString()).getEdge());
            u = V.get(u).previous;
        }
        return path;
    }

    public class VertexEntryDijkstra {

        Node v;
        float dist = Float.MAX_VALUE;
        boolean visited = false;
        Node previous = null;

        public VertexEntryDijkstra(Node v) {
            this.v = v;
        }
    }


    public float calcCostPath(Map<String, EdgeAux> edges, List<Edge> path) {
        float c = 0;
        for (Edge e : path) {
            c += edges.get(e.toString()).getCost();
        }
        return c;
    }

}
