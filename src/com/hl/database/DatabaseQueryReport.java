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
     * @param connection connection to HL database
     * @param authorName author's name
     * @return result set for report 1
     * @throws NameFormatException
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
     * Queries for report 3: Books with Similar Topic
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
                + "OR LOWER(bk.Abstract) LIKE ? OR LOWER(kw.Tag) LIKE ? ORDER BY bk.ISBN ASC";
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

    public static ResultSet queryReport4(Connection connection) {
        ResultSet results = null;
        return results;
    }

    /**
     * Queries for report 5: Most Popular Subjects
     * 
     * @param connection connection to HL database
     * @param subject    specific subject
     * @return result set for report 5
     */
    public static ResultSet queryReport5(Connection connection) {
        ResultSet results = null;
        String sql = "SELECT Tag, MAX(Frequency) AS Frequency "
                + "FROM (SELECT kw.Tag, COUNT(ISBN) AS Frequency FROM BookKeyword bk "
                + "INNER JOIN Keyword kw ON (bk.Keyword_ID = kw.ID) "
                + "GROUP BY bk.Keyword_ID ORDER BY Frequency DESC) TagFrequency ORDER BY Tag ASC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Queries for report 6: Multi Skills Movie Crew
     * 
     * @param connection connection to HL database
     * @return result set for report 6
     */
    public static ResultSet queryReport6(Connection connection) {
        ResultSet results = null;
        String sql = "SELECT TRIM(pi.FamilyName) AS `Family Name`, COUNT(r.ID) AS `# of Roles`, "
                + "TRIM(cw.MovieName) AS `Movie Name` "
                + "FROM PeopleInvolved pi INNER JOIN CrewMember cw ON (pi.ID = cw.PeopleInvolved_ID) "
                + "INNER JOIN Role r ON (r.ID = cw.Role_ID) GROUP BY pi.FamilyName, cw.MovieName "
                + "HAVING COUNT(r.ID) > 1 ORDER BY `Family Name` ASC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Queries for report 7: Award Winning Movies
     * 
     * @param connection connection to HL database
     * @return result set for report 7
     */
    public static ResultSet queryReport7(Connection connection) {
        ResultSet results = null;
        String sql = "SELECT CONCAT(pi.FirstName, ' ', IF(ISNULL(pi.MiddleName), '', CONCAT(pi.MiddleName, ' ')), "
                + "pi.FamilyName) AS `Director Name`, TRIM(cm.MovieName) AS `Movie Name`, `# of Awards` "
                + "FROM PeopleInvolved pi INNER JOIN CrewMember cm ON (pi.ID = cm.PeopleInvolved_ID) "
                + "INNER JOIN Role r ON (cm.Role_ID = r.ID) "
                + "INNER JOIN (SELECT cm.MovieName, COUNT(Award) AS `# of Awards` FROM CrewMember cm "
                + "INNER JOIN Award a USING (PeopleInvolved_ID, MovieName) "
                + "WHERE a.Award GROUP BY MovieName HAVING COUNT(Award) >= 1) AwardCount "
                + "USING (MovieName) WHERE r.Description = 'D' " + "ORDER BY pi.FamilyName ASC, `Movie Name` ASC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            results = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static ResultSet queryReport8(Connection connection) {
        ResultSet results = null;
        return results;
    }

    public static ResultSet queryReport9(Connection connection) {
        ResultSet results = null;
        return results;
    }

    public static ResultSet queryReport10(Connection connection) {
        ResultSet results = null;
        return results;
    }
}
