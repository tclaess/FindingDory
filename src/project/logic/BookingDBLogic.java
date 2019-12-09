/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.logic;
import java.util.ArrayList;
import project.db.DBBooking;
import project.db.DBException;

/**
 *
 * @author aroen
 */
public class BookingDBLogic {
    
    
    private ArrayList<Object> bookings;
    private ArrayList<Booking> BookNr;
    private static  BookingDBLogic bookingDBLogic= new BookingDBLogic();
/*
    public BookingDBLogic() {
    

            BookNr = DBBooking.getBookNr();

            
          }
*/
    public static BookingDBLogic getInstance() {
        return bookingDBLogic;
    }

    public ArrayList<Object> getBookings() {
        return bookings;
    }

    public ArrayList<Booking> getBookNr() {
        return BookNr;
    }
    
    public static Booking getBooking(String number) throws DBException {
        
        return DBBooking.getBooking(number);
    }
    public void createBooking(Customer c, Booking b, ArrayList<Flight[]> f) throws DBException {
        
        return DBBooking.createBooking(c, b, f);

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}




