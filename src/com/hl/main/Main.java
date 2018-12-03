package com.hl.main;

import com.hl.database.DatabaseDriver;
import com.hl.gui.HomeLibrary;

public class Main {

    public static void main(String[] args) {
        DatabaseDriver.INSERT_ROLES_ON_INITIALIZE = true;
        DatabaseDriver.initializeDatabase();
        new HomeLibrary();
    }

}
