package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
/**
 * this class is a controller for GasStationManagerStock.fxml
 * the current stock at the station in brought up from the DB myfuel.stock to the stockTable
 * the manager gets to select a row from the table and change the threshold of the product he chose 
 * the user/station manager gets to write on the changeThresholdTextField after he selects a row to change
 * the update button updates the new threshold and refreshes the table to get the new value at the table 
 * selectLabel displays an information an what the user can do at this page, the label is visible only when user gets the mouse on the button"?" 
 * dateLabel displays the current date 
 * if its the end of the month the save button is enabled so that the manager saves the stock every end of month
 * saving the stock is for using it later when generating the stock report
 */
public class GasStationManagerStock implements Initializable { 
	
	@FXML
    private Label userWelcomeLabel;
    @FXML
    private TableView<Product> stockTable;
    @FXML
    private TableColumn<Product, String> productNameTableColumn;
    @FXML
    private TableColumn<Product, String> priceTableColumn;
    @FXML
    private TableColumn<Product, String> amountInStockTableColumn;
    @FXML
    private TableColumn<Product, String> productStatusTableColumn;
    @FXML
    private TableColumn<Product, String> alertThresholdTableColumn;
    @FXML
    private TableColumn<Product, String> maxPriceTableColumn;
    @FXML
    private Button updateButton;
    @FXML
    private TextField changeThresholdTextField;
    @FXML
    private TextField managerNameTextField;
    @FXML
    private Label selectLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Button saveStockButton;
  
    String strStationName;
    String str;
    ObservableList<String> oblist; 
    ArrayList<String> param11=new ArrayList<String>();
    String chosenProductToUpdate;
    Alert alertError = new Alert(AlertType.ERROR);
    Alert alertSuccess = new Alert(AlertType.CONFIRMATION);
    String newThreshold=null;
    String value;
    ResultSet rs1=null; 
    LocalDateTime now11;
   
    String stockGasoline,stockDiesel,stockScooter,stockHome;
    
