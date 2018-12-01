# cscc43
Home Library project for CSCC43 assignments 1 and 3, developed by Yun Jie (Jeffrey) Li in the fall of 2018.

## Running the HL application
The `HL` database must be first setup on `localhost` with all the tables from the `a2_script.sql` file created. People involved roles must also have been already inserted.

To run the Home Library application, execute the file in the following path:
```
src/com/hl/main/Main.java
```
When the application is first run, it will initialize all the required indicies, functions, and views in the `HL` database.

## Code Structure
All dynamic SQL code are under the `com.hl.database` package.
* SQL code for connecting to the `HL` database and initializing it are in `DatabaseDriver.java`.
    * SQL code for creating the required indices, functions, and views for the A3 reports are in the `initializeDatabase()` method.
* SQL code for querying data are in `DatabaseSelector.java`.
* SQL code for inserting data are in `DatabaseInserter.java`.
* SQL code for deleting data are in `DatabaseDeleter.java`.
* SQL code for updating data are in `DatabaseUpdater.java`.
* SQL code for A1 view queries are in `DatabaseQueryView.java`.
* SQL code for A3 reports are in `DatabaseQueryReport.java`.
