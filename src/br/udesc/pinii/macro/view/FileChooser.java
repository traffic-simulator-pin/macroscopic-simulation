package br.udesc.pinii.macro.view;

import javax.swing.*;
import java.io.File;

public class FileChooser extends JFileChooser {

    public FileChooser() {
        super.getFileSystemView().getHomeDirectory();

        int returnValue = super.showOpenDialog(null);

        if(returnValue == JFileChooser.APPROVE_OPTION){
            File selectedFile = super.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
        }
    }
}
