package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import entities.ItemInStock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * this class is a controller for GasStationManagerStockReports.fxml
 * stockTable displays all the stock we had at the end of every month at the relevant quarter
 * 
 */
public class GasStationManagerStockReports implements Initializable {

    @FXML
    private TableView<ItemInStock> stockTable;
    @FXML
   private TableColumn<ItemInStock, String> productColumnStockTable;
    @FXML
    private TableColumn<ItemInStock, String> quantityInStockColumnStockTable;
    @FXML
    private TableColumn<ItemInStock, String> dateStock; 
    @FXML
    private Label monthsLabel;
    String m1,m2; 	 //months as strings
    int n;		 //months as integer
    String date,stationName;
    Calendar todayDate; 
    ArrayList<String>param=new ArrayList<String>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getDate();
		getStationName();
		monthsLabel.setText(m1+"-"+m2);  
		monthsLabel.setAlignment(Pos.CENTER);
		param.add(stationName);
		if(n==4) refreshStockTableJanToMarch(); 
		if(n==7) refreshStockTableAprilToJune();
		if(n==10) refreshStockTableJulyToSep();
		if(n==1) refreshStockTableOctToDec();
		
	}
	/**
	 * refreshIncome functions get the relevant data from DB to the page 
	 * all stock for the specific quarter is displayed for the user
	 * all stocks for every end of month are saved in DB so that the manager can generate them every quarter
 	 */
	public void refreshStockTableJanToMarch()
	{ 
		ResultSet rs = null ; 
		String selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='1'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		ObservableList<ItemInStock> oblist = FXCollections.observableArrayList();
		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-01"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
	// 
		rs = null ; 
		selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='2'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
 		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-02"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
	// 
		rs = null ; 
		selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='3'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
 		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-03"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		
		productColumnStockTable.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("productName")); 
		quantityInStockColumnStockTable.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("productAmount")); 
		dateStock.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("date"));
		stockTable.setItems(oblist);
	}
	public void refreshStockTableAprilToJune()
	{
		ResultSet rs = null ; 
		String selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='4'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		ObservableList<ItemInStock> oblist = FXCollections.observableArrayList();
		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-04"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
	// 
		rs = null ; 
		selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='5'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
 		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-05"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
	// 
		rs = null ; 
		selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='6'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
 		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-06"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		
		productColumnStockTable.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("productName")); 
		quantityInStockColumnStockTable.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("productAmount")); 
		dateStock.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("date"));
		stockTable.setItems(oblist);
	}
	public void refreshStockTableJulyToSep()
	{
		ResultSet rs = null ; 
		String selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='7'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		ObservableList<ItemInStock> oblist = FXCollections.observableArrayList();
		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-07"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
	// 
		rs = null ; 
		selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='8'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
 		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-08"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
	// 
		rs = null ; 
		selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='9'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
 		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-09"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		
		productColumnStockTable.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("productName")); 
		quantityInStockColumnStockTable.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("productAmount")); 
		dateStock.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("date"));
		stockTable.setItems(oblist);
	}
	public void refreshStockTableOctToDec()
	{
		ResultSet rs = null ; 
		String selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='10'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		ObservableList<ItemInStock> oblist = FXCollections.observableArrayList();
		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-10"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
	// 
		rs = null ; 
		selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='11'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
 		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-11"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
	// 
		rs = null ; 
		selectQuery = "SELECT productName,productAmount FROM myfuel.quartelystock WHERE stationName=? AND endOfMonth='12'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
 		try
		{  while(rs.next()) { 
				oblist.add(new ItemInStock(rs.getString("productName"),rs.getString("productAmount"),"2020-12"));
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		
		productColumnStockTable.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("productName")); 
		quantityInStockColumnStockTable.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("productAmount")); 
		dateStock.setCellValueFactory(new PropertyValueFactory<ItemInStock, String>("date"));
		stockTable.setItems(oblist);
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
	    		stationName= r1.getString("gasStation");  
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getDate()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	    LocalDateTime now = LocalDateTime.now();  
	    date= dtf.format(now); 
		todayDate = new GregorianCalendar(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
		System.out.println(now.getMonthValue());
		n = now.getMonthValue();
		n=7;  
		if(n==4) //meaning its 1/4/2020. can generate jan=march
		{
			m1="January";m2="March";
		}
		if(n==7) //meaning its 1/7/2020
		{
			m1="April";m2="June";
		}
		if(n==10) //meaning its 1/10/2020
		{
			m1="July";m2="September";
		}
		if(n==1) //meaning its 1/7/2020
		{
			m1="October";m2="December";
		}
	}

}
