package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.MainController;
import br.udesc.pinii.macro.model.GraphAdjacencyList;
import br.udesc.pinii.macro.model.Node;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FileChooser extends JFileChooser {

    public FileChooser() throws FileNotFoundException {
        super.getFileSystemView().getHomeDirectory();

        int returnValue = super.showOpenDialog(null);
        List<String> file = new ArrayList<String>();

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = super.getSelectedFile();
            InputStream in = new FileInputStream(selectedFile);
            Scanner scan = new Scanner(in);

            while (scan.hasNext()) {
                file.add(scan.nextLine());
            }

        }
        //Isso vai sair
//        System.out.println("Tamanho:" + file.size());
//        HashMap<String, Double> edgeHash = new HashMap<String, Double>();
//        GraphAdjacencyList graph = new GraphAdjacencyList();
//
//        for (int i = 1; i < file.size(); i++) {//adicionar todos os vertices, lista para controle do que ja foi add  arqString.size()
//            String[] split = file.get(i).split(" "); //pegar a string na posição e dar split por tab
//            String chaveAresta = "";
//            chaveAresta = split[0] + split[1];
//            edgeHash.put(chaveAresta, Double.parseDouble(split[2]));
//            Node v = new Node(split[0]);
//            graph.addNode(v, split[0]);
//            Node v1 = new Node(split[1]);
//            graph.addNode(v1, split[1]);
//
//            Node v2 = graph.getNode(split[0]);//vertice da colun 1
//            v2.setDist(99999999);//dist infinita
//            Node v3 = graph.getNode(split[1]);//vertice da colun 2
//            v3.setDist(99999999);//distancia infinita 99999999
//            graph.addNode(v2, v3);//add adj
//        }
//        String source = "1";
//        GraphAdjacencyList minGraph = MainController.getInstance().dijkstra(graph, source, edgeHash);
//        String target = "12";
//        String target2 = target;
//        System.out.println("Vertice alvo: " + target);
//
//        System.out.println("Dist: " + minGraph.getNode(target2).getDist());


    }
}
