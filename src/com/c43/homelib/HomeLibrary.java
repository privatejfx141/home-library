package com.c43.homelib;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;

public class HomeLibrary {
    public JMenu createDataMenu(JFrame frame) {
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
                createFrame(frame);
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

    public JMenuBar createMenuBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        // build data menu
        JMenu dataMenu = createDataMenu(frame);
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
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        frame.add(btnExit);
        // create and set up the content pane.
        HomeLibrary demo = new HomeLibrary();
        frame.setJMenuBar(demo.createMenuBar(frame));
        // frame.setContentPane(demo.createContentPane());
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
    
    
    public static void createFrame(JFrame frame)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JDialog dialog = new JDialog(frame, "Test", true);
                dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try 
                {
                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                   e.printStackTrace();
                }
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
                JTextArea textArea = new JTextArea(15, 50);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                textArea.setFont(Font.getFont(Font.SANS_SERIF));
                JScrollPane scroller = new JScrollPane(textArea);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                JPanel inputpanel = new JPanel();
                inputpanel.setLayout(new FlowLayout());
                JTextField input = new JTextField(20);
                JButton button = new JButton("Enter");
                DefaultCaret caret = (DefaultCaret) textArea.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                panel.add(scroller);
                inputpanel.add(input);
                inputpanel.add(button);
                panel.add(inputpanel);
                dialog.getContentPane().add(BorderLayout.CENTER, panel);
                dialog.pack();
                dialog.setLocationByPlatform(true);
                dialog.setVisible(true);
                dialog.setResizable(false);
                input.requestFocus();
            }
        });
    }
    
}
