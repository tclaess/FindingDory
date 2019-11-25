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
    private String arrivalDateTime;
    private String carbondio;
    private String a_Code;
    private String d_Code;
    private String ICAO;
    

    public Flight(String flightNr, String depDateTime, String arrivalDateTime, String carbondio, String ICAO, String d_Code, String a_Code) {
        this.flightNr = flightNr;
        this.depDateTime = depDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.carbondio = carbondio;
        this.ICAO = ICAO;
        this.a_Code = a_Code;
        this.d_Code = d_Code;
    }

    public String getFlightNr() {
        return flightNr;
    }

    public String getDepDateTime() {
        return depDateTime;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public String getCarbondio() {
        return carbondio;
    }

    public String getA_Code() {
        return a_Code;
    }

    public String getD_Code() {
        return d_Code;
    }

    public String getICAO() {
        return ICAO;
    }

    public void setFlightNr(String flightNr) {
        this.flightNr = flightNr;
    }

    public void setDepDateTime(String depDateTime) {
        this.depDateTime = depDateTime;
    }
    
    public

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public void setCarbondio(String carbondio) {
        this.carbondio = carbondio;
    }

    public void setA_Code(String a_Code) {
        this.a_Code = a_Code;
    }

    public void setD_Code(String d_Code) {
        this.d_Code = d_Code;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    @Override
    public String toString() {
        return "flightNr= " + flightNr + "\n" 
                + "depDateTime= " + depDateTime + "\n"  
                + "arrivalDateTime= " + arrivalDateTime + "\n"  
                + "carbondio= " + carbondio + "\n"  
                + "d_Code= " + d_Code + "\n"  
                + "a_Code= " + a_Code + "\n"  
                + "ICAO= " + ICAO + "\n"
                + " " + "\n";
    }

    
    
     
    
    
    
    
}
