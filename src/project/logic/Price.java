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
public class Price {
    int price;
    String flightNr;
    String depDateTime;

    public Price(int price, String flightNr, String depDateTime) {
        this.price = price;
        this.flightNr = flightNr;
        this.depDateTime = depDateTime;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setFlightNr(String flightNr) {
        this.flightNr = flightNr;
    }

    public void setDepDateTime(String depDateTime) {
        this.depDateTime = depDateTime;
    }

    public int getPrice() {
        return price;
    }

    public String getFlightNr() {
        return flightNr;
    }

    public String getDepDateTime() {
        return depDateTime;
    }
    
    
}
