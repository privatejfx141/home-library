package com.hl.test;

import static com.hl.record.movie.MovieCrew.*;

import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseInserter;
import com.hl.exceptions.DatabaseInsertException;
import com.hl.record.movie.Movie;
import com.hl.record.movie.MovieCrew;

import java.sql.Connection;
import java.sql.SQLException;

public class InsertMovie {
    
    public static void main(String[] args) throws DatabaseInsertException, SQLException {
        
        Connection connection = DatabaseDriver.connectToDatabase();
        
        for (String role : MovieCrew.ROLES) {
            DatabaseInserter.insertMovieRole(connection, role);
        }
        
        Movie movie = new Movie.Builder()
                .setName("Captain Philips")
                .setYear(2013)
                .addCrew(MovieCrew.parseName("Paul Greengrass", DIRECTOR, "Male", true))
                .addCrew(MovieCrew.parseName("Paul Greengrass", PRODUCER, "Male", true))
                .addCrew(MovieCrew.parseName("Tom Hanks", CAST, "Male", true))
                .addCrew(MovieCrew.parseName("Christopher Rouse", EDITOR, "Male", false))
                .addCrew(MovieCrew.parseName("Henry Jackman", COMPOSER, "Male", false))
                .addCrew(MovieCrew.parseName("Christopher Rouse", SCRIPTWRITER, "Male", false))
                .addCrew(MovieCrew.parseName("Christopher Rouse", COSTUME_DESIGNER, "Male", false))
                .create();
        DatabaseInserter.insertMovie(connection, movie);

        movie = new Movie.Builder()
                .setName("Saving Private Ryan")
                .setYear(1998)
                .addCrew(MovieCrew.parseName("Steven Spielberg", DIRECTOR, "Male", true))
                .addCrew(MovieCrew.parseName("John Williams", COMPOSER, "Male", true))
                .addCrew(MovieCrew.parseName("Ian Bryce", SCRIPTWRITER, "Male", false))
                .addCrew(MovieCrew.parseName("Robert Rodat", PRODUCER, "Male", false))
                .addCrew(MovieCrew.parseName("Tom Hanks", CAST, "Male", true))
                .addCrew(MovieCrew.parseName("Tom Hanks", EDITOR, "Male", false))
                .addCrew(MovieCrew.parseName("Christopher Rouse", SCRIPTWRITER, "Male", false))
                .addCrew(MovieCrew.parseName("Steven Spielberg", COSTUME_DESIGNER, "Male", true))
                .create();
        DatabaseInserter.insertMovie(connection, movie);
        
        movie = new Movie.Builder()
                .setName("The Black Hawk Down")
                .setYear(2003)
                .addCrew(MovieCrew.parseName("Ridley Scott", DIRECTOR, "Male", true))
                .addCrew(MovieCrew.parseName("Jerry Bruckheimer", PRODUCER, "Male", false))
                .addCrew(MovieCrew.parseName("Ridley Scott", PRODUCER, "Male", false))
                .addCrew(MovieCrew.parseName("Steven Spielberg", COSTUME_DESIGNER, "Male", true))
                .addCrew(MovieCrew.parseName("Ken Nolan", SCRIPTWRITER, "Male", false))
                .addCrew(MovieCrew.parseName("Josh Hartnett", CAST, "Male", true))
                .addCrew(MovieCrew.parseName("Ewan McGregor", CAST, "Male", false))
                .addCrew(MovieCrew.parseName("Tom Sizemore", CAST, "Male", false))
                .addCrew(MovieCrew.parseName("Orlando Bloom", CAST, "Male", false))
                .addCrew(MovieCrew.parseName("Tom Hardy", CAST, "Male", false))
                .addCrew(MovieCrew.parseName("Nikolaj Coster-Waldau", CAST, "Male", false))
                .addCrew(MovieCrew.parseName("Hans Zimmer", COMPOSER, "Male", false))
                .addCrew(MovieCrew.parseName("Pietro Scalia", EDITOR, "Male", false))
                .create();
        DatabaseInserter.insertMovie(connection, movie);

        connection.close();
        System.out.println("Insertion complete");

    }

}
