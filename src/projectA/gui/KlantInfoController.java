/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectA.gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import project.db.DBCustomer;
import static project.db.DBCustomer.getCustomer;
import static project.db.DBCustomer.getCustomers;
import project.db.DBException;
import project.logic.Agency;
import project.logic.Booking;
import project.logic.Customer;
import project.logic.CustomerDBLogic;

/**
 * FXML Controller class
 *
 * @author aroen
 */
public class KlantInfoController {
    
 @FXML 
 private TableView<Customer> tableView;
 @FXML
 private TableColumn<Customer, String> IDColumn;
 @FXML
 private TableColumn<Customer, String> CountryColumn;
 @FXML
 private TableColumn<Customer, String> FirstNameColumn;
 @FXML
 private TableColumn<Customer, String> LastNameColumn;
 @FXML
 private TableColumn<Customer, String> BirthdayDateColumn;
 @FXML
 private TableColumn<Customer, String> GenderColumn;

    private ArrayList<Customer> model4;
   
    private ObservableList<Customer> customers;        
          
    @FXML
    private Button detailedPersonViewButton1;
    @FXML
    private Button back41;
    @FXML
    private Button delete44;
    private CustomerDBLogic model;
    @FXML
 public void changeIDCellEvent(CellEditEvent edittedCell){
     
     
     Customer personSelected = tableView.getSelectionModel().getSelectedItem();
     personSelected.setID(edittedCell.getNewValue().toString());
 }
    
    
 @FXML
 public void changeCountryCellEvent(CellEditEvent edittedCell){
     
     
     Customer personSelected = tableView.getSelectionModel().getSelectedItem();
     personSelected.setCountry(edittedCell.getNewValue().toString());
 }
    
 @FXML
     public void changeyFirstNameCellEvent(CellEditEvent edittedCell){
     
     
     Customer personSelected = tableView.getSelectionModel().getSelectedItem();
     personSelected.setFirstName(edittedCell.getNewValue().toString());
 }
 @FXML
      public void changeLastNameCellEvent(CellEditEvent edittedCell){
     
     
     Customer personSelected = tableView.getSelectionModel().getSelectedItem();
     personSelected.setLastName(edittedCell.getNewValue().toString());
 }
 @FXML
       public void changeBirthdayDateCellEvent(CellEditEvent edittedCell){
     
     
     Customer personSelected = tableView.getSelectionModel().getSelectedItem();
     personSelected.setBirthdayDate(edittedCell.getNewValue().toString());
 }
 @FXML
        public void changeGenderCellEvent(CellEditEvent edittedCell){
     
     
     Customer personSelected = tableView.getSelectionModel().getSelectedItem();
     personSelected.setGender(edittedCell.getNewValue().toString());
 }
        
    
    
    //gaat terug naar optiesklanten
    @FXML
    private void backopt11(ActionEvent event) throws IOException {
        
        Parent loaderParent = FXMLLoader.load(getClass().getResource("OptiesKlanten.fxml"));
      Scene loaderScene = new Scene(loaderParent);
      Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
      
      Window.setScene(loaderScene);
      Window.show();
        
         
    }
    @FXML
    public void deleteButtonPushed1(){
        ObservableList<Customer> selectedRows, allPeople;
        allPeople = tableView.getItems();
        
        selectedRows = tableView.getSelectionModel().getSelectedItems();
        // gaan over de geselecteerde rijen
        for(Customer customer: selectedRows)
        {
            allPeople.remove(customer);
        }
    }
    @FXML
    private void klantRapport1(ActionEvent event) throws IOException {
       
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("KlantenRapport.fxml"));
        Parent loaderParent = loader.load();
        
        Scene loaderScene = new Scene(loaderParent);
      
      //staat in verbinding met de controller en roept een methode op
      KlantenRapportController controller = loader.getController();
      controller.initData(tableView.getSelectionModel().getSelectedItem());
      
      Stage Window = (Stage)((Node)event.getSource()).getScene().getWindow();
      
      Window.setScene(loaderScene);
      Window.show();
        
        
    }
    
    //methode zorgt voor het inschakelen van de delete button wanneer er 1 of meer rijen geselecteerd zijn
    
    @FXML
    public void selectedARow()
    {
        this.detailedPersonViewButton1.setDisable(false);
    }
    
    
    
    public void initialize(URL url, ResourceBundle rb) throws DBException{
        // inzetten in de kolommen
        
       IDColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("ID"));
       CountryColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("Country"));
       FirstNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
       LastNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
       BirthdayDateColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("birthdayDate"));
       GenderColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("gender"));
      model = CustomerDBLogic.getInstance();
      customers = FXCollections.observableArrayList(model.getCustomers());
       tableView.setItems(customers);
       
       
           
       
       
      
     // dit is voor de gegevens aan te passen  
      tableView.setEditable(true);
     
     IDColumn.setCellFactory(TextFieldTableCell.forTableColumn());
     CountryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
     FirstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
     LastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
     BirthdayDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
     GenderColumn.setCellFactory(TextFieldTableCell.forTableColumn());
     
     //select multiple rows at once
     tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    
     //we hebben de delete customer uitgeschakeld wanneer deze niemand heeft geselecteerd 
     this.detailedPersonViewButton1.setDisable(true);
    }
    
 

    
    

   

    
  
    
    
    
    
    
}

    
    
    
   




