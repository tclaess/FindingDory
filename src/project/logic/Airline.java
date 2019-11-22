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
public class Airline {
    private String ICAO;
    private String airlName;

    public Airline(String ICAO, String airlName) {
        this.ICAO = ICAO;
        this.airlName = airlName;
    }

    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

    public void setAirlName(String airlName) {
        this.airlName = airlName;
    }

    public String getICAO() {
        return ICAO;
    }

    public String getAirlName() {
        return airlName;
    }

    @Override
    public String toString() {
        return "Airline{" + "ICAO=" + ICAO + ", airlName=" + airlName + '}';
    }
    
    
}