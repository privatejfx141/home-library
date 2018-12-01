package com.hl.generics;

public enum Genders {
    MALE("Male"), FEMALE("Female");

    private final String name;

    Genders(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
