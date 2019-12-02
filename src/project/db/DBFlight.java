/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.logic.Flight;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author tomclaessens
 */
public class DBFlight {
    
  public static ArrayList<ArrayList<Flight[]>> getFlights(String dAirport, String aAirport, String depDate) throws DBException, ParseException {
        ArrayList<Flight[]> singleFlights = getSingleFlights(dAirport, aAirport, depDate);
        ArrayList<Flight[]> transferFlights = getTransferFlights(dAirport, aAirport, depDate);
        ArrayList<ArrayList<Flight[]>> flights = new ArrayList();
        flights.add(singleFlights);
        flights.add(transferFlights);
        return flights; 
    }
    
    public static ArrayList<Flight[]> getSingleFlights(String dAirport, String aAirport, String depDate) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      String D_Code = getCode(dAirport);
      String A_Code = getCode(aAirport);
      String Correct_depDate = getDateTime(depDate);
      
      String sql = "SELECT * "
	+ "FROM FLIGHT "
	+ "WHERE A_CODE = '" + A_Code + "' AND D_CODE = '" + D_Code + "' AND CAST(DEPDATETIME as date) = '" + Correct_depDate + "'";

        // let op de spatie na '*' en 'CUSTOMER' in voorgaande SQL
      ResultSet rs = stmt.executeQuery(sql);
      String flightNr, depDateTime, arrivalDateTime, carbondio, ICAO, d_Code, a_Code;
      ArrayList<Flight[]> singleFlights = new ArrayList();
      while (rs.next()) {
        flightNr = rs.getString("FLIGHTNR");
        depDateTime = rs.getString("DEPDATETIME");
	arrivalDateTime = rs.getString("ARRIVALDATETIME");
	carbondio = rs.getString("CARBONDIO");
	ICAO = rs.getString("ICAO");
	a_Code = rs.getString("A_CODE");
        d_Code = rs.getString("D_CODE");
	Flight flight = new Flight(flightNr, depDateTime, arrivalDateTime, carbondio, ICAO, d_Code, a_Code);
        Flight[] flightArr = new Flight[1];
        flightArr[0] = flight; 
        singleFlights.add(flightArr);
      }
      DBConnector.closeConnection(con);
      return singleFlights;
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
  } 
 

  public static ArrayList<Flight[]> getTransferFlights(String dAirport, String aAirport, String depDate) throws DBException, ParseException
  {
    Connection con = null;
    
      String D_Code = getCode(dAirport);
      String A_Code = getCode(aAirport);
      String Correct_depDate = getDateTime(depDate);
      
      ArrayList<Flight> arrayEersteVluchten = getEersteVluchten(D_Code, Correct_depDate);
      ArrayList<Flight> arrayTweedeVluchten = getTweedeVluchten(A_Code, Correct_depDate);
      
      // twee arrayslists vergelijken
      
      int lengte1 = arrayEersteVluchten.size();
      int lengte2 = arrayTweedeVluchten.size(); 
      ArrayList<Flight[]> arrayVluchten = new ArrayList<Flight[]>();
      for(int i = 0; i < lengte1; i++ ){
          for(int e = 0; e < lengte2; e++ ){
              if(arrayEersteVluchten.get(i).getA_Code().equalsIgnoreCase(arrayTweedeVluchten.get(e).getD_Code())){
                  Flight[] flightArr = new Flight[2];
                  flightArr[0] = arrayEersteVluchten.get(i);
                  flightArr[1] = arrayTweedeVluchten.get(e);
                  arrayVluchten.add(flightArr);
              }
          }
      }
      
      ArrayList<Flight[]> transferVluchten = tijdControle(arrayVluchten);
      
      return  transferVluchten;
    
  } 
  
  

  /** Vanaf hieronder: sorteeralgoritmen 
  */
  
  /*public static int getCO2(ArrayList<Flight[]> lijst){
      
  }*/
  
