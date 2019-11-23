/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.logic.Flight;

/**
 *
 * @author tomclaessens
 */
public class DBFlight {
    public static Flight getFlight(String dAirport, String aAirport, String depDate) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String D_Code = getCode(dAirport);
      String A_Code = getCode(aAirport);
      String Correct_depDate = getDateTime(depDate);
      
      String sql = "SELECT * "
	+ "FROM FLIGHT "
	+ "WHERE A_CODE = '" + A_Code + "' AND D_CODE = '" + D_Code + "' AND CAST(DEPDATETIME as date) = '" + Correct_depDate + "'";

        // let op de spatie na '*' en 'CUSTOMER' in voorgaande SQL
      ResultSet rs = stmt.executeQuery(sql);
      String flightNr, depDateTime, arrivalDateTime, carbondio, ICAO, d_Code, a_Code;
      
      if (rs.next()) {
        flightNr = rs.getString("FLIGHTNR");
        depDateTime = rs.getString("DEPDATETIME");
	arrivalDateTime = rs.getString("ARRIVALDATETIME");
	carbondio = rs.getString("CARBONDIO");
	ICAO = rs.getString("ICAO");
	a_Code = rs.getString("A_CODE");
        d_Code = rs.getString("D_CODE");
	
      } else {// we verwachten slechts 1 rij...
	DBConnector.closeConnection(con);
	return null;
      }
      Flight flight = new Flight(flightNr, depDateTime, arrivalDateTime, carbondio, ICAO, d_Code, a_Code);
      DBConnector.closeConnection(con);
      return flight;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  } 
    
  public static String getCode(String AirportName) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      
      String sql = "SELECT AIRPORTCODE "
	+ "FROM AIRPORT "
	+ "WHERE AIRPORTNAME = '" + AirportName + "'";

        
      ResultSet rs = stmt.executeQuery(sql);
      String airportcode;
      
      if (rs.next()) {
        airportcode = rs.getString("AIRPORTCODE");
        
	
      } else {// we verwachten slechts 1 rij...
	DBConnector.closeConnection(con);
	return null;
      }
      
      DBConnector.closeConnection(con);
      return airportcode;
      
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
    
  }
  
  public static String getDateTime(String date){
      String a = Character.toString(date.charAt(0));
      String b = Character.toString(date.charAt(1));
      String c = Character.toString(date.charAt(3));
      String d = Character.toString(date.charAt(4));
      String e = Character.toString(date.charAt(6));
      String f = Character.toString(date.charAt(7));
      String g = Character.toString(date.charAt(8));
      String h = Character.toString(date.charAt(9));
      
      
      String dateTime = e+f+g+h+ "-" +c+d+ "-" +a+b;
      return dateTime;
  }
  
  
  public static void main(String args[]){
      
      try {
      
          
        /* String test = getCode("brussels");
            System.out.println(test);  */ 
        
        Flight flight = getFlight("new york", "brussels", "22/11/2019");
          System.out.println(flight.getA_Code());
        
        
          
    
    } 
    catch (DBException e) {
       System.out.println("e.getMessage");
        // Logger.getLogger(DBBooking.class.getName()).log(Level.SEVERE, null, ex);
     } 
      
      
  }
}
