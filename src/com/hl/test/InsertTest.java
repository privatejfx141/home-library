package com.hl.test;

import static com.hl.record.movie.MovieCrew.CAST;
import static com.hl.record.movie.MovieCrew.COMPOSER;
import static com.hl.record.movie.MovieCrew.COSTUME_DESIGNER;
import static com.hl.record.movie.MovieCrew.DIRECTOR;
import static com.hl.record.movie.MovieCrew.EDITOR;
import static com.hl.record.movie.MovieCrew.PRODUCER;
import static com.hl.record.movie.MovieCrew.SCRIPTWRITER;

import java.sql.Connection;
import java.sql.SQLException;

import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseInserter;
import com.hl.exceptions.DatabaseInsertException;
import com.hl.exceptions.NameFormatException;
import com.hl.record.Person;
import com.hl.record.book.Book;
import com.hl.record.movie.Movie;
import com.hl.record.movie.MovieCrew;
import com.hl.record.music.MusicAlbum;
import com.hl.record.music.MusicTrack;

public class InsertTest {

    public static void main(String[] args) throws DatabaseInsertException, SQLException, NameFormatException {
        Connection connection = DatabaseDriver.connectToDatabase();

        // add roles
        for (String role : MovieCrew.ROLES) {
            DatabaseInserter.insertMovieRole(connection, role);
        }

        insertBooks(connection);
        insertMusic(connection);
        insertMovies(connection);
        connection.close();
    }

    public static void insertBooks(Connection connection) throws DatabaseInsertException, NameFormatException {
        Book book = new Book.Builder() //
                .setIsbn("9780521402309") //
                .setTitle("The Great Gatsby") //
                .setPublisher("Bodley Head") //
                .setYearOfPublication(1967) //
                .setNumberOfPages(191) //
                .addAuthor(Person.parseName("Francis Scott Fitzgerald")) //
                .setDescription("The Great Gatsby book description") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder() //
                .setIsbn("0000871299208") //
                .setTitle("To Kill a Mockingbird") //
                .setPublisher("J. B. Lippincott & Co.") //
                .setYearOfPublication(1960) //
                .setNumberOfPages(281) //
                .addAuthor(Person.parseName("Harper Lee")) //
                .addAuthor(Person.parseName("Christopher Sergel")) //
                .setDescription("To Kill a Mockingbird book description") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder() //
                .setIsbn("0006021637887") //
                .setTitle("Go Set a Watchman") //
                .setPublisher("HarperCollins") //
                .setYearOfPublication(2015) //
                .setNumberOfPages(278) //
                .addAuthor(Person.parseName("Harper Lee")) //
                .setDescription("Go Set a Watchman book description") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder() //
                .setIsbn("0000870212850") //
                .setTitle("The Hunt for Red October") //
                .setPublisher("Naval Institute Press") //
                .setYearOfPublication(1984) //
                .setNumberOfPages(387) //
                .addAuthor(Person.parseName("Tom Clancy")) //
                .setDescription("The Hunt for Red October book description") //
                .addKeyword("Jack Ryan") //
                .addKeyword("Military") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder() //
                .setIsbn("0000399132414") //
                .setTitle("Patriot Games") //
                .setPublisher("G.P. Putnam's Sons") //
                .setYearOfPublication(1987) //
                .setNumberOfPages(540) //
                .addAuthor(Person.parseName("Tom Clancy")) //
                .setDescription("Patriot Games book description") //
                .addKeyword("Jack Ryan") //
                .addKeyword("Military") //
                .addKeyword("Thriller") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder() //
                .setIsbn("0-307-34660-9") //
                .setTitle("World War Z") //
                .setPublisher("Crown") //
                .setYearOfPublication(2006) //
                .setNumberOfPages(342) //
                .addAuthor(Person.parseName("Max Brooks")) //
                .setDescription("World War Z book description Thriller") //
                .addKeyword("Horror") //
                .addKeyword("Zombies") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder() //
                .setIsbn("1-4000-4962-8") //
                .setTitle("The Zombie Survival Guide") //
                .setPublisher("Crown") //
                .setYearOfPublication(2003) //
                .setNumberOfPages(272) //
                .addAuthor(Person.parseName("Max Brooks")) //
                .setDescription("The Zombie Survival Guide book description") //
                .addKeyword("Horror") //
                .addKeyword("Zombies") //
                .addKeyword("Humour") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder() //
                .setIsbn("9780307464972") //
                .setTitle("The Harlem Hellfighters") //
                .setPublisher("Crown") //
                .setYearOfPublication(2014) //
                .setNumberOfPages(272) //
                .addAuthor(Person.parseName("Max Brooks")) //
                .addAuthor(Person.parseName("Caanan White")) //
                .setDescription("The Harlem Hellfighters book description") //
                .addKeyword("Military") //
                .addKeyword("Graphic novel") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder() //
                .setIsbn("0-7475-5100-6") //
                .setTitle("Harry Potter and the Order of the Phoenix") //
                .setPublisher("Scholastic") //
                .setYearOfPublication(2003) //
                .setNumberOfPages(870) //
                .addAuthor(Person.parseName("J. K. Rowling")) //
                .addAuthor(Person.parseName("Jason Hanks")) //
                .setDescription("Harry Potter and the Order of the Phoenix book description") //
                .addKeyword("Fantasy") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder() //
                .setIsbn("A000000000001") //
                .setTitle("Test Book 1") //
                .setPublisher("Dummy Publisher") //
                .setYearOfPublication(2006) //
                .setNumberOfPages(281) //
                .addAuthor(Person.parseName("Jack Doeverson")) //
                .setDescription("Test Book 1 book description Thriller") //
                .addKeyword("Test") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder() //
                .setIsbn("A000000000002") //
                .setTitle("Test Book 2") //
                .setPublisher("Dummy Publisher") //
                .setYearOfPublication(2007) //
                .setNumberOfPages(265) //
                .addAuthor(Person.parseName("Johnson Doeverson")) //
                .setDescription("Test Book 2 book description Thriller") //
                .addKeyword("Test") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        book = new Book.Builder() //
                .setIsbn("A000000000003") //
                .setTitle("Test Book 3") //
                .setPublisher("Dummy Publisher") //
                .setYearOfPublication(2008) //
                .setNumberOfPages(281) //
                .addAuthor(Person.parseName("Bob Doeverson")) //
                .setDescription("Test Book 3 book description Thriller") //
                .addKeyword("Test") //
                .create();
        DatabaseInserter.insertBook(connection, book);

        System.out.println("Book insertion complete");
    }

