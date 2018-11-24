package com.hl.gui.data.music;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseInserter;
import com.hl.exceptions.DatabaseInsertException;
import com.hl.exceptions.NameFormatException;
import com.hl.gui.HomeLibrary;
import com.hl.record.Person;
import com.hl.record.music.MusicAlbum;
import com.hl.record.music.MusicTrack;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<String, MusicTrack> trackMap = new HashMap<>();
    private ArrayList<String> trackNames = new ArrayList<>();

    private ActionListener cancelListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            dispose();
        }
    };

    private ActionListener submitListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // parse field to get album data
            MusicAlbum album = parseFields();
            if (album == null) {
                return;
            }
            // insert into database
            if (insertMusicAlbum(album)) {
                dispose();
            }
        }
    };

    private ActionListener openDialogListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            openMusicTrackDialog(null);
        }
    };
    
    /**
     * Create the dialog.
     * 
     * @param insertRecord
     */
    public MusicDialog(JFrame parentFrame, int mode) {
        super(parentFrame, "Insert Music Album", true);
        if (mode == HomeLibrary.UPDATE_RECORD) {
            setTitle("Update Music Album");
        }
        setResizable(false);
        setBounds(100, 100, 450, 470);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);

        createGUI();
    }

    private void createGUI() {
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
        addTrackButton.addActionListener(openDialogListener);
        GridBagConstraints gbc_addTrackButton = new GridBagConstraints();
        gbc_addTrackButton.insets = new Insets(0, 0, 5, 0);
        gbc_addTrackButton.gridwidth = 2;
        gbc_addTrackButton.gridx = 0;
        gbc_addTrackButton.gridy = 3;
        contentPanel.add(addTrackButton, gbc_addTrackButton);

        trackList = new JList<String>(new DefaultListModel<String>());
        trackList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2) {
                    // double click to update track
                    String oldTrackKey = trackList.getSelectedValue();
                    MusicTrack track = trackMap.get(oldTrackKey);
                    openMusicTrackDialog(track);
                }
                if (SwingUtilities.isRightMouseButton(evt)) {
                    // right click to delete track
                    String oldTrackKey = trackList.getSelectedValue();
                    if (oldTrackKey != null) {
                        MusicTrack track = trackMap.get(oldTrackKey);
                        removeMusicTrack(track);
                    }
                }
            }
        });
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
        okButton.addActionListener(submitListener);
        okButton.setMnemonic('s');
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(cancelListener);
        cancelButton.setMnemonic('c');
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    private void openMusicTrackDialog(MusicTrack oldTrack) {
        MusicTrackDialog dialog = new MusicTrackDialog(this, oldTrack, trackNames);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
        MusicTrack newTrack = dialog.getMusicTrack();
        // if new track was submitted
        if (newTrack != null) {
            // if we are updating
            if (oldTrack != null) {
                removeMusicTrack(oldTrack);
            }
            addMusicTrack(newTrack);
        }
    }

    private void handleSubmit() {
        // parse field to get album data
        MusicAlbum album = parseFields();
        if (album == null) {
            return;
        }
        // insert into database
        if (insertMusicAlbum(album)) {
            dispose();
        }
    }

    private MusicAlbum parseFields() {
        String name = nameField.getText();
        String producerText = producerField.getText();
        String yearText = yearField.getText();
        // check mandatory fields
        if (name.isEmpty() || producerText.isEmpty() || yearText.isEmpty()) {
            String error = "All mandatory fields (in blue) must be filled in before submitting.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        // attempt to parse year
        int year = -1;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            String error = "Release Year must be an integer.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        // attempt to parse producer
        Person producer;
        try {
            producer = Person.parseName(producerText);
        } catch (NameFormatException e) {
            String error = "Producer name is not a proper name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        // check music tracks
        if (trackMap.isEmpty()) {
            String error = "Music album must have at least 1 music track.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return new MusicAlbum(name, year, producer, new ArrayList<>(trackMap.values()));
    }

    private void addMusicTrack(MusicTrack track) {
        trackMap.put(track.toString(), track);
        DefaultListModel<String> model = (DefaultListModel<String>) trackList.getModel();
        model.addElement(track.toString());
        trackNames.add(track.getName().toLowerCase());
    }

    private void removeMusicTrack(MusicTrack track) {
        String trackKey = track.toString();
        trackMap.remove(track.toString());
        DefaultListModel<String> model = (DefaultListModel<String>) trackList.getModel();
        model.removeElement(trackKey);
        trackNames.remove(track.getName().toLowerCase());
    }

    private boolean insertMusicAlbum(MusicAlbum album) {
        Connection connection = DatabaseDriver.connectToDatabase();
        try {
            int result = DatabaseInserter.insertMusicAlbum(connection, album);
            if (result > 0) {
                HomeLibrary.showSubmitMessageBox(this, HomeLibrary.INSERT_DB_SUCCESS_MSG);
                return true;
            } else {
                HomeLibrary.showSubmitErrorMessageBox(this, HomeLibrary.INSERT_DB_FAILURE_MSG);
            }
        } catch (DatabaseInsertException e) {
            HomeLibrary.showSubmitErrorMessageBox(this, HomeLibrary.INSERT_DB_FAILURE_MSG);
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
