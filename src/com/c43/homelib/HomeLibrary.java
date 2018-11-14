package com.c43.homelib;

//Usually you will require both swing and awt packages
//even if you are working with just swings.
import javax.swing.*;
import java.awt.*;

class HomeLibrary {
    public static void main(String args[]) {
        JFrame HomeLibraryForm;
        HomeLibraryForm = new JFrame("Deposit");
        HomeLibraryForm.setResizable(false);
        HomeLibraryForm.setTitle("Home Library");
        HomeLibraryForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JMenuBar menuBar = new JMenuBar();
        HomeLibraryForm.getContentPane().add(menuBar, BorderLayout.NORTH);
        
        JMenu dataMenu = new JMenu("Data");
        dataMenu.setMnemonic('d');
        menuBar.add(dataMenu);
        
        JMenuItem insertDataItem = new JMenuItem("Insert");
        dataMenu.add(insertDataItem);
        
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
        HomeLibraryForm.getContentPane().add(mainPanel, BorderLayout.CENTER);
        
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
        HomeLibraryForm.getContentPane().add(panel, BorderLayout.SOUTH);
        
        JButton dataButton = new JButton("Data");
        panel.add(dataButton);
        dataButton.setVerticalAlignment(SwingConstants.BOTTOM);
        
        JButton viewButton = new JButton("View");
        panel.add(viewButton);
        
        JButton reportButton = new JButton("Report");
        panel.add(reportButton);
        
        JButton exitButton = new JButton("Exit");
        exitButton.setMnemonic('x');
        panel.add(exitButton);
        
        HomeLibraryForm.setLocationRelativeTo(null);
        HomeLibraryForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        HomeLibraryForm.pack();
        HomeLibraryForm.setVisible(true);
    }
}