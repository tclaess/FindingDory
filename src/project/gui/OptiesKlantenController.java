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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project.logic.Agency;

/**
 * FXML Controller class
 *
 * @author mathi
 */
public class OptiesKlantenController implements Initializable {

    private Agency model;
    
    @FXML
    private Button klantToevoegenBtn;
    @FXML
    private Button klantInfoBtn;
    @FXML
    private Button klantUpdateBtn;
    @FXML
    private Button terugBtn;
    @FXML
    private Button klantRapportBtn;
    private TextField klantInfoTxt;
    @FXML
    private TextField voornaamInfoTxt;
    @FXML
    private TextField voornaamUpdateTxt;
    @FXML
    private TextField voornaamRapportTxt;
    @FXML
    private TextField achternaamInfoTxt;
    @FXML
    private TextField achternaamUpdateTxt;
    @FXML
    private TextField achternaamRapportTxt;
    
    @FXML
    public void klantToevoegenBtnPushed(ActionEvent event) throws IOException
    {
        Parent klantToevoegenParent = FXMLLoader.load(getClass().getResource("/project/gui/KlantToevoegen.fxml"));
        Scene klantToevoegenScene = new Scene(klantToevoegenParent);
        
        //This line gets the Stage information
        Stage window = FXMLLoader.load(getClass().getResource("/project/gui/KlantToevoegen.fxml"));
        
        window.setScene(klantToevoegenScene);
        window.show();
    }
    
    public void klantInfoBtnPushed()
    {
        String voornaam = voornaamInfoTxt.getText();
        String achternaam = achternaamInfoTxt.getText();
        
        model.getCustomer(voornaam, achternaam);
        
    }
    
    public void terugBtnPushed (ActionEvent event) throws IOException
    {
        Parent startWindowParent = FXMLLoader.load(getClass().getResource("/project/gui/StartWindow.fxml"));
        Scene startWindowScene = new Scene(startWindowParent);
        
        Stage window = FXMLLoader.load(getClass().getResource("/project/gui/StartWindow.fxml")); 
        
        window.setScene(startWindowScene);
        window.show();
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
