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

public static ArrayList<Flight[]> sortPrice(String dAirport, String aAirport, String depDate) throws DBException, ParseException{
    Connection con = null;
    String D_Code = getCode(dAirport);
    String A_Code = getCode(aAirport);
    String Correct_depDate = getDateTime(depDate);
   
    ArrayList<String> hulpArray = new ArrayList<>();
    ArrayList<Flight[]> singleVluchten = new ArrayList<>();
    ArrayList<Flight[]> transferVluchten = new ArrayList<>();
    ArrayList<Flight[]> gesorteerdeVluchten = new ArrayList<>();
    
    singleVluchten = getFlights(dAirport, aAirport, depDate).get(0);
    transferVluchten = getFlights(dAirport, aAirport, depDate).get(1);
    try {
    con = DBConnector.getConnection();
    Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      
    for(int i = 0; i < singleVluchten.size(); i++)
    {
        // price eruithalen voor deze vlucht
      
         String sql = "SELECT PRICE "
	+ "FROM PRICE "
	+ "WHERE FLIGHTNR = '" + singleVluchten.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'ECONOMY' " ;
         
        ResultSet rs = stmt.executeQuery(sql);
        String price;
        while(rs.next()){
        price = rs.getString("PRICE");
        
        for(int e = 0; e < hulpArray.size(); e++)
        {
            if(Double.parseDouble(price) < Double.parseDouble(hulpArray.get(e)))
            {
                hulpArray.add(e,price);
                gesorteerdeVluchten.add(e, singleVluchten.get(i));
                break;
            } 
        } 
        if(hulpArray.contains(price) == false && gesorteerdeVluchten.contains(singleVluchten.get(i)) == false){
              hulpArray.add(price);
              gesorteerdeVluchten.add(singleVluchten.get(i));
              }
        }
    }

    
    for(int o = 0; o < transferVluchten.size(); o++)
    {
      // price eruithalen voor deze vlucht
      
         String sql1 = "SELECT PRICE "
	+ "FROM PRICE "
	+ "WHERE FLIGHTNR = '" + transferVluchten.get(o)[0].getFlightNr() + "'"
        + "OR FLIGHTNR = '" + transferVluchten.get(o)[1].getFlightNr() + "'"
        + "AND FLIGHTCLASS = 'ECONOMY'" ;
  
        ResultSet rs = stmt.executeQuery(sql1);
        
        String[] priceVlucht = new String[2];
        
        for(int s = 0; s < 2; s++){
        rs.next();   
        priceVlucht[s] = rs.getString("PRICE");
        
        }
       
        double volledigePrice = Double.parseDouble(priceVlucht[0]) + Double.parseDouble(priceVlucht[1]);
      
        
       for(int a = 0; a < hulpArray.size(); a++)
       {
           if(volledigePrice < Double.parseDouble(hulpArray.get(a)))
            {
                hulpArray.add(a,Double.toString(volledigePrice));
                gesorteerdeVluchten.add(a, transferVluchten.get(o));
                break;
            }
            if(hulpArray.contains(Double.toString(volledigePrice)) == false && gesorteerdeVluchten.contains(transferVluchten.get(o)) == false){
              hulpArray.add(Double.toString(volledigePrice));
              gesorteerdeVluchten.add(transferVluchten.get(o));
              }
       }
        
    }
    DBConnector.closeConnection(con);
    
    } 
    
    catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }

  return gesorteerdeVluchten;   
}

