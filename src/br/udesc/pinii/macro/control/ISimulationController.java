package br.udesc.pinii.macro.control;

import br.udesc.pinii.macro.control.observer.Observer;
import br.udesc.pinii.macro.model.Graph;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ISimulationController {

    void addObserver(Observer observer);

    void notifyRefreshEdges();

    void setSelectedFile(File file);

    void startMSA();

    boolean runSteps();

    boolean step();

    Graph processGraph(File netFile);

    <T> List<T> processODMatrix(Graph G, File odFile, float demand_factor, Class<br.udesc.pinii.macro.model.MSA> clazz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    void printLinksFlow();

    void reset();

    int size();

    String getNodos(int rowIndex);

    int getVehicles(int rowIndex);

    float getCapacity(int rowIndex);

    float getType(int rowIndex);

}
