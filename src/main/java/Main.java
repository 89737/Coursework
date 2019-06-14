import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static Connection db = null; // acts as a global variable

    public static void main(String[] args) {
        openDatabase("Login.db"); // the file i want to open
        try{
            PreparedStatement ps = db.prepareStatement("SELECT UserID, UserName, Password FROM User");

            ResultSet results = ps.executeQuery();
            while(results.next()) {
                int userID = results.getInt(1);
                String userName = results.getString(2);
                String password = results.getString(3);
                System.out.println(userID + " " + userName + " " + password);
            }
        } catch (Exception exception){
            System.out.println("database error: " + exception.getMessage());
        }
        // code to get data from, write to the database in here
        closeDatabase();
    }

    private static void openDatabase(String dbFile){
        try{
            // this loads the database server
            Class.forName("org.sqlite.JDBC");
            // these 2 lines configure the database settings
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            // this opens the database file
            db = DriverManager.getConnection("jdbc:sqlite:resources/login/" + dbFile, config.toProperties());
            System.out.println("Database connection successfully established");
            // this catches any errors that have occurred
        } catch (Exception exception){
            System.out.println("database connection error: " + exception.getMessage());
        }
    }
    private static void closeDatabase(){
        try{
            db.close();
            System.out.println("disconnected from database.");
        }catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }


    public static void deleteUser ( int UserID, String username, String password){
        try {
            PreparedStatement ps = db.prepareStatement("DELETE FROM User WHERE UserID = 1");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }

    public static void updatetUser ( int UserID, String username, String password){
        try {
            PreparedStatement ps = db.prepareStatement("UPDATE User SET username = bob, password = bob111 WHERE UserID = 1");
            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }

    public static void insertUser ( int UserID, String Username, String Password){
        try {
            PreparedStatement ps = db.prepareStatement("INSERT INTO User (UserID, username, password) VALUES(5,Legend27,WhoAm1?)");
            ps.executeUpdate();
            System.out.println("Record added to Users table");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error: something gone wrong. please contact the administrator with the error code WC-WA.");
        }
    }


    public static void ListUsers () {
        try {
            PreparedStatement ps = db.prepareStatement("SELECT UserID, UserName, Password FROM User");
            ResultSet results = ps.executeQuery();
            while (results.next()) ;
            {
                int UserID = results.getInt(1);
                String username = results.getString(2);
                String Password = results.getString(3);
                System.out.println(UserID + " " + username + " " + Password);

            }


        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
        }
    }
}