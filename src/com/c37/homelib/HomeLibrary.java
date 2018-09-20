package com.c37.homelib;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class HomeLibrary {

	public JMenu createDataMenu() {
		JMenu menu = new JMenu("Data");
		menu.setMnemonic(KeyEvent.VK_D);		
		
		// build insert item
		JMenu itmInsert = new JMenu("Insert");
		itmInsert.setMnemonic(KeyEvent.VK_I);
		
		JMenuItem itmInsBook = new JMenuItem("Book");
		
		itmInsert.add(itmInsBook);
		JMenuItem itmInsMusic = new JMenuItem("Music");
		itmInsert.add(itmInsMusic);
		JMenuItem itmInsMovie = new JMenuItem("Movie");
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
		
		return menu;
	}
	
	public JMenu createReportMenu() {
		JMenu menu = new JMenu("Report");
		
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

        // create and set up the content pane.
        HomeLibrary demo = new HomeLibrary();
        frame.setJMenuBar(demo.createMenuBar());
        //frame.setContentPane(demo.createContentPane());

        // display the window.
        frame.setSize(640, 480);
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
