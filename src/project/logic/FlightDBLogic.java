/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.logic;

import java.util.ArrayList;
import project.db.DBFlight;

/**
 *
 * @author aroen
 */
public class FlightDBLogic {
    private ArrayList<Flight> flights;
    private static FlightDBLogic flightDBLogic = new FlightDBLogic();
    
   
    public static FlightDBLogic getInstanceflight(){
        return flightDBLogic;
    }
    
    public ArrayList<Flight> getFlights(){
        return flights;
    }
    
    
    public Flight getMinsteCarbon(ArrayList<Flight> flights){
        return null;
        
    }
    public Flight getSingleVlucht(ArrayList<Flight> flights){
        return null;
    
    }
    public Flight getOverstappenVlucht(ArrayList<Flight> flights){
        return null;
    
    }
    public Flight getGoedkoopsteVlucht(ArrayList<Flight> flights){
        return null;
    
    }
    
    
    
}




