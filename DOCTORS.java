package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DOCTORS {

    private Connection connection;


    public DOCTORS(Connection connection) {
        this.connection = connection;

    }


    //2nd method

    public void viewDoctors() {

        String query = "SELECT * FROM DOCTORS";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("DOCTORS:");
            System.out.println("+-----------+------------------+-------+----------+");
            System.out.println("|DOCTOR ID  |NAME              |SPECIALIZATION    |");
            System.out.println("+-----------+------------------+-------+----------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                String specialization = resultSet.getString("SPECIALIZATION");
                System.out.printf("|%-11s|%-18s|%-18s|\n", id, name, specialization);
                System.out.println("+-----------+------------------+-------+----------+");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //3rd method
    public boolean getDoctorID(int id) {
        String query = "SELECT * FROM DOCTORS WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
