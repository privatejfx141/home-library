package com.hl.gui.data.book;

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
import com.hl.exceptions.DatabaseInsertException;
import com.hl.gui.HomeLibrary;
import com.hl.gui.data.HomeLibraryProductDialog;
import com.hl.record.Person;
import com.hl.record.book.Book;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JList;

public class BookDialog extends HomeLibraryProductDialog {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -8642016230269365480L;

    private final JPanel contentPanel = new JPanel();

    private JTextField isbnField;
    private JTextField nameField;
    private JTextField publisherField;
    private JTextField editionField;
    private JTextField pagesField;
    private JTextField yearField;

    private JTextArea descriptionField;
    private JTextArea keywordField;
    private JScrollPane authorPane;

    private JList<String> authorList;
    private JButton addAuthorButton;

    private HashMap<String, Person> authorMap = new HashMap<>();
    private Book book;

    private MouseAdapter listMouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent evt) {
            String authorKey = authorList.getSelectedValue();
            if (SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2) {
                // double click to update track
                Person author = authorMap.get(authorKey);
                handleOpenDialog(author);
            }
            if (SwingUtilities.isRightMouseButton(evt)) {
                // right click to delete track
                if (authorKey != null) {
                    Person author = authorMap.get(authorKey);
                    removeAuthor(author);
                }
            }
        }
    };

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        new BookDialog(null);
    }

    /**
     * Create the dialog.
     * 
     * @wbp.parser.constructor
     */
    public BookDialog(Frame parent) {
        this(parent, null);
    }

    public BookDialog(Frame parent, Book data) {
        super(parent, data);
        initialize();
        if (data != null) {
            populateFields(data);
        }
        setVisible(true);
    }

    private void initialize() {
        createGUI();
        addMandatoryField(isbnField);
        addMandatoryField(nameField);
        addMandatoryField(publisherField);
        addMandatoryField(pagesField);
        addMandatoryField(yearField);
    }

    @Override
    public void createGUI() {
        setResizable(false);
        setBounds(100, 100, 480, 700);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 170, 260 };
        gbl_contentPanel.rowHeights = new int[] { 26, 26, 26, 26, 26, 26, 32, 100, 32, 120, 32, 100 };
        gbl_contentPanel.columnWeights = new double[] { 1.0, 1.0 };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 1.0 };
        contentPanel.setLayout(gbl_contentPanel);

        JLabel isbnLabel = new JLabel("ISBN");
        isbnLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_isbnLabel = new GridBagConstraints();
        gbc_isbnLabel.fill = GridBagConstraints.BOTH;
        gbc_isbnLabel.insets = new Insets(0, 0, 5, 5);
        gbc_isbnLabel.gridx = 0;
        gbc_isbnLabel.gridy = 0;
        contentPanel.add(isbnLabel, gbc_isbnLabel);

        isbnField = new JTextField(13);
        GridBagConstraints gbc_isbnField = new GridBagConstraints();
        gbc_isbnField.fill = GridBagConstraints.BOTH;
        gbc_isbnField.insets = new Insets(0, 0, 5, 0);
        gbc_isbnField.gridx = 1;
        gbc_isbnField.gridy = 0;
        contentPanel.add(isbnField, gbc_isbnField);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.fill = GridBagConstraints.BOTH;
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.gridx = 0;
        gbc_nameLabel.gridy = 1;
        contentPanel.add(nameLabel, gbc_nameLabel);

        nameField = new JTextField(45);
        GridBagConstraints gbc_nameField = new GridBagConstraints();
        gbc_nameField.fill = GridBagConstraints.BOTH;
        gbc_nameField.insets = new Insets(0, 0, 5, 0);
        gbc_nameField.gridx = 1;
        gbc_nameField.gridy = 1;
        contentPanel.add(nameField, gbc_nameField);
        nameField.setColumns(10);

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

        addAuthorButton = new JButton("Add Author");
        addAuthorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                handleOpenDialog(null);
            }
        });
        addAuthorButton.setMnemonic('a');
        GridBagConstraints gbc_addAuthorButton = new GridBagConstraints();
        gbc_addAuthorButton.gridwidth = 2;
        gbc_addAuthorButton.insets = new Insets(0, 0, 5, 5);
        gbc_addAuthorButton.gridx = 0;
        gbc_addAuthorButton.gridy = 6;
        contentPanel.add(addAuthorButton, gbc_addAuthorButton);

        authorPane = new JScrollPane();
        GridBagConstraints gbc_authorPane = new GridBagConstraints();
        gbc_authorPane.gridwidth = 2;
        gbc_authorPane.insets = new Insets(0, 0, 5, 0);
        gbc_authorPane.fill = GridBagConstraints.BOTH;
        gbc_authorPane.gridx = 0;
        gbc_authorPane.gridy = 7;
        contentPanel.add(authorPane, gbc_authorPane);

        authorList = new JList<String>(new DefaultListModel<String>());
        authorList.addMouseListener(listMouseListener);
        authorPane.setViewportView(authorList);

        JLabel descriptionLabel = new JLabel("Description (5000 characters max)");
        GridBagConstraints gbc_descriptionLabel = new GridBagConstraints();
        gbc_descriptionLabel.anchor = GridBagConstraints.WEST;
        gbc_descriptionLabel.gridwidth = 2;
        gbc_descriptionLabel.insets = new Insets(0, 0, 5, 0);
        gbc_descriptionLabel.gridx = 0;
        gbc_descriptionLabel.gridy = 8;
        contentPanel.add(descriptionLabel, gbc_descriptionLabel);

        JScrollPane descriptionPane = new JScrollPane();
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

        JLabel keywordLabel = new JLabel("Keywords (semicolon-separated)");
        GridBagConstraints gbc_keywordLabel = new GridBagConstraints();
        gbc_keywordLabel.anchor = GridBagConstraints.WEST;
        gbc_keywordLabel.gridwidth = 2;
        gbc_keywordLabel.insets = new Insets(0, 0, 5, 0);
        gbc_keywordLabel.gridx = 0;
        gbc_keywordLabel.gridy = 10;
        contentPanel.add(keywordLabel, gbc_keywordLabel);

        JScrollPane keywordPane = new JScrollPane();
        GridBagConstraints gbc_keywordPane = new GridBagConstraints();
        gbc_keywordPane.fill = GridBagConstraints.BOTH;
        gbc_keywordPane.gridwidth = 2;
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
        submitButton.setMnemonic('s');
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
        cancelButton.setMnemonic('c');
        cancelButton.addActionListener(cancelListener);
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this);
    }

    private void handleOpenDialog(Person oldAuthor) {
        ArrayList<Person> addedAuthors = new ArrayList<>(authorMap.values());
        // if updating, remove old author from check
        if (oldAuthor != null) {
            addedAuthors.remove(oldAuthor);
        }
        BookAuthorDialog dialog = new BookAuthorDialog(this, addedAuthors, oldAuthor);
        Person newAuthor = dialog.getParsedData();
        // if new author was submitted
        if (newAuthor != null) {
            // if updating, remove old author to be replaced
            if (oldAuthor != null) {
                removeAuthor(oldAuthor);
            }
            addAuthor(newAuthor);
        }
    }

    private void addAuthor(Person author) {
        String authorKey = author.toString();
        authorMap.put(authorKey, author);
        DefaultListModel<String> model = (DefaultListModel<String>) authorList.getModel();
        model.addElement(authorKey);
        if (authorMap.size() >= 5) {
            addAuthorButton.setEnabled(false);
        }
        System.out.println("Book dialog: " + authorKey + " added.");
    }

    private void removeAuthor(Person author) {
        String authorKey = author.toString();
        authorMap.remove(authorKey);
        DefaultListModel<String> model = (DefaultListModel<String>) authorList.getModel();
        model.removeElement(authorKey);
        if (authorMap.size() < 5) {
            addAuthorButton.setEnabled(true);
        }
        System.out.println("Book dialog: " + authorKey + " removed.");
    }

    private void handleSubmit() {
        // parse field to get book data
        Book book = (Book) parseFields();
        if (book == null) {
            return;
        }
        // insert into database
        if (insertToDatabase(book)) {
            dispose();
        }
    }

    @Override
    public void populateFields(Object data) {
        book = (Book) data;
        String isbn = book.getIsbn();
        setTitle(getTitle() + " (ISBN: " + isbn + ")");
        isbnField.setText(isbn);
        isbnField.setEnabled(false);
        nameField.setText(book.getTitle());
        publisherField.setText(book.getPublisher());
        editionField.setText(Integer.toString(book.getEditionNumber()));
        pagesField.setText(Integer.toString(book.getNumberOfPages()));
        yearField.setText(Integer.toString(book.getYearOfPublication()));
        descriptionField.setText(book.getDescription());
    }

    @Override
    public Object parseFields() {
        // check mandatory fields
        if (checkMandatoryFields()) {
            // check if author was added
            if (authorMap.isEmpty()) {
                String error = "At least one author must be added.";
                HomeLibrary.showSubmitErrorMessageBox(this, error);
                return null;
            }
            // parse keywords
            String keywordText = keywordField.getText().trim();
            List<String> keywords = new ArrayList<String>(Arrays.asList(keywordText.split("\\s*;\\s*")));
            if (keywordText.isEmpty()) {
                keywords.clear();
            }
            try {
                // create book
                Book book = new Book.Builder() //
                        .setIsbn(isbnField.getText()) //
                        .setTitle(nameField.getText()) //
                        .setPublisher(publisherField.getText()) //
                        .setEditionNumber(editionField.getText()) //
                        .setNumberOfPages(pagesField.getText()) //
                        .setYearOfPublication(yearField.getText()) //
                        .setDescription(descriptionField.getText()) //
                        .addAuthors(authorMap.values()) //
                        .addKeywords(keywords) //
                        .create();
                return book;
            } catch (NumberFormatException e) {
                HomeLibrary.showSubmitErrorMessageBox(this, e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Object getParsedData() {
        return book;
    }

    @Override
    public boolean insertToDatabase(Object data) {
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

    @Override
    public boolean updateToDatabase(Object data) {
        // TODO Auto-generated method stub
        return false;
    }

}
