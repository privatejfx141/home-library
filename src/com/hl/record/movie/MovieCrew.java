package com.hl.record.movie;

import com.hl.record.Person;

public class MovieCrew extends Person {
    public static String[] ROLES = new String[] { "Director", "Scriptwriter", "Cast", "Producer", "Composer", "Editor",
            "Costume designer" };
    private String role = "";
    private boolean award;

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