   /**
    * when the user selects a row 
    * enable the text field for the user to insert the new threshold value 
    */
    @FXML 
    void handleRowClick(MouseEvent event) { 
    	changeThresholdTextField.setDisable(false);
    	int index = stockTable.getSelectionModel().getSelectedIndex();
		
		if(index <= -1)
		{
			return;
		} 
		chosenProductToUpdate=productNameTableColumn.getCellData(index).toString();  
    } 
   /**
    * enable the update button when user wants to write the new threshold
    */
    @FXML
    void handleChangeClick(MouseEvent event) {
    	updateButton.setDisable(false);
    }
  /**
   * checks if the inserted value is a valid, non-negative number
   * updates the threshold of the selected product in the DB  
   * alerts the user that the threshold is now updated
   * refreshs the table to display the new threshold value
   */
    @FXML
    void handleUpdateThresholdButton(ActionEvent event) {
    	
    	int erro=0;
    	newThreshold= changeThresholdTextField.getText(); 
	    	try 
	        { 
	    		 double value = Integer.parseInt(newThreshold); //check if threshold is negative number
	    		 System.out.println(value);
	    		 if(value<0)
	    		 {
	    			 alertError.setTitle("Error");
				     alertError.setHeaderText("");
				     alertError.setContentText("Updating threshold level failed\nCan not insert negative number");
					 alertError.show();
					 erro=1;
	    		 } 
	        }  
	        catch (NumberFormatException e)  
	        { 
	            System.out.println(newThreshold + " is not a valid integer number"); 
	            alertError.setTitle("Error");
			    alertError.setHeaderText("");
			    alertError.setContentText("Updating threshold level failed\nPlease insert a number");
			    alertError.show();
				erro=1;
	        }  
    	if(erro==0)
    	{
	    	if(newThreshold.equals("")) //empty text field
		    {
		    	alertError.setTitle("Error");
				alertError.setHeaderText("");
				alertError.setContentText("insert new threshold level\nField cannot be empty");
				alertError.show();
	    	} 
	    		
	    	else 
	    	{   
		    	ArrayList<String>param=new ArrayList<String>();
		    	param.add(newThreshold);
		    	param.add(chosenProductToUpdate);
		    	
		    	param.add(strStationName); 
		    	String query="UPDATE myfuel.products SET thresholdLevel=? WHERE productName=? AND gasStationName=?"; 
		    	try
				{
					ClientUI.chat.accept("");
					ChatClient.updateTable(query, param); 
					checkIfDidUpdateTable();
						
				} 
				catch (Exception e)
				{
					System.err.println("Error : could not update");
					alertError.setTitle("Error");
					alertError.setHeaderText("");
					alertError.setContentText("Updating threshold level failed\n exception");
					alertError.show();
					e.printStackTrace();
				} 
	    	}
    	}
    	chosenProductToUpdate= null;
    	changeThresholdTextField.setText("");
    	updateButton.setDisable(true);
    	changeThresholdTextField.setDisable(true);
    	refreshStockTable();
    }
	/**
	 * a function to make sure to not alert the user that threshold was updated if i was not
	 */
    public void checkIfDidUpdateTable() {
		try   //check if it did update table 
		{
			ArrayList<String>temp=new ArrayList<String>();
			temp.add(chosenProductToUpdate); 
			temp.add(strStationName);
			ClientUI.chat.accept("");
			rs1 = ChatClient.selectWithParameters("SELECT thresholdLevel FROM myfuel.products WHERE productName=? AND gasStationName=?", temp);
			
		} 
		catch (Exception e)
		{
			System.err.println("Error : client server problem");
			e.printStackTrace();
		} 
		try {
			while(rs1.next())
			{
				value= rs1.getString("thresholdLevel");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		if(value.equals(newThreshold))
		{
			alertSuccess.setTitle("confirmation");
			alertSuccess.setHeaderText("");
			alertSuccess.setContentText("Updated threshold level successfully");
			alertSuccess.show();
		}
		else
		{
			alertError.setTitle("Error");
			alertError.setHeaderText("");
			alertError.setContentText("Updating threshold level failed");
			alertError.show(); 
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
		 LocalDateTime now = LocalDateTime.now();  
		 now11=now;
	     String date= dtf.format(now); 
	    dateLabel.setText(date);	
	    enebleSaveStockButtonOrNot();
		selectLabel.setVisible(false);
		changeThresholdTextField.setDisable(true);
		updateButton.setDisable(true);
		getStationName();
     	refreshStockTable();
	}
	/**
	 * stores the stock of last month in DB
	 * gets current amount from products table and stores in quartelyStock
	 */
	 @FXML
	 void handleSaveStockButton(ActionEvent event) {
		 stockGasoline= getAmountOf("gasoline"); 		 
		 stockDiesel=getAmountOf("diesel"); 		 
		 stockScooter= getAmountOf("scooterFuel"); 		 
		 stockHome=getAmountOf("homeHeatingFuel");  
		 updateStock("Gasoline",stockGasoline); 
		 alertSuccess.setTitle("Confirmation");
		 alertSuccess.setHeaderText("");
		 alertSuccess.setContentText("Saved stock");
		 alertSuccess.show();
	 } 
	 public String getAmountOf(String fuel)
	 {
		 String amount="0";
		 ArrayList<String> f=new ArrayList<String>();
		 f.add(fuel);
		 f.add(strStationName);
		 ResultSet r1 = null;
		 String querytry="SELECT amountInStock myfuel.products WHERE productName=?,gasStationName=?"; 
			try
			{
				ClientUI.chat.accept("");
				r1 = ChatClient.selectWithParameters(querytry, f); 
			} 
			catch (Exception e)
			{
				System.err.println("Error : client server problem");
				e.printStackTrace();
			} 
		    try {
		    	while(r1.next())
		    	{
		    		amount=r1.getString("amountInStock");
		    	}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return amount;
	 }
	 public void updateStock(String fuel,String amount)
	 {	 
		 	ArrayList<String>param=new ArrayList<String>();
		 	param.add(fuel);
		 	param.add(amount);
		 	Date date = Calendar.getInstance().getTime();
		 	DateFormat dateFormat = new SimpleDateFormat("mm");
		 	String strMonth = dateFormat.format(date);
		 	System.out.println(strMonth);
		 	param.add(strMonth);
		 	param.add(strStationName);
		 	ResultSet r1 = null;
	 		String querytry="UPDATE myfuel.quartelystock SET productName=?,productAmount=? WHERE endOfMonth=? AND stationName=?"; 
			try
			{
				ClientUI.chat.accept("");
				r1 = ChatClient.selectWithParameters(querytry, param); 
			} 
			catch (Exception e)
			{
				alertError.setTitle("Error");
				alertError.setHeaderText("");
				alertError.setContentText("saving stock failed");
				alertError.show();
				System.err.println("Error : client server problem");
				e.printStackTrace();
			} 
		    try {
		    	while(r1.next())
		    	{
		    		strStationName= r1.getString("gasStation");
		    		System.out.println(strStationName);
		    		param11.add(strStationName);
		    	}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	public void enebleSaveStockButtonOrNot()
	{ 
		
		if((now11.getDayOfMonth()==31 && now11.getMonthValue()==1)||(now11.getDayOfMonth()==29 && now11.getMonthValue()==2) ||(now11.getDayOfMonth()==31 && now11.getMonthValue()==3)||(now11.getDayOfMonth()==30 && now11.getMonthValue()==4)||(now11.getDayOfMonth()==31 && now11.getMonthValue()==5)||(now11.getDayOfMonth()==30 && now11.getMonthValue()==6)||(now11.getDayOfMonth()==31 && now11.getMonthValue()==7)||(now11.getDayOfMonth()==31 && now11.getMonthValue()==8)||(now11.getDayOfMonth()==30 && now11.getMonthValue()==9)||(now11.getDayOfMonth()==31 && now11.getMonthValue()==10)||(now11.getDayOfMonth()==30 && now11.getMonthValue()==11)||(now11.getDayOfMonth()==31 && now11.getMonthValue()==12)) //start of next month
			saveStockButton.setDisable(false); //let manager save stock of the last day of the month
		else
			saveStockButton.setDisable(true);
	}
	public void getStationName()
	{
		ResultSet r1 = null;
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
			System.err.println("Error : get message table : client server problem");
			e.printStackTrace();
		} 
	    try {
	    	while(r1.next())
	    	{
	    		strStationName= r1.getString("gasStation");
	    		System.out.println(strStationName);
	    		param11.add(strStationName);
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * brings all stock data from DB to page
	 */
	public void refreshStockTable()  
		{
	     	ResultSet rs = null ; 
			String selectQuery = "SELECT productName,rate,amountInStock,status,thresholdLevel,maxPrice FROM myfuel.products WHERE gasStationName=?";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param11);
			} 
			catch (Exception e)
			{
				System.err.println("Error : client server problem");
				e.printStackTrace();
			}
			ObservableList<Product> oblist = FXCollections.observableArrayList();

			try
			{
				while(rs.next())
				{
					oblist.add(new Product(rs.getString("productName"),rs.getString("rate"),rs.getString("amountInStock"), rs.getString("status"),rs.getString("thresholdLevel"),rs.getString("maxPrice")));
				}
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			
			productNameTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productName")); 
			priceTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("rate"));
			amountInStockTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("amountInStock"));
			productStatusTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("status"));
			alertThresholdTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("thresholdLevel"));
			maxPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("maxPrice"));
			
			stockTable.setItems(oblist);
			
		} 
	/**
	 * hideMsg and showMsg show a description about what the user can opperate in that page 
	 * the msg is shown only when the user gets his mouse on the button
	 */
	@FXML
	void hideMsg(MouseEvent event) {
		 selectLabel.setVisible(false);
	 }
	@FXML
	void showMsg(MouseEvent event) {
		 selectLabel.setVisible(true);
	}
	/**
	 * disableButton functions disable the update button when user clicks away from the table
	 */ 
    @FXML
    void disableButton3(MouseEvent event) {
    	updateButton.setDisable(true);
    	stockTable.getSelectionModel().clearSelection();
    	changeThresholdTextField.setDisable(true);
    } 
    @FXML
    void disableButton4(MouseEvent event) {
    	updateButton.setDisable(true);
    	stockTable.getSelectionModel().clearSelection();
    	changeThresholdTextField.setDisable(true);
    } 
    @FXML
    void disableButton5(MouseEvent event) {
    	updateButton.setDisable(true);
    	stockTable.getSelectionModel().clearSelection();
    	changeThresholdTextField.setDisable(true);
    } 
}
