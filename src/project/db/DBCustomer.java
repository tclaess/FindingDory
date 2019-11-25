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



public class DBCustomer {
    
    public static Customer getCustomer(String ID, String countryOfC) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT * "
	+ "FROM CUSTOMER "
	+ "WHERE ID = '" + ID + "' AND COUNTRY = '" + countryOfC + "'";

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
  
  public static ArrayList<Customer> getCustomers() throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT * "
              + "FROM CUSTOMER ";
      ResultSet srs = stmt.executeQuery(sql);
      ArrayList<Customer> customers = new ArrayList<Customer>();
      // cursor mechanisme
      while (srs.next())
      customers.add(getCustomer(srs.getString("ID"), srs.getString("country")));
      DBConnector.closeConnection(con);
      return customers;
      //Kunnen extra string toevoegen voor een mooiere presentatie (System.out.println("..."))
    } catch (DBException dbe) {
      dbe.printStackTrace();
      DBConnector.closeConnection(con);
      throw dbe;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
  
  
  public static boolean saveCustomer(Customer c) throws DBException {
    Connection con = null;
    boolean done = false;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT ID, COUNTRY "
              + "FROM CUSTOMER "
              + "WHERE ID = '" + c.getID() + "' AND COUNTRY = '" + c.getCountry() + "'";
      ResultSet srs = stmt.executeQuery(sql);
      if (srs.next()) {
        // UPDATE
	sql = "UPDATE CUSTOMER "
                + "SET FNAME = '" + c.getFirstName() + "'"
		+ ", LNAME = '" + c.getLastName() + "'"
		+ ", BDATE = '" + c.getBirthdayDate() + "'"
		+ ", GENDER = '" + c.getGender() + "'"
                + "WHERE ID = '" + c.getID() + "' AND  COUNTRY = '" + c.getCountry() + "'";
        stmt.executeUpdate(sql);
        done = true;
      } else {
	// INSERT
	sql = "INSERT into CUSTOMER "
                + "(ID, COUNTRY, FNAME, LNAME, BDATE, GENDER) "
		+ "VALUES ('" + c.getID() 
                + "', '" + c.getCountry()
		+ "', '" + c.getFirstName()
                + "', '" + c.getLastName()
		+ "', " + c.getBirthdayDate() 
                + ", '" + c.getGender() + "')";
        stmt.executeUpdate(sql);
        done = true;
      }
      DBConnector.closeConnection(con);
      return done;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  } 
  
  public static boolean deleteCustomer(Customer c) throws DBException{
     Connection con = null;
     boolean done = false;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT ID, COUNTRY "
              + "FROM CUSTOMER "
              + "WHERE ID = '" + c.getID() + "' AND COUNTRY = '" + c.getCountry() + "'";
      
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        //DELETE
        sql = "DELETE FROM CUSTOMER "
                + "WHERE ID = '" + c.getID() + "' AND  COUNTRY = '" + c.getCountry() + "'";
        stmt.executeUpdate(sql);
        done = true;
      } else {// we verwachten slechts 1 rij...
	DBConnector.closeConnection(con);
      }
      
      return done;
      
    }
    
    catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
  
   public static void main(String[] args){
       
     try {
      

      Customer customer = new Customer("G32019S1", "Belgium", "Timothy", "De Campenaere", "19990909", "MALE");
      System.out.println(DBCustomer.getCustomers());
         
    } 
    catch (DBException ex) {
        System.out.println("ex.getMessage");
      // Logger.getLogger(DBBooking.class.getName()).log(Level.SEVERE, null, ex);
    } 
  }
}