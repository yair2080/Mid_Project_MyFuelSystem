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
import entities.Purchase;
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
 * this class is a controller for GasStationManagerPurchasesReports.fxml
 * brings data from DB myfuel.requests where we have all the items the station order from suppliers
 * the column delivered in DB myfuel.requests represent if the supplier has supplied the order
 * when supplier deny the order he can delete the row from the table
 * supplierName column in DB myfuel.requests represents if the order was sent to supplier 
 * supplierName column contains null when the request to order is sent to station manager 
 * when the station manager approves the order he updates the supplierName column to supplier he wants to order from
 * purchasesTable is the table where we display all products and total amount that we ordered for 3 months
 * getStationName function sets the String stationName to the name of the station where the manager works at
 */
public class GasStationManagerPurchasesReports implements Initializable{  

    @FXML
    private TableView<Purchase> purchasesTable;
    @FXML
    private TableColumn<Purchase, String> productColumnPurchasesTable;
    @FXML
    private TableColumn<Purchase, String> totalColumn;
    @FXML
    private TableColumn<Purchase, String> datePurchases; 
    @FXML
    private Label monthsLabel;
    String strStationName;
    ArrayList<String>param=new ArrayList<String>();
    String m1,m2; 	 //months as strings
    int n;		 //months as integer
    String date;
    Calendar todayDate; 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getDate();
		monthsLabel.setText(m1+"-"+m2);
		monthsLabel.setAlignment(Pos.CENTER);
		getStationName();
		param.add(LoginControllerC.userin); 
		if(n==4)refreshStockTableMonthJanToMarch(); 
		if(n==7) refreshStockTableMonthAprilToJune();
		if(n==10) refreshStockTableMonthJulyToSep();
		if(n==1) refreshStockTableMonthOctoberToDec();
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
		n=7;  /////////////////changed
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
	 * all purchases for the specific quarter is displayed for the user
	 * total purchases is calculated for every month in that quarter and every product the station bought
	 */	
	public void refreshStockTableMonthAprilToJune()
	{ 
		ObservableList<Purchase> oblist = FXCollections.observableArrayList();
		ResultSet rs = null ; 
		String selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-04-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param); 
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{
			while(rs.next())
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-04")); 
		} 
		catch (SQLException e) { e.printStackTrace(); }
	//
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-04-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-04")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-04-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-04")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-04-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-04")); 
		}
		catch (SQLException e) { e.printStackTrace();}
	//	 
		 rs = null ; 
		 selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-05-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param); 
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{
			while(rs.next())
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-05")); 
		} 
		catch (SQLException e) { e.printStackTrace(); }
	//
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-05-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-05")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-05-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-05")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-05-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-05")); 
		}
		catch (SQLException e) { e.printStackTrace();}
	//
		 rs = null ; 
		 selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-06-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param); 
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{
			while(rs.next())
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-06")); 
		} 
		catch (SQLException e) { e.printStackTrace(); }
	//
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-06-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-06")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-06-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-06")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-06-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-06")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		
		 
		productColumnPurchasesTable.setCellValueFactory(new PropertyValueFactory<Purchase, String>("productName")); 
		totalColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("total"));
	 	datePurchases.setCellValueFactory(new PropertyValueFactory<Purchase, String>("date")); 
	 	
		purchasesTable.setItems(oblist); 
	} 
	public void refreshStockTableMonthJulyToSep()
	{ 
			ObservableList<Purchase> oblist = FXCollections.observableArrayList();
			ResultSet rs = null ; 
			String selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-07-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param); 
			} 
			catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
			try
			{
				while(rs.next())
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-07")); 
			} 
			catch (SQLException e) { e.printStackTrace(); }
		//
			rs = null ; 
			selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-07-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param);
			} 
			catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
			try 
			{
				while(rs.next()) 
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-07")); 
			}
			catch (SQLException e) { e.printStackTrace();}
			rs = null ; 
			selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-07-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param);
			} 
			catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
			try 
			{
				while(rs.next()) 
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-07")); 
			}
			catch (SQLException e) { e.printStackTrace();}
			rs = null ; 
			selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-07-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param);
			} 
			catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
			try 
			{
				while(rs.next()) 
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-07")); 
			}
			catch (SQLException e) { e.printStackTrace();}
		//	 
			 rs = null ; 
			 selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-08-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param); 
			} 
			catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
			try
			{
				while(rs.next())
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-08")); 
			} 
			catch (SQLException e) { e.printStackTrace(); }
		//
			rs = null ; 
			selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-08-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param);
			} 
			catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
			try 
			{
				while(rs.next()) 
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-08")); 
			}
			catch (SQLException e) { e.printStackTrace();}
			rs = null ; 
			selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-08-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param);
			} 
			catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
			try 
			{
				while(rs.next()) 
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-08")); 
			}
			catch (SQLException e) { e.printStackTrace();}
			rs = null ; 
			selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-08-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param);
			} 
			catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
			try 
			{
				while(rs.next()) 
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-08")); 
			}
			catch (SQLException e) { e.printStackTrace();}
		//
			 rs = null ; 
			 selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-09-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param); 
			} 
			catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
			try
			{
				while(rs.next())
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-09")); 
			} 
			catch (SQLException e) { e.printStackTrace(); }
		//
			rs = null ; 
			selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-09-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param);
			} 
			catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
			try 
			{
				while(rs.next()) 
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-09")); 
			}
			catch (SQLException e) { e.printStackTrace();}
			rs = null ; 
			selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-09-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param);
			} 
			catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
			try 
			{
				while(rs.next()) 
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-09")); 
			}
			catch (SQLException e) { e.printStackTrace();}
			rs = null ; 
			selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-09-%%' AND delivered='yes'";
			try
			{
				ClientUI.chat.accept("");
				rs = ChatClient.selectWithParameters(selectQuery, param);
			} 
			catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
			try 
			{
				while(rs.next()) 
					if(rs.getString("total")!=null)
						oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-09")); 
			}
			catch (SQLException e) { e.printStackTrace();}
			
			 
			productColumnPurchasesTable.setCellValueFactory(new PropertyValueFactory<Purchase, String>("productName")); 
			totalColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("total"));
		 	datePurchases.setCellValueFactory(new PropertyValueFactory<Purchase, String>("date")); 
		 	
			purchasesTable.setItems(oblist); 
		} 	
	public void refreshStockTableMonthOctoberToDec()
	{
		ObservableList<Purchase> oblist = FXCollections.observableArrayList();
		ResultSet rs = null ; 
		String selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-10-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param); 
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{
			while(rs.next())
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-10")); 
		} 
		catch (SQLException e) { e.printStackTrace(); }
	//
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-10-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-10")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-10-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-10")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-10-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-10")); 
		}
		catch (SQLException e) { e.printStackTrace();}
	//	 
		 rs = null ; 
		 selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-11-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param); 
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{
			while(rs.next())
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-11")); 
		} 
		catch (SQLException e) { e.printStackTrace(); }
	//
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-11-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-11")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-11-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-11")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-11-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-11")); 
		}
		catch (SQLException e) { e.printStackTrace();}
	//
		 rs = null ; 
		 selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-12-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param); 
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{
			while(rs.next())
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-12")); 
		} 
		catch (SQLException e) { e.printStackTrace(); }
	//
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-12-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-12")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-12-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-12")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-12-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-12")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		
		 
		productColumnPurchasesTable.setCellValueFactory(new PropertyValueFactory<Purchase, String>("productName")); 
		totalColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("total"));
	 	datePurchases.setCellValueFactory(new PropertyValueFactory<Purchase, String>("date")); 
	 	
		purchasesTable.setItems(oblist);
	} 	
	public void refreshStockTableMonthJanToMarch()
	{
		ObservableList<Purchase> oblist = FXCollections.observableArrayList();
		ResultSet rs = null ; 
		String selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-01-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param); 
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{
			while(rs.next())
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-01")); 
		} 
		catch (SQLException e) { e.printStackTrace(); }
	//
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-01-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-01")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-01-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-01")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-01-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-01")); 
		}
		catch (SQLException e) { e.printStackTrace();}
	//	 
		 rs = null ; 
		 selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-02-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param); 
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{
			while(rs.next())
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-02")); 
		} 
		catch (SQLException e) { e.printStackTrace(); }
	//
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-02-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-02")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-02-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-02")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-02-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-02")); 
		}
		catch (SQLException e) { e.printStackTrace();}
	//
		 rs = null ; 
		 selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Gasoline'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-03-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param); 
		} 
		catch (Exception e) { System.err.println("Error : client server problem"); e.printStackTrace(); }
		try
		{
			while(rs.next())
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Gasoline",rs.getString("total"),"2020-03")); 
		} 
		catch (SQLException e) { e.printStackTrace(); }
	//
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='Diesel'  AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-03-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("Diesel",rs.getString("total"),"2020-03")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='ScooterFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-03-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("ScooterFuel",rs.getString("total"),"2020-03")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		rs = null ; 
		selectQuery = "SELECT SUM(productAmount) AS total FROM myfuel.requests WHERE productName='HomeHeatingFuel' AND supplierName!='null' AND gasStationManager=? AND date LIKE '2020-03-%%' AND delivered='yes'";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e) { System.err.println("Error : client server problem");e.printStackTrace(); }
		try 
		{
			while(rs.next()) 
				if(rs.getString("total")!=null)
					oblist.add(new Purchase("HomeHeatingFuel",rs.getString("total"),"2020-03")); 
		}
		catch (SQLException e) { e.printStackTrace();}
		
		 
		productColumnPurchasesTable.setCellValueFactory(new PropertyValueFactory<Purchase, String>("productName")); 
		totalColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("total"));
	 	datePurchases.setCellValueFactory(new PropertyValueFactory<Purchase, String>("date")); 
	 	
		purchasesTable.setItems(oblist);
	} 
	/**
	 * gets the station that this manager works in
	 * from DB
	 */
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
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
