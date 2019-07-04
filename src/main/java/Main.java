import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
    public static Connection db = null; // acts as a global variable

    public static void main(String[] args) {
        openDatabase("Login.db"); // the file i want to open
      //UserFunctions.queryToUpdate()
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
            // those two lines of code close the database and then displaysa  message that it has disconnected
            db.close();
            System.out.println("disconnected from database.");

        }catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }


    public static void deleteUser ( int UserID, String username, String password){
        try {
            // SQL statement that removes a specific part from the database where the requirement is met
            PreparedStatement ps = db.prepareStatement("DELETE FROM User WHERE UserID = 1");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }

    public static void updateUser ( int UserID, String username, String password){
        try {
            // the SQL statement that sets a change to a ID where there is a matching piece of existing data
            PreparedStatement ps = db.prepareStatement("UPDATE User SET username = bob, password = bob111 WHERE UserID = 1");
            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }

    public static void insertUser ( int UserID, String Username, String Password){
        try {
            //the 2 lines create a user with the properties for that table and then updates the table to insert them in
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
            //
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