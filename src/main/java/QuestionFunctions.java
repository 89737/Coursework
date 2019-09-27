import main.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QuestionFunctions{


        public static void insertQuestion(int SubjectID, String QuestionName, String QuestionDate) {
            try {
                //the 2 lines create a user with the properties for that table and then updates the table to insert them in
                PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Question(SubjectID, QuestionName,QuestionDate) VALUES(2,what bird can fly backwards, 26/09/2019)");
                ps.executeUpdate(); //executes the query to update the table
                System.out.println("Record added to Questions table"); //confirmation message that the table has been updated
            } catch (Exception exception) {
                System.out.println(exception.getMessage()); //displays the error message if any
                System.out.println("Error: something gone wrong. please contact the administrator with the error code WC-WA.");
            }
        }



        public static void updateQuestion(int QuestionID,int SubjectID,String QuestionName, String QuestionDate) {
            try {
                // the SQL statement that sets a change to an ID where there is a matching piece of existing data
                PreparedStatement ps = Main.db.prepareStatement("UPDATE QuestionSET QuestionName = ? WHERE QuestionID = 1");
                ps.executeUpdate();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());

            }
        }






        public static void deleteQuestion(int QuestionID,int SubjectID, String QuestionName, String QuestionDate) {
            try {
                // SQL statement that removes a specific part from the database where the requirement is met
                PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Question WHERE questionID = ?");
            } catch (Exception exception) {
                System.out.println(exception.getMessage());

            }
        }



        public static void ListQuestion() {
            try { // try catch method to catch any errors to stop the program breaking
                //
                PreparedStatement ps = Main.db.prepareStatement("SELECT QuestionID,SubjectID, QuestionName, QuestionDate FROM Question");
                ResultSet results = ps.executeQuery();
                //while it is not the end of the database it will display each user with their attributes
                while (results.next()) ;
                {
                    // each column will be displayed for all the entities
                    int QuestionID = results.getInt(1);
                    int SubjectID = results.getInt(2);
                    String QuestionName= results.getString(3);
                    String QuestionDate= results.getString(4);
                    System.out.println(QuestionID + " " + SubjectID + " " + QuestionName+ " " + QuestionDate + " " ); //displays the list of users

                }

            } catch (Exception exception) {
                System.out.println(exception.getMessage());

            }

        }
}
