import main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestFunctions {


    public static void insertTest(int SubjectID, String TestName, String TestDate, String TestScore, String TestMax) {
        try {
            //the 2 lines create a user with the properties for that table and then updates the table to insert them in
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Test(SubjectID, Testname,TestDate, TestScore, TestMax) VALUES(1,Milgram,26/09/2019, 9,10 )");
            ps.executeUpdate(); //executes the query to update the table
            System.out.println("Record added to Test table"); //confirmation message that the table has been updated
        } catch (Exception exception) {
            System.out.println(exception.getMessage()); //displays the error message if any
            System.out.println("Error: something gone wrong. please contact the administrator with the error code WC-WA.");
        }
    }



    public static void updateTest(int TestID,int SubjectID,String TestName, String TestDate, String TestScore, String TestMax) {
        try {
            // the SQL statement that sets a change to an ID where there is a matching piece of existing data
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Test SET TestName = ? WHERE TestID= 1");
            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }






    public static void deleteTest(int TestID,int SubjectID, String TestName, String TestDate, String TestScore, String TestMax) {
        try {
            // SQL statement that removes a specific part from the database where the requirement is met
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Test WHERE TestID= ?");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }



    public static void ListTests() {
        try { // try catch method to catch any errors to stop the program breaking
            //
            PreparedStatement ps = Main.db.prepareStatement("SELECT TestID, SubjectID, TestName, TestDate, TestScore, TestMax FROM Subject");
            ResultSet results = ps.executeQuery();
            //while it is not the end of the database it will display each user with their attributes
            while (results.next()) ;
            {
                // each column will be displayed for all the entities
                int TestID = results.getInt(1);
                int SubjectID= results.getInt(2);
                String TestName= results.getString(3);
                String TestDate  = results.getString(4);
                String TestScore = results.getString(5);
                String TestMax = results.getString(6);
                System.out.println(TestID + " " +  SubjectID + " " + TestName+ " " + TestDate+ " " + TestScore + " " + TestMax + " " ); //displays the list of users

            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }

    }

}
