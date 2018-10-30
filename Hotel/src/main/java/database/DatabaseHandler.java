package database;

import javax.swing.*;
import java.sql.*;

/**
 * Created by jeremyjiang on 10/27/18.
 */
public class DatabaseHandler {

    private static DatabaseHandler handler = null;

    private static final String DB_URL = "jdbc:derby:database;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    public DatabaseHandler() {
        createConnection();
        setupTable();
    }

    private static void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void setupTable() {
        String tableName = "CUSTOMER";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables =  dbm.getTables(null, null, tableName.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + tableName + " already exists");
            }
            else {
                stmt.execute("CREATE TABLE " + tableName + "("
                        + "name varchar(200),\n"
                        + "roomId int,\n"
                        + "checkInDate date,\n"
                        + "checkOutDate date,\n"
                        + "requirement varchar(200),\n"
                        + "isGone boolean default false,\n"
                        + "primary key (name, checkInDate)"
                        + ")"

                );
            }


        } catch (SQLException e){
            System.err.println(e.getMessage() + " --- setupDatabase");

        } finally {
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.print("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {

        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Error" + ex.getMessage(), "Error OCcured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }






}

