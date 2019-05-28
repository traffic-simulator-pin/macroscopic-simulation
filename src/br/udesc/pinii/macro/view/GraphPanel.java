package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.ControllerXML;
import br.udesc.pinii.macro.model.Edge;
import br.udesc.pinii.macro.model.Node;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphPanel extends JPanel {

    private Graph prefGraph;
    private br.udesc.pinii.macro.model.Graph appGraph;
    private Visualization visual;
    private int[] palette2;
    private int test = 0;

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
        LabelRenderer labelrenderer = new LabelRenderer("name");
        labelrenderer.setRoundedCorner(8, 8);
        visual.setRendererFactory(new DefaultRendererFactory(labelrenderer));

        int[] palette1 = new int[]{ColorLib.rgb(200, 200, 200)};
        DataColorAction fill = new DataColorAction("graph.nodes", "conj", Constants.NOMINAL, VisualItem.FILLCOLOR, palette1);
        ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR, ColorLib.gray(0));

//        palette2 = new int[]{ColorLib.rgb(200, 200, 200), ColorLib.rgb(0, 140, 0), ColorLib.rgb(220, 0, 0), ColorLib.rgb(0, 0, 200)};
        setColors();
        DataColorAction edges = new DataColorAction("graph.edges", "id", 0, VisualItem.STROKECOLOR, palette2);
        //ColorAction edges = new ColorAction("graph.edges", VisualItem.STROKECOLOR, ColorLib.gray(200));
        //ColorAction edges2 = new ColorAction("graph.edges", new  BooleanLiteral(false), VisualItem.STROKECOLOR, ColorLib.rgb(200, 0, 0));

        ActionList color = new ActionList();
        color.add(fill);
        color.add(text);
        color.add(edges);

        ActionList layout = new ActionList(Activity.INFINITY);
        layout.add(new ForceDirectedLayout("graph"));
        layout.add(new RepaintAction());

        visual.putAction("color", color);
        visual.putAction("layout", layout);

        //Container para adição no JFrame
        Display display = new Display(visual);
        display.setSize(1920, 1080);
        //Controles de movimento e zoom
        display.addControlListener(new DragControl());
        display.addControlListener(new PanControl());
        display.addControlListener(new ZoomControl());

        this.add(display);//VERIFICAR

        visual.run("color");
        visual.run("layout");

        ActionList repaint = new ActionList();
        repaint.add(color);
        visual.putAction("repaint", repaint);

    }

    public void setColors() {
        int size = appGraph.getEdges().size();
        palette2 = new int[size];
        System.out.println("size : " + size);
        for (int i = 0; i < size; i++) {
            palette2[i] = ColorLib.rgb(0, 0, 0);
        }
    }

    public void refreshEdges() {
        for (int i = 0; i < appGraph.getEdges().size(); i++) {
            if (appGraph.getEdges().get(i).getVehiclesCount() > 1500) {
                palette2[i] = ColorLib.rgb(255, 255 - (appGraph.getEdges().get(i).getVehiclesCount() / 58), 0);
            } else {
                palette2[i] = ColorLib.rgb(appGraph.getEdges().get(i).getVehiclesCount() / 2, 255, 0);
            }
        }
        //palette2[test] = ColorLib.rgb(200, 0, 0);
        //test++;
        visual.run("repaint");
    }

}
