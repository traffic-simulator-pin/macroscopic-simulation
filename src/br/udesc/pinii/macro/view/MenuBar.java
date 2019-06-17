package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.SimulationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class MenuBar extends JMenuBar {

    private final FrameSystem frame;

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
//    private FileChooser fileChooser;

    public MenuBar(FrameSystem frame) {
        this.frame = frame;
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
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setVisible(true);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.exit = new JMenuItem("Sair");
        this.exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        this.controls = new JMenu("Controles");

        this.play = new JMenuItem("Executar");
        this.play.setIcon(new ImageIcon("images/play.png"));
        this.play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doPlay();

            }
        });


        this.tools = new JMenu("Opções");
        this.options = new JMenuItem("Sobre");

    }

    private void addComponets() {
        this.file.add(newSimulation);
        this.file.add(exit);
        super.add(file);

        this.controls.add(play);
        super.add(controls);

        this.tools.add(options);
        super.add(tools);

    }

    private void doPlay() {
        simulationController.start();
        StatisticFrame statisticFrame = new StatisticFrame();
        ScoreFrame scoreFrame = new ScoreFrame();
        scoreFrame.setVisible(true);
        statisticFrame.setVisible(true);
    }
}
