package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseDeleter {

    public static boolean deletePerson(Connection connection, int personId) throws SQLException {
        String sql = "DELETE FROM PeopleInvolved WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, personId);
        statement.executeUpdate();
        return true;
    }

    public static boolean deleteKeyword(Connection connection, int keywordId) throws SQLException {
        String sql = "DELETE FROM Keyword WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, keywordId);
        statement.executeUpdate();
        return true;
    }

    public static boolean deleteBook(Connection connection, String isbn) {
        List<Integer> authorIds = DatabaseSelector.getBookAuthorIds(connection, isbn);
        List<Integer> keywordIds = DatabaseSelector.getBookKeywordIds(connection, isbn);
        if (!(deleteBookAuthors(connection, isbn) && deleteBookKeywords(connection, isbn))) {
            return false;
        }
        if (!deleteBookDetails(connection, isbn)) {
            return false;
        }
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
        return true;
    }

    private static boolean deleteBookDetails(Connection connection, String isbn) {
        String sql = "DELETE FROM Book WHERE ISBN = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, isbn);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean deleteBookAuthors(Connection connection, String isbn) {
        String sql = "DELETE FROM BookAuthor WHERE ISBN = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, isbn);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean deleteBookKeywords(Connection connection, String isbn) {
        String sql = "DELETE FROM BookKeyword WHERE ISBN = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, isbn);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteMusicAlbum(Connection connection, String albumName, int year) {
        List<Integer> peopleInvolvedIds = DatabaseSelector.getMusicCrewIds(connection, albumName, year);
        if (!(deleteMusicCrew(connection, albumName, year) && deleteMusicSingers(connection, albumName, year))) {
            return false;
        }
        if (!deleteMusicTracks(connection, albumName, year)) {
            return false;
        }
        // delete orphan people involved
        for (int crewId : peopleInvolvedIds) {
            try {
                deletePerson(connection, crewId);
            } catch (SQLException e) {
                continue;
            }
        }
        return true;
    }

    private static boolean deleteMusicTracks(Connection connection, String albumName, int year) {
        String sql = "DELETE FROM Music WHERE LOWER(AlbumName) = ? AND Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName.toLowerCase());
            statement.setInt(2, year);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean deleteMusicCrew(Connection connection, String albumName, int year) {
        String sql = "DELETE FROM PeopleInvolvedMusic WHERE LOWER(AlbumName) = ? AND Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName.toLowerCase());
            statement.setInt(2, year);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean deleteMusicSingers(Connection connection, String albumName, int year) {
        String sql = "DELETE FROM MusicSinger WHERE LOWER(AlbumName) = ? And Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName.toLowerCase());
            statement.setInt(2, year);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteMovie(Connection connection, String movieName, int year) {
        List<Integer> crewIds = DatabaseSelector.getMovieCrewIds(connection, movieName, year);
        if (!(deleteMovieCrew(connection, movieName, year) && deleteMovieAward(connection, movieName, year))) {
            return false;
        }
        if (!deleteMovieDetails(connection, movieName, year)) {
            return false;
        }
        // delete orphan crew members
        for (int crewId : crewIds) {
            try {
                deletePerson(connection, crewId);
            } catch (SQLException e) {
                continue;
            }
        }
        return true;
    }

    private static boolean deleteMovieDetails(Connection connection, String movieName, int year) {
        String sql = "DELETE FROM Movie WHERE LOWER(MovieName) = ? AND Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movieName.toLowerCase());
            statement.setInt(2, year);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean deleteMovieCrew(Connection connection, String movieName, int year) {
        String sql = "DELETE FROM CrewMember WHERE LOWER(MovieName) = ? AND ReleaseYear = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movieName.toLowerCase());
            statement.setInt(2, year);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean deleteMovieAward(Connection connection, String movieName, int year) {
        String sql = "DELETE FROM Award WHERE LOWER(MovieName) = ? AND Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movieName.toLowerCase());
            statement.setInt(2, year);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
