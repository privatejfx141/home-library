package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUpdater {

    public static boolean updateMusicSongwriter(Connection connection, String albumName, int year, String trackName,
            int personId, boolean isSongwriter) {
        String sql = "UPDATE PeopleInvolvedMusic SET isSongwriter = ? WHERE LOWER(AlbumName) = ? AND Year = ? "
                + "AND LOWER(MusicName) = ? AND PeopleInvolved_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, isSongwriter);
            statement.setString(2, albumName.toLowerCase());
            statement.setInt(3, year);
            statement.setString(4, trackName.toLowerCase());
            statement.setInt(5, personId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateMusicComposer(Connection connection, String albumName, int year, String trackName,
            int personId, boolean isComposer) {
        String sql = "UPDATE PeopleInvolvedMusic SET isComposer = ? WHERE LOWER(AlbumName) = ? AND Year = ? "
                + "AND LOWER(MusicName) = ? AND PeopleInvolved_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, isComposer);
            statement.setString(2, albumName.toLowerCase());
            statement.setInt(3, year);
            statement.setString(4, trackName.toLowerCase());
            statement.setInt(5, personId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateMusicArranger(Connection connection, String albumName, int year, String trackName,
            int personId, boolean isArranger) {
        String sql = "UPDATE PeopleInvolvedMusic SET isArranger = ? WHERE LOWER(AlbumName) = ? AND Year = ? "
                + "AND LOWER(MusicName) = ? AND PeopleInvolved_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, isArranger);
            statement.setString(2, albumName.toLowerCase());
            statement.setInt(3, year);
            statement.setString(4, trackName.toLowerCase());
            statement.setInt(5, personId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateMovieAward(Connection connection, int personId, String movieName, int year,
            boolean award) {
        String sql = "UPDATE Award SET Award = ? WHERE PeopleInvolved_ID = ? AND LOWER(MovieName) = ? AND Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, award);
            statement.setInt(2, personId);
            statement.setString(3, movieName.toLowerCase());
            statement.setInt(4, year);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
