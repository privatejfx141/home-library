package com.hl.gui.movie;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.record.movie.MovieCrew;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class MovieCrewDialog extends JDialog {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -9130390966287303839L;

    private final JPanel contentPanel = new JPanel();
    private HashMap<String, ArrayList<MovieCrew>> crewMembers;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField middleNameField;
    JComboBox<String> genderBox;
    JComboBox<String> roleBox;
    JCheckBox awardCheckBox;
    MovieCrew crew;

    /**
     * Create the dialog.
     * 
     * @param parentDialog
     * @param crewMembers
     */
    public MovieCrewDialog(MovieDialog parentDialog, HashMap<String, ArrayList<MovieCrew>> crewMembers) {
        super(parentDialog, "Insert Film Crew", true);
        this.crewMembers = crewMembers;
        setResizable(false);
        setBounds(100, 100, 450, 274);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 160, 240 };
        gbl_contentPanel.rowHeights = new int[] { 26, 26, 26, 26, 26, 26 };
        gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0 };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
        contentPanel.setLayout(gbl_contentPanel);

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_firstNameLabel = new GridBagConstraints();
        gbc_firstNameLabel.fill = GridBagConstraints.BOTH;
        gbc_firstNameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_firstNameLabel.gridx = 0;
        gbc_firstNameLabel.gridy = 0;
        contentPanel.add(firstNameLabel, gbc_firstNameLabel);

        firstNameField = new JTextField();
        firstNameLabel.setLabelFor(firstNameField);
        GridBagConstraints gbc_firstNameField = new GridBagConstraints();
        gbc_firstNameField.fill = GridBagConstraints.BOTH;
        gbc_firstNameField.insets = new Insets(0, 0, 5, 0);
        gbc_firstNameField.gridx = 1;
        gbc_firstNameField.gridy = 0;
        contentPanel.add(firstNameField, gbc_firstNameField);
        firstNameField.setColumns(10);

        JLabel lblMiddleName = new JLabel("Middle Name");
        GridBagConstraints gbc_lblMiddleName = new GridBagConstraints();
        gbc_lblMiddleName.anchor = GridBagConstraints.WEST;
        gbc_lblMiddleName.insets = new Insets(0, 0, 5, 5);
        gbc_lblMiddleName.gridx = 0;
        gbc_lblMiddleName.gridy = 1;
        contentPanel.add(lblMiddleName, gbc_lblMiddleName);

        middleNameField = new JTextField();
        lblMiddleName.setLabelFor(middleNameField);
        GridBagConstraints gbc_middleNameField = new GridBagConstraints();
        gbc_middleNameField.insets = new Insets(0, 0, 5, 0);
        gbc_middleNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_middleNameField.gridx = 1;
        gbc_middleNameField.gridy = 1;
        contentPanel.add(middleNameField, gbc_middleNameField);
        middleNameField.setColumns(10);

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
        gbc_lastNameLabel.fill = GridBagConstraints.BOTH;
        gbc_lastNameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lastNameLabel.gridx = 0;
        gbc_lastNameLabel.gridy = 2;
        contentPanel.add(lastNameLabel, gbc_lastNameLabel);

        lastNameField = new JTextField();
        lastNameLabel.setLabelFor(lastNameField);
        GridBagConstraints gbc_lastNameField = new GridBagConstraints();
        gbc_lastNameField.insets = new Insets(0, 0, 5, 0);
        gbc_lastNameField.fill = GridBagConstraints.BOTH;
        gbc_lastNameField.gridx = 1;
        gbc_lastNameField.gridy = 2;
        contentPanel.add(lastNameField, gbc_lastNameField);
        lastNameField.setColumns(10);

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_genderLabel = new GridBagConstraints();
        gbc_genderLabel.anchor = GridBagConstraints.WEST;
        gbc_genderLabel.insets = new Insets(0, 0, 5, 5);
        gbc_genderLabel.gridx = 0;
        gbc_genderLabel.gridy = 3;
        contentPanel.add(genderLabel, gbc_genderLabel);

        genderBox = new JComboBox<String>();
        genderLabel.setLabelFor(genderBox);
        genderBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Male", "Female" }));
        GridBagConstraints gbc_genderBox = new GridBagConstraints();
        gbc_genderBox.insets = new Insets(0, 0, 5, 0);
        gbc_genderBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_genderBox.gridx = 1;
        gbc_genderBox.gridy = 3;
        contentPanel.add(genderBox, gbc_genderBox);

        JLabel roleLabel = new JLabel("Role");
        roleLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_roleLabel = new GridBagConstraints();
        gbc_roleLabel.anchor = GridBagConstraints.WEST;
        gbc_roleLabel.insets = new Insets(0, 0, 5, 5);
        gbc_roleLabel.gridx = 0;
        gbc_roleLabel.gridy = 4;
        contentPanel.add(roleLabel, gbc_roleLabel);

        roleBox = new JComboBox<String>();
        roleLabel.setLabelFor(roleBox);
        roleBox.setModel(new DefaultComboBoxModel<String>(MovieCrew.ROLES));
        GridBagConstraints gbc_roleBox = new GridBagConstraints();
        gbc_roleBox.insets = new Insets(0, 0, 5, 0);
        gbc_roleBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_roleBox.gridx = 1;
        gbc_roleBox.gridy = 4;
        contentPanel.add(roleBox, gbc_roleBox);

        awardCheckBox = new JCheckBox("Won award? (i.e. Oscar)");
        GridBagConstraints gbc_awardCheckBox = new GridBagConstraints();
        gbc_awardCheckBox.gridwidth = 2;
        gbc_awardCheckBox.gridx = 0;
        gbc_awardCheckBox.gridy = 5;
        contentPanel.add(awardCheckBox, gbc_awardCheckBox);

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
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    private void submit() {
        String firstName = firstNameField.getText();
        String middleName = middleNameField.getText();
        String lastName = lastNameField.getText();
        String role = (String) roleBox.getSelectedItem();
        String gender = (String) genderBox.getSelectedItem();
        boolean award = awardCheckBox.isSelected();
        if (firstName.isEmpty() || lastName.isEmpty()) {
            String error = "All mandatory fields (in blue) must be filled in before submitting.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int numCrew = 0;
            if (crewMembers.get(role) != null) {
                numCrew = crewMembers.get(role).size();
            }
            if (numCrew == 10 && role.equalsIgnoreCase("Cast")) {
                String error = "Only at most 10 members of the role Cast can be added.";
                JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            } else if (numCrew == 3 && !role.equalsIgnoreCase("Cast")) {
                String error = "Only at most 3 members of the role " + role + " can be added.";
                JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            } else {
                crew = new MovieCrew(firstName, middleName, lastName, role, gender, award);
                dispose();
            }
        }
    }

    public MovieCrew getCrew() {
        return crew;
    }
}
