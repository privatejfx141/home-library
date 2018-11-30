package com.hl.generics;

public enum Roles {

    AUTHOR("Author"), //
    SINGER("Singer"), //
    SONGWRITER("Song Writer"), //
    MUSIC_ARRANGER("Music Arranger"), //
    MUSIC_PRODUCER("Music Producer"), //
    COMPOSER("Composer"), //
    DIRECTOR("Director"), //
    SCRIPTWRITER("Script Writer"), //
    CAST("Cast"), //
    MOVIE_PRODUCER("Movie Producer"), //
    MOVIE_EDITOR("Movie Editor"), //
    COSTUME_DESIGNER("Costume Designer") //
    ;

    private final String name;

    Roles(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
