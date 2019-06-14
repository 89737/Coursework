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
            PreparedStatement ps = db.prepareStatement("SELECT UserID, UserName, Password FROM Users");

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
}
