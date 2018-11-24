package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hl.record.Person;
import com.hl.record.book.Book;
import com.hl.record.movie.Movie;
import com.hl.record.movie.MovieCrew;
import com.hl.record.music.MusicAlbum;
import com.hl.record.music.MusicTrack;

public class DatabaseSelector {

    public static int getPersonId(Connection connection, Person person) {
        int personId = -1;
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String sql = "SELECT DISTINCT ID FROM PeopleInvolved WHERE LOWER(FirstName) = ? AND LOWER(FamilyName) = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName.toLowerCase());
            statement.setString(2, lastName.toLowerCase());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                personId = results.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personId;
    }

    public static String getBookISBN(Connection connection, String bookTitle) {
        String isbn = null;
        String sql = "SELECT DISTINCT ISBN FROM Book WHERE LOWER(Title) = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bookTitle.toLowerCase());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                isbn = results.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isbn;
    }

    public static int getKeywordId(Connection connection, String keyword) {
        int keywordId = -1;
        String sql = "SELECT DISTINCT ID FROM Keyword WHERE LOWER(tag) = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, keyword.toLowerCase());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                keywordId = results.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keywordId;
    }

    public static int getMovieYear(Connection connection, String movieName) {
        int year = -1;
        String sql = "SELECT DISTINCT Year FROM Movie WHERE LOWER(MovieName) = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movieName.toLowerCase());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                year = results.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return year;
    }

    public static int getMovieRoleId(Connection connection, String role) {
        int roleId = -1;
        String sql = "SELECT DISTINCT ID FROM Role WHERE LOWER(Description) = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role.toLowerCase());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                roleId = results.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleId;
    }

    public static int getMusicAlbumYear(Connection connection, String albumName) {
        int year = -1;
        String sql = "SELECT DISTINCT Year From Music WHERE LOWER(AlbumName) = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName.toLowerCase());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                year = results.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return year;
    }

