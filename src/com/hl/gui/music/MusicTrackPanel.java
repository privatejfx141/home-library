package com.hl.gui.music;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hl.record.music.MusicTrack;
import javax.swing.border.EmptyBorder;

public class MusicTrackPanel extends JPanel {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 1294079778860808968L;

    public JPanel trackPanel = new JPanel();
    public JTextField nameField;
    public JTextField languageField;
    public JTextField songwriterField;
    public JTextField composerField;
    public JTextField arrangementField;
    public JComboBox<String> diskTypeComboBox;
    public MusicTrack musicTrack;
    public JTextField singer1Field;
    public JTextField singer2Field;
    
    /**
     * Create the panel.
     */
    public MusicTrackPanel() {
        GridBagLayout gbl_trackPanel = new GridBagLayout();
        gbl_trackPanel.columnWidths = new int[] { 160, 240 };
        gbl_trackPanel.rowHeights = new int[] {26, 26, 26, 26, 26, 26, 26};
        gbl_trackPanel.columnWeights = new double[] { 0.0, 1.0 };
        gbl_trackPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        trackPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        trackPanel.setLayout(gbl_trackPanel);

        JLabel nameLabel = new JLabel("Track Name");
        nameLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.fill = GridBagConstraints.BOTH;
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.gridx = 0;
        gbc_nameLabel.gridy = 0;
        trackPanel.add(nameLabel, gbc_nameLabel);

        nameField = new JTextField();
        GridBagConstraints gbc_nameField = new GridBagConstraints();
        gbc_nameField.fill = GridBagConstraints.BOTH;
        gbc_nameField.insets = new Insets(0, 0, 5, 0);
        gbc_nameField.gridx = 1;
        gbc_nameField.gridy = 0;
        trackPanel.add(nameField, gbc_nameField);
        nameField.setColumns(10);

        JLabel languageLabel = new JLabel("Language");
        GridBagConstraints gbc_languageLabel = new GridBagConstraints();
        gbc_languageLabel.fill = GridBagConstraints.BOTH;
        gbc_languageLabel.insets = new Insets(0, 0, 5, 5);
        gbc_languageLabel.gridx = 0;
        gbc_languageLabel.gridy = 1;
        trackPanel.add(languageLabel, gbc_languageLabel);

        languageField = new JTextField();
        GridBagConstraints gbc_languageField = new GridBagConstraints();
        gbc_languageField.fill = GridBagConstraints.BOTH;
        gbc_languageField.insets = new Insets(0, 0, 5, 0);
        gbc_languageField.gridx = 1;
        gbc_languageField.gridy = 1;
        trackPanel.add(languageField, gbc_languageField);
        languageField.setColumns(10);

        JLabel lblSingers = new JLabel("Singer 1");
        lblSingers.setForeground(Color.BLUE);
        GridBagConstraints gbc_lblSingers = new GridBagConstraints();
        gbc_lblSingers.anchor = GridBagConstraints.WEST;
        gbc_lblSingers.insets = new Insets(0, 0, 5, 5);
        gbc_lblSingers.gridx = 0;
        gbc_lblSingers.gridy = 2;
        trackPanel.add(lblSingers, gbc_lblSingers);

        singer1Field = new JTextField();
        GridBagConstraints gbc_singer1Field = new GridBagConstraints();
        gbc_singer1Field.insets = new Insets(0, 0, 5, 0);
        gbc_singer1Field.fill = GridBagConstraints.HORIZONTAL;
        gbc_singer1Field.gridx = 1;
        gbc_singer1Field.gridy = 2;
        trackPanel.add(singer1Field, gbc_singer1Field);
        singer1Field.setColumns(10);

        JLabel lblSinger = new JLabel("Singer 2");
        GridBagConstraints gbc_lblSinger = new GridBagConstraints();
        gbc_lblSinger.insets = new Insets(0, 0, 5, 5);
        gbc_lblSinger.anchor = GridBagConstraints.WEST;
        gbc_lblSinger.gridx = 0;
        gbc_lblSinger.gridy = 3;
        trackPanel.add(lblSinger, gbc_lblSinger);

        singer2Field = new JTextField();
        GridBagConstraints gbc_singer2Field = new GridBagConstraints();
        gbc_singer2Field.insets = new Insets(0, 0, 5, 0);
        gbc_singer2Field.fill = GridBagConstraints.HORIZONTAL;
        gbc_singer2Field.gridx = 1;
        gbc_singer2Field.gridy = 3;
        trackPanel.add(singer2Field, gbc_singer2Field);
        singer2Field.setColumns(10);

        JLabel songwriterLabel = new JLabel("Songwriter");
        songwriterLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_songwriterLabel = new GridBagConstraints();
        gbc_songwriterLabel.fill = GridBagConstraints.BOTH;
        gbc_songwriterLabel.insets = new Insets(0, 0, 5, 5);
        gbc_songwriterLabel.gridx = 0;
        gbc_songwriterLabel.gridy = 4;
        trackPanel.add(songwriterLabel, gbc_songwriterLabel);

        songwriterField = new JTextField();
        GridBagConstraints gbc_songwriterField = new GridBagConstraints();
        gbc_songwriterField.fill = GridBagConstraints.BOTH;
        gbc_songwriterField.insets = new Insets(0, 0, 5, 0);
        gbc_songwriterField.gridx = 1;
        gbc_songwriterField.gridy = 4;
        trackPanel.add(songwriterField, gbc_songwriterField);
        songwriterField.setColumns(10);

        JLabel composerLabel = new JLabel("Composer");
        composerLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_composerLabel = new GridBagConstraints();
        gbc_composerLabel.fill = GridBagConstraints.BOTH;
        gbc_composerLabel.insets = new Insets(0, 0, 5, 5);
        gbc_composerLabel.gridx = 0;
        gbc_composerLabel.gridy = 5;
        trackPanel.add(composerLabel, gbc_composerLabel);

        composerField = new JTextField();
        GridBagConstraints gbc_composerField = new GridBagConstraints();
        gbc_composerField.fill = GridBagConstraints.BOTH;
        gbc_composerField.insets = new Insets(0, 0, 5, 0);
        gbc_composerField.gridx = 1;
        gbc_composerField.gridy = 5;
        trackPanel.add(composerField, gbc_composerField);
        composerField.setColumns(10);

        JLabel arrangementLabel = new JLabel("Arrangement");
        arrangementLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_arrangementLabel = new GridBagConstraints();
        gbc_arrangementLabel.fill = GridBagConstraints.BOTH;
        gbc_arrangementLabel.insets = new Insets(0, 0, 5, 5);
        gbc_arrangementLabel.gridx = 0;
        gbc_arrangementLabel.gridy = 6;
        trackPanel.add(arrangementLabel, gbc_arrangementLabel);

        arrangementField = new JTextField();
        GridBagConstraints gbc_arrangementField = new GridBagConstraints();
        gbc_arrangementField.insets = new Insets(0, 0, 5, 0);
        gbc_arrangementField.fill = GridBagConstraints.BOTH;
        gbc_arrangementField.gridx = 1;
        gbc_arrangementField.gridy = 6;
        trackPanel.add(arrangementField, gbc_arrangementField);
        arrangementField.setColumns(10);

        JLabel diskTypeLabel = new JLabel("Disk Type");
        GridBagConstraints gbc_diskTypeLabel = new GridBagConstraints();
        gbc_diskTypeLabel.anchor = GridBagConstraints.WEST;
        gbc_diskTypeLabel.insets = new Insets(0, 0, 0, 5);
        gbc_diskTypeLabel.gridx = 0;
        gbc_diskTypeLabel.gridy = 7;
        trackPanel.add(diskTypeLabel, gbc_diskTypeLabel);

        diskTypeComboBox = new JComboBox<>();
        diskTypeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "CD", "Vinyl" }));
        diskTypeComboBox.setSelectedIndex(-1);
        GridBagConstraints gbc_diskTypeComboBox = new GridBagConstraints();
        gbc_diskTypeComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_diskTypeComboBox.gridx = 1;
        gbc_diskTypeComboBox.gridy = 7;
        trackPanel.add(diskTypeComboBox, gbc_diskTypeComboBox);
    }

}
