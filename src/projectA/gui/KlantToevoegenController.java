/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectA.gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import project.db.DBCustomer;
import project.db.DBException;
import project.logic.Agency;
import project.logic.Customer;
import project.logic.CustomerDBLogic;

/**
 * FXML Controller class
 *
 * @author aroen
 */
public class KlantToevoegenController implements Initializable {
private CustomerDBLogic model;
    private CheckBox ManCheck;
    private CheckBox VrouwCheck;
    @FXML
    private Button terugKlantOPties;
    @FXML
    private TextField VoornaamTxt;
    @FXML
    private TextField AchternaamTxt;
    @FXML
    private TextField LandTxt;
    @FXML
    private TextField IDTxt;
    @FXML
    private Button ToevoegenBtn;
    private TextField geslacht;
    
    @FXML
    private TextField datum;
    @FXML
    private AnchorPane datapantoevoegen;
@FXML
    private RadioButton manRBtn;
    @FXML
    private RadioButton vrouwRBtn;
    private ToggleGroup genderToggleGroup;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        genderToggleGroup = new ToggleGroup();
        this.manRBtn.setToggleGroup(genderToggleGroup);
        this.vrouwRBtn.setToggleGroup(genderToggleGroup);
        genderToggleGroup.selectToggle(manRBtn);
        model = CustomerDBLogic.getInstance();
    }    
    
    public String radioBtnCheck()                                               //beke mijn twijfels over
    {
        String gender = "";
        if(this.genderToggleGroup.getSelectedToggle().equals(this.manRBtn))
            gender = "MALE";
        if(this.genderToggleGroup.getSelectedToggle().equals(this.vrouwRBtn))
            gender = "FEMALE";
        return gender;
    }
    @FXML
    public void terugBtnPushed (ActionEvent event) throws IOException
    {
         Parent loaderParent = FXMLLoader.load(getClass().getResource("OptiesKlanten.fxml"));
      Scene loaderScene = new Scene(loaderParent);
      Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
      
      Window.setScene(loaderScene);
      Window.show();
        
    }

    @FXML
    private void addCustomer(ActionEvent event) throws DBException, IOException {
        
     
      String FirstName = VoornaamTxt.getText();
      String LastName = AchternaamTxt.getText();
      
       String Country = LandTxt.getText();
       String ID = IDTxt.getText();
       
       
      String BirthdayDate = datum.getText(); 
      
      String gender = radioBtnCheck();
     
     
         
      

          
       
     
    
      boolean inputValid = false;
        while(!inputValid){
            
            
            if(FirstName.length() < 2){
                JOptionPane.showMessageDialog(null, "Geef een geldige voornaam op.");
                break;
            }
            if(LastName.length() < 2){
                JOptionPane.showMessageDialog(null, "Geef een geldige naam op.");
                break;  
            } 
            
            if(Country.length() < 2){
                JOptionPane.showMessageDialog(null, "Geef een geldig land op.");
                break;                 
            }
            
            
            if(ID.length() < 3){
                JOptionPane.showMessageDialog(null, "Geef een geldige ID op.");
                break; 
            }
            if(!BirthdayDate.matches("[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}")){
                
            
                JOptionPane.showMessageDialog(null, "Controleer of uw geboortedatum correct is ingevoerd");
                break; 
            }
            inputValid = true;
        }
           if(inputValid)
           {
            Customer klant = new Customer(ID, Country, FirstName, LastName, BirthdayDate, gender);
                                                                                //update lijst van customers bij optiesKlanten en eigenlijk ook bij reis boeken
            model.addCustomer(klant);      
            }
           else{
               System.exit(0);
           }
            
           
            
           
        
        
            
            
        
      
      
      
      
      
      
      
      
      
      
     
      
      
      /*
      loader.load();
      StudentListViewController controller =
              (StudentListViewController) loader.getController();
      controller.addStudent(student);
      
      //studentListView.getItems().add(student);
      model.addStudent(student);
      System.out.println("aantal studenten" + model.getStudents().size());
    } catch (IOException ex) {
      Logger.getLogger(StudentAddController.class.getName()).log(Level.SEVERE, null, ex);
*/
    }
  
        
        
        
  }   
        
 
    
    






