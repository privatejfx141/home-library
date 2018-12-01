package com.hl.generics;

public enum DiskTypes {
    CD("CD"), VINYL("Vinyl");

    private final String name;

    DiskTypes(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
