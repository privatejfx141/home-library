package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hl.exceptions.NameFormatException;
import com.hl.record.Person;

public class DatabaseQueryReport {

    /**
     * Queries for report 1: Authors' Publications.
     * 
     * @param connection connection to HL database.
     * @param authorName author's name.
     * @return result set for report 1.
     * @throws NameFormatException On error with parsing a person's full name.
     */
    public static ResultSet queryReport1(Connection connection, String authorName) throws NameFormatException {
        ResultSet results = null;
        Person author = Person.parseName(authorName);
        String sql = "SELECT bk.ISBN, bk.Title, TRIM(bk.YearOfPublication) AS `Publication Year` FROM Book bk "
                + "JOIN BookAuthor ba USING (ISBN) JOIN PeopleInvolved pi ON (ba.Author_ID = pi.ID) "
                + "WHERE LOWER(pi.FirstName) = ? AND LOWER(pi.FamilyName) = ? ORDER BY bk.ISBN ASC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, author.getFirstName().toLowerCase());
            statement.setString(2, author.getLastName().toLowerCase());
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Queries for report 2: Publications in one Year.
     * 
     * @param connection connection to HL database
     * @param year       year of publication
     * @return result set for report 2
     */
    public static ResultSet queryReport2(Connection connection, int year) {
        ResultSet results = null;
        String sql = "SELECT bk.ISBN, bk.Title, TRIM(bk.YearOfPublication) AS `Publication Year`, "
                + "TRIM(pi.FamilyName) AS `Family Name`, LEFT(pi.FirstName, 1) AS `Initial` FROM Book bk "
                + "JOIN BookAuthor ba USING (ISBN) JOIN PeopleInvolved pi ON (ba.Author_ID = pi.ID) "
                + "WHERE bk.YearOfPublication = ? ORDER BY bk.Title ASC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, year);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Queries for report 3: Books with Similar Topic.
     * 
     * @param connection connection to HL database
     * @param subject    specific subject
     * @return result set for report 3
     */
    public static ResultSet queryReport3(Connection connection, String subject) {
        ResultSet results = null;
        String sql = "SELECT DISTINCT bk.ISBN, bk.Title, TRIM(bk.YearOfPublication) AS `Publication Year` "
                + "FROM Book bk INNER JOIN BookKeyword bkw USING (ISBN) "
                + "INNER JOIN Keyword kw ON (bkw.Keyword_ID = kw.ID) WHERE LOWER(bk.Title) LIKE ? "
                + "OR LOWER(bk.Abstract) LIKE ? OR LOWER(kw.Tag) LIKE ? ORDER BY bk.ISBN";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + subject.toLowerCase() + "%");
            statement.setString(2, "%" + subject.toLowerCase() + "%");
            statement.setString(3, "%" + subject.toLowerCase() + "%");
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Queries for report 4: Frequent Publishers Uses FrequentAuthors view and
     * FullName function.
     * 
     * @param connection connection to HL database
     * @return result set for report 4
     */
    public static ResultSet queryReport4(Connection connection) {
        ResultSet results = null;
        String sql = "SELECT bk.ISBN, bk.Title, FullName(pi.ID) as `Author`, bk.YearOfPublication FROM Book bk "
                + "JOIN BookAuthor ba ON bk.ISBN = ba.ISBN JOIN PeopleInvolved pi ON pi.ID = ba.Author_ID "
                + "WHERE pi.ID IN (SELECT ID FROM FrequentAuthors) ORDER BY pi.FamilyName, bk.YearOfPublication";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Queries for report 5: Most Popular Subjects. Uses TagFrequency view.
     * 
     * @param connection connection to HL database
     * @param subject    specific subject
     * @return result set for report 5
     */
    public static ResultSet queryReport5(Connection connection) {
        ResultSet results = null;
        String sql = "SELECT Tag, Frequency AS Frequency FROM TagFrequency "
                + "WHERE Frequency = (SELECT MAX(Frequency) FROM TagFrequency) ORDER BY Tag";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Queries for report 6: Multi Skills Movie Crew.
     * 
     * @param connection connection to HL database
     * @return result set for report 6
     */
    public static ResultSet queryReport6(Connection connection) {
        ResultSet results = null;
        String sql = "SELECT pi.FamilyName, r.Description AS `Role`, cw.MovieName "
                + "FROM PeopleInvolved pi JOIN MultiSkillsMovieRoleCount rc ON (pi.ID = rc.PeopleInvolved_ID) "
                + "JOIN CrewMember cw ON (pi.ID = cw.PeopleInvolved_ID) "
                + "JOIN Role r ON (r.ID = cw.Role_ID) ORDER BY pi.FamilyName";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Queries for report 7: Award Winning Movies. Uses AwardCount and Directors
     * views.
     * 
     * @param connection connection to HL database
     * @return result set for report 7
     */
    public static ResultSet queryReport7(Connection connection) {
        ResultSet results = null;
        String sql = "SELECT FullName(d.ID) AS `Director Name`, d.MovieName AS `Movie Name`, aw.Awards "
                + "FROM Directors d INNER JOIN AwardCount aw USING (MovieName) ORDER by d.FamilyName ASC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Queries for report 8: Music with Similar Name Uses FullName function.
     * 
     * @param connection connection to HL database
     * @return result set for report 8
     */
    public static ResultSet queryReport8(Connection connection) {
        ResultSet results = null;
        String sql = "SELECT ms.AlbumName, ms.MusicName, FullName(pi.ID) AS `Singer`, ms.Year FROM "
                + "MusicSinger ms INNER JOIN PeopleInvolved pi ON (ms.PeopleInvolved_ID = pi.ID) "
                + "WHERE LOWER(ms.MusicName) IN (SELECT LOWER(m.MusicName) FROM Music m "
                + "GROUP BY m.MusicName HAVING COUNT(m.AlbumName) > 1)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Queries for report 9: Multi Skills Music Crew
     * 
     * @param connection connection to HL database
     * @return result set for report 9
     */
    public static ResultSet queryReport9(Connection connection) {
        ResultSet results = null;
        String sql = "SELECT pi.FamilyName AS `Family Name`, LEFT(pi.FirstName, 1) AS `Initial`, "
                + "pim.MusicName AS `Music Name`, pim.AlbumName AS `Album Name`, pim.Year "
                + "FROM PeopleInvolved pi JOIN PeopleInvolvedMusic pim ON (pi.ID = pim.PeopleInvolved_ID) "
                + "WHERE pim.isSongwriter AND pim.isComposer AND NOT pim.isArranger "
                + "ORDER BY pim.Year, pim.MusicName DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Queries for report 10: Similar Names
     * 
     * @param connection connection to HL database
     * @return result set for report 10
     */
    public static ResultSet queryReport10(Connection connection) {
        ResultSet results = null;
        String sql = "SELECT FamilyName, Role FROM AllFamilyNames WHERE FamilyName IN "
                + "(SELECT FamilyName FROM AllFamilyNames GROUP BY FamilyName HAVING COUNT(FamilyName) > 1) "
                + "ORDER BY FamilyName";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

}
