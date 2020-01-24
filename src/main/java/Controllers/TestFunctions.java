package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
@Path("Tests/")
public class TestFunctions {

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    //FormDataParam gets the data from a web page to use in the function
    public String insertTest(@FormDataParam("TestName") String TestName, @FormDataParam("TestDate") String TestDate, @FormDataParam("SaveTest") Boolean SaveTest, @FormDataParam("TestScore") Integer TestScore, @FormDataParam("TestMax") Integer TestMax, @FormDataParam("SubjectID") Integer SubjectID) {
        try {
            //an if statement to check that any of the fields are null and if so then an error would be displayed to fill in the field
            if ( TestName == null || TestDate == null || SaveTest == null || TestScore == null || TestMax ==null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            //System.out.println("Tests/new TestID=" + TestID); //this displays the new TestID being created


            //the line creates a test with the properties for that table and then updates the table to insert them in
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Tests (TestName, TestDate,  TestScore, TestMax, SubjectID) VALUES(?,?,?,?,?,?)");
            //these lines are for each of the attributes that need to be set a prepared statement data type
            ps.setString(1, TestName);
            ps.setString(2, TestDate);
            ps.setBoolean(3,SaveTest);
            ps.setInt(4,TestScore);
            ps.setInt(5,TestMax);
            ps.setInt(6,SubjectID);
            ps.executeUpdate(); //executes the query to update the table
            return "{\"status\": \"OK\"}"; //confirmation message for when the new test has been inserted

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
    public String updateTest(@FormDataParam("TestID") Integer TestID, @FormDataParam("TestName") String TestName, @FormDataParam("TestDate") String TestDate, @FormDataParam("SaveTest") Boolean SaveTest, @FormDataParam("TestScore") Integer TestScore, @FormDataParam("TestMax") Integer TestMax, @FormDataParam("SubjectID") Integer SubjectID)  {
        try {

            if (TestID == null || TestName == null || TestDate == null) { //if one or more of the fields are left empty then an error will be thrown to alert the test to fill them in
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Tests/update TestID=" + TestID);

            // the SQL statement that sets a change to an ID where there is a matching piece of existing data
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Tests SET TestName = ?, TestDate = ? , TestScore = ?, TestMax = ?, SubjectID = ? WHERE TestID = ?");
            ps.setString(1,TestName);
            ps.setString(2,TestDate);
            ps.setInt(3,TestScore);
            ps.setInt(4,TestMax);
            ps.setInt(5,SubjectID);
            ps.setInt(6,TestID);
            ps.executeUpdate(); //executes the query to be carried out

            return "{\"status\": \"OK\"}"; //returns a confirmation message that the update was a success

        } catch (Exception exception) { // catches any errors coming through and displays an error to display to the test
            System.out.println(exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }
    }


    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    //FormDataParam gets the data from a web page to use in the function
    //only a TestID needs to be found as the rest of the records don't matter for deleting the row
    public String deleteTest(@FormDataParam("TestID") Integer  TestID)  {
        try {
            // an if statement to check that the TestID isn't null and displays an error if it is
            if (TestID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Tests/delete TestID=" + TestID); // this displays the TestID that has been deleted from the table

            // SQL statement that removes a specific part from the database where the requirement is met
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Tests WHERE TestID = ?");
            ps.setInt(1, TestID);
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
    public String ListTests() {
        System.out.println("Tests/list");
        JSONArray list = new JSONArray();  //creates an array for the JSON objects to be used
        try { // try catch method to catch any errors to stop the program breaking
            PreparedStatement ps = Main.db.prepareStatement("SELECT TestID, TestName, TestDate, TestScore, TestMax, SubjectID FROM Tests");
            ResultSet results = ps.executeQuery();

            //while it is not the end of the database it will display each Test with their attributes

            while (results.next()) //while loop allows the loop to run until the end of the tables data
            {
                // each item is put into the database as a JSON object that item has been created as
                JSONObject item = new JSONObject(); //this will create an object to go into the JSON array
                item.put("TestID",results.getInt(1));
                item.put("TestName",results.getString(2));
                item.put("TestDate",results.getString(3));
                item.put("TestScore",results.getInt(4));
                item.put("TestMax", results.getInt(5));
                item.put("SubjecID",results.getInt(6));
                list.add(item); // this adds the item for each column's data in the list
            }
            return list.toString(); // this returns the table data as a list for each item
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";

        }

    }
    @GET
    @Path("get")   //gets the item based off the TestID given
    @Produces(MediaType.APPLICATION_JSON)     //runs as a JSON
    public String getTest(@CookieParam("token") String token) throws Exception {
        if (token == null) {     //checks to make sure the TestID isn't null and if it is catches an exception to avoid breaking
            throw new Exception("Tests's 'TestID' is missing in the HTTP request's URL.");
        }
        System.out.println("Tests/get/");   //prints the TestID and the path it followed to find it

        JSONArray o = new JSONArray();//creates a JSON array for the item
        // a try catch statement for the listing of the item that wants to be listed
        try {
            PreparedStatement tk = Main.db.prepareStatement("SELECT UserID from Users where Token=?"); // this selects the User from where the token is valid
            tk.setString(1,token); // this sets the parameterindex to a string of token so it gives it the value of the Unknown token value
            ResultSet tkr = tk.executeQuery(); // this line executes the Select query to find the User based on the token generated
            //this next query selects the testname and the score from that test from where the token identified the user logged in
            PreparedStatement ps = Main.db.prepareStatement("SELECT TestName, TestScore FROM Tests WHERE UserID = ?");
            ps.setInt(1, tkr.getInt("UserID")); // sets the UserID to that of the one found in the token
            ResultSet results = ps.executeQuery(); //executes the next select query to find the testscore and testnames
            while (results.next()) {   //this goes through each test item for the User where it matches the token generated by logging in
                JSONObject item = new JSONObject();
                item.put("TestName", results.getString(1)); // this adds the testname to the list to be dislayed in the graph in the column
                item.put("TestScore", results.getInt(2)); //this adds the testscore to the column which can then be added to graph by by the JSONobject
                o.add(item);
            }
            return o.toString();     //returns the item given for the TestID as a string
            //catches any errors when running the list to stop the program from breaking if there are any errors when runnning the queries
        } catch (Exception exception) {
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to get item, please see server console for more info.\"}";
        }
    }

}
