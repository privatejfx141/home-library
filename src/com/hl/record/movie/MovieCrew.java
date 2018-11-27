package com.hl.record.movie;

import com.hl.generics.MovieRoles;
import com.hl.record.Person;

public class MovieCrew extends Person {

    public static final String DIRECTOR = MovieRoles.D.toString();
    public static final String SCRIPTWRITER = MovieRoles.S.toString();
    public static final String CAST = MovieRoles.C.toString();
    public static final String PRODUCER = MovieRoles.P.toString();
    public static final String COMPOSER = MovieRoles.CO.toString();
    public static final String EDITOR = MovieRoles.E.toString();
    public static final String COSTUME_DESIGNER = MovieRoles.CD.toString();
    public static final String[] ROLES = new String[] { DIRECTOR, SCRIPTWRITER, CAST, PRODUCER, COMPOSER, EDITOR,
            COSTUME_DESIGNER };

    public static String getRoleDescriptor(String role) {
        if (role.equalsIgnoreCase("Director")) {
            return DIRECTOR;
        } else if (role.equalsIgnoreCase("Script writer")) {
            return SCRIPTWRITER;
        } else if (role.equalsIgnoreCase("Cast")) {
            return CAST;
        } else if (role.equalsIgnoreCase("Producer")) {
            return PRODUCER;
        } else if (role.equalsIgnoreCase("Composer")) {
            return COMPOSER;
        } else if (role.equalsIgnoreCase("Editor")) {
            return EDITOR;
        } else if (role.equalsIgnoreCase("Costume designer")) {
            return COSTUME_DESIGNER;
        }
        return null;
    }

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
