package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.SimulationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class MenuBar extends JMenuBar {

    private JMenu file;
    private JMenuItem newSimulation;
    private JMenuItem exit;

    private JMenu controls;
    private JMenuItem pause;
    private JMenuItem play;
    private JMenuItem stop;

    private JMenu tools;
    private JMenuItem options;

    private SimulationController simulationController;
    private FileChooser fileChooser;

    public MenuBar() {
        this.setPreferredSize(new Dimension(30, 30));
        this.initalizeComponets();
        this.addComponets();
        this.simulationController = SimulationController.getInstance();
    }

    private void initalizeComponets() {
        this.file = new JMenu("Arquivo");
        this.newSimulation = new JMenuItem("Nova Simulação");
        this.newSimulation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fileChooser = new FileChooser(simulationController);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.exit = new JMenuItem("Sair");

        this.controls = new JMenu("Controles");
        this.pause = new JMenuItem("Pausar");
        this.pause.setIcon(new ImageIcon("images/pause.png"));
        this.play = new JMenuItem("Continuar");
        this.play.setIcon(new ImageIcon("images/play.png"));
        this.stop = new JMenuItem("Parar");
        this.stop.setIcon(new ImageIcon("images/stop.png"));

        this.tools = new JMenu("Ferramentas");
        this.options = new JMenuItem("Opções");

    }

    private void addComponets() {
        this.file.add(newSimulation);
        this.file.add(exit);
        super.add(file);

        this.controls.add(pause);
        this.controls.add(play);
        this.controls.add(stop);
        super.add(controls);

        this.tools.add(options);
        super.add(tools);

    }
}
