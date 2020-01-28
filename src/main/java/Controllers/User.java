package Controllers;


import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

@Path("User/")
public class User {

    @POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    //takes the values of username and password from the table and takes them to enter to the form
    public String loginUser(@FormDataParam("Username") String username, @FormDataParam("Password") String password) {

        try {

            System.out.println("user/login");

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT Password FROM Users WHERE Username = ?");
            ps1.setString(1, username);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next()) {

                String correctPassword = loginResults.getString(1);
                if (password.equals(correctPassword)) { //if the password is matching the value of the stored password

                    String token = UUID.randomUUID().toString(); //creates a universally unique identifier for the token for the user
                    // runs an SQL update statement for setting a new token value for the matching user for the username
                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Users SET Token = ? WHERE Username = ?");
                    ps2.setString(1, token);
                    ps2.setString(2, username);
                    ps2.executeUpdate(); //executes the query with the strings set to the column values

                    JSONObject userDetails = new JSONObject();
                    userDetails.put("username", username);  // this gives the val
                    userDetails.put("token", token);
                    return userDetails.toString();

                } else {
                    //if the password is incorrect then the return is an incorrect password error
                    return "{\"error\": \"Incorrect password!\"}";
                }

            } else {
                //otherwise the return is that the user is unknown
                return "{\"error\": \"Unknown user!\"}";
            }

        } catch (Exception exception){
            System.out.println("Database error during /user/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }
    @POST
    @Path("logout") //this method is needed to run the logout function for the button to logout
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String logoutUser(@CookieParam("token") String token) { //needs the cookie for the token to be able to remove it from the database

        try {

            System.out.println("user/logout");
            // this selects the UserID from the users for a token that is currently found
            PreparedStatement ps1 = Main.db.prepareStatement("SELECT UserID FROM Users WHERE Token = ?");
            ps1.setString(1, token);
            ResultSet logoutResults = ps1.executeQuery();
            if (logoutResults.next()) {

                int id = logoutResults.getInt(1); //this is the results set for the token to be deleted for logging out
                //this deletes the token value by setting it to null so there is no matching token
                PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Users SET Token = NULL WHERE UserID = ?");
                ps2.setInt(1, id);
                ps2.executeUpdate(); //this executes the update to set the token to null to allow the program to logout the user

                return "{\"status\": \"OK\"}";
            } else {

                return "{\"error\": \"Invalid token!\"}";

            }
            //the catch block catches any errors that occur to stop the program from crashing
        } catch (Exception exception){
            System.out.println("Database error during /user/logout: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }

    }

    public static boolean validToken(String token) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT UserID FROM Users WHERE Token = ?");
            ps.setString(1, token);
            ResultSet logoutResults = ps.executeQuery();
            return logoutResults.next();
        } catch (Exception exception) {
            System.out.println("Database error during /user/logout: " + exception.getMessage());
            return false;
        }
    }
}
