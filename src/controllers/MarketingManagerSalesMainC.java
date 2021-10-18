package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import entities.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * this is a controller for MarketingManagerSalesMain gui.
 * in this form, the marketing manager can see all the sales that is in
 * his gas station (that he created).
 * the form also contains 3 buttons that allows the marketing manager to :
 * 1. create new sale (goes to another form)
 * 2. delete an existed sale 
 * 3. edit an existed sale (goes to another form)
 */

public class MarketingManagerSalesMainC implements MainInstanceInterface, Initializable
{
	private ConnectToInstance mainInstance = null;
	
	String marketingManagerUsername = LoginControllerC.userin;
	public static String userGasStation = "";
	
	String productNameST = "";
	String discountST = "";
	String startDateST = "";
	String daysDurationST = "";
	String startHourST = "";
	String hoursDurationST = "";
	String gasStationST = "";
	String pictureColorST = "";
	String saleCodeST = "";
	
	boolean isTableEmpty = true;
	
	MarketingManagerSalesCreationC classInstance;
	
    @FXML
    private TableView<Sale> salesTable;

    @FXML
    private TableColumn<Sale, String> saleCodeHeadline;

    @FXML
    private TableColumn<Sale, String> productNameHeadline;

    @FXML
    private TableColumn<Sale, String> discountHeadline;

    @FXML
    private TableColumn<Sale, String> startDateHeadline;

    @FXML
    private TableColumn<Sale, String> daysDurationHeadline;

    @FXML
    private TableColumn<Sale, String> startHourHeadline;

    @FXML
    private TableColumn<Sale, String> hoursDurationHeadline;

    @FXML
    private TableColumn<Sale, String> gasStationHeadline;

    @FXML
    private TableColumn<Sale, String> pictureColorHeadline;

    @FXML
    private Button createNewSaleButton;
    
    @FXML
    private Button editSaleButton;

    @FXML
    private Button deleteSaleButton;
    
    /**
     * handleDeleteSaleButtonClick delete the selected sale from the form
     * and from the mySql.
     * @param event - this event occurs when the user clicks on the delete sale button
     */
    
    @FXML
    void handleDeleteSaleButtonClick(ActionEvent event) 
    {
    	if(isTableEmpty)
		{
			JOptionPane.showMessageDialog(null,"Error : Table is empty OR you didn't click on a row \nCan not delete sale","Error",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
    	
    	String deleteQuery ="DELETE FROM sales WHERE saleCode = ?";
 	    ArrayList <String> items = new ArrayList<>();
 	    items.add(saleCodeST);
 	   
 	    ClientUI.chat.accept("");
   		ChatClient.updateTable(deleteQuery, items);
   		
   		refreshTable();
    }
    
    /**
     * handleEditSaleButtonClick takes the selected sale information
     * and send it to the salesCreator (and editor) form
     * @param event - this event occurs when the user clicks on the edit sale button
     */

    @FXML
    void handleEditSaleButtonClick(ActionEvent event)
    {	
    	if(isTableEmpty)
		{
			JOptionPane.showMessageDialog(null,"Error : Table is empty","Error",JOptionPane.INFORMATION_MESSAGE);
			return;
		}
    
    	if(mainInstance != null)
    	{
    		FXMLLoader loader = mainInstance.sendMessage("MarketingManagerSalesCreation");
    		classInstance = loader.getController();
    	}
    	
    	classInstance.editInitialization(productNameST, discountST, startDateST, daysDurationST, startHourST, hoursDurationST, gasStationST, pictureColorST, saleCodeST);
    }
    
    /**
     * handleSalesTableMouseClick takes the selected sale row from the sale table
     * that I created in the form and save the data in a variables for a future use
     * @param event - this event occurs when the user clicks on a row in the sales table
     */

    @FXML
    void handleSalesTableMouseClick(MouseEvent event) 
    {
    	int index = salesTable.getSelectionModel().getSelectedIndex();
		
		if(index <= -1)
		{
			isTableEmpty = true;
			return;
		}
		
		isTableEmpty = false;
		
		productNameST = productNameHeadline.getCellData(index).toString();
		discountST = discountHeadline.getCellData(index).toString();;
		startDateST = startDateHeadline.getCellData(index).toString();
		daysDurationST = daysDurationHeadline.getCellData(index).toString();
		startHourST = startHourHeadline.getCellData(index).toString();
		hoursDurationST = hoursDurationHeadline.getCellData(index).toString();
		gasStationST = gasStationHeadline.getCellData(index).toString();
		pictureColorST = pictureColorHeadline.getCellData(index).toString();
		saleCodeST = saleCodeHeadline.getCellData(index).toString();
    } 

    /**
     * handleCreateNewSaleButtonClick transfer us to the salesCreator form
     * there we will be able to create a new sale
     * @param event - this event occurs when the user clicks on createNewSale button
     */
    
    @FXML
    void handleCreateNewSaleButtonClick(ActionEvent event)
    {
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("MarketingManagerSalesCreation");
    	}
    }
    
    /**
     * initialize : the function simply calls the refresh sale table function
     * then all the sales will be represented on the table
     */
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		refreshTable();
	}
	
	
	 /**
     * refreshTable : the function is responsible to take all the sales
     * that are of the marketing manager gas station, and display it in
     * the sales table that I have created in the form
     */
	
	public void refreshTable()
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
		
		
		ArrayList<String> jrr = new ArrayList<>();
		String selectQuery = "select * from sales where gasStation = ?";
		jrr.add(userGasStation);
		System.out.println(userGasStation);/////
    	ResultSet rs = null;
    	ObservableList<Sale> oblist = FXCollections.observableArrayList();

		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, jrr);
			
			if( rs.equals(null) )
			{
				return;
			}
			
			while(rs.next())
			{
				oblist.add(new Sale(rs.getString("productName"), rs.getString("discount"), rs.getString("startDate"), rs.getString("daysDuration"), rs.getString("startHour"), rs.getString("hoursDuration"), rs.getString("gasStation"), rs.getString("pictureColor"), rs.getString("saleCode")));
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerRates : client server problem");
			e.printStackTrace();
		}
		
		saleCodeHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("saleCode"));
		productNameHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("productName"));
		discountHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("discount"));
		startDateHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("startDate"));
		daysDurationHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("daysDuration"));
		startHourHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("startHour"));
		hoursDurationHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("hoursDuration"));
		gasStationHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("gasStation"));
		pictureColorHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("pictureColor"));
		
		salesTable.setItems(oblist);
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
