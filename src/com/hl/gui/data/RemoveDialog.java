package com.hl.gui.data;

import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.hl.database.DatabaseDeleter;
import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseSelector;

public class RemoveDialog {

    private JFrame parentFrame;

    public RemoveDialog(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        String product = promptProduct();
        deleteProductFromDatabase(product);
    }

    public String promptProduct() {
        String prompt = "Enter the name of a product (book, music album, movie) to remove:";
        String product = JOptionPane.showInputDialog(parentFrame, prompt);
        // if cancelled
        if (product == null) {
            return null;
        }
        // if any characters were entered
        product = product.trim();
        if (product.isEmpty()) {
            String error = "Product name cannot be empty.";
            JOptionPane.showMessageDialog(parentFrame, error, "Remove Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return product;
    }

    public void deleteProductFromDatabase(String product) {
        Connection connection = DatabaseDriver.connectToDatabase();
        String type = null;
        // if product is book
        String isbn = DatabaseSelector.getBookISBN(connection, product);
        if (isbn != null && !isbn.isEmpty()) {
            type = "book";
            DatabaseDeleter.deleteBook(connection, isbn);
        } else {
            // if product is music album
            int year = DatabaseSelector.getMusicAlbumYear(connection, product);
            if (year > 0) {
                type = "music album";
                DatabaseDeleter.deleteMusicAlbum(connection, product, year);
            } else {
                // if product is movie
                year = DatabaseSelector.getMovieYear(connection, product);
                if (year > 0) {
                    type = "movie";
                    DatabaseDeleter.deleteMovie(connection, product, year);
                } else {
                    String error = "Product with name " + product + " does not exist.";
                    JOptionPane.showMessageDialog(parentFrame, error, "Remove Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        String msg = "Product of type " + type + " and name " + product + " was successfully removed.";
        JOptionPane.showMessageDialog(parentFrame, msg, "Remove Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
