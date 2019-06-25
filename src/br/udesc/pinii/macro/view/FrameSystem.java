package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.ISimulationController;
import br.udesc.pinii.macro.control.SimulationController;
import br.udesc.pinii.macro.control.observer.Observer;
import br.udesc.pinii.macro.model.Graph;
import br.udesc.pinii.macro.model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FrameSystem extends JFrame implements Observer {

    private Container container;

    private JMenuBar menuBar;
    private GraphPanel graphPanel;
    private SimulationController simulationController;

    private static FrameSystem instance;

    public static FrameSystem getInstance() {
        if (instance == null)
            instance = new FrameSystem();

        return instance;
    }


    private FrameSystem() {
        setTitle("Simulação Macroscópica");
        setSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        simulationController = SimulationController.getInstance();
        simulationController.addObserver(this);
        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        container = super.getContentPane();
        container.setLayout(new GridLayout(1, 1));
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 117) {
                    refreshEdges();
                }
            }
        });
    }

    private void addComponents() {
        setContentPane(container);
        addMenu();
    }

    private void addMenu() {
        menuBar = new MenuBar(this);
        setJMenuBar(menuBar);
    }

    @Override
    public void refreshEdges() {
        graphPanel.refreshEdges();
    }

    public void initNewGraph(Graph graph) {
        graphPanel = new GraphPanel(graph);
        container.add(graphPanel);
        container.doLayout();
    }

}
