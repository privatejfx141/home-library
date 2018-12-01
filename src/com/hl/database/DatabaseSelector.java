package com.hl.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hl.record.Person;
import com.hl.record.book.Book;
import com.hl.record.movie.Movie;
import com.hl.record.movie.MovieCrew;
import com.hl.record.music.MusicAlbum;
import com.hl.record.music.MusicTrack;

/**
 * Collection of methods for querying product data from the HL database.
 * 
 * @author Yun Jie (Jeffrey) Li
 */
public class DatabaseSelector {

    /**
     * Returns the product data (book, music album, movie) for the given product
     * name if it exists. Otherwise, returns <code>null</code> if such product data
     * does not exist.
     * 
     * @param connection  Connection to the HL database.
     * @param productName Name of the product to search for.
     * @return Product data if exists, <code>null</code> otherwise.
     */
    public static Object getProduct(Connection connection, String productName) {
        // get book if exists
        String bookISBN = getBookISBN(connection, productName);
        if (bookISBN != null) {
            return getBook(connection, bookISBN);
        }
        // get music album if exists
        int year = getMusicAlbumYear(connection, productName);
        if (year > 0) {
            return getMusicAlbum(connection, productName, year);
        }
        // get movie if exists
        year = getMovieYear(connection, productName);
        if (year > 0) {
            return getMovie(connection, productName, year);
        }
        return null;
    }

    /**
     * Returns the ID number of the record in the PeopleInvolved table that matches
     * the given data. Otherwise, returns <code>-1</code> if such a record does not
     * exist.
     * 
     * @param connection Connection to the HL database.
     * @param person     Person data to search ID for.
     * @return Record ID of the person if exists in database, <code>-1</code>
     *         otherwise.
     */
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

