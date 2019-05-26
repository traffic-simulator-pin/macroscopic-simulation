package br.udesc.pinii.macro.control;

import br.udesc.pinii.macro.model.Edge;
import br.udesc.pinii.macro.model.Graph;
import br.udesc.pinii.macro.model.Node;
import java.util.List;

/**
 *
 * @author José Carlos
 */
public class ControllerXML {

    private Graph graph;

    public ControllerXML(Graph graph) {
        this.graph = graph;
    }

    public void gerarXML(String nomeArquivo) {
        GenerateXML.iniciarArquivo();
        for (int i = 0; i < graph.getNodes().size(); i++) {
            GenerateXML.gerarNodo(Integer.parseInt(graph.getNodes().get(i).toString()), graph.getNodes().get(i).toString(), "A");
        }
        for (int i = 0; i < graph.getEdges().size(); i++) {
            GenerateXML.gerarAresta(graph.getEdges().get(i).getId(), graph.getEdges().get(i).getSource().toString(), graph.getEdges().get(i).getTarget().toString());
        }
        GenerateXML.fecharArquivo();
        GenerateXML.salvarXML(nomeArquivo);
    }
}
