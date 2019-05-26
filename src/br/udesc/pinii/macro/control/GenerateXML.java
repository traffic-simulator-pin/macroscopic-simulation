package br.udesc.pinii.macro.control;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateXML {

    private static StringBuffer arquivo;

    public static void iniciarArquivo() {
        arquivo = new StringBuffer(""
                + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">\n"
                + "<graph edgedefault=\"undirected\">\n"
                + "<!-- data schema -->\n"
                + "<key id=\"name\" for=\"node\" attr.name=\"name\" attr.type=\"string\"/>\n"
                + "<key id=\"conj\" for=\"node\" attr.name=\"conj\" attr.type=\"string\"/>\n"
                + "<key id=\"id\" for=\"edge\" attr.name=\"id\" attr.type=\"string\"/>\n\n<!-- nodes -->  ");
    }

    public static void gerarNodo(long userId, String name, String conj) {
        arquivo.append("\n<node id=\"").append(userId).append("\"> ").
                append("<data key=\"name\">").append(name).
                append("</data> <data key=\"conj\">").append(conj).
                append("</data> </node>");
    }

    public static void gerarAresta(String id, String idSource, String idTarget) {
        arquivo.append("\n<edge source=\"").append(idSource).append("\" target=\"").append(idTarget).append("\"> ").
                append("<data key=\"id\">").append(id).
                append("</data> </edge> ");
    }

    public static void fecharArquivo() {
        arquivo.append("\n\n</graph>\n</graphml>");
    }

    public static void salvarXML(String path) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(arquivo.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao salvar arquivo...Saindo");
            System.exit(0);
        }
    }
}