/* In deze methode ordenen we alle vluchten van korste naar langste duration
  */

 public static ArrayList<Flight[]> sortDuration(String dAirport, String aAirport, String depDate) throws DBException, ParseException{
      Connection con = null;
      
      
      String D_Code = getCode(dAirport);
      String A_Code = getCode(aAirport);
      String Correct_depDate = getDateTime(depDate);
      
      ArrayList<Double> hulpArray = new ArrayList<>();
      ArrayList<Flight[]> gesorteerdeFlights= new ArrayList<>();
      
      ArrayList<ArrayList<Flight[]>> alleVluchten = new  ArrayList<>();
      ArrayList<Flight[]> singleVluchten = new  ArrayList<>();
      ArrayList<Flight[]> transferVluchten = new  ArrayList<>();
      alleVluchten = getFlights(dAirport, aAirport, depDate);
      singleVluchten = getFlights(dAirport, aAirport, depDate).get(0);
      transferVluchten = getFlights(dAirport, aAirport, depDate).get(1);
      
      
      for(int i = 0; i < singleVluchten.size(); i++){
              String departureTime = singleVluchten.get(i)[0].getDepDateTime();
              String arrivalTime = singleVluchten.get(i)[0].getArrivalDateTime();
              Double duration = DBFlight.timeDifference(departureTime, arrivalTime);
              //System.out.println(duration/3600000);              
              //ArrayList<Flight> flight = new ArrayList<>(1);
              //flight.add(singleVluchten.get(i));
              for(int o = 0; o < hulpArray.size(); o++){
                  if(duration < hulpArray.get(o)){
                      hulpArray.add(o, duration);
                      gesorteerdeFlights.add(o, singleVluchten.get(i));
                      break;
                  }         
          }
              if(hulpArray.contains(duration) == false && gesorteerdeFlights.contains(singleVluchten.get(i)) == false){
              hulpArray.add(duration);
              gesorteerdeFlights.add(singleVluchten.get(i));
              }
      }
      
      for(int i = 0; i < transferVluchten.size(); i++){
          String departureTimeVlucht1 = transferVluchten.get(i)[0].getDepDateTime();
          String arrivalTimeVlucht1 = transferVluchten.get(i)[0].getArrivalDateTime();
          String departureTimeVlucht2 = transferVluchten.get(i)[1].getDepDateTime();
          String arrivalTimeVlucht2 = transferVluchten.get(i)[1].getArrivalDateTime();
          
          Double duration1 = DBFlight.timeDifference(departureTimeVlucht1, arrivalTimeVlucht1);
          Double duration2 = DBFlight.timeDifference(departureTimeVlucht2, arrivalTimeVlucht2);
          Double totalDuration = duration1 + duration2;
          //System.out.println(totalDuration/3600000);
          
              for(int o = 0; o < hulpArray.size(); o++){
                  if(totalDuration < hulpArray.get(o)){
                      hulpArray.add(o,totalDuration);
                      gesorteerdeFlights.add(o, transferVluchten.get(i));
                      break;
                  }
              }
              if(hulpArray.contains(totalDuration) == false && gesorteerdeFlights.contains(transferVluchten.get(i)) == false){
              hulpArray.add(totalDuration);
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
  
  public static ArrayList<ArrayList<Flight>> getFlight(String dAirport, String aAirport, String depDate) throws DBException
  {
      Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String D_Code = getCode(dAirport);
      String A_Code = getCode(aAirport);
      String Correct_depDate = getDateTime(depDate);
      ArrayList<String> d_Codes = new ArrayList<>();
      d_Codes.add(D_Code);
      ArrayList<String> d_Codes2 = new ArrayList<>();
      ArrayList<Flight> singleFlights = new ArrayList<>();
      ArrayList<Flight> doubleFlights = new ArrayList<>();
      ArrayList<Flight> tripleFlights = new ArrayList<>();
      ArrayList<Flight> overigeFlights = new ArrayList<>();
      ArrayList<Flight> overigeFlights2 = new ArrayList<>();
      ArrayList<Flight[]> vluchten1 = new ArrayList<>();
      ArrayList<Flight[]> vluchten2 = new ArrayList<>();
      ArrayList<Flight[]> vluchten3 = new ArrayList<>();
      ArrayList<ArrayList<Flight>> vluchten = new ArrayList<>();
      ArrayList<Flight> realDoubleFlights = new ArrayList<>();
      ArrayList<Flight> realTripleFlights = new ArrayList<>();
      for(int i = 0; i < 3; i++){
          for(int p = 0; p < d_Codes.size(); p++){
            String sqlFlights = "SELECT * "
              + "FROM FLIGHT "
              + "WHERE D_CODE = '" + d_Codes.get(p) + "'" + "AND CAST(DEPDATETIME as DATE) = '" + Correct_depDate + "'";
            
            d_Codes.remove(p);
            ResultSet rsFlights = stmt.executeQuery(sqlFlights);
            String flightNr, depDateTime, arrivalDateTime, carbondio, ICAO, d_Code, a_Code;
            
            while(rsFlights.next())
            {
                flightNr = rsFlights.getString("FLIGHTNR");
                depDateTime = rsFlights.getString("DEPDATETIME");
                arrivalDateTime = rsFlights.getString("ARRIVALDATETIME");
                carbondio = rsFlights.getString("CARBONDIO");
                ICAO = rsFlights.getString("ICAO");
                d_Code = rsFlights.getString("D_CODE");
                a_Code = rsFlights.getString("A_CODE");
                Flight flight = new Flight(flightNr, depDateTime, arrivalDateTime, carbondio, ICAO, d_Code, a_Code);

                if(i == 0 && a_Code.equalsIgnoreCase(A_Code)){
                    singleFlights.add(flight); 
                }
                else if(i == 0){
                    overigeFlights.add(flight);
                    d_Codes.add(p, a_Code);
                }
                else if(i == 1 && a_Code.equalsIgnoreCase(A_Code)){
                    for(int r = 0; r < overigeFlights.size(); r++){
                        if(overigeFlights.get(r).getA_Code().equalsIgnoreCase(d_Code)){
                             doubleFlights.add(overigeFlights.get(r));
                             doubleFlights.add(flight);
                             realDoubleFlights = tijdControle(doubleFlights);
                        }
                    }
                }
                else if(i == 1){
                    overigeFlights2.add(flight);
                    d_Codes.add(p, a_Code);
                }
                else if(i == 2 && a_Code.equalsIgnoreCase(A_Code)){
                    for(int r = 0; r < overigeFlights.size(); r++){
                        for(int l = 0; l < overigeFlights2.size(); l++){
                        if(overigeFlights.get(r).getA_Code().equalsIgnoreCase(overigeFlights2.get(l).getD_Code()) && overigeFlights2.get(l).getA_Code().equalsIgnoreCase(d_Code)){
                            tripleFlights.add(overigeFlights.get(r));
                            tripleFlights.add(overigeFlights2.get(l));
                            tripleFlights.add(flight);
                            //realTripleFlights = tijdControle(tripleFlights);
                        }
                    }
                    }
                }
            }
          }
      }
      DBConnector.closeConnection(con);
      vluchten.add(singleFlights);
      vluchten.add(realDoubleFlights);
      vluchten.add(tripleFlights);
      return vluchten;
    }
  catch (Exception ex) {
      System.out.println("ex.getMessage");
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
    }
   
  /* In deze methode berekenen we het verschil in milliseconden tussen twee data; We moeten het formaat nog wel omzetten
  om zo de SimpleDateFormat klasse te kunnen gebruiken
  */
  
  public static double timeDifference(String newStart, String newEinde) throws ParseException
  {
      String start = newStart;
      String einde = newEinde;
      
      String a = Character.toString(start.charAt(0));
      String b = Character.toString(start.charAt(1));
      String c = Character.toString(start.charAt(2));
      String d = Character.toString(start.charAt(3));
      String e = Character.toString(start.charAt(5));
      String f = Character.toString(start.charAt(6));
      String g = Character.toString(start.charAt(8));
      String h = Character.toString(start.charAt(9));
      String i = Character.toString(start.charAt(11));
      String j = Character.toString(start.charAt(12));
      String k = Character.toString(start.charAt(14));
      String l = Character.toString(start.charAt(15));
      String m = Character.toString(start.charAt(17));  
      String n = Character.toString(start.charAt(18));
       
      String a1 = Character.toString(einde.charAt(0));
      String b1 = Character.toString(einde.charAt(1));
      String c1 = Character.toString(einde.charAt(2));
      String d1 = Character.toString(einde.charAt(3));
      String e1 = Character.toString(einde.charAt(5));
      String f1 = Character.toString(einde.charAt(6));
      String g1 = Character.toString(einde.charAt(8));
      String h1 = Character.toString(einde.charAt(9));
      String i1 = Character.toString(einde.charAt(11));
      String j1 = Character.toString(einde.charAt(12));
      String k1 = Character.toString(einde.charAt(14));
      String l1 = Character.toString(einde.charAt(15));
      String m1 = Character.toString(einde.charAt(17));  
      String n1 = Character.toString(einde.charAt(18));
      
      
      String juisteStart = e+f+ "/" +g+h+ "/" +a+b+c+d+ " " + i+j + ":" +k+l +":" + m+n;
      String juisteEinde = e1+f1+ "/" +g1+h1+ "/" +a1+b1+c1+d1+ " " + i1+j1 + ":" +k1+l1 +":" + m1+n1;
      
      /* String dateStart = "01/14/2012 09:29:58";
        String dateStop = "01/15/2012 10:31:48";*/

      SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

      Date dt1 = null;
      Date dt2 = null;
      
      dt1 = format.parse(juisteStart);
      dt2 = format.parse(juisteEinde);

      //in milliseconds
      double difference = dt2.getTime() - dt1.getTime();
      return difference;
       } 
  
 
  
  /* Deze methode geeft een mooie output weer voor een ArrayList van Flight[] */ 
      public static String toString1(ArrayList<Flight[]> flights) {
          Connection con = null;
          String output = "";
          try {
            con = DBConnector.getConnection();
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
          
                for(int i = 0; i < flights.size(); i++){
                    if(flights.get(i).length == 1){
                            String priceEco = "";
                            String sql1 = "SELECT PRICE "
                            + "FROM PRICE "
                            + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'ECONOMY' " ;

                                ResultSet rs1 = stmt.executeQuery(sql1);
                                while(rs1.next()){
                                    priceEco = rs1.getString("PRICE");
                                    }
                                if(priceEco.equals("")){
                                    priceEco = "Geen Economy Class beschikbaar voor deze vlucht";
                                    } 
                                
                                
                            String priceBuss = "";
                            String sql2 = "SELECT PRICE "
                            + "FROM PRICE "
                            + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'BUSINESS' " ;

                                ResultSet rs2 = stmt.executeQuery(sql2);
                                while(rs2.next()){
                                    priceBuss = rs2.getString("PRICE");
                                    }
                                if(priceBuss.equals("")){
                                    priceBuss = "Geen Business Class beschikbaar voor deze vlucht";
                                    }
                            String priceFirst = "";
                            String sql3 = "SELECT PRICE "
                            + "FROM PRICE "
                            + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'FIRST' " ;

                                ResultSet rs3 = stmt.executeQuery(sql3);
                                while(rs3.next()){
                                    priceFirst = rs3.getString("PRICE");
                                    }
                                if(priceFirst.equals("")){
                                    priceFirst = "Geen First Class beschikbaar voor deze vlucht";
                                    }
                             
                                
                        output = output.concat("Single Flight: \n"
                                + "    flightNr= " + flights.get(i)[0].getFlightNr() + "\n" 
                                + "    depDateTime= " + flights.get(i)[0].getDepDateTime() + "\n"  
                                + "    arrivalDateTime= " + flights.get(i)[0].getArrivalDateTime() + "\n"  
                                + "    carbondio= " + flights.get(i)[0].getCarbondio() + "\n"  
                                + "    d_Code= " + flights.get(i)[0].getD_Code() + "\n"  
                                + "    a_Code= " + flights.get(i)[0].getA_Code() + "\n"  
                                + "    ICAO= " + flights.get(i)[0].getICAO()+ "\n"
                                + " " + "\n" 
                                + "    PRICE Economy= " + priceEco+ "\n"
                                + "    PRICE Business= " + priceBuss+ "\n"
                                + "    PRICE First Class= " + priceFirst+ "\n"
                                + " " + "\n" 
                                + "------------------------------------ \n"
                                + " " + "\n"); 
                    }
                    if(flights.get(i).length == 2){
                                 boolean check1 = true;
                                 boolean check2 = true; 
                                 boolean check3 = true; 
                                 String priceEco = "";
                                    String sql1 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'ECONOMY' " ;

                                        ResultSet rs1 = stmt.executeQuery(sql1);
                                        while(rs1.next()){
                                            priceEco = priceEco.concat(rs1.getString("PRICE"));
                                            }
                                        if(priceEco.equals("")){
                                            priceEco= "Geen Economy Class beschikbaar voor deze vlucht";
                                            check1 = false;
                                            }
                                    String priceBuss = "";
                                    String sql2 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'BUSINESS' " ;

                                        ResultSet rs2 = stmt.executeQuery(sql2);
                                        while(rs2.next()){
                                            priceBuss = priceBuss.concat(rs2.getString("PRICE"));
                                            }
                                        if(priceBuss.equals("")){
                                            priceBuss = "Geen Business Class beschikbaar voor deze vlucht";
                                            check2 = false;
                                            }
                                    String priceFirst = "";
                                    String sql3 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'FIRST' " ;

                                        ResultSet rs3 = stmt.executeQuery(sql3);
                                        while(rs3.next()){
                                            priceFirst = priceFirst.concat(rs3.getString("PRICE"));
                                            }
                                        if(priceFirst.equals("")){
                                            priceFirst = "Geen First Class beschikbaar voor deze vlucht";
                                            check3 = false;
                                            }
                                    String priceEco1 = "";
                                    String sql11 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[1].getFlightNr() + "' AND FLIGHTCLASS = 'ECONOMY' " ;

                                        ResultSet rs11 = stmt.executeQuery(sql11);
                                        while(rs11.next()){
                                            priceEco1 = priceEco1.concat(rs11.getString("PRICE"));
                                            }
                                        if(priceEco1.equals("")){
                                            priceEco1 = "Geen Economy Class beschikbaar voor deze vlucht";
                                            check1 = false;
                                            }
                                    String priceBuss1 = "";
                                    String sql21 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[1].getFlightNr() + "' AND FLIGHTCLASS = 'BUSINESS' " ;

                                        ResultSet rs21 = stmt.executeQuery(sql21);
                                        while(rs21.next()){
                                            priceBuss1 = priceBuss1.concat(rs21.getString("PRICE"));
                                            }
                                        if(priceBuss1.equals("")){
                                            priceBuss1 = "Geen Business Class beschikbaar voor deze vlucht";
                                            check2 = false;
                                            }
                                    String priceFirst1 = "";
                                    String sql31 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[1].getFlightNr() + "' AND FLIGHTCLASS = 'FIRST' " ;

                                        ResultSet rs31 = stmt.executeQuery(sql31);
                                        while(rs31.next()){
                                            priceFirst1 = priceFirst1.concat(rs31.getString("PRICE"));
                                            }
                                        if(priceFirst1.equals("")){
                                            priceFirst1 = "Geen First Class beschikbaar voor deze vlucht";
                                            check3 = false;
                                            }
                                        
                                        
                        String totalEco;
                        String totalBuss;
                        String totalFirst;
                        
                        if(check1 == false && check2 == false && check3 == false){ 
                            totalEco = "Niet beschikbaar";
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = "Niet beschikbaar"; 
                        }  
                        if(!check1 && !check2 ){
                            totalEco = "Niet beschikbaar";
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1));
                        }
                        if(!check1 && !check3 ){
                            totalEco = "Niet beschikbaar";
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1)); 
                            totalFirst = "Niet beschikbaar";
                        }
                        if(!check2 && !check3 ){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1)); 
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = "Niet beschikbaar";
                        }
                        if(!check1){
                            totalEco = "Niet beschikbaar"; 
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1)); 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1)); 
                        }
                        if(!check2){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1)); ; 
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1)); 
                        }
                        if(!check3){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1)); ; 
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1)); ; 
                            totalFirst = "Niet beschikbaar"; 
                        }
                        else{
                        totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1)); 
                        totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1)); 
                        totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1)); 
                        }     
                        
                        
                                 
                        output =  output.concat("Transfer flight: \n"
                                + "  Flight 1: \n"
                                + "    flightNr= " + flights.get(i)[0].getFlightNr() + "\n" 
                                + "    depDateTime= " + flights.get(i)[0].getDepDateTime() + "\n"  
                                + "    arrivalDateTime= " + flights.get(i)[0].getArrivalDateTime() + "\n"  
                                + "    carbondio= " + flights.get(i)[0].getCarbondio() + "\n"  
                                + "    d_Code= " + flights.get(i)[0].getD_Code() + "\n"  
                                + "    a_Code= " + flights.get(i)[0].getA_Code() + "\n"  
                                + "    ICAO= " + flights.get(i)[0].getICAO()+ "\n"
                                + " " + "\n"
                                + "    PRICE Economy= " + priceEco+ "\n"
                                + "    PRICE Business= " + priceBuss+ "\n"
                                + "    PRICE First Class= " + priceFirst+ "\n"        
                                + " " + "\n"
                                + "  Flight 2: \n"
                                + "    flightNr= " + flights.get(i)[1].getFlightNr() + "\n" 
                                + "    depDateTime= " + flights.get(i)[1].getDepDateTime() + "\n"  
                                + "    arrivalDateTime= " + flights.get(i)[1].getArrivalDateTime() + "\n"  
                                + "    carbondio= " + flights.get(i)[1].getCarbondio() + "\n"  
                                + "    d_Code= " + flights.get(i)[1].getD_Code() + "\n"  
                                + "    a_Code= " + flights.get(i)[1].getA_Code() + "\n"  
                                + "    ICAO= " + flights.get(i)[1].getICAO()+ "\n"
                                + " " + "\n"
                                + "    PRICE Economy= " + priceEco1+ "\n"
                                + "    PRICE Business= " + priceBuss1+ "\n"
                                + "    PRICE First Class= " + priceFirst1+ "\n"
                                + " " + "\n"
                                + "  Total price: \n "
                                + "   PRICE Economy= " + totalEco+ "\n"
                                + "    PRICE Business= " + totalBuss+ "\n"
                                + "    PRICE First Class= " + totalFirst+ "\n"
                                + " " + "\n"
                                + "------------------------------------ \n"
                                + " " + "\n");
                    }
                }
        DBConnector.closeConnection(con); 
        return output; 
        
    }   
    catch (Exception ex) {
      ex.printStackTrace();
      DBConnector.closeConnection(con);
      return ""; 
    }
}
    
    public static String toString2(ArrayList<ArrayList<Flight[]>> totalFlights) {
        String output = "";
        for(int i = 0; i < totalFlights.size(); i++){
            output = output.concat(toString1(totalFlights.get(i)));
        }
        return output;
    }  
      
 
  // main 
  public static void main(String args[]) throws ParseException{
      
      try {
        ArrayList<ArrayList<Flight>> test = getFlight("brussels", "london", "23/11/2019");
          System.out.println(test);
    } 
    catch (DBException e) {
       System.out.println("e.getMessage");
        // Logger.getLogger(DBBooking.class.getName()).log(Level.SEVERE, null, ex);
     } 
     
      
  }

}

