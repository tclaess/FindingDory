/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.db;

import project.logic.Customer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.logic.Booking;
import project.logic.Flight;

public class DBBooking {
    
    public static Booking getBooking(String bookNr) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT * "
	+ "FROM BOOKING "
	+ "WHERE BOOKNR = '" + bookNr + "'";

        // let op de spatie na '*' en 'CUSTOMER' in voorgaande SQL
      ResultSet rs = stmt.executeQuery(sql);
      String bookNR, bookDate;
      
      if (rs.next()) {
        bookNR = rs.getString("BOOKNR");
        bookDate = rs.getString("BOOKDATE");
      } else {// we verwachten slechts 1 rij...
	DBConnector.closeConnection(con);
	return null;
      }
      Booking booking = new Booking(bookNR, bookDate);
      DBConnector.closeConnection(con);
      return booking;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  }
    
    public static void deleteBooking(Booking b) throws DBException{
        Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT BOOKNR "
              + "FROM BOOKING "
              + "WHERE BOOKNR = '" + b.getBookNr() + "'";
      
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next()) {
        //DELETE
        sql = "DELETE FROM BOOKING "
                + "WHERE BOOKNR = '" + b.getBookNr() + "'";
        stmt.executeUpdate(sql);
      } else {// we verwachten slechts 1 rij...
	DBConnector.closeConnection(con);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
    }
  
    public static void createBooking(Customer c, Booking b, ArrayList<Flight[]> f) throws DBException{
      Connection con = null;
      try {
         con = DBConnector.getConnection();
         Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
         
         // INSERT INTO BOOKING
	String sql1 = "INSERT into BOOKING "
                + "(BOOKNR, BOOKDATE) "
		+ "VALUES ('" + b.getBookNr()
                + "', '" + b.getBookDate() + "')";
        stmt.executeUpdate(sql1);
        
        //  INSERT INTO BOOKINGS
        String sql2 = "INSERT into BOOKINGS "
                + "(ID, COUNTRY, BOOKNR) "
                + "VALUES ('" + c.getID()
                + "', '" + c.getCountry()
                + "', '" + b.getBookNr() + "')";
        stmt.executeUpdate(sql2);
        
        // INSERT INTO FLIGHTLINE
        if(f.size() == 1){
        String sql3 = "INSERT into FLIGHT_LINE "
                + "(BOOKNR, FLIGHTNR, DEPDATETIME) "
                + "VALUES ('" + b.getBookNr()
                + "', '" + f.get(0)[0].getFlightNr()
                + "', '" + f.get(0)[0].getDepDateTime() + "')";
        stmt.executeUpdate(sql3);
        }
        
        else{
              String sql3 = "INSERT into FLIGHT_LINE "
                + "(BOOKNR, FLIGHTNR, DEPDATETIME) "
                + "VALUES ('" + b.getBookNr()
                + "', '" + f.get(0)[0].getFlightNr()
                + "', '" + f.get(0)[0].getDepDateTime() + "')";
              stmt.executeUpdate(sql3);
              
              String sql4 = "INSERT into FLIGHT_LINE "
                + "(BOOKNR, FLIGHTNR, DEPDATETIME) "
                + "VALUES ('" + b.getBookNr()
                + "', '" + f.get(0)[1].getFlightNr()
                + "', '" + f.get(0)[1].getDepDateTime() + "')";
              stmt.executeUpdate(sql4);
        }
        
      DBConnector.closeConnection(con);
      }
      catch (DBException dbe) {
      dbe.printStackTrace();
      DBConnector.closeConnection(con);
      throw dbe;
    } 
      catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }              
  }
    
    public static void shortestBooking(Booking booking) throws DBException{
       Connection con = null;
      try {
         con = DBConnector.getConnection();
         Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
         
        
         
      }
      catch (DBException dbe) {
      dbe.printStackTrace();
      DBConnector.closeConnection(con);
      throw dbe;
    } 
      catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }  
     
    } 

    public static void leastTransfersBooking(Booking booking) throws DBException{
        Connection con = null;
      try {
         con = DBConnector.getConnection();
         Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
         
        
         
      }
      catch (DBException dbe) {
      dbe.printStackTrace();
      DBConnector.closeConnection(con);
      throw dbe;
    } 
      catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }  
    
    } 
    

    public static void cheapestBooking(Booking booking) throws DBException{
    Connection con = null;
      try {
         con = DBConnector.getConnection();
         Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
         
        
         
      }
      catch (DBException dbe) {
      dbe.printStackTrace();
      DBConnector.closeConnection(con);
      throw dbe;
    } 
      catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }              
}
    
    public static void lowestCO2Booking(Booking booking) throws DBException{
        Connection con = null;
      try {
         con = DBConnector.getConnection();
         Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
         
        
         
      }
      catch (DBException dbe) {
      dbe.printStackTrace();
      DBConnector.closeConnection(con);
      throw dbe;
    } 
      catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
     
    }
    // main 
  public static void main(String args[]) throws ParseException{
      
      try {
          //Booking booking = new Booking("4321", "2019-12-05");
          //DBBooking.deleteBooking(booking);
          Customer c = DBCustomer.getCustomer("G32019S2", "Belgium");
          Booking b = new Booking("9656", "2019-12-05");
          ArrayList<Flight[]> f = DBFlight.getTransferFlights("brussels", "new york", "23/11/2019");
          DBBooking.createBooking(c, b, f);
          
                  
          
    } 
    catch (DBException e) {
       System.out.println("e.getMessage");
        // Logger.getLogger(DBBooking.class.getName()).log(Level.SEVERE, null, ex);
     } 
     
      
  }
}
  
 

