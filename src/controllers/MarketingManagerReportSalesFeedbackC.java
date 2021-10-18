package controllers;

import java.net.URL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import entities.SaleReport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * this is a controller for MarketingManagerReportSalesFeedback gui.
 * we enter this form when we click on the rates button and then on the
 * sales feedback report button
 * the form is simply display the information of this report
 */

public class MarketingManagerReportSalesFeedbackC implements Initializable
{
	String marketingManagerUsername = LoginControllerC.userin;
	
    @FXML
    private Label userWelcomeLabel;

    @FXML
    private TableView<SaleReport> salesFeedbackTable;

    @FXML
    private TableColumn<SaleReport, String> saleCodeHeadline;

    @FXML
    private TableColumn<SaleReport, String> numberOfUsersHeadline;

    @FXML
    private TableColumn<SaleReport, Integer> totalIncomeHeadline;

    @FXML
    private TableColumn<SaleReport, String> saleDiscountHeadline;
    
    /**
     * initialize : the function is responsible of 2 things
     * 1. find what is the current marketing manager gas station
     * 2. call the function who does the calculation and sets the relevant
     * information into the report table that is inside the form
     */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		String gasStationS = findManagerGasStation();
		refreshSalesFeedbackTable(gasStationS);
	}
	
	/**
     * findManagerGasStation : the function is responsible to find
     * and return the current marketing manager gas station
     * @return String : gas station name
     */
	
	public String findManagerGasStation()
	{
		String gasStationS = "none";
		ResultSet rs = null;
		ArrayList<String> crr = new ArrayList<>();
		
		String selectQuery = "select gasStation FROM employees where username = ?";
		crr.add(marketingManagerUsername);
		
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, crr);
			
			while(rs.next())
			{
				gasStationS = rs.getString("gasStation");
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingRepUpdateFromEmployee : client server problem");
			e.printStackTrace();
		}
		
		return gasStationS;
	}
    
	/**
     * refreshSalesFeedbackTable : the function is responsible to gather
     * all information that is necessary to the report
     * and update it in the sales feedback table in the form
     * @param gasStation - the current marketing manager gas station
     */
	
	public void refreshSalesFeedbackTable(String gasStationS)
	{
		ResultSet rs = null;
		ArrayList<String> brr = new ArrayList<>();
		ObservableList<SaleReport> oblist = FXCollections.observableArrayList();
		
		String usersNumber = "0";
		Integer totalCost = 0;
		String discountOfSale = "0";
		
		String selectQuery = "select distinct saleCode FROM salesReport WHERE gasStation = ?";
		brr.add(gasStationS);

		try
		{
			ClientUI.chat.accept("");
			
			rs = ChatClient.selectWithParameters(selectQuery, brr);
			
			while(rs.next())
			{
				usersNumber = getNumberOfSaleUsers(rs.getString("saleCode"));
				totalCost = getTotalSaleCost(rs.getString("saleCode"));
				discountOfSale = getSaleDiscount(rs.getString("saleCode")) + "%";
			
				if( !rs.getString("saleCode").equals("0") )
				{
					oblist.add(new SaleReport(rs.getString("saleCode"), usersNumber, totalCost, discountOfSale));
				}	
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerReports : client server problem");		
			e.printStackTrace();
		}
		
		saleCodeHeadline.setCellValueFactory(new PropertyValueFactory<SaleReport, String>("saleCode"));
		numberOfUsersHeadline.setCellValueFactory(new PropertyValueFactory<SaleReport, String>("username"));
		totalIncomeHeadline.setCellValueFactory(new PropertyValueFactory<SaleReport, Integer>("cost"));
		saleDiscountHeadline.setCellValueFactory(new PropertyValueFactory<SaleReport, String>("gasStation"));
		
		salesFeedbackTable.setItems(oblist);
	}
	
	/**
     * getTotalSaleCost : the function sums up how much the gas station 
     * has earned from a given sale, and return its value
     * @param saleCodeS - the sale number of the requested sale
     * @raturn sum - the sum of all the incomes from this sale
     */
    
	private int getTotalSaleCost(String saleCodeS)
	{
		ResultSet rs = null;
		ArrayList<String> arr = new ArrayList<>();
		int sum = 0;
		
		String query = "select sum(cost) FROM salesReport where saleCode = ?";
		arr.add(saleCodeS);
		
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(query, arr);
			
			while(rs.next())
			{
				sum = rs.getInt("sum(cost)");
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerReports : client server problem");			e.printStackTrace();
		}
		
		return sum;
	}
	
	/**
     * getNumberOfSaleUsers : the function count how many customers 
     * were using a given sale, and return this number
     * @param saleCodeS - the sale number of the requested sale
     * @return usersCount - number of people used this sale (as a string value)
     */
	
	public String getNumberOfSaleUsers(String saleCodeS)
	{
		ResultSet rs = null;
		ArrayList<String> arr = new ArrayList<>();
		String usersCount = "0";
		
		String query = "select count(*) FROM salesReport where saleCode = ?";
		arr.add(saleCodeS);
		
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(query, arr);
			
			while(rs.next())
			{
				usersCount = rs.getString("count(*)");
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerReports : client server problem");			e.printStackTrace();
		}
		
		return usersCount;
	}
	

	/**
     * getSaleDiscount : the function returns the discount of a given sale 
     * @param saleCodeS - the sale number of the requested sale
     * @return discount - the discount of this sale (as a string value)
     */	

	public String getSaleDiscount(String saleCodeS)
	{
		ResultSet rs = null;
		ArrayList<String> arr = new ArrayList<>();
		String discount = "none";
		
		String query = "select discount from sales where saleCode = ?";
		arr.add(saleCodeS);
		
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(query, arr);
			
			while(rs.next())
			{
				discount = rs.getString("discount");
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerReports : client server problem");
			e.printStackTrace();
		}
		
		return discount;
	}
}

