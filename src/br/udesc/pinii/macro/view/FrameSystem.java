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
import java.util.List;

public class FrameSystem extends JFrame implements Observer{

    private Container container;

    private JMenuBar menuBar;
    private GraphPanel graphPanel;
    private ISimulationController simulationController;

    public FrameSystem() {
        setTitle("Simulação Macroscópica");
        setSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        addComponents();
    }

    private void initializeComponents() {
        container = super.getContentPane();
        container.setLayout(new BorderLayout());
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

    public void initGraph(Graph graph, SimulationController simulationController) {
        this.simulationController = simulationController;
        simulationController.addObserver(this);
        graphPanel = new GraphPanel(graph);
        container.add(graphPanel, BorderLayout.CENTER);
        container.doLayout();
    }

    @Override
    public void refreshEdges() {
        graphPanel.refreshEdges();
    }
    
}
