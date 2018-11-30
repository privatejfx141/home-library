package com.hl.gui.data.movie;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.generics.Roles;
import com.hl.gui.HomeLibrary;
import com.hl.gui.data.HomeLibraryProductDialog;
import com.hl.record.movie.MovieCrew;

import javax.swing.JLabel;
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
import java.awt.Dialog;

public class MovieCrewDialog extends HomeLibraryProductDialog {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -9130390966287303839L;

    private final JPanel contentPanel = new JPanel();
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField middleNameField;
    private JComboBox<String> genderBox;
    private JComboBox<String> roleBox;
    private JCheckBox awardCheckBox;

    private MovieCrew crew;
    private int crewId = -1;

    /**
     * Map of role descriptor -> crew members
     */
    private HashMap<String, ArrayList<MovieCrew>> addedMembers;

    public MovieCrewDialog(Dialog parent) {
        this(parent, new HashMap<>(), null);
    }

    public MovieCrewDialog(Dialog parent, MovieCrew data) {
        this(parent, new HashMap<>(), data);
    }

    public MovieCrewDialog(Dialog parent, HashMap<String, ArrayList<MovieCrew>> addedMembers) {
        this(parent, addedMembers, null);
    }

    /**
     * @wbp.parser.constructor
     */
    public MovieCrewDialog(Dialog parent, HashMap<String, ArrayList<MovieCrew>> addedMembers, MovieCrew data) {
        super(parent, data);
        initialize();
        this.addedMembers = addedMembers;
        if (data != null) {
            populateFields(data);
        }
        setVisible(true);
    }

    private void initialize() {
        createGUI();
        addMandatoryField(firstNameField);
        addMandatoryField(lastNameField);
    }

    @Override
    public void createGUI() {
        setTitle("Submit Movie Crew Member");
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

        ArrayList<String> rolesList = new ArrayList<>();
        for (String role : MovieCrew.ROLES) {
            rolesList.add(role);
        }

        roleBox = new JComboBox<String>();
        roleLabel.setLabelFor(roleBox);
        roleBox.setModel(new DefaultComboBoxModel<String>(rolesList.toArray(new String[0])));
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
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this);
    }

    protected void handleSubmit() {
        crew = parseFields();
        if (crew == null) {
            return;
        } else {
            // check if crew member was already added
            String role = crew.getRole();
            for (MovieCrew addedMember : addedMembers.get(role)) {
                if (crew.compare(addedMember)) {
                    String error = "Crew member already exists.";
                    HomeLibrary.showSubmitErrorMessageBox(this, error);
                    return;
                }
            }
            System.out.println("Movie crew dialog: " + crew + " [" + role + "] submitted.");
            dispose();
        }
    }

    @Override
    public MovieCrew parseFields() {
        if (checkMandatoryFields()) {
            String role = String.valueOf(roleBox.getSelectedItem());
            ArrayList<MovieCrew> addedSelectedMembers = addedMembers.get(role);
            int crewSize = addedSelectedMembers.size();
            String gender = (String) genderBox.getSelectedItem();
            boolean award = awardCheckBox.isSelected();
            String castRole = Roles.CAST.toString();
            if (role.equalsIgnoreCase(castRole) && crewSize >= 10) {
                String error = "Only at most 10 members of the role Cast can be added.";
                HomeLibrary.showSubmitErrorMessageBox(this, error);
                return null;
            } else if (!role.equalsIgnoreCase(castRole) && crewSize >= 3) {
                String error = "Only at most 3 members of the role " + role + " can be added.";
                HomeLibrary.showSubmitErrorMessageBox(this, error);
                return null;
            }
            MovieCrew member = (MovieCrew) new MovieCrew.Builder() //
                    .setRole(role) //
                    .hasAward(award) //
                    .setGender(gender) //
                    .setFirstName(firstNameField.getText()) //
                    .setMiddleName(middleNameField.getText()) //
                    .setLastName(lastNameField.getText()) //
                    .setId(crewId) //
                    .create();
            return member;
        }
        return null;
    }

    @Override
    public MovieCrew getParsedData() {
        return crew;
    }

    @Override
    public void populateFields(Object data) {
        MovieCrew member = (MovieCrew) data;
        crewId = member.getId();
        setTitle(getTitle() + " (ID: " + crewId + ")");
        firstNameField.setText(member.getFirstName());
        middleNameField.setText(member.getMiddleName());
        lastNameField.setText(member.getLastName());
        genderBox.setSelectedItem(member.getGender());
        roleBox.setSelectedItem(member.getRole());
        awardCheckBox.setSelected(member.getAward());
    }

    @Override
    public boolean insertToDatabase(Object data) {
        // nothing to do here
        return true;
    }

    @Override
    public boolean updateToDatabase(Object data) {
        // nothing to do here
        return true;
    }

}
