/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.logic;

/**
 *
 * @author timothydecampenaere
 */
public class Flight {
    private String flightNr;
    private String depDateTime;
    private Airline airline;
    private Airport dAirport;
    private Airport aAirport;
    private String aCode;
    private String dCode;
    private String ICAO;
   

    public Flight(String flightNr, String depDateTime) {
        this.flightNr = flightNr;
        this.depDateTime = depDateTime;
        this.ICAO = airline.getICAO();
        this.aCode = aAirport.getAirportCode();
        this.dCode = dAirport.getAirportCode();
    }

    public void setFlightNr(String flightNr) {
        this.flightNr = flightNr;
    }

    public void setDepDateTime(String depDateTime) {
        this.depDateTime = depDateTime;
    }

    public String getFlightNr() {
        return flightNr;
    }

    public String getDepDateTime() {
        return depDateTime;
    }

    public Airline getAirline() {
        return airline;
    }

    public Airport getdAirport() {
        return dAirport;
    }

    public Airport getaAirport() {
        return aAirport;
    }

    public String getaCode() {
        return aCode;
    }

    public String getdCode() {
        return dCode;
    }

    public String getICAO() {
        return ICAO;
    }

    
    @Override
    public String toString() {
        return "Flight{" + "flightNr=" + flightNr + ", depDateTime=" + depDateTime + ", airline=" + airline + ", dAirport=" + dAirport + ", aAirport=" + aAirport + ", aCode=" + aCode + ", dCode=" + dCode + ", ICAO=" + ICAO + '}';
    }
    
    
    
    
}
