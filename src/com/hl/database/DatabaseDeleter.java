package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.hl.exceptions.DatabaseDeleteException;

/**
 * Collection of methods for removing product data from the HL database.
 * 
 * @author Yun Jie (Jeffrey) Li
 */
public class DatabaseDeleter {

    /**
     * Connects to the HL database and deletes the record of the person with the
     * given ID from the PeopleInvolved table. Returns <code>true</code> upon
     * successful deletion.
     * 
     * @param connection Connection to the HL database.
     * @param personId   Record ID of the person to delete.
     * @return <code>true</code> upon successful deletion from the PeopleInvolved
     *         table.
     * @throws SQLException On delete error.
     */
    protected static boolean deletePerson(Connection connection, int personId) throws SQLException {
        String sql = "DELETE FROM PeopleInvolved WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, personId);
        statement.executeUpdate();
        return true;
    }

    /**
     * Connects to the HL database and deletes the record of the keyword with the
     * given ID from the Keyword table. Returns <code>true</code> upon successful
     * deletion.
     * 
     * @param connection Connection to the HL database.
     * @param keywordId  Record ID of the keyword to delete.
     * @return <code>true</code> upon successful deletion from the Keyword table.
     * @throws SQLException On delete error.
     */
    protected static boolean deleteKeyword(Connection connection, int keywordId) throws SQLException {
        String sql = "DELETE FROM Keyword WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, keywordId);
        statement.executeUpdate();
        return true;
    }

