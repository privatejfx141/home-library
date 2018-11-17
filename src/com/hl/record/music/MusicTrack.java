package com.hl.record.music;

import com.hl.record.Person;

public class MusicTrack {
    private String name = "";
    private String language = "";
    private Person songwriter;
    private Person composer;
    private Person arrangement;

    public MusicTrack(String name, String language, Person songwriter, Person composer, Person arrangement) {
        this.name = name;
        this.language = language;
        this.songwriter = songwriter;
        this.composer = composer;
        this.arrangement = arrangement;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
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
}
