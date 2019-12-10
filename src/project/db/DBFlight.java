/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
   
  
    /* Deze methode zoekt alle vluchten van dAirport naar aAirport op een bepaalde dag
    Je geeft hierbij ook de max aantalTransfers in. De methode geeft alle vluchten weer tot en met 3 transfers, dit is het aantal transfers 
    dat ons realistisch en acceptabel leek
    */
    
   public static ArrayList<ArrayList<Flight[]>> getFlight(String dAirport, String aAirport, String depDate) throws DBException, ParseException, SQLException
  {
      Connection con = null;
    try {
      con = DBConnector.getConnection();
      Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
      String D_Code = getCode(dAirport);
      String A_Code = getCode(aAirport);
      String Correct_depDate = getDateTime(depDate);
      ArrayList<String> D_CODES1 = new ArrayList<>();
      ArrayList<String> D_CODES2 = new ArrayList<>();
      ArrayList<Flight[]> singleFlights = new ArrayList<>();
      ArrayList<Flight[]> doubleFlights = new ArrayList<>();
      ArrayList<Flight[]> tripleFlights = new ArrayList<>();
      ArrayList<Flight> overigeFlights = new ArrayList<>();
      ArrayList<Flight> overigeFlights2 = new ArrayList<>();
      ArrayList<Flight> overigeFlights3 = new ArrayList<>();
      ArrayList<Flight> nonduplicateFlights1 = new ArrayList<>();
      ArrayList<Flight> nonduplicateFlights2 = new ArrayList<>();
      ArrayList<ArrayList<Flight[]>> vluchten = new ArrayList<>();
      for(int i = 0; i < 3; i++){
            String sqlFlights;
            if(i == 0){
                sqlFlights = "SELECT * "
              + "FROM FLIGHT "
              + "WHERE D_CODE = '" + D_Code + "'" + "AND CAST(DEPDATETIME as DATE) = '" + Correct_depDate + "'";
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

                if(a_Code.equalsIgnoreCase(A_Code)){
                    Flight[] flights1 = new Flight[1];
                    flights1[0] = flight;
                    singleFlights.add(flights1);
                }
                else{
                    overigeFlights.add(flight);
                    
                }
            }
            }
            
            if(i == 1 && !overigeFlights.isEmpty() ){
                for(int p = 0; p < overigeFlights.size(); p++){
                    String A_CODE = overigeFlights.get(p).getA_Code();
                    if(!D_CODES1.contains(A_CODE)){
                        D_CODES1.add(A_CODE);
                    }
                }
                
                for(int p = 0; p < D_CODES1.size(); p++){
                    String D_CODE = D_CODES1.get(p);
                    
                    
                sqlFlights = "SELECT * "
                    + "FROM FLIGHT "
                    + "WHERE D_CODE = '" + D_CODE + "'" + "AND CAST(DEPDATETIME as DATE) = '" + Correct_depDate + "'";
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
           
                
                if(a_Code.equalsIgnoreCase(A_Code)){
                        
                             Flight[] flights2 = new Flight[2];
                             for(int u = 0; u < overigeFlights.size(); u++){
                                 if(overigeFlights.get(u).getA_Code().equalsIgnoreCase(flight.getD_Code())){
                                     flights2[0] = overigeFlights.get(u);
                                 }
                             }
                             flights2[1] = flight;
                             if(tijdControle2(flights2) == true){
                             doubleFlights.add(flights2);
                             }
                }
                else{
                    overigeFlights2.add(flight);
                    
                    
                }
            }
            }
      }
            
            if(i == 2 && !overigeFlights2.isEmpty()){
                
            for(int p = 0; p < overigeFlights2.size(); p++){
                    String A_CODE = overigeFlights2.get(p).getA_Code();
                    if(!D_CODES2.contains(A_CODE)){
                        D_CODES2.add(A_CODE); 
                    }
                }
                
                for(int p = 0; p < D_CODES2.size(); p++){
                    String D_CODE = D_CODES2.get(p);
                 
                sqlFlights = "SELECT * "
                    + "FROM FLIGHT "
                    + "WHERE D_CODE = '" + D_CODE + "'" + "AND CAST(DEPDATETIME as DATE) = '" + Correct_depDate + "'";
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
           
                System.out.println("------------------" + flight.getFlightNr());
              
                if(a_Code.equalsIgnoreCase(A_Code)){
                        
                             Flight[] flights3 = new Flight[3];
                             
                             for(int u = 0; u < overigeFlights.size(); u++){
                                 for(int k = 0; k < overigeFlights2.size(); k++){
                                 if(overigeFlights.get(u).getA_Code().equalsIgnoreCase(overigeFlights2.get(k).getD_Code()) && overigeFlights2.get(k).getA_Code().equalsIgnoreCase(flight.getD_Code())){
                                     flights3[0] = overigeFlights.get(u);
                                 }
                             }
                             }
           
                             for(int k = 0; k < overigeFlights.size(); k++){
                                 for(int u = 0; u < overigeFlights2.size(); u++){
                                 if(overigeFlights2.get(u).getA_Code().equalsIgnoreCase(d_Code) && overigeFlights.get(k).getA_Code().equalsIgnoreCase(overigeFlights2.get(u).getD_Code()) ){
                                     flights3[1] = overigeFlights2.get(u);
                                 } 
                             }
                             }
                             
                             flights3[2] = flight;
                             System.out.println(flights3[0].getFlightNr());
                             System.out.println(flights3[1].getFlightNr());
                             System.out.println(flights3[2].getFlightNr());
                             System.out.println("----------------------------------------------");
                             
                             if(tijdControle3(flights3) == true){
                             tripleFlights.add(flights3);  
                             }
                }
                else{
                    overigeFlights3.add(flight);
                    
                }
            }
            }
      }
                /* for(int p = 0; p < overigeFlights2.size(); p++){
                String A_CODE = overigeFlights2.get(p).getA_Code();
                sqlFlights = "SELECT * "
              + "FROM FLIGHT "
              + "WHERE D_CODE = '" + overigeFlights2.get(p).getA_Code() + "'" + "AND CAST(DEPDATETIME as DATE) = '" + Correct_depDate + "'";
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

                if(a_Code.equalsIgnoreCase(A_Code)){
                     for(int r = 0; r < overigeFlights.size(); r++){
                        if(overigeFlights.get(r).getA_Code().equalsIgnoreCase(overigeFlights2.get(p).getD_Code()) && overigeFlights2.get(p).getA_Code().equalsIgnoreCase(d_Code) && overigeFlights.get(r).getD_Code().equalsIgnoreCase(D_Code)){
                            Flight[] flights3 = new Flight[3];
                            flights3[0] = overigeFlights.get(r);
                            flights3[1] = overigeFlights2.get(p);
                            flights3[2] = flight;
                            if(tijdControle3(flights3) == true){
                            tripleFlights.add(flights3);
                            }
                    }
                }
                }
            }
            }
          
      }
      } */ }
      
      DBConnector.closeConnection(con);
      vluchten.add(singleFlights);
      vluchten.add(doubleFlights);
      vluchten.add(tripleFlights);
      return vluchten;
    }
      
  catch (Exception ex) {
      System.out.println(ex.getMessage());
      DBConnector.closeConnection(con);
      throw new DBException(ex);
    }
    }
  
 /*
    Hier staan alle hulpmethoden weergegeven
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
  
  /* In deze methode controleren we of de reiziger voldoende tijd heeft om over te stappen in de 
     tussenstop. We nemen een marge van 30 minuten.
  */
  
  
  public static boolean tijdControle2(Flight[] arrayVluchten) throws ParseException{
      boolean possible = false;
      
              SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
              Date date1 = format.parse(arrayVluchten[0].getArrivalDateTime().substring(10));
              Date date2 = format.parse(arrayVluchten[1].getDepDateTime().substring(10));
              long difference = date2.getTime() - date1.getTime();
              System.out.println(difference);
              
              //de output van difference is in milliseconden, 1 800 000 milliseconden zijn 30 minuten,
              //dit nemen we als minimumtijd die je nodig hebt om over te stappen.
              if(difference >= 1800000){
                  possible = true;
              }
      
      return possible; 
  }
  
  public static boolean tijdControle3(Flight[] arrayVluchten) throws ParseException{
      boolean possible = false;
      //for(int i = 0; i < arrayVluchten.length; i++){
              SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
              Date date1 = format.parse(arrayVluchten[0].getArrivalDateTime().substring(10));
              Date date2 = format.parse(arrayVluchten[1].getDepDateTime().substring(10));
              long difference1 = date2.getTime() - date1.getTime();
              
              //de output van difference is in milliseconden, 1 800 000 milliseconden zijn 30 minuten,
              //dit nemen we als minimumtijd die je nodig hebt om over te stappen.
           
              Date date3 = format.parse(arrayVluchten[2].getDepDateTime().substring(10));
              Date date4 = format.parse(arrayVluchten[1].getArrivalDateTime().substring(10));
              long difference2 = date3.getTime() - date4.getTime();
              if(difference1 >= 1800000 && difference2 >= 1800000){
                  possible = true;
              }
        
        return possible;
  }  
   
  public static boolean tijdControle4(Flight[] arrayVluchten) throws ParseException{
      boolean possible = false;
      //for(int i = 0; i < arrayVluchten.length; i++){
              SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
              Date date1 = format.parse(arrayVluchten[0].getArrivalDateTime().substring(10));
              Date date2 = format.parse(arrayVluchten[1].getDepDateTime().substring(10));
              long difference1 = date2.getTime() - date1.getTime();
              
              //de output van difference is in milliseconden, 1 800 000 milliseconden zijn 30 minuten,
              //dit nemen we als minimumtijd die je nodig hebt om over te stappen.
           
              Date date3 = format.parse(arrayVluchten[2].getDepDateTime().substring(10));
              long difference2 = date3.getTime() - date2.getTime();
              
              Date date4 = format.parse(arrayVluchten[2].getDepDateTime().substring(10));
              long difference3 = date4.getTime() - date3.getTime();
              
              if(difference1 >= 1800000 && difference2 >= 1800000 && difference3 >= 1800000){
                  possible = true;
              }
              
        return possible;
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

// In deze methode ordenen we alle vluchten van korste naar langste duration
  
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
  
  
  //In deze methode berekenen we het verschil in milliseconden tussen twee data; We moeten het formaat nog wel omzetten
  // zo de SimpleDateFormat klasse te kunnen gebruiken
  
  
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
  
 
  
  // Deze methode geeft een mooie output weer voor een ArrayList van Flight[]  
      public static String toString1(ArrayList<Flight[]> flights){
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
                        
                        if(check1 == true && check2 == true && check3 == true){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1)); 
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1)); 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1)); 
                            
                        }  
                        if(!check1 && !check2 && check3 == true){
                            totalEco = "Niet beschikbaar";
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1));
                        }
                        if(!check1 && !check3 && check2 == true){
                            totalEco = "Niet beschikbaar";
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1)); 
                            totalFirst = "Niet beschikbaar";
                        }
                        if(!check2 && !check3 && check1 == true){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1)); 
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = "Niet beschikbaar";
                        }
                        if(!check1 && check2 == true && check3 == true){
                            totalEco = "Niet beschikbaar"; 
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1)); 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1)); 
                        }
                        if(!check2 && check1 == true && check3 == true){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1)); ; 
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1)); 
                        }
                        if(!check3 && check1 == true && check2 == true){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1)); ; 
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1)); ; 
                            totalFirst = "Niet beschikbaar"; 
                        }
                        else{
                            totalEco = "Niet beschikbaar";
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = "Niet beschikbaar"; 
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
                    if(flights.get(i).length == 3){
                                 boolean check1 = true;
                                 boolean check2 = true; 
                                 boolean check3 = true; 
                                 String priceEco = "";
                                    String sql11 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'ECONOMY' " ;

                                        ResultSet rs11 = stmt.executeQuery(sql11);
                                        while(rs11.next()){
                                            priceEco = priceEco.concat(rs11.getString("PRICE"));
                                            }
                                        if(priceEco.equals("")){
                                            priceEco= "Geen Economy Class beschikbaar voor deze vlucht";
                                            check1 = false;
                                            }
                                    String priceBuss = "";
                                    String sql21 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'BUSINESS' " ;

                                        ResultSet rs21 = stmt.executeQuery(sql21);
                                        while(rs21.next()){
                                            priceBuss = priceBuss.concat(rs21.getString("PRICE"));
                                            }
                                        if(priceBuss.equals("")){
                                            priceBuss = "Geen Business Class beschikbaar voor deze vlucht";
                                            check2 = false;
                                            }
                                    String priceFirst = "";
                                    String sql31 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'FIRST' " ;

                                        ResultSet rs31 = stmt.executeQuery(sql31);
                                        while(rs31.next()){
                                            priceFirst = priceFirst.concat(rs31.getString("PRICE"));
                                            }
                                        if(priceFirst.equals("")){
                                            priceFirst = "Geen First Class beschikbaar voor deze vlucht";
                                            check3 = false;
                                            }
                                    String priceEco1 = "";
                                    String sql12 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[1].getFlightNr() + "' AND FLIGHTCLASS = 'ECONOMY' " ;

                                        ResultSet rs12 = stmt.executeQuery(sql12);
                                        while(rs12.next()){
                                            priceEco1 = priceEco1.concat(rs12.getString("PRICE"));
                                            }
                                        if(priceEco1.equals("")){
                                            priceEco1 = "Geen Economy Class beschikbaar voor deze vlucht";
                                            check1 = false;
                                            }
                                    String priceBuss1 = "";
                                    String sql22 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[1].getFlightNr() + "' AND FLIGHTCLASS = 'BUSINESS' " ;

                                        ResultSet rs22 = stmt.executeQuery(sql22);
                                        while(rs22.next()){
                                            priceBuss1 = priceBuss1.concat(rs22.getString("PRICE"));
                                            }
                                        if(priceBuss1.equals("")){
                                            priceBuss1 = "Geen Business Class beschikbaar voor deze vlucht";
                                            check2 = false;
                                            }
                                    String priceFirst1 = "";
                                    String sql32 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[1].getFlightNr() + "' AND FLIGHTCLASS = 'FIRST' " ;

                                        ResultSet rs32 = stmt.executeQuery(sql32);
                                        while(rs32.next()){
                                            priceFirst1 = priceFirst1.concat(rs32.getString("PRICE"));
                                            }
                                        if(priceFirst1.equals("")){
                                            priceFirst1 = "Geen First Class beschikbaar voor deze vlucht";
                                            check3 = false;
                                            }
                                        
                                    String priceEco2 = "";
                                    String sql13 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[2].getFlightNr() + "' AND FLIGHTCLASS = 'ECONOMY' " ;

                                        ResultSet rs13 = stmt.executeQuery(sql13);
                                        while(rs13.next()){
                                            priceEco2 = priceEco2.concat(rs13.getString("PRICE"));
                                            }
                                        if(priceEco2.equals("")){
                                            priceEco2 = "Geen Economy Class beschikbaar voor deze vlucht";
                                            check1 = false;
                                            }
                                    String priceBuss2 = "";
                                    String sql23 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[2].getFlightNr() + "' AND FLIGHTCLASS = 'BUSINESS' " ;

                                        ResultSet rs23 = stmt.executeQuery(sql23);
                                        while(rs23.next()){
                                            priceBuss2 = priceBuss2.concat(rs23.getString("PRICE"));                                           
                                            }
                                        if(priceBuss2.equals("")){
                                            priceBuss2 = "Geen Business Class beschikbaar voor deze vlucht";
                                            check2 = false;
                                            }
                                    String priceFirst2 = "";
                                    String sql33 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[2].getFlightNr() + "' AND FLIGHTCLASS = 'FIRST' " ;

                                        ResultSet rs33 = stmt.executeQuery(sql33);
                                        while(rs33.next()){
                                            priceFirst2 = priceFirst2.concat(rs33.getString("PRICE"));
                                            }
                                        if(priceFirst2.equals("")){
                                            priceFirst2 = "Geen First Class beschikbaar voor deze vlucht";
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
                       else if(!check1 && !check2){
                            totalEco = "Niet beschikbaar";
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1) + Float.parseFloat(priceFirst2));
                        }
                        else if(!check1 && !check3){
                            totalEco = "Niet beschikbaar";
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1) + Float.parseFloat(priceBuss2)); 
                            totalFirst = "Niet beschikbaar";
                        }
                        else if(!check2 && !check3){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1) + Float.parseFloat(priceEco2)); 
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = "Niet beschikbaar";
                        }
                        else if(!check1){
                            totalEco = "Niet beschikbaar"; 
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1) + Float.parseFloat(priceBuss2)); 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1) + Float.parseFloat(priceFirst2)); 
                        }
                        else if(!check2){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1) + Float.parseFloat(priceEco2)); ; 
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1) + Float.parseFloat(priceFirst2)); 
                        }
                        else if(!check3){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1) + Float.parseFloat(priceEco2)); ; 
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1) + Float.parseFloat(priceBuss2)); ; 
                            totalFirst = "Niet beschikbaar"; 
                        }
                        else{
                        totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1) + Float.parseFloat(priceEco2)); 
                        totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1) + Float.parseFloat(priceBuss2)); 
                        totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1) + Float.parseFloat(priceFirst2)); 
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
                                + "  Flight 3: \n"
                                + "    flightNr= " + flights.get(i)[2].getFlightNr() + "\n" 
                                + "    depDateTime= " + flights.get(i)[2].getDepDateTime() + "\n"  
                                + "    arrivalDateTime= " + flights.get(i)[2].getArrivalDateTime() + "\n"  
                                + "    carbondio= " + flights.get(i)[2].getCarbondio() + "\n"  
                                + "    d_Code= " + flights.get(i)[2].getD_Code() + "\n"  
                                + "    a_Code= " + flights.get(i)[2].getA_Code() + "\n"  
                                + "    ICAO= " + flights.get(i)[2].getICAO()+ "\n"
                                + " " + "\n"
                                + "    PRICE Economy= " + priceEco2 + "\n"
                                + "    PRICE Business= " + priceBuss2 + "\n"
                                + "    PRICE First Class= " + priceFirst2 + "\n"        
                                + " " + "\n"
                                + "  Total price: \n "
                                + "   PRICE Economy= " + totalEco+ "\n"
                                + "    PRICE Business= " + totalBuss+ "\n"
                                + "    PRICE First Class= " + totalFirst+ "\n"
                                + " " + "\n"
                                + "------------------------------------ \n"
                                + " " + "\n");
                    }
                    if(flights.get(i).length == 4){
                                 boolean check1 = true;
                                 boolean check2 = true; 
                                 boolean check3 = true; 
                                 String priceEco = "";
                                    String sql11 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'ECONOMY' " ;

                                        ResultSet rs11 = stmt.executeQuery(sql11);
                                        while(rs11.next()){
                                            priceEco = priceEco.concat(rs11.getString("PRICE"));
                                            }
                                        if(priceEco.equals("")){
                                            priceEco= "Geen Economy Class beschikbaar voor deze vlucht";
                                            check1 = false;
                                            }
                                    String priceBuss = "";
                                    String sql21 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'BUSINESS' " ;

                                        ResultSet rs21 = stmt.executeQuery(sql21);
                                        while(rs21.next()){
                                            priceBuss = priceBuss.concat(rs21.getString("PRICE"));
                                            }
                                        if(priceBuss.equals("")){
                                            priceBuss = "Geen Business Class beschikbaar voor deze vlucht";
                                            check2 = false;
                                            }
                                    String priceFirst = "";
                                    String sql31 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[0].getFlightNr() + "' AND FLIGHTCLASS = 'FIRST' " ;

                                        ResultSet rs31 = stmt.executeQuery(sql31);
                                        while(rs31.next()){
                                            priceFirst = priceFirst.concat(rs31.getString("PRICE"));
                                            }
                                        if(priceFirst.equals("")){
                                            priceFirst = "Geen First Class beschikbaar voor deze vlucht";
                                            check3 = false;
                                            }
                                    String priceEco1 = "";
                                    String sql12 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[1].getFlightNr() + "' AND FLIGHTCLASS = 'ECONOMY' " ;

                                        ResultSet rs12 = stmt.executeQuery(sql12);
                                        while(rs12.next()){
                                            priceEco1 = priceEco1.concat(rs12.getString("PRICE"));
                                            }
                                        if(priceEco1.equals("")){
                                            priceEco1 = "Geen Economy Class beschikbaar voor deze vlucht";
                                            check1 = false;
                                            }
                                    String priceBuss1 = "";
                                    String sql22 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[1].getFlightNr() + "' AND FLIGHTCLASS = 'BUSINESS' " ;

                                        ResultSet rs22 = stmt.executeQuery(sql22);
                                        while(rs22.next()){
                                            priceBuss1 = priceBuss1.concat(rs22.getString("PRICE"));
                                            }
                                        if(priceBuss1.equals("")){
                                            priceBuss1 = "Geen Business Class beschikbaar voor deze vlucht";
                                            check2 = false;
                                            }
                                    String priceFirst1 = "";
                                    String sql32 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[1].getFlightNr() + "' AND FLIGHTCLASS = 'FIRST' " ;

                                        ResultSet rs32 = stmt.executeQuery(sql32);
                                        while(rs32.next()){
                                            priceFirst1 = priceFirst1.concat(rs32.getString("PRICE"));
                                            }
                                        if(priceFirst1.equals("")){
                                            priceFirst1 = "Geen First Class beschikbaar voor deze vlucht";
                                            check3 = false;
                                            }
                                        
                                    String priceEco2 = "";
                                    String sql13 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[2].getFlightNr() + "' AND FLIGHTCLASS = 'ECONOMY' " ;

                                        ResultSet rs13 = stmt.executeQuery(sql13);
                                        while(rs13.next()){
                                            priceEco2 = priceEco2.concat(rs13.getString("PRICE"));
                                            }
                                        if(priceEco2.equals("")){
                                            priceEco2 = "Geen Economy Class beschikbaar voor deze vlucht";
                                            check1 = false;
                                            }
                                    String priceBuss2 = "";
                                    String sql23 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[2].getFlightNr() + "' AND FLIGHTCLASS = 'BUSINESS' " ;

                                        ResultSet rs23 = stmt.executeQuery(sql23);
                                        while(rs23.next()){
                                            priceBuss2 = priceBuss2.concat(rs23.getString("PRICE"));                                           
                                            }
                                        if(priceBuss2.equals("")){
                                            priceBuss2 = "Geen Business Class beschikbaar voor deze vlucht";
                                            check2 = false;
                                            }
                                    String priceFirst2 = "";
                                    String sql33 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[2].getFlightNr() + "' AND FLIGHTCLASS = 'FIRST' " ;

                                        ResultSet rs33 = stmt.executeQuery(sql33);
                                        while(rs33.next()){
                                            priceFirst2 = priceFirst2.concat(rs33.getString("PRICE"));
                                            }
                                        if(priceFirst2.equals("")){
                                            priceFirst2 = "Geen First Class beschikbaar voor deze vlucht";
                                            check3 = false;
                                            }
                                        
                                    String priceEco3 = "";
                                    String sql131 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[3].getFlightNr() + "' AND FLIGHTCLASS = 'ECONOMY' " ;

                                        ResultSet rs131 = stmt.executeQuery(sql131);
                                        while(rs131.next()){
                                            priceEco3 = priceEco3.concat(rs131.getString("PRICE"));
                                            }
                                        if(priceEco3.equals("")){
                                            priceEco3 = "Geen Economy Class beschikbaar voor deze vlucht";
                                            check1 = false;
                                            }
                                    String priceBuss3 = "";
                                    String sql231 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[3].getFlightNr() + "' AND FLIGHTCLASS = 'BUSINESS' " ;

                                        ResultSet rs231 = stmt.executeQuery(sql231);
                                        while(rs231.next()){
                                            priceBuss3 = priceBuss3.concat(rs231.getString("PRICE"));                                           
                                            }
                                        if(priceBuss3.equals("")){
                                            priceBuss3 = "Geen Business Class beschikbaar voor deze vlucht";
                                            check2 = false;
                                            }
                                    String priceFirst3 = "";
                                    String sql331 = "SELECT PRICE "
                                    + "FROM PRICE "
                                    + "WHERE FLIGHTNR = '" + flights.get(i)[3].getFlightNr() + "' AND FLIGHTCLASS = 'FIRST' " ;

                                        ResultSet rs331 = stmt.executeQuery(sql331);
                                        while(rs331.next()){
                                            priceFirst3 = priceFirst3.concat(rs331.getString("PRICE"));
                                            }
                                        if(priceFirst3.equals("")){
                                            priceFirst3 = "Geen First Class beschikbaar voor deze vlucht";
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
                       else if(!check1 && !check2){
                            totalEco = "Niet beschikbaar";
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1) + Float.parseFloat(priceFirst2) + + Float.parseFloat(priceFirst3));
                        }
                        else if(!check1 && !check3){
                            totalEco = "Niet beschikbaar";
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1) + Float.parseFloat(priceBuss2)+ Float.parseFloat(priceBuss3)); 
                            totalFirst = "Niet beschikbaar";
                        }
                        else if(!check2 && !check3){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1) + Float.parseFloat(priceEco2) + Float.parseFloat(priceEco3)); 
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = "Niet beschikbaar";
                        }
                        else if(!check1){
                            totalEco = "Niet beschikbaar"; 
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1) + Float.parseFloat(priceBuss2) + Float.parseFloat(priceBuss3)); 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1) + Float.parseFloat(priceFirst2) + Float.parseFloat(priceFirst3)); 
                        }
                        else if(!check2){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1) + Float.parseFloat(priceEco2) + Float.parseFloat(priceEco3)); ; 
                            totalBuss = "Niet beschikbaar"; 
                            totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1) + Float.parseFloat(priceFirst2) + Float.parseFloat(priceFirst3)); 
                        }
                        else if(!check3){
                            totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1) + Float.parseFloat(priceEco2) + Float.parseFloat(priceEco3)); ; 
                            totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1) + Float.parseFloat(priceBuss2) + Float.parseFloat(priceBuss3)); ; 
                            totalFirst = "Niet beschikbaar"; 
                        }
                        else{
                        totalEco = String.valueOf(Float.parseFloat(priceEco) + Float.parseFloat(priceEco1) + Float.parseFloat(priceEco2) + Float.parseFloat(priceEco3)); 
                        totalBuss = String.valueOf(Float.parseFloat(priceBuss) + Float.parseFloat(priceBuss1) + Float.parseFloat(priceBuss2) + Float.parseFloat(priceBuss3)); 
                        totalFirst = String.valueOf(Float.parseFloat(priceFirst) + Float.parseFloat(priceFirst1) + Float.parseFloat(priceFirst2) + Float.parseFloat(priceFirst3)); 
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
                                + "  Flight 3: \n"
                                + "    flightNr= " + flights.get(i)[2].getFlightNr() + "\n" 
                                + "    depDateTime= " + flights.get(i)[2].getDepDateTime() + "\n"  
                                + "    arrivalDateTime= " + flights.get(i)[2].getArrivalDateTime() + "\n"  
                                + "    carbondio= " + flights.get(i)[2].getCarbondio() + "\n"  
                                + "    d_Code= " + flights.get(i)[2].getD_Code() + "\n"  
                                + "    a_Code= " + flights.get(i)[2].getA_Code() + "\n"  
                                + "    ICAO= " + flights.get(i)[2].getICAO()+ "\n"
                                + " " + "\n"
                                + "    PRICE Economy= " + priceEco2 + "\n"
                                + "    PRICE Business= " + priceBuss2 + "\n"
                                + "    PRICE First Class= " + priceFirst2 + "\n"        
                                + " " + "\n"
                                + "  Total price: \n "
                                + "   PRICE Economy= " + totalEco+ "\n"
                                + "    PRICE Business= " + totalBuss+ "\n"
                                + "    PRICE First Class= " + totalFirst+ "\n"
                                + " " + "\n"
                                
                                + " " + "\n"
                                + "  Flight 4: \n"
                                + "    flightNr= " + flights.get(i)[3].getFlightNr() + "\n" 
                                + "    depDateTime= " + flights.get(i)[3].getDepDateTime() + "\n"  
                                + "    arrivalDateTime= " + flights.get(i)[3].getArrivalDateTime() + "\n"  
                                + "    carbondio= " + flights.get(i)[3].getCarbondio() + "\n"  
                                + "    d_Code= " + flights.get(i)[3].getD_Code() + "\n"  
                                + "    a_Code= " + flights.get(i)[3].getA_Code() + "\n"  
                                + "    ICAO= " + flights.get(i)[3].getICAO()+ "\n"
                                + " " + "\n"
                                + "    PRICE Economy= " + priceEco3 + "\n"
                                + "    PRICE Business= " + priceBuss3 + "\n"
                                + "    PRICE First Class= " + priceFirst3 + "\n"        
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
  public static void main(String args[]) throws ParseException, SQLException{
      
      try {
        ArrayList<ArrayList<Flight[]>> test = getFlight("brussels", "frankfurt", "25/11/2019");
          System.out.println("lengte arraylist1 = " + test.size());
        for(int i = 0; i < test.size(); i++){
            System.out.println("met" + i + " transfer = " + test.get(i).size());
            for(int e = 0; e < test.get(i).size(); e ++){
                for(int u = 0; u < test.get(i).get(e).length; u++){
                    System.out.println("flight number: "+test.get(i).get(e)[u].getFlightNr());
                    System.out.println("vertrek: "+test.get(i).get(e)[u].getD_Code());
                    System.out.println("aankomst: "+test.get(i).get(e)[u].getA_Code());
                    System.out.println("vertrek: "+test.get(i).get(e)[u].getDepDateTime() + " \n ");
                    System.out.println("aankomst: "+test.get(i).get(e)[u].getArrivalDateTime()+ " \n ");
                }
                System.out.println("----------");
                // D_CODE.equalsIgnoreCase(d_Code) && overigeFlights.get(p).getD_Code().equalsIgnoreCase(D_Code)
            }
        }

//System.out.println(toString2(test));
    } 
    catch (DBException e) {
       System.out.println("fout");
        // Logger.getLogger(DBBooking.class.getName()).log(Level.SEVERE, null, ex);
     } 
     
      
  }

}

