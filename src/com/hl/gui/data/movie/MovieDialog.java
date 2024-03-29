package com.hl.gui.data.movie;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseInserter;
import com.hl.database.DatabaseUpdater;
import com.hl.exceptions.DatabaseInsertException;
import com.hl.exceptions.DatabaseUpdateException;
import com.hl.gui.HomeLibrary;
import com.hl.gui.data.HomeLibraryProductDialog;
import com.hl.record.movie.Movie;
import com.hl.record.movie.MovieCrew;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class MovieDialog extends HomeLibraryProductDialog {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 2138888572924832525L;

    private final JPanel contentPanel = new JPanel();
    private JTextField nameField;
    private JTextField yearField;

    JList<String> castList;
    JList<String> directorList;
    JList<String> producerList;
    JList<String> scriptwriterList;
    JList<String> composerList;
    JList<String> editorList;
    JList<String> designerList;

    private HashMap<String, JList<String>> crewLists = new HashMap<>();
    private HashMap<JList<String>, String> listToDescriptor = new HashMap<>();

    /**
     * Map of role descriptor -> Map of crew member key -> crew member
     */
    private HashMap<String, HashMap<String, MovieCrew>> crewMap = new HashMap<>();
    private Movie movie;

    private String oldMovieName = "";
    private int oldMovieYear = 0;

    private MouseAdapter listMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent evt) {
            @SuppressWarnings("unchecked")
            JList<String> crewList = (JList<String>) evt.getSource();
            String key = crewList.getSelectedValue();
            String roleDescriptor = listToDescriptor.get(crewList);
            if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2) {
                // double click to update track
                MovieCrew crew = crewMap.get(roleDescriptor).get(key);
                handleOpenDialog(crew);
            }
            if (SwingUtilities.isRightMouseButton(evt)) {
                // right click to delete track
                MovieCrew crew = crewMap.get(roleDescriptor).get(key);
                removeCrewMember(crew);
            }
        }
    };

    public static void main(String[] args) {
        new MovieDialog(null);
    }

    /**
     * @wbp.parser.constructor
     */
    public MovieDialog(Frame parent) {
        this(parent, null);
    }

    public MovieDialog(Frame parent, Movie data) {
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
        // create map from descriptor to list
        crewLists.put(MovieCrew.CAST, castList);
        crewLists.put(MovieCrew.COMPOSER, composerList);
        crewLists.put(MovieCrew.COSTUME_DESIGNER, designerList);
        crewLists.put(MovieCrew.DIRECTOR, directorList);
        crewLists.put(MovieCrew.EDITOR, editorList);
        crewLists.put(MovieCrew.PRODUCER, producerList);
        crewLists.put(MovieCrew.SCRIPTWRITER, scriptwriterList);
        // create map from list to descriptor
        listToDescriptor.put(castList, MovieCrew.CAST);
        listToDescriptor.put(composerList, MovieCrew.COMPOSER);
        listToDescriptor.put(designerList, MovieCrew.COSTUME_DESIGNER);
        listToDescriptor.put(directorList, MovieCrew.DIRECTOR);
        listToDescriptor.put(editorList, MovieCrew.EDITOR);
        listToDescriptor.put(producerList, MovieCrew.PRODUCER);
        listToDescriptor.put(scriptwriterList, MovieCrew.SCRIPTWRITER);
        // initialize crew member map
        for (String roleDescriptor : MovieCrew.ROLES) {
            crewMap.put(roleDescriptor, new HashMap<String, MovieCrew>());
        }
    }

    private void handleOpenDialog(MovieCrew oldCrew) {
        // create added crew members map for checking
        HashMap<String, ArrayList<MovieCrew>> addedMembers = new HashMap<>();
        for (String roleDescriptor : crewMap.keySet()) {
            HashMap<String, MovieCrew> roleMembers = crewMap.get(roleDescriptor);
            addedMembers.put(roleDescriptor, new ArrayList<MovieCrew>(roleMembers.values()));
        }
        // if updating, remove old member
        if (oldCrew != null) {
            String roleDescriptor = oldCrew.getRole();
            addedMembers.get(roleDescriptor).remove(oldCrew);
        }
        MovieCrewDialog dialog = new MovieCrewDialog(this, addedMembers, oldCrew);
        MovieCrew newCrew = dialog.getParsedData();
        // if new crew member was submitted
        if (newCrew != null) {
            // if updating, remove old crew member to be replaced
            if (oldCrew != null) {
                removeCrewMember(oldCrew);
            }
            addCrewMember(newCrew);
        }
    }

    private void handleSubmit() {
        movie = parseFields();
        if (movie == null) {
            return;
        }
        if (isUpdating) {
            if (updateToDatabase(movie)) {
                System.out.println("Movie dialog: updated to DB.");
                dispose();
            }
        } else {
            if (insertToDatabase(movie)) {
                System.out.println("Movie dialog: inserted to DB.");
                dispose();
            }
        }
    }

    private void addCrewMember(MovieCrew crew) {
        String role = crew.getRole();
        String key = crew.toString();
        // add to crew map
        crewMap.get(role).put(key, crew);
        // add to JLists
        DefaultListModel<String> model = (DefaultListModel<String>) crewLists.get(role).getModel();
        model.addElement(key);
        System.out.println("Movie dialog: " + key + " [" + role + "] added.");
    }

    private void removeCrewMember(MovieCrew crew) {
        String role = crew.getRole();
        String key = crew.toString();
        // remove from crew map
        crewMap.get(role).remove(key);
        // remove from JLists
        DefaultListModel<String> model = (DefaultListModel<String>) crewLists.get(role).getModel();
        model.removeElement(key);
        System.out.println("Movie dialog: " + key + " [" + role + "] removed.");
    }

    @Override
    public void createGUI() {
        setResizable(false);
        setBounds(100, 100, 450, 780);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 180, 240 };
        gbl_contentPanel.rowHeights = new int[] { 26, 26, 72, 72, 72, 72, 72, 72, 72, 26 };
        gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0 };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0 };
        contentPanel.setLayout(gbl_contentPanel);

        JLabel nameLabel = new JLabel("Movie name");
        nameLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.anchor = GridBagConstraints.WEST;
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.gridx = 0;
        gbc_nameLabel.gridy = 0;
        contentPanel.add(nameLabel, gbc_nameLabel);

        nameField = new JTextField();
        GridBagConstraints gbc_nameField = new GridBagConstraints();
        gbc_nameField.insets = new Insets(0, 0, 5, 0);
        gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameField.gridx = 1;
        gbc_nameField.gridy = 0;
        contentPanel.add(nameField, gbc_nameField);
        nameField.setColumns(10);

        JButton addCastButton = new JButton("Add Movie Cast Member");
        addCastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                handleOpenDialog(null);
            }
        });
        addCastButton.setMnemonic('a');
        GridBagConstraints gbc_addCastButton = new GridBagConstraints();
        gbc_addCastButton.gridwidth = 2;
        gbc_addCastButton.insets = new Insets(0, 0, 5, 0);
        gbc_addCastButton.gridx = 0;
        gbc_addCastButton.gridy = 1;
        contentPanel.add(addCastButton, gbc_addCastButton);

        JLabel directorLabel = new JLabel("Director(s)");
        GridBagConstraints gbc_directorLabel = new GridBagConstraints();
        gbc_directorLabel.anchor = GridBagConstraints.WEST;
        gbc_directorLabel.insets = new Insets(0, 0, 5, 5);
        gbc_directorLabel.gridx = 0;
        gbc_directorLabel.gridy = 2;
        contentPanel.add(directorLabel, gbc_directorLabel);

        directorList = new JList<String>(new DefaultListModel<String>());
        directorList.addMouseListener(listMouseAdapter);
        GridBagConstraints gbc_directorList = new GridBagConstraints();
        gbc_directorList.insets = new Insets(0, 0, 5, 0);
        gbc_directorList.fill = GridBagConstraints.BOTH;
        gbc_directorList.gridx = 1;
        gbc_directorList.gridy = 2;
        contentPanel.add(directorList, gbc_directorList);

        JLabel scriptwriterLabel = new JLabel("Script writer(s)");
        GridBagConstraints gbc_scriptwriterLabel = new GridBagConstraints();
        gbc_scriptwriterLabel.anchor = GridBagConstraints.WEST;
        gbc_scriptwriterLabel.insets = new Insets(0, 0, 5, 5);
        gbc_scriptwriterLabel.gridx = 0;
        gbc_scriptwriterLabel.gridy = 3;
        contentPanel.add(scriptwriterLabel, gbc_scriptwriterLabel);

        scriptwriterList = new JList<String>(new DefaultListModel<String>());
        scriptwriterList.addMouseListener(listMouseAdapter);
        GridBagConstraints gbc_scriptwriterList = new GridBagConstraints();
        gbc_scriptwriterList.insets = new Insets(0, 0, 5, 0);
        gbc_scriptwriterList.fill = GridBagConstraints.BOTH;
        gbc_scriptwriterList.gridx = 1;
        gbc_scriptwriterList.gridy = 3;
        contentPanel.add(scriptwriterList, gbc_scriptwriterList);

        JLabel producerLabel = new JLabel("Producer(s)");
        GridBagConstraints gbc_producerLabel = new GridBagConstraints();
        gbc_producerLabel.anchor = GridBagConstraints.WEST;
        gbc_producerLabel.insets = new Insets(0, 0, 5, 5);
        gbc_producerLabel.gridx = 0;
        gbc_producerLabel.gridy = 4;
        contentPanel.add(producerLabel, gbc_producerLabel);

        producerList = new JList<String>(new DefaultListModel<String>());
        producerList.addMouseListener(listMouseAdapter);
        GridBagConstraints gbc_producerList = new GridBagConstraints();
        gbc_producerList.insets = new Insets(0, 0, 5, 0);
        gbc_producerList.fill = GridBagConstraints.BOTH;
        gbc_producerList.gridx = 1;
        gbc_producerList.gridy = 4;
        contentPanel.add(producerList, gbc_producerList);

        JLabel composerLabel = new JLabel("Composer(s)");
        GridBagConstraints gbc_composerLabel = new GridBagConstraints();
        gbc_composerLabel.anchor = GridBagConstraints.WEST;
        gbc_composerLabel.insets = new Insets(0, 0, 5, 5);
        gbc_composerLabel.gridx = 0;
        gbc_composerLabel.gridy = 5;
        contentPanel.add(composerLabel, gbc_composerLabel);

        composerList = new JList<String>(new DefaultListModel<String>());
        composerList.addMouseListener(listMouseAdapter);
        GridBagConstraints gbc_composerList = new GridBagConstraints();
        gbc_composerList.insets = new Insets(0, 0, 5, 0);
        gbc_composerList.fill = GridBagConstraints.BOTH;
        gbc_composerList.gridx = 1;
        gbc_composerList.gridy = 5;
        contentPanel.add(composerList, gbc_composerList);

        JLabel editorLabel = new JLabel("Editor(s)");
        GridBagConstraints gbc_editorLabel = new GridBagConstraints();
        gbc_editorLabel.anchor = GridBagConstraints.WEST;
        gbc_editorLabel.insets = new Insets(0, 0, 5, 5);
        gbc_editorLabel.gridx = 0;
        gbc_editorLabel.gridy = 6;
        contentPanel.add(editorLabel, gbc_editorLabel);

        editorList = new JList<String>(new DefaultListModel<String>());
        editorList.addMouseListener(listMouseAdapter);
        GridBagConstraints gbc_editorList = new GridBagConstraints();
        gbc_editorList.insets = new Insets(0, 0, 5, 0);
        gbc_editorList.fill = GridBagConstraints.BOTH;
        gbc_editorList.gridx = 1;
        gbc_editorList.gridy = 6;
        contentPanel.add(editorList, gbc_editorList);

        JLabel designerLabel = new JLabel("Costume designer(s)");
        GridBagConstraints gbc_designerLabel = new GridBagConstraints();
        gbc_designerLabel.anchor = GridBagConstraints.WEST;
        gbc_designerLabel.insets = new Insets(0, 0, 5, 5);
        gbc_designerLabel.gridx = 0;
        gbc_designerLabel.gridy = 7;
        contentPanel.add(designerLabel, gbc_designerLabel);

        designerList = new JList<String>(new DefaultListModel<String>());
        designerList.addMouseListener(listMouseAdapter);
        GridBagConstraints gbc_designerList = new GridBagConstraints();
        gbc_designerList.insets = new Insets(0, 0, 5, 0);
        gbc_designerList.fill = GridBagConstraints.BOTH;
        gbc_designerList.gridx = 1;
        gbc_designerList.gridy = 7;
        contentPanel.add(designerList, gbc_designerList);

        JLabel castLabel = new JLabel("Cast member(s)");
        GridBagConstraints gbc_castLabel = new GridBagConstraints();
        gbc_castLabel.anchor = GridBagConstraints.WEST;
        gbc_castLabel.insets = new Insets(0, 0, 5, 5);
        gbc_castLabel.gridx = 0;
        gbc_castLabel.gridy = 8;
        contentPanel.add(castLabel, gbc_castLabel);

        castList = new JList<String>(new DefaultListModel<String>());
        castList.addMouseListener(listMouseAdapter);
        JScrollPane castScrollPane = new JScrollPane();
        GridBagConstraints gbc_castScrollPane = new GridBagConstraints();
        gbc_castScrollPane.fill = GridBagConstraints.BOTH;
        gbc_castScrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_castScrollPane.gridx = 1;
        gbc_castScrollPane.gridy = 8;
        contentPanel.add(castScrollPane, gbc_castScrollPane);
        castScrollPane.setViewportView(castList);

        JLabel yearLabel = new JLabel("Year of release");
        yearLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_yearLabel = new GridBagConstraints();
        gbc_yearLabel.anchor = GridBagConstraints.WEST;
        gbc_yearLabel.insets = new Insets(0, 0, 0, 5);
        gbc_yearLabel.gridx = 0;
        gbc_yearLabel.gridy = 9;
        contentPanel.add(yearLabel, gbc_yearLabel);

        yearField = new JTextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 1;
        gbc_textField.gridy = 9;
        contentPanel.add(yearField, gbc_textField);
        yearField.setColumns(10);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
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

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this);
    }

    @Override
    public void populateFields(Object data) {
        Movie movie = (Movie) data;
        oldMovieName = movie.getName();
        oldMovieYear = movie.getYear();
        // populate text fields
        nameField.setText(oldMovieName);
        yearField.setText(Integer.toString(oldMovieYear));
        // populate lists with crew members
        for (MovieCrew member : movie.getCrewMembers()) {
            addCrewMember(member);
        }
    }

    @Override
    public Movie parseFields() {
        if (checkMandatoryFields()) {
            // check if any crew members were added for each role
            for (String role : crewMap.keySet()) {
                HashMap<String, MovieCrew> selectCrewMap = crewMap.get(role);
                if (selectCrewMap == null || selectCrewMap.size() == 0) {
                    String error = "At least one member of role " + role + " must be added.";
                    HomeLibrary.showSubmitErrorMessageBox(this, error);
                    return null;
                }
            }
            // combine all crew members
            ArrayList<MovieCrew> allMembers = new ArrayList<>();
            for (HashMap<String, MovieCrew> members : crewMap.values()) {
                allMembers.addAll(members.values());
            }
            try {
                Movie movie = new Movie.Builder() //
                        .setPrimaryKey(oldMovieName, oldMovieYear) //
                        .setName(nameField.getText()) //
                        .setYear(yearField.getText()) //
                        .addCrewMembers(allMembers) //
                        .create();
                return movie;
            } catch (NumberFormatException e) {
                HomeLibrary.showSubmitErrorMessageBox(this, e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Movie getParsedData() {
        return movie;
    }

    @Override
    public boolean insertToDatabase(Object data) {
        Movie movie = (Movie) data;
        Connection connection = DatabaseDriver.connectToDatabase();
        try {
            DatabaseInserter.insertMovie(connection, movie);
            HomeLibrary.showSubmitMessageBox(this, HomeLibrary.INSERT_DB_SUCCESS_MSG);
            return true;
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
        boolean success = false;
        Movie movie = (Movie) data;
        Connection connection = DatabaseDriver.connectToDatabase();
        try {
            if (success = DatabaseUpdater.updateMovie(connection, movie)) {
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
        return success;
    }

}
