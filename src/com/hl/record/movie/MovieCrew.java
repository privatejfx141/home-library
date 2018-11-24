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

    private String role = "";
    private boolean award;

    public static String getRoleDescriptor(String role) {
        if (role.equalsIgnoreCase("Director")) {
            return DIRECTOR;
        } else if (role.equalsIgnoreCase("Scriptwriter")) {
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
        MovieCrew crew = null;
        name = name.trim();
        String[] split = name.split(" ");
        String firstName = "";
        String middleName = "";
        String lastName = "";
        firstName = split[0];
        if (split.length == 2) {
            lastName = split[1];
        } else if (split.length == 3) {
            middleName = split[1];
            lastName = split[2];
        }
        crew = new MovieCrew(firstName, middleName, lastName, role, gender, award);
        return crew;
    }

    public MovieCrew(int id, String firstName, String middleName, String lastName, String role, String gender,
            boolean award) {
        super(id, firstName, middleName, lastName, gender);
        this.role = role;
        this.award = award;
    }

    public MovieCrew(String firstName, String middleName, String lastName, String role, String gender, boolean award) {
        this(-1, firstName, middleName, lastName, role, gender, award);
    }

    public String getRole() {
        return role;
    }

    public boolean getAward() {
        return award;
    }
}