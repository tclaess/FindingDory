
package projectA.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import project.logic.Customer;















/**
 * FXML Controller class
 *
 * @author aroen
 */
public class KlantenRapportController implements Initializable {

    private Customer selectedCustomer;
    @FXML
    private Label IDlabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label lnLabel;
    @FXML
    private Label fnLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label gLabel;
    @FXML
    private Label dbLabel;
    @FXML
    private Label fromLabel;
    @FXML
    private Label toLabel;
    @FXML
    private Label agelabel;
    

    public void initData(Customer customer){
        selectedCustomer = customer;
        IDlabel.setText(selectedCustomer.getID());
        countryLabel.setText(selectedCustomer.getCountry());
        fnLabel.setText(selectedCustomer.getFirstName());
        lnLabel.setText(selectedCustomer.getFirstName());
        dateLabel.setText(selectedCustomer.getBirthdayDate());
        gLabel.setText(selectedCustomer.getGender());
        
        toLabel.setText(selectedCustomer.getFirstName());
    }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
