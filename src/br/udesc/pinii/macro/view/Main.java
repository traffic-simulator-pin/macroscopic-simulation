package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.SimulationController;
import br.udesc.pinii.macro.model.Graph;
import br.udesc.pinii.macro.model.MSA;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        FrameSystem frameSystem = new FrameSystem();
        frameSystem.setVisible(true);
        frameSystem.setLocationRelativeTo(null);
        Graph graph = FileChooser.processGraph(new File("files/base.xml"));
        try {
            List<MSA> drivers = FileChooser.processODMatrix(graph, new File("files/base.xml"), 1.0f, MSA.class);
            SimulationController simulationController = new SimulationController(graph, drivers);
            simulationController.start();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        System.out.println("Sucesso ao ler xml");
    }


}
