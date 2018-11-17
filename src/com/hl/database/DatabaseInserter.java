package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hl.record.Person;
import com.hl.record.book.Book;
import com.hl.record.movie.Movie;
import com.hl.record.movie.MovieCrew;
import com.hl.record.music.MusicAlbum;
import com.hl.record.music.MusicTrack;

public class DatabaseInserter {

    public static int insertPerson(Connection connection, Person person) {
        String sql = "INSERT INTO PeopleInvolved (FirstName, MiddleName, FamilyName, Gender) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getFirstName());
            if (person.getMiddleName().isEmpty()) {
                statement.setNull(2, java.sql.Types.VARCHAR);
            } else {
                statement.setString(2, person.getMiddleName());
            }
            statement.setString(3, person.getLastName());
            if (person.getGender().isEmpty()) {
                statement.setNull(3, java.sql.Types.BOOLEAN);
            } else {
                statement.setBoolean(4, person.getGender().startsWith("M"));
            }
            if (statement.executeUpdate() > 0) {
                ResultSet uniqueKey = statement.getGeneratedKeys();
                if (uniqueKey.next()) {
                    return uniqueKey.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int insertBook(Connection connection, Book book) {
        insertBookDetails(connection, book);

        for (Person author : book.getAuthors()) {
            insertBookAuthor(connection, book.getIsbn(), -1);
        }
        return -1;
    }

    private static int insertBookDetails(Connection connection, Book book) {
        String sql = "INSERT INTO TABLE Book (ISBN, Title, Publisher, NumberOfPages, YearOfPublication, EditionNumber, Abstract) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getNumberOfPages());
            statement.setInt(5, book.getYearOfPublication());
            if (book.getEditionNumber() == -1) {
                statement.setNull(6, java.sql.Types.INTEGER);
            } else {
                statement.setInt(6, book.getEditionNumber());
            }
            if (book.getDescription().isEmpty()) {
                statement.setNull(7, java.sql.Types.VARCHAR);
            } else {
                statement.setString(7, book.getDescription());
            }
            if (statement.executeUpdate() > 0) {
                ResultSet uniqueKey = statement.getGeneratedKeys();
                if (uniqueKey.next()) {
                    return uniqueKey.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int insertKeyword(Connection connection, String keyword) {
        return -1;
    }

    public static int insertBookAuthor(Connection connection, String isbn, int authorId) {
        return -1;
    }

    public static int insertBookKeyword(Connection connection, String isbn, int keywordId) {
        return -1;
    }

    public static int insertMusicAlbum(Connection connection, MusicAlbum album) {
        return -1;
    }
    
    public static int insertMusicTrack(Connection connection, MusicTrack track) {
        return -1;
    }
    
    public static int insertMovie(Connection connection, Movie movie) {
        return -1;
    }
    
    public static int insertRole(Connection connection, String role) {
        return -1;
    }

    public static int insertMovieCrew(Connection connection, MovieCrew crew) {
        return -1;
    }
    
    public static int insertAward() {
        return -1;
    }
    
}
