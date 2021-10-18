package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import entities.Requests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
/**
 * this class is a controller for GasStationManagerOrderRequests.fxml
 * the page contains a table where all requests to order are displayed for the station manager to approve or deny 
 * when the manager confirms the order the supplierName column in myfuel.requests is updated to supplier name he orders from
 * the delivered column is null because the supplier has not delivered yet the order
 */
public class GasStationManagerOrderRequestsC  implements Initializable {

    @FXML
    private TableView<Requests> requestsTable; 
    @FXML
    private TableColumn<Requests, String> productNameRequestsTable;
    @FXML
    private TableColumn<Requests, String> productAmountRequestsTable;
    @FXML
    private TableColumn<Requests, String> dateColumn;
// 
    @FXML
    private Button DeleteRequestButton;
    @FXML
    private ComboBox<String> supplierListComboBox; 
    @FXML
    private Button orderConfirmationButton; 
//
    @FXML
    private Label userWelcomeLabel;
    ArrayList<String>listorder=new ArrayList<String>(); 
    Date dateT;
    DateFormat dateFormat;
    String strDate;
    Alert alertError = new Alert(AlertType.ERROR);
    Alert alertSuccess = new Alert(AlertType.CONFIRMATION);
    Alert alertWarning = new Alert(AlertType.WARNING);
    String strStationName;
    String productNameToOrder,productAmountToOrder;
    ArrayList<String> al= new ArrayList<String>();
    Optional<ButtonType>result;
    ButtonType cancleButtonType = new ButtonType("Cancel",ButtonData.CANCEL_CLOSE);
    String dateBefore;
//
/**
 * function to handle the click on confirmation button
 * gas station manager gets to select a row in requests table to confirm or deny
 * 
 */
    @FXML
    void handleOrderConfirmationButton(ActionEvent event) {
    	  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	       LocalDateTime now = LocalDateTime.now();  
	       strDate= dtf.format(now); 
        	listorder.add(strDate); //date for query 
  	  		System.out.println("strDate" +strDate);
  	//????  		listorder.add(strStationName); //station name 
  	  		listorder.add(supplierListComboBox.getValue()); //supplier name to order from 
  	  		listorder.add("null");
     		listorder.add(productNameToOrder);  //product name to order for query
     		listorder.add(productAmountToOrder);  //amount to order for query
     		listorder.add(LoginControllerC.userin);
     		listorder.add(dateBefore);
	 
  			System.out.println(listorder);
	    	String selectQuery = "UPDATE myfuel.requests SET date=?,supplierName=?,delivered=? WHERE productName=? AND productAmount=? AND gasStationManager=? AND date=?";
	    	try
			{
				ClientUI.chat.accept("");
				ChatClient.updateTable(selectQuery, listorder); 
			} 
			catch (Exception e)
			{ 
				alertSuccess.setTitle("ERROR");
				alertSuccess.setHeaderText("");
				alertSuccess.setContentText("Sending order failed");
				alertSuccess.show();
				e.printStackTrace();
			}
			listorder.clear();  
			alertSuccess.setTitle("confirmation");
			alertSuccess.setHeaderText("");
			alertSuccess.setContentText("Sended order successfully");
			alertSuccess.show();
			
			refreshRequestsTable();
	    	supplierListComboBox.setDisable(true); //disable until request is clicked on
	    	orderConfirmationButton.setDisable(true);
	    	DeleteRequestButton.setDisable(true);
    }
   /**
    * when the station manager clicks the delete button
    * remove row/request from table and do not send order to supplier 
    */
    @FXML
    void handleDeleteRequestButton(ActionEvent event) {
    	al.add(productNameToOrder);
		al.add(productAmountToOrder);
		al.add(LoginControllerC.userin);
		al.add("null");
		al.add("null");
    	String selectQuery = "DELETE FROM myfuel.requests WHERE productName=? AND productAmount=? AND gasStationManager=? AND delivered=? AND supplierName=?";
		try
		{
			ClientUI.chat.accept("");
			ChatClient.updateTable(selectQuery, al);
		} 
		catch (Exception e)
		{
			alertSuccess.setTitle("ERROR");
			alertSuccess.setHeaderText("");
			alertSuccess.setContentText("deleting request failed");
			alertSuccess.show();
			e.printStackTrace();
			System.err.println("could not delete row"); 
			e.printStackTrace();
		}
		al.clear();   
		alertSuccess.setTitle("confirmation");
		alertSuccess.setHeaderText("");
		alertSuccess.setContentText("Deleted request successfully");
		alertSuccess.show();
		refreshRequestsTable();
		supplierListComboBox.setDisable(true); //disable until request is clicked on
    	orderConfirmationButton.setDisable(true);
    	DeleteRequestButton.setDisable(true);
    }
 /**
  * initialize combo box with suppliers list that work in this specific station
  * manager gets to choose the supplier before sending the order
  */
    @FXML
    void handleSupplierComboBox(ActionEvent event) {
    	orderConfirmationButton.setDisable(false);  //disable button until request is clicked on
    }
  /**
   * first function that runs when the page opens
   * disable delete and confirm buttons until user selects a row
   * initialize combo box of suppliers list from DB
   */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
    	DeleteRequestButton.setDisable(true);
    	getStationName();
    	refreshRequestsTable();
    	supplierListComboBox.setDisable(true); //disable until request is clicked on
    	orderConfirmationButton.setDisable(true);  //disable button until request is clicked on 
    	suppliersListComboBox();
	} 
   /**
    * load requests table from DB myfuel.requests
    * to display the requests table for user 
    */
    public void refreshRequestsTable()
   	{
       	ResultSet rs = null;
       	ArrayList<String> param=new ArrayList<String>();
   		String selectQuery = "SELECT date,productName,productAmount FROM myfuel.requests WHERE gasStationManager=? AND supplierName=? AND delivered=?";
        param.add(LoginControllerC.userin);
        param.add("null");
        param.add("null");
   		try
   		{
   			ClientUI.chat.accept("");
   			rs = ChatClient.selectWithParameters(selectQuery, param);
   		} 
   		catch (Exception e)
   		{
   			System.err.println("Error :client server problem");
   			e.printStackTrace();
   		}
   		ObservableList<Requests> oblist = FXCollections.observableArrayList();

   		try
   		{
   			while(rs.next())
   			{
   				oblist.add(new Requests(rs.getString("date"),rs.getString("productName"), rs.getString("productAmount"))); 
   			}
   		} 
   		catch (SQLException e)
   		{
   			e.printStackTrace();
   		}
   		productNameRequestsTable.setCellValueFactory(new PropertyValueFactory<Requests, String>("productName"));
   		productAmountRequestsTable.setCellValueFactory(new PropertyValueFactory<Requests, String>("productAmount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Requests, String>("date"));
   		requestsTable.setItems(oblist);
   		
   	} 
  
    public void suppliersListComboBox() {
    	ObservableList<String> suppliersList =FXCollections.observableArrayList( );
    	ResultSet r1 = null;
    	String querytry="Select username From myfuel.employees WHERE gasStation=? AND part=?";
    	ArrayList<String>param=new ArrayList<String>();
    	param.add(strStationName);
    	param.add("supplier"); 
    	try
		{
			ClientUI.chat.accept("");
			r1 = ChatClient.selectWithParameters(querytry, param); 
		} 
		catch (Exception e)
		{
			System.err.println("Error:client server problem");
			e.printStackTrace();
		} 
	    try {
	    	while(r1.next())
	    	{
	    		suppliersList.add(r1.getString("username")); 
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    supplierListComboBox.setItems(suppliersList);
    }
   /**
    * when the user selects a row 
    * enable delete button and suppliers list
    * enable confirm button only after user chooses a supplier
    */
    @FXML
    public void handleRowClickRequestsTable(MouseEvent event) {
    	DeleteRequestButton.setDisable(false);
    	orderConfirmationButton.setDisable(true);
    	supplierListComboBox.getSelectionModel().clearSelection();
    	supplierListComboBox.setDisable(false); 
 	   int index = requestsTable.getSelectionModel().getSelectedIndex();
 		
 		if(index <= -1)
 		{
 			return;
 		}  
 	   productNameToOrder=productNameRequestsTable.getCellData(index).toString();
 	   productAmountToOrder=productAmountRequestsTable.getCellData(index).toString();
 	  dateBefore= dateColumn.getCellData(index).toString();

    }
    public void getStationName()
    { 
    	ResultSet r1=null;
    	String querytry="Select gasStation From myfuel.employees WHERE username=?";
    	ArrayList<String>param=new ArrayList<String>();
    	param.add(LoginControllerC.userin); 
    	try
		{
			ClientUI.chat.accept("");
			 r1 = ChatClient.selectWithParameters(querytry, param); 
		} 
		catch (Exception e)
		{
			System.err.println("Error:client server problem");
			e.printStackTrace();
		} 
	    try {
	    	while(r1.next())
	    	{
	    		strStationName= r1.getString("gasStation"); 
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }   
  /**
   * all disablebutton functions disable the confirm and delete buttons 
   * also resets the combo box
   * every time user clicks somewhere other that a row on the table
   */
    @FXML
    void disableButton(MouseEvent event) {
    	orderConfirmationButton.setDisable(true);
    	supplierListComboBox.setDisable(true);
    	requestsTable.getSelectionModel().clearSelection();
    	supplierListComboBox.getSelectionModel().clearSelection();
    }  
    @FXML
    void disableButton4(MouseEvent event) {
    	orderConfirmationButton.setDisable(true);
    	supplierListComboBox.setDisable(true);
    	requestsTable.getSelectionModel().clearSelection();
    	supplierListComboBox.getSelectionModel().clearSelection();
    }
    @FXML
    void disableButton5(MouseEvent event) {
    	orderConfirmationButton.setDisable(true);
    	supplierListComboBox.setDisable(true);
    	requestsTable.getSelectionModel().clearSelection();
    	supplierListComboBox.getSelectionModel().clearSelection();
    }
    @FXML
    void disableButton6(MouseEvent event) {
    	orderConfirmationButton.setDisable(true);
    	supplierListComboBox.setDisable(true);
    	requestsTable.getSelectionModel().clearSelection();
    	supplierListComboBox.getSelectionModel().clearSelection();
    }
}
