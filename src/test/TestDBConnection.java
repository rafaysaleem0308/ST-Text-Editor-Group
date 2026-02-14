package test;

import dal.DatabaseConnection;
import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        Connection conn = db.getConnection();

        if (conn != null) {
            System.out.println("Database connected successfully!");
            db.closeConnection();
        } else {
            System.out.println("Failed to connect to database.");
        }
    }
}
