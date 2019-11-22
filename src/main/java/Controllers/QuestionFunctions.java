package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("Questions/")
public class QuestionFunctions {

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    //FormDataParam gets the data from a web page to use in the function
    public String insertQuestion( @FormDataParam("QuestionName") String QuestionName, @FormDataParam("QuestionDate") String QuestionDate, @FormDataParam("SubjectID") Integer SubjectID) {
        try {
            //an if statement to check that any of the fields are null and if so then an error would be displayed to fill in the field
            if ( QuestionName == null || QuestionDate == null || SubjectID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
           // System.out.println("Questions/new QuestionID=" + QuestionID); //this displays the new QuestionID being created


            //the line creates a user with the properties for that table and then updates the table to insert them in
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Questions (QuestionName, QuestionDate, SubjectID) VALUES(?,?,?)");
            //these lines are for each of the attributes that need to be set a prepared statement data type

            ps.setString(1, QuestionName);
            ps.setString(2, QuestionDate);
            ps.setInt(3,SubjectID);
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
    public String updateQuestion(@FormDataParam("QuestionID") Integer QuestionID, @FormDataParam("QuestionName") String QuestionName, @FormDataParam("QuestionDate") String QuestionDate, @FormDataParam("SubjectID") String SubjectID) {
        try {

            if (QuestionID == null || QuestionName == null || QuestionDate == null || SubjectID ==null) { //if one or more of the fields are left empty then an error will be thrown to alert the user to fill them in
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Questions/update QuestionID=" + QuestionID);

            // the SQL statement that sets a change to an ID where there is a matching piece of existing data
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Questions SET QuestionName = ?, QuestionDate = ?, SubjectID = ? WHERE QuestionID = ?");
            ps.setString(1,QuestionName);
            ps.setString(2,QuestionDate);
            ps.setString(3,SubjectID);
            ps.setInt(4,QuestionID);
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
    //only a QuestionID needs to be found as the rest of the records don't matter for deleting the row
    public String deleteQuestion(@FormDataParam("QuestionID") Integer  QuestionID)  {
        try {
            // an if statement to check that the QuestionID isn't null and displays an error if it is
            if (QuestionID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Questions/delete QuestionID=" + QuestionID); // this displays the QuestionID that has been deleted from the table

            // SQL statement that removes a specific part from the database where the requirement is met
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Questions WHERE QuestionID = ?");
            ps.setInt(1, QuestionID);
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
    public String ListQuestions() {
        System.out.println("Users/list");
        JSONArray list = new JSONArray();  //creates an array for the JSON objects to be used
        try { // try catch method to catch any errors to stop the program breaking
            PreparedStatement ps = Main.db.prepareStatement("SELECT QuestionID, QuestionName, QuestionDate, SubjectID FROM Questions");
            ResultSet results = ps.executeQuery();

            //while it is not the end of the database it will display each user with their attributes

            while (results.next()) ; //while loop allows the loop to run until the end of the tables data
            {
                // each item is put into the database as a JSON object that item has been created as
                JSONObject item = new JSONObject(); //this will create an object to go into the JSON array
                item.put("QuestionID",results.getInt(1));
                item.put("QuestionName",results.getString(2));
                item.put("QuestionDate",results.getString(3));
                item.put("SubjectID",results.getString(4));
                list.add(item); // this adds the item for each column's data in the list
            }
            return list.toString(); // this returns the table data as a list for each item
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";

        }

    }
    @GET
    @Path("get/{QuestionID}")   //gets the item based off the QuestionID given
    @Produces(MediaType.APPLICATION_JSON)     //runs as a JSON
    public String getQuestion(@PathParam("QuestionID") Integer QuestionID) throws Exception {
        if (QuestionID == null) {     //checks to make sure the QuestionID isn't null and if it is catches an exception to avoid breaking
            throw new Exception("Questions's 'QuestionID' is missing in the HTTP request's URL.");
        }
        System.out.println("Users/get/" + QuestionID);   //prints the QuestionID and the path it followed to find it
        JSONObject item = new JSONObject();   //creates a JSON array for the item
        // a try catch statement for the listing of the item that wants to be listed
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT QuestionName, QuestionDate, SubjectID FROM Questions WHERE QuestionID = ?");
            ps.setInt(1, QuestionID);
            ResultSet results = ps.executeQuery();
            // if the result has an item then it will list all the values for it
            if (results.next()) {
                item.put("QuestionID", QuestionID);
                item.put("QuestionName", results.getString(1));
                item.put("QuestionDate", results.getString(2));
                item.put("SubjectID", results.getString(3));
            }
            return item.toString();     //returns the item given for the QuestionID as a string
            //catches any errors when running the list
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }

}