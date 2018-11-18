package com.hl.record.music;

import java.util.ArrayList;
import java.util.List;

import com.hl.record.Person;

public class MusicTrack {
    private String name = "";
    private String language = "";
    private List<Person> singers;
    private Person songwriter;
    private Person composer;
    private Person arrangement;
    private String diskType = "";

    public MusicTrack(String name, String language, Person songwriter, Person composer, Person arrangement,
            String diskType) {
        this.name = name;
        this.language = language;
        this.singers = new ArrayList<>();
        this.songwriter = songwriter;
        this.composer = composer;
        this.arrangement = arrangement;
        this.diskType = diskType;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public List<Person> getSingers() {
        return singers;
    }

    public void addSinger(Person singer) {
        singers.add(singer);
    }

    public Person getSongwriter() {
        return songwriter;
    }

    public Person getComposer() {
        return composer;
    }

    public Person getArrangement() {
        return arrangement;
    }

    public String getDiskType() {
        return diskType;
    }
}
