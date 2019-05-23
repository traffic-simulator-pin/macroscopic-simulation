package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.SimulationController;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

public class FileChooser extends JFileChooser {

    private SimulationController simulationController;

    public FileChooser(SimulationController simulationController) throws FileNotFoundException {
        super.getFileSystemView().getHomeDirectory();
        this.simulationController = simulationController;

        int returnValue = super.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = super.getSelectedFile();
            this.simulationController.generateGraph(selectedFile);
        }
    }
}