public static ArrayList<Flight[]> sortCarbonDio(String dAirport, String aAirport, String depDate) throws DBException, ParseException{
      Connection con = null;
      
      
      String D_Code = getCode(dAirport);
      String A_Code = getCode(aAirport);
      String Correct_depDate = getDateTime(depDate);
      
      ArrayList<String> hulpArray = new ArrayList<>();
      ArrayList<Flight[]> gesorteerdeFlights= new ArrayList<>();
      
      ArrayList<ArrayList<Flight[]>> alleVluchten = new  ArrayList<>();
      ArrayList<Flight[]> singleVluchten = new  ArrayList<>();
      ArrayList<Flight[]> transferVluchten = new  ArrayList<>();
      alleVluchten = getFlights(dAirport, aAirport, depDate);
      singleVluchten = getFlights(dAirport, aAirport, depDate).get(0);
      transferVluchten = getFlights(dAirport, aAirport, depDate).get(1);
      
      
      for(int i = 0; i < singleVluchten.size(); i++){
              String carbonDio = singleVluchten.get(i)[0].getCarbondio();
              float carbonD = Float.parseFloat(carbonDio);
              //ArrayList<Flight> flight = new ArrayList<>(1);
              //flight.add(singleVluchten.get(i));
              for(int o = 0; o < hulpArray.size(); o++){
                  if(carbonD < Float.parseFloat(hulpArray.get(o))){
                      hulpArray.add(o, carbonDio);
                      gesorteerdeFlights.add(o, singleVluchten.get(i));
                      break;
                  }         
          }
              if(hulpArray.contains(carbonDio) == false && gesorteerdeFlights.contains(singleVluchten.get(i)) == false){
              hulpArray.add(carbonDio);
              gesorteerdeFlights.add(singleVluchten.get(i));
              }
      }
      
      for(int i = 0; i < transferVluchten.size(); i++){
          String carbonDio1 = transferVluchten.get(i)[0].getCarbondio();
          float carbonD1 = Float.parseFloat(carbonDio1);
          String carbonDio2 = transferVluchten.get(i)[1].getCarbondio();
          float carbonD2 = Float.parseFloat(carbonDio2);
          float carbonDio = carbonD1 + carbonD2;
          ArrayList<Flight> flight = new ArrayList<>(2);
              flight.add(0, transferVluchten.get(i)[0]);
              flight.add(1, transferVluchten.get(i)[1]);
              for(int o = 0; o < hulpArray.size(); o++){
                  if(carbonDio < Float.parseFloat(hulpArray.get(o))){
                      hulpArray.add(o, Float.toString(carbonDio));
                      gesorteerdeFlights.add(o, transferVluchten.get(i));
                      break;
                  }
              }
              if(hulpArray.contains(carbonDio) == false && gesorteerdeFlights.contains(transferVluchten.get(i)) == false){
              hulpArray.add(Float.toString(carbonDio));
              gesorteerdeFlights.add(transferVluchten.get(i));
              }
      } 
        return gesorteerdeFlights;
      
  }
  
