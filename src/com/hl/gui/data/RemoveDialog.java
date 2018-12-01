package com.hl.gui.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.hl.database.DatabaseDeleter;
import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseSelector;
import com.hl.exceptions.DatabaseDeleteException;
import com.hl.gui.HomeLibrary;

public class RemoveDialog {

    private JFrame parentFrame;

    public RemoveDialog(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        String product = promptProduct();
        if (product != null && !product.isEmpty()) {
            deleteProductFromDatabase(product);
        }
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
        // attempt to remove book
        String isbn = DatabaseSelector.getBookISBN(connection, product);
        if (isbn != null && !isbn.isEmpty()) {
            try {
                DatabaseDeleter.deleteBook(connection, isbn);
                String message = String.format(HomeLibrary.REMOVE_DB_SUCCESS_MSG, product, "book");
                HomeLibrary.showRemoveMessageBox(parentFrame, message);
            } catch (DatabaseDeleteException e) {
                String error = HomeLibrary.REMOVE_DB_FAILURE_MSG + "\n" + e.getMessage();
                HomeLibrary.showRemoveErrorMessageBox(parentFrame, error);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        // attempt to remove music album
        int year = DatabaseSelector.getMusicAlbumYear(connection, product);
        if (year > 0) {
            try {
                DatabaseDeleter.deleteMusicAlbum(connection, product, year);
                String message = String.format(HomeLibrary.REMOVE_DB_SUCCESS_MSG, product, "music album");
                HomeLibrary.showRemoveMessageBox(parentFrame, message);
            } catch (DatabaseDeleteException e) {
                String error = HomeLibrary.REMOVE_DB_FAILURE_MSG + "\n" + e.getMessage();
                HomeLibrary.showRemoveErrorMessageBox(parentFrame, error);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        // attempt to remove movie
        year = DatabaseSelector.getMovieYear(connection, product);
        if (year > 0) {
            try {
                DatabaseDeleter.deleteMovie(connection, product, year);
                String message = String.format(HomeLibrary.REMOVE_DB_SUCCESS_MSG, product, "movie");
                HomeLibrary.showRemoveMessageBox(parentFrame, message);
            } catch (DatabaseDeleteException e) {
                String error = HomeLibrary.REMOVE_DB_FAILURE_MSG + "\n" + e.getMessage();
                HomeLibrary.showRemoveErrorMessageBox(parentFrame, error);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        // otherwise product does not exist
        String error = "Product with name " + product + " does not exist.";
        HomeLibrary.showRemoveErrorMessageBox(parentFrame, error);
    }

}
