package com.c43.homelib;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalityType;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import java.awt.GridLayout;
import javax.swing.border.EtchedBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class BookDialog extends JDialog {
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
    private JPanel descriptionPanel;
    private JLabel keywordLabel;
    private JPanel panel;
    private JTextArea textArea;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            BookDialog dialog = new BookDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public BookDialog() {
        setResizable(false);
        setTitle("Insert Book");
        setBounds(100, 100, 480, 720);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 220, 260 };
        gbl_contentPanel.rowHeights = new int[] {26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26};
        gbl_contentPanel.columnWeights = new double[] { 1.0, 1.0 };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0,
                1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0 };
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel nameLabel = new JLabel("Name");
            GridBagConstraints gbc_nameLabel = new GridBagConstraints();
            gbc_nameLabel.fill = GridBagConstraints.BOTH;
            gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
            gbc_nameLabel.gridx = 0;
            gbc_nameLabel.gridy = 0;
            contentPanel.add(nameLabel, gbc_nameLabel);
        }
        {
            nameField = new JTextField();
            GridBagConstraints gbc_nameField = new GridBagConstraints();
            gbc_nameField.fill = GridBagConstraints.BOTH;
            gbc_nameField.insets = new Insets(0, 0, 5, 0);
            gbc_nameField.gridx = 1;
            gbc_nameField.gridy = 0;
            contentPanel.add(nameField, gbc_nameField);
            nameField.setColumns(10);
        }
        {
            JLabel isbnLabel = new JLabel("ISBN");
            GridBagConstraints gbc_isbnLabel = new GridBagConstraints();
            gbc_isbnLabel.fill = GridBagConstraints.BOTH;
            gbc_isbnLabel.insets = new Insets(0, 0, 5, 5);
            gbc_isbnLabel.gridx = 0;
            gbc_isbnLabel.gridy = 1;
            contentPanel.add(isbnLabel, gbc_isbnLabel);
        }
        {
            isbnField = new JTextField();
            GridBagConstraints gbc_isbnField = new GridBagConstraints();
            gbc_isbnField.fill = GridBagConstraints.BOTH;
            gbc_isbnField.insets = new Insets(0, 0, 5, 0);
            gbc_isbnField.gridx = 1;
            gbc_isbnField.gridy = 1;
            contentPanel.add(isbnField, gbc_isbnField);
            isbnField.setColumns(10);
        }
        {
            JLabel publisherLabel = new JLabel("Publisher");
            GridBagConstraints gbc_publisherLabel = new GridBagConstraints();
            gbc_publisherLabel.fill = GridBagConstraints.BOTH;
            gbc_publisherLabel.insets = new Insets(0, 0, 5, 5);
            gbc_publisherLabel.gridx = 0;
            gbc_publisherLabel.gridy = 2;
            contentPanel.add(publisherLabel, gbc_publisherLabel);
        }
        {
            publisherField = new JTextField();
            GridBagConstraints gbc_publisherField = new GridBagConstraints();
            gbc_publisherField.fill = GridBagConstraints.BOTH;
            gbc_publisherField.insets = new Insets(0, 0, 5, 0);
            gbc_publisherField.gridx = 1;
            gbc_publisherField.gridy = 2;
            contentPanel.add(publisherField, gbc_publisherField);
            publisherField.setColumns(10);
        }
        {
            JLabel editionLabel = new JLabel("Edition Number");
            GridBagConstraints gbc_editionLabel = new GridBagConstraints();
            gbc_editionLabel.fill = GridBagConstraints.BOTH;
            gbc_editionLabel.insets = new Insets(0, 0, 5, 5);
            gbc_editionLabel.gridx = 0;
            gbc_editionLabel.gridy = 3;
            contentPanel.add(editionLabel, gbc_editionLabel);
        }
        {
            editionField = new JTextField();
            GridBagConstraints gbc_editionField = new GridBagConstraints();
            gbc_editionField.fill = GridBagConstraints.BOTH;
            gbc_editionField.insets = new Insets(0, 0, 5, 0);
            gbc_editionField.gridx = 1;
            gbc_editionField.gridy = 3;
            contentPanel.add(editionField, gbc_editionField);
            editionField.setColumns(10);
        }
        {
            JLabel pagesLabel = new JLabel("Number of Pages");
            GridBagConstraints gbc_pagesLabel = new GridBagConstraints();
            gbc_pagesLabel.fill = GridBagConstraints.BOTH;
            gbc_pagesLabel.insets = new Insets(0, 0, 5, 5);
            gbc_pagesLabel.gridx = 0;
            gbc_pagesLabel.gridy = 4;
            contentPanel.add(pagesLabel, gbc_pagesLabel);
        }
        {
            pagesField = new JTextField();
            GridBagConstraints gbc_pagesField = new GridBagConstraints();
            gbc_pagesField.fill = GridBagConstraints.BOTH;
            gbc_pagesField.insets = new Insets(0, 0, 5, 0);
            gbc_pagesField.gridx = 1;
            gbc_pagesField.gridy = 4;
            contentPanel.add(pagesField, gbc_pagesField);
            pagesField.setColumns(10);
        }
        {
            JLabel yearLabel = new JLabel("Publication Year");
            yearLabel.setHorizontalAlignment(SwingConstants.LEFT);
            GridBagConstraints gbc_yearLabel = new GridBagConstraints();
            gbc_yearLabel.fill = GridBagConstraints.BOTH;
            gbc_yearLabel.insets = new Insets(0, 0, 5, 5);
            gbc_yearLabel.gridx = 0;
            gbc_yearLabel.gridy = 5;
            contentPanel.add(yearLabel, gbc_yearLabel);
        }
        {
            yearField = new JTextField();
            GridBagConstraints gbc_yearField = new GridBagConstraints();
            gbc_yearField.insets = new Insets(0, 0, 5, 0);
            gbc_yearField.fill = GridBagConstraints.BOTH;
            gbc_yearField.gridx = 1;
            gbc_yearField.gridy = 5;
            contentPanel.add(yearField, gbc_yearField);
            yearField.setColumns(10);
        }
        {
            JLabel authorLabel = new JLabel("Author(s) (First Name, Middle Name, Surname)");
            authorLabel.setHorizontalAlignment(SwingConstants.LEFT);
            GridBagConstraints gbc_authorLabel = new GridBagConstraints();
            gbc_authorLabel.insets = new Insets(0, 0, 5, 0);
            gbc_authorLabel.anchor = GridBagConstraints.WEST;
            gbc_authorLabel.gridwidth = 2;
            gbc_authorLabel.gridx = 0;
            gbc_authorLabel.gridy = 6;
            contentPanel.add(authorLabel, gbc_authorLabel);
        }
        {
            JPanel author1Panel = new JPanel();
            GridBagConstraints gbc_author1Panel = new GridBagConstraints();
            gbc_author1Panel.gridheight = 5;
            gbc_author1Panel.insets = new Insets(0, 0, 5, 0);
            gbc_author1Panel.gridwidth = 2;
            gbc_author1Panel.fill = GridBagConstraints.BOTH;
            gbc_author1Panel.gridx = 0;
            gbc_author1Panel.gridy = 7;
            contentPanel.add(author1Panel, gbc_author1Panel);
            author1Panel.setLayout(new GridLayout(5, 3, 3, 3));
            {
                author1FirstNameField = new JTextField();
                author1Panel.add(author1FirstNameField);
                author1FirstNameField.setColumns(10);
            }
            {
                author1MiddleNameField = new JTextField();
                author1Panel.add(author1MiddleNameField);
                author1MiddleNameField.setColumns(10);
            }
            {
                author1SurnameField = new JTextField();
                author1Panel.add(author1SurnameField);
                author1SurnameField.setColumns(10);
            }
            {
                author2FirstNameField = new JTextField();
                author1Panel.add(author2FirstNameField);
                author2FirstNameField.setColumns(10);
            }
            {
                author2MiddleNameField = new JTextField();
                author1Panel.add(author2MiddleNameField);
                author2MiddleNameField.setColumns(10);
            }
            {
                author2SurnameField = new JTextField();
                author1Panel.add(author2SurnameField);
                author2SurnameField.setColumns(10);
            }
            {
                author3FirstNameField = new JTextField();
                author1Panel.add(author3FirstNameField);
                author3FirstNameField.setColumns(10);
            }
            {
                author3MiddleNameField = new JTextField();
                author1Panel.add(author3MiddleNameField);
                author3MiddleNameField.setColumns(10);
            }
            {
                author3SurnameField = new JTextField();
                author1Panel.add(author3SurnameField);
                author3SurnameField.setColumns(10);
            }
            {
                author4FirstNameField = new JTextField();
                author1Panel.add(author4FirstNameField);
                author4FirstNameField.setColumns(10);
            }
            {
                author4MiddleNameField = new JTextField();
                author1Panel.add(author4MiddleNameField);
                author4MiddleNameField.setColumns(10);
            }
            {
                author4SurnameField = new JTextField();
                author1Panel.add(author4SurnameField);
                author4SurnameField.setColumns(10);
            }
            {
                author5FirstNameField = new JTextField();
                author1Panel.add(author5FirstNameField);
                author5FirstNameField.setColumns(10);
            }
            {
                author5MiddleNameField = new JTextField();
                author1Panel.add(author5MiddleNameField);
                author5MiddleNameField.setColumns(10);
            }
            {
                author5SurnameField = new JTextField();
                author1Panel.add(author5SurnameField);
                author5SurnameField.setColumns(10);
            }
        }
        {
            JLabel descriptionLabel = new JLabel("Description (5000 characters max)");
            GridBagConstraints gbc_descriptionLabel = new GridBagConstraints();
            gbc_descriptionLabel.anchor = GridBagConstraints.WEST;
            gbc_descriptionLabel.gridwidth = 2;
            gbc_descriptionLabel.insets = new Insets(0, 0, 5, 0);
            gbc_descriptionLabel.gridx = 0;
            gbc_descriptionLabel.gridy = 12;
            contentPanel.add(descriptionLabel, gbc_descriptionLabel);
        }
        {
            descriptionPanel = new JPanel();
            descriptionPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
            GridBagConstraints gbc_descriptionPanel = new GridBagConstraints();
            gbc_descriptionPanel.gridheight = 4;
            gbc_descriptionPanel.gridwidth = 2;
            gbc_descriptionPanel.insets = new Insets(0, 0, 5, 0);
            gbc_descriptionPanel.fill = GridBagConstraints.BOTH;
            gbc_descriptionPanel.gridx = 0;
            gbc_descriptionPanel.gridy = 13;
            contentPanel.add(descriptionPanel, gbc_descriptionPanel);
            descriptionPanel.setLayout(new BorderLayout(0, 0));
            {
                JTextArea descriptionText = new JTextArea();
                descriptionText.setToolTipText("5000 characters max");
                descriptionText.setTabSize(4);
                descriptionPanel.add(descriptionText);
                descriptionText.setLineWrap(true);
            }
        }
        {
            keywordLabel = new JLabel("Keywords (comma-separated)");
            GridBagConstraints gbc_keywordLabel = new GridBagConstraints();
            gbc_keywordLabel.anchor = GridBagConstraints.WEST;
            gbc_keywordLabel.gridwidth = 2;
            gbc_keywordLabel.insets = new Insets(0, 0, 5, 0);
            gbc_keywordLabel.gridx = 0;
            gbc_keywordLabel.gridy = 17;
            contentPanel.add(keywordLabel, gbc_keywordLabel);
        }
        {
            panel = new JPanel();
            panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.gridheight = 3;
            gbc_panel.gridwidth = 2;
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 18;
            contentPanel.add(panel, gbc_panel);
            panel.setLayout(new BorderLayout(0, 0));
            {
                textArea = new JTextArea();
                panel.add(textArea, BorderLayout.CENTER);
            }
        }
        {
            JPanel buttonPane = new JPanel();
            FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
            buttonPane.setLayout(fl_buttonPane);
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton submitButton = new JButton("Submit");
                submitButton.setActionCommand("OK");
                buttonPane.add(submitButton);
                getRootPane().setDefaultButton(submitButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        System.exit(0);
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }
}
