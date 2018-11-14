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

public class MusicDialog extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField nameField;
    private JTextField textField_1;
    private JTextField producerField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            MusicDialog dialog = new MusicDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public MusicDialog() {
        setResizable(false);
        setTitle("Insert Music Album");
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] {217, 217};
        gbl_contentPanel.rowHeights = new int[] {26, 26, 26, 0, 0, 26, 26};
        gbl_contentPanel.columnWeights = new double[]{0.0, 0.0};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel nameLabel = new JLabel("Album Name");
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
            JLabel yearLabel = new JLabel("Release Year");
            GridBagConstraints gbc_yearLabel = new GridBagConstraints();
            gbc_yearLabel.fill = GridBagConstraints.BOTH;
            gbc_yearLabel.insets = new Insets(0, 0, 5, 5);
            gbc_yearLabel.gridx = 0;
            gbc_yearLabel.gridy = 1;
            contentPanel.add(yearLabel, gbc_yearLabel);
        }
        {
            textField_1 = new JTextField();
            GridBagConstraints gbc_textField_1 = new GridBagConstraints();
            gbc_textField_1.fill = GridBagConstraints.BOTH;
            gbc_textField_1.insets = new Insets(0, 0, 5, 0);
            gbc_textField_1.gridx = 1;
            gbc_textField_1.gridy = 1;
            contentPanel.add(textField_1, gbc_textField_1);
            textField_1.setColumns(10);
        }
        {
            JLabel producerLabel = new JLabel("Producer");
            GridBagConstraints gbc_producerLabel = new GridBagConstraints();
            gbc_producerLabel.fill = GridBagConstraints.BOTH;
            gbc_producerLabel.insets = new Insets(0, 0, 5, 5);
            gbc_producerLabel.gridx = 0;
            gbc_producerLabel.gridy = 2;
            contentPanel.add(producerLabel, gbc_producerLabel);
        }
        {
            producerField = new JTextField();
            GridBagConstraints gbc_producerField = new GridBagConstraints();
            gbc_producerField.insets = new Insets(0, 0, 5, 0);
            gbc_producerField.fill = GridBagConstraints.BOTH;
            gbc_producerField.gridx = 1;
            gbc_producerField.gridy = 2;
            contentPanel.add(producerField, gbc_producerField);
            producerField.setColumns(10);
        }
        {
            JButton addTrackButton = new JButton("Add Track");
            GridBagConstraints gbc_addTrackButton = new GridBagConstraints();
            gbc_addTrackButton.gridwidth = 2;
            gbc_addTrackButton.insets = new Insets(0, 0, 0, 5);
            gbc_addTrackButton.gridx = 0;
            gbc_addTrackButton.gridy = 4;
            contentPanel.add(addTrackButton, gbc_addTrackButton);
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
