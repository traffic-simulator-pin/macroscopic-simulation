package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.ISimulationController;
import br.udesc.pinii.macro.control.SimulationController;
import br.udesc.pinii.macro.control.observer.Observer;
import br.udesc.pinii.macro.model.Node;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static javax.swing.SwingConstants.CENTER;

public class FrameSystem extends JFrame implements Observer {

    private JDesktopPane desktopPane;
    private Container container;

    private JMenuBar menuBar;
    private GraphPanel graphPanel;
    private ISimulationController simulationController;


    public FrameSystem() {
        super.setTitle("Simulação Macroscópica");
        super.setSize(new Dimension(900, 600));
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.simulationController = SimulationController.getInstance();
        this.simulationController.addObserver(this);
        this.initializeComponents();
        this.addComponents();
        this.addMenu();
    }

    private void initializeComponents() {
        this.desktopPane = new JDesktopPane();
        this.container = super.getContentPane();
    }

    private void addComponents() {
//        this.container.add(desktopPane, CENTER);
        setContentPane(desktopPane);

    }

    private void addMenu() {
        menuBar = new MenuBar();
        super.setJMenuBar(menuBar);

    }

    private void addInternalFrame(JInternalFrame frame) {
        desktopPane.add(frame);
    }

    @Override
    public void showGraph(List<Node> nodeList) {
        this.graphPanel = new GraphPanel(nodeList);
        this.graphPanel.setVisible(true);
    }
}
