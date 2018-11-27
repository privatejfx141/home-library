package com.hl.gui.data.book;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.gui.HomeLibrary;
import com.hl.gui.data.HomeLibraryProductDialog;
import com.hl.record.Person;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.awt.event.ActionEvent;

public class BookAuthorDialog extends HomeLibraryProductDialog {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 4943732248157667593L;

    private final JPanel contentPanel = new JPanel();
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField lastNameField;

    private Person author;
    private int authorId = -1;
    private Collection<Person> addedAuthors;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        Person author = new Person.Builder().setFirstName("Alex").setLastName("Doe").create();
        BookAuthorDialog dialog = new BookAuthorDialog(null, author);
        System.out.println(dialog.getParsedData());
    }

    private ActionListener submitListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSubmit();
        }
    };

    private ActionListener cancelListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            BookAuthorDialog.this.author = null;
            dispose();
        }
    };

    public BookAuthorDialog(Dialog parent) {
        this(parent, new ArrayList<>(), null);
    }

    public BookAuthorDialog(Dialog parent, Person data) {
        this(parent, new ArrayList<>(), data);
    }

    public BookAuthorDialog(Dialog parent, Collection<Person> addedAuthors) {
        this(parent, addedAuthors, null);
    }

    /**
     * @wbp.parser.constructor
     */
    public BookAuthorDialog(Dialog parent, Collection<Person> addedAuthors, Person data) {
        super(parent, data);
        initialize();
        this.addedAuthors = addedAuthors;
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

    private void handleSubmit() {
        author = parseFields();
        if (author == null) {
            return;
        } else {
            // check if author was already added
            for (Person addedAuthor : addedAuthors) {
                if (author.compare(addedAuthor)) {
                    String error = "Author already exists.";
                    HomeLibrary.showSubmitErrorMessageBox(this, error);
                    return;
                }
            }
            System.out.println("Author dialog: " + author + " submitted.");
            dispose();
        }
    }

    @Override
    public void createGUI() {
        setResizable(false);
        setTitle("Submit Book Author");
        setBounds(100, 100, 380, 240);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setLayout(new GridLayout(6, 1, 0, 4));
        {
            JLabel firstNameLabel = new JLabel("First Name");
            firstNameLabel.setForeground(Color.BLUE);
            contentPanel.add(firstNameLabel);
        }
        {
            firstNameField = new JTextField();
            contentPanel.add(firstNameField);
            firstNameField.setColumns(10);
        }
        {
            JLabel middleNameLabel = new JLabel("Middle Name");
            contentPanel.add(middleNameLabel);
        }
        {
            middleNameField = new JTextField();
            contentPanel.add(middleNameField);
            middleNameField.setColumns(10);
        }
        {
            JLabel lastNameLabel = new JLabel("Family Name");
            lastNameLabel.setForeground(Color.BLUE);
            contentPanel.add(lastNameLabel);
        }
        {
            lastNameField = new JTextField();
            contentPanel.add(lastNameField);
            lastNameField.setColumns(10);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(submitListener);
                submitButton.setMnemonic('s');
                submitButton.setActionCommand("OK");
                buttonPane.add(submitButton);
                getRootPane().setDefaultButton(submitButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(cancelListener);
                cancelButton.setMnemonic('c');
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this);
    }

    @Override
    public void populateFields(Object data) {
        author = (Person) data;
        authorId = author.getId();
        setTitle(getTitle() + " (ID: " + authorId + ")");
        firstNameField.setText(author.getFirstName());
        middleNameField.setText(author.getMiddleName());
        lastNameField.setText(author.getLastName());
    }

    @Override
    public Person parseFields() {
        if (checkMandatoryFields()) {
            Person author = new Person.Builder() //
                    .setId(authorId) //
                    .setFirstName(firstNameField.getText()) //
                    .setMiddleName(middleNameField.getText()) //
                    .setLastName(lastNameField.getText()) //
                    .create();
            return author;
        }
        return null;
    }

    @Override
    public Person getParsedData() {
        return author;
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
