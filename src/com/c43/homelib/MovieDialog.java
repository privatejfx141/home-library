package com.c43.homelib;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MovieDialog extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField nameField;
    private JTextField director1Field;
    private JTextField director2Field;
    private JTextField director3Field;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            MovieDialog dialog = new MovieDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public MovieDialog() {
        setResizable(false);
        setTitle("Insert Movie");
        setBounds(100, 100, 520, 418);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] {217, 217};
        gbl_contentPanel.rowHeights = new int[] {26, 26, 26, 26, 26, 26, 26, 26, 26, 26};
        gbl_contentPanel.columnWeights = new double[]{1.0, 0.0};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0};
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel nameLabel = new JLabel("Movie Title");
            GridBagConstraints gbc_nameLabel = new GridBagConstraints();
            gbc_nameLabel.fill = GridBagConstraints.BOTH;
            gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
            gbc_nameLabel.gridx = 0;
            gbc_nameLabel.gridy = 0;
            contentPanel.add(nameLabel, gbc_nameLabel);
        }
        {
            nameField = new JTextField();
            GridBagConstraints gbc_nameField = new GridBagConstraints();
            gbc_nameField.fill = GridBagConstraints.BOTH;
            gbc_nameField.insets = new Insets(0, 0, 5, 0);
            gbc_nameField.gridx = 1;
            gbc_nameField.gridy = 0;
            contentPanel.add(nameField, gbc_nameField);
            nameField.setColumns(10);
        }
        {
            JButton addCrewButton = new JButton("Add Film Crew");
            GridBagConstraints gbc_addCrewButton = new GridBagConstraints();
            gbc_addCrewButton.insets = new Insets(0, 0, 5, 0);
            gbc_addCrewButton.gridwidth = 2;
            gbc_addCrewButton.gridx = 0;
            gbc_addCrewButton.gridy = 1;
            contentPanel.add(addCrewButton, gbc_addCrewButton);
        }
        {
            JLabel directorLabel = new JLabel("Director(s)");
            GridBagConstraints gbc_directorLabel = new GridBagConstraints();
            gbc_directorLabel.gridwidth = 2;
            gbc_directorLabel.insets = new Insets(0, 0, 5, 0);
            gbc_directorLabel.gridx = 0;
            gbc_directorLabel.gridy = 2;
            contentPanel.add(directorLabel, gbc_directorLabel);
        }
        {
            JPanel panel = new JPanel();
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.gridwidth = 2;
            gbc_panel.insets = new Insets(0, 0, 5, 0);
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 3;
            contentPanel.add(panel, gbc_panel);
            {
                director1Field = new JTextField();
                director1Field.setEditable(false);
                panel.add(director1Field);
                director1Field.setColumns(10);
            }
            {
                director2Field = new JTextField();
                director2Field.setEditable(false);
                panel.add(director2Field);
                director2Field.setColumns(10);
            }
            {
                director3Field = new JTextField();
                director3Field.setEditable(false);
                panel.add(director3Field);
                director3Field.setColumns(10);
            }
        }
        {
            JLabel scriptwriterLabel = new JLabel("Scriptwriter(s)");
            GridBagConstraints gbc_scriptwriterLabel = new GridBagConstraints();
            gbc_scriptwriterLabel.gridwidth = 2;
            gbc_scriptwriterLabel.insets = new Insets(0, 0, 5, 5);
            gbc_scriptwriterLabel.gridx = 0;
            gbc_scriptwriterLabel.gridy = 4;
            contentPanel.add(scriptwriterLabel, gbc_scriptwriterLabel);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("Submit");
                okButton.setMnemonic('s');
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
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
