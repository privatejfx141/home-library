package com.hl.gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.hl.database.DatabaseDriver;
import com.hl.database.DatabaseQueryReport;
import com.hl.exceptions.NameFormatException;

public class ReportDialog {

    private final JFrame parentFrame;

    public static final String[] REPORTS = new String[] { "Author's Publications", "Publication in one Year",
            "Books with Similar Topic", "Frequent Publishers", "Most Popular Subjects", "Multi Skills Movie Crew",
            "Award Winning Movies", "Music with Similar Name", "Multi Skills Music Crew", "Similar Names" };

    public ReportDialog(JFrame parentFrame, int reportNumber) {
        this.parentFrame = parentFrame;
        String reportName = REPORTS[reportNumber - 1];
        generateReport(reportNumber, reportName);
    }

    private void generateReport(int reportNumber, String reportName) {
        Connection connection = DatabaseDriver.connectToDatabase();
        ResultSet results = null;
        switch (reportNumber) {
        case 1:
            results = queryReport1(connection);
            break;
        case 2:
            results = queryReport2(connection);
            break;
        case 3:
            results = queryReport3(connection);
            break;
        case 4:
            results = DatabaseQueryReport.queryReport4(connection);
            break;
        case 5:
            results = DatabaseQueryReport.queryReport5(connection);
            break;
        case 6:
            results = DatabaseQueryReport.queryReport6(connection);
            break;
        case 7:
            results = DatabaseQueryReport.queryReport7(connection);
            break;
        case 8:
            results = DatabaseQueryReport.queryReport8(connection);
            break;
        case 9:
            results = DatabaseQueryReport.queryReport9(connection);
            break;
        case 10:
            results = DatabaseQueryReport.queryReport10(connection);
            break;
        }
        // if selecting results successful, generate report
        if (results != null) {
            try {
                ResultSetTable.generateTableDialog(results, reportName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // close connection
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet queryReport1(Connection connection) {
        ResultSet results = null;
        String authorName = JOptionPane.showInputDialog(parentFrame, "Enter an author name:");
        if (authorName != null) {
            authorName = authorName.trim();
            if (authorName.isEmpty()) {
                String error = "Author's name cannot be empty.";
                JOptionPane.showMessageDialog(parentFrame, error, "Report Error", JOptionPane.ERROR_MESSAGE);
                return null;
            } else {
                try {
                    results = DatabaseQueryReport.queryReport1(connection, authorName);
                } catch (NameFormatException e) {
                    JOptionPane.showMessageDialog(parentFrame, e.getMessage(), "Report Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
        }
        return results;
    }

    private ResultSet queryReport2(Connection connection) {
        ResultSet results = null;
        String year = JOptionPane.showInputDialog(parentFrame, "Enter a year of publication:");
        if (year != null) {
            year = year.trim();
            if (year.isEmpty()) {
                String error = "Year of publication cannot be empty.";
                JOptionPane.showMessageDialog(parentFrame, error, "Report Error", JOptionPane.ERROR_MESSAGE);
                return null;
            } else {
                int pubYear = -1;
                try {
                    pubYear = Integer.parseInt(year);
                } catch (NumberFormatException e) {
                    String error = "Year of publication must be an integer.";
                    JOptionPane.showMessageDialog(parentFrame, error, "Report Error", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                results = DatabaseQueryReport.queryReport2(connection, pubYear);
            }
        }
        return results;
    }

    private ResultSet queryReport3(Connection connection) {
        ResultSet results = null;
        String subject = JOptionPane.showInputDialog(parentFrame, "Enter a specific subject to search for:");
        if (subject != null) {
            subject = subject.trim();
            if (subject.isEmpty()) {
                String error = "Subject cannot be empty.";
                JOptionPane.showMessageDialog(parentFrame, error, "Report Error", JOptionPane.ERROR_MESSAGE);
                return null;
            } else {
                results = DatabaseQueryReport.queryReport3(connection, subject);
            }
        }
        return results;
    }

}