    /**
     * Deletes the book with the given name and year of publication from the
     * database, and returns <code>true</code> upon successful deletion.
     * 
     * @param connection Connection to the HL database.
     * @param isbn       ISBN of the book to delete.
     * @return <code>true</code> upon successful deletion of the book data from the
     *         database.
     * @throws DatabaseDeleteException On error with deletion.
     */
    public static boolean deleteBook(Connection connection, String isbn) throws DatabaseDeleteException {
        String exceptionMessage = "";
        List<Integer> authorIds = DatabaseSelector.getBookAuthorIds(connection, isbn);
        List<Integer> keywordIds = DatabaseSelector.getBookKeywordIds(connection, isbn);
        try {
            connection.setAutoCommit(false);
            deleteBookAuthors(connection, isbn);
            deleteBookKeywords(connection, isbn);
            deleteBookDetails(connection, isbn);
            // delete orphan authors
            for (int authorId : authorIds) {
                try {
                    deletePerson(connection, authorId);
                } catch (SQLException e) {
                    continue;
                }
            }
            // delete orphan keywords
            for (int keywordId : keywordIds) {
                try {
                    deleteKeyword(connection, keywordId);
                } catch (SQLException e) {
                    continue;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            exceptionMessage = e.getMessage();
            try {
                connection.rollback();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        throw new DatabaseDeleteException(exceptionMessage);
    }

    protected static boolean deleteBookDetails(Connection connection, String isbn) throws SQLException {
        String sql = "DELETE FROM Book WHERE ISBN = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, isbn);
        statement.executeUpdate();
        return true;
    }

    protected static boolean deleteBookAuthors(Connection connection, String isbn) throws SQLException {
        String sql = "DELETE FROM BookAuthor WHERE ISBN = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, isbn);
        statement.executeUpdate();
        return true;
    }

    protected static boolean deleteBookKeywords(Connection connection, String isbn) throws SQLException {
        String sql = "DELETE FROM BookKeyword WHERE ISBN = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, isbn);
        statement.executeUpdate();
        return true;
    }

    /**
     * Deletes the music album with the given name and year of release from the
     * database, and returns <code>true</code> upon successful deletion.
     * 
     * @param connection Connection to the HL database.
     * @param albumName  Name of music album to delete.
     * @param year       Year of release of music album to delete.
     * @return <code>true</code> upon successful deletion of the music album data
     *         from the database.
     * @throws DatabaseDeleteException On error with deletion.
     */
    public static boolean deleteMusicAlbum(Connection connection, String albumName, int year)
            throws DatabaseDeleteException {
        return deleteMusicAlbum(connection, albumName, year, true);
    }

    /**
     * Deletes the music album with the given name and year of release from the
     * database, and returns <code>true</code> upon successful deletion.
     * 
     * @param connection      Connection to the HL database.
     * @param albumName       Name of music album to delete.
     * @param year            Year of release of music album to delete.
     * @param finalAutoCommit Whether or not to set autocommit to <code>true</code>
     *                        after deletion.
     * @return <code>true</code> upon successful deletion of the music album data
     *         from the database.
     * @throws DatabaseDeleteException On error with deletion.
     */
    protected static boolean deleteMusicAlbum(Connection connection, String albumName, int year,
            boolean finalAutoCommit) throws DatabaseDeleteException {
        String exceptionMessage = "";
        List<Integer> peopleInvolvedIds = DatabaseSelector.getMusicCrewIds(connection, albumName, year);
        try {
            connection.setAutoCommit(false);
            deleteMusicCrew(connection, albumName, year);
            deleteMusicSingers(connection, albumName, year);
            deleteMusicTracks(connection, albumName, year);
            // delete orphan people involved
            for (int crewId : peopleInvolvedIds) {
                try {
                    deletePerson(connection, crewId);
                } catch (SQLException e) {
                    continue;
                }
            }
            if (finalAutoCommit) {
                connection.commit();
            }
            connection.setAutoCommit(finalAutoCommit);
            return true;
        } catch (SQLException e) {
            exceptionMessage = e.getMessage();
            try {
                connection.rollback();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(finalAutoCommit);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        throw new DatabaseDeleteException(exceptionMessage);
    }

    protected static boolean deleteMusicTracks(Connection connection, String albumName, int year) throws SQLException {
        String sql = "DELETE FROM Music WHERE LOWER(AlbumName) = ? AND Year = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, albumName.toLowerCase());
        statement.setInt(2, year);
        statement.executeUpdate();
        return true;
    }

    protected static boolean deleteMusicCrew(Connection connection, String albumName, int year) throws SQLException {
        String sql = "DELETE FROM PeopleInvolvedMusic WHERE LOWER(AlbumName) = ? AND Year = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, albumName.toLowerCase());
        statement.setInt(2, year);
        statement.executeUpdate();
        return true;
    }

    protected static boolean deleteMusicSingers(Connection connection, String albumName, int year) throws SQLException {
        String sql = "DELETE FROM MusicSinger WHERE LOWER(AlbumName) = ? And Year = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, albumName.toLowerCase());
        statement.setInt(2, year);
        statement.executeUpdate();
        return true;
    }

    /**
     * Deletes the movie with the given name and year of release from the database,
     * and returns <code>true</code> upon successful deletion.
     * 
     * @param connection Connection to the HL database.
     * @param movieName  Name of the movie to delete.
     * @param year       Year of release of movie to delete.
     * @return <code>true</code> upon successful deletion of the movie data from the
     *         database.
     * @throws DatabaseDeleteException On error with deletion.
     */
    public static boolean deleteMovie(Connection connection, String movieName, int year)
            throws DatabaseDeleteException {
        return deleteMovie(connection, movieName, year, true);
    }

    /**
     * Deletes the movie with the given name and year of release from the database,
     * and returns <code>true</code> upon successful deletion.
     * 
     * @param connection      Connection to the HL database.
     * @param movieName       Name of the movie to delete.
     * @param year            Year of release of movie to delete.
     * @param finalAutoCommit Whether or not to set autocommit to <code>true</code>
     *                        after deletion.
     * @return <code>true</code> upon successful deletion of the movie data from the
     *         database.
     * @throws DatabaseDeleteException On error with deletion.
     */
    protected static boolean deleteMovie(Connection connection, String movieName, int year, boolean finalAutoCommit)
            throws DatabaseDeleteException {
        String exceptionMessage = "";
        List<Integer> crewIds = DatabaseSelector.getMovieCrewIds(connection, movieName, year);
        try {
            connection.setAutoCommit(false);
            deleteMovieCrew(connection, movieName, year);
            deleteMovieAward(connection, movieName, year);
            deleteMovieDetails(connection, movieName, year);
            // delete orphan crew members
            for (int crewId : crewIds) {
                try {
                    deletePerson(connection, crewId);
                } catch (SQLException e) {
                    continue;
                }
            }
            if (finalAutoCommit) {
                connection.commit();
            }
            connection.setAutoCommit(finalAutoCommit);
            return true;
        } catch (SQLException e) {
            exceptionMessage = e.getMessage();
            try {
                connection.rollback();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(finalAutoCommit);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        throw new DatabaseDeleteException(exceptionMessage);
    }

    protected static boolean deleteMovieDetails(Connection connection, String movieName, int year) throws SQLException {
        String sql = "DELETE FROM Movie WHERE LOWER(MovieName) = ? AND Year = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, movieName.toLowerCase());
        statement.setInt(2, year);
        statement.executeUpdate();
        return true;
    }

    protected static boolean deleteMovieCrew(Connection connection, String movieName, int year) throws SQLException {
        String sql = "DELETE FROM CrewMember WHERE LOWER(MovieName) = ? AND ReleaseYear = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, movieName.toLowerCase());
        statement.setInt(2, year);
        statement.executeUpdate();
        return true;
    }

    protected static boolean deleteMovieAward(Connection connection, String movieName, int year) throws SQLException {
        String sql = "DELETE FROM Award WHERE LOWER(MovieName) = ? AND Year = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, movieName.toLowerCase());
        statement.setInt(2, year);
        statement.executeUpdate();
        return true;
    }

}
