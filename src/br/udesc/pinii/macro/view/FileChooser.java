package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.SimulationController;
import br.udesc.pinii.macro.model.Edge;
import br.udesc.pinii.macro.model.Graph;
import br.udesc.pinii.macro.model.MSA;
import br.udesc.pinii.macro.model.Node;
import br.udesc.pinii.macro.util.Params;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileChooser extends JFileChooser {

    private SimulationController<br.udesc.pinii.macro.model.MSA<br.udesc.pinii.macro.model.MSA>> simulationController;

    public FileChooser(SimulationController simulationController) throws FileNotFoundException {
        super.getFileSystemView().getHomeDirectory();

        int returnValue = super.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = super.getSelectedFile();
//            this.simulationController.generateGraph(selectedFile);
        }
    }


    public static Graph processGraph(File netFile) {
        System.out.println("chegou aqui");
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
                E.put(e.getAttribute("name"), new Edge(
                        e.getAttribute("name"),
                        V.get(e.getAttribute("source")),
                        V.get(e.getAttribute("target")),
                        stringToFloat(e.getAttribute("capacity")) * Params.CAPACITY_FACTOR,
                        stringToFloat(e.getAttribute("length")),
                        isDirected("false"),
                        stringToFloat(e.getAttribute("speed")),
                        stringToFloat(e.getAttribute("constantA")),
                        stringToFloat(e.getAttribute("constantB"))
                ));
                E.put(e.getAttribute("target") + e.getAttribute("source"), new Edge(
                        e.getAttribute("target") + e.getAttribute("source"),
                        V.get(e.getAttribute("target")),
                        V.get(e.getAttribute("source")),
                        stringToFloat(e.getAttribute("capacity")) * Params.CAPACITY_FACTOR,
                        stringToFloat(e.getAttribute("length")),
                        isDirected("false"),
                        stringToFloat(e.getAttribute("speed")),
                        stringToFloat(e.getAttribute("constantA")),
                        stringToFloat(e.getAttribute("constantB"))
                ));
            }
        } catch (IOException | NumberFormatException | ParserConfigurationException | SAXException e) {
            System.err.println("Error on reading XML file!");
        }
        System.out.println("Sucesso ao gerar grafo");
        return new Graph(V, E);
    }

    public static <T> List<T> processODMatrix(Graph G, File odFile, float demand_factor, Class<br.udesc.pinii.macro.model.MSA> clazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<T> drivers = new ArrayList<>();
        System.out.println("Aguarde a criação da matriz od...");
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(odFile);

            NodeList list = doc.getElementsByTagName("od");

            int countD = 0;

            for (int i = 0; i < list.getLength(); i++) {
                Element e = (Element) list.item(i);
                Node origin = G.getNodes(e.getAttribute("source"));
                Node destination = G.getNodes(e.getAttribute("target"));
                System.out.print(origin + "-");
                System.out.println(destination);
                int size = (int) (Integer.parseInt(e.getAttribute("trips")) * demand_factor);
                for (int d = size; d > 0; d--) {
                    Object driver = clazz.getConstructor(clazz.getConstructors()[0].getParameterTypes()).newInstance(++countD, origin, destination, G);
//                    Object driver = new MSA(++countD, origin, destination, G);
                    drivers.add((T) driver);
                    drivers.add((T) driver);
                }
            }
            Params.DEMAND_SIZE = countD;

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
        return value.equalsIgnoreCase("true");
    }

}
