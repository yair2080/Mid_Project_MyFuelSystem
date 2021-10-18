package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import entities.Product;
import entities.RateMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


/**
 * this is a controller for ChainManagerRates gui.
 * in this form we can watch rates requests that had been sent
 * by the marketing manager to the chain manager to be confirmed or
 * declined
 */

public class ChainManagerRatesC implements Initializable
{
	String chainManagerUsername = LoginControllerC.userin;
	String chainManagerID = null;
	String userGasStation = "";
	String marketingManagerID = null;
	String productName = null;
	String requestedRate = null;
	
    @FXML
    private TextArea letterTextArea;

    @FXML
    private Button rejectButton;

    @FXML
    private Button confirmButton;

    @FXML
    private TableView<RateMessage> requestedRatesTable;

    @FXML
    private TableColumn<RateMessage, String> dateHeadline;

    @FXML
    private TableColumn<RateMessage, String> chainManagerIDHeadline;

    @FXML
    private TableColumn<RateMessage, String> marketingManagerIDHeadline;

    @FXML
    private TableColumn<RateMessage, String> productNameHeadline;

    @FXML
    private TableColumn<RateMessage, String> requestedRateHeadline;

    @FXML
    private TableView<Product> currentRatesTable;
    
    @FXML
    private TableColumn<Product, String> productNameHeadlineP;

    @FXML
    private TableColumn<Product, String> currentRateHeadline;

    @FXML
    private TableColumn<Product, String> amountInStockHeadline;

    @FXML
    private TableColumn<Product, String> statusHeadline;

    @FXML
    private TableColumn<Product, String> thresholdLevelHeadline;

    @FXML
    private TableColumn<Product, String> maxPriceHeadline;
    
    @FXML
    private TableColumn<Product, String> gasStationNameHeadline;

    /**
     * initialize : the function is doing 3 things :
     * 1. finds out the current chain manager gas station
     * 2. load rates requests into the rates messages table that 
     * I created in the form
     * 3. load the current information (the current rates) of this 
     * gas station products (so the chain manager could see it and
     * then make the right choice)
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		getChainManagerGasStationName();
		loadRateMessageTable();
		loadCurrentRatesTable();	
	}
	
	/**
     * getChainManagerGasStationName : the function is responsible
     * to update the current chain manager gas station
     */
	
	public void getChainManagerGasStationName()
    {
    	ArrayList<String> brr = new ArrayList<>();
    	ResultSet gs = null;
    	
    	String query = "select gasStation from employees where username = ? ";
    	brr.add(chainManagerUsername);	

		try
		{
			ClientUI.chat.accept("");
			gs = ChatClient.selectWithParameters(query, brr);
			
			while(gs.next())
			{
				userGasStation = gs.getString("gasStation");
				System.out.println("userGasStation : " + userGasStation);
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerRates : client server problem");
			e.printStackTrace();
		}
    }
	
	/**
     * loadRateMessageTable : the function finds all the requests that
     * are sent to this chain manager.
     * then it loads those requests to the requests table in the form 
     */
    
	public void loadRateMessageTable()
    {
		// 1. find current user ID (this chain manager ID)
		String query2 = "select workerID from employees where username = ?";
		ArrayList<String> drr = new ArrayList<>();
		drr.add(chainManagerUsername);
		ResultSet ps = null;
		
		try
		{
			ClientUI.chat.accept("");
			ps = ChatClient.selectWithParameters(query2, drr);	
			
			while(ps.next())
			{
				chainManagerID = ps.getString("workerID");
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerRates : client server problem");
			e.printStackTrace();
		}
		
		// 2. get every message that was meant to our current user (chain manager)
		String selectQuery = "select * from ratesrequests where chainManagerID = ?";
    	ResultSet rs = null;
    	
    	ArrayList<String> brr = new ArrayList<>();
    	brr.add(chainManagerID);
		ObservableList<RateMessage> oblist = FXCollections.observableArrayList();

		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, brr);	
			
			while(rs.next())
			{
				oblist.add(new RateMessage(rs.getString("date"), rs.getString("chainManagerID"), rs.getString("marketingManagerID"), rs.getString("productName"), rs.getString("requestedRate")));
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerRates : client server problem");
			e.printStackTrace();
		}
		
		// 3. load messages into messages table
		dateHeadline.setCellValueFactory(new PropertyValueFactory<RateMessage, String>("date"));
		chainManagerIDHeadline.setCellValueFactory(new PropertyValueFactory<RateMessage, String>("chainManagerID"));
		marketingManagerIDHeadline.setCellValueFactory(new PropertyValueFactory<RateMessage, String>("marketingManagerID"));
		productNameHeadline.setCellValueFactory(new PropertyValueFactory<RateMessage, String>("productName"));
		requestedRateHeadline.setCellValueFactory(new PropertyValueFactory<RateMessage, String>("requestedRate"));
		
		
		requestedRatesTable.setItems(oblist);
    }
	
	/**
     * loadCurrentRatesTable : the function finds products information
     * (product name, current rate, max rate) that belongs to the
     * chain manager gas station and shows them in the currentRates table
     * in the form
     */
	
	public void loadCurrentRatesTable()
    {
    	ResultSet rs = null;
    	ArrayList<String> zrr = new ArrayList<>();
    	
		String selectQuery = "select * from products where gasStationName = ?";
		zrr.add(userGasStation);
		
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, zrr);
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerRates : client server problem");
			e.printStackTrace();
		}
		
		ObservableList<Product> oblist = FXCollections.observableArrayList();

