package com.c43.homelib;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class HomeLibrary {

	public JMenu createDataMenu() {
		JMenu menu = new JMenu("Data");
		menu.setMnemonic(KeyEvent.VK_D);		
		
		// build insert item
		JMenu itmInsert = new JMenu("Insert");
		itmInsert.setMnemonic(KeyEvent.VK_I);
		
		// build and add insert book option
		JMenuItem itmInsBook = new JMenuItem("Book");
		itmInsBook.setMnemonic(KeyEvent.VK_B);
		itmInsBook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Insert book.");
			}
		});
		itmInsert.add(itmInsBook);
		
		// build insert music option
		JMenuItem itmInsMusic = new JMenuItem("Music");
		itmInsMusic.setMnemonic(KeyEvent.VK_M);
		itmInsMusic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Insert music.");
			}
		});
		itmInsert.add(itmInsMusic);
		
		// build insert movie option
		JMenuItem itmInsMovie = new JMenuItem("Movie");
		itmInsMovie.setMnemonic(KeyEvent.VK_O);
		itmInsMovie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Insert movie.");
			}
		});
		itmInsert.add(itmInsMovie);
		
		
		menu.add(itmInsert);
		
		JMenuItem itmUpdate = new JMenuItem("Update");
		itmUpdate.setMnemonic(KeyEvent.VK_U);
		menu.add(itmUpdate);
		
		
		JMenuItem itmRemove = new JMenuItem("Remove");
		itmRemove.setMnemonic(KeyEvent.VK_R);
		menu.add(itmRemove);
		
		return menu;
	}
	
	public JMenu createViewMenu() {
		JMenu menu = new JMenu("View");
		menu.setMnemonic(KeyEvent.VK_V);
		
		return menu;
	}
	
	public JMenu createReportMenu() {
		JMenu menu = new JMenu("Report");
		menu.setMnemonic(KeyEvent.VK_R);
		
		return menu;
	}
	
	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		// build data menu
		JMenu dataMenu = createDataMenu();
		menuBar.add(dataMenu);
		
		// build view menu
		JMenu viewMenu = createViewMenu();
		menuBar.add(viewMenu);
		
		// build report menu
		JMenu reportMenu = createReportMenu();
		menuBar.add(reportMenu);
		
		return menuBar;
	}
	
	
	public static void createGUI() {
		// create and set up the window.
        JFrame frame = new JFrame("Home Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton btnExit = new JButton("Exit");
        btnExit.setMnemonic(KeyEvent.VK_E);
        frame.add(btnExit);

        // create and set up the content pane.
        HomeLibrary demo = new HomeLibrary();
        frame.setJMenuBar(demo.createMenuBar());
        //frame.setContentPane(demo.createContentPane());

        // display the window.
        frame.setSize(300, 100);
        frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
	}
	
	
}
