package com.hl.record.music;

import java.util.ArrayList;

import com.hl.record.Person;

public class MusicAlbum {
    private String name = "";
    private int year = -1;
    private Person producer;
    private ArrayList<MusicTrack> tracks;

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
