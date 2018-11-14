package com.c43.homelib;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JCheckBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;

public class QueryDialog extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField nameField;
    private JTextField yearField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            QueryDialog dialog = new QueryDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public QueryDialog() {
        setResizable(false);
        setTitle("Query Record");
        setBounds(100, 100, 480, 200);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] {200, 220};
        gbl_contentPanel.rowHeights = new int[] {26, 26, 26};
        gbl_contentPanel.columnWeights = new double[]{0.0, 0.0};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0};
        contentPanel.setLayout(gbl_contentPanel);
        {
            JPanel checkBoxPanel = new JPanel();
            GridBagConstraints gbc_checkBoxPanel = new GridBagConstraints();
            gbc_checkBoxPanel.gridwidth = 2;
            gbc_checkBoxPanel.fill = GridBagConstraints.BOTH;
            gbc_checkBoxPanel.insets = new Insets(0, 0, 5, 5);
            gbc_checkBoxPanel.gridx = 0;
            gbc_checkBoxPanel.gridy = 0;
            contentPanel.add(checkBoxPanel, gbc_checkBoxPanel);
            {
                JCheckBox bookCheckBox = new JCheckBox("Book");
                checkBoxPanel.add(bookCheckBox);
            }
            {
                JCheckBox musicCheckBox = new JCheckBox("Music Album");
                checkBoxPanel.add(musicCheckBox);
            }
            {
                JCheckBox movieCheckBox = new JCheckBox("Movie");
                checkBoxPanel.add(movieCheckBox);
            }
        }
        {
            JLabel nameLabel = new JLabel("Product Name");
            GridBagConstraints gbc_nameLabel = new GridBagConstraints();
            gbc_nameLabel.fill = GridBagConstraints.BOTH;
            gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
            gbc_nameLabel.gridx = 0;
            gbc_nameLabel.gridy = 1;
            contentPanel.add(nameLabel, gbc_nameLabel);
        }
        {
            nameField = new JTextField();
            nameField.setToolTipText("Enter name of product");
            GridBagConstraints gbc_nameField = new GridBagConstraints();
            gbc_nameField.fill = GridBagConstraints.BOTH;
            gbc_nameField.insets = new Insets(0, 0, 5, 0);
            gbc_nameField.gridx = 1;
            gbc_nameField.gridy = 1;
            contentPanel.add(nameField, gbc_nameField);
            nameField.setColumns(10);
        }
        {
            JLabel yearLabel = new JLabel("Year of Publication/Year");
            yearLabel.setToolTipText("");
            GridBagConstraints gbc_yearLabel = new GridBagConstraints();
            gbc_yearLabel.fill = GridBagConstraints.BOTH;
            gbc_yearLabel.insets = new Insets(0, 0, 0, 5);
            gbc_yearLabel.gridx = 0;
            gbc_yearLabel.gridy = 2;
            contentPanel.add(yearLabel, gbc_yearLabel);
        }
        {
            yearField = new JTextField();
            GridBagConstraints gbc_yearField = new GridBagConstraints();
            gbc_yearField.fill = GridBagConstraints.BOTH;
            gbc_yearField.gridx = 1;
            gbc_yearField.gridy = 2;
            contentPanel.add(yearField, gbc_yearField);
            yearField.setColumns(10);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton submitButton = new JButton("Submit");
                submitButton.setMnemonic('s');
                submitButton.setActionCommand("OK");
                buttonPane.add(submitButton);
                getRootPane().setDefaultButton(submitButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setMnemonic('c');
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }
}
