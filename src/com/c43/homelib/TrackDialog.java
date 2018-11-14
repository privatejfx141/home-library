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

public class TrackDialog extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField nameField;
    private JTextField languageField;
    private JTextField songwriterField;
    private JTextField composerField;
    private JTextField arrangementField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            TrackDialog dialog = new TrackDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public TrackDialog() {
        setResizable(false);
        setTitle("Insert Album Track");
        setBounds(100, 100, 450, 240);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] {200, 220};
        gbl_contentPanel.rowHeights = new int[] {26, 26, 26, 26, 26};
        gbl_contentPanel.columnWeights = new double[]{0.0, 0.0};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel nameLabel = new JLabel("Track Name");
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
            JLabel languageLabel = new JLabel("Language");
            GridBagConstraints gbc_languageLabel = new GridBagConstraints();
            gbc_languageLabel.fill = GridBagConstraints.BOTH;
            gbc_languageLabel.insets = new Insets(0, 0, 5, 5);
            gbc_languageLabel.gridx = 0;
            gbc_languageLabel.gridy = 1;
            contentPanel.add(languageLabel, gbc_languageLabel);
        }
        {
            languageField = new JTextField();
            GridBagConstraints gbc_languageField = new GridBagConstraints();
            gbc_languageField.fill = GridBagConstraints.BOTH;
            gbc_languageField.insets = new Insets(0, 0, 5, 0);
            gbc_languageField.gridx = 1;
            gbc_languageField.gridy = 1;
            contentPanel.add(languageField, gbc_languageField);
            languageField.setColumns(10);
        }
        {
            JLabel songwriterLabel = new JLabel("Songwriter");
            GridBagConstraints gbc_songwriterLabel = new GridBagConstraints();
            gbc_songwriterLabel.fill = GridBagConstraints.BOTH;
            gbc_songwriterLabel.insets = new Insets(0, 0, 5, 5);
            gbc_songwriterLabel.gridx = 0;
            gbc_songwriterLabel.gridy = 2;
            contentPanel.add(songwriterLabel, gbc_songwriterLabel);
        }
        {
            songwriterField = new JTextField();
            GridBagConstraints gbc_songwriterField = new GridBagConstraints();
            gbc_songwriterField.fill = GridBagConstraints.BOTH;
            gbc_songwriterField.insets = new Insets(0, 0, 5, 0);
            gbc_songwriterField.gridx = 1;
            gbc_songwriterField.gridy = 2;
            contentPanel.add(songwriterField, gbc_songwriterField);
            songwriterField.setColumns(10);
        }
        {
            JLabel composerLabel = new JLabel("Composer");
            GridBagConstraints gbc_composerLabel = new GridBagConstraints();
            gbc_composerLabel.fill = GridBagConstraints.BOTH;
            gbc_composerLabel.insets = new Insets(0, 0, 5, 5);
            gbc_composerLabel.gridx = 0;
            gbc_composerLabel.gridy = 3;
            contentPanel.add(composerLabel, gbc_composerLabel);
        }
        {
            composerField = new JTextField();
            GridBagConstraints gbc_composerField = new GridBagConstraints();
            gbc_composerField.fill = GridBagConstraints.BOTH;
            gbc_composerField.insets = new Insets(0, 0, 5, 0);
            gbc_composerField.gridx = 1;
            gbc_composerField.gridy = 3;
            contentPanel.add(composerField, gbc_composerField);
            composerField.setColumns(10);
        }
        {
            JLabel arrangementLabel = new JLabel("Arrangement");
            GridBagConstraints gbc_arrangementLabel = new GridBagConstraints();
            gbc_arrangementLabel.fill = GridBagConstraints.BOTH;
            gbc_arrangementLabel.insets = new Insets(0, 0, 0, 5);
            gbc_arrangementLabel.gridx = 0;
            gbc_arrangementLabel.gridy = 4;
            contentPanel.add(arrangementLabel, gbc_arrangementLabel);
        }
        {
            arrangementField = new JTextField();
            GridBagConstraints gbc_arrangementField = new GridBagConstraints();
            gbc_arrangementField.fill = GridBagConstraints.BOTH;
            gbc_arrangementField.gridx = 1;
            gbc_arrangementField.gridy = 4;
            contentPanel.add(arrangementField, gbc_arrangementField);
            arrangementField.setColumns(10);
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
