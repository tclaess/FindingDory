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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mathi
 */
public class StartWindowController implements Initializable {

    @FXML
    private Button boekReisBtn;
    @FXML
    private Button zoekBoekingBtn;
    @FXML
    private Button klantenbestandBtn;
    
    public void boekReisBtnPushed (ActionEvent event) throws IOException
    {
        Parent boekReisParent = FXMLLoader.load(getClass().getResource("/project/gui/OptiesReis.fxml"));
        Scene boekReisScene = new Scene(boekReisParent);
        
        Stage window = FXMLLoader.load(getClass().getResource("/project/gui/OptiesReis.fxml")); 
        
        window.setScene(boekReisScene);
        window.show();
    }
    
    public void zoekBoekingBtnPushed (ActionEvent event) throws IOException
    {
        Parent zoekBoekingParent = FXMLLoader.load(getClass().getResource("/project/gui/OptiesBoeking.fxml"));
        Scene zoekBoekingScene = new Scene(zoekBoekingParent);
        
        Stage window = FXMLLoader.load(getClass().getResource("/project/gui/OptiesBoeking.fxml")); 
        
        window.setScene(zoekBoekingScene);
        window.show();
    }
    
    public void klantenbestandBtnPushed (ActionEvent event) throws IOException
    {
        Parent klantenbestandParent = FXMLLoader.load(getClass().getResource("/project/gui/OptiesKlanten.fxml"));
        Scene klantenbestandScene = new Scene(klantenbestandParent);
        
        Stage window = FXMLLoader.load(getClass().getResource("/project/gui/OptiesKlanten.fxml")); 
        
        window.setScene(klantenbestandScene);
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
