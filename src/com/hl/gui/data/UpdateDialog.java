package com.hl.gui.data;

import java.awt.Frame;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseSelector;
import com.hl.gui.HomeLibrary;
import com.hl.gui.data.book.BookDialog;
import com.hl.gui.data.movie.MovieDialog;
import com.hl.gui.data.music.MusicDialog;
import com.hl.record.book.Book;
import com.hl.record.movie.Movie;
import com.hl.record.music.MusicAlbum;

public class UpdateDialog {

    private Frame parent;

    public UpdateDialog(Frame parent) {
        this.parent = parent;
        String productName = promptProductName();
        if (productName != null) {
            updateProduct(productName);
        }
    }

    public String promptProductName() {
        String prompt = "Enter the name of a product (book, music album, movie) to update:";
        String product = JOptionPane.showInputDialog(parent, prompt);
        // if cancelled
        if (product == null) {
            return null;
        }
        // if any characters were entered
        product = product.trim();
        if (product.isEmpty()) {
            String error = "Product name cannot be empty.";
            HomeLibrary.showSubmitErrorMessageBox(parent, error);
            return null;
        }
        return product;
    }

    public void updateProduct(String productName) {
        // get product from database
        Connection connection = DatabaseDriver.connectToDatabase();
        Object product = DatabaseSelector.getProduct(connection, productName);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // update product based on type
        if (product instanceof Book) {
            new BookDialog(parent, (Book) product);
        } else if (product instanceof MusicAlbum) {
            new MusicDialog(parent, (MusicAlbum) product);
        } else if (product instanceof Movie) {
            new MovieDialog(parent, (Movie) product);
        } else {
            String error = "Product does not exist in the database.";
            HomeLibrary.showSubmitErrorMessageBox(parent, error);
        }
    }

}
