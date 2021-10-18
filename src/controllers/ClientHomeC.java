
package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ClientHomeC implements Initializable, MainInstanceInterface{
	private ConnectToInstance mainInstance = null;
    @FXML
    private Label userWelcomeLabel;

    @FXML
    private Label messageLable;

    @FXML
    private Button specialOfferButton;

    @FXML
    private ImageView randomPicture;

    String user_username = LoginControllerC.userin;
	String user_gasStation1 = "";
	String user_gasStation2 = "";
	String user_gasStation3 = "";
	int numOfGasStations;
	int numOfSales = 0;
    @FXML
    void handleSpecialOfferButtonClick(ActionEvent event) {
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("ClientHomeSale");
    	}
    }
    /**
     * initialize : the function is responsible to display the home
     * picture and the single button to the user.
     * if there are no discounts for the client - the special offer button
     * will be disabled to the user
     */

	 @Override
		public void initialize(URL arg0, ResourceBundle arg1) {
		 if( checkSpecialOffer() )
			{
				specialOfferButton.setDisable(false);
				messageLable.setText("You got " +  numOfSales + " discounts");
			}
			else
			{
				specialOfferButton.setDisable(true);
				messageLable.setText("Maybe next time");
			}
	 }
	
	 /**
     * checkSpecialOffer finds out what are the gas stations that the user
     * has in its subscription. then it checks if there exists an offers for
     * those gas stations.
     * if we have discounts - it counts how many and returns true
     * else returns false 
     * @return boolean - true if there are discounts and false otherwise
     */
    
	public boolean checkSpecialOffer()
	{
		// part 1 : get gasStation1 of user
		ArrayList<String> arr = new ArrayList<>();
		ResultSet rs1 = null;
		
		String query1 = "select gasStation1, gasStation2, gasStation3 from purchaseplan where username = ? ";
		arr.add(user_username);

		try
		{
			ClientUI.chat.accept("");
			rs1 = ChatClient.selectWithParameters(query1, arr);
			
			if(rs1 != null)
			{
				while(rs1.next())
				{
					System.out.println("user_gasStation2 before : " +  user_gasStation2);

					user_gasStation1 = rs1.getString("gasStation1");
					user_gasStation2 = rs1.getString("gasStation2");
					System.out.println("user_gasStation2 after : " +  user_gasStation2);
					user_gasStation3 = rs1.getString("gasStation3");
					
					System.out.println("userGasStation1 : " + user_gasStation1);
					System.out.println("userGasStation2 : " + user_gasStation2);
					System.out.println("userGasStation3 : " + user_gasStation3);
				}
			}
			else
			{
				return false;
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerRates : client server problem");
			e.printStackTrace();
		}
		
		// part 2 : check if the system has special offers for those gas stations
		ArrayList<String> crr = new ArrayList<>();
		ResultSet rs3 = null;
		String query3 = "";
		boolean isSaleNumberZero = true;
		
		if( (checkGasStation(user_gasStation1)) && (!checkGasStation(user_gasStation2)) && (!checkGasStation(user_gasStation3)) )
		{
			query3 = "select * from sales where gasStation = ?";
			crr.add(user_gasStation1);
			
			numOfGasStations = 1;
		}
		
		if( (checkGasStation(user_gasStation1)) && (checkGasStation(user_gasStation2)) && (!checkGasStation(user_gasStation3)) )
		{
			query3 = "select * from sales where gasStation = ? OR gasStation = ?";
			crr.add(user_gasStation1);
			crr.add(user_gasStation2);
			
			numOfGasStations = 2;
		}
		
		if( (checkGasStation(user_gasStation1)) && (checkGasStation(user_gasStation2)) && (checkGasStation(user_gasStation3)) )
		{
			query3 = "select * from sales where gasStation = ? OR gasStation = ? OR gasStation = ?";
			crr.add(user_gasStation1);
			crr.add(user_gasStation2);
			crr.add(user_gasStation3);
			
			numOfGasStations = 3;
		}

		try
		{
			ClientUI.chat.accept("");
			rs3 = ChatClient.selectWithParameters(query3, crr);
			
			
			while(rs3.next())
			{
				numOfSales++;
				isSaleNumberZero = false;
			}
			
			if(isSaleNumberZero)
			{
				return false;
			}

		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerRates : client server problem");
			e.printStackTrace();
		}
		
		return true;
	}
	/**
     * checkGasStation get a gas station name and return true or false
     * @param gasStationString - the gas station name
     * @return boolean - true if gas station is one of ours and else otherwise
     */
	
	public boolean checkGasStation(String gasStationString)
	{
		if( gasStationString.equals("Delek") )
		{
			return true;
		}
		if( gasStationString.equals("Paz") )
		{
			return true;
		}
		if( gasStationString.equals("Sonol") )
		{
			return true;
		}
		if( gasStationString.equals("Ten") )
		{
			return true;
		}
		
		return false;
	}
	 /**
     * setMainInstance : this is the function from the MainInstanceInterface interface
     * here we handle opening internal forms from another controller (another window)
     * @param main_instance - the instance of the main controller in which
     *  we want to use
     */
    @Override
	public void setMainInstance(ConnectToInstance main_instance)
	{
		this.mainInstance = main_instance;
	}
}
