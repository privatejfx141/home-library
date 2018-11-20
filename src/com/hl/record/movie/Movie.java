package com.hl.record.movie;

import java.util.ArrayList;
import java.util.HashMap;

public class Movie {
    private String name = "";
    private int year = -1;
    private HashMap<String, ArrayList<MovieCrew>> crewMembers = new HashMap<>();

    public static class Builder {
        private Movie movie = new Movie();

        public Builder setName(String name) {
            movie.name = name;
            return this;
        }

        public Builder setYear(int year) {
            movie.year = year;
            return this;
        }

        public Builder addCrew(MovieCrew crew) {
            String role = crew.getRole();
            if (movie.crewMembers.containsKey(role)) {
                movie.crewMembers.get(role).add(crew);
            } else {
                ArrayList<MovieCrew> members = new ArrayList<>();
                members.add(crew);
                movie.crewMembers.put(role, members);
            }
            return this;
        }

        public Movie create() {
            return movie;
        }
    }

    private Movie() {
        crewMembers = new HashMap<>();
    }

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
