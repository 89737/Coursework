import main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SubjectFunctions {

    public static void insertSubject(int UserID, String SubjectName, String ClassName) {
        try {
            //the 2 lines create a user with the properties for that table and then updates the table to insert them in
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Subject (UserID, subjectname, ClassName) VALUES(1,Physics)");
            ps.executeUpdate(); //executes the query to update the table
            System.out.println("Record added to Users table"); //confirmation message that the table has been updated
        } catch (Exception exception) {
            System.out.println(exception.getMessage()); //displays the error message if any
            System.out.println("Error: something gone wrong. please contact the administrator with the error code WC-WA.");
        }
    }



    public static void updateSubject(int SubjectID,int UserID,String SubjectName, String ClassName) {
        try {
            // the SQL statement that sets a change to an ID where there is a matching piece of existing data
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Subject SET SubjectName = ? WHERE SubjectID = 1");
            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }






    public static void deleteSubject(int SubjectID,int UserID, String SubjectName, String ClassName) {
        try {
            // SQL statement that removes a specific part from the database where the requirement is met
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Subject WHERE SubjectID = ?");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }



    public static void ListSubject() {
        try { // try catch method to catch any errors to stop the program breaking
            //
            PreparedStatement ps = Main.db.prepareStatement("SELECTSubjectID, UserID, SubjectName, ClassName FROM Subject");
            ResultSet results = ps.executeQuery();
            //while it is not the end of the database it will display each user with their attributes
            while (results.next()) ;
            {
                // each column will be displayed for all the entities
                int SubjectID = results.getInt(1);
                int UserID = results.getInt(2);
                String SubjectName= results.getString(3);
                String ClassName = results.getString(4);
                System.out.println(SubjectID + " " + UserID + " " + SubjectName+ " " + ClassName+ " " ); //displays the list of users

            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }

    }

}
