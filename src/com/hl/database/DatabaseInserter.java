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

public class DatabaseInserter {

    public static int insertPerson(Connection connection, Person person) throws DatabaseInsertException {
        String sql = "INSERT INTO PeopleInvolved (FirstName, MiddleName, FamilyName, Gender) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getFirstName());
            if (person.getMiddleName().isEmpty()) {
                statement.setNull(2, java.sql.Types.VARCHAR);
            } else {
                statement.setString(2, person.getMiddleName());
            }
            statement.setString(3, person.getLastName());
            if (person.getGender().isEmpty()) {
                statement.setNull(4, java.sql.Types.BOOLEAN);
            } else {
                statement.setBoolean(4, person.getGender().startsWith("M"));
            }
            if (statement.executeUpdate() > 0) {
                ResultSet uniqueKey = statement.getGeneratedKeys();
                if (uniqueKey.next()) {
                    return uniqueKey.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }

    public static String insertBook(Connection connection, Book book) throws DatabaseInsertException {
        String recordId = insertBookDetails(connection, book);
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
        return recordId;
    }

    private static String insertBookDetails(Connection connection, Book book) throws DatabaseInsertException {
        String sql = "INSERT INTO Book (ISBN, Title, Publisher, NumberOfPages, YearOfPublication, "
                + "EditionNumber, Abstract) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getPublisher());
            statement.setInt(4, book.getNumberOfPages());
            statement.setInt(5, book.getYearOfPublication());
            if (book.getEditionNumber() == -1) {
                statement.setNull(6, java.sql.Types.INTEGER);
            } else {
                statement.setInt(6, book.getEditionNumber());
            }
            if (book.getDescription().isEmpty()) {
                statement.setNull(7, java.sql.Types.VARCHAR);
            } else {
                statement.setString(7, book.getDescription());
            }
            if (statement.executeUpdate() > 0) {
                return book.getIsbn();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }

    public static int insertKeyword(Connection connection, String keyword) throws DatabaseInsertException {
        String sql = "INSERT INTO Keyword (Tag) VALUES (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, keyword);
            if (statement.executeUpdate() > 0) {
                ResultSet uniqueKey = statement.getGeneratedKeys();
                if (uniqueKey.next()) {
                    return uniqueKey.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }

    private static int insertBookAuthor(Connection connection, String isbn, int authorId)
            throws DatabaseInsertException {
        String sql = "INSERT INTO BookAuthor (ISBN, Author_ID) VALUES (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, isbn);
            statement.setInt(2, authorId);
            if (statement.executeUpdate() > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }

    private static int insertBookKeyword(Connection connection, String isbn, int keywordId)
            throws DatabaseInsertException {
        String sql = "INSERT INTO BookKeyword (ISBN, Keyword_ID) VALUES (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, isbn);
            statement.setInt(2, keywordId);
            if (statement.executeUpdate() > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }

    public static int insertMusicAlbum(Connection connection, MusicAlbum album) throws DatabaseInsertException {
        int recordId = -1;
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
        return recordId;
    }

    public static int insertMusicTrack(Connection connection, MusicAlbum album, MusicTrack track, int producerId)
            throws DatabaseInsertException {
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

    public static int insertMusicTrackDetails(Connection connection, MusicAlbum album, MusicTrack track, int producerId)
            throws DatabaseInsertException {
        String sql = "INSERT INTO Music (AlbumName, Year, MusicName, Language, DiskType, Producer_ID) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, album.getName());
            statement.setInt(2, album.getYear());
            statement.setString(3, track.getName());
            if (track.getLanguage() == null || track.getLanguage().isEmpty()) {
                statement.setNull(4, java.sql.Types.VARCHAR);
            } else {
                statement.setString(4, track.getLanguage());
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }

    public static int insertMusicSinger(Connection connection, MusicAlbum album, MusicTrack track, Person singer)
            throws DatabaseInsertException {
        // insert singer as a person
        int personId = DatabaseSelector.getPersonId(connection, singer);
        if (personId == -1) {
            personId = insertPerson(connection, singer);
        }
        // prepare SQL statement
        String sql = "INSERT INTO MusicSinger (AlbumName, Year, MusicName, PeopleInvolved_ID) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, album.getName());
            statement.setInt(2, album.getYear());
            statement.setString(3, track.getName());
            statement.setInt(4, personId);
            if (statement.executeUpdate() > 0) {
                return personId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }

    public static int insertMusicCrew(Connection connection, MusicAlbum album, MusicTrack track, Person crew,
            String musicRole) throws DatabaseInsertException {
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
        if (DatabaseSelector.hasMusicCrew(connection, albumName, year, trackName, personId)) {
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
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }

    public static int insertMovie(Connection connection, Movie movie) throws DatabaseInsertException {
        int recordId = insertMovieDetails(connection, movie);
        for (String role : MovieCrew.ROLES) {
            try {
                for (MovieCrew crew : movie.getCrewMembers().get(role)) {
                    int personId = insertMovieCrew(connection, movie, crew);
                    insertMovieAward(connection, personId, movie, crew.getAward());
                }
            } catch (NullPointerException e) {
                System.out.println("Warning! Crew of " + role + " does not exist.");
            }
        }
        return recordId;
    }

    private static int insertMovieDetails(Connection connection, Movie movie) throws DatabaseInsertException {
        String sql = "INSERT INTO Movie (MovieName, Year) VALUES (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movie.getName());
            statement.setInt(2, movie.getYear());
            if (statement.executeUpdate() > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }

    public static int insertMovieRole(Connection connection, String role) throws DatabaseInsertException {
        String sql = "INSERT INTO Role (Description) VALUES (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, role);
            if (statement.executeUpdate() > 0) {
                ResultSet uniqueKey = statement.getGeneratedKeys();
                if (uniqueKey.next()) {
                    return uniqueKey.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }

    public static int insertMovieCrew(Connection connection, Movie movie, MovieCrew crew)
            throws DatabaseInsertException {
        // insert as person
        int personId = DatabaseSelector.getPersonId(connection, crew);
        if (personId == -1) {
            personId = insertPerson(connection, crew);
        }
        return insertMovieCrewDetails(connection, movie, personId, crew);
    }

    private static int insertMovieCrewDetails(Connection connection, Movie movie, int personId, MovieCrew crew)
            throws DatabaseInsertException {
        String sql = "INSERT INTO CrewMember (PeopleInvolved_ID, MovieName, ReleaseYear, Role_ID) VALUES (?, ?, ?, ?)";
        int roleId = DatabaseSelector.getMovieRoleId(connection, crew.getRole());
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);
            statement.setString(2, movie.getName());
            statement.setInt(3, movie.getYear());
            statement.setInt(4, roleId);
            if (statement.executeUpdate() > 0) {
                return personId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }

    public static int insertMovieAward(Connection connection, int personId, Movie movie, boolean award)
            throws DatabaseInsertException {
        String movieName = movie.getName();
        int year = movie.getYear();
        if (DatabaseSelector.hasMovieAward(connection, personId, movieName, year)) {
            if (award) {
                DatabaseUpdater.updateMovieAward(connection, personId, movieName, year, award);
            }
            return 0;
        }
        String sql = "INSERT INTO Award (PeopleInvolved_ID, MovieName, Year, Award) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);
            statement.setString(2, movieName);
            statement.setInt(3, year);
            statement.setBoolean(4, award);
            if (statement.executeUpdate() > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new DatabaseInsertException();
    }
}
