package com.hl.record.movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hl.gui.HomeLibrary;

public class Movie {

    private String name = "";
    private int year = -1;
    private ArrayList<MovieCrew> crewMembers;

    public static class Builder {
        private Movie movie = new Movie();

        public Builder setName(String name) {
            try {
                movie.name = name.trim();
            } catch (NullPointerException e) {
                movie.name = "";
            }
            return this;
        }

        public Builder setYear(int year) {
            movie.year = year;
            return this;
        }

        public Builder setYear(String year) {
            try {
                year = year.trim();
                return setYear(Integer.parseInt(year));
            } catch (NullPointerException | NumberFormatException e) {
                String error = String.format(HomeLibrary.INTEGER_FIELD_MSG, "Year of release");
                throw new NumberFormatException(error);
            }
        }

        public Builder addCrewMember(MovieCrew member) {
            movie.crewMembers.add(member);
            return this;
        }

        public Builder addCrewMembers(Collection<MovieCrew> members) {
            movie.crewMembers.addAll(members);
            return this;
        }

        public Movie create() {
            return movie;
        }
    }

    private Movie() {
        crewMembers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public List<MovieCrew> getCrewMembers() {
        return crewMembers;
    }

}
