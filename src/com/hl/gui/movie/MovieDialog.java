package com.hl.gui.movie;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.record.movie.Movie;
import com.hl.record.movie.MovieCrew;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;

public class MovieDialog extends JDialog {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 2138888572924832525L;

    private final JPanel contentPanel = new JPanel();
    private JTextField nameField;
    private JTextField yearField;
    private HashMap<String, ArrayList<MovieCrew>> crewMembers = new HashMap<>();
    private HashMap<String, JList<String>> crewLists = new HashMap<>();
    private Movie movie = null;

    /**
     * Create the dialog.
     * 
     * @param homeLibrary
     */
    public MovieDialog(JFrame parentFrame) {
        super(parentFrame, "Insert Movie", true);
        setResizable(false);
        setTitle("Insert Movie");
        setBounds(100, 100, 450, 770);
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
            public void actionPerformed(ActionEvent e) {
                openMovieCrewDialog();
            }
        });
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

        JList<String> directorList = new JList<String>(new DefaultListModel<String>());
        crewLists.put("Director", directorList);
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

        JList<String> scriptwriterList = new JList<String>(new DefaultListModel<String>());
        crewLists.put("Scriptwriter", scriptwriterList);
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

        JList<String> producerList = new JList<String>(new DefaultListModel<String>());
        crewLists.put("Producer", producerList);
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

        JList<String> composerList = new JList<String>(new DefaultListModel<String>());
        crewLists.put("Composer", composerList);
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

        JList<String> editorList = new JList<String>(new DefaultListModel<String>());
        crewLists.put("Editor", editorList);
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

        JList<String> designerList = new JList<String>(new DefaultListModel<String>());
        crewLists.put("Costume designer", designerList);
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

        JScrollPane castScrollPane = new JScrollPane();
        GridBagConstraints gbc_castScrollPane = new GridBagConstraints();
        gbc_castScrollPane.fill = GridBagConstraints.BOTH;
        gbc_castScrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_castScrollPane.gridx = 1;
        gbc_castScrollPane.gridy = 8;
        contentPanel.add(castScrollPane, gbc_castScrollPane);
        JList<String> castList = new JList<String>(new DefaultListModel<String>());
        crewLists.put("Cast", castList);
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

    private void openMovieCrewDialog() {
        try {
            MovieCrewDialog dialog = new MovieCrewDialog(this, crewMembers);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            MovieCrew MovieCrew = dialog.getCrew();
            if (MovieCrew != null) {
                String role = MovieCrew.getRole();
                if (!crewMembers.containsKey(role)) {
                    crewMembers.put(role, new ArrayList<>());
                }
                crewMembers.get(role).add(MovieCrew);
                String name = MovieCrew.getFirstName();
                if (!MovieCrew.getMiddleName().isEmpty()) {
                    name += " " + MovieCrew.getMiddleName();
                }
                name += " " + MovieCrew.getLastName();
                JList<String> roleList = crewLists.get(role);
                ((DefaultListModel<String>) roleList.getModel()).addElement(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submit() {
        String name = nameField.getText();
        String yearText = yearField.getText();
        if (name.isEmpty() || yearText.isEmpty()) {
            String error = "All mandatory fields (in blue) must be filled in before submitting.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
        } else {
            for (String role : MovieCrew.ROLES) {
                if (crewMembers.get(role) == null || crewMembers.get(role).size() == 0) {
                    String error = "There must be at least one crew member of role " + role + ".";
                    JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            int year = -1;
            try {
                year = Integer.parseInt(yearText);
            } catch (NumberFormatException e) {
                String error = "Year of release must be an integer.";
                JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            movie = new Movie(name, year, crewMembers);
            dispose();
        }

    }

    public Movie getMovie() {
        return movie;
    }
}
