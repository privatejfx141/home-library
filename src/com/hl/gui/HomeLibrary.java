package com.hl.gui;

import javax.swing.*;

import com.hl.gui.data.RemoveDialog;
import com.hl.gui.data.UpdateDialog;
import com.hl.gui.data.book.BookDialog;
import com.hl.gui.data.movie.MovieDialog;
import com.hl.gui.data.music.MusicDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeLibrary extends JFrame {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 9186575314197946439L;

    public static final String MANDATORY_FIELD_MSG = "All mandatory fields (in blue) must be filled in before submitting.";
    public static final String INTEGER_FIELD_MSG = "%s must be an integer.";
    public static final String INSERT_DB_SUCCESS_MSG = "Insertion to HL database was successful!";
    public static final String INSERT_DB_FAILURE_MSG = "Error! Insertion to HL database was not successful.";
    public static final String UPDATE_DB_SUCCESS_MSG = "Update to HL database was successful!";
    public static final String UPDATE_DB_FAILURE_MSG = "Error! Update to HL database was not successful.";

    public static void main(String args[]) {
        new HomeLibrary();
    }

    public static void showSubmitMessageBox(Component parentComponent, String message) {
        String title = "Submit Success";
        int msgType = JOptionPane.INFORMATION_MESSAGE;
        JOptionPane.showMessageDialog(parentComponent, message, title, msgType);
    }

    public static void showSubmitErrorMessageBox(Component parentComponent, String error) {
        String title = "Submit Error";
        int msgType = JOptionPane.ERROR_MESSAGE;
        JOptionPane.showMessageDialog(parentComponent, error, title, msgType);
    }

    public HomeLibrary() {
        JMenuBar menuBar = new JMenuBar();
        getContentPane().add(menuBar, BorderLayout.NORTH);

        JMenu dataMenu = new JMenu("Data");
        dataMenu.setMnemonic('d');
        menuBar.add(dataMenu);

        JMenu insertDataMenu = new JMenu("Insert");
        dataMenu.add(insertDataMenu);

        JMenuItem insertBookItem = new JMenuItem("Book");
        insertDataMenu.add(insertBookItem);

        JMenuItem insertMusicItem = new JMenuItem("Music Album");
        insertMusicItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInsertMusic();
            }
        });
        insertDataMenu.add(insertMusicItem);

        JMenuItem insertMovieItem = new JMenuItem("Movie");
        insertMovieItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInsertMovie();
            }
        });
        insertDataMenu.add(insertMovieItem);
        insertBookItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                handleInsertBook();
            }
        });

        JMenuItem updateDataItem = new JMenuItem("Update");
        updateDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                handleUpdateItem();
            }
        });
        dataMenu.add(updateDataItem);

        JMenuItem removeDataItem = new JMenuItem("Remove");
        removeDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                handleRemoveItem();
            }
        });
        dataMenu.add(removeDataItem);

        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic('v');
        menuBar.add(viewMenu);

        JMenuItem queryViewItem = new JMenuItem("Query");
        queryViewItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleQuery();
            }
        });
        viewMenu.add(queryViewItem);

        JMenu reportMenu = new JMenu("Report");
        reportMenu.setMnemonic('r');
        menuBar.add(reportMenu);

        JMenuItem reportItem1 = new JMenuItem("Author's Publications");
        reportItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReport(1);
            }
        });
        reportMenu.add(reportItem1);

        JMenuItem reportItem2 = new JMenuItem("Publication in one Year");
        reportItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReport(2);
            }
        });
        reportMenu.add(reportItem2);

        JMenuItem reportItem3 = new JMenuItem("Books with Similar Topic");
        reportItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReport(3);
            }
        });
        reportMenu.add(reportItem3);

        JMenuItem reportItem4 = new JMenuItem("Frequent Publishers");
        reportItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReport(4);
            }
        });
        reportMenu.add(reportItem4);

        JMenuItem reportItem5 = new JMenuItem("Most Popular Subjects");
        reportItem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReport(5);
            }
        });
        reportMenu.add(reportItem5);

        JMenuItem reportItem6 = new JMenuItem("Multi Skills Movie Crew");
        reportItem6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReport(6);
            }
        });
        reportMenu.add(reportItem6);

        JMenuItem reportItem7 = new JMenuItem("Award Winning Movies");
        reportItem7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReport(7);
            }
        });
        reportMenu.add(reportItem7);

        JMenuItem reportItem8 = new JMenuItem("Music with Similar Name");
        reportItem8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReport(8);
            }
        });
        reportMenu.add(reportItem8);

        JMenuItem reportItem9 = new JMenuItem("Multi Skills Music Crew");
        reportItem9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReport(9);
            }
        });
        reportMenu.add(reportItem9);

        JMenuItem reportItem10 = new JMenuItem("Similar Names");
        reportItem10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReport(10);
            }
        });
        reportMenu.add(reportItem10);

        JPanel mainPanel = new JPanel();
        FlowLayout fl_mainPanel = (FlowLayout) mainPanel.getLayout();
        fl_mainPanel.setAlignOnBaseline(true);
        fl_mainPanel.setVgap(32);
        fl_mainPanel.setHgap(32);
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        Box verticalBox = Box.createVerticalBox();
        mainPanel.add(verticalBox);

        JLabel introLabel = new JLabel("Welcome to the Home Library!");
        introLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        verticalBox.add(introLabel);
        introLabel.setVerticalAlignment(SwingConstants.TOP);
        introLabel.setFont(new Font("Tahoma", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("Select an option to begin...");
        subtitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        verticalBox.add(subtitleLabel);

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);

        JButton aboutButton = new JButton("About");
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Home Library application created by Yun Jie (Jeffrey) Li."
                        + "\nAssignments 1 and 3, CSCC43 Fall 2018.";
                int msgType = JOptionPane.INFORMATION_MESSAGE;
                JOptionPane.showMessageDialog(HomeLibrary.this, message, "About", msgType);
            }
        });
        aboutButton.setMnemonic('a');
        panel.add(aboutButton);

        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String message = "Mandatory text fields are labeled in blue."
                        + "\nTo edit an item from a JList, double-click with left mouse button."
                        + "\nTo delete an item from a JList, click with right mouse button.";
                int msgType = JOptionPane.INFORMATION_MESSAGE;
                JOptionPane.showMessageDialog(HomeLibrary.this, message, "Help", msgType);
            }
        });
        helpButton.setMnemonic('h');
        panel.add(helpButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        exitButton.setMnemonic('x');
        panel.add(exitButton);

        setResizable(false);
        setTitle("Home Library");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
        pack();
        setVisible(true);
    }

    protected void handleQuery() {
        QueryDialog dialog = new QueryDialog(this);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    protected void handleUpdateItem() {
        new UpdateDialog(this);
    }

    protected void handleRemoveItem() {
        new RemoveDialog(this);
    }

    protected void handleInsertBook() {
        new BookDialog(this);
    }

    protected void handleInsertMusic() {
        new MusicDialog(this);
    }

    protected void handleInsertMovie() {
        new MovieDialog(this);
    }

    protected void handleReport(int reportNumber) {
        new ReportDialog(this, reportNumber);
    }

}
