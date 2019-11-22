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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.logic.Booking;

public class DBBooking {
    
    public static void deleteBooking(Booking b){
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
  
    public static void createBooking() throws DBException{
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
}
  /*public static ArrayList<Customer> getGraduates() throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String sql = "SELECT number "
              + "FROM Students "
              + "WHERE graduate=" + true;
      ResultSet srs = stmt.executeQuery(sql);
      
      ArrayList<Customer> studenten = new ArrayList<Customer>();
      while (srs.next())
        studenten.add(getStudent(srs.getInt("number")));
      DBConnector.closeConnection(con);
      return studenten;
    } catch (DBException dbe) {
      dbe.printStackTrace();
      DBConnector.closeConnection(con);
      throw dbe;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }              
  }*/
 

