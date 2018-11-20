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

    public static class Builder {

        private MusicTrack track = new MusicTrack();

        public Builder setName(String name) {
            track.name = name;
            return this;
        }

        public Builder setLanguage(String language) {
            track.language = language;
            return this;
        }

        public Builder addSinger(Person singer) {
            track.singers.add(singer);
            return this;
        }

        public Builder setSongwriter(Person songwriter) {
            track.songwriter = songwriter;
            return this;
        }

        public Builder setComposer(Person composer) {
            track.composer = composer;
            return this;
        }

        public Builder setArranger(Person arrangement) {
            track.arrangement = arrangement;
            return this;
        }

        public Builder setDiskType(String diskType) {
            track.diskType = diskType;
            return this;
        }

        public MusicTrack create() {
            return track;
        }

    }

    private MusicTrack() {
        this.singers = new ArrayList<>();
    }

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
