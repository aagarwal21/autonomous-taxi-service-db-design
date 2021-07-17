/**
 * DataSense Cohort
 * Database Population Program
 * Project Phase 4
 * 27 April 2018
 **/

// NOTE: the JDBC library must be included in the classpath for this program to work. The library can be found at: https://dev.mysql.com/downloads/connector/j/

import java.sql.*;

import java.util.ArrayList;

public class DataSenseDbPopulator {
    
    static final String DRIVER = "com.mysql.jdbc.Driver";  
    static final String URL = "jdbc:mysql://localhost/DataSenseDB";

    static final String UNAME = "root";
    static final String PWD = "";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ArrayList<String> taxID, customerID, SSN, fleetID, garageID, VIN, tripID;
        taxID = new ArrayList<>();
        customerID = new ArrayList<>();
        SSN = new ArrayList<>();
        fleetID = new ArrayList<>();
        garageID = new ArrayList<>();
        VIN = new ArrayList<>();
        tripID = new ArrayList<>();
        
        Connection dbcon = DriverManager.getConnection(URL, UNAME, PWD);
        
        // Populate COMPANY
        String compInsert = " insert into COMPANY values (?, ?, ?)";
        PreparedStatement stmtCompInsert = dbcon.prepareStatement(compInsert);
        
        for(int i = 1; i <= 32; i++)
        {
            String randTaxID = String.valueOf(((int)(Math.random()*900000000)) + 100000000) + "0";
            
            stmtCompInsert.setString (1, randTaxID);
            stmtCompInsert.setString (2, "Company Name" + i);
            stmtCompInsert.setString   (3, "Company Address" + i);
            stmtCompInsert.execute();
            taxID.add(randTaxID);
        }
        
        // Populate CUSTOMER
        String custInsert = " insert into CUSTOMER values (?, ?, ?, ?, ?)";
        PreparedStatement stmtCustInsert = dbcon.prepareStatement(custInsert);
        
        for(int i = 1; i <= 200; i++)
        {
            String randCustID = String.valueOf(((int)(Math.random()*900000000)) + 100000000) + "0";
            String randPhone = String.valueOf((int)(Math.random()*900)+100) + String.valueOf((int)(Math.random()*900) + 100) +
                    String.valueOf((int)(Math.random()*9000) + 1000);
            
            stmtCustInsert.setString(1, randCustID);
            stmtCustInsert.setString(2, "First Name" + i);
            stmtCustInsert.setString(3, "Last Name" + i);
            stmtCustInsert.setString(4, "Customer Address" + i);
            stmtCustInsert.setString(5, randPhone);
            stmtCustInsert.execute();
            customerID.add(randCustID);
        }
        
        // Populate TRIP
        String tripInsert = " insert into TRIP values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmtTripInsert = dbcon.prepareStatement(tripInsert);
        
        for(int i = 0; i < customerID.size(); i++)
        {
            String randTripID = String.valueOf(((int)(Math.random()*900000000)) + 100000000) + "0";
            
            stmtTripInsert.setString(1, randTripID);
            stmtTripInsert.setString(2, taxID.get((int)(Math.random()*taxID.size())));
            stmtTripInsert.setString(3, customerID.get(i));
            stmtTripInsert.setInt(4, (int)(Math.random()*7) + 1);
            stmtTripInsert.setInt(5, (int)(Math.random()*5) + 1);
            stmtTripInsert.setInt(6, (int)(Math.random()*50) + 1);
            stmtTripInsert.setDouble(7, Math.random()*500);
            stmtTripInsert.execute();
            tripID.add(randTripID);
        }
        
        // Populate GARAGE
        String garInsert = " insert into GARAGE values (?, ?, ?, ?)";
        PreparedStatement stmtGarInsert = dbcon.prepareStatement(garInsert);
        
        for(int i = 0; i < taxID.size(); i++)
        {
            String randGarID = String.valueOf(((int)(Math.random()*900000000)) + 100000000) + "0";
            
            stmtGarInsert.setString(1, randGarID);
            stmtGarInsert.setString(2, taxID.get(i));
            stmtGarInsert.setString(3, "Garage Address " + i);
            stmtGarInsert.setInt(4, 50);
            stmtGarInsert.execute();
            garageID.add(randGarID);
        }
        
        // Populate FLEET
        String fleetInsert = " insert into FLEET values (?, ?, ?, ?)";
        PreparedStatement stmtFleetInsert = dbcon.prepareStatement(fleetInsert);
        
        for(int i = 0; i < garageID.size(); i++)
        {
            String randFleetID = String.valueOf(((int)(Math.random()*900000000)) + 100000000) + "0";
            
            stmtFleetInsert.setString(1, randFleetID);
            stmtFleetInsert.setString(2, taxID.get(i));
            stmtFleetInsert.setString(3, garageID.get(i));
            stmtFleetInsert.setInt(4, (int)(Math.random()*40) + 10);
            stmtFleetInsert.execute();
            fleetID.add(randFleetID);
        }
        
