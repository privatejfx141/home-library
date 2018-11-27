package com.hl.record;

import com.hl.exceptions.NameFormatException;

public class Person {

    public static String MALE = "Male";
    public static String FEMALE = "Female";

    public static class Builder {
        protected Person person;

        public Builder() {
            person = new Person();
        }

        public Builder setId(int id) {
            person.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            try {
                person.firstName = firstName.trim();
            } catch (NullPointerException e) {
                person.firstName = "";
            }
            return this;
        }

        public Builder setMiddleName(String middleName) {
            try {
                person.middleName = middleName.trim();
            } catch (NullPointerException e) {
                person.middleName = "";
            }
            return this;
        }

        public Builder setLastName(String lastName) {
            try {
                person.lastName = lastName.trim();
            } catch (NullPointerException e) {
                person.lastName = "";
            }
            return this;
        }

        public Builder setGender(String gender) {
            try {
                person.gender = gender.trim();
            } catch (NullPointerException e) {
                person.gender = "";
            }
            return this;
        }

        public Builder setGender(boolean gender) {
            return setGender(gender ? MALE : FEMALE);
        }

        public Person create() {
            return person;
        }
    }

    protected int id = -1;
    protected String firstName = "";
    protected String middleName = "";
    protected String lastName = "";
    protected String gender = "";

    public static Person parseName(String name) throws NameFormatException {
        Person person = new Person();
        name = name.trim();
        String[] split = name.split("\\s+");
        person.firstName = split[0];
        if (split.length == 2) {
            person.lastName = split[1];
        } else if (split.length == 3) {
            person.middleName = split[1];
            person.lastName = split[2];
        } else {
            throw new NameFormatException("Name is not a proper name.");
        }
        return person;
    }

    protected Person() {
        this.id = -1;
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

    public String getName() {
        String name = firstName;
        if (!middleName.isEmpty()) {
            name += " " + middleName;
        }
        name += " " + lastName;
        return name;
    }

    public String getGender() {
        return gender;
    }

    public boolean compare(Person other) {
        if (other == null) {
            return false;
        }
        if (this.middleName == null) {
            this.middleName = "";
        }
        if (other.middleName == null) {
            other.middleName = "";
        }
        if (this.gender == null) {
            this.gender = "";
        }
        if (other.gender == null) {
            other.gender = "";
        }
        return this.firstName.equalsIgnoreCase(other.firstName) && this.middleName.equals(other.middleName)
                && this.lastName.equalsIgnoreCase(other.lastName) && this.gender.equalsIgnoreCase(other.gender);
    }

    @Override
    public String toString() {
        String repr = getName();
        if (id != -1) {
            repr += " [ID" + id + "]";
        }
        return repr;
    }

}
