package br.udesc.pinii.macro.control;

import br.udesc.pinii.macro.control.observer.Observer;
import br.udesc.pinii.macro.model.Graph;
import br.udesc.pinii.macro.model.Node;

import java.io.File;
import java.util.List;

public interface ISimulationController {


    void addObserver(Observer observer);

    void notifyRefreshEdges();

}
