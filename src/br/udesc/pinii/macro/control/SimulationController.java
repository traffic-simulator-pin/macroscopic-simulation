package br.udesc.pinii.macro.control;

import br.udesc.pinii.macro.control.observer.Observer;
import br.udesc.pinii.macro.model.Edge;
import br.udesc.pinii.macro.model.Graph;
import br.udesc.pinii.macro.model.MSA;
import br.udesc.pinii.macro.util.Params;

import java.util.*;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulationController<T extends MSA> implements ISimulationController {

    private List<Observer> observers;
    private final Graph graph;
    private final List<T> drivers;
    private final ExecutorService eservice = Executors.newFixedThreadPool(Params.CORES);
    private final CompletionService<Object> cservice = new ExecutorCompletionService<>(eservice);

    public SimulationController(Graph graph, List<T> drivers) {
        this.observers = new ArrayList<>();
        this.graph = graph;
        this.drivers = drivers;
    }

    public void start() {
        System.out.println("iniciou");
        this.reset();

        Params.EG_EPSILON = Params.EG_EPSILON_DEFAULT;

        while (Params.EPISODE < Params.NUM_EPISODES) {
            this.runEpisode();
            Params.EG_EPSILON = Params.EG_EPSILON * Params.EG_DECAYRATE;
        }

        for (MSA d : this.drivers) {
            d.printRoute();
        }
        System.out.println("Finalizando...");
        this.eservice.shutdown();
    }

    private boolean runEpisode() {
        Params.EPISODE++;
        Params.STEP = 0;

        Params.PHI_MSA = 1.0f / Params.EPISODE;

        for (MSA d : this.drivers) {
            d.reset();
            d.beforeEpisode();
        }
        while (Params.STEP < Params.NUM_STEPS) {
            printLinksFlow();
            if (!this.step()) {
                break;
            }
        }

        for (Edge e : this.graph.getEdges()) {
            e.afterEpisode();
        }

        for (MSA d : this.drivers) {
            d.afterEpisode();
        }
        return true;
    }

    private boolean step() {
        boolean finished = true;
        List<MSA> driversToProcess = new LinkedList<>();

        for (T d : this.drivers) {
            if (!d.hasArrived() && d.mustBeProcessed()) {
                finished = false;
                driversToProcess.add(d);
            }
        }
        if (finished) {
            return false;
        }

        Params.STEP++;

        for (MSA driver : driversToProcess) {
            this.cservice.submit(driver);
        }

        for (MSA msa : driversToProcess) {
            try {
                boolean result = this.cservice.take().isDone();
                if (!result) {
                    System.out.println("step A error");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (Edge e : this.graph.getEdges()) {
            e.clearVehiclesHere();
            if (Params.STEP == 1) {
                e.clearTotalFlow();
            }
        }
        for (MSA d : driversToProcess) {
            if (!d.hasArrived()) {
                d.getCurrent_road().incVehiclesHere();
            }

        }

        for (Edge e : this.graph.getEdges()) {
            e.updateCost();
        }
        printLinksFlow();

        for (MSA driver : driversToProcess) {
            this.cservice.submit(driver);
        }

        for (MSA msa : driversToProcess) {
            try {
                boolean result = this.cservice.take().isDone();
                if (!result) {
                    System.out.println("step_b error");
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public void printLinksFlow() {
        List<Edge> list = this.graph.getEdges();
        Collections.sort(list);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=================Camada================");
        for (Edge e : list) {
            System.out.print(e.getSource() + "-" + e.getTarget());
            System.out.print(" quantidade: ");
            System.out.println(e.getVehiclesCount());
        }
    }


    private void reset() {
        Params.EG_EPSILON = Params.EG_EPSILON_DEFAULT;
        Params.STEP = 0;
        Params.EPISODE = 0;
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyShowGraph() {
        for (Observer observer : observers) {
//            observer.showGraph(nodes);
        }
    }
}
