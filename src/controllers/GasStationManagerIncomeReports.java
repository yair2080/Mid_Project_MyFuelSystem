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
import entities.Income;
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
 * this class is a controller for GasStationManagerIncomeReports.fxml
 * brings data from DB myfuel.purchases where we have all the items the station sold to clients
 * incomeTable is the table where we display all products and total amount that we sold for 3 months
 * getStationName function sets the String stationName to the name of the station where the manager works at
 * labelMonths is for displaying at the fxml file the current quarter we are working with
 */
public class GasStationManagerIncomeReports implements Initializable {

    @FXML
    private TableView<Income> incomeTable;
    @FXML
    private TableColumn<Income, String> productColumnIncomeTable;
    @FXML
    private TableColumn<Income, String> totalColumnIncomeTable; 
    @FXML
    private TableColumn<Income, String> dateColumn; 
    @FXML
    private Label labelMonths; 
    
    String stationName; 
    ArrayList<String> param=new ArrayList<String>();
    String m1,m2; 	 //months as strings
    int n1,n2,n;		 //months as integer
    String date;
    Calendar todayDate; 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getDate();
		getStationName();
		if(n==4)refreshIncomeTableJanToMarch(); 
		if(n==7) refreshIncomeTableAprilToJune();
		if(n==10) refreshIncomeTableJulyToSep();
		if(n==1) refreshIncomeTableOctToDec();
		labelMonths.setText(m1 + "-" + m2);
		labelMonths.setAlignment(Pos.CENTER);
	}
	/**
	 * getData function checks the date 
	 * stores the month (int) in variable n 
	 * station manager can generate all reports only at the end of every quarter
	 * for example ,at the start of April 1/4 or 1/7 or 1/10 or 1/1
	 * initialize function sets the month label according to getData
	 * Initialize the n variable to 7 so the reports work properly as if today is 1/7 and we can generate reports of april to june
	 */
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
	/**
	 * refreshIncome functions get the relevant data from DB to the page 
	 * all income for the specific quarter is displayed for the user
	 * total income is calculated for every month in that quarter and every product the station sold
	 */	
	public void refreshIncomeTableAprilToJune()
	{ 
		param.add(stationName);  
		ResultSet rs = null ; 
		String selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-04-%%'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		ObservableList<Income> oblist = FXCollections.observableArrayList();
		try
		{  while(rs.next()) {
			if(rs.getString("total")!=null)
				oblist.add(new Income("Diesel",rs.getString("total"),"2020-04"));   
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-04-%%'";
		try
		{ ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next()) 
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-04"));  
			
		} 
		catch (SQLException e) { e.printStackTrace(); }
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-04-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  	while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-04"));   
			 
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-04-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-04"));   
		} 
		catch (SQLException e) { e.printStackTrace(); }  
		rs = null ;  
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-05-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next()) 
			 if(rs.getString("total")!=null)
				 oblist.add(new Income("Diesel",rs.getString("total"),"2020-05"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-05-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-05"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-05-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-05"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-05-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-05"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-06-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("Diesel",rs.getString("total"),"2020-06"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-06-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-06"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-06-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-06"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-06-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  {
			System.out.println(rs.getString("total"));
			 if(rs.getString("total")!=null)
					 oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-06"));   
			}
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		 
		productColumnIncomeTable.setCellValueFactory(new PropertyValueFactory<Income, String>("fuelType")); 
		totalColumnIncomeTable.setCellValueFactory(new PropertyValueFactory<Income, String>("total")); 
		dateColumn.setCellValueFactory(new PropertyValueFactory<Income, String>("date"));
		incomeTable.setItems(oblist);
	}
	public void refreshIncomeTableJanToMarch()
	{ 
		param.add(stationName);  
		ResultSet rs = null ; 
		String selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-01-%%'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		ObservableList<Income> oblist = FXCollections.observableArrayList();
		try
		{  while(rs.next()) {
			if(rs.getString("total")!=null)
				oblist.add(new Income("Diesel",rs.getString("total"),"2020-01"));   
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-01-%%'";
		try
		{ ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next()) 
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-01"));  
			
		} 
		catch (SQLException e) { e.printStackTrace(); }
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-01-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  	while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-01"));   
			 
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-01-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-01"));   
		} 
		catch (SQLException e) { e.printStackTrace(); }  
		rs = null ;  
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-02-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next()) 
			 if(rs.getString("total")!=null)
				 oblist.add(new Income("Diesel",rs.getString("total"),"2020-02"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-02-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-02"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-02-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-02"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-02-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-02"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-03-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("Diesel",rs.getString("total"),"2020-03"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-03-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-03"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-03-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-03"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-03-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  {
			System.out.println(rs.getString("total"));
			 if(rs.getString("total")!=null)
					 oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-03"));   
			}
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		 
		productColumnIncomeTable.setCellValueFactory(new PropertyValueFactory<Income, String>("fuelType")); 
		totalColumnIncomeTable.setCellValueFactory(new PropertyValueFactory<Income, String>("total")); 
		dateColumn.setCellValueFactory(new PropertyValueFactory<Income, String>("date"));
		incomeTable.setItems(oblist);
	}
	public void refreshIncomeTableJulyToSep()
	{ 
		param.add(stationName);  
		ResultSet rs = null ; 
		String selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-07-%%'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		ObservableList<Income> oblist = FXCollections.observableArrayList();
		try
		{  while(rs.next()) {
			if(rs.getString("total")!=null)
				oblist.add(new Income("Diesel",rs.getString("total"),"2020-07"));   
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-07-%%'";
		try
		{ ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next()) 
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-07"));  
			
		} 
		catch (SQLException e) { e.printStackTrace(); }
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-07-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  	while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-07"));   
			 
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-07-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-07"));   
		} 
		catch (SQLException e) { e.printStackTrace(); }  
		rs = null ;  
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-08-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next()) 
			 if(rs.getString("total")!=null)
				 oblist.add(new Income("Diesel",rs.getString("total"),"2020-08"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-08-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-08"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-08-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-08"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-08-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-08"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-09-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("Diesel",rs.getString("total"),"2020-09"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-09-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-09"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-09-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-09"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-09-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  {
			System.out.println(rs.getString("total"));
			 if(rs.getString("total")!=null)
					 oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-09"));   
			}
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		 
		productColumnIncomeTable.setCellValueFactory(new PropertyValueFactory<Income, String>("fuelType")); 
		totalColumnIncomeTable.setCellValueFactory(new PropertyValueFactory<Income, String>("total")); 
		dateColumn.setCellValueFactory(new PropertyValueFactory<Income, String>("date"));
		incomeTable.setItems(oblist);
	}
	public void refreshIncomeTableOctToDec()
	{ 
		param.add(stationName);  
		ResultSet rs = null ; 
		String selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-10-%%'";
		try
		{	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		ObservableList<Income> oblist = FXCollections.observableArrayList();
		try
		{  while(rs.next()) {
			if(rs.getString("total")!=null)
				oblist.add(new Income("Diesel",rs.getString("total"),"2020-10"));   
			}
		} 
		catch (SQLException e) { e.printStackTrace(); }
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-10-%%'";
		try
		{ ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next()) 
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-10"));  
			
		} 
		catch (SQLException e) { e.printStackTrace(); }
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-10-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  	while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-10"));   
			 
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-10-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-10"));   
		} 
		catch (SQLException e) { e.printStackTrace(); }  
		rs = null ;  
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-11-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next()) 
			 if(rs.getString("total")!=null)
				 oblist.add(new Income("Diesel",rs.getString("total"),"2020-11"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-11-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-11"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-11-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-11"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-11-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-11"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Diesel' AND stationName=? AND date LIKE '2020-12-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("Diesel",rs.getString("total"),"2020-12"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='Gasoline' AND stationName=? AND date LIKE '2020-12-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())   
			if(rs.getString("total")!=null)
				oblist.add(new Income("Gasoline",rs.getString("total"),"2020-12"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='ScooterFuel' AND stationName=? AND date LIKE '2020-12-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  
			if(rs.getString("total")!=null)
				oblist.add(new Income("ScooterFuel",rs.getString("total"),"2020-12"));   
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		rs = null ; 
		selectQuery = "SELECT SUM(totalPrice) AS total FROM myfuel.purchases WHERE fuelType='HomeHeatingFuel' AND stationName=? AND date LIKE '2020-12-%%'";
		try
		{ 	ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{  while(rs.next())  {
			System.out.println(rs.getString("total"));
			 if(rs.getString("total")!=null)
					 oblist.add(new Income("HomeHeatingFuel",rs.getString("total"),"2020-12"));   
			}
		} 
		catch (SQLException e) { e.printStackTrace(); } 
		 
		productColumnIncomeTable.setCellValueFactory(new PropertyValueFactory<Income, String>("fuelType")); 
		totalColumnIncomeTable.setCellValueFactory(new PropertyValueFactory<Income, String>("total")); 
		dateColumn.setCellValueFactory(new PropertyValueFactory<Income, String>("date"));
		incomeTable.setItems(oblist);
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
	    		System.out.println(stationName); 
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
