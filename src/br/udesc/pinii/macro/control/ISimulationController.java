package br.udesc.pinii.macro.control;

import br.udesc.pinii.macro.control.observer.Observer;


public interface ISimulationController {


    void addObserver(Observer observer);

    void notifyRefreshEdges();

}
