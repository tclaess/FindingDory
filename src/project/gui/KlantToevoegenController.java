/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import project.db.DBCustomer;
import project.db.DBException;
import project.logic.Customer;

/**
 * FXML Controller class
 *
 * @author mathi
 */
public class KlantToevoegenController implements Initializable {

    @FXML
    private TextField voornaamTxt;
    @FXML
    private TextField achternaamTxt;
    @FXML
    private TextField landTxt;
    @FXML
    private TextField IDTxt;
    @FXML
    private TextField geboortedatumTxt;
    @FXML
    private Button klantToevoegenBtn;
    
    private ToggleGroup genderToggleGroup;
    @FXML
    private RadioButton manRBtn;
    @FXML
    private RadioButton vrouwRBtn;
    @FXML
    private Label testLbl;
    
    public void klantToevoegenBtnPushed(ActionEvent event) throws DBException
    {
        String test = "";
        String voornaam = voornaamTxt.getText();
        String achternaam = achternaamTxt.getText();
        String land = landTxt.getText();
        String ID = IDTxt.getText();
        String geboortedatum = geboortedatumTxt.getText();
        String gender = radioBtnCheck();
        
        if(!geboortedatum.matches("[0-9]{2}[/]{1}[0-9]{2}[/]{1}[0-9]{4}"))
            test += "Controleer of uw geboortedatum correct is ingevoerd";
        
        //ruimte voor andere testen: lege strings, minimaal aantal karakters,..
        
        if(!test.equals(""))
            this.testLbl.setText(test);
        else
        {
            Customer klant = new Customer(ID, land, voornaam, achternaam, geboortedatum, gender);
                                                                                //update lijst van customers bij optiesKlanten en eigenlijk ook bij reis boeken
            DBCustomer.saveCustomer(klant);                                     //moet nog via model agency
        }
    }
    
    public void terugBtnPushed (ActionEvent event) throws IOException
    {
        Parent klantenbestandParent = FXMLLoader.load(getClass().getResource("/project/gui/OptiesKlanten.fxml"));
        Scene klantenbestandScene = new Scene(klantenbestandParent);
        
        Stage window = FXMLLoader.load(getClass().getResource("/project/gui/OptiesKlanten.fxml")); 
        
        window.setScene(klantenbestandScene);
        window.show();
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
        
    }    
    
}
