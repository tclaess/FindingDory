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
import project.logic.Customer;

/**
 *
 * @author tomclaessens
 */
public class DBFlight {
        public static Customer getFlight(String dAirport, String aAirport, String depDate) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      
      String sql = "SELECT A_CODE "
	+ "FROM AIRPORT "
	+ "WHERE AIRPORTCODE = '" + dAirport + "' AND COUNTRY = '" + countryOfC + "'";

        // let op de spatie na '*' en 'CUSTOMER' in voorgaande SQL
      ResultSet rs = stmt.executeQuery(sql);
      String id, country, firstName, lastName, birthdayDate, gender;
      
      if (rs.next()) {
        id = rs.getString("ID");
        country = rs.getString("COUNTRY");
	firstName = rs.getString("FNAME");
	lastName = rs.getString("LNAME");
	birthdayDate = rs.getString("BDATE");
	gender = rs.getString("GENDER");
	
      } else {// we verwachten slechts 1 rij...
	DBConnector.closeConnection(con);
	return null;
      }
      Customer customer = new Customer(id, country, firstName, lastName, birthdayDate, gender);
      DBConnector.closeConnection(con);
      return customer;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
  public static Customer getCode(String AirportName) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      
      String sql = "SELECT AIRPORTCODE "
	+ "FROM AIRPORT "
	+ "WHERE AIRPORTCODE = '" + AirportName + "'";

        // let op de spatie na '*' en 'CUSTOMER' in voorgaande SQL
      ResultSet rs = stmt.executeQuery(sql);
      String id, country, firstName, lastName, birthdayDate, gender;
      
      if (rs.next()) {
        id = rs.getString("ID");
        country = rs.getString("COUNTRY");
	firstName = rs.getString("FNAME");
	lastName = rs.getString("LNAME");
	birthdayDate = rs.getString("BDATE");
	gender = rs.getString("GENDER");
	
      } else {// we verwachten slechts 1 rij...
	DBConnector.closeConnection(con);
	return null;
      }
      Customer customer = new Customer(id, country, firstName, lastName, birthdayDate, gender);
      DBConnector.closeConnection(con);
      return customer;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
  
}
