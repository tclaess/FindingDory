/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import project.logic.Customer;

/**
 * FXML Controller class
 *
 * @author mathi
 */
public class KlantInfoController implements Initializable {
    
    @FXML private TableView<Customer> algemeenTableView;
    @FXML private TableColumn<Customer, String> voornaamColumn;
    @FXML private TableColumn<Customer, String> achternaamColumn;
    @FXML private TableColumn<Customer, String> genderColumn;
    @FXML private TableColumn<Customer, String> geboortedatumColumn;
    @FXML private TableColumn<Customer, String> landColumn;
    @FXML private TableColumn<Customer, String> IDColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
