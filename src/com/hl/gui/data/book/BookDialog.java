package com.hl.gui.data.book;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseInserter;
import com.hl.exceptions.DatabaseInsertException;
import com.hl.gui.HomeLibrary;
import com.hl.record.Person;
import com.hl.record.book.Book;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;

public class BookDialog extends JDialog {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -8642016230269365480L;

    private final JPanel contentPanel = new JPanel();
    private JTextField nameField;
    private JTextField isbnField;
    private JTextField publisherField;
    private JTextField editionField;
    private JTextField pagesField;
    private JTextField yearField;

    private JTextField author1FirstNameField;
    private JTextField author1MiddleNameField;
    private JTextField author1SurnameField;
    private JTextField author2FirstNameField;
    private JTextField author2MiddleNameField;
    private JTextField author2SurnameField;
    private JTextField author3FirstNameField;
    private JTextField author3MiddleNameField;
    private JTextField author3SurnameField;
    private JTextField author4FirstNameField;
    private JTextField author4MiddleNameField;
    private JTextField author4SurnameField;
    private JTextField author5FirstNameField;
    private JTextField author5MiddleNameField;
    private JTextField author5SurnameField;

    private JLabel keywordLabel;
    private JScrollPane descriptionPane;
    private JScrollPane keywordPane;
    private JTextArea descriptionField;
    private JTextArea keywordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            BookDialog dialog = new BookDialog(null, 0);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public BookDialog(JFrame parentFrame, int mode) {
        super(parentFrame, "Insert Book", true);
        if (mode == HomeLibrary.UPDATE_RECORD) {
            setTitle("Update Book");
        }
        setResizable(false);
        setBounds(100, 100, 480, 720);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        createGUI();
    }

