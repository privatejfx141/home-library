package com.hl.test;

import java.sql.Connection;
import java.sql.SQLException;

import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseInserter;
import com.hl.exceptions.DatabaseInsertException;
import com.hl.exceptions.NameFormatException;
import com.hl.record.Person;
import com.hl.record.music.MusicAlbum;
import com.hl.record.music.MusicTrack;

public class InsertMusic {

    public static void main(String args[]) throws SQLException, DatabaseInsertException, NameFormatException {
        Connection connection = DatabaseDriver.connectToDatabase();

        MusicAlbum album = new MusicAlbum.Builder()
                .setName("Songs from the Big Chair")
                .setYear(1985)
                .setProducer(Person.parseName("Chris Hughes"))
                .addTrack(new MusicTrack.Builder()
                        .setName("Everybody Wants to Rule the World")
                        .setLanguage("English")
                        .setSongwriter(Person.parseName("Roland Orzabal"))
                        .setComposer(Person.parseName("Chris Hughes"))
                        .setArranger(Person.parseName("Ian Stanley"))
                        .addSinger(Person.parseName("Curt Smith"))
                        .create())
                .addTrack(new MusicTrack.Builder()
                        .setName("Shout")
                        .setLanguage("English")
                        .setSongwriter(Person.parseName("Roland Orzabal"))
                        .setComposer(Person.parseName("Chris Hughes"))
                        .setArranger(Person.parseName("Ian Stanley"))
                        .addSinger(Person.parseName("Roland Orzabal"))
                        .addSinger(Person.parseName("Curt Smith"))
                        .create())
                .create();
        DatabaseInserter.insertMusicAlbum(connection, album);

        album = new MusicAlbum.Builder()
                .setName("The American Idiot")
                .setYear(2003)
                .setProducer(Person.parseName("Rob Cavallo"))
                .addTrack(new MusicTrack.Builder()
                        .setName("American Idiot")
                        .setSongwriter(Person.parseName("Billie Joe Armstrong"))
                        .setComposer(Person.parseName("Tre Cool"))
                        .setArranger(Person.parseName("Mike Dirnt"))
                        .addSinger(Person.parseName("Billie Joe Armstrong"))
                        .setDiskType("CD")
                        .create())
                .addTrack(new MusicTrack.Builder()
                        .setName("Jesus of Suburbia")
                        .setSongwriter(Person.parseName("Billie Joe Armstrong"))
                        .setComposer(Person.parseName("Mike Dirnt"))
                        .setArranger(Person.parseName("Tre Cool"))
                        .addSinger(Person.parseName("Billie Joe Armstrong"))
                        .create())
                .addTrack(new MusicTrack.Builder()
                        .setName("Wake Me Up When September Ends")
                        .setSongwriter(Person.parseName("Mike Dirnt"))
                        .setComposer(Person.parseName("Billie Joe Armstrong"))
                        .setArranger(Person.parseName("Tre Cool"))
                        .addSinger(Person.parseName("Billie Joe Armstrong"))
                        .create())
                .create();
        DatabaseInserter.insertMusicAlbum(connection, album);
        
        connection.close();
        System.out.println("Insertion complete");
    }
    
}