    public static void insertMusic(Connection connection) throws DatabaseInsertException, NameFormatException {
        MusicAlbum album = new MusicAlbum.Builder() //
                .setName("Songs from the Big Chair") //
                .setYear(1985) //
                .setProducer("Chris Hughes") //
                .addTrack(new MusicTrack.Builder() //
                        .setName("Everybody Wants to Rule the World") //
                        .setLanguage("English") //
                        .setSongwriter("Roland Orzabal") //
                        .setComposer("Chris Hughes") //
                        .setArranger("Ian Stanley") //
                        .addSinger("Curt Smith") //
                        .create()) //
                .addTrack(new MusicTrack.Builder() //
                        .setName("Shout") //
                        .setLanguage("English") //
                        .setSongwriter("Chris Hughes") //
                        .setComposer("Chris Hughes") //
                        .setArranger("Ian Stanley") //
                        .addSinger("Roland Orzabal") //
                        .addSinger("Curt Smith") //
                        .create()) //
                .create();
        DatabaseInserter.insertMusicAlbum(connection, album);

        album = new MusicAlbum.Builder() //
                .setName("American Idiot") //
                .setYear(2003) //
                .setProducer("Rob Cavallo") //
                .addTrack(new MusicTrack.Builder() //
                        .setName("American Idiot") //
                        .setSongwriter("Billie Joe Armstrong") //
                        .setComposer("Tre Cool") //
                        .setArranger("Mike Dirnt") //
                        .addSinger("Billie Joe Armstrong") //
                        .setDiskType("CD") //
                        .create()) //
                .addTrack(new MusicTrack.Builder() //
                        .setName("Jesus of Suburbia") //
                        .setSongwriter("Mike Dirnt") //
                        .setComposer("Mike Dirnt") //
                        .setArranger("Mike Dirnt") //
                        .addSinger("Billie Joe Armstrong") //
                        .create()) //
                .addTrack(new MusicTrack.Builder() //
                        .setName("Wake Me Up When September Ends") //
                        .setSongwriter("Billie Joe Armstrong") //
                        .setComposer("Billie Joe Armstrong") //
                        .setArranger("Tre Cool") //
                        .addSinger("Billie Joe Armstrong") //
                        .create()) //
                .addTrack(new MusicTrack.Builder() //
                        .setName("Whatsername") //
                        .setSongwriter("Billie Joe Armstrong") //
                        .setComposer("Mike Dirnt") //
                        .setArranger("Tre Hanks") //
                        .addSinger("Billie Joe Armstrong") //
                        .addSinger("Mike Dirnt") //
                        .create()) //
                .create();
        DatabaseInserter.insertMusicAlbum(connection, album);

        album = new MusicAlbum.Builder() //
                .setName("21st Century Breakdown") //
                .setYear(2009) //
                .setProducer("Butch Vig") //
                .addTrack(new MusicTrack.Builder() //
                        .setName("21 Guns") //
                        .setSongwriter("Billie Joe Hanks") //
                        .setComposer("Billie Joe Armstrong") //
                        .setArranger("Billie Joe Armstrong") //
                        .addSinger("Billie Joe Armstrong") //
                        .create()) //
                .addTrack(new MusicTrack.Builder() //
                        .setName("Whatsername") //
                        .setSongwriter("Billie Joe Armstrong") //
                        .setComposer("Mike Dirnt") //
                        .setArranger("Tre Hanks") //
                        .addSinger("Tre Hanks") //
                        .addSinger("Billie Dirnt") //
                        .create()) //
                .create();
        DatabaseInserter.insertMusicAlbum(connection, album);

        System.out.println("Music insertion complete");
    }

