package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientHomeHeatingC implements MainInstanceInterface,Initializable
{
	
	private ConnectToInstance mainInstance=null;
	private Stage primaryStage;
	private AnchorPane mainLayout;
	/**
	 * JavaFX parameters
	 */
	@FXML
    private AnchorPane mainProgramPane;

    @FXML
    private Label headlineLable;
    
    @FXML
    private Label userWelcomeLabel; //welcome-username
    
    @FXML
    private BorderPane whiteBorderPane;
    
    @FXML
    private Button followOrderBtn;
   
    @FXML
    private Pane followPane;
    
    /**
     * Opening ClientHomeHeatingOrder
     * @param event
     */
    @FXML
   void handleAddNewOrder(ActionEvent event)
   {
    	if(mainInstance!=null)
    	{
    		mainInstance.sendMessage("ClientHomeHeatingOrder");
    	}
    	
   }
    
    /**
     * Opening ClientHomeHeatingFollowOrders
     * @param event
     */
    @FXML
    void handleFollowOrdersButton(ActionEvent event)
   {
    	
    	if(mainInstance!=null)
    	{
    		mainInstance.sendMessage("ClientHomeHeatingFollowOrders");
    	}
    	
   }

	@Override
	public void setMainInstance(ConnectToInstance main_instance) {
		this.mainInstance=main_instance;
		
	}
	/**initializing view-
	 * Whenever the client don't have any orders- the follow orders wont open because he 
	 * doesn't have any orders to follow.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) { 
		ArrayList<String> pa = new ArrayList<String>();
		pa.add(LoginControllerC.userin);
		String query="SELECT * FROM orders WHERE username=?";
		ClientUI.chat.accept("");
		ResultSet r=ChatClient.selectWithParameters(query, pa);
		try {
			if(!r.next())
			{
				followPane.setDisable(true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
