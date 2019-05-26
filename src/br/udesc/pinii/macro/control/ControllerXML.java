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
        for (int i = 1; i <= graph.getNodes().size(); i++) {
            GenerateXML.gerarNodo(Integer.parseInt(graph.getNodes().get(i - 1).toString()), "" + graph.getNodes().get(i - 1).toString(), "A");
        }
        int id = 0;
        for (int i = 1; i <= graph.getNodes().size(); i++) {
            List<Edge> arestas = graph.getNodes().get(i - 1).getOutEdges();
            for (int j = 1; j <= arestas.size(); j++) {
                GenerateXML.gerarAresta(id, Integer.parseInt(arestas.get(j - 1).getSource().toString()), Integer.parseInt(arestas.get(j - 1).getTarget().toString()));
                id++;
            }
        }
        GenerateXML.fecharArquivo();
        GenerateXML.salvarXML(nomeArquivo);
    }
}
