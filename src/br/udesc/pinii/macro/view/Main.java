package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.SimulationController;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        FrameSystem frameSystem = FrameSystem.getInstance();
        frameSystem.setVisible(true);
        frameSystem.setLocationRelativeTo(null);
        SimulationController simulationController = SimulationController.getInstance();
        simulationController.setSelectedFile(new File("files/base.xml"));

    }

}
