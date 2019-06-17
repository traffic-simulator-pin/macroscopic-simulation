package br.udesc.pinii.macro.view;

import br.udesc.pinii.macro.control.SimulationController;
import br.udesc.pinii.macro.control.observer.Observer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ScoreFrame extends JFrame implements Observer {
    private SimulationController simulationController;
    private ItensTableModel itModel;
    private JTable jtItens;

    public ScoreFrame() {
        setTitle("Ranking de rotas");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        simulationController = SimulationController.getInstance();
        simulationController.addObserver(this);
        initializeComponents();
    }

    private void initializeComponents() {
        JPanel jp2 = new JPanel();
        jp2.setLayout(new BorderLayout());
        // criar a JTable
        jtItens = new JTable();
        itModel = new ItensTableModel();
        jtItens.setModel(itModel);
        jtItens.setDefaultRenderer(Number.class, new ItensTableRenderer());

        jp2.add(new JScrollPane(jtItens), BorderLayout.CENTER);
        add(jp2, BorderLayout.CENTER);
    }

    @Override
    public void refreshEdges() {
        itModel.fireTableDataChanged();
    }

    class ItensTableModel extends AbstractTableModel {

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 0) {
                return String.class;
            } else {
                return Number.class;
            }
        }

        @Override
        public int getRowCount() {
            return simulationController.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int colIndex) {

            switch (colIndex) {
                case 0:
                    return simulationController.getScoreNodes(rowIndex);
                default:
                    return simulationController.getScoreValue(rowIndex);
            }

        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Nodos";
                default:
                    return "Pontuação";
            }

        }

    }

    class ItensTableRenderer extends DefaultTableCellRenderer {

        public ItensTableRenderer() {
            setHorizontalAlignment(JLabel.RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int col) {

            JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            if (row == simulationController.size()) {
                l.setFont(new Font(l.getFont().getFontName(), Font.BOLD, l.getFont().getSize()));
            }
            return l;
        }
    }
}
