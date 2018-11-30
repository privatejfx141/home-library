package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.hl.record.Person;
import com.hl.record.book.Book;
import com.hl.record.movie.Movie;
import com.hl.record.movie.MovieCrew;
import com.hl.record.music.MusicAlbum;
import com.hl.record.music.MusicTrack;

public class DatabaseUpdater {

    protected static boolean updatePerson(Connection connection, Person person) throws SQLException {
        String sql = "UPDATE PeopleInvolved SET FirstName = ?, MiddleName = ?, FamilyName = ?, Gender = ? "
                + "WHERE ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
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
        statement.setInt(5, person.getId());
        statement.executeUpdate();
        return true;
    }

    public static boolean updateBook(Connection connection, Book book) {
        boolean success = false;
        try {
            connection.setAutoCommit(false);
            updateBookDetails(connection, book);
            updateBookAuthors(connection, book);
            updateBookKeywords(connection, book);
            connection.commit();
            success = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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

    protected static boolean updateBookDetails(Connection connection, Book book) throws SQLException {
        String sql = "UPDATE Book SET Title = ?, Publisher = ?, NumberOfPages = ?, YearOfPublication = ?, "
                + "EditionNumber = ?, Abstract = ? WHERE ISBN = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, book.getTitle());
        statement.setString(2, book.getPublisher());
        statement.setInt(3, book.getNumberOfPages());
        statement.setInt(4, book.getYearOfPublication());
        if (book.hasEditionNumber()) {
            statement.setInt(5, book.getEditionNumber());
        } else {
            statement.setNull(5, java.sql.Types.INTEGER);
        }
        if (book.hasDescription()) {
            statement.setString(6, book.getDescription());
        } else {
            statement.setNull(6, java.sql.Types.VARCHAR);
        }
        statement.setString(7, book.getIsbn());
        statement.executeUpdate();
        return true;
    }

    protected static boolean updateBookAuthors(Connection connection, Book book) throws SQLException {
        String isbn = book.getIsbn();
        List<Integer> removeAuthorIds = DatabaseSelector.getBookAuthorIds(connection, isbn);
        // remove all book authors and re-add with updated ones
        DatabaseDeleter.deleteBookAuthors(connection, isbn);
        // update or insert new authors
        for (Person author : book.getAuthors()) {
            int authorId = author.getId();
            // if author already exists in the database
            if (authorId > 0) {
                updatePerson(connection, author);
                removeAuthorIds.remove(Integer.valueOf(authorId));
            } else {
                authorId = DatabaseInserter.insertPerson(connection, author);
            }
            DatabaseInserter.insertBookAuthor(connection, isbn, authorId);
        }
        // remove all discarded authors
        for (int authorId : removeAuthorIds) {
            try {
                DatabaseDeleter.deletePerson(connection, authorId);
            } catch (SQLException e) {
                continue;
            }
        }
        return true;
    }

    protected static boolean updateBookKeywords(Connection connection, Book book) throws SQLException {
        String isbn = book.getIsbn();
        List<Integer> removeKeywordIds = DatabaseSelector.getBookKeywordIds(connection, isbn);
        // remove all book keywords and re-add updated ones
        DatabaseDeleter.deleteBookKeywords(connection, isbn);
        // insert new keywords
        for (String keyword : book.getKeywords()) {
            int keywordId = DatabaseSelector.getKeywordId(connection, keyword);
            // insert keyword already exists in the database
            if (keywordId > 0) {
                removeKeywordIds.remove(Integer.valueOf(keywordId));
            } else {
                keywordId = DatabaseInserter.insertKeyword(connection, keyword);
            }
            DatabaseInserter.insertBookKeyword(connection, isbn, keywordId);
        }
        // remove all discarded keywords
        for (int keywordId : removeKeywordIds) {
            try {
                DatabaseDeleter.deleteKeyword(connection, keywordId);
            } catch (SQLException e) {
                continue;
            }
        }
        return true;
    }

    public static boolean updateMusicAlbum(Connection connection, MusicAlbum album) {
        boolean success = false;
        try {
            connection.setAutoCommit(false);
            String oldAlbumName = album.getPrimaryKeyName();
            int oldAlbumYear = album.getPrimaryKeyYear();
            DatabaseDeleter.deleteMusicAlbum(connection, oldAlbumName, oldAlbumYear);
            DatabaseInserter.insertMusicAlbum(connection, album);
            connection.setAutoCommit(false);
            DatabaseUpdater.updateMusicCrewMembers(connection, album);
            success = true;
            connection.commit();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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

    protected static boolean updateMusicTrack(Connection connection, String albumName, int year, MusicTrack track)
            throws SQLException {
        String sql = "UPDATE Music SET Language = ?, DiskType = ? WHERE LOWER(AlbumName) = ? AND Year = ? "
                + "AND LOWER(MusicName) = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        if (track.hasLanguage()) {
            statement.setString(1, track.getLanguage());
        } else {
            statement.setNull(1, java.sql.Types.VARCHAR);
        }
        if (track.hasDiskType()) {
            statement.setBoolean(2, track.getDiskType().startsWith("V"));
        } else {
            statement.setNull(2, java.sql.Types.BOOLEAN);
        }
        statement.setString(3, albumName.toLowerCase());
        statement.setInt(4, year);
        statement.setString(5, track.getName().toLowerCase());
        statement.executeUpdate();
        return true;
    }

    protected static boolean updateMusicCrewMembers(Connection connection, MusicAlbum album) throws SQLException {
        String albumName = album.getName();
        int year = album.getYear();
        List<Integer> removeCrewIds = DatabaseSelector.getMusicCrewIds(connection, albumName, year);
        DatabaseDeleter.deleteMusicCrew(connection, albumName, year);
        DatabaseDeleter.deleteMusicSingers(connection, albumName, year);
        // update old crew members or update new ones
        for (MusicTrack track : album.getMusicTracks()) {
            int crewId = -1;
            for (Person singer : track.getSingers()) {
                if ((crewId = singer.getId()) > 0) {
                    updatePerson(connection, singer);
                    removeCrewIds.remove(Integer.valueOf(crewId));
                }
                crewId = DatabaseInserter.insertMusicSinger(connection, album, track, singer);
            }
            Person[] members = new Person[] { track.getSongwriter(), track.getComposer(), track.getArrangement() };
            for (Person member : members) {
                if ((crewId = member.getId()) > 0) {
                    updatePerson(connection, member);
                    removeCrewIds.remove(Integer.valueOf(crewId));
                }
            }
            DatabaseInserter.insertMusicCrew(connection, album, track, track.getSongwriter(), "Songwriter");
            DatabaseInserter.insertMusicCrew(connection, album, track, track.getComposer(), "Composer");
            DatabaseInserter.insertMusicCrew(connection, album, track, track.getArrangement(), "Arranger");
        }
        // remove discarded crew members
        for (int crewId : removeCrewIds) {
            try {
                DatabaseDeleter.deletePerson(connection, crewId);
            } catch (SQLException e) {
                continue;
            }
        }
        return true;
    }

    protected static boolean updateMusicSongwriter(Connection connection, String albumName, int year, String trackName,
            int personId, boolean isSongwriter) throws SQLException {
        String sql = "UPDATE PeopleInvolvedMusic SET isSongwriter = ? WHERE LOWER(AlbumName) = ? AND Year = ? "
                + "AND LOWER(MusicName) = ? AND PeopleInvolved_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setBoolean(1, isSongwriter);
        statement.setString(2, albumName.toLowerCase());
        statement.setInt(3, year);
        statement.setString(4, trackName.toLowerCase());
        statement.setInt(5, personId);
        statement.executeUpdate();
        return true;
    }

    protected static boolean updateMusicComposer(Connection connection, String albumName, int year, String trackName,
            int personId, boolean isComposer) throws SQLException {
        String sql = "UPDATE PeopleInvolvedMusic SET isComposer = ? WHERE LOWER(AlbumName) = ? AND Year = ? "
                + "AND LOWER(MusicName) = ? AND PeopleInvolved_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setBoolean(1, isComposer);
        statement.setString(2, albumName.toLowerCase());
        statement.setInt(3, year);
        statement.setString(4, trackName.toLowerCase());
        statement.setInt(5, personId);
        statement.executeUpdate();
        return true;
    }

    protected static boolean updateMusicArranger(Connection connection, String albumName, int year, String trackName,
            int personId, boolean isArranger) throws SQLException {
        String sql = "UPDATE PeopleInvolvedMusic SET isArranger = ? WHERE LOWER(AlbumName) = ? AND Year = ? "
                + "AND LOWER(MusicName) = ? AND PeopleInvolved_ID = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setBoolean(1, isArranger);
        statement.setString(2, albumName.toLowerCase());
        statement.setInt(3, year);
        statement.setString(4, trackName.toLowerCase());
        statement.setInt(5, personId);
        statement.executeUpdate();
        return true;
    }

    public static boolean updateMovie(Connection connection, Movie movie) {
        boolean success = false;
        try {
            connection.setAutoCommit(false);
            if (movie.needsReinsert()) {
                String oldMovieName = movie.getPrimaryKeyName();
                int oldMovieYear = movie.getPrimaryKeyYear();
                DatabaseDeleter.deleteMovie(connection, oldMovieName, oldMovieYear);
                DatabaseInserter.insertMovie(connection, movie);
            }
            connection.setAutoCommit(false);
            success = updateMovieCrewMembers(connection, movie);
            connection.commit();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
            success = false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
        return success;
    }

    protected static boolean updateMovieCrewMembers(Connection connection, Movie movie) throws SQLException {
        String movieName = movie.getName();
        int year = movie.getYear();
        List<Integer> removeCrewIds = DatabaseSelector.getMovieCrewIds(connection, movieName, year);
        DatabaseDeleter.deleteMovieCrew(connection, movieName, year);
        DatabaseDeleter.deleteMovieAward(connection, movieName, year);
        // update old crew members or insert new crew members
        for (MovieCrew member : movie.getCrewMembers()) {
            int crewId = member.getId();
            if (crewId > 0) {
                updatePerson(connection, member);
                removeCrewIds.remove(Integer.valueOf(crewId));
            }
            crewId = DatabaseInserter.insertMovieCrew(connection, movie, member);
            DatabaseInserter.insertMovieAward(connection, crewId, movie, member.getAward());
        }
        // remove discarded crew members
        for (int crewId : removeCrewIds) {
            try {
                DatabaseDeleter.deletePerson(connection, crewId);
            } catch (SQLException e) {
                continue;
            }
        }
        return true;
    }

    protected static boolean updateMovieAward(Connection connection, int personId, String movieName, int year,
            boolean award) throws SQLException {
        String sql = "UPDATE Award SET Award = ? WHERE PeopleInvolved_ID = ? AND LOWER(MovieName) = ? AND Year = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setBoolean(1, award);
        statement.setInt(2, personId);
        statement.setString(3, movieName.toLowerCase());
        statement.setInt(4, year);
        statement.executeUpdate();
        return true;
    }

}
