package com.hl.test;

import java.sql.Connection;
import java.sql.SQLException;
import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseInserter;
import com.hl.exceptions.DatabaseInsertException;
import com.hl.exceptions.NameFormatException;
import com.hl.record.Person;
import com.hl.record.book.Book;

public class InsertBooks {

    public static void main(String[] args) throws DatabaseInsertException, SQLException, NameFormatException {
        Connection connection = DatabaseDriver.connectToDatabase();
        
        Book book = new Book.Builder()
                .setIsbn("9780521402309")
                .setTitle("The Great Gatsby")
                .setPublisher("Bodley Head")
                .setYearOfPublication(1967)
                .setNumberOfPages(191)
                .addAuthor(Person.parseName("Francis Scott Fitzgerald"))
                .setDescription("The Great Gatsby book description")
                .create();
        DatabaseInserter.insertBook(connection, book);
        
        book = new Book.Builder()
                .setIsbn("0000871299208")
                .setTitle("To Kill a Mockingbird")
                .setPublisher("J. B. Lippincott & Co.")
                .setYearOfPublication(1960)
                .setNumberOfPages(281)
                .addAuthor(Person.parseName("Harper Lee"))
                .addAuthor(Person.parseName("Christopher Sergel"))
                .setDescription("To Kill a Mockingbird book description")
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder()
                .setIsbn("0006021637887")
                .setTitle("Go Set a Watchman")
                .setPublisher("HarperCollins")
                .setYearOfPublication(2015)
                .setNumberOfPages(278)
                .addAuthor(Person.parseName("Harper Lee"))
                .setDescription("Go Set a Watchman book description")
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder()
                .setIsbn("0000870212850")
                .setTitle("The Hunt for Red October")
                .setPublisher("Naval Institute Press")
                .setYearOfPublication(1984)
                .setNumberOfPages(387)
                .addAuthor(Person.parseName("Tom Clancy"))
                .setDescription("The Hunt for Red October book description")
                .addKeyword("Jack Ryan")
                .addKeyword("Military")
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder()
                .setIsbn("0000399132414")
                .setTitle("Patriot Games")
                .setPublisher("G.P. Putnam's Sons")
                .setYearOfPublication(1987)
                .setNumberOfPages(540)
                .addAuthor(Person.parseName("Tom Clancy"))
                .setDescription("Patriot Games book description")
                .addKeyword("Jack Ryan")
                .addKeyword("Military")
                .addKeyword("Thriller")
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder()
                .setIsbn("0-307-34660-9")
                .setTitle("World War Z")
                .setPublisher("Crown")
                .setYearOfPublication(2006)
                .setNumberOfPages(342)
                .addAuthor(Person.parseName("Max Brooks"))
                .setDescription("World War Z book description Thriller")
                .addKeyword("Horror")
                .addKeyword("Zombies")
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder()
                .setIsbn("1-4000-4962-8")
                .setTitle("The Zombie Survival Guide")
                .setPublisher("Crown")
                .setYearOfPublication(2003)
                .setNumberOfPages(272)
                .addAuthor(Person.parseName("Max Brooks"))
                .setDescription("The Zombie Survival Guide book description")
                .addKeyword("Horror")
                .addKeyword("Zombies")
                .addKeyword("Humour")
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder()
                .setIsbn("9780307464972")
                .setTitle("The Harlem Hellfighters")
                .setPublisher("Crown")
                .setYearOfPublication(2014)
                .setNumberOfPages(272)
                .addAuthor(Person.parseName("Max Brooks"))
                .addAuthor(Person.parseName("Caanan White"))
                .setDescription("The Harlem Hellfighters book description")
                .addKeyword("Military")
                .addKeyword("Graphic novel")
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder()
                .setIsbn("0-7475-5100-6")
                .setTitle("Harry Potter and the Order of the Phoenix")
                .setPublisher("Scholastic")
                .setYearOfPublication(2003)
                .setNumberOfPages(870)
                .addAuthor(Person.parseName("J. K. Rowling"))
                .addAuthor(Person.parseName("Jason Cockcroft"))
                .setDescription("Harry Potter and the Order of the Phoenix book description")
                .addKeyword("Fantasy")
                .create();
        DatabaseInserter.insertBook(connection, book);
        
//        String isbn = DatabaseSelector.getBookISBN(connection, "The Harlem Hellfighters Thriller");
//        System.out.println(isbn);
//        if (DatabaseDeleter.deleteBook(connection, isbn)) {
//            System.out.println("Deletion successful");
//        }
        connection.close();
        System.out.println("Insertions complete");
    }
    
}
