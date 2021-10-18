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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * this is a controller for MarketingManagerReportCharacterization gui.
 * we enter this form when we click on the rates button and then on the
 * characterization report button
 * the form is simply display the information of this report
 */

public class MarketingManagerReportCharacterizationC implements Initializable
{
	String marketingManagerUsername = LoginControllerC.userin;
	
	int minIncome = 1000000;
	int maxIncome = 0;
	int range = 0;

    @FXML
    private TableView<SaleReport> customerCharacterizationTable;

    @FXML
    private TableColumn<SaleReport, String> clientIDHeadline;

    @FXML
    private TableColumn<SaleReport, String> fullNameHeadline;

    @FXML
    private TableColumn<SaleReport, Integer> totalIncomeHeadline;

    @FXML
    private TableColumn<SaleReport, String> customerRateHeadline;
    
    /**
     * initialize : the function is responsible of 3 things
     * 1. find what is the current marketing manager gas station
     * 2. update the correct min/max/range value of this gas station product sales incomes
     * 3. call the function who does the calculation and sets the relevant
     * information into the report table that is inside the form
     */
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
    	// find gas station
		String gasStationS = findManagerGasStation(); 
		
		// update Min Max Range Incomes
		getMinMaxIncomesAndRange(gasStationS); 
		
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
     * getMinMaxIncomesAndRange : the function is responsible to update
     * the minimum value, the maximum value and the range between this two values
     * of this gas station product sales incomes
     * @param gasStation - the current marketing manager gas station
     */
    
    public void getMinMaxIncomesAndRange(String gasStation)
    {
    	Integer tempIncome = 0;
    	boolean isFirstTime = true;
    	
    	ResultSet rs = null;
    	ArrayList<String> arr = new ArrayList<>();
    	
    	String selectQuery = "select distinct username FROM salesReport WHERE gasStation = ?";
		arr.add(gasStation);
    	
    	ResultSet rs2 = null;
    	ArrayList<String> arr2 = new ArrayList<>();
    	
    	String selectQuery2 = "select sum(cost) FROM salesReport WHERE gasStation = ? AND username = ?";
		arr2.add(gasStation);
    	
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, arr);
			
			while(rs.next())
			{
				if( isFirstTime )
				{
					arr2.add(rs.getString("username"));
					isFirstTime = false;
				}
				else
				{
					arr2.set(1, rs.getString("username"));
				}
				
				try
				{
					ClientUI.chat.accept("");
					rs2 = ChatClient.selectWithParameters(selectQuery2, arr2);
					
					while(rs2.next())
					{
						tempIncome = rs2.getInt("sum(cost)");
						
						if( tempIncome > maxIncome)
						{
							maxIncome = tempIncome;
						}
						
						if( tempIncome < minIncome)
						{
							minIncome = tempIncome;
						}
					}
				} 
				catch (Exception e)
				{
					System.err.println("Error : MarketingRepUpdateFromEmployee : client server problem");
					e.printStackTrace();
				}
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingRepUpdateFromEmployee : client server problem");
			e.printStackTrace();
		}
		
		System.out.println("minIncome = " + minIncome);
		System.out.println("maxIncome = " + maxIncome);
		System.out.println("range = " + (maxIncome - minIncome) );
		
		range = maxIncome - minIncome;
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
		ArrayList<String> clientInfo = new ArrayList<>();
		ObservableList<SaleReport> oblist = FXCollections.observableArrayList();
		
		String clientID = "0";
		String clientFullName = "0";
		Integer totalIncome = 0;
		Integer customerRate = 0;
		
		String selectQuery = "select distinct username FROM salesReport WHERE gasStation = ?";
		brr.add(gasStationS);

