package com.hl.gui;

import javax.swing.*;
import com.hl.gui.book.BookDialog;
import com.hl.gui.movie.MovieDialog;
import com.hl.gui.music.MusicDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeLibrary extends JFrame {

    public static final int INSERT_RECORD = 0;
    public static final int UPDATE_RECORD = 1;

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 9186575314197946439L;

    public static void main(String args[]) {
        HomeLibrary HomeLibraryForm = new HomeLibrary();
        HomeLibraryForm.setResizable(false);
        HomeLibraryForm.setTitle("Home Library");
        HomeLibraryForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HomeLibraryForm.setLocationRelativeTo(null);
        HomeLibraryForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HomeLibraryForm.pack();
        HomeLibraryForm.setVisible(true);
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
        dataMenu.add(updateDataItem);

        JMenuItem removeDataItem = new JMenuItem("Remove");
        removeDataItem.addActionListener(new ActionListener() {
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

        JButton dataButton = new JButton("Data");
        panel.add(dataButton);
        dataButton.setVerticalAlignment(SwingConstants.BOTTOM);

        JButton viewButton = new JButton("View");
        panel.add(viewButton);

        JButton reportButton = new JButton("Report");
        panel.add(reportButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        exitButton.setMnemonic('x');
        panel.add(exitButton);

    }

    protected void handleQuery() {
        try {
            QueryDialog dialog = new QueryDialog(this);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void handleRemoveItem() {
        RemoveDialog dialog = new RemoveDialog(this);
    }

    private void handleInsertBook() {
        try {
            BookDialog dialog = new BookDialog(this, INSERT_RECORD);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleInsertMusic() {
        try {
            MusicDialog dialog = new MusicDialog(this, INSERT_RECORD);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleInsertMovie() {
        try {
            MovieDialog dialog = new MovieDialog(this, INSERT_RECORD);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleReport(int reportNumber) {
        ReportDialog dialog = new ReportDialog(this, reportNumber);
    }

}