/* Vanaf hieronder vindt u de hulpmethoden voor de methode getTransferFlight. We gaan er in ons model vanuit dat er
   maar 1 transfer kan plaatsvinden.
  */

  // Via deze methode krijgt men de airportcode door de naam van de luchthaven in te geven.
  public static String getCode(String AirportName) throws DBException {
    Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      
      String sql = "SELECT AIRPORTCODE "
	+ "FROM AIRPORT "
	+ "WHERE AIRPORTNAME = '" + AirportName + "'";

        
      ResultSet rs = stmt.executeQuery(sql);
      String airportcode;
      
      if (rs.next()) {
        airportcode = rs.getString("AIRPORTCODE");
        
	
      } else {// we verwachten slechts 1 rij...
	DBConnector.closeConnection(con);
	return null;
      }
      
      DBConnector.closeConnection(con);
      return airportcode;
      
    } catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
    
  }
  
  // Via deze methode wordt de tijd van de datum gescheiden, zodat alleen de datum overblijft. + in juiste vorm voor SQL
  public static String getDateTime(String date){
      String a = Character.toString(date.charAt(0));
      String b = Character.toString(date.charAt(1));
      String c = Character.toString(date.charAt(3));
      String d = Character.toString(date.charAt(4));
      String e = Character.toString(date.charAt(6));
      String f = Character.toString(date.charAt(7));
      String g = Character.toString(date.charAt(8));
      String h = Character.toString(date.charAt(9));
      
      
      String dateTime = e+f+g+h+ "-" +c+d+ "-" +a+b;
      return dateTime;
  }
    
  // Deze methode geeft de vluchten weer van de vertrekluchthaven naar de tussenstop.
  public static ArrayList<Flight> getEersteVluchten(String D_Code, String Correct_depDate) throws DBException
  {
      Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      
      
      String sqlDepFlights = "SELECT * "
	+ "FROM FLIGHT "
	+ "WHERE D_CODE = '" + D_Code + "'" + "AND CAST(DEPDATETIME as DATE) = '" + Correct_depDate + "'";
      
      
      ResultSet rsDepFlights = stmt.executeQuery(sqlDepFlights);
      
      
      
      String flightNr1, depDateTime1, arrivalDateTime1, carbondio1, ICAO1, d_Code1, a_Code1;
      ArrayList<Flight> arrayEersteVluchten = new ArrayList<Flight>();
      while(rsDepFlights.next())
      {
        
        flightNr1 = rsDepFlights.getString("FLIGHTNR");
        depDateTime1 = rsDepFlights.getString("DEPDATETIME");
	arrivalDateTime1 = rsDepFlights.getString("ARRIVALDATETIME");
	carbondio1 = rsDepFlights.getString("CARBONDIO");
	ICAO1 = rsDepFlights.getString("ICAO");
	a_Code1 = rsDepFlights.getString("A_CODE");
        d_Code1 = rsDepFlights.getString("D_CODE");
        Flight flight = new Flight(flightNr1, depDateTime1, arrivalDateTime1, carbondio1, ICAO1, d_Code1, a_Code1);
        arrayEersteVluchten.add(flight);
        
      }
      DBConnector.closeConnection(con);
      return arrayEersteVluchten;
  }
  catch (Exception ex) {
      System.out.println("ex.getMessage");
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
    
  }
  
  // Deze methode geeft de vluchten weer van de tussenstop naar de aankomstluchthaven
  public static ArrayList<Flight> getTweedeVluchten(String A_Code, String Correct_depDate) throws DBException
  {
      Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
      
      
      String sqlArrFlights = "SELECT * "
	+ "FROM FLIGHT "
	+ "WHERE A_CODE = '" + A_Code + "' AND CAST(DEPDATETIME as DATE) = '" + Correct_depDate + "'";
      
      
        // let op de spatie na '*' en 'CUSTOMER' in voorgaande SQL
      ResultSet rsArrFlights = stmt.executeQuery(sqlArrFlights);
      
      
      
      String flightNr2, depDateTime2, arrivalDateTime2, carbondio2, ICAO2, d_Code2, a_Code2;
      ArrayList<Flight> arrayTweedeVluchten = new ArrayList<Flight>();
      while(rsArrFlights.next())
      {
        
        flightNr2 = rsArrFlights.getString("FLIGHTNR");
        depDateTime2 = rsArrFlights.getString("DEPDATETIME");
	arrivalDateTime2 = rsArrFlights.getString("ARRIVALDATETIME");
	carbondio2 = rsArrFlights.getString("CARBONDIO");
	ICAO2 = rsArrFlights.getString("ICAO");
	a_Code2 = rsArrFlights.getString("A_CODE");
        d_Code2 = rsArrFlights.getString("D_CODE");
        Flight flight = new Flight(flightNr2, depDateTime2, arrivalDateTime2, carbondio2, ICAO2, d_Code2, a_Code2);
        arrayTweedeVluchten.add(flight);
      }
      DBConnector.closeConnection(con);
      return arrayTweedeVluchten;
  }
  catch (Exception ex) {
      System.out.println("ex.getMessage");
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
    
  }
  
  /* In deze methode controleren we of de reiziger voldoende tijd heeft om over te stappen in de 
     tussenstop. We nemen een marge van 30 minuten.
  */
  public static ArrayList<Flight[]> tijdControle(ArrayList<Flight[]> arrayVluchten) throws ParseException{
      ArrayList<Flight[]> HaalbareFlights = new ArrayList<>();
      for(int i = 0; i < arrayVluchten.size(); i += 1){
              SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
              Date date1 = format.parse(arrayVluchten.get(i)[0].getArrivalDateTime().substring(10));
              Date date2 = format.parse(arrayVluchten.get(i)[1].getDepDateTime().substring(10));
              long difference = date2.getTime() - date1.getTime();
              //de output van difference is in milliseconden, 1 800 000 milliseconden zijn 30 minuten,
              //dit nemen we als minimumtijd die je nodig hebt om over te stappen.
              if(difference >= 1800000){
                  HaalbareFlights.add(arrayVluchten.get(i));
              }
      }
      return HaalbareFlights; 
  }

  // main 
  public static void main(String args[]) throws ParseException{
      
      try {
      
          
        /* String test = getCode("brussels");
            System.out.println(test);  */ 
        
        /* Flight flight = getTransferFlight("london", "brussels", "22/11/2019");
          System.out.println(flight.getArrivalDateTime()); 
        ArrayList<Flight> test2 = hulpMethode2(getCode("new york"));
        System.out.println(test2.get(0).getA_Code()); */
        
        ArrayList<Flight[]> test = sortCarbonDio("new york", "london", "23/11/2019");
          System.out.println(test.get(0).length);
          System.out.println(test.get(1).length);
          System.out.println(test.get(2).length);
          System.out.println(test.get(3).length);
          
          
       /* ArrayList<Flight[]> test = getTransferFlights("brussels", "london", "23/11/2019");
          System.out.println("optie 1");
          System.out.println("vlucht 1");
        System.out.println(test.get(0)[0].getD_Code());
        System.out.println(test.get(0)[0].getA_Code()); 
        System.out.println(test.get(0)[0].getICAO()); 
          System.out.println("vlucht2");
        System.out.println(test.get(0)[1].getD_Code());
        System.out.println(test.get(0)[1].getA_Code()); 
        System.out.println(test.get(0)[1].getICAO()); */
        /* System.out.println("optie2");
            System.out.println("vlucht1");
        System.out.println(test.get(1)[0].getD_Code()); 
        System.out.println(test.get(1)[0].getA_Code()); 
        System.out.println(test.get(1)[0].getICAO()); 
            System.out.println("vlucht2");
        System.out.println(test.get(1)[1].getD_Code()); 
        System.out.println(test.get(1)[1].getA_Code()); 
        System.out.println(test.get(1)[1].getICAO()); */
        
    
    } 
    catch (DBException e) {
       System.out.println("e.getMessage");
        // Logger.getLogger(DBBooking.class.getName()).log(Level.SEVERE, null, ex);
     } 
      
      
  }
}
