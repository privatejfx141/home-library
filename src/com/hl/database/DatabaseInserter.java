package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hl.exceptions.DatabaseInsertException;
import com.hl.record.Person;
import com.hl.record.book.Book;
import com.hl.record.movie.Movie;
import com.hl.record.movie.MovieCrew;
import com.hl.record.music.MusicAlbum;
import com.hl.record.music.MusicTrack;

/**
 * Collection of methods for inserting new product data to the HL database.
 * 
 * @author Yun Jie (Jeffrey) Li
 */
public class DatabaseInserter {

    protected static int insertPerson(Connection connection, Person person) throws SQLException {
        String sql = "INSERT INTO PeopleInvolved (FirstName, MiddleName, FamilyName, Gender) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, person.getFirstName());
        if (person.hasMiddleName()) {
            statement.setString(2, person.getMiddleName());
        } else {
            statement.setNull(2, java.sql.Types.VARCHAR);
        }
        statement.setString(3, person.getLastName());
        if (person.hasGender()) {
            statement.setBoolean(4, person.getGender().startsWith("M"));
        } else {
            statement.setNull(4, java.sql.Types.BOOLEAN);
        }
        if (statement.executeUpdate() > 0) {
            ResultSet uniqueKey = statement.getGeneratedKeys();
            if (uniqueKey.next()) {
                return uniqueKey.getInt(1);
            }
        }
        throw new SQLException();
    }

