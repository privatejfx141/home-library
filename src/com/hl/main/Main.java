package com.hl.main;

import com.hl.database.DatabaseDriver;
import com.hl.gui.HomeLibrary;

public class Main {

    public static void main(String[] args) {
        DatabaseDriver.initializeDatabase();
        new HomeLibrary();
    }

}
