package com.hl.record.music;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hl.exceptions.NameFormatException;
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
            try {
                track.name = name.trim();
            } catch (NullPointerException e) {
                track.name = "";
            }
            return this;
        }

        public Builder setLanguage(String language) {
            try {
                track.language = language.trim();
            } catch (NullPointerException e) {
                track.language = "";
            }
            return this;
        }

        public Builder addSinger(Person singer) {
            if (singer != null) {
                track.singers.add(singer);
            }
            return this;
        }

        public Builder addSinger(String singer) throws NameFormatException {
            singer = singer.trim();
            if (!singer.isEmpty()) {
                try {
                    return addSinger(Person.parseName(singer));
                } catch (NameFormatException e) {
                    String error = "Singer name is not a proper name.";
                    throw new NameFormatException(error);
                }
            }
            return this;
        }

        public Builder addSingers(Collection<Person> singers) {
            track.singers.addAll(singers);
            return this;
        }

        public Builder setSongwriter(Person songwriter) {
            track.songwriter = songwriter;
            return this;
        }

        public Builder setSongwriter(String songwriter) throws NameFormatException {
            try {
                track.songwriter = Person.parseName(songwriter.trim());
            } catch (NameFormatException e) {
                String error = "Songwriter name is not a proper name.";
                throw new NameFormatException(error);
            }
            return this;
        }

        public Builder setComposer(Person composer) {
            track.composer = composer;
            return this;
        }

        public Builder setComposer(String composer) throws NameFormatException {
            try {
                track.composer = Person.parseName(composer.trim());
            } catch (NameFormatException e) {
                String error = "Composer name is not a proper name.";
                throw new NameFormatException(error);
            }
            return this;
        }

        public Builder setArranger(Person arrangement) {
            track.arrangement = arrangement;
            return this;
        }

        public Builder setArranger(String arrangement) throws NameFormatException {
            try {
                track.arrangement = Person.parseName(arrangement);
            } catch (NameFormatException e) {
                String error = "Arranger name is not a proper name.";
                throw new NameFormatException(error);
            }
            return this;
        }

        public Builder setDiskType(String diskType) {
            try {
                track.diskType = diskType.trim();
            } catch (NullPointerException e) {
                track.diskType = "";
            }
            return this;
        }

        public Builder setDiskType(boolean diskType) {
            return setDiskType(diskType ? "Vinyl" : "CD");
        }

        public MusicTrack create() {
            return track;
        }

    }

    private MusicTrack() {
        this.singers = new ArrayList<>();
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

    @Override
    public String toString() {
        String repr = name;
        if (language != null && !language.isEmpty()) {
            repr += " [" + language + "]";
        }
        repr += " - ";
        for (Person singer : singers) {
            repr += singer.toString() + ", ";
        }
        repr += "SW: " + songwriter.toString();
        repr += ", CP: " + composer.toString();
        repr += ", AG: " + arrangement.toString();
        return repr;
    }

}
