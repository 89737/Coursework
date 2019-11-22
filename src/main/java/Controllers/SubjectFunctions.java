package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("Subjects/")
public class SubjectFunctions {

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    //FormDataParam gets the data from a web page to use in the function
    public String insertSubject(@FormDataParam("SubjectName") String SubjectName, @FormDataParam("ClassName") String ClassName, @FormDataParam("UserID") Integer UserID) {
        try {
            //an if statement to check that any of the fields are null and if so then an error would be displayed to fill in the field
            if( SubjectName == null || ClassName == null || UserID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
          //  System.out.println("Subjects/new SubjectID=" + SubjectID); //this displays the new SubjectID being created


            //the line creates a user with the properties for that table and then updates the table to insert them in
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Subjects (SubjectName, ClassName, UserID) VALUES(?,?,?)");
            //these lines are for each of the attributes that need to be set a prepared statement data type;
            ps.setString(1, SubjectName);
            ps.setString(2, ClassName);
            ps.setInt(3, UserID);
            ps.executeUpdate(); //executes the query to update the table
            return "{\"status\": \"OK\"}"; //confirmation message for when the new user has been inserted

        } catch (Exception exception) { // this is used to catch any errors throughout the program
            System.out.println(exception.getMessage());
            return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
        }
    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    //FormDataParam gets the data from a web page to use in the function
    public String updateSubject(@FormDataParam("SubjectID") Integer SubjectID, @FormDataParam("SubjectName") String SubjectName, @FormDataParam("ClassName") String ClassName, @FormDataParam("UserID") Integer UserID) {
        try {

            if (SubjectID == null || SubjectName == null || ClassName == null || SubjectID == null) { //if one or more of the fields are left empty then an error will be thrown to alert the user to fill them in
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Subjects/update SubjectID=" + SubjectID);

            // the SQL statement that sets a change to an ID where there is a matching piece of existing data
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Subjects SET SubjectName = ?, ClassName = ?, UserID = ? WHERE SubjectID = ?");
            ps.setString(1, SubjectName);
            ps.setString(2, ClassName);
            ps.setInt(3, UserID);
            ps.setInt(4,SubjectID);
            ps.executeUpdate(); //executes the query to be carried out

            return "{\"status\": \"OK\"}"; //returns a confirmation message that the update was a success

        } catch (Exception exception) { // catches any errors coming through and displays an error to display to the user
            System.out.println(exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }


    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    //FormDataParam gets the data from a web page to use in the function
    //only a SubjectID needs to be found as the rest of the records don't matter for deleting the row
    public String deleteSubject(@FormDataParam("SubjectID") Integer SubjectID) {
        try {
            // an if statement to check that the SubjectID isn't null and displays an error if it is
            if (SubjectID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Subjects/delete SubjectID=" + SubjectID); // this displays the SubjectID that has been deleted from the table

            // SQL statement that removes a specific part from the database where the requirement is met
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Subjects WHERE SubjectID = ?");
            ps.setInt(1, SubjectID);
            ps.execute(); //this executes the query to be run

            return "{\"status\": \"OK\"}";  // displays a message to show that the query has been executed

        } catch (Exception exception) { // catch method catches any errors to stop the program from breaking and displays the error message
            System.out.println(exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }
    }


    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String ListSubjects() {
        System.out.println("Subjects/list");
        JSONArray list = new JSONArray();  //creates an array for the JSON objects to be used
        try { // try catch method to catch any errors to stop the program breaking
            PreparedStatement ps = Main.db.prepareStatement("SELECT SubjectID, SubjectName, ClassName, UserID FROM Subjects");
            ResultSet results = ps.executeQuery();

            //while it is not the end of the database it will display each user with their attributes

            while (results.next()) ; //while loop allows the loop to run until the end of the tables data
            {
                // each item is put into the database as a JSON object that item has been created as
                JSONObject item = new JSONObject(); //this will create an object to go into the JSON array
                item.put("SubjectID", results.getInt(1));
                item.put("SubjectName", results.getString(2));
                item.put("ClassName", results.getString(3));
                item.put("UserID", results.getInt(4));
                list.add(item); // this adds the item for each column's data in the list
            }
            return list.toString(); // this returns the table data as a list for each item
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";

        }
    }
    @GET
    @Path("get/{SubjectID}")   //gets the item based off the userID given
    @Produces(MediaType.APPLICATION_JSON)     //runs as a JSON
    public String getSubject(@PathParam("SubjectID") Integer SubjectID) throws Exception {
        if (SubjectID == null) {     //checks to make sure the UserID isn't null and if it is catches an exception to avoid breaking
            throw new Exception("Subject's 'SubjectID' is missing in the HTTP request's URL.");
        }
        System.out.println("Subject/get/" + SubjectID);   //prints the UserID and the path it followed to find it
        JSONObject item = new JSONObject();   //creates a JSON array for the item
        // a try catch statement for the listing of the item that wants to be listed
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT SubjectName, ClassName FROM Subjects WHERE SubjectID = ?");
            ps.setInt(1, SubjectID);
            ResultSet results = ps.executeQuery();
            // if the result has an item then it will list all the values for it
            if (results.next()) {
                item.put("SubjectID", SubjectID);
                item.put("SubjectName", results.getString(1));
                item.put("ClassName", results.getString(2));
                item.put("UserID", results.getInt(3));
            }
            return item.toString();     //returns the item given for the UserId as a string
            //catches any errors when running the list
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }

}