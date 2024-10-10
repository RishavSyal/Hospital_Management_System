package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementClass {

    private static final String url = "jdbc:mysql://localhost:3306/hospital";

    private static final String username = "root";

    private static final String password = "rishu@123";


    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, scanner);
            DOCTORS doctor = new DOCTORS(connection);
            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("1. Add Patient");
                System.out.println("2. View  Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter Your Choice");
                int Choice = scanner.nextInt();

                switch (Choice) {
                    case 1:
                        // Add Patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        // View Patient
                        patient.ViewPatients();
                        System.out.println();
                        break;
                    case 3:
                        //view doctor
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        //Book Appointment
                        bookAppointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;

                    case 5:
                        //exit
                        System.out.println("Thank You !, For Using Hospital Management System ! ");
                        System.out.println("REGARDS ");
                        System.out.println("Er.Rishav Syal ");
                        return;
                    default:
                        System.out.println("Enter Valid Choice!!!!");
                        break;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public static void bookAppointment(Patient patient,DOCTORS doctor,Connection connection,Scanner scanner){
        System.out.println("Enter Patient ID: ");
        int patientID=scanner.nextInt();
        System.out.println("Enter Doctor ID: ");
        int doctorID=scanner.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD):");
        String appointmentDate=scanner.next();
        if(patient.getPatientID(patientID) && doctor.getDoctorID(doctorID)) {
          if(checkDoctorAvailability(doctorID,appointmentDate,connection)){
              String appointmentQuery= "Insert into appointments(PATIENT_ID,DOCTOR_ID,APPOINTMENT_DATE) VALUES(?,?,?)";
              try{
                  PreparedStatement preparedStatement=connection.prepareStatement(appointmentQuery);
                  preparedStatement.setInt(1,patientID);
                  preparedStatement.setInt(2,doctorID);
                  preparedStatement.setString(3,appointmentDate);
                  int rowsaffected = preparedStatement.executeUpdate();
                  if(rowsaffected>0){
                      System.out.println("Appointment Booked!!");
                  }
                  else {
                      System.out.println("Failed to Book Appointment!!");
                  }
              }
              catch (SQLException e){
                  e.printStackTrace();
              }

          }
          else{
              System.out.println("Doctor not  available on this date !!!");
          }
        }
        else{
            System.out.println("Either doctor or Patient does not exit!!!");

    }
}


public static boolean checkDoctorAvailability(int doctorID,String appointmentDate,Connection connection){
String query= "SELECT COUNT(*) FROM appointments WHERE doctor_id =? AND appointment_date=? ";

try{
    PreparedStatement preparedStatement=connection.prepareStatement(query);
    preparedStatement.setInt(1,doctorID);
    preparedStatement.setString(2,appointmentDate);
    ResultSet resultSet=preparedStatement.executeQuery();
    if(resultSet.next()){
        int count=resultSet.getInt(1);
        if(count==0){
            return true;
        }
        else {
            return false;
        }
    }

}
catch (SQLException e){
    e.printStackTrace();
}
  return false;
    }

    }

