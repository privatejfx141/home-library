package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Collection of methods for querying basic product data from the HL database.
 * 
 * @author Yun Jie (Jeffrey) Li
 */
public class DatabaseQueryView {

    protected static ArrayList<Object[]> getViewData(ResultSet results, String productType) throws SQLException {
        ArrayList<Object[]> dataList = new ArrayList<>();
        while (results.next()) {
            String title = results.getString(1);
            int year = results.getInt(2);
            String nameQuery = results.getString(3);
            String[] nameSplit = nameQuery.split("\\s*,\\s*");
            String lastName = nameSplit[0];
            String firstMiddleName = nameSplit[1];
            Object[] dataRow = new Object[] { title, year, "B", firstMiddleName + " " + lastName };
            dataList.add(dataRow);
        }
        return dataList;
    }

    public static ArrayList<Object[]> getViewData(Connection connection, String product, int year, boolean isBook,
            boolean isMusic, boolean isMovie) {
        ArrayList<Object[]> dataTable = new ArrayList<>();
        if (isBook) {
            try {
                ResultSet results = getViewBook(connection, product, year);
                dataTable.addAll(getViewData(results, "B"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (isMusic) {
            try {
                ResultSet results = getViewMusic(connection, product, year);
                dataTable.addAll(getViewData(results, "M"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (isMovie) {
            try {
                ResultSet results = getViewMovie(connection, product, year);
                dataTable.addAll(getViewData(results, "F"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dataTable;
    }

    /**
     * Returns a fixed ResultSet for the given name and publication year of a book.
     * 
     * @param connection Connection to the HL database.
     * @param product    Title of the book.
     * @param year       Release of publication for the book.
     * @return A fixed ResultSet for the given name and release year of a book.
     * @throws SQLException On error with querying database.
     */
    public static ResultSet getViewBook(Connection connection, String product, int year) throws SQLException {
        String sql = "SELECT bk.Title, bk.YearOfPublication, MIN(ReverseFullName(pi.ID)) AS Name "
                + "FROM Book bk JOIN BookAuthor ba USING (ISBN) JOIN PeopleInvolved pi ON (ba.Author_ID = pi.ID) "
                + "WHERE LOWER(bk.Title) LIKE ? AND bk.YearOfPublication = ? GROUP BY bk.Title "
                + "ORDER BY bk.Title ASC";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + product.toLowerCase() + "%");
        statement.setInt(2, year);
        return statement.executeQuery();
    }

    /**
     * Returns a fixed ResultSet for the given name and release year of a music album.
     * 
     * @param connection Connection to the HL database.
     * @param product    Name of the music album.
     * @param year       Release of year for the music album.
     * @return A fixed ResultSet for the given name and release year of a music album.
     * @throws SQLException On error with querying database.
     */
    public static ResultSet getViewMusic(Connection connection, String product, int year) throws SQLException {
        String sql = "SELECT ms.AlbumName, ms.Year, MIN(ReverseFullName(pi.ID)) AS Name "
                + "FROM MusicSinger ms JOIN PeopleInvolved pi ON (ms.PeopleInvolved_ID = pi.ID) "
                + "WHERE LOWER(ms.AlbumName) LIKE ? AND ms.Year = ? GROUP BY ms.AlbumName "
                + "ORDER BY ms.AlbumName ASC";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + product.toLowerCase() + "%");
        statement.setInt(2, year);
        return statement.executeQuery();
    }

    /**
     * Returns a fixed ResultSet for the given name and release year of a movie.
     * 
     * @param connection Connection to the HL database.
     * @param product    Name of the movie.
     * @param year       Release of year for the movie.
     * @return A fixed ResultSet for the given name and release year of a movie.
     * @throws SQLException On error with querying database.
     */
    public static ResultSet getViewMovie(Connection connection, String product, int year) throws SQLException {
        String sql = "SELECT cm.MovieName, cm.ReleaseYear, MIN(ReverseFullName(pi.ID)) AS Name "
                + "FROM CrewMember cm JOIN PeopleInvolved pi ON (cm.PeopleInvolved_ID = pi.ID) "
                + "WHERE LOWER(cm.MovieName) LIKE ? AND cm.ReleaseYear = ? GROUP BY cm.MovieName "
                + "ORDER BY cm.MovieName ASC";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + product.toLowerCase() + "%");
        statement.setInt(2, year);
        return statement.executeQuery();
    }

}
