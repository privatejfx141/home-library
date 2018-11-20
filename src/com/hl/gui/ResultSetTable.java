package com.hl.gui;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class ResultSetTable {

    public static void generateTableDialog(ResultSet results, String title) throws SQLException {
        generateTableDialog(buildTableModel(results), title);
    }

    public static void generateTableDialog(ArrayList<Object[]> rawData, List<String> header, String title)
            throws SQLException {
        generateTableDialog(buildTableModel(rawData, header), title);
    }

    private static void generateTableDialog(DefaultTableModel model, String title) {
        JTable table = new JTable(model) {
            private static final long serialVersionUID = 1185868188864055488L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        UIManager.put("OptionPane.minimumSize", new Dimension(table.getColumnCount() * 160, 100));
        JOptionPane.showMessageDialog(null, new JScrollPane(table), title, JOptionPane.PLAIN_MESSAGE);
        UIManager.put("OptionPane.minimumSize", new Dimension(50, 50));
    }

    public static DefaultTableModel buildTableModel(ResultSet results) throws SQLException {
        ResultSetMetaData metaData = results.getMetaData();
        // names of columns
        List<String> header = new ArrayList<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            header.add(metaData.getColumnName(column));
        }
        return buildTableModel(results, header);
    }

    public static DefaultTableModel buildTableModel(ResultSet results, List<String> header) throws SQLException {
        Vector<String> columnNames = new Vector<>(header);
        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (results.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnNames.size(); columnIndex++) {
                vector.add(results.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    public static DefaultTableModel buildTableModel(ArrayList<Object[]> rawData, List<String> header) {
        Vector<String> columnNames = new Vector<String>();
        columnNames.addAll(header);
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        for (Object[] row : rawData) {
            Vector<Object> vector = new Vector<Object>();
            for (Object item : row) {
                vector.add(item);
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

}
