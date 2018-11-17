package com.hl.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDriver {

    protected static Connection connectToDatabase() {
        Connection connection = null;
        String server = "localhost";
        String schema = "hl";
        String url = "jdbc:mysql://" + server + "/" + schema;
        String username = "root";
        String password = "";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] argv) throws Exception {
        Connection connection = connectToDatabase();
    }


    

    

}
