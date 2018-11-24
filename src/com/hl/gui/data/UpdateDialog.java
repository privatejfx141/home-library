package com.hl.gui.data;

import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.hl.database.DatabaseDriver;

public class UpdateDialog {

    private JFrame parentFrame;
    
    public UpdateDialog(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        
    }
    
    public void handleUpdateItem() {
        String prompt = "Enter the name of a product (book, music album, movie) to update:"; 
        String product = JOptionPane.showInputDialog(parentFrame, prompt);
        // if cancelled
        if (product == null) {
            return;
        }
        // if any characters were entered
        product = product.trim();
        if (product.isEmpty()) {
            String error = "Product name cannot be empty.";
            JOptionPane.showMessageDialog(parentFrame, error, "Remove Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        updateProduct(product);
    }

    public void updateProduct(String product) {
        Connection connection = DatabaseDriver.connectToDatabase();
        String type = "";
    }
    
}
