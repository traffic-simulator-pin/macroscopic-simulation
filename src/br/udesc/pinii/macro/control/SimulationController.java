package br.udesc.pinii.macro.control;

import br.udesc.pinii.macro.control.observer.Observer;
import br.udesc.pinii.macro.model.Edge;
import br.udesc.pinii.macro.model.Graph;
import br.udesc.pinii.macro.model.MSA;
import br.udesc.pinii.macro.model.Node;
import br.udesc.pinii.macro.util.Params;
import br.udesc.pinii.macro.view.FrameSystem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimulationController<T extends MSA> extends Thread implements ISimulationController {

    private List<Observer> observers;
    private Graph graph;
    private List<T> drivers;
    private final ExecutorService eservice = Executors.newFixedThreadPool(Params.CORES);
    private final CompletionService<Object> cservice = new ExecutorCompletionService<>(eservice);
    private File selectedFile;
    private static SimulationController instance;

    private HashMap<String, Edge> newEdges;

    public static SimulationController getInstance() {
        if (instance == null)
            instance = new SimulationController();

        return instance;
    }

    private SimulationController() {
        this.observers = new ArrayList<>();
        this.newEdges = new HashMap<>();
    }


    @Override
    public void setSelectedFile(File file) {
        this.selectedFile = file;
        this.graph = processGraph(file);
        try {
            this.drivers = processODMatrix(graph, file, 1.0f, MSA.class);
            FrameSystem.getInstance().initNewGraph(graph);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        super.run();
        this.startMSA();
    }

    @Override
    public void startMSA() {
        System.out.println("iniciou");
        this.reset();
        Params.EG_EPSILON = Params.EG_EPSILON_DEFAULT;

        while (Params.EPISODE < 150) {
            this.runSteps();
            Params.EG_EPSILON = Params.EG_EPSILON * Params.EG_DECAYRATE;
        }

        for (MSA d : this.drivers) {
            d.printRoute();
        }
        System.out.println("Finalizando...");
        this.eservice.shutdown();
    }

    @Override
    public boolean runSteps() {
        Params.EPISODE++;
        Params.STEP = 0;

        Params.PHI_MSA = 1.0f / Params.EPISODE;

        for (MSA d : this.drivers) {
            d.reset();
            d.beforeEpisode();
        }
        while (Params.STEP < 100) {
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

    @Override
    public boolean step() {
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

    @Override
    public Graph processGraph(File netFile) {
        HashMap<String, Node> V = null;
        HashMap<String, Edge> E = null;

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(netFile);

            NodeList list = doc.getElementsByTagName("node");
            V = new HashMap<>(list.getLength());
            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                V.put(e.getAttribute("id"), new Node(e.getAttribute("id")));
            }

            list = doc.getElementsByTagName("edge");
            E = new HashMap<>(list.getLength());
            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                boolean show = isDirected(e.getAttribute("oneway"));
                E.put(e.getAttribute("name"), new Edge(
                        e.getAttribute("name"),
                        V.get(e.getAttribute("source")),
                        V.get(e.getAttribute("target")),
                        stringToFloat(e.getAttribute("capacity")) * 1.0f,
                        stringToFloat(e.getAttribute("length")),
                        isDirected(e.getAttribute("oneway")),
                        stringToFloat(e.getAttribute("speed")),
                        stringToFloat(e.getAttribute("constantA")),
                        stringToFloat(e.getAttribute("constantB")),
                        show
                ));
                E.put(e.getAttribute("target") + e.getAttribute("source"), new Edge(
                        e.getAttribute("target") + "-" + e.getAttribute("source"),
                        V.get(e.getAttribute("target")),
                        V.get(e.getAttribute("source")),
                        stringToFloat(e.getAttribute("capacity")) * 1.0f,
                        stringToFloat(e.getAttribute("length")),
                        isDirected("false"),
                        stringToFloat(e.getAttribute("speed")),
                        stringToFloat(e.getAttribute("constantA")),
                        stringToFloat(e.getAttribute("constantB")),
                        !show
                ));
            }

        } catch (IOException | NumberFormatException | ParserConfigurationException | SAXException e) {
            System.err.println("Error on reading XML file!");
        }
        return new Graph(V, E);
    }

    @Override
    public <T> List<T> processODMatrix(Graph G, File odFile, float demand_factor, Class<br.udesc.pinii.macro.model.MSA> clazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<T> drivers = new ArrayList<>();
        System.out.println("Aguarde a criação da matriz od...");
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(odFile);

            NodeList list = doc.getElementsByTagName("conection");

            int countD = 0;

            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                Node origin = G.getNodes(e.getAttribute("source"));
                Node destination = G.getNodes(e.getAttribute("target"));
                System.out.print(origin + "-");
                System.out.println(destination);
                int size = (int) (Integer.parseInt(e.getAttribute("flow")) * demand_factor);
                for (int d = size; d > 0; d--) {
                    Object driver = clazz.getConstructor(clazz.getConstructors()[0].getParameterTypes()).newInstance(++countD, origin, destination, G);
                    drivers.add((T) driver);
                    drivers.add((T) driver);
                }
            }
            Params.DEMAND_SIZE = countD;
            for (Edge edge : graph.getEdges()) {
                if (edge.isShow()) {
                    newEdges.put(edge.getId(), edge);
                }
            }
            System.out.println("sucesso ao gerar matriz OD");
        } catch (IOException | NumberFormatException | ParserConfigurationException | SAXException e) {
            System.err.println("Error on reading XML file!");
        }
        return drivers;
    }

    private static float stringToFloat(String value) {
        if (value.equals("")) {
            return 0f;
        } else {
            return Float.parseFloat(value);
        }
    }

    private static boolean isDirected(String value) {
        return value.equalsIgnoreCase(value);
    }

    @Override
    public void printLinksFlow() {
        List<Edge> list = this.graph.getEdges();
        Collections.sort(list);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyRefreshEdges();
    }


    @Override
    public void reset() {
        Params.EG_EPSILON = Params.EG_EPSILON_DEFAULT;
        Params.STEP = 0;
        Params.EPISODE = 0;
    }


    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyRefreshEdges() {
        for (Observer observer : observers) {
            observer.refreshEdges();
        }
    }

    @Override
    public int size() {
        return newEdges.size();
    }

    @Override
    public String getNodos(int rowIndex) {
        return graph.getEdges().get(rowIndex).getId();
    }

    @Override
    public int getVehicles(int rowIndex) {
        return graph.getEdges().get(rowIndex).getVehiclesCount();
    }

    @Override
    public float getCapacity(int rowIndex) {
        return graph.getEdges().get(rowIndex).getCapacity();
    }

    @Override
    public float getType(int rowIndex) {
        return graph.getEdges().get(rowIndex).getAcumulatedCost();
    }
}
