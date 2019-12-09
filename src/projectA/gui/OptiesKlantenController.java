/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectA.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author aroen
 */
public class OptiesKlantenController implements Initializable {

    @FXML
    private Button KlantToevoegenBtn;
    @FXML
    private Button KlantInfoBtn;
    @FXML
    private Button KlantUpdateBtn;
    @FXML
    private Button KlantRapportBtn;
    private TextField KlantUpdateTxt;
    private TextField KlantRapportTxt;
    private AnchorPane dataPane;
    private TextField country;
    @FXML
    private AnchorPane datapanklanttoev;
    @FXML
    private AnchorPane datapanklantinfo;
    @FXML
    private AnchorPane datapanklantupdate;
    @FXML
    private AnchorPane datapanklantrapport;
    @FXML
    private Button Returnn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    
   
        
    @FXML
    private void KlantInfo3(ActionEvent event) throws IOException {
    
    Parent loaderParent = FXMLLoader.load(getClass().getResource("KlantInfo4.fxml"));
      Scene loaderScene = new Scene(loaderParent);
      Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
      
      Window.setScene(loaderScene);
      Window.show(); 
    
        
    
    }
    
    
    
    
    
    
    @FXML
    private void KlantToevoegen(ActionEvent event) throws IOException {
        
    Parent loaderParent = FXMLLoader.load(getClass().getResource("KlantToevoegen.fxml"));
      Scene loaderScene = new Scene(loaderParent);
      Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
      
      Window.setScene(loaderScene);
      Window.show();
        
        
        
    }
    @FXML
    private void KlantUpdate(ActionEvent event) {
    String ID = KlantUpdateTxt.getText();
    String Country = country.getText();
    
    
    
    }
    
    
    @FXML
    private void KlantRapport(ActionEvent event) {
        int KlantID = Integer.parseInt(KlantRapportTxt.getText());
        
        
    }

    @FXML
    private void Return(ActionEvent event) throws IOException {
        Parent loaderParent = FXMLLoader.load(getClass().getResource("StartWindow22.fxml"));
      Scene loaderScene = new Scene(loaderParent);
      Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
      
      Window.setScene(loaderScene);
      Window.show();
        
        
    }

   

    

    }
    