		try
		{
			while(rs.next())
			{
				oblist.add(new Product(rs.getString("productName"), rs.getString("rate"), rs.getString("amountInStock"), rs.getString("status"), rs.getString("thresholdLevel"), rs.getString("maxPrice"), rs.getString("gasStationName")));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		productNameHeadlineP.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
		currentRateHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("rate"));
		amountInStockHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("amountInStock"));
		statusHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("status"));
		thresholdLevelHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("thresholdLevel"));
		maxPriceHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("maxPrice"));
		gasStationNameHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("gasStationName"));
		
		currentRatesTable.setItems(oblist);	
    }
	
	 /**
     * handleMessageRowClick opens the request in a letter paper.
     * (displays it as a real request)
     * @param event - this event occurs when the user clicks on a request row in the table
     */
	
    @FXML
    void handleMessageRowClick(MouseEvent event)
    {
    	int index = requestedRatesTable.getSelectionModel().getSelectedIndex();
		
		if(index <= -1)
		{
			return;
		}
		
		marketingManagerID = marketingManagerIDHeadline.getCellData(index).toString();
		
		// 1st step : find firstName and lastName of our current user
    	String selectQuery = "select firstName, lastName from employees where username = ?";
    	ResultSet rs1 = null;
    	
    	ArrayList<String> brr = new ArrayList<>();
    	brr.add(chainManagerUsername);
    	
    	String chainManagerFirstName = null;
    	String chainManagerLastName = null;
    	
    	try
		{
			ClientUI.chat.accept("");
			rs1 = ChatClient.selectWithParameters(selectQuery, brr);
			
			while(rs1.next())
			{
				chainManagerFirstName = rs1.getString("firstName");	
				chainManagerLastName = rs1.getString("lastName");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
    	// 2nd step : find out the name of the marketing manager that sent the massage
    	String marketingManagerFirstName = null;
    	String marketingManagerLastName = null;
    	
    	ArrayList<String> arr = new ArrayList<>();
    	arr.add(marketingManagerID);
    	
    	ResultSet rs = null;
    	
    	String query = "select firstName, lastName from employees where workerID = ?";
    	
    	try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(query, arr);
			
			while(rs.next())
			{
				marketingManagerFirstName = rs.getString("firstName");	
				marketingManagerLastName = rs.getString("lastName");
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
    	
    	String date = dateHeadline.getCellData(index).toString();
    	productName = productNameHeadline.getCellData(index).toString();
    	requestedRate = requestedRateHeadline.getCellData(index).toString();
    	
    	//3rd step : write the message inside the latter's paper
    	letterTextArea.setText( date + "\nFrom : " + marketingManagerFirstName + " " + marketingManagerLastName + "\n"
    			+ "Dear " + chainManagerFirstName + " " + chainManagerLastName + ", \nI want to change the rate of " + productName + " \nto be " 
    			+ requestedRate + " dollars \nThankYou" );
    }
    
    /**
     * handleConfirmButtonClick first checks if the request is not empty
     * if everything is fine, this function updates the requested rate to be the
     * new rate. (in the mySql and inside the form
     * @param event - this event occurs when the chain manager clicks on the confirm button
     */
    
    @FXML
    void handleConfirmButtonClick(ActionEvent event)
    {
    	//boolean canConfirm = true;
    	
    	// check 1 : the text area was empty
    	if(letterTextArea.getText().isEmpty())
    	{
    		JOptionPane.showMessageDialog(null,"Error : There was no massage to confirm","Error",JOptionPane.INFORMATION_MESSAGE);
    		//canConfirm = false;
    		return;
    	}
    	
    	// 1st step : update product table with the new rate
    	String updateQuery = "UPDATE products " + " SET rate = ? WHERE productName = ?";
    	ArrayList <String> members = new ArrayList<>();
    	
    	members.add(requestedRate);
    	members.add(productName);
    	
    	ClientUI.chat.accept("");
	    ChatClient.updateTable(updateQuery, members);
	    
	    // 2nd step : delete this handled request from the table
	    deleteRequestFromTable();
	    
	    letterTextArea.clear();
	    
	    // refresh tables
	    loadRateMessageTable();
		loadCurrentRatesTable();
    	
    	// show success message
    	JOptionPane.showMessageDialog(null,"Product rate successfully updated","Success Message",JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * deleteRequestFromTable deletes the selected rate request form the
     * mySql and from the table inside the form
     */
    
    public void deleteRequestFromTable()
    {
    	 String deleteQuery ="DELETE FROM ratesrequests WHERE marketingManagerID = ? and productName = ?";
 	    ArrayList <String> items = new ArrayList<>();
 	    
 	    items.add(marketingManagerID);
 	    items.add(productName);
 	    
     	ClientUI.chat.accept("");
     	ChatClient.updateTable(deleteQuery, items);
    }

    /**
     * handleRejectButtonClick first check that the selected request is not empty
     * if everything fine it deletes this request from the table
     * and shows a corresponding message
     * @param event - this event occurs when the chain manager clicks on the confirm button
     */
    
    @FXML
    void handleRejectButtonClick(ActionEvent event)
    {
    	// boolean canReject = true;
    	
    	// check 1 : the text area was empty
    	if(letterTextArea.getText().isEmpty())
    	{
    		JOptionPane.showMessageDialog(null,"Error : There was no massage to reject","Error",JOptionPane.INFORMATION_MESSAGE);
    		// canReject = false;
    		return;
    	}	
    	
    	letterTextArea.clear();

	    // 1st step : delete this handled request from the table
	    deleteRequestFromTable();
	    
	    // refresh table
	    loadRateMessageTable();
    	
	 // show reject message
    	JOptionPane.showMessageDialog(null,"Product rate was not changed \nRequset has been deleted","Reject Message",JOptionPane.INFORMATION_MESSAGE);
    }
    


}
