package br.udesc.pinii.macro.view;

import javax.swing.*;
import java.awt.*;

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

    public MenuBar() {
        this.setPreferredSize(new Dimension(30, 30));
        this.initalizeComponets();
        this.addComponets();
    }

    private void initalizeComponets() {
        this.file = new JMenu("Arquivo");
        this.newSimulation = new JMenuItem("Nova Simulação");
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
