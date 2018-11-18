package com.hl.gui.music;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.record.Person;
import com.hl.record.music.MusicAlbum;
import com.hl.record.music.MusicTrack;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;

public class MusicDialog extends JDialog {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 187480722570953554L;

    private final JPanel contentPanel = new JPanel();
    private JTextField nameField;
    private JTextField yearField;
    private JTextField producerField;
    private JList<String> trackList;
    private ArrayList<MusicTrack> musicTracks = new ArrayList<>();
    private MusicAlbum musicAlbum;

    /**
     * Create the dialog.
     */
    public MusicDialog(JFrame parentFrame) {
        super(parentFrame, "Insert Music Album", true);
        setResizable(false);
        setBounds(100, 100, 450, 470);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 160, 240 };
        gbl_contentPanel.rowHeights = new int[] { 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26 };
        gbl_contentPanel.columnWeights = new double[] { 1.0, 0.0 };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0 };
        contentPanel.setLayout(gbl_contentPanel);

        JLabel nameLabel = new JLabel("Album Name");
        nameLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.fill = GridBagConstraints.BOTH;
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.gridx = 0;
        gbc_nameLabel.gridy = 0;
        contentPanel.add(nameLabel, gbc_nameLabel);

        nameField = new JTextField();
        nameLabel.setLabelFor(nameField);
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
        yearLabel.setLabelFor(yearField);
        GridBagConstraints gbc_yearField = new GridBagConstraints();
        gbc_yearField.fill = GridBagConstraints.BOTH;
        gbc_yearField.insets = new Insets(0, 0, 5, 0);
        gbc_yearField.gridx = 1;
        gbc_yearField.gridy = 1;
        contentPanel.add(yearField, gbc_yearField);
        yearField.setColumns(10);

        JLabel producerLabel = new JLabel("Producer");
        producerLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_producerLabel = new GridBagConstraints();
        gbc_producerLabel.fill = GridBagConstraints.BOTH;
        gbc_producerLabel.insets = new Insets(0, 0, 5, 5);
        gbc_producerLabel.gridx = 0;
        gbc_producerLabel.gridy = 2;
        contentPanel.add(producerLabel, gbc_producerLabel);

        producerField = new JTextField();
        producerLabel.setLabelFor(producerField);
        GridBagConstraints gbc_producerField = new GridBagConstraints();
        gbc_producerField.insets = new Insets(0, 0, 5, 0);
        gbc_producerField.fill = GridBagConstraints.BOTH;
        gbc_producerField.gridx = 1;
        gbc_producerField.gridy = 2;
        contentPanel.add(producerField, gbc_producerField);
        producerField.setColumns(10);

        JButton addTrackButton = new JButton("Add Music Track");
        addTrackButton.setMnemonic('t');
        addTrackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMusicTrackDialog();
            }
        });
        GridBagConstraints gbc_addTrackButton = new GridBagConstraints();
        gbc_addTrackButton.insets = new Insets(0, 0, 5, 0);
        gbc_addTrackButton.gridwidth = 2;
        gbc_addTrackButton.gridx = 0;
        gbc_addTrackButton.gridy = 3;
        contentPanel.add(addTrackButton, gbc_addTrackButton);

        trackList = new JList<String>(new DefaultListModel<String>());
        JScrollPane trackListScrollPane = new JScrollPane(trackList);
        GridBagConstraints gbc_trackListScrollPane = new GridBagConstraints();
        gbc_trackListScrollPane.gridheight = 9;
        gbc_trackListScrollPane.gridwidth = 2;
        gbc_trackListScrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_trackListScrollPane.fill = GridBagConstraints.BOTH;
        gbc_trackListScrollPane.gridx = 0;
        gbc_trackListScrollPane.gridy = 4;
        contentPanel.add(trackListScrollPane, gbc_trackListScrollPane);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("Submit");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });
        okButton.setMnemonic('s');
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

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

    private void openMusicTrackDialog() {
        MusicTrackDialog dialog = new MusicTrackDialog(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
        MusicTrack track = dialog.getMusicTrack();
        if (track != null) {
            addMusicTrack(dialog.getMusicTrack());
        }
    }

    private void addMusicTrack(MusicTrack track) {
        musicTracks.add(track);
        DefaultListModel<String> model = (DefaultListModel<String>) trackList.getModel();
        String element = track.getName() + " [" + track.getLanguage() + "] - ";
        element += track.getSongwriter() + ", " + track.getComposer() + ", ";
        element += track.getArrangement();
        model.addElement(element);
    }

    private void submit() {
        String name = nameField.getText();
        String producerText = producerField.getText();
        String yearText = yearField.getText();
        // check mandatory fields
        if (name.isEmpty() || producerText.isEmpty() || yearText.isEmpty()) {
            String error = "All mandatory fields (in blue) must be filled in before submitting.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // attempt to parse year
        int year = -1;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            String error = "Release Year must be an integer.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // attempt to parse producer
        Person producer = Person.parseName(producerText);
        if (producer == null) {
            String error = "Producer name is not a proper name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // check music tracks
        if (musicTracks.isEmpty()) {
            String error = "Music album must have at least 1 music track.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        musicAlbum = new MusicAlbum(name, year, producer, musicTracks);
        dispose();
    }

    public MusicAlbum getMusicAlbum() {
        return musicAlbum;
    }
}
