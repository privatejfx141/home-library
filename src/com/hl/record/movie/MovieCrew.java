package com.hl.record.movie;

import com.hl.generics.Roles;
import com.hl.record.Person;

public class MovieCrew extends Person {

    public static final String CAST = Roles.CAST.toString();
    public static final String COMPOSER = Roles.COMPOSER.toString();
    public static final String COSTUME_DESIGNER = Roles.COSTUME_DESIGNER.toString();
    public static final String DIRECTOR = Roles.DIRECTOR.toString();
    public static final String EDITOR = Roles.MOVIE_EDITOR.toString();
    public static final String PRODUCER = Roles.MOVIE_PRODUCER.toString();
    public static final String SCRIPTWRITER = Roles.SCRIPTWRITER.toString();

    public static final String[] ROLES = new String[] { CAST, COMPOSER, COSTUME_DESIGNER, DIRECTOR, EDITOR, PRODUCER,
            SCRIPTWRITER };

    public static MovieCrew parseName(String name, String role, String gender, boolean award) {
        MovieCrew member = new MovieCrew();
        name = name.trim();
        String[] split = name.split("\\s+");
        member.firstName = split[0];
        if (split.length == 2) {
            member.lastName = split[1];
        } else if (split.length == 3) {
            member.middleName = split[1];
            member.lastName = split[2];
        }
        member.role = role.trim();
        member.gender = gender.trim();
        member.award = award;
        return member;
    }

    private String role = "";
    private boolean award;

    public static class Builder extends Person.Builder {
        private MovieCrew member;

        public Builder() {
            super();
            person = new MovieCrew();
            member = (MovieCrew) person;
        }

        public Builder setPerson(Person person) {
            if (person != null) {
                setId(person.getId());
                setFirstName(person.getFirstName());
                setMiddleName(person.getMiddleName());
                setLastName(person.getLastName());
                setGender(person.getGender());
            }
            return this;
        }

        public Builder setRole(String role) {
            try {
                member.role = role.trim();
            } catch (NullPointerException e) {
                member.role = "";
            }
            return this;
        }

        public Builder hasAward(boolean award) {
            member.award = award;
            return this;
        }

        @Override
        public MovieCrew create() {
            return member;
        }
    }

    private MovieCrew() {
        super();
    }

    public String getRole() {
        return role;
    }

    public boolean getAward() {
        return award;
    }

}
