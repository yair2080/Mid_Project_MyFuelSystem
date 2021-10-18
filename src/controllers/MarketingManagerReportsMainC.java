package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * this is a controller for MarketingManagerReportsMain gui.
 * we enter this form when we click on the rates button in the main window
 * the form simply display two buttons that will lead us to the reports forms
 */

public class MarketingManagerReportsMainC implements MainInstanceInterface
{
	private ConnectToInstance mainInstance = null;
	
    @FXML
    private Button customerCharacterizationReportButton;

    @FXML
    private Button salesFeedbackReportButton;

    @FXML
    private Label userWelcomeLabel;
    
    /**
     * handleCustomerCharacterizationReportButtonClick opens the characterization report form.
     * the function uses an inner function called "sendMessage" that is implemented
     * in the marketingManagerMainC controller (this function belongs
     *  to the interface "connectToInstance")
     * @param event - this event occurs when the user clicks on the characterization report button
     */

    @FXML
    void handleCustomerCharacterizationReportButtonClick(ActionEvent event)
    {
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("MarketingManagerReportCharacterization");
    	}
    }
   
    /**
     * handleSalesFeedbackReportButtonClick opens the sales feedback report form.
     * the function uses an inner function called "sendMessage" that is implemented
     * in the marketingManagerMainC controller (this function belongs
     *  to the interface "connectToInstance")
     * @param event - this event occurs when the user clicks on the sales feedback report button
     */
    
    @FXML
    void handleSalesFeedbackReportButtonClick(ActionEvent event)
    {

    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("MarketingManagerReportSalesFeedback");
    	}
    }
    
    /**
     * setMainInstance : this is the function from the MainInstanceInterface interface
     * here we handle opening internal forms from another controller (another window)
     * @param main_instance - the instance of the main controller in which
     *  we want to use
     */

    public void setMainInstance(ConnectToInstance main_instance)
	{
		this.mainInstance = main_instance;
	}
}
