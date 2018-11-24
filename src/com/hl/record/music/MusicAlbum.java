package com.hl.record.music;

import java.util.ArrayList;

import com.hl.exceptions.NameFormatException;
import com.hl.record.Person;

public class MusicAlbum {
    private String name = "";
    private int year = -1;
    private Person producer;
    private ArrayList<MusicTrack> tracks;

    public static class Builder {

        private MusicAlbum album = new MusicAlbum();

        public Builder setName(String name) {
            album.name = name;
            return this;
        }

        public Builder setYear(int year) {
            album.year = year;
            return this;
        }

        public Builder setProducer(Person producer) {
            album.producer = producer;
            return this;
        }

        public Builder setProducer(String producer) throws NameFormatException {
            album.producer = Person.parseName(producer);
            return this;
        }

        public Builder addTrack(MusicTrack track) {
            album.tracks.add(track);
            return this;
        }

        public MusicAlbum create() {
            return album;
        }

    }

    private MusicAlbum() {
        tracks = new ArrayList<MusicTrack>();
    }

    public MusicAlbum(String name, int year, Person producer, ArrayList<MusicTrack> tracks) {
        this.name = name;
        this.year = year;
        this.producer = producer;
        this.tracks = tracks;
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
