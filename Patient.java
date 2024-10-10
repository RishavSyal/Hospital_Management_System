package HospitalManagementSystem;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
        public void addPatient () {
            System.out.println("Enter Patient Name:");
            String name = scanner.next();

            System.out.println("Enter Patient Age:");
            int age = scanner.nextInt();

            System.out.println("Enter Patient Gender:");
            String gender = scanner.next();


        try {
            String query = "INSERT INTO PATIENTS(NAME,AGE,GENDER) VALUES(?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int rowaffected=preparedStatement.executeUpdate();
            if(rowaffected>0){
                System.out.println("Patient Added Sucessfully");
            }
            else{
                System.out.println("NOT Added, Failed to add Patient");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //2nd method

    public void ViewPatients(){

        String query="SELECT * FROM PATIENTS";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            System.out.println("PATIENTS:");
            System.out.println("+-----------+------------------+-------+----------+");
            System.out.println("|Patient ID |NAME              |AGE    | GENDER   |");
            System.out.println("+-----------+------------------+-------+----------+");

            while(resultSet.next()){
                int id=resultSet.getInt("ID");
                String name=resultSet.getString("NAME");
                int age=resultSet.getInt("AGE");
                String gender=resultSet.getString("GENDER");
                System.out.printf("| %-10s| %-17s| %-6s| %-9s|\n",id,name,age,gender);
                System.out.println("+-----------+------------------+-------+----------+");
            }


        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    //3rd method
  public boolean getPatientID(int id){
        String query="SELECT * FROM patients WHERE id = ?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
           preparedStatement.setInt(1,id);
           ResultSet resultSet=preparedStatement.executeQuery();
           if(resultSet.next()){
               return true;
           }
           else {
               return false;
           }

        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
  }




}
