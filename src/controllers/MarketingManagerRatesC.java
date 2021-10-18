package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import entities.Employee;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * this is a controller for MarketingManagerRates gui.
 * we enter this form when we click on the rates button in the marketing manager main window.
 * the form is responsible to send a rate request to the chain manager
 */

public class MarketingManagerRatesC implements Initializable
{
	String chosenWorkerID;
	String marketingManagerUsername = LoginControllerC.userin;
	String userGasStation = "";
	String oldRate;
	String maxPrice;
	String selectedChainManager;
	
    @FXML
    private Label nameLabel;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> productNameHeadline;

    @FXML
    private TableColumn<Product, String> rateHeadline;
    
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

    @FXML
    private TableView<Employee> chainManagerTable;

    @FXML
    private TableColumn<Employee, String> firstNameHeadline;

    @FXML
    private TableColumn<Employee, String> lastNameHeadline;

    @FXML
    private TableColumn<Employee, String> workerIDHeadline;
    
    @FXML
    private TableColumn<Employee, String> mailHeadline;

    @FXML
    private TableColumn<Employee, String> partHeadline;

    @FXML
    private TableColumn<Employee, String> gasStationHeadline;

    @FXML
    private TableColumn<Employee, String> usernameHeadline;

    @FXML
    private TableColumn<Employee, String> passwordHeadline;

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField rateTextField;

    @FXML
    private Button sendButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;
    
    /**
     * initialize : the function is responsible of 3 things
     * 1. find what is the current marketing manager gas station
     * 2. load all the information of this form
     * 3. set the relevant TextField to be disabled
     */
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
    	// functions
    	getMarketingManagerGasStationName();
    	loadProductsTable();
    	loadEmployeeTable();
    	
    	// fields
    	firstNameTextField.setDisable(true);
    	lastNameTextField.setDisable(true);
    	productNameTextField.setDisable(true);
    	rateTextField.setDisable(true);
    	
