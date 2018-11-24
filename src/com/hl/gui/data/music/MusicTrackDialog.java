package com.hl.gui.data.music;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.exceptions.NameFormatException;
import com.hl.gui.HomeLibrary;
import com.hl.record.Person;
import com.hl.record.music.MusicTrack;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MusicTrackDialog extends JDialog {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 6144503497906561260L;

    private final JPanel contentPanel = new JPanel();
    private JTextField nameField = new JTextField();
    private JTextField languageField = new JTextField();
    private JTextField songwriterField = new JTextField();;
    private JTextField composerField = new JTextField();
    private JTextField arrangementField = new JTextField();
    private JTextField singer1Field = new JTextField();
    private JTextField singer2Field = new JTextField();
    private JComboBox<String> diskTypeComboBox = new JComboBox<>(
            new DefaultComboBoxModel<String>(new String[] { "CD", "Vinyl" }));
    private ArrayList<String> trackNames;
    private MusicTrack musicTrack;

    public static void main(String args[]) {
        MusicTrackDialog dialog = new MusicTrackDialog(null, null, new ArrayList<>());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
    }

    /**
     * Create the dialog.
     * 
     * @param parentDialog
     * @param track
     * @param trackNames
     */
    public MusicTrackDialog(MusicDialog parentDialog, MusicTrack track, ArrayList<String> trackNames) {
        super(parentDialog, "Test", true);
        // set track names to keep track of which tracks are already added
        this.trackNames = trackNames;

        setResizable(false);
        setTitle("Insert Album Track");
        setBounds(100, 100, 450, 320);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);

        createGUI();

        // if update, pre-populate fields
        if (track != null) {
            trackNames.remove(track.getName().toLowerCase());
            prepopulateFields(track);
        }
    }

    private void createGUI() {
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
                handleSubmit();
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

    private void prepopulateFields(MusicTrack track) {
        nameField.setText(track.getName());
        languageField.setText(track.getLanguage());
        songwriterField.setText(track.getSongwriter().toString());
        composerField.setText(track.getComposer().toString());
        arrangementField.setText(track.getArrangement().toString());
        List<Person> singers = track.getSingers();
        singer1Field.setText(singers.get(0).toString());
        if (singers.size() == 2) {
            singer2Field.setText(singers.get(1).toString());
        }
        diskTypeComboBox.setSelectedItem(track.getDiskType());
    }

    private MusicTrack parseFields() {
        String name = nameField.getText().trim();
        String language = languageField.getText().trim();
        String singer1Text = singer1Field.getText().trim();
        String singer2Text = singer2Field.getText().trim();
        String songwriterText = songwriterField.getText().trim();
        String composerText = composerField.getText().trim();
        String arrangementText = arrangementField.getText().trim();
        String diskType = (String) diskTypeComboBox.getSelectedItem();
        // check for mandatory fields
        if (name.isEmpty() || singer1Text.isEmpty() || songwriterText.isEmpty() || composerText.isEmpty()
                || arrangementText.isEmpty()) {
            String error = HomeLibrary.MANDATORY_FIELD_MSG;
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        // check if track name already exists
        if (trackNames.contains(name.toLowerCase())) {
            String error = "Track " + name + " already exists in album.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        // parse people
        Person singer1;
        try {
            singer1 = Person.parseName(singer1Text);
        } catch (NameFormatException e) {
            String error = "Singer 1 name is not a proper name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        Person singer2 = null;
        if (!singer2Text.isEmpty()) {
            try {
                singer2 = Person.parseName(singer2Text);
            } catch (NameFormatException e) {
                String error = "Singer 2 name is not a proper name.";
                JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        Person songwriter;
        try {
            songwriter = Person.parseName(songwriterText);
        } catch (NameFormatException e) {
            String error = "Song writer name is not a proper name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        Person composer;
        try {
            composer = Person.parseName(composerText);
        } catch (NameFormatException e) {
            String error = "Composer name is not a proper name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        Person arrangement;
        try {
            arrangement = Person.parseName(arrangementText);
        } catch (NameFormatException e) {
            String error = "Arrangement name is not a proper name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        MusicTrack track = new MusicTrack(name, language, songwriter, composer, arrangement, diskType);
        track.addSinger(singer1);
        if (singer2 != null) {
            track.addSinger(singer2);
        }
        return track;
    }

    private void handleSubmit() {
        musicTrack = parseFields();
        if (musicTrack != null) {
            dispose();
        }
    }

    public MusicTrack getMusicTrack() {
        return musicTrack;
    }

}
