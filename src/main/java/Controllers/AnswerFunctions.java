package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class AnswerFunctions {
    @Path("Answers/")
    public class AnswerFunction {

        @POST
        @Path("new")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        //FormDataParam gets the data from a web page to use in the function
        public String insertAnswer( @FormDataParam("AnswerName") String AnswerName, @FormDataParam("QuestionID") Integer QuestionID) {
            try {
                //an if statement to check that any of the fields are null and if so then an error would be displayed to fill in the field
                if (AnswerName == null || QuestionID == null ) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
              //  System.out.println("Answers/new AnswerID=" + AnswerID); //this displays the new UserId being created


                //the line creates a user with the properties for that table and then updates the table to  them in
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Answers (AnswerName, QuestionID) VALUES(?,?)");
                //these lines are for each of the attributes that need to be set a prepared statement data type
                ps.setString(2, AnswerName);
                ps.setInt(1, QuestionID);
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
        public String updateAnswer(@FormDataParam("AnswerID") Integer AnswerID, @FormDataParam("AnswerName") String AnswerName, @FormDataParam("QuestionID") Integer QuestionID) {
            try {

                if (AnswerID == null || AnswerName == null || QuestionID == null ) { //if one or more of the fields are left empty then an error will be thrown to alert the user to fill them in
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("Answers/update AnswerID=" + AnswerID);

                // the SQL statement that sets a change to an ID where there is a matching piece of existing data
                PreparedStatement ps = Main.db.prepareStatement("UPDATE Answers SET AnswerName = ?, QuestionID = ? WHERE AnswerID = ?");
                ps.setString(1,AnswerName);
                ps.setInt(2,QuestionID);
                ps.setInt(3,AnswerID);
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
        //only a userID needs to be found as the rest of the records don't matter for deleting the row
        public String deleteAnswer(@FormDataParam("AnswerID") Integer  AnswerID)  {
            try {
                // an if statement to check that the UserID isn't null and displays an error if it is
                if (AnswerID == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("Answers/delete AnswerID=" + AnswerID); // this displays the userID that has been deleted from the table

                // SQL statement that removes a specific part from the database where the requirement is met
                PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Answers WHERE AnswerID = ?");
                ps.setInt(1, AnswerID);
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
        public String ListAnswers() {
            System.out.println("Answers/list");
            JSONArray list = new JSONArray();  //creates an array for the JSON objects to be used
            try { // try catch method to catch any errors to stop the program breaking
                PreparedStatement ps = Main.db.prepareStatement("SELECT AnswerID, AnswerName, QuestionID FROM Answers");
                ResultSet results = ps.executeQuery();

                //while it is not the end of the database it will display each user with their attributes

                while (results.next())  //while loop allows the loop to run until the end of the tables data
                {
                    // each item is put into the database as a JSON object that item has been created as
                    JSONObject item = new JSONObject(); //this will create an object to go into the JSON array
                    item.put("AnswerID",results.getInt(1));
                    item.put("AnswerName",results.getString(2));
                    item.put("QuestionID",results.getInt(3));
                    list.add(item); // this adds the item for each column's data in the list
                }
                return list.toString(); // this returns the table data as a list for each item
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                return "{\"error\": \"Unable to list items, please see server console for more info.\"}";

            }

        }
        @GET
        @Path("get/{AnswerID}")
        @Produces(MediaType.APPLICATION_JSON)
        public String getAnswer(@PathParam("AnswerID") Integer AnswerID) throws Exception {
            if (AnswerID == null) {
                throw new Exception("Answers's 'UserID' is missing in the HTTP request's URL.");
            }
            System.out.println("Answers/get/" + AnswerID);
            JSONObject item = new JSONObject();
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT AnswerName FROM Answers WHERE AnswerID = ?");
                ps.setInt(1, AnswerID);
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    item.put("AnswerID", AnswerID);
                    item.put("AnswerName", results.getString(1));
                    item.put("QuestionID", results.getInt(2));
                }
                return item.toString();
            } catch (Exception exception) {
                System.out.println("Database error: " + exception.getMessage());
                return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
            }
        }

    }

}
