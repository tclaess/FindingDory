/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author aroen
 */
public class StartWindowController implements Initializable {

    @FXML
    private Button BoekReisBtn;
    @FXML
    private Button ZoekBoekingBtn;
    @FXML
    private Button KlantenbestandBtn;
    private AnchorPane dataPan;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void BoekReis(ActionEvent event) {
    try {
      
      
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/tricksproject/gui/OptiesReis.fxml"));
      dataPan.getChildren().setAll(pane);
      
      
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  
    @FXML
    private void ZoekBoeking(ActionEvent event) {
    try {
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/tricksproject/gui/OptiesBoeking.fxml"));
      dataPan.getChildren().setAll(pane);
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
    @FXML
   private void OptiesKlanten(ActionEvent event) {
    try {
      AnchorPane pane = (AnchorPane) FXMLLoader.load(getClass().getResource("/tricksproject/gui/OptiesKlanten.fxml"));
      dataPan.getChildren().setAll(pane);
    } catch (IOException ex) {
      Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  
  
}


