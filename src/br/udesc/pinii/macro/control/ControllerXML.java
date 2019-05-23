package br.udesc.pinii.macro.control;

import br.udesc.pinii.macro.model.Edge;
import br.udesc.pinii.macro.model.Node;
import java.util.List;

/**
 *
 * @author José Carlos
 */
public class ControllerXML {

    private List<Node> nodes;

    public ControllerXML(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void gerarXML(String nomeArquivo) {
        GenerateXML.iniciarArquivo();
        for (int i = 1; i <= nodes.size(); i++) {
            GenerateXML.gerarNodo(i, "" + nodes.get(i - 1).getName(), "A");
        }
        int id = 0;
        for (int i = 1; i <= nodes.size(); i++) {
            List<Edge> arestas = nodes.get(i - 1).getEdges();
            for (int j = 1; j <= arestas.size(); j++) {
                GenerateXML.gerarAresta(id, Integer.parseInt(arestas.get(j - 1).getSource().getName()), Integer.parseInt(arestas.get(j - 1).getTarget().getName()));
                id++;
            }
        }
        GenerateXML.fecharArquivo();
        GenerateXML.salvarXML(nomeArquivo);
    }
}