    public static void insertMovies(Connection connection) throws DatabaseInsertException, NameFormatException {
        Movie movie = new Movie.Builder() //
                .setName("Captain Philips") //
                .setYear(2013) //
                .addCrewMember(MovieCrew.parseName("Paul Greengrass", DIRECTOR, "Male", true)) //
                .addCrewMember(MovieCrew.parseName("Tom Sizemore", PRODUCER, "Male", true)) //
                .addCrewMember(MovieCrew.parseName("Tom Hanks", CAST, "Male", true)) //
                .addCrewMember(MovieCrew.parseName("Christopher Rouse", EDITOR, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Henry Jackman", COMPOSER, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Ken Nolan", SCRIPTWRITER, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Christopher Rouse", COSTUME_DESIGNER, "Male", false)) //
                .create();
        DatabaseInserter.insertMovie(connection, movie);

        movie = new Movie.Builder() //
                .setName("Saving Private Ryan") //
                .setYear(1998) //
                .addCrewMember(MovieCrew.parseName("Steven Spielberg", DIRECTOR, "Male", true)) //
                .addCrewMember(MovieCrew.parseName("John Williams", COMPOSER, "Male", true)) //
                .addCrewMember(MovieCrew.parseName("Ian Bryce", SCRIPTWRITER, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Robert Rodat", PRODUCER, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Tom Hanks", CAST, "Male", true)) //
                .addCrewMember(MovieCrew.parseName("Ken Nolan", EDITOR, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Christopher Rouse", SCRIPTWRITER, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Steven Spielberg", COSTUME_DESIGNER, "Male", true)) //
                .create();
        DatabaseInserter.insertMovie(connection, movie);

        movie = new Movie.Builder() //
                .setName("The Black Hawk Down") //
                .setYear(2003) //
                .addCrewMember(MovieCrew.parseName("Ridley Scott", DIRECTOR, "Male", true)) //
                .addCrewMember(MovieCrew.parseName("Jerry Bruckheimer", PRODUCER, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Ridley Scott", PRODUCER, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Steven Spielberg", COSTUME_DESIGNER, "Male", true)) //
                .addCrewMember(MovieCrew.parseName("Ken Nolan", SCRIPTWRITER, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Josh Hartnett", CAST, "Male", true)) //
                .addCrewMember(MovieCrew.parseName("Ewan McGregor", CAST, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Tom Sizemore", CAST, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Orlando Bloom", CAST, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Tom Hardy", CAST, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Nikolaj Coster-Waldau", CAST, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Hans Zimmer", COMPOSER, "Male", false)) //
                .addCrewMember(MovieCrew.parseName("Pietro Scalia", EDITOR, "Male", false)) //
                .create();
        DatabaseInserter.insertMovie(connection, movie);

        System.out.println("Movie insertion complete");
    }
}