    public static Person getPerson(Connection connection, int personId) {
        Person person = null;
        String sql = "SELECT * FROM PeopleInvolved WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int id = results.getInt("ID");
                String firstName = results.getString("FirstName");
                String middleName = results.getString("MiddleName");
                String lastName = results.getString("FamilyName");
                boolean gender = results.getBoolean("Gender");
                String genderName = gender ? "Male" : "Female";
                person = new Person(id, firstName, middleName, lastName, genderName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public static Book getBook(Connection connection, String isbn) {
        Book book = null;
        String sql = "SELECT * FROM Book WHERE ISBN = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, isbn);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                String title = results.getString("Title");
                String publisher = results.getString("Publisher");
                int pages = results.getInt("NumberOfPages");
                int year = results.getInt("YearOfPublication");
                int edition = results.getInt("EditionNumber");
                edition = edition == 0 ? -1 : edition;
                String description = results.getString("Abstract");
                List<Person> authors = getBookAuthors(connection, isbn);
                List<String> keywords = getBookKeywords(connection, isbn);
                book = new Book(isbn, title, publisher, pages, year, edition, description, authors, keywords);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    public static List<Integer> getBookAuthorIds(Connection connection, String isbn) {
        List<Integer> authorIds = new ArrayList<>();
        String sql = "SELECT Author_ID FROM BookAuthor WHERE ISBN = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, isbn);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int authorId = results.getInt("Author_ID");
                authorIds.add(authorId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorIds;
    }

    public static List<Person> getBookAuthors(Connection connection, String isbn) {
        List<Person> authors = new ArrayList<>();
        for (int authorId : getBookAuthorIds(connection, isbn)) {
            Person author = getPerson(connection, authorId);
            authors.add(author);
        }
        return authors;
    }

    public static List<Integer> getBookKeywordIds(Connection connection, String isbn) {
        List<Integer> keywordIds = new ArrayList<>();
        String sql = "SELECT Keyword_ID FROM BookKeyword WHERE ISBN = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, isbn);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int keywordId = results.getInt("Keyword_ID");
                keywordIds.add(keywordId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keywordIds;
    }

    public static List<String> getBookKeywords(Connection connection, String isbn) {
        List<String> keywords = new ArrayList<>();
        for (int keywordId : getBookKeywordIds(connection, isbn)) {
            String keyword = getKeyword(connection, keywordId);
            keywords.add(keyword);
        }
        return keywords;
    }

    public static String getKeyword(Connection connection, int keywordId) {
        String keyword = null;
        String sql = "SELECT Tag FROM Keyword WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, keywordId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                keyword = results.getString("Tag");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return keyword;
    }

    public static MusicAlbum getMusicAlbum(Connection connection, String albumName, int year) {
        MusicAlbum album = null;
        Person producer = null;
        ArrayList<MusicTrack> tracks = new ArrayList<>();
        String sql = "SELECT MusicName, Language, DiskType, Producer_ID FROM Music WHERE AlbumName = ? AND Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName);
            statement.setInt(2, year);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                String trackName = results.getString("MusicName");
                String language = results.getString("Language");
                boolean diskType = results.getBoolean("DiskType");
                if (producer == null) {
                    int producerId = results.getInt("Producer_ID");
                    producer = getPerson(connection, producerId);
                }
                MusicTrack track = getMusicTrack(connection, albumName, year, trackName, language, diskType);
                tracks.add(track);
            }
            album = new MusicAlbum(albumName, year, producer, tracks);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return album;
    }

    public static MusicTrack getMusicTrack(Connection connection, String albumName, int year, String name,
            String language, boolean diskType) {
        MusicTrack track = null;
        String diskTypeName = diskType ? "Vinyl" : "CD";
        Person songwriter = getMusicCrew(connection, albumName, year, name, "Songwriter");
        Person composer = getMusicCrew(connection, albumName, year, name, "Composer");
        Person arranger = getMusicCrew(connection, albumName, year, name, "Arranger");
        track = new MusicTrack(name, language, songwriter, composer, arranger, diskTypeName);
        return track;
    }

    public static List<Integer> getMusicCrewIds(Connection connection, String albumName, int year) {
        List<Integer> crewIds = new ArrayList<>();
        // add people involved music
        String sql = "SELECT DISTINCT PeopleInvolved_ID FROM PeopleInvolvedMusic WHERE LOWER(AlbumName) = ? AND YEAR = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName.toLowerCase());
            statement.setInt(2, year);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int crewId = results.getInt("PeopleInvolved_ID");
                crewIds.add(crewId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // add music singers
        sql = "SELECT DISTINCT PeopleInvolved_ID FROM MusicSinger WHERE LOWER(AlbumName) = ? AND YEAR = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName.toLowerCase());
            statement.setInt(2, year);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int crewId = results.getInt("PeopleInvolved_ID");
                crewIds.add(crewId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // add producer
        sql = "SELECT DISTINCT Producer_ID FROM Music WHERE LOWER(AlbumName) = ? AND YEAR = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName.toLowerCase());
            statement.setInt(2, year);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int crewId = results.getInt("Producer_ID");
                crewIds.add(crewId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crewIds;
    }

    public static Person getMusicCrew(Connection connection, String albumName, int year, String trackName,
            String musicRole) {
        Person crew = null;
        String sql = "SELECT PeopleInvolved_ID FROM PeopleInvolvedMusic WHERE AlbumName = ? AND Year = ? AND MusicName = ?";
        // if song writer
        if (musicRole.startsWith("S")) {
            sql += " AND isSongwriter";
        }
        // if composer
        if (musicRole.startsWith("C")) {
            sql += " AND isComposer";
        }
        // if arranger
        if (musicRole.startsWith("A")) {
            sql += " AND isArranger";
        }
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName);
            statement.setInt(2, year);
            statement.setString(3, trackName);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int personId = results.getInt("PeopleInvolved_ID");
                crew = getPerson(connection, personId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crew;
    }

    public static boolean hasMusicCrew(Connection connection, String albumName, int year, String trackName, int personId) {
        String sql = "SELECT * FROM PeopleInvolvedMusic WHERE LOWER(AlbumName) = ? "
                + "AND Year = ? AND LOWER(MusicName) = ? And PeopleInvolved_ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName.toLowerCase());
            statement.setInt(2, year);
            statement.setString(3, trackName.toLowerCase());
            statement.setInt(4, personId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Person> getMusicSingers(Connection connection, String albumName, int year, String trackName) {
        List<Person> singers = new ArrayList<>();
        String sql = "SELECT PeopleInvolved_ID FROM MusicSinger WHERE AlbumName = ? AND Year = ? AND MusicName = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName);
            statement.setInt(2, year);
            statement.setString(3, trackName);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int personId = results.getInt("PeopleInvolved_ID");
                Person singer = getPerson(connection, personId);
                singers.add(singer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return singers;
    }

    public static Movie getMovie(Connection connection, String movieName, int year) {
        Movie movie = null;
        // get all crew members
        HashMap<String, ArrayList<MovieCrew>> crewMembers = new HashMap<>();
        for (String role : MovieCrew.ROLES) {
            crewMembers.put(role, new ArrayList<>());
        }
        String sql = "SELECT * FROM CrewMember WHERE MovieName = ? AND ReleaseYear = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movieName);
            statement.setInt(2, year);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int personId = results.getInt("PeopleInvolved_ID");
                int roleId = results.getInt("Role_ID");
                MovieCrew crew = getMovieCrew(connection, personId, movieName, year, roleId);
                crewMembers.get(crew.getRole()).add(crew);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        movie = new Movie(movieName, year, crewMembers);
        return movie;
    }

    public static List<Integer> getMovieCrewIds(Connection connection, String movieName, int year) {
        List<Integer> crewIds = new ArrayList<>();
        String sql = "SELECT PeopleInvolved_ID FROM CrewMember WHERE LOWER(MovieName) = ? AND Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movieName.toLowerCase());
            statement.setInt(2, year);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int crewId = results.getInt("PeopleInvolved_ID");
                crewIds.add(crewId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return crewIds;
    }

    public static MovieCrew getMovieCrew(Connection connection, int personId, String movieName, int year, int roleId) {
        MovieCrew crew = null;
        try {
            Person person = getPerson(connection, personId);
            int id = person.getId();
            String firstName = person.getFirstName();
            String middleName = person.getMiddleName();
            String lastName = person.getLastName();
            String gender = person.getGender();
            String role = getMovieRole(connection, roleId);
            boolean award = getMovieAward(connection, personId, movieName, year);
            crew = new MovieCrew(id, firstName, middleName, lastName, gender, role, award);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return crew;
    }

    public static String getMovieRole(Connection connection, int roleId) {
        String role = null;
        String sql = "SELECT Description FROM Role WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, roleId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                role = results.getString("Description");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    public static boolean getMovieAward(Connection connection, int personId, String movieName, int year) {
        boolean award = false;
        String sql = "SELECT Award FROM Award WHERE PeopleInvolved_ID = ? AND MovieName = ? And Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);
            statement.setString(2, movieName);
            statement.setInt(3, year);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                award = results.getBoolean("Award");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return award;
    }

    public static boolean hasMovieAward(Connection connection, int personId, String movieName, int year) {
        String sql = "SELECT * FROM Award WHERE PeopleInvolved_ID = ? AND MovieName = ? And Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);
            statement.setString(2, movieName);
            statement.setInt(3, year);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
