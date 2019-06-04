package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.SimulationController;
import br.udesc.pinii.macro.model.Graph;
import br.udesc.pinii.macro.model.MSA;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class FileChooser extends JFileChooser {

    private SimulationController simulationController;

    public FileChooser() throws FileNotFoundException {
        super.getFileSystemView().getHomeDirectory();

        simulationController = SimulationController.getInstance();

        int returnValue = super.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = super.getSelectedFile();
            simulationController.setSelectedFile(selectedFile);
        }
    }

}
