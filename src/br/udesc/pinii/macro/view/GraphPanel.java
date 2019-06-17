package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.ControllerXML;
import br.udesc.pinii.macro.control.SimulationController;
import br.udesc.pinii.macro.model.Edge;
import br.udesc.pinii.macro.model.Node;
import br.udesc.pinii.macro.model.StatisticEdge;
import impl_pronta.*;
import impl_pronta.action.ActionList;
import impl_pronta.action.RepaintAction;
import impl_pronta.action.assignment.ColorAction;
import impl_pronta.action.assignment.DataColorAction;
import impl_pronta.action.layout.graph.ForceDirectedLayout;
import impl_pronta.activity.Activity;
import impl_pronta.controls.DragControl;
import impl_pronta.controls.PanControl;
import impl_pronta.controls.ZoomControl;
import impl_pronta.data.Graph;
import impl_pronta.data.io.DataIOException;
import impl_pronta.data.io.GraphMLReader;
import impl_pronta.render.DefaultRendererFactory;
import impl_pronta.render.LabelRenderer;
import impl_pronta.util.ColorLib;
import impl_pronta.visual.VisualItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphPanel extends JPanel {

    private Graph prefGraph;
    private br.udesc.pinii.macro.model.Graph appGraph;
    private Visualization visual;
    private int[] palette2;
    private SimulationController simulationController;

    public GraphPanel(br.udesc.pinii.macro.model.Graph appGraph) {
        this.appGraph = appGraph;
        ControllerXML c = new ControllerXML(appGraph);

        c.gerarXML("grafo.xml");

        prefGraph = null;
        try {
            prefGraph = new GraphMLReader().readGraph("grafo.xml");
        } catch (DataIOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        visual = new Visualization();
        visual.add("graph", prefGraph);
        simulationController = SimulationController.getInstance();
        LabelRenderer labelrenderer = new LabelRenderer("name");
        labelrenderer.setRoundedCorner(8, 8);
        visual.setRendererFactory(new DefaultRendererFactory(labelrenderer));

        int[] palette1 = new int[]{ColorLib.rgb(200, 200, 200)};
        DataColorAction fill = new DataColorAction("graph.nodes", "conj", Constants.NOMINAL, VisualItem.FILLCOLOR, palette1);
        ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));

        setColors();
        DataColorAction edges = new DataColorAction("graph.edges", "id", 0, VisualItem.STROKECOLOR, palette2);

        ActionList color = new ActionList();
        color.add(fill);
        color.add(text);
        color.add(edges);

        ActionList layout = new ActionList(Activity.INFINITY);
        layout.add(new ForceDirectedLayout("graph"));
        layout.add(new RepaintAction());

        visual.putAction("color", color);
        visual.putAction("layout", layout);

        Display display = new Display(visual);
        display.setSize(1920, 1080);
        display.addControlListener(new DragControl());
        display.addControlListener(new PanControl());
        display.addControlListener(new ZoomControl());

        this.add(display);

        visual.run("color");
        visual.run("layout");

        ActionList repaint = new ActionList();
        repaint.add(color);
        visual.putAction("repaint", repaint);

    }

    public void setColors() {
        int size = appGraph.getEdges().size();
        palette2 = new int[size];
        for (int i = 0; i < size; i++) {
            palette2[i] = ColorLib.rgb(0, 0, 0);
        }
    }

    public void refreshEdges() {
        for (int i = 0; i < appGraph.getEdges().size(); i++) {
            double roadSize = appGraph.getEdges().get(i).getCapacity();
            if (appGraph.getEdges().get(i).getVehiclesCount() * 100 / roadSize > 100) {
                simulationController.updateScore(i, 4);
                palette2[i] = ColorLib.rgb(255, 0, 0);
            } else if (appGraph.getEdges().get(i).getVehiclesCount() * 100 / roadSize > 80) {
                simulationController.updateScore(i, 3);
                palette2[i] = ColorLib.rgb(254, 111, 0);
            } else if (appGraph.getEdges().get(i).getVehiclesCount() * 100 / roadSize > 50) {
                simulationController.updateScore(i, 2);
                palette2[i] = ColorLib.rgb(254, 212, 0);
            } else if (appGraph.getEdges().get(i).getVehiclesCount() * 100 / roadSize > 20) {
                simulationController.updateScore(i, 1);
                palette2[i] = ColorLib.rgb(76, 255, 0);
            } else if (appGraph.getEdges().get(i).getVehiclesCount() == 0) {
                simulationController.updateScore(i, 0);
                palette2[i] = ColorLib.rgb(0, 0, 0);
            }
        }
        visual.run("repaint");
    }

}