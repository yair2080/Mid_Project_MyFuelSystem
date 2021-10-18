package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import entities.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
/**
 * this is a controller for ClientHomeSale gui.
 * in this form we show the client all the discounts that are
 * relevant to him. we also show him the sale code for him to remember
 * it and use it when the right time will come 
 * (when he refuels or make a home heating order)
 */

public class ClientHomeSaleC implements Initializable
{
	LocalDate date = LocalDate.now();
	String user_username = LoginControllerC.userin;
	String user_gasStation1 = "";
	String user_gasStation2 = "";
	String user_gasStation3 = "";
	
    @FXML
    private Pane picturePane;

    @FXML
    private ImageView randomPicture;

    @FXML
    private Label saleLabel1;

    @FXML
    private Label saleLabel2;

    @FXML
    private Label saleLabel3;

    @FXML
    private Label saleLabel4;

    @FXML
    private Label saleLabel5;

    @FXML
    private Label discountLabel;

    @FXML
    private TableView<Sale> salesTable;

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
    private TableColumn<Sale, String> saleCodeHeadline;
    /**
     * initialize : the function finds the client's gas stations
     * and then it upload all the discounts that fits those gas stations
     */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1)
    {
    	getUserGasStations();
		refreshSalesTable();
		
	}
    /**
     * handleSalesTableRowClick opens the discount in the big pane
     * with all the sale details and sale information
     * @param event - this event occurs when the user clicks on a row
     * in the sales table in the form
     */
    @FXML
    void handleSalesTableRowClick(MouseEvent event)
    {
    	int index = salesTable.getSelectionModel().getSelectedIndex();
		
		if(index <= -1)
		{
			return;
		}
		
		saleLabel5.setText("");
		// saleLabel5.setDisable(true);
		
		/*String pictureURL = "@../../pictures/newSaleLogo" + pictureColorHeadline.getCellData(index).toString() + ".png";
		Image image = new Image(pictureURL);
	    randomPicture.setImage(image);*/
	    
    	discountLabel.setText( discountHeadline.getCellData(index).toString() +"%" );
    	saleLabel1.setText("Only in " + gasStationHeadline.getCellData(index).toString());
    	saleLabel2.setText("We have an amazing discount");
    	saleLabel3.setText("of " + discountHeadline.getCellData(index).toString() + "% on " + productNameHeadline.getCellData(index).toString() );
    	saleLabel4.setText("Only from " + startDateHeadline.getCellData(index).toString() + " for " + daysDurationHeadline.getCellData(index).toString() + " days");
    	
    	if( !startHourHeadline.getCellData(index).toString().equals("Choose") )
    	{
    		// saleLabel5.setDisable(false);
    		saleLabel5.setText("Start at " + startHourHeadline.getCellData(index).toString() + " for " + hoursDurationHeadline.getCellData(index).toString() + " hours");
    	}
    }
    /**
     * refreshSalesTable : this function check if
     * 1. sale date is relevant
     * 2. gas station of the sale belongs to the gas stations of the user
     * then it update the sales table in the form with all the discount
     * details.
     */
    public void refreshSalesTable()
    {
		ObservableList<Sale> oblist = FXCollections.observableArrayList();
    	ResultSet rs = null;
		String selectQuery = "select * from sales";
		int rowCounter = 0;

		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.getTable(selectQuery);	
			
			while(rs.next())
			{
				// check that the date is relevant
				if( checkSaleDate(rs.getString("startDate"), rs.getString("daysDuration")) )
				{
					// check gas stations belongs to the user
					if( checkGasStation(rs.getString("gasStation")) )
					{
						oblist.add(new Sale(rs.getString("productName"), rs.getString("discount"), rs.getString("startDate"), rs.getString("daysDuration"), rs.getString("startHour"), rs.getString("hoursDuration"), rs.getString("gasStation"), rs.getString("pictureColor"), rs.getString("saleCode")));
						rowCounter++;
						System.out.println("rowCounter : " + rowCounter);
					}
				}  
			}
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingRepUpdateFromEmployee : client server problem");
			e.printStackTrace();
		}
		
		productNameHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("productName"));
		discountHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("discount"));
		startDateHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("startDate"));
		daysDurationHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("daysDuration"));
		startHourHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("startHour"));
		hoursDurationHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("hoursDuration"));
		gasStationHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("gasStation"));
		pictureColorHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("pictureColor"));
		saleCodeHeadline.setCellValueFactory(new PropertyValueFactory<Sale, String>("saleCode"));
		
		salesTable.setItems(oblist);	
    }
    /**
     * refreshSalesTable : this function check if
     * 1. sale date is relevant
     * 2. gas station of the sale belongs to the gas stations of the user
     * then it update the sales table in the form with all the discount
     * details.
     */
    public void getUserGasStations()
    {
    	ArrayList<String> arr = new ArrayList<>();
		ResultSet rs = null;
		
		String query = "select gasStation1, gasStation2, gasStation3 from purchaseplan where username = ? ";
		arr.add(user_username);
		//arr.add("benBen");
		
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(query, arr);
			
			while(rs.next())
			{
				user_gasStation1 = rs.getString("gasStation1");
				user_gasStation2 = rs.getString("gasStation2");
				user_gasStation3 = rs.getString("gasStation3");
				
				System.out.println("userGasStation1 : " + user_gasStation1);
				System.out.println("userGasStation2 : " + user_gasStation2);
				System.out.println("userGasStation3 : " + user_gasStation3);
			}
			
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerRates : client server problem");
			e.printStackTrace();
		}
    }
    
    /**
     * checkGasStation : this function checks if gasStation from the sale
     * equals to one of the user stations
     * @param gasStation - the gas station name
     * @return true - if gas station belongs to the user and false otherwise
     */
    public boolean checkGasStation(String gasStation)
    {
    	if(gasStation.equals(user_gasStation1) || gasStation.equals(user_gasStation2) || gasStation.equals(user_gasStation3) ) 
    	{
    		return true;
    	}
    	
    	return false;
    }
    /**
     * checkSaleDate : this function checks that the sale date is rellevant
     * to be shown
     * @param saleDateC - the start date of the sale
     * @param daysDurationC - how many days the sale lasts
     * @return true - if the date is relevant and false otherwise
     */
    public boolean checkSaleDate(String saleDateC, String daysDurationC)
    {
    	LocalDate saleStartDate = null;
    	
    	System.out.println("saleDate = " + saleDateC);
		
		saleStartDate =  LocalDate.parse(saleDateC);
		System.out.println(saleStartDate+":)");
		// check 1
    	if( date.getYear() < saleStartDate.getYear())
    	{
    		
    		return true;
    		
    	}
    	
    	// check 2
    	if( date.getYear() == saleStartDate.getYear() )
    	{
    		if( date.getMonthValue() < saleStartDate.getMonthValue() )
        	{
    			return true;
        	} 		
    	}
    	
    	// check 3
    	if( date.getYear() == saleStartDate.getYear() )
    	{
    		if( date.getMonthValue() == saleStartDate.getMonthValue() )
        	{
    			if(date.getDayOfMonth() < saleStartDate.getDayOfMonth() )
    	    	{
    				return true;
    	    	}
        	} 		
    	}
    	
    	// check 4
    	if( date.getYear() == saleStartDate.getYear() )
    	{
    		if( date.getMonthValue() == saleStartDate.getMonthValue() )
        	{
				int totalSaleDate = saleStartDate.getDayOfMonth() + Integer.parseInt(daysDurationC) - 1;
				
				if(date.getDayOfMonth() > totalSaleDate )
				{
					return false;
				}
				
				return true;
        	} 		
    	}
    	
    	JOptionPane.showMessageDialog(null,"Error : Date already passed \nPlease choose a later date","Error",JOptionPane.INFORMATION_MESSAGE);
    	
		return false;
    }

	

}