		try
		{
			ClientUI.chat.accept("");
			
			rs = ChatClient.selectWithParameters(selectQuery, brr);
			
			while(rs.next())
			{System.out.println("username="+rs.getString("username"));
				clientInfo = getClientInformation(rs.getString("username"));
				clientID = clientInfo.get(0);
				clientFullName = clientInfo.get(1);
				
				totalIncome = getClientTotalIncome(rs.getString("username"), gasStationS);
				customerRate = getClientRate(totalIncome);
			
				oblist.add(new SaleReport(clientID, clientFullName, totalIncome, customerRate.toString()));
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerReports : client server problem");		
			e.printStackTrace();
		}
		
		clientIDHeadline.setCellValueFactory(new PropertyValueFactory<SaleReport, String>("saleCode"));
		fullNameHeadline.setCellValueFactory(new PropertyValueFactory<SaleReport, String>("username"));
		totalIncomeHeadline.setCellValueFactory(new PropertyValueFactory<SaleReport, Integer>("cost"));
		customerRateHeadline.setCellValueFactory(new PropertyValueFactory<SaleReport, String>("gasStation"));
		
		customerCharacterizationTable.setItems(oblist);
	}
    
    /**
     * getClientInformation : the function is given a username of the product buyer
     * it finds this buyer personal details (clientID, firstName and lastName) 
     * and return it in an arrayList "result" in the following order :
     * result[0] is clientID
     * result[1] is full name (firstName + lastName)
     * @param username - the username of the product buyer
     * @return ArrayList<String> - buyer's personal details
     */
    
	public ArrayList<String> getClientInformation(String username)
    {
    	ResultSet rs = null;
		ArrayList<String> crr = new ArrayList<>();
		ArrayList<String> result = new ArrayList<>();
		
		String clientID = "null";
		String fullName = "null";
		
    	String selectQuery = "select firstName, lastName, IdNumber FROM singleclient WHERE username = ?";
		crr.add(username);

		try
		{
			ClientUI.chat.accept("");
			
			rs = ChatClient.selectWithParameters(selectQuery, crr);
			
			while(rs.next())
			{
				fullName = rs.getString("firstName") + " " + rs.getString("lastName");
				clientID = rs.getString("IdNumber");
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerReports : client server problem");		
			e.printStackTrace();
		}
		
		result.add(clientID);
		result.add(fullName);
		
		System.out.println("clientID : " + result.get(0));
		System.out.println("fullName : " + result.get(1));
		
    	return result;
    }
	
	 /**
     * getClientTotalIncome : the function is given a username of the product buyer
     * and the same gas station (of the current marketing manager) 
     * and return the total income the gas station did on this buyer
     * @param username - the username of the product buyer
     * @param gasStation - the current marketing manager gas station
     * @return int - clientTotalIncome
     */
    
    public int getClientTotalIncome(String username, String gasStation)
    {
    	ResultSet rs = null;
		ArrayList<String> drr = new ArrayList<>();
		
		int clientTotalIncome = 0;
		
    	String selectQuery = "select sum(cost) FROM salesReport WHERE username = ? AND gasStation = ?";
		drr.add(username);
		drr.add(gasStation);

		try
		{
			ClientUI.chat.accept("");
			
			rs = ChatClient.selectWithParameters(selectQuery, drr);
			
			while(rs.next())
			{
				clientTotalIncome = rs.getInt("sum(cost)");
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerReports : client server problem");		
			e.printStackTrace();
		}
		
		return clientTotalIncome;
    }


	/**
    * getClientRate : the function get an income number
    * and uses the minimum \ maximum incomes to calculate the 
    * buyer's rate corresponding to its income
    * @param income - the total amount of money the buyer spent
    * @return int - buyer's (client) rate (from 1 to 10)
    */
    
    // calculate the client's rate from its total income
    private int getClientRate(int income) 
    {
    	int difference = maxIncome - income;
    	int difference2 = range - difference;
    	
    	double rate = difference2 / (double)range;
    	rate *= 10;
    	
    	if(rate == 10)
    	{
    		return 10;
    	}
    	
    	return ( (int)rate + 1 );
	}
}
