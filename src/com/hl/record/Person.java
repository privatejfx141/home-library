package com.hl.record;

import com.hl.exceptions.NameFormatException;

public class Person {
    private int id = -1;
    private String firstName = "";
    private String middleName = "";
    private String lastName = "";
    private String gender = "";
    
    public static Person parseName(String name) throws NameFormatException {
        Person person = null;
        name = name.trim();
        String[] split = name.split(" ");
        if (split.length == 2) {
            person = new Person(split[0], "", split[1]);
        } else if (split.length == 3) {
            person = new Person(split[0], split[1], split[2]);
        } else {
            throw new NameFormatException("Name is not a proper name.");
        }
        return person;
    }

    public Person(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public Person(String firstName, String middleName, String lastName, String gender) {
        this(firstName, middleName, lastName);
        this.gender = gender;
    }

    public Person(int id, String firstName, String middleName, String lastName) {
        this(firstName, middleName, lastName);
        this.id = id;
    }

    public Person(int id, String firstName, String middleName, String lastName, String gender) {
        this(firstName, middleName, lastName, gender);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        if (middleName.isEmpty()) {
            return firstName + " " + lastName;
        } else {
            return firstName + " " + middleName + " " + lastName;
        }
    }
}
