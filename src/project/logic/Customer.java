/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.logic;

import java.util.ArrayList;

public class Customer {
  private String ID;
  private String country;
  private String firstName;
  private String lastName;
  private String birthdayDate;
  private String gender;

    public Customer(String ID, String country, String firstName, String lastName, String birthdayDate, String gender) {
        this.ID = ID;
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdayDate = birthdayDate;
        this.gender = gender;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getID() {
        return ID;
    }

    public String getCountry() {
        return country;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthdayDate() {
        return birthdayDate;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Customer{" + "ID=" + ID + ", country=" + country + ", firstName=" + firstName + ", lastName=" + lastName + ", birthdayDate=" + birthdayDate + ", gender=" + gender + '}';
    }
}
    