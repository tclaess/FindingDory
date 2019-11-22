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

    public Airport(String airportCode) {
        this.airportCode = airportCode;
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
