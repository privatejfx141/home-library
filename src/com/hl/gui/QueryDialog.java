package com.hl.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseQueryView;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class QueryDialog extends JDialog {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 7699303038774575197L;

    private final JPanel contentPanel = new JPanel();
    private JCheckBox bookCheckBox = new JCheckBox("Book");
    private JCheckBox musicCheckBox = new JCheckBox("Music Album");
    private JCheckBox movieCheckBox = new JCheckBox("Movie");
    private JLabel nameLabel = new JLabel("Product Name");
    private JTextField nameField = new JTextField();
    private JLabel yearLabel = new JLabel("Year of Publication/Release");
    private JTextField yearField = new JTextField();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            QueryDialog dialog = new QueryDialog(null);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public QueryDialog(JFrame parentFrame) {
        super(parentFrame, "Query Record", true);
        setResizable(false);
        setTitle("Query Record");
        setBounds(100, 100, 480, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] {180, 240};
        gbl_contentPanel.rowHeights = new int[] { 26, 26, 26 };
        gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0 };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0 };
        contentPanel.setLayout(gbl_contentPanel);

        JPanel checkBoxPanel = new JPanel();
        GridBagConstraints gbc_checkBoxPanel = new GridBagConstraints();
        gbc_checkBoxPanel.gridwidth = 2;
        gbc_checkBoxPanel.fill = GridBagConstraints.BOTH;
        gbc_checkBoxPanel.insets = new Insets(0, 0, 5, 5);
        gbc_checkBoxPanel.gridx = 0;
        gbc_checkBoxPanel.gridy = 0;
        contentPanel.add(checkBoxPanel, gbc_checkBoxPanel);

        bookCheckBox.setMnemonic('b');
        checkBoxPanel.add(bookCheckBox);
        musicCheckBox.setMnemonic('a');
        checkBoxPanel.add(musicCheckBox);
        movieCheckBox.setMnemonic('m');
        checkBoxPanel.add(movieCheckBox);

        nameLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.fill = GridBagConstraints.BOTH;
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.gridx = 0;
        gbc_nameLabel.gridy = 1;
        contentPanel.add(nameLabel, gbc_nameLabel);

        nameField.setToolTipText("Enter name of product");
        GridBagConstraints gbc_nameField = new GridBagConstraints();
        gbc_nameField.fill = GridBagConstraints.BOTH;
        gbc_nameField.insets = new Insets(0, 0, 5, 0);
        gbc_nameField.gridx = 1;
        gbc_nameField.gridy = 1;
        contentPanel.add(nameField, gbc_nameField);
        nameField.setColumns(10);

        yearLabel.setForeground(Color.BLUE);
        yearLabel.setToolTipText("");
        GridBagConstraints gbc_yearLabel = new GridBagConstraints();
        gbc_yearLabel.fill = GridBagConstraints.BOTH;
        gbc_yearLabel.insets = new Insets(0, 0, 0, 5);
        gbc_yearLabel.gridx = 0;
        gbc_yearLabel.gridy = 2;
        contentPanel.add(yearLabel, gbc_yearLabel);

        GridBagConstraints gbc_yearField = new GridBagConstraints();
        gbc_yearField.fill = GridBagConstraints.BOTH;
        gbc_yearField.gridx = 1;
        gbc_yearField.gridy = 2;
        contentPanel.add(yearField, gbc_yearField);
        yearField.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });
        submitButton.setMnemonic('s');
        submitButton.setActionCommand("OK");
        buttonPane.add(submitButton);
        getRootPane().setDefaultButton(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setMnemonic('c');
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    protected void submit() {
        // check if any check boxes are selected
        boolean isBook = bookCheckBox.isSelected();
        boolean isMusic = musicCheckBox.isSelected();
        boolean isMovie = movieCheckBox.isSelected();
        if (!(isBook || isMusic || isMovie)) {
            String error = "At least one checkbox option must be selected.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // parse name and year fields
        String name = nameField.getText();
        String yearText = yearField.getText();
        if (name.isEmpty() || yearText.isEmpty()) {
            String error = "All mandatory fields (in blue) must be filled in before submitting.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int year = -1;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            String error = "Year of Publication/Release must be an integer.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dispose();
        // display results
        Connection connection = DatabaseDriver.connectToDatabase();
        ArrayList<Object[]> rawData = DatabaseQueryView.getViewData(connection, name, year, isBook, isMusic, isMovie);
        ArrayList<String> header = new ArrayList<>();
        header.add("Product Name");
        header.add("Year");
        header.add("Product Type");
        header.add("Director/Singer/Author");
        try {
            ResultSetTable.generateTableDialog(rawData, header, "View Results");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
