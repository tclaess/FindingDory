/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.logic;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import project.db.DBCustomer;
import project.db.DBException;

/**
 *
 * @author aroen
 */
public class CustomerDBLogic {
    
    private static ArrayList<Customer> customers;
    private static CustomerDBLogic customerDBLogic = new CustomerDBLogic();
  
    public CustomerDBLogic() 
    {
        try {
        customers = DBCustomer.getCustomers();
        } 
        catch (DBException ex) {
            Logger.getLogger(CustomerDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    
    public static CustomerDBLogic getInstance()
    {
        return customerDBLogic;
    }

    public ArrayList<Customer> getCustomers() 
    {
        return customers;
    }
    
    public static Customer getCustomer(String number) throws DBException 
    {
        return CustomerDBLogic.getCustomer(number);
    }
    
    public void addCustomer(Customer customer) 
    {
        try {
            customers.add(customer);
            DBCustomer.saveCustomer(customer);
        } catch (DBException ex) {
            Logger.getLogger(CustomerDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    public void deleteCustomer(Customer customer)
    {
        try
        {
            customers.remove(customer);
            
            DBCustomer.deleteCustomer(customer);
            
        } 
        catch(DBException ex) 
        {
        Logger.getLogger(CustomerDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void changeCustomer(Customer customer) 
    {
        try {
            int number = 0;
            for (int i = 0; i < customers.size(); i++) 
            {
                if (customer.getID().equalsIgnoreCase(customers.get(i).getID()))
                {
                    number = i;
                }
            }
            int index = number;
            customers.set(index, customer);
            DBCustomer.saveCustomer(customer);
        } catch (DBException ex) {
            Logger.getLogger(CustomerDBLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}



