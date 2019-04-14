package br.udesc.pinii.macro.control.listeners;

import br.udesc.pinii.macro.view.FileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectFileListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Selecionar arquivos");
        FileChooser fileChooser = new FileChooser();
    }
}
