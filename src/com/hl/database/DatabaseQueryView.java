package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseQueryView {

    public static ArrayList<Object[]> getViewData(Connection connection, String product, int year, boolean isBook,
            boolean isMusic, boolean isMovie) {
        ArrayList<Object[]> dataTable = new ArrayList<>();
        if (isBook) {
            try {
                ResultSet results = getViewBook(connection, product, year);
                while (results.next()) {
                    String title = results.getString(1);
                    int publicationYear = results.getInt(2);
                    String nameQuery = results.getString(3);
                    String[] nameSplit = nameQuery.split("\\s*,\\s*");
                    String lastName = nameSplit[0];
                    String firstMiddleName = nameSplit[1];
                    Object[] dataRow = new Object[] { title, publicationYear, "B", firstMiddleName + " " + lastName };
                    dataTable.add(dataRow);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (isMusic) {
            try {
                ResultSet results = getViewMusic(connection, product, year);
                while (results.next()) {
                    String title = results.getString(1);
                    int releaseYear = results.getInt(2);
                    String nameQuery = results.getString(3);
                    String[] nameSplit = nameQuery.split("\\s*,\\s*");
                    String lastName = nameSplit[0];
                    String firstMiddleName = nameSplit[1];
                    Object[] dataRow = new Object[] { title, releaseYear, "M", firstMiddleName + " " + lastName };
                    dataTable.add(dataRow);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (isMovie) {
            try {
                ResultSet results = getViewMovie(connection, product, year);
                while (results.next()) {
                    String title = results.getString(1);
                    int releaseYear = results.getInt(2);
                    String nameQuery = results.getString(3);
                    String[] nameSplit = nameQuery.split("\\s*,\\s*");
                    String lastName = nameSplit[0];
                    String firstMiddleName = nameSplit[1];
                    Object[] dataRow = new Object[] { title, releaseYear, "F", firstMiddleName + " " + lastName };
                    dataTable.add(dataRow);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dataTable;
    }

    public static ResultSet getViewBook(Connection connection, String product, int year) throws SQLException {
        String sql = "SELECT bk.Title, bk.YearOfPublication, "
                + "MIN(TRIM(CONCAT(pi.FamilyName, ', ', pi.FirstName, ' ', IFNULL(pi.MiddleName, '')))) AS Name "
                + "FROM Book bk JOIN BookAuthor ba USING (ISBN) JOIN PeopleInvolved pi ON (ba.Author_ID = pi.ID) "
                + "WHERE LOWER(bk.Title) LIKE ? AND bk.YearOfPublication = ? GROUP BY bk.Title "
                + "ORDER BY bk.Title ASC";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + product.toLowerCase() + "%");
        statement.setInt(2, year);
        return statement.executeQuery();
    }

    public static ResultSet getViewMusic(Connection connection, String product, int year) throws SQLException {
        String sql = "SELECT ms.AlbumName, ms.Year, "
                + "MIN(TRIM(CONCAT(pi.FamilyName, ', ', pi.FirstName, ' ', IFNULL(pi.MiddleName, '')))) AS Name "
                + "FROM MusicSinger ms JOIN PeopleInvolved pi ON (ms.PeopleInvolved_ID = pi.ID) "
                + "WHERE LOWER(ms.AlbumName) LIKE ? AND ms.Year = ? GROUP BY ms.AlbumName "
                + "ORDER BY ms.AlbumName ASC";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + product.toLowerCase() + "%");
        statement.setInt(2, year);
        return statement.executeQuery();
    }

    public static ResultSet getViewMovie(Connection connection, String product, int year) throws SQLException {
        String sql = "SELECT cm.MovieName, cm.ReleaseYear, "
                + "MIN(TRIM(CONCAT(pi.FamilyName, ', ', pi.FirstName, ' ', IFNULL(pi.MiddleName, '')))) AS Name "
                + "FROM CrewMember cm JOIN PeopleInvolved pi ON (cm.PeopleInvolved_ID = pi.ID) "
                + "WHERE LOWER(cm.MovieName) LIKE ? AND cm.ReleaseYear = ? GROUP BY cm.MovieName "
                + "ORDER BY cm.MovieName ASC";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + product.toLowerCase() + "%");
        statement.setInt(2, year);
        return statement.executeQuery();
    }

}
