package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.SimulationController;

import java.io.File;

public class Main implements Runnable {

    public static void main(String[] args) {

        Thread thread = new Thread(new Main());
        thread.start();


//        SimulationController simulationController = SimulationController.getInstance();
//        simulationController.setSelectedFile(new File("files/base.xml"));
//        simulationController.start();

    }

    @Override
    public void run() {
        FrameSystem frameSystem = FrameSystem.getInstance();
        frameSystem.setLocationRelativeTo(null);
        frameSystem.setVisible(true);
    }
}
