package com.hl.database;

import java.sql.Connection;

import com.hl.record.Person;

public class DatabaseSelector {

    public static int getPersonId(Connection connection, Person person) {
        return -1;
    }
    
    public static int getKeywordId(Connection connection, String keyword) {
        return -1;
    }
    
}