    	nameLabel.setText(marketingManagerUsername);
	}
    
    /**
     * getMarketingManagerGasStationName : the function is responsible
     * to update the current marketing manager gas station
     */
    
    public void getMarketingManagerGasStationName()
    {
    	ArrayList<String> brr = new ArrayList<>();
    	ResultSet gs = null;
    	
    	String query = "select gasStation from employees where username = ? ";
    	brr.add(marketingManagerUsername);	

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
     * loadEmployeeTable : the function is responsible to find out which
     * chain manager belongs to this gas station.
     * afterwards it put the information inside the employee's table
     * that i created inside the form
     */
    
    public void loadEmployeeTable()
    {
    	// part 1 : get the chain manager of this gas station
    	ResultSet rs = null;	
    	ArrayList<String> arr = new ArrayList<>();
    	ObservableList<Employee> oblist = FXCollections.observableArrayList();

		String selectQuery = "select * from employees where part = ? and gasStation = ? ";
		
		arr.add("chain manager"); // arr[0]
		arr.add(userGasStation); // arr[1]
		
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, arr);
			
			while(rs.next())
			{
				oblist.add(new Employee(rs.getString("firstName"), rs.getString("lastName"), rs.getString("workerID"), rs.getString("mail"), rs.getString("part"), rs.getString("gasStation"), rs.getString("username"), rs.getString("passwordun")));
			}
		}  
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerRates : client server problem");
			e.printStackTrace();
		}
		
		// part 2 : show information of the chain manager in the table
		firstNameHeadline.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
		lastNameHeadline.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
		workerIDHeadline.setCellValueFactory(new PropertyValueFactory<Employee, String>("workerID"));
		
		chainManagerTable.setItems(oblist);	
    }
    
    /**
     * loadProductsTable : the function is responsible to find the products
     * information that belong to this gas station.
     * afterwards it put the information inside the product's table
     * that i created inside the form
     */
    
    public void loadProductsTable()
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
		
		productNameHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
		rateHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("rate"));
		amountInStockHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("amountInStock"));
		statusHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("status"));
		thresholdLevelHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("thresholdLevel"));
		maxPriceHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("maxPrice"));
		gasStationNameHeadline.setCellValueFactory(new PropertyValueFactory<Product, String>("gasStationName"));
		
		productsTable.setItems(oblist);	
    }
    
    /**
     * handleChainManagerRowClick gets the information from this row the user chose.
     * @param event - this event occurs when the user clicks on a row in the employee table
     */

    @FXML
    void handleChainManagerRowClick(MouseEvent event)
    {
		int index = chainManagerTable.getSelectionModel().getSelectedIndex();
				
		if(index <= -1)
		{
			return;
		}
		
		firstNameTextField.setText(firstNameHeadline.getCellData(index).toString());
		firstNameTextField.setDisable(true);
		lastNameTextField.setText(lastNameHeadline.getCellData(index).toString());
		lastNameTextField.setDisable(true);
		
		chosenWorkerID = workerIDHeadline.getCellData(index).toString();
    }

    /**
     * handleProductRowClick gets the information from this row the user chose.
     * @param event - this event occurs when the user clicks on a row in the employee table
     */
    
    @FXML
    void handleProductRowClick(MouseEvent event)
    {
    	rateTextField.setDisable(false);
    	
    	int index = productsTable.getSelectionModel().getSelectedIndex();
		
		if(index <= -1)
		{
			return;
		}
		
		productNameTextField.setText(productNameHeadline.getCellData(index).toString());
		rateTextField.setText(rateHeadline.getCellData(index).toString());
		
		oldRate = rateTextField.getText();
		maxPrice = maxPriceHeadline.getCellData(index).toString();
    }

    /**
     * handleSendButtonClick is responsible of 2 things
     * 1. It checks if all fields are field before sending the rate
     * request to the chain manager.
     * while checking it sends a proper message for every scenario (error or success) 
     * 2. If all checks passed successfully - it save the relevant
     *  information in the "ratesrequests" table (in the mySQL)
     * @param event - this event occurs when the user clicks on the send button
     */
    
    @FXML
    void handleSendButtonClick(ActionEvent event)
    {
    	boolean toSend = true;
    	
    	// check 0 : all fields are field
    	if( productNameTextField.getText().isEmpty() || rateTextField.getText().isEmpty() || firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty() )
    	{
    		JOptionPane.showMessageDialog(null,"Error : There are some empty fields","Error",JOptionPane.INFORMATION_MESSAGE);
    		toSend = false;
    		return;
    	}
    	
    	String newRate = rateTextField.getText();
    	
    	int nRate = Integer.parseInt(newRate.trim());
    	int oRate = Integer.parseInt(oldRate.trim());
    	int mPrice = Integer.parseInt(maxPrice.trim());
    	
    	// check 1 : newRate equals oldRate
    	if( oRate == nRate )
    	{
    		// popup window
    		JOptionPane.showMessageDialog(null,"Error : old rate is equal to the new rate","Error",JOptionPane.INFORMATION_MESSAGE);
    		toSend = false;
    	}
    	
    	// check 2 : newRate is more expensive than maxPrice
    	if( nRate > mPrice )
    	{
    		// popup window
    		JOptionPane.showMessageDialog(null,"Error : new rate is bigger than max price","Error",JOptionPane.INFORMATION_MESSAGE);
        	toSend = false;
    	}
    	
    	// all checks passed successfully 
    	if( toSend )
    	{
    		String marketingManagerID = null;
    		ResultSet rs = null;	
        	ArrayList<String> brr = new ArrayList<>();
        	
        	// find marketingManagerID
    		String selectQuery = "select workerID from employees where username = ? ";
    		brr.add(marketingManagerUsername);
    		
    		try
    		{
    			ClientUI.chat.accept("");
    			rs = ChatClient.selectWithParameters(selectQuery, brr);	
    			
    			while(rs.next())
    			{
    				marketingManagerID = rs.getString("workerID");
    			}
    		} 
    		catch (Exception e)
    		{
    			System.err.println("Error : MarketingManagerRates : client server problem");
    			e.printStackTrace();
    		}
    		
    		// update table
    		ArrayList<String> crr = new ArrayList<>();
    		LocalDate date = LocalDate.now();
    		String query = "INSERT INTO ratesrequests (date, chainManagerID, marketingManagerID, productName, requestedRate) VALUES (?,?,?,?,?);";
			 
    		crr.add(date.toString());
    		crr.add(chosenWorkerID);
    		crr.add(marketingManagerID);
    		crr.add(productNameTextField.getText());
    		crr.add(newRate);
    		
    		try
    		{
    			ClientUI.chat.accept("");
    			ChatClient.updateTable(query, crr);
    		} 
    		catch (Exception e)
    		{
    			System.err.println("Error : MarketingManagerRates : client server problem");
    			e.printStackTrace();
    		}
    		
    		// popup window
    		JOptionPane.showMessageDialog(null,"The message was sent successfuly to the chain manager","Success message",JOptionPane.INFORMATION_MESSAGE);
    	}
    }
}
