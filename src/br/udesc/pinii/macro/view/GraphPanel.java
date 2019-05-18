package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.model.Edge;
import br.udesc.pinii.macro.model.Node;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphPanel extends JFrame {


    private List<Node> nodeList;

    public GraphPanel(List<Node> nodeList) {
        this.nodeList = nodeList;
        super.setSize(new Dimension(800, 600));
        super.setBackground(Color.black);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        for (int i = 0; i < nodeList.size(); i++) {
            int x = 10 + (nodeList.get(i).getX() * 80);
            int y = (35 + (nodeList.get(i).getY() * 40));
            g.setColor(Color.BLACK);
            g.drawString(nodeList.get(i).getName(), x, y);
            g.fillOval(x, y, 10, 10);
            for (int j = 0; j < nodeList.get(i).getEdges().size(); j++) {
                g.setColor(Color.BLACK);
                g.drawLine(10 + (nodeList.get(i).getEdges().get(j).getSource().getX() * 80), 35 + nodeList.get(i).getEdges().get(j).getSource().getY() * 40, 10 + nodeList.get(i).getEdges().get(j).getTarget().getX() * 80, 35 + nodeList.get(i).getEdges().get(j).getTarget().getY() * 40);
            }
        }
    }


}
