package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.ISimulationController;
import br.udesc.pinii.macro.control.SimulationController;
import br.udesc.pinii.macro.control.observer.Observer;
import br.udesc.pinii.macro.model.Node;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class FrameSystem extends JFrame implements Observer {

    private Container container;

    private JMenuBar menuBar;
    private GraphPanel graphPanel;
    private ISimulationController simulationController;

    public FrameSystem() {
        setTitle("Simulação Macroscópica");
        setSize(new Dimension(900, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        simulationController = SimulationController.getInstance();
//        simulationController.addObserver(this);
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
                    doStep();
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
    public void showGraph(List<Node> nodeList) {
        graphPanel = new GraphPanel(nodeList);
        container.add(graphPanel, BorderLayout.CENTER);
        container.doLayout();
    }

    public void doStep() {
        graphPanel.doStep();
    }
}
