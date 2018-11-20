package com.hl.gui.music;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.Box;
import java.awt.GridLayout;

public class AlbumDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField producerField;
    private JTextField nameField;
    private JTextField yearField;
    private JLabel yearLabel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            AlbumDialog dialog = new AlbumDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public AlbumDialog() {
        setTitle("Insert Music Album");
        setBounds(100, 100, 450, 460);
        getContentPane().setLayout(new BorderLayout());

        Box verticalBox = Box.createVerticalBox();
        getContentPane().add(verticalBox, BorderLayout.NORTH);
        verticalBox.add(contentPanel);
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 160, 240 };
        gbl_contentPanel.rowHeights = new int[] { 26, 26, 26 };
        gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0 };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0 };
        contentPanel.setLayout(gbl_contentPanel);

        JLabel nameLabel = new JLabel("Album Name");
        nameLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.fill = GridBagConstraints.BOTH;
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.gridx = 0;
        gbc_nameLabel.gridy = 0;
        contentPanel.add(nameLabel, gbc_nameLabel);
        nameLabel.setLabelFor(nameField);

        nameField = new JTextField();
        GridBagConstraints gbc_nameField = new GridBagConstraints();
        gbc_nameField.fill = GridBagConstraints.BOTH;
        gbc_nameField.insets = new Insets(0, 0, 5, 0);
        gbc_nameField.gridx = 1;
        gbc_nameField.gridy = 0;
        contentPanel.add(nameField, gbc_nameField);
        nameField.setColumns(10);

        JLabel yearLabel = new JLabel("Release Year");
        yearLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_yearLabel = new GridBagConstraints();
        gbc_yearLabel.fill = GridBagConstraints.BOTH;
        gbc_yearLabel.insets = new Insets(0, 0, 5, 5);
        gbc_yearLabel.gridx = 0;
        gbc_yearLabel.gridy = 1;
        contentPanel.add(yearLabel, gbc_yearLabel);

        yearField = new JTextField();
        GridBagConstraints gbc_yearField = new GridBagConstraints();
        gbc_yearField.fill = GridBagConstraints.BOTH;
        gbc_yearField.insets = new Insets(0, 0, 5, 0);
        gbc_yearField.gridx = 1;
        gbc_yearField.gridy = 1;
        contentPanel.add(yearField, gbc_yearField);
        yearField.setColumns(10);
        yearLabel.setLabelFor(yearField);

        JLabel producerLabel = new JLabel("Producer");
        producerLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_producerLabel = new GridBagConstraints();
        gbc_producerLabel.fill = GridBagConstraints.BOTH;
        gbc_producerLabel.insets = new Insets(0, 0, 0, 5);
        gbc_producerLabel.gridx = 0;
        gbc_producerLabel.gridy = 2;
        contentPanel.add(producerLabel, gbc_producerLabel);
        producerLabel.setLabelFor(producerField);

        producerField = new JTextField();
        GridBagConstraints gbc_producerField = new GridBagConstraints();
        gbc_producerField.fill = GridBagConstraints.BOTH;
        gbc_producerField.gridx = 1;
        gbc_producerField.gridy = 2;
        contentPanel.add(producerField, gbc_producerField);
        producerField.setColumns(10);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        verticalBox.add(tabbedPane);

        tabbedPane.addTab("test", null);
        JPanel addTabPanel = new JPanel(new FlowLayout (FlowLayout.CENTER, 5, 0));
        JButton addTab = new JButton ("+");
        addTab.setOpaque(false);
        addTab.setBorder(null);
        addTab.setContentAreaFilled(false);
        addTab.setFocusPainted(false);
        addTab.setFocusable(false);
        addTabPanel.add(addTab);
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount () - 1, addTabPanel);
        addTab.setFocusable(false);
        addTab.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                String title = "Tab " + String.valueOf(tabbedPane.getTabCount () - 1);
                tabbedPane.addTab(title, new MusicTrackPanel());
            }});
        tabbedPane.setVisible(true);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton submitButton = new JButton("Submit");
        submitButton.setMnemonic('s');
        submitButton.setActionCommand("OK");
        buttonPane.add(submitButton);
        getRootPane().setDefaultButton(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setMnemonic('c');
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

    }

}
