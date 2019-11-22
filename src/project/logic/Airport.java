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
public class Airport {
    private String airportCode;
    private String airportName;
    private String timeZone;

    public Airport(String airportCode, String airportName, String timeZone) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.timeZone = timeZone;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportCode() {
        return airportCode;
    }

    @Override
    public String toString() {
        return "Airport{" + "airportCode=" + airportCode + '}';
    }
    
    
}