    /**
     * Connects to the HL database and inserts the book's details, keywords, and
     * author data.
     * 
     * @param connection Connection to the HL database.
     * @param book       Book data to insert.
     * @return Book ISBN on successful insert, <code>null</code> otherwise.
     * @throws DatabaseInsertException
     */
    public static String insertBook(Connection connection, Book book) throws DatabaseInsertException {
        String exceptionMessage = "";
        String recordId = null;
        try {
            connection.setAutoCommit(false);
            recordId = insertBookDetails(connection, book);
            String isbn = book.getIsbn();
            // insert authors
            for (Person author : book.getAuthors()) {
                int personId = DatabaseSelector.getPersonId(connection, author);
                if (personId == -1) {
                    personId = insertPerson(connection, author);
                }
                insertBookAuthor(connection, book.getIsbn(), personId);
            }
            // insert keywords
            for (String keyword : book.getKeywords()) {
                int keywordId = DatabaseSelector.getKeywordId(connection, keyword);
                if (keywordId == -1) {
                    keywordId = insertKeyword(connection, keyword);
                }
                insertBookKeyword(connection, isbn, keywordId);
            }
            connection.commit();
            connection.setAutoCommit(true);
            return recordId;
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
        throw new DatabaseInsertException(exceptionMessage);
    }

    protected static String insertBookDetails(Connection connection, Book book) throws SQLException {
        String sql = "INSERT INTO Book (ISBN, Title, Publisher, NumberOfPages, YearOfPublication, "
                + "EditionNumber, Abstract) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, book.getIsbn());
        statement.setString(2, book.getTitle());
        statement.setString(3, book.getPublisher());
        statement.setInt(4, book.getNumberOfPages());
        statement.setInt(5, book.getYearOfPublication());
        if (book.hasEditionNumber()) {
            statement.setInt(6, book.getEditionNumber());
        } else {
            statement.setNull(6, java.sql.Types.INTEGER);
        }
        if (book.hasDescription()) {
            statement.setString(7, book.getDescription());
        } else {
            statement.setNull(7, java.sql.Types.VARCHAR);
        }
        if (statement.executeUpdate() > 0) {
            return book.getIsbn();
        }
        throw new SQLException();
    }

    protected static int insertKeyword(Connection connection, String keyword) throws SQLException {
        String sql = "INSERT INTO Keyword (Tag) VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, keyword);
        if (statement.executeUpdate() > 0) {
            ResultSet uniqueKey = statement.getGeneratedKeys();
            if (uniqueKey.next()) {
                return uniqueKey.getInt(1);
            }
        }
        throw new SQLException();
    }

    protected static int insertBookAuthor(Connection connection, String isbn, int authorId) throws SQLException {
        String sql = "INSERT INTO BookAuthor (ISBN, Author_ID) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, isbn);
        statement.setInt(2, authorId);
        if (statement.executeUpdate() > 0) {
            return 1;
        }
        throw new SQLException();
    }

    protected static int insertBookKeyword(Connection connection, String isbn, int keywordId) throws SQLException {
        String sql = "INSERT INTO BookKeyword (ISBN, Keyword_ID) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, isbn);
        statement.setInt(2, keywordId);
        if (statement.executeUpdate() > 0) {
            return 1;
        }
        throw new SQLException();
    }

    public static int insertMusicAlbum(Connection connection, MusicAlbum album) throws DatabaseInsertException {
        return insertMusicAlbum(connection, album, true);
    }

    /**
     * Connects to the HL database and inserts the music album's details, music
     * tracks' details, and music crew data.
     * 
     * @param connection Connection to the HL database.
     * @param album      Music album data to insert.
     * @return <code>1</code> on successful insert, <code>-1</code> otherwise.
     * @throws DatabaseInsertException
     */
    protected static int insertMusicAlbum(Connection connection, MusicAlbum album, boolean finalAutoCommit)
            throws DatabaseInsertException {
        String exceptionMessage = "";
        int recordId = -1;
        try {
            connection.setAutoCommit(false);
            // insert album producer
            Person producer = album.getProducer();
            int producerId = DatabaseSelector.getPersonId(connection, producer);
            if (producerId == -1) {
                producerId = insertPerson(connection, producer);
            }
            // insert all music tracks
            for (MusicTrack track : album.getMusicTracks()) {
                recordId = insertMusicTrack(connection, album, track, producerId);
            }
            if (finalAutoCommit) {
                connection.commit();
            }
            connection.setAutoCommit(finalAutoCommit);
            return recordId;
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
        throw new DatabaseInsertException(exceptionMessage);
    }

    protected static int insertMusicTrack(Connection connection, MusicAlbum album, MusicTrack track, int producerId)
            throws SQLException {
        // insert track details
        int recordId = insertMusicTrackDetails(connection, album, track, producerId);
        // insert singers
        for (Person singer : track.getSingers()) {
            insertMusicSinger(connection, album, track, singer);
        }
        // insert music crew
        insertMusicCrew(connection, album, track, track.getSongwriter(), "Songwriter");
        insertMusicCrew(connection, album, track, track.getComposer(), "Composer");
        insertMusicCrew(connection, album, track, track.getArrangement(), "Arranger");
        return recordId;
    }

    protected static int insertMusicTrackDetails(Connection connection, MusicAlbum album, MusicTrack track,
            int producerId) throws SQLException {
        String sql = "INSERT INTO Music (AlbumName, Year, MusicName, Language, DiskType, Producer_ID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, album.getName());
        statement.setInt(2, album.getYear());
        statement.setString(3, track.getName());
        if (track.hasLanguage()) {
            statement.setString(4, track.getLanguage());
        } else {
            statement.setNull(4, java.sql.Types.VARCHAR);
        }
        if (track.getDiskType() == null || track.getDiskType().isEmpty()) {
            statement.setNull(5, java.sql.Types.SMALLINT);
        } else {
            statement.setBoolean(5, track.getDiskType().startsWith("V"));
        }
        statement.setInt(6, producerId);
        if (statement.executeUpdate() > 0) {
            return 1;
        }
        throw new SQLException();
    }

    protected static int insertMusicSinger(Connection connection, MusicAlbum album, MusicTrack track, Person singer)
            throws SQLException {
        // insert singer as a person
        int personId = DatabaseSelector.getPersonId(connection, singer);
        if (personId == -1) {
            personId = insertPerson(connection, singer);
        }
        // prepare SQL statement
        String sql = "INSERT INTO MusicSinger (AlbumName, Year, MusicName, PeopleInvolved_ID) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, album.getName());
        statement.setInt(2, album.getYear());
        statement.setString(3, track.getName());
        statement.setInt(4, personId);
        if (statement.executeUpdate() > 0) {
            return personId;
        }
        throw new SQLException();
    }

    protected static int insertMusicCrew(Connection connection, MusicAlbum album, MusicTrack track, Person crew,
            String musicRole) throws SQLException {
        String albumName = album.getName();
        int year = album.getYear();
        String trackName = track.getName();
        // get subtype discriminator
        boolean isSongwriter = musicRole.startsWith("S");
        boolean isComposer = musicRole.startsWith("C");
        boolean isArranger = musicRole.startsWith("A");
        // insert music crew as person
        int personId = DatabaseSelector.getPersonId(connection, crew);
        if (personId == -1) {
            personId = insertPerson(connection, crew);
        }
        if (DatabaseSelector.isMusicCrew(connection, albumName, year, trackName, personId)) {
            if (isSongwriter) {
                DatabaseUpdater.updateMusicSongwriter(connection, albumName, year, trackName, personId, isSongwriter);
            }
            if (isComposer) {
                DatabaseUpdater.updateMusicComposer(connection, albumName, year, trackName, personId, isComposer);
            }
            if (isArranger) {
                DatabaseUpdater.updateMusicArranger(connection, albumName, year, trackName, personId, isArranger);
            }
            return personId;
        }
        // prepare SQL statement
        String sql = "INSERT INTO PeopleInvolvedMusic (AlbumName, Year, MusicName, PeopleInvolved_ID, "
                + "IsSongwriter, IsComposer, IsArranger) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, albumName);
        statement.setInt(2, year);
        statement.setString(3, trackName);
        statement.setInt(4, personId);
        statement.setBoolean(5, isSongwriter);
        statement.setBoolean(6, isComposer);
        statement.setBoolean(7, isArranger);
        if (statement.executeUpdate() > 0) {
            return personId;
        }
        throw new SQLException();
    }

    public static int insertMovie(Connection connection, Movie movie) throws DatabaseInsertException {
        return insertMovie(connection, movie, true);
    }

    protected static int insertMovie(Connection connection, Movie movie, boolean finalAutoCommit)
            throws DatabaseInsertException {
        String exceptionMessage = "";
        int recordId = -1;
        try {
            connection.setAutoCommit(false);
            recordId = insertMovieDetails(connection, movie);
            for (MovieCrew crew : movie.getCrewMembers()) {
                int personId = insertMovieCrew(connection, movie, crew);
                insertMovieAward(connection, personId, movie, crew.getAward());
            }
            if (finalAutoCommit) {
                connection.commit();
            }
            connection.setAutoCommit(finalAutoCommit);
            return recordId;
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
        throw new DatabaseInsertException(exceptionMessage);
    }

    protected static int insertMovieDetails(Connection connection, Movie movie) throws SQLException {
        String sql = "INSERT INTO Movie (MovieName, Year) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, movie.getName());
        statement.setInt(2, movie.getYear());
        if (statement.executeUpdate() > 0) {
            return 1;
        }
        throw new SQLException();
    }

    public static int insertRole(Connection connection, String role) throws SQLException {
        String sql = "INSERT INTO Role (Description) VALUES (?)";
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, role);
        if (statement.executeUpdate() > 0) {
            ResultSet uniqueKey = statement.getGeneratedKeys();
            if (uniqueKey.next()) {
                return uniqueKey.getInt(1);
            }
        }
        throw new SQLException();
    }

    protected static int insertMovieCrew(Connection connection, Movie movie, MovieCrew crew) throws SQLException {
        // insert as person
        int personId = DatabaseSelector.getPersonId(connection, crew);
        if (personId == -1) {
            personId = insertPerson(connection, crew);
        }
        return insertMovieCrewDetails(connection, movie, personId, crew);
    }

    protected static int insertMovieCrewDetails(Connection connection, Movie movie, int personId, MovieCrew crew)
            throws SQLException {
        String sql = "INSERT INTO CrewMember (PeopleInvolved_ID, MovieName, ReleaseYear, Role_ID) VALUES (?, ?, ?, ?)";
        int roleId = DatabaseSelector.getRoleId(connection, crew.getRole());
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, personId);
        statement.setString(2, movie.getName());
        statement.setInt(3, movie.getYear());
        statement.setInt(4, roleId);
        if (statement.executeUpdate() > 0) {
            return personId;
        }
        throw new SQLException();
    }

    protected static int insertMovieAward(Connection connection, int personId, Movie movie, boolean award)
            throws SQLException {
        String movieName = movie.getName();
        int year = movie.getYear();
        if (DatabaseSelector.hasMovieAward(connection, personId, movieName, year)) {
            if (award) {
                DatabaseUpdater.updateMovieAward(connection, personId, movieName, year, award);
            }
            return 1;
        }
        String sql = "INSERT INTO Award (PeopleInvolved_ID, MovieName, Year, Award) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, personId);
        statement.setString(2, movieName);
        statement.setInt(3, year);
        statement.setBoolean(4, award);
        if (statement.executeUpdate() > 0) {
            return 1;
        }
        throw new SQLException();
    }

}