        // Populate ROUTE
        String routeInsert = " insert into ROUTE values (?, ?, ?, ?, ?)";
        PreparedStatement stmtRouteInsert = dbcon.prepareStatement(routeInsert);
        
        for(int i = 0; i < tripID.size(); i++)
        {            
            stmtRouteInsert.setString(1, tripID.get(i));
            stmtRouteInsert.setString(2, "Endpoint 1");
            stmtRouteInsert.setString(3, "Endpoint 2");
            stmtRouteInsert.setBoolean(4, true);
            stmtRouteInsert.setBoolean(5, false);
            stmtRouteInsert.execute();
        }
        
        // Populate VEHICLE
        String vehicleInsert = " insert into VEHICLE values (?, ?, ?, ?, ?)";
        PreparedStatement stmtVehicleInsert = dbcon.prepareStatement(vehicleInsert);
        
        for(int i = 0; i < fleetID.size(); i++)
        {
            for(int j = 0; j < 50; j++)
            {
                String randVIN = String.valueOf(((int)(Math.random()*900000000)) + 100000000) + String.valueOf(((int)(Math.random()*90000000)) + 10000000);
                
                stmtVehicleInsert.setString(1, randVIN);
                stmtVehicleInsert.setString(2, fleetID.get(i));
                stmtVehicleInsert.setString(3, "Make");
                stmtVehicleInsert.setString(4, "Model");
                stmtVehicleInsert.setInt(5, (int)(Math.random()*7)+1);
                stmtVehicleInsert.execute();
                VIN.add(randVIN);
            }
        }
        
        // Populate EMPLOYEE
        String empInsert = " insert into EMPLOYEE values (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmtEmpInsert = dbcon.prepareStatement(empInsert);
        
        for(int i = 0; i < garageID.size(); i++)
        {
            for(int j = 0; j < 2; j++)
            {
                String randSSN = String.valueOf(((long)(Math.random()*900000000)) + 100000000);

                stmtEmpInsert.setString(1, randSSN);
                stmtEmpInsert.setString(2, garageID.get(i));
                stmtEmpInsert.setString(3, "First Name");
                stmtEmpInsert.setString(4, "Last Name");
                stmtEmpInsert.setDate(5, new Date((int)(Math.random()*119), (int)(Math.random()*12)+1, (int)(Math.random()*28)+1));
                stmtEmpInsert.setInt(6, (int)(Math.random()*25000)+50000);
                stmtEmpInsert.execute();
                SSN.add(randSSN);
            }
        }
        
        // Populate FOREMAN
        String fmanInsert = " insert into FOREMAN values (?, ?, ?)";
        PreparedStatement stmtFmanInsert = dbcon.prepareStatement(fmanInsert);
        
        for(int i = 0; i < SSN.size(); i+=2)
        {
            String randLic = String.valueOf(((int)(Math.random()*90000000)) + 10000000);
            
            stmtFmanInsert.setString(1, SSN.get(i));
            stmtFmanInsert.setString(2, randLic);
            stmtFmanInsert.setDate(3, new Date((int)(Math.random()*25)+96, (int)(Math.random()*12)+1, (int)(Math.random()*28)+1));
            stmtFmanInsert.execute();
        }
        
        // Populate MECHANIC
        String mechInsert = " insert into MECHANIC values (?, ?, ?, ?)";
        PreparedStatement stmtMechInsert = dbcon.prepareStatement(mechInsert);
        
        for(int i = 0; i < SSN.size(); i+=2)
        {
            String randLic = String.valueOf(((int)(Math.random()*90000000)) + 10000000);
            String randIns = String.valueOf(((int)(Math.random()*900000000)) + 100000000) + "0";
            
            stmtMechInsert.setString(1, SSN.get(i+1));
            stmtMechInsert.setString(2, SSN.get(i));
            stmtMechInsert.setString(3, randLic);
            stmtMechInsert.setString(4, randIns);
            stmtMechInsert.execute();
        }
        
        // Populate MAINTAINS
        String maintInsert = " insert into MAINTAINS values (?, ?)";
        PreparedStatement stmtMaintInsert = dbcon.prepareStatement(maintInsert);
        
        for(int i = 0; i < garageID.size(); i++)
        {
            for(int j = i*50; j < (i+1)*50; j++)
            {
                stmtMaintInsert.setString(1, SSN.get(i*2+1));
                stmtMaintInsert.setString(2, VIN.get(j));
                stmtMaintInsert.execute();
            }
        }
        
        dbcon.close();
    }
}
