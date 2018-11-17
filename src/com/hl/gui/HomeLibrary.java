package com.hl.gui;

import javax.swing.*;
import com.hl.gui.book.BookDialog;
import com.hl.gui.movie.MovieDialog;
import com.hl.gui.music.MusicDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeLibrary extends JFrame {

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
                openInsertMusicDialog();
            }
        });
        insertDataMenu.add(insertMusicItem);

        JMenuItem insertMovieItem = new JMenuItem("Movie");
        insertMovieItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openInsertMovieDialog();
            }
        });
        insertDataMenu.add(insertMovieItem);
        insertBookItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                openInsertBookDialog();
            }
        });

        JMenuItem updateDataItem = new JMenuItem("Update");
        dataMenu.add(updateDataItem);

        JMenuItem removeDataItem = new JMenuItem("Remove");
        dataMenu.add(removeDataItem);

        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic('v');
        menuBar.add(viewMenu);

        JMenuItem queryViewItem = new JMenuItem("Query");
        viewMenu.add(queryViewItem);

        JMenu reportMenu = new JMenu("Report");
        reportMenu.setMnemonic('r');
        menuBar.add(reportMenu);

        JMenuItem reportItem1 = new JMenuItem("Author's Publications");
        reportMenu.add(reportItem1);

        JMenuItem reportItem2 = new JMenuItem("Publication in one Year");
        reportMenu.add(reportItem2);

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

    private void openInsertBookDialog() {
        try {
            BookDialog dialog = new BookDialog(this);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            dialog.setLocationRelativeTo(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openInsertMusicDialog() {
        try {
            MusicDialog dialog = new MusicDialog(this);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openInsertMovieDialog() {
        try {
            MovieDialog dialog = new MovieDialog(this);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
