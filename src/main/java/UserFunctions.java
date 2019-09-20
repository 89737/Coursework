import main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserFunctions {

    public static void insertUser(String Username, String Password, String Email) {
        try {
            //the 2 lines create a user with the properties for that table and then updates the table to insert them in
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO User (username, password, email) VALUES(Legend27,WhoAm1?, )");
            ps.executeUpdate(); //executes the query to update the table
            System.out.println("Record added to Users table"); //confirmation message that the user table has been updated
        } catch (Exception exception) {
            System.out.println(exception.getMessage()); //displays the error message if any errors have occurred
            System.out.println("Error: something gone wrong. please contact the administrator with the error code WC-WA.");
        }
    }

    public static void updateUser(int UserID, String username, String password, String Email) {
        try {
            // the SQL statement that sets a change to a ID where there is a matching piece of existing data
            PreparedStatement ps = Main.db.prepareStatement("UPDATE User SET username = ?, password = ?, Email = ? WHERE UserID = ?");
            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }


    public static void deleteUser(int UserID, String username, String password, String Email) {
        try {
            // SQL statement that removes a specific part from the database where the requirement is met
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM User WHERE UserID = ?");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }

    public static void ListUsers() {
        try { // try catch method to catch any errors to stop the program breaking
            //
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID, UserName, Password, Email FROM User");
            ResultSet results = ps.executeQuery();
            //while it is not the end of the database it will display each user with their attributes
            while (results.next()) ;
            {
                // each column will be displayed for all the entities
                int UserID = results.getInt(1);
                String username = results.getString(2);
                String Password = results.getString(3);

                System.out.println(UserID + " " + username + " " + Password + " " ); //displays the list of users

            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }

    }
}