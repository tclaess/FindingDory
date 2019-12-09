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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    @FXML
    private AnchorPane datapan1;
    @FXML
    private AnchorPane datapan2;
    @FXML
    private AnchorPane datapan3;
    @FXML
    private AnchorPane StartWindow;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void BoekReis(ActionEvent event) throws IOException {
    
      
      Parent loaderParent = FXMLLoader.load(getClass().getResource("OptiesReis.fxml"));
      Scene loaderScene = new Scene(loaderParent);
      Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
      
      Window.setScene(loaderScene);
      Window.show();
  }

  
    @FXML
    private void ZoekBoeking(ActionEvent event) throws IOException {
    Parent loaderParent = FXMLLoader.load(getClass().getResource("OptiesBoeking.fxml"));
      Scene loaderScene = new Scene(loaderParent);
      Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
      
      Window.setScene(loaderScene);
      Window.show();
  }
    @FXML
   private void OptiesKlanten(ActionEvent event) throws IOException {
   Parent loaderParent = FXMLLoader.load(getClass().getResource("OptiesKlanten.fxml"));
      Scene loaderScene = new Scene(loaderParent);
      Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
      
      Window.setScene(loaderScene);
      Window.show();
   
   }

  
  
}


