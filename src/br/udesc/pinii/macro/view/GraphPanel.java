package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.model.Edge;
import br.udesc.pinii.macro.model.Node;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraphPanel extends JFrame {

    private int SIZE = 10;
    private Node point1 = null, point2 = null;

    private List<Node> nodeList;

    public GraphPanel(List<Node> nodeList) {
        this.nodeList = nodeList;
        super.setSize(new Dimension(400, 300));
        super.setBackground(Color.black);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        System.out.println("Chegou aqui");
        super.paint(g);
        g.setColor(Color.green);
        if (point1 != null && point2 != null) {
            g.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
            g.fillOval(point2.getX() - 3, point2.getY() - 3, 6, 6);
        }
        System.out.println("Node list size: " + nodeList.size());
        for (int i = 0; i < nodeList.size(); i++) {
            g.setColor(Color.green);
            g.fillOval(nodeList.get(i).getX() + SIZE, nodeList.get(i).getY() + SIZE, SIZE * 2, SIZE * 2);
            g.setColor(Color.black);
            //g.drawString(String.valueOf(i), nodeList.get(i).getX() - SIZE / 2, nodeList.get(i).getY() + SIZE / 2);
            for (int j = 0; j < nodeList.get(i).getEdges().size(); j++) {
                g.setColor(Color.green);
                g.drawLine(nodeList.get(i).getEdges().get(j).getSource().getX(), nodeList.get(i).getEdges().get(j).getSource().getY(), nodeList.get(i).getEdges().get(j).getTarget().getX(), nodeList.get(i).getEdges().get(j).getTarget().getY());
                g.fillOval(nodeList.get(i).getEdges().get(j).getTarget().getX() - 3, nodeList.get(i).getEdges().get(j).getTarget().getY() - 3, 6, 6);
            }
        }
    }


}
