/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.PropertyValueFactory;
import project.logic.Agency;
import project.logic.Customer;

/**
 * FXML Controller class
 *
 * @author mathi
 */
public class KlantInfoController implements Initializable {
    
    private Agency model;
    
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
        voornaamColumn.setCellValueFactory(new PropertyValueFactory<Customer, String> ("firstName"));
        achternaamColumn.setCellValueFactory(new PropertyValueFactory<Customer, String> ("lastName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Customer, String> ("gender"));
        geboortedatumColumn.setCellValueFactory(new PropertyValueFactory<Customer, String> ("birthdayDate"));
        landColumn.setCellValueFactory(new PropertyValueFactory<Customer, String> ("country"));
        IDColumn.setCellValueFactory(new PropertyValueFactory<Customer, String> ("ID"));
        
        //load data
        
        algemeenTableView.setItems(getCustomer());
    }
    
    public ObservableList<Customer> getCustomer()
    {
        ObservableList<Customer> c = FXCollections.observableArrayList();
        c.add(model.getCustomer(voornaamInfoTxt.getText(), achternaamInfoTxt.getText())); //Textfield uit OptiesKlantenController nodig
        
        return c;
    }
}
