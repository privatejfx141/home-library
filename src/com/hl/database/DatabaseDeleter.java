package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseDeleter {

    /**
     * Connects to the HL database and deletes
     * 
     * @param connection Connection to the HL database.
     * @param personId
     * @return
     * @throws SQLException On delete error.
     */
    protected static boolean deletePerson(Connection connection, int personId) throws SQLException {
        String sql = "DELETE FROM PeopleInvolved WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, personId);
        statement.executeUpdate();
        return true;
    }

    protected static boolean deleteKeyword(Connection connection, int keywordId) throws SQLException {
        String sql = "DELETE FROM Keyword WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, keywordId);
        statement.executeUpdate();
        return true;
    }

    public static boolean deleteBook(Connection connection, String isbn) {
        boolean success = false;
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
            success = true;
        } catch (SQLException e) {
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
        return success;
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

    public static boolean deleteMusicAlbum(Connection connection, String albumName, int year) {
        boolean success = false;
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
            connection.commit();
            success = true;
        } catch (SQLException e) {
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
        return success;
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

    public static boolean deleteMovie(Connection connection, String movieName, int year) {
        boolean success = false;
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
            connection.commit();
            success = true;
        } catch (SQLException e) {
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
        return success;
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
