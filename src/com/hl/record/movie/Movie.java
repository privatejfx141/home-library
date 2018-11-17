package com.hl.record.movie;

import java.util.ArrayList;
import java.util.HashMap;

public class Movie {
    private String name = "";
    private int year = -1;
    private HashMap<String, ArrayList<MovieCrew>> crewMembers;

    public Movie(String name, int year, HashMap<String, ArrayList<MovieCrew>> crewMembers) {
        this.name = name;
        this.year = year;
        this.crewMembers = crewMembers;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public HashMap<String, ArrayList<MovieCrew>> getCrewMembers() {
        return crewMembers;
    }
}
