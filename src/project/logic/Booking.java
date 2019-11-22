/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.logic;

import project.db.DBBooking;
import project.db.DBException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.db.DBException;
import project.db.DBBooking;

/**
 *
 * @author fgailly
 */
public class Booking {
    private String bookNr;
    private String bookDate;

    public Booking(String bookNr, String bookDate) {
        this.bookNr = bookNr;
        this.bookDate = bookDate;
    }

    public void setBookNr(String bookNr) {
        this.bookNr = bookNr;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getBookNr() {
        return bookNr;
    }

    public String getBookDate() {
        return bookDate;
    }

    @Override
    public String toString() {
        return "Booking{" + "bookNr=" + bookNr + ", bookDate=" + bookDate + '}';
    }
    

  
    
  
  
  
  
}
