package br.udesc.pinii.macro.view;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;

public class FrameSystem extends JFrame {

    private JDesktopPane desktopPane;
    private Container container;

    private JMenuBar menuBar;


    public FrameSystem() {
        super.setTitle("Simulação Macroscópica");
        super.setSize(new Dimension(900, 600));
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.initializeComponents();
        this.addComponents();
        this.addMenu();
    }

    private void initializeComponents() {
        this.desktopPane = new JDesktopPane();
        this.container = super.getContentPane();
    }

    private void addComponents() {
        this.container.add(desktopPane, CENTER);

    }

    private void addMenu(){
        menuBar = new MenuBar();
        super.setJMenuBar(menuBar);

    }

    private void addInternalFrame(JInternalFrame frame){
        desktopPane.add(frame);
    }

}
