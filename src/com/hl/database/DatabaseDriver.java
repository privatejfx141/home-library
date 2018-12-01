package com.hl.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hl.generics.Roles;

public class DatabaseDriver {

    public static final boolean INSERT_ROLES_ON_INITIALIZE = false;

    public static void main(String[] args) {
        initializeDatabase();
    }

    public static Connection connectToDatabase() {
        Connection connection = null;
        String server = "localhost";
        String schema = "hl";
        String url = "jdbc:mysql://" + server + "/" + schema;
        String username = "root";
        String password = "";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Connects to the HL database and inserts all possible people involved roles to
     * the Role table.
     */
    public static void addRoles() {
        Connection connection = connectToDatabase();
        try {
            connection.setAutoCommit(false);
            for (Roles role : Roles.values()) {
                String roleName = role.toString();
                DatabaseInserter.insertRole(connection, roleName);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }

    /**
     * Initializes the database by creating the necessary indices, functions, and
     * views and inserting data before the Home Library application can be
     * appropriately used.
     */
    public static void initializeDatabase() {
        Connection connection = connectToDatabase();
        try {
            connection.setAutoCommit(false);

            String sql = "CREATE INDEX idx_family_name ON PeopleInvolved(FamilyName)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();

            // insert all possible crew roles
            if (INSERT_ROLES_ON_INITIALIZE) {
                for (Roles role : Roles.values()) {
                    DatabaseInserter.insertRole(connection, role.toString());
                }
            }

            /* create stored procedures and functions */

            sql = "SET GLOBAL log_bin_trust_function_creators = 1";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "DROP FUNCTION IF EXISTS FullName";
            statement = connection.prepareStatement(sql);
            statement.execute();
            sql = "CREATE FUNCTION FullName(PeopleInvolved_ID INT) RETURNS VARCHAR(135) " //
                    + "BEGIN DECLARE fullName VARCHAR(135); " //
                    + "SET fullName = (SELECT CONCAT(FirstName, ' ', IF(ISNULL(MiddleName), "
                    + "'', CONCAT(MiddleName, ' ')), FamilyName) " //
                    + "FROM PeopleInvolved WHERE ID = PeopleInvolved_ID); " //
                    + "RETURN fullName; END";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "DROP FUNCTION IF EXISTS ReverseFullName";
            statement = connection.prepareStatement(sql);
            statement.execute();
            sql = "CREATE FUNCTION ReverseFullName(PeopleInvolved_ID INT) RETURNS VARCHAR(135) " //
                    + "BEGIN DECLARE fullName VARCHAR(135); " //
                    + "SET fullName = (SELECT CONCAT(FamilyName, ', ', FirstName, " //
                    + "IF(ISNULL(MiddleName), '', CONCAT(' ', MiddleName))) " //
                    + "FROM PeopleInvolved WHERE ID = PeopleInvolved_ID); " //
                    + "RETURN fullName; END";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "SET GLOBAL log_bin_trust_function_creators = 0";
            statement = connection.prepareStatement(sql);
            statement.execute();

            /* create view for report 4 */

            sql = "DROP VIEW IF EXISTS FrequentAuthors";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "CREATE VIEW FrequentAuthors AS SELECT DISTINCT ba1.Author_ID AS ID FROM BookAuthor ba1 "
                    + "INNER JOIN BookAuthor ba2 ON ba1.Author_ID = ba2.Author_ID "
                    + "INNER JOIN Book b1 ON b1.ISBN = ba1.ISBN INNER JOIN Book b2 ON b2.ISBN = ba2.ISBN "
                    + "WHERE ba1.ISBN != ba2.ISBN AND b1.YearOfPublication = b2.YearOfPublication + 1";
            statement = connection.prepareStatement(sql);
            statement.execute();

            /* create view for report 5 */

            sql = "DROP VIEW IF EXISTS TagFrequency";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "CREATE VIEW TagFrequency AS SELECT kw.Tag, COUNT(ISBN) AS Frequency FROM BookKeyword bk "
                    + "INNER JOIN Keyword kw ON (bk.Keyword_ID = kw.ID) GROUP BY bk.Keyword_ID ORDER BY Frequency DESC";
            statement = connection.prepareStatement(sql);
            statement.execute();

            /* create view for report 6 */

            sql = "DROP VIEW IF EXISTS MultiSkillsMovieRoleCount";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "CREATE VIEW MultiSkillsMovieRoleCount AS SELECT PeopleInvolved_ID, COUNT(Role_ID) AS `Roles` "
                    + "FROM CrewMember cw GROUP BY PeopleInvolved_ID HAVING COUNT(Role_ID) > 1 ORDER by PeopleInvolved_ID";
            statement = connection.prepareStatement(sql);
            statement.execute();

            /* create views for report 7 */

            sql = "DROP VIEW IF EXISTS AwardCount";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "CREATE VIEW AwardCount AS SELECT cm.MovieName, COUNT(Award) AS `Awards` FROM CrewMember cm "
                    + "INNER JOIN Award a USING (PeopleInvolved_ID, MovieName) "
                    + "WHERE a.Award GROUP BY MovieName HAVING COUNT(Award) >= 1";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "DROP VIEW IF EXISTS Directors";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "CREATE VIEW Directors AS SELECT pi.ID, pi.FamilyName, cm.MovieName FROM PeopleInvolved pi "
                    + "INNER JOIN CrewMember cm ON (pi.ID = cm.PeopleInvolved_ID) "
                    + "INNER JOIN Role r ON (cm.Role_ID = r.ID) WHERE r.Description = 'Director'";
            statement = connection.prepareStatement(sql);
            statement.execute();

            /* create views for report 10 */

            sql = "DROP VIEW IF EXISTS BookFamilyNames";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "CREATE VIEW BookFamilyNames AS SELECT pi.FamilyName, 'Author' AS Role FROM PeopleInvolved pi "
                    + "JOIN BookAuthor ba ON (pi.ID = ba.Author_ID) GROUP BY pi.FirstName, pi.FamilyName";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "DROP VIEW IF EXISTS MusicFamilyNames";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "CREATE VIEW MusicFamilyNames AS SELECT pi.FamilyName, 'Songwriter' AS Role FROM PeopleInvolved pi "
                    + "JOIN (SELECT PeopleInvolved_ID FROM PeopleInvolvedMusic WHERE isSongwriter) pim "
                    + "ON (pi.ID = pim.PeopleInvolved_ID) GROUP BY pi.FirstName, pi.FamilyName "
                    + "UNION SELECT pi.FamilyName, 'Composer' AS Role FROM PeopleInvolved pi "
                    + "JOIN (SELECT PeopleInvolved_ID FROM PeopleInvolvedMusic WHERE isComposer) pim "
                    + "ON (pi.ID = pim.PeopleInvolved_ID) GROUP BY pi.FirstName, pi.FamilyName "
                    + "UNION SELECT pi.FamilyName, 'Arranger' AS Role FROM PeopleInvolved pi "
                    + "JOIN (SELECT PeopleInvolved_ID FROM PeopleInvolvedMusic WHERE isArranger) pim "
                    + "ON (pi.ID = pim.PeopleInvolved_ID) GROUP BY pi.FirstName, pi.FamilyName "
                    + "UNION SELECT pi.FamilyName, 'Singer' AS Role FROM PeopleInvolved pi "
                    + "JOIN MusicSinger ms ON (pi.ID = ms.PeopleInvolved_ID) GROUP BY pi.FirstName, pi.FamilyName";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "DROP VIEW IF EXISTS MovieFamilyNames";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "CREATE VIEW MovieFamilyNames AS SELECT pi.FamilyName, r.Description AS Role "
                    + "FROM PeopleInvolved pi JOIN CrewMember cm ON (pi.ID = cm.PeopleInvolved_ID) "
                    + "JOIN Role r ON (cm.Role_ID = r.ID) GROUP BY cm.MovieName, pi.FirstName, pi.FamilyName";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "DROP VIEW IF EXISTS AllFamilyNames";
            statement = connection.prepareStatement(sql);
            statement.execute();

            sql = "CREATE VIEW AllFamilyNames AS SELECT * FROM BookFamilyNames "
                    + "UNION SELECT * FROM MusicFamilyNames UNION SELECT * FROM MovieFamilyNames";
            statement = connection.prepareStatement(sql);
            statement.execute();

            connection.commit();
            System.out.println("Database has been successfully initialized.");
        } catch (SQLException e) {
            System.out.println("Database seems to already have been initialized, continuing...");
            try {
                connection.rollback();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }

}
