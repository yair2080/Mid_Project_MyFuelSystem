package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
/**
 * this class is a controller for the update main gui , in this gui there are three buttons . the client button open the update page
 * for a single client details, company button open the update page for a company details and the employee button open the update page
 * for employee details.
 *
 */
public class MarketingRepUpdateMainC implements MainInstanceInterface{

	private ConnectToInstance mainInstance = null;
    @FXML
    private Label userWelcomeLabel;

    @FXML
    private Button UpdateClientButton;

    @FXML
    private Button UpdateWorkerButton;

    @FXML
    private Button UpdateCompanyButton;
    
/**
 * this function opens the update company details
 * @param event ,this event occurs when the user clicks on the button company.
 */
    @FXML
    void UpdateCompanyButton(ActionEvent event) {
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("MarketingRepUpdateCompany");
    	}
    }
/**
 * this function opens the update single client details
 * @param event ,this event occurs when the user clicks on the button single client.
 */
    @FXML
    void handleUpdateClientButton(ActionEvent event) {
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("MarketingRepUpdateClient");
    	}
    }
/**
 * this function opens the update employee details
 * @param event ,this event occurs when the user clicks on the button employee.
 */
    @FXML
    void handleUpdateWorkerButton(ActionEvent event) {
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("MarketingRepUpdateEmployee");
    	}
    }
    
    public void setMainInstance(ConnectToInstance main_instance)
	{
		this.mainInstance = main_instance;
	}

}
