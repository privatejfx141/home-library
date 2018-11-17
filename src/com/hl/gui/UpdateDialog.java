package com.hl.gui;

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

public class UpdateDialog extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UpdateDialog dialog = new UpdateDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public UpdateDialog() {
        setResizable(false);
        setTitle("Update Item");
        setBounds(100, 100, 450, 160);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] {400};
        gbl_contentPanel.rowHeights = new int[] {26, 26};
        gbl_contentPanel.columnWeights = new double[]{0.0};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0};
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel itemLabel = new JLabel("Name of Item to Update");
            GridBagConstraints gbc_itemLabel = new GridBagConstraints();
            gbc_itemLabel.fill = GridBagConstraints.BOTH;
            gbc_itemLabel.insets = new Insets(0, 0, 5, 0);
            gbc_itemLabel.gridx = 0;
            gbc_itemLabel.gridy = 0;
            contentPanel.add(itemLabel, gbc_itemLabel);
        }
        {
            textField = new JTextField();
            GridBagConstraints gbc_textField = new GridBagConstraints();
            gbc_textField.fill = GridBagConstraints.BOTH;
            gbc_textField.gridx = 0;
            gbc_textField.gridy = 1;
            contentPanel.add(textField, gbc_textField);
            textField.setColumns(64);
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
