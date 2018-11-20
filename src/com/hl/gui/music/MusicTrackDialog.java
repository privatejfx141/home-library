package com.hl.gui.music;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.exceptions.NameFormatException;
import com.hl.record.Person;
import com.hl.record.music.MusicTrack;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MusicTrackDialog extends JDialog {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 6144503497906561260L;

    private final JPanel contentPanel = new JPanel();
    private JTextField nameField;
    private JTextField languageField;
    private JTextField songwriterField;
    private JTextField composerField;
    private JTextField arrangementField;
    private JComboBox<String> diskTypeComboBox;
    private MusicTrack musicTrack;
    private JTextField singer1Field;
    private JTextField singer2Field;

    public static void main(String args[]) {
        MusicTrackDialog dialog = new MusicTrackDialog(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
    }

    /**
     * Create the dialog.
     * 
     * @param parentDialog
     */
    public MusicTrackDialog(MusicDialog parentDialog) {
        super(parentDialog, "Test", true);

        setResizable(false);
        setTitle("Insert Album Track");
        setBounds(100, 100, 450, 320);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 160, 240 };
        gbl_contentPanel.rowHeights = new int[] { 26, 26, 26, 26, 26, 26, 26 };
        gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0 };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        contentPanel.setLayout(gbl_contentPanel);

        JLabel nameLabel = new JLabel("Track Name");
        nameLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.fill = GridBagConstraints.BOTH;
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.gridx = 0;
        gbc_nameLabel.gridy = 0;
        contentPanel.add(nameLabel, gbc_nameLabel);

        nameField = new JTextField();
        GridBagConstraints gbc_nameField = new GridBagConstraints();
        gbc_nameField.fill = GridBagConstraints.BOTH;
        gbc_nameField.insets = new Insets(0, 0, 5, 0);
        gbc_nameField.gridx = 1;
        gbc_nameField.gridy = 0;
        contentPanel.add(nameField, gbc_nameField);
        nameField.setColumns(10);

        JLabel languageLabel = new JLabel("Language");
        GridBagConstraints gbc_languageLabel = new GridBagConstraints();
        gbc_languageLabel.fill = GridBagConstraints.BOTH;
        gbc_languageLabel.insets = new Insets(0, 0, 5, 5);
        gbc_languageLabel.gridx = 0;
        gbc_languageLabel.gridy = 1;
        contentPanel.add(languageLabel, gbc_languageLabel);

        languageField = new JTextField();
        GridBagConstraints gbc_languageField = new GridBagConstraints();
        gbc_languageField.fill = GridBagConstraints.BOTH;
        gbc_languageField.insets = new Insets(0, 0, 5, 0);
        gbc_languageField.gridx = 1;
        gbc_languageField.gridy = 1;
        contentPanel.add(languageField, gbc_languageField);
        languageField.setColumns(10);

        JLabel lblSingers = new JLabel("Singer 1");
        lblSingers.setForeground(Color.BLUE);
        GridBagConstraints gbc_lblSingers = new GridBagConstraints();
        gbc_lblSingers.anchor = GridBagConstraints.WEST;
        gbc_lblSingers.insets = new Insets(0, 0, 5, 5);
        gbc_lblSingers.gridx = 0;
        gbc_lblSingers.gridy = 2;
        contentPanel.add(lblSingers, gbc_lblSingers);

        singer1Field = new JTextField();
        GridBagConstraints gbc_singer1Field = new GridBagConstraints();
        gbc_singer1Field.insets = new Insets(0, 0, 5, 0);
        gbc_singer1Field.fill = GridBagConstraints.HORIZONTAL;
        gbc_singer1Field.gridx = 1;
        gbc_singer1Field.gridy = 2;
        contentPanel.add(singer1Field, gbc_singer1Field);
        singer1Field.setColumns(10);

        JLabel lblSinger = new JLabel("Singer 2");
        GridBagConstraints gbc_lblSinger = new GridBagConstraints();
        gbc_lblSinger.insets = new Insets(0, 0, 5, 5);
        gbc_lblSinger.anchor = GridBagConstraints.WEST;
        gbc_lblSinger.gridx = 0;
        gbc_lblSinger.gridy = 3;
        contentPanel.add(lblSinger, gbc_lblSinger);

        singer2Field = new JTextField();
        GridBagConstraints gbc_singer2Field = new GridBagConstraints();
        gbc_singer2Field.insets = new Insets(0, 0, 5, 0);
        gbc_singer2Field.fill = GridBagConstraints.HORIZONTAL;
        gbc_singer2Field.gridx = 1;
        gbc_singer2Field.gridy = 3;
        contentPanel.add(singer2Field, gbc_singer2Field);
        singer2Field.setColumns(10);

        JLabel songwriterLabel = new JLabel("Songwriter");
        songwriterLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_songwriterLabel = new GridBagConstraints();
        gbc_songwriterLabel.fill = GridBagConstraints.BOTH;
        gbc_songwriterLabel.insets = new Insets(0, 0, 5, 5);
        gbc_songwriterLabel.gridx = 0;
        gbc_songwriterLabel.gridy = 4;
        contentPanel.add(songwriterLabel, gbc_songwriterLabel);

        songwriterField = new JTextField();
        GridBagConstraints gbc_songwriterField = new GridBagConstraints();
        gbc_songwriterField.fill = GridBagConstraints.BOTH;
        gbc_songwriterField.insets = new Insets(0, 0, 5, 0);
        gbc_songwriterField.gridx = 1;
        gbc_songwriterField.gridy = 4;
        contentPanel.add(songwriterField, gbc_songwriterField);
        songwriterField.setColumns(10);

        JLabel composerLabel = new JLabel("Composer");
        composerLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_composerLabel = new GridBagConstraints();
        gbc_composerLabel.fill = GridBagConstraints.BOTH;
        gbc_composerLabel.insets = new Insets(0, 0, 5, 5);
        gbc_composerLabel.gridx = 0;
        gbc_composerLabel.gridy = 5;
        contentPanel.add(composerLabel, gbc_composerLabel);

        composerField = new JTextField();
        GridBagConstraints gbc_composerField = new GridBagConstraints();
        gbc_composerField.fill = GridBagConstraints.BOTH;
        gbc_composerField.insets = new Insets(0, 0, 5, 0);
        gbc_composerField.gridx = 1;
        gbc_composerField.gridy = 5;
        contentPanel.add(composerField, gbc_composerField);
        composerField.setColumns(10);

        JLabel arrangementLabel = new JLabel("Arrangement");
        arrangementLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_arrangementLabel = new GridBagConstraints();
        gbc_arrangementLabel.fill = GridBagConstraints.BOTH;
        gbc_arrangementLabel.insets = new Insets(0, 0, 5, 5);
        gbc_arrangementLabel.gridx = 0;
        gbc_arrangementLabel.gridy = 6;
        contentPanel.add(arrangementLabel, gbc_arrangementLabel);

        arrangementField = new JTextField();
        GridBagConstraints gbc_arrangementField = new GridBagConstraints();
        gbc_arrangementField.insets = new Insets(0, 0, 5, 0);
        gbc_arrangementField.fill = GridBagConstraints.BOTH;
        gbc_arrangementField.gridx = 1;
        gbc_arrangementField.gridy = 6;
        contentPanel.add(arrangementField, gbc_arrangementField);
        arrangementField.setColumns(10);

        JLabel diskTypeLabel = new JLabel("Disk Type");
        GridBagConstraints gbc_diskTypeLabel = new GridBagConstraints();
        gbc_diskTypeLabel.anchor = GridBagConstraints.WEST;
        gbc_diskTypeLabel.insets = new Insets(0, 0, 0, 5);
        gbc_diskTypeLabel.gridx = 0;
        gbc_diskTypeLabel.gridy = 7;
        contentPanel.add(diskTypeLabel, gbc_diskTypeLabel);

        diskTypeComboBox = new JComboBox<>();
        diskTypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "CD", "Vinyl" }));
        diskTypeComboBox.setSelectedIndex(-1);
        GridBagConstraints gbc_diskTypeComboBox = new GridBagConstraints();
        gbc_diskTypeComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_diskTypeComboBox.gridx = 1;
        gbc_diskTypeComboBox.gridy = 7;
        contentPanel.add(diskTypeComboBox, gbc_diskTypeComboBox);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
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
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        cancelButton.setMnemonic('c');
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    private void submit() {
        String name = nameField.getText();
        String language = languageField.getText();
        String singer1Text = singer1Field.getText();
        String singer2Text = singer2Field.getText();
        String songwriterText = songwriterField.getText();
        String composerText = composerField.getText();
        String arrangementText = arrangementField.getText();
        String diskType = (String) diskTypeComboBox.getSelectedItem();
        // check for mandatory fields
        if (name.isEmpty() || singer1Text.isEmpty() || songwriterText.isEmpty() || composerText.isEmpty()
                || arrangementText.isEmpty()) {
            String error = "All mandatory fields (in blue) must be filled in before submitting.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // parse people
        Person singer1;
        try {
            singer1 = Person.parseName(singer1Text);
        } catch (NameFormatException e) {
            String error = "Singer 1 name is not a proper name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Person singer2;
        try {
            singer2 = Person.parseName(singer2Text);
        } catch (NameFormatException e) {
            String error = "Singer 2 name is not a proper name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Person songwriter;
        try {
            songwriter = Person.parseName(songwriterText);
        } catch (NameFormatException e) {
            String error = "Song writer name is not a proper name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Person composer;
        try {
            composer = Person.parseName(composerText);
        } catch (NameFormatException e) {
            String error = "Composer name is not a proper name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Person arrangement;
        try {
            arrangement = Person.parseName(arrangementText);
        } catch (NameFormatException e) {
            String error = "Arrangement name is not a proper name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        musicTrack = new MusicTrack(name, language, songwriter, composer, arrangement, diskType);
        musicTrack.addSinger(singer1);
        if (singer2 != null) {
            musicTrack.addSinger(singer2);
        }
        System.out.println("Added track: " + name);
        dispose();
    }

    public MusicTrack getMusicTrack() {
        return musicTrack;
    }

}
