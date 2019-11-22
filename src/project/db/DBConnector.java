/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.db;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnector {

    private static final String DB_NAME = "db2019_03";//vul hier uw databank naam in
    private static final String DB_PASS = "jsmdnfhr";//vul hier uw databank paswoord in

    public static Connection getConnection() throws DBException {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
          
            
            String URL = "jdbc:mysql://pdbmbook.com:3306/db2019_03";

            con = DriverManager.getConnection(URL, DB_NAME, DB_PASS);
            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            closeConnection(con);
            throw new DBException(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            closeConnection(con);
            throw new DBException(e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            closeConnection(con);
            throw new DBException(e);
        }
    }

    public static void closeConnection(Connection con) {
        try {
		 if(con != null)
            	con.close();
        } catch (SQLException e) {
            //do nothing
        }
    }
}