    private void createGUI() {
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 220, 260 };
        gbl_contentPanel.rowHeights = new int[] { 26, 26, 26, 26, 26, 26, 26, 26, 26, 152, 26, 72 };
        gbl_contentPanel.columnWeights = new double[] { 1.0, 1.0 };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0 };
        contentPanel.setLayout(gbl_contentPanel);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.fill = GridBagConstraints.BOTH;
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.gridx = 0;
        gbc_nameLabel.gridy = 0;
        contentPanel.add(nameLabel, gbc_nameLabel);

        nameField = new JTextField(45);
        GridBagConstraints gbc_nameField = new GridBagConstraints();
        gbc_nameField.fill = GridBagConstraints.BOTH;
        gbc_nameField.insets = new Insets(0, 0, 5, 0);
        gbc_nameField.gridx = 1;
        gbc_nameField.gridy = 0;
        contentPanel.add(nameField, gbc_nameField);
        nameField.setColumns(10);

        JLabel isbnLabel = new JLabel("ISBN");
        isbnLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_isbnLabel = new GridBagConstraints();
        gbc_isbnLabel.fill = GridBagConstraints.BOTH;
        gbc_isbnLabel.insets = new Insets(0, 0, 5, 5);
        gbc_isbnLabel.gridx = 0;
        gbc_isbnLabel.gridy = 1;
        contentPanel.add(isbnLabel, gbc_isbnLabel);

        isbnField = new JTextField(13);
        GridBagConstraints gbc_isbnField = new GridBagConstraints();
        gbc_isbnField.fill = GridBagConstraints.BOTH;
        gbc_isbnField.insets = new Insets(0, 0, 5, 0);
        gbc_isbnField.gridx = 1;
        gbc_isbnField.gridy = 1;
        contentPanel.add(isbnField, gbc_isbnField);

        JLabel publisherLabel = new JLabel("Publisher");
        publisherLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_publisherLabel = new GridBagConstraints();
        gbc_publisherLabel.fill = GridBagConstraints.BOTH;
        gbc_publisherLabel.insets = new Insets(0, 0, 5, 5);
        gbc_publisherLabel.gridx = 0;
        gbc_publisherLabel.gridy = 2;
        contentPanel.add(publisherLabel, gbc_publisherLabel);

        publisherField = new JTextField(45);
        GridBagConstraints gbc_publisherField = new GridBagConstraints();
        gbc_publisherField.fill = GridBagConstraints.BOTH;
        gbc_publisherField.insets = new Insets(0, 0, 5, 0);
        gbc_publisherField.gridx = 1;
        gbc_publisherField.gridy = 2;
        contentPanel.add(publisherField, gbc_publisherField);
        publisherField.setColumns(10);

        JLabel editionLabel = new JLabel("Edition Number");
        GridBagConstraints gbc_editionLabel = new GridBagConstraints();
        gbc_editionLabel.fill = GridBagConstraints.BOTH;
        gbc_editionLabel.insets = new Insets(0, 0, 5, 5);
        gbc_editionLabel.gridx = 0;
        gbc_editionLabel.gridy = 3;
        contentPanel.add(editionLabel, gbc_editionLabel);

        editionField = new JTextField();
        GridBagConstraints gbc_editionField = new GridBagConstraints();
        gbc_editionField.fill = GridBagConstraints.BOTH;
        gbc_editionField.insets = new Insets(0, 0, 5, 0);
        gbc_editionField.gridx = 1;
        gbc_editionField.gridy = 3;
        contentPanel.add(editionField, gbc_editionField);
        editionField.setColumns(10);

        JLabel pagesLabel = new JLabel("Number of Pages");
        pagesLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_pagesLabel = new GridBagConstraints();
        gbc_pagesLabel.fill = GridBagConstraints.BOTH;
        gbc_pagesLabel.insets = new Insets(0, 0, 5, 5);
        gbc_pagesLabel.gridx = 0;
        gbc_pagesLabel.gridy = 4;
        contentPanel.add(pagesLabel, gbc_pagesLabel);

        pagesField = new JTextField();
        GridBagConstraints gbc_pagesField = new GridBagConstraints();
        gbc_pagesField.fill = GridBagConstraints.BOTH;
        gbc_pagesField.insets = new Insets(0, 0, 5, 0);
        gbc_pagesField.gridx = 1;
        gbc_pagesField.gridy = 4;
        contentPanel.add(pagesField, gbc_pagesField);
        pagesField.setColumns(10);

        JLabel yearLabel = new JLabel("Publication Year");
        yearLabel.setForeground(Color.BLUE);
        yearLabel.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_yearLabel = new GridBagConstraints();
        gbc_yearLabel.fill = GridBagConstraints.BOTH;
        gbc_yearLabel.insets = new Insets(0, 0, 5, 5);
        gbc_yearLabel.gridx = 0;
        gbc_yearLabel.gridy = 5;
        contentPanel.add(yearLabel, gbc_yearLabel);

        yearField = new JTextField();
        GridBagConstraints gbc_yearField = new GridBagConstraints();
        gbc_yearField.insets = new Insets(0, 0, 5, 0);
        gbc_yearField.fill = GridBagConstraints.BOTH;
        gbc_yearField.gridx = 1;
        gbc_yearField.gridy = 5;
        contentPanel.add(yearField, gbc_yearField);
        yearField.setColumns(10);

        JLabel authorLabel = new JLabel("Author(s) (First Name, Middle Name, Surname)");
        authorLabel.setForeground(Color.BLUE);
        authorLabel.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_authorLabel = new GridBagConstraints();
        gbc_authorLabel.insets = new Insets(0, 0, 5, 0);
        gbc_authorLabel.anchor = GridBagConstraints.WEST;
        gbc_authorLabel.gridwidth = 2;
        gbc_authorLabel.gridx = 0;
        gbc_authorLabel.gridy = 6;
        contentPanel.add(authorLabel, gbc_authorLabel);

        JPanel authorPanel = new JPanel();
        GridBagConstraints gbc_authorPanel = new GridBagConstraints();
        gbc_authorPanel.insets = new Insets(0, 0, 5, 0);
        gbc_authorPanel.gridwidth = 2;
        gbc_authorPanel.fill = GridBagConstraints.BOTH;
        gbc_authorPanel.gridx = 0;
        gbc_authorPanel.gridy = 7;
        contentPanel.add(authorPanel, gbc_authorPanel);
        authorPanel.setLayout(new GridLayout(5, 3, 3, 3));

        author1FirstNameField = new JTextField();
        authorPanel.add(author1FirstNameField);
        author1FirstNameField.setColumns(10);

        author1MiddleNameField = new JTextField();
        authorPanel.add(author1MiddleNameField);
        author1MiddleNameField.setColumns(10);

        author1SurnameField = new JTextField();
        authorPanel.add(author1SurnameField);
        author1SurnameField.setColumns(10);

        author2FirstNameField = new JTextField();
        authorPanel.add(author2FirstNameField);
        author2FirstNameField.setColumns(10);

        author2MiddleNameField = new JTextField();
        authorPanel.add(author2MiddleNameField);
        author2MiddleNameField.setColumns(10);

        author2SurnameField = new JTextField();
        authorPanel.add(author2SurnameField);
        author2SurnameField.setColumns(10);

        author3FirstNameField = new JTextField();
        authorPanel.add(author3FirstNameField);
        author3FirstNameField.setColumns(10);

        author3MiddleNameField = new JTextField();
        authorPanel.add(author3MiddleNameField);
        author3MiddleNameField.setColumns(10);

        author3SurnameField = new JTextField();
        authorPanel.add(author3SurnameField);
        author3SurnameField.setColumns(10);

        author4FirstNameField = new JTextField();
        authorPanel.add(author4FirstNameField);
        author4FirstNameField.setColumns(10);

        author4MiddleNameField = new JTextField();
        authorPanel.add(author4MiddleNameField);
        author4MiddleNameField.setColumns(10);

        author4SurnameField = new JTextField();
        authorPanel.add(author4SurnameField);
        author4SurnameField.setColumns(10);

        author5FirstNameField = new JTextField();
        authorPanel.add(author5FirstNameField);
        author5FirstNameField.setColumns(10);

        author5MiddleNameField = new JTextField();
        authorPanel.add(author5MiddleNameField);
        author5MiddleNameField.setColumns(10);

        author5SurnameField = new JTextField();
        authorPanel.add(author5SurnameField);
        author5SurnameField.setColumns(10);

        JLabel descriptionLabel = new JLabel("Description (5000 characters max)");
        GridBagConstraints gbc_descriptionLabel = new GridBagConstraints();
        gbc_descriptionLabel.anchor = GridBagConstraints.WEST;
        gbc_descriptionLabel.gridwidth = 2;
        gbc_descriptionLabel.insets = new Insets(0, 0, 5, 0);
        gbc_descriptionLabel.gridx = 0;
        gbc_descriptionLabel.gridy = 8;
        contentPanel.add(descriptionLabel, gbc_descriptionLabel);

        descriptionPane = new JScrollPane();
        GridBagConstraints gbc_descriptionPane = new GridBagConstraints();
        gbc_descriptionPane.fill = GridBagConstraints.BOTH;
        gbc_descriptionPane.gridwidth = 2;
        gbc_descriptionPane.insets = new Insets(0, 0, 5, 0);
        gbc_descriptionPane.gridx = 0;
        gbc_descriptionPane.gridy = 9;
        contentPanel.add(descriptionPane, gbc_descriptionPane);

        descriptionField = new JTextArea();
        descriptionField.setLineWrap(true);
        descriptionPane.setViewportView(descriptionField);

        keywordLabel = new JLabel("Keywords (semicolon-separated)");
        GridBagConstraints gbc_keywordLabel = new GridBagConstraints();
        gbc_keywordLabel.anchor = GridBagConstraints.WEST;
        gbc_keywordLabel.gridwidth = 2;
        gbc_keywordLabel.insets = new Insets(0, 0, 5, 0);
        gbc_keywordLabel.gridx = 0;
        gbc_keywordLabel.gridy = 10;
        contentPanel.add(keywordLabel, gbc_keywordLabel);

        keywordPane = new JScrollPane();
        GridBagConstraints gbc_keywordPane = new GridBagConstraints();
        gbc_keywordPane.fill = GridBagConstraints.BOTH;
        gbc_keywordPane.gridwidth = 2;
        gbc_keywordPane.insets = new Insets(0, 0, 0, 5);
        gbc_keywordPane.gridx = 0;
        gbc_keywordPane.gridy = 11;
        contentPanel.add(keywordPane, gbc_keywordPane);

        keywordField = new JTextArea();
        keywordField.setLineWrap(true);
        keywordPane.setViewportView(keywordField);

        JPanel buttonPane = new JPanel();
        FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
        buttonPane.setLayout(fl_buttonPane);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });
        submitButton.setActionCommand("OK");
        buttonPane.add(submitButton);
        getRootPane().setDefaultButton(submitButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    private Person parseAuthor(String firstName, String middleName, String lastName) {
        if (firstName.isEmpty()) {
            String error = "Author must have a First Name.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (lastName.isEmpty()) {
            String error = "Author must have a Surname.";
            JOptionPane.showMessageDialog(this, error, "Submit Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return new Person(firstName, middleName, lastName);
    }

    private List<Person> parseAuthors() {
        List<Person> authors = new ArrayList<>();
        // parse author 1
        String firstName = author1FirstNameField.getText().trim();
        String middleName = author1MiddleNameField.getText().trim();
        String lastName = author1SurnameField.getText().trim();
        Person author = parseAuthor(firstName, middleName, lastName);
        authors.add(author);
        // parse author 2
        firstName = author2FirstNameField.getText().trim();
        middleName = author2MiddleNameField.getText().trim();
        lastName = author2SurnameField.getText().trim();
        if (!(firstName + middleName + lastName).isEmpty()) {
            author = parseAuthor(firstName, middleName, lastName);
            if ((author = parseAuthor(firstName, middleName, lastName)) == null)
                return null;
            authors.add(author);
        }
        // parse author 3
        firstName = author3FirstNameField.getText().trim();
        middleName = author3MiddleNameField.getText().trim();
        lastName = author3SurnameField.getText().trim();
        if (!(firstName + middleName + lastName).isEmpty()) {
            if ((author = parseAuthor(firstName, middleName, lastName)) == null)
                return null;
            authors.add(author);
        }
        // parse author 4
        firstName = author4FirstNameField.getText().trim();
        middleName = author4MiddleNameField.getText().trim();
        lastName = author4SurnameField.getText().trim();
        if (!(firstName + middleName + lastName).isEmpty()) {
            if ((author = parseAuthor(firstName, middleName, lastName)) == null)
                return null;
            authors.add(author);
        }
        // parse author 5
        firstName = author5FirstNameField.getText().trim();
        middleName = author5MiddleNameField.getText().trim();
        lastName = author5SurnameField.getText().trim();
        if (!(firstName + middleName + lastName).isEmpty()) {
            if ((author = parseAuthor(firstName, middleName, lastName)) == null)
                return null;
            authors.add(author);
        }
        return authors;
    }

    private void handleSubmit() {
        // parse field to get book data
        Book book = parseFields();
        if (book == null) {
            return;
        }
        // insert into database
        if (insertBook(book)) {
            dispose();
        }
    }

    private Book parseFields() {
        String isbn = isbnField.getText().trim();
        String name = nameField.getText().trim();
        String publisher = publisherField.getText().trim();
        String pagesText = pagesField.getText().trim();
        String yearText = yearField.getText().trim();
        String editionText = editionField.getText().trim();
        // check mandatory fields
        if (isbn.isEmpty() || name.isEmpty() || publisher.isEmpty() || pagesText.isEmpty() || yearText.isEmpty()) {
            HomeLibrary.showSubmitErrorMessageBox(this, HomeLibrary.MANDATORY_FIELD_MSG);
            return null;
        }
        // check fields for author
        List<Person> authors = parseAuthors();
        if (authors == null) {
            return null;
        }
        // parse number values
        int pages = -1;
        try {
            pages = Integer.parseInt(pagesText);
        } catch (NumberFormatException e) {
            String error = String.format(HomeLibrary.INTEGER_FIELD_MSG, "Number of Pages");
            HomeLibrary.showSubmitErrorMessageBox(this, error);
            return null;
        }
        int year = -1;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            String error = String.format(HomeLibrary.INTEGER_FIELD_MSG, "Year of Publication");
            HomeLibrary.showSubmitErrorMessageBox(this, error);
            return null;
        }
        int edition = -1;
        if (!editionText.isEmpty()) {
            try {
                edition = Integer.parseInt(editionText);
            } catch (NumberFormatException e) {
                String error = String.format(HomeLibrary.INTEGER_FIELD_MSG, "Edition Number");
                HomeLibrary.showSubmitErrorMessageBox(this, error);
                return null;
            }
        }
        // parse description and keywords
        String description = descriptionField.getText().trim();
        String keywordText = keywordField.getText().trim();
        List<String> keywords = new ArrayList<String>(Arrays.asList(keywordText.split("\\s*;\\s*")));
        if (keywordText.isEmpty()) {
            keywords.clear();
        }
        // build book
        return new Book(isbn, name, publisher, pages, year, edition, description, authors, keywords);
    }

    private boolean insertBook(Book book) {
        Connection connection = DatabaseDriver.connectToDatabase();
        try {
            DatabaseInserter.insertBook(connection, book);
            HomeLibrary.showSubmitMessageBox(this, HomeLibrary.INSERT_DB_SUCCESS_MSG);
            return true;
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
