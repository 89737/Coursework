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
            // those two lines of code close the database and then displays a  message that it has disconnected
            db.close();
            System.out.println("disconnected from database.");

        }catch (Exception exception) {
            System.out.println("Database disconnection error: " + exception.getMessage());
        }
    }
}