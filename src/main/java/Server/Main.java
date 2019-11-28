package Server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static Connection db = null; // acts as a global variable

    //PHASE 1 P.S.V.M.
    /*public static void main(String[] args) {
        openDatabase("Login.db"); // the file i want to open
      //Controllers.UserFunctions.queryToUpdate()
    closeDatabase();
    }*/
    public static void main(String[] args) {

        openDatabase("Login.db"); //used to open the database I want the resources from
        //closeDatabase();
        ResourceConfig config = new ResourceConfig(); //creates a new resource config
        config.packages("Controllers"); //accesses the controller package with the CRUD methods
        config.register(MultiPartFeature.class);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config)); //creates a new holder for the main controllers

        Server server = new Server(8081); //this is the  port I will be using for testing
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(servlet, "/*");
        try { // a try method to run the server without it crashing if an error occurs
            server.start(); //used to start the server
            System.out.println("Server successfully started."); //message if the server has successfully connected
            server.join(); //will join the server once it is ready to be used
        } catch (Exception e) { //used to catch any errors
            e.printStackTrace(); //if any errors have been found the program will trace the routine to find it
        }

    }



    private static void openDatabase(String dbFile){
        try{
            // this loads the database server
            Class.forName("org.sqlite.JDBC");
            // these 2 lines configure the database settings
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            // this opens the database file
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
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