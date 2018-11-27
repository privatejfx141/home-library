package com.hl.record.music;

import java.util.ArrayList;
import java.util.Collection;

import com.hl.exceptions.NameFormatException;
import com.hl.gui.HomeLibrary;
import com.hl.record.Person;

public class MusicAlbum {

    private String name = "";
    private int year = -1;
    private Person producer;
    private ArrayList<MusicTrack> tracks;

    public static class Builder {
        private MusicAlbum album = new MusicAlbum();

        public Builder setName(String name) {
            try {
                album.name = name.trim();
            } catch (NullPointerException e) {
                album.name = "";
            }
            return this;
        }

        public Builder setYear(int year) {
            album.year = year;
            return this;
        }

        public Builder setYear(String year) throws NumberFormatException {
            try {
                year = year.trim();
                setYear(Integer.parseInt(year));
            } catch (NullPointerException | NumberFormatException e) {
                String error = String.format(HomeLibrary.INTEGER_FIELD_MSG, "Year of release");
                throw new NumberFormatException(error);
            }
            return this;
        }

        public Builder setProducer(Person producer) {
            album.producer = producer;
            return this;
        }

        public Builder setProducer(String producer) throws NameFormatException {
            try {
                album.producer = Person.parseName(producer);
            } catch (NameFormatException e) {
                String error = "Producer name is not a proper name.";
                throw new NameFormatException(error);
            }
            return this;
        }

        public Builder addTrack(MusicTrack track) {
            album.tracks.add(track);
            return this;
        }

        public Builder addTracks(Collection<MusicTrack> tracks) {
            album.tracks.addAll(tracks);
            return this;
        }

        public MusicAlbum create() {
            return album;
        }
    }

    private MusicAlbum() {
        tracks = new ArrayList<MusicTrack>();
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public Person getProducer() {
        return producer;
    }

    public ArrayList<MusicTrack> getMusicTracks() {
        return tracks;
    }

}
