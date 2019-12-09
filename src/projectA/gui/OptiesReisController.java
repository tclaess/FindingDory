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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mathi
 */
public class OptiesReisController implements Initializable {

    
    
    
    
    
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void KlantDieAlBestaat(ActionEvent event) throws IOException {
         Parent loaderParent = FXMLLoader.load(getClass().getResource("OptiesReis2.fxml"));
      Scene loaderScene = new Scene(loaderParent);
      Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
      
      Window.setScene(loaderScene);
      Window.show();
        
        
        
        
        
    }
    
    
        private void KlantDieNogNietbestaat(ActionEvent event) throws IOException {
       Parent loaderParent = FXMLLoader.load(getClass().getResource("KlantToevoegen.fxml"));
      Scene loaderScene = new Scene(loaderParent);
      Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
      
      Window.setScene(loaderScene);
      Window.show();
        }
  }  
        
        
        
    
    








