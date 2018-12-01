package com.hl.gui.data.music;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseInserter;
import com.hl.database.DatabaseUpdater;
import com.hl.exceptions.DatabaseInsertException;
import com.hl.exceptions.DatabaseUpdateException;
import com.hl.exceptions.NameFormatException;
import com.hl.gui.HomeLibrary;
import com.hl.gui.data.HomeLibraryProductDialog;
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
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;

public class MusicDialog extends HomeLibraryProductDialog {

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
    private MusicAlbum album;

    private String oldAlbumName = "";
    private int oldAlbumYear = -1;

    public static void main(String[] args) {
        new MusicDialog(null);
    }

    private ActionListener cancelListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            MusicDialog.this.album = null;
            dispose();
        }
    };

    private ActionListener submitListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSubmit();
        }
    };

    private ActionListener openDialogListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            handleOpenDialog(null);
        }
    };

    private MouseAdapter listMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent evt) {
            String trackKey = trackList.getSelectedValue();
            if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2) {
                // double click to update track
                MusicTrack track = trackMap.get(trackKey);
                handleOpenDialog(track);
            }
            if (SwingUtilities.isRightMouseButton(evt)) {
                // right click to delete track
                if (trackKey != null) {
                    MusicTrack track = trackMap.get(trackKey);
                    removeMusicTrack(track);
                }
            }
        }
    };

    /**
     * @wbp.parser.constructor
     */
    public MusicDialog(Frame parent) {
        this(parent, null);
    }

    /**
     * Create the dialog.
     * 
     * @param insertRecord
     */
    public MusicDialog(Frame parent, MusicAlbum data) {
        super(parent, data);
        initialize();
        if (isUpdating = data != null) {
            populateFields(data);
        }
        setVisible(true);
    }

    private void initialize() {
        createGUI();
        addMandatoryField(nameField);
        addMandatoryField(yearField);
        addMandatoryField(producerField);
        System.out.println("Music dialog: initialized.");
    }

    private void handleOpenDialog(MusicTrack oldTrack) {
        ArrayList<MusicTrack> addedTracks = new ArrayList<>(trackMap.values());
        // if updating, remove old author from check
        if (oldTrack != null) {
            addedTracks.remove(oldTrack);
        }
        MusicTrackDialog dialog = new MusicTrackDialog(this, addedTracks, oldTrack);
        MusicTrack newTrack = dialog.getParsedData();
        // if new track was submitted
        if (newTrack != null) {
            // if we are updating
            if (oldTrack != null) {
                removeMusicTrack(oldTrack);
            }
            addMusicTrack(newTrack);
        }
    }

    private void addMusicTrack(MusicTrack track) {
        String trackKey = track.getName();
        trackMap.put(trackKey, track);
        DefaultListModel<String> model = (DefaultListModel<String>) trackList.getModel();
        model.addElement(trackKey);
        System.out.println("Music dialog: " + trackKey + " added.");
    }

    private void removeMusicTrack(MusicTrack track) {
        String trackKey = track.getName();
        trackMap.remove(trackKey);
        DefaultListModel<String> model = (DefaultListModel<String>) trackList.getModel();
        model.removeElement(trackKey);
        System.out.println("Music dialog: " + trackKey + " removed.");
    }

    private void handleSubmit() {
        // parse field to get album data
        album = (MusicAlbum) parseFields();
        if (album == null) {
            return;
        }
        // update or insert to database
        if (isUpdating) {
            if (updateToDatabase(album)) {
                System.out.println("Music dialog: updated to DB.");
                dispose();
            }
        } else {
            if (insertToDatabase(album)) {
                System.out.println("Music dialog: inserted to DB.");
                dispose();
            }
        }
    }

    @Override
    public void createGUI() {
        setResizable(false);
        setBounds(100, 100, 450, 480);
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
        producerLabel.setToolTipText("Right-click to clear");
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
        trackList.addMouseListener(listMouseAdapter);
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

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this);
    }

    @Override
    public void populateFields(Object data) {
        MusicAlbum album = (MusicAlbum) data;
        oldAlbumName = album.getPrimaryKeyName();
        oldAlbumYear = album.getPrimaryKeyYear();
        // populate text fields
        nameField.setText(oldAlbumName);
        yearField.setText(Integer.toString(oldAlbumYear));
        producerField.setText(album.getProducer().getName());
        // populate list with music tracks
        for (MusicTrack track : album.getMusicTracks()) {
            addMusicTrack(track);
        }
    }

    @Override
    public MusicAlbum parseFields() {
        if (checkMandatoryFields()) {
            if (trackMap.isEmpty()) {
                String error = "At least one music track must be added.";
                HomeLibrary.showSubmitErrorMessageBox(this, error);
                return null;
            }
            try {
                MusicAlbum album = new MusicAlbum.Builder() //
                        .setPrimaryKey(oldAlbumName, oldAlbumYear) //
                        .setName(nameField.getText()) //
                        .setProducer(producerField.getText()) //
                        .setYear(yearField.getText()) //
                        .addTracks(trackMap.values()) //
                        .create();
                return album;
            } catch (NumberFormatException | NameFormatException e) {
                HomeLibrary.showSubmitErrorMessageBox(this, e.getMessage());
            }
        }
        return null;
    }

    @Override
    public MusicAlbum getParsedData() {
        return album;
    }

    @Override
    public boolean insertToDatabase(Object data) {
        MusicAlbum album = (MusicAlbum) data;
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
            String error = HomeLibrary.INSERT_DB_FAILURE_MSG + "\n" + e.getMessage();
            HomeLibrary.showSubmitErrorMessageBox(this, error);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean updateToDatabase(Object data) {
        boolean result = false;
        MusicAlbum album = (MusicAlbum) data;
        Connection connection = DatabaseDriver.connectToDatabase();
        try {
            if (result = DatabaseUpdater.updateMusicAlbum(connection, album)) {
                HomeLibrary.showSubmitMessageBox(this, HomeLibrary.UPDATE_DB_SUCCESS_MSG);
            } else {
                HomeLibrary.showSubmitErrorMessageBox(this, HomeLibrary.UPDATE_DB_FAILURE_MSG);
            }
        } catch (DatabaseUpdateException e) {
            String error = HomeLibrary.UPDATE_DB_FAILURE_MSG + "\n" + e.getMessage();
            HomeLibrary.showSubmitErrorMessageBox(this, error);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