    /**
     * Returns <code>true</code> if the record for the person with the given ID
     * exists in the PeopleInvolved table.
     * 
     * @param connection Connection to the HL database.
     * @param personId   Person data to search ID for.
     * @return <code>true</code> if the person exists, <code>false</code> otherwise.
     */
    public static boolean personExists(Connection connection, int personId) {
        boolean exists = false;
        String sql = "SELECT * FROM PeopleInvolved WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * Returns ISBN of the book with the given title if it exists. Otherwise,
     * returns <code>null</code>.
     * 
     * @param connection Connection to the HL database.
     * @param bookTitle  Title of the book.
     * @return ISBN of the book, <code>null</code> otherwise.
     */
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

    /**
     * Returns the ID of the record under the Keyword table with the given keyword
     * if it exists. Otherwise, returns <code>-1</code>.
     * 
     * @param connection Connection to the HL database.
     * @param keyword    Keyword to search ID for.
     * @return Record ID of the keyword if exists, <code>-1</code> otherwise.
     */
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

    /**
     * Returns the year of release of the given music album if it's record exists in
     * the Music table. Otherwise, returns <code>-1</code>.
     * 
     * @param connection Connection to the HL database.
     * @param albumName  Title of the music album.
     * @return Year of release of the given music album if exists, <code>-1</code>
     *         otherwise.
     */
    public static int getMusicAlbumYear(Connection connection, String albumName) {
        int year = -1;
        String sql = "SELECT DISTINCT Year From Music WHERE LOWER(AlbumName) = ? ORDER BY Year LIMIT 1";
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

    /**
     * Returns the year of release of the given movie if it's record exists in the
     * Movie table. Otherwise, returns <code>-1</code>.
     * 
     * @param connection Connection to the HL database.
     * @param movieName  Title of the movie.
     * @return Year of release of the given movie if exists, <code>-1</code>
     *         otherwise.
     */
    public static int getMovieYear(Connection connection, String movieName) {
        int year = -1;
        String sql = "SELECT DISTINCT Year FROM Movie WHERE LOWER(MovieName) = ? ORDER BY Year LIMIT 1";
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

    /**
     * Returns the record ID of the people involved role as it appears in the Role
     * table if it exists. Otherwise, returns <code>-1</code>.
     * 
     * @param connection Connection to the HL database.
     * @param role       Movie crew role type to search ID for.
     * @return Record ID of role if exists, <code>-1<code> otherwise.
     */
    public static int getRoleId(Connection connection, String role) {
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

    /**
     * Returns the person data under the PeopleInvolved table given the ID for that
     * record if it exists. Otherwise, returns <code>null</code>.
     * 
     * @param connection Connection to the HL database.
     * @param personId   ID of the person's record in the PeopleInvolved table.
     * @return Person data if exists, <code>null</code> otherwise.
     */
    public static Person getPerson(Connection connection, int personId) {
        Person person = null;
        String sql = "SELECT * FROM PeopleInvolved WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                person = new Person.Builder() //
                        .setId(results.getInt("ID")) //
                        .setFirstName(results.getString("FirstName")) //
                        .setMiddleName(results.getString("MiddleName")) //
                        .setLastName(results.getString("FamilyName")) //
                        .setGender(results.getBoolean("Gender")) //
                        .create();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    /**
     * View -> Query -> Book <br/>
     * Returns the data for a book with the given ISBN if it exists. Otherwise,
     * returns <code>null</code>.
     * 
     * @param connection Connection to the HL database.
     * @param isbn       ISBN of the book.
     * @return Book data object if ISBN exists, <code>null</code> otherwise.
     */
    public static Book getBook(Connection connection, String isbn) {
        Book book = null;
        String sql = "SELECT * FROM Book WHERE ISBN = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, isbn);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                book = new Book.Builder() //
                        .setIsbn(results.getString("ISBN")) //
                        .setTitle(results.getString("Title")) //
                        .setPublisher(results.getString("Publisher")) //
                        .setNumberOfPages(results.getInt("NumberOfPages")) //
                        .setYearOfPublication(results.getInt("YearOfPublication")) //
                        .setEditionNumber(results.getInt("EditionNumber")) //
                        .setDescription(results.getString("Abstract")) //
                        .addAuthors(getBookAuthors(connection, isbn)) //
                        .addKeywords(getBookKeywords(connection, isbn)) //
                        .create();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

    /**
     * 
     * @param connection Connection to the HL database.
     * @param isbn       ISBN of the book.
     * @return
     */
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

    /**
     * 
     * @param connection Connection to the HL database.
     * @param isbn       ISBN of the book.
     * @return
     */
    public static List<Person> getBookAuthors(Connection connection, String isbn) {
        List<Person> authors = new ArrayList<>();
        for (int authorId : getBookAuthorIds(connection, isbn)) {
            Person author = getPerson(connection, authorId);
            authors.add(author);
        }
        return authors;
    }

    /**
     * 
     * @param connection Connection to the HL database.
     * @param isbn       ISBN of the book.
     * @return
     */
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

    /**
     * 
     * @param connection Connection to the HL database.
     * @param isbn       ISBN of the book.
     * @return
     */
    public static List<String> getBookKeywords(Connection connection, String isbn) {
        List<String> keywords = new ArrayList<>();
        for (int keywordId : getBookKeywordIds(connection, isbn)) {
            String keyword = getKeyword(connection, keywordId);
            keywords.add(keyword);
        }
        return keywords;
    }

    /**
     * 
     * @param connection Connection to the HL database.
     * @param keywordId  ID of the keyword's record in the Keyword table.
     * @return
     */
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

    /**
     * Returns the data for the music album with the given music album name and
     * release year if it exists. Otherwise, returns <code>null</code>.
     * 
     * @param connection Connection to the HL database.
     * @param albumName  Title of the music album.
     * @param year       Year of when the product was published or released.
     * @return Music album data if exists, <code>null</code> otherwise.
     */
    public static MusicAlbum getMusicAlbum(Connection connection, String albumName, int year) {
        MusicAlbum album = null;
        Person producer = null;
        ArrayList<MusicTrack> tracks = new ArrayList<>();
        String sql = "SELECT AlbumName, MusicName, Language, DiskType, Producer_ID FROM Music "
                + "WHERE LOWER(AlbumName) = ? AND Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName.toLowerCase());
            statement.setInt(2, year);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                albumName = results.getString("AlbumName");
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
            album = new MusicAlbum.Builder() //
                    .setName(albumName) //
                    .setYear(year) //
                    .addTracks(tracks) //
                    .setProducer(producer) //
                    .create();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return album;
    }

    /**
     * 
     * @param connection Connection to the HL database.
     * @param albumName  Title of the music album.
     * @param year       Year of when the product was published or released.
     * @param trackName  Name of the music track.
     * @param language
     * @param diskType
     * @return
     */
    public static MusicTrack getMusicTrack(Connection connection, String albumName, int year, String trackName,
            String language, boolean diskType) {
        MusicTrack track = null;
        // get music crew
        Person songwriter = getMusicCrew(connection, albumName, year, trackName, "Songwriter");
        Person composer = getMusicCrew(connection, albumName, year, trackName, "Composer");
        Person arranger = getMusicCrew(connection, albumName, year, trackName, "Arranger");
        // get music singers
        ArrayList<Person> singers = new ArrayList<>();
        String sql = "SELECT PeopleInvolved_ID FROM MusicSinger WHERE LOWER(AlbumName) = ? AND Year = ? "
                + "AND LOWER(MusicName) = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName.toLowerCase());
            statement.setInt(2, year);
            statement.setString(3, trackName.toLowerCase());
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int singerId = results.getInt("PeopleInvolved_ID");
                Person singer = getPerson(connection, singerId);
                singers.add(singer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // create and return music track
        track = new MusicTrack.Builder() //
                .setName(trackName) //
                .setSongwriter(songwriter) //
                .setComposer(composer) //
                .setArranger(arranger) //
                .addSingers(singers) //
                .setDiskType(diskType) //
                .create();
        return track;
    }

    /**
     * 
     * @param connection Connection to the HL database.
     * @param albumName  Title of the music album.
     * @param year       Year of when the product was published or released.
     * @return
     */
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

    /**
     * 
     * @param connection Connection to the HL database.
     * @param albumName  Title of the music album.
     * @param year       Year of when the product was published or released.
     * @param trackName  Name of the music track.
     * @param musicRole  Role type of the music crew member ["Arranger, "Composer",
     *                   "Song writer"].
     * @return
     */
    public static Person getMusicCrew(Connection connection, String albumName, int year, String trackName,
            String musicRole) {
        Person crew = null;
        String sql = "SELECT PeopleInvolved_ID FROM PeopleInvolvedMusic WHERE LOWER(AlbumName) = ? "
                + "AND Year = ? AND LOWER(MusicName) = ?";
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
            statement.setString(1, albumName.toLowerCase());
            statement.setInt(2, year);
            statement.setString(3, trackName.toLowerCase());
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

    /**
     * 
     * @param connection Connection to the HL database.
     * @param albumName  Title of the music album.
     * @param year       Year of when the product was published or released.
     * @param trackName  Name of the music track.
     * @param personId   ID of the person's record in the PeopleInvolved table.
     * @return
     */
    public static boolean isMusicCrew(Connection connection, String albumName, int year, String trackName,
            int personId) {
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

    /**
     * 
     * @param connection Connection to the HL database.
     * @param albumName  Title of the music album.
     * @param year       Year of when the product was published or released.
     * @param trackName  Name of the music track.
     * @return
     */
    public static List<Person> getMusicSingers(Connection connection, String albumName, int year, String trackName) {
        List<Person> singers = new ArrayList<>();
        String sql = "SELECT PeopleInvolved_ID FROM MusicSinger WHERE LOWER(AlbumName) = ? "
                + "AND Year = ? AND LOWER(MusicName) = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, albumName.toLowerCase());
            statement.setInt(2, year);
            statement.setString(3, trackName.toLowerCase());
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

    /**
     * Returns the movie data with the given movie name and release year if it
     * exists. Otherwise, returns <code>null</code>.
     * 
     * @param connection Connection to the HL database.
     * @param movieName  Title of the movie.
     * @param year       Year of when the product was published or released.
     * @return Movie data if it exists, <code>null</code> otherwise.
     */
    public static Movie getMovie(Connection connection, String movieName, int year) {
        Movie movie = null;
        // get all crew members
        ArrayList<MovieCrew> crewMembers = new ArrayList<>();
        String sql = "SELECT * FROM CrewMember WHERE LOWER(MovieName) = ? AND ReleaseYear = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, movieName.toLowerCase());
            statement.setInt(2, year);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                movieName = results.getString("MovieName");
                int personId = results.getInt("PeopleInvolved_ID");
                int roleId = results.getInt("Role_ID");
                MovieCrew crew = getMovieCrew(connection, personId, movieName, year, roleId);
                crewMembers.add(crew);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        movie = new Movie.Builder() //
                .setName(movieName) //
                .setYear(year) //
                .addCrewMembers(crewMembers) //
                .create();
        return movie;
    }

    /**
     * 
     * @param connection Connection to the HL database.
     * @param movieName  Title of the movie.
     * @param year       Year of when the product was published or released.
     * @return
     */
    public static List<Integer> getMovieCrewIds(Connection connection, String movieName, int year) {
        List<Integer> crewIds = new ArrayList<>();
        String sql = "SELECT PeopleInvolved_ID FROM CrewMember WHERE LOWER(MovieName) = ? AND ReleaseYear = ?";
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

    /**
     * 
     * @param connection Connection to the HL database.
     * @param personId   ID of the person's record in the PeopleInvolved table.
     * @param movieName  Title of the movie.
     * @param year       Year of when the product was published or released.
     * @param roleId     ID of the crew member role type in the Role table.
     * @return
     */
    public static MovieCrew getMovieCrew(Connection connection, int personId, String movieName, int year, int roleId) {
        MovieCrew crew = null;
        try {
            crew = new MovieCrew.Builder() //
                    .setPerson(getPerson(connection, personId)) //
                    .setRole(getMovieRole(connection, roleId)) //
                    .hasAward(getMovieAward(connection, personId, movieName, year)) //
                    .create();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return crew;
    }

    /**
     * 
     * @param connection Connection to the HL database.
     * @param roleId     ID of the crew member role type in the Role table.
     * @return
     */
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

    /**
     * 
     * @param connection Connection to the HL database.
     * @param personId   ID of the person's record in the PeopleInvolved table.
     * @param movieName  Title of the movie.
     * @param year       Year of when the product was published or released.
     * @return
     */
    public static boolean getMovieAward(Connection connection, int personId, String movieName, int year) {
        boolean award = false;
        String sql = "SELECT Award FROM Award WHERE PeopleInvolved_ID = ? AND LOWER(MovieName) = ? And Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);
            statement.setString(2, movieName.toLowerCase());
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

    /**
     * 
     * @param connection Connection to the HL database.
     * @param personId   ID of the person's record in the PeopleInvolved table.
     * @param movieName  Title of the movie.
     * @param year       Year of when the product was published or released.
     * @return
     */
    public static boolean hasMovieAward(Connection connection, int personId, String movieName, int year) {
        String sql = "SELECT * FROM Award WHERE PeopleInvolved_ID = ? AND LOWER(MovieName) = ? And Year = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, personId);
            statement.setString(2, movieName.toLowerCase());
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
