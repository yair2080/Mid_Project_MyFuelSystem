package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * this is a controller for MarketingManagerSalesCreation gui.
 * we enter this form when we click on the rates button in the main window
 * the form simply display two buttons that will lead us to the reports forms
 */

public class MarketingManagerSalesCreationC implements Initializable
{	
    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField hoursField;

    @FXML
    private Button bluePictureButton;

    @FXML
    private Pane picturePane;
    @FXML
    private ImageView greenPicture;

    @FXML
    private ImageView orangePicture;

    @FXML
    private ImageView blue1picture;

    @FXML
    private ImageView Blue2Picture;

    @FXML
    private ImageView redPicture;

    @FXML
    private ImageView BrownPicture;

    @FXML
    private ImageView purpulePicture;
    @FXML
    private ImageView randomPicture;

    @FXML
    private Button brownPictureButton;

    @FXML
    private Button darkPictureButton;

    @FXML
    private Button greenPictureButton;
    
    @FXML
    private Button perplePictureButton;

    @FXML
    private Button orangePictureButton;

    @FXML
    private Button pinkPictureButton;

    @FXML
    private Button bluePalePictureButton;
    
    @FXML
    private ChoiceBox<String> productChoiceBox;

    @FXML
    private ChoiceBox<String> gasStationChoiceBox;

    @FXML
    private ChoiceBox<String> discountChoiceBox;
    
    @FXML
    private ChoiceBox<String> startHourChoiceBox;

    @FXML
    private ChoiceBox<String> daysDurationChoiceBox;

    @FXML
    private ChoiceBox<String> hoursDurationChoiceBox;

    @FXML
    private Button showPreviewButton;

    @FXML
    private Button saveButton;
    
    @FXML
    private Label discountLabel;
    
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
    
    String timeString = "";
   
    String days = "1";
    int sum = 0;
    String color = "";
    String saleCodeSS;
    String saleStartDateSS = "";
    boolean isDateClicked = false;
	boolean isEdit = false;
	
	/////////////////////////////////////////////
    
    ObservableList<String> products = FXCollections.observableArrayList("diesel", "gasoline", "homeHeatingFuel", "scooterFuel");
	//ObservableList<String> gasStations = FXCollections.observableArrayList("Delek", "Paz", "Sonol", "Ten");
    ObservableList<String> gasStations;
	ObservableList<String> discounts = FXCollections.observableArrayList("5", "10", "15", "20", "25", "30", "35", "40", "50", "60", "70", "80");
	ObservableList<String> daysDuration = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7");
	ObservableList<String> startHour = FXCollections.observableArrayList("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "18:00", "20:00", "22:00");
	ObservableList<String> hoursDuration = FXCollections.observableArrayList("1", "2", "3", "4");
	
	
	
	/**
     * editInitialization : this functions handle the case in which the sale
     * is already existed.
     * it initialize the existed sale details in the corresponding text fields and variables
     * @param are all the parameters of the sale table from mySql
     * @param productNameS = the product the sale is about
     * @param discountS = the discount the sale is giving
     * @param startDateS = the date the sale starts
     * @param daysDurationS = how many days the sale stands
     * @param startHourS = the hour in a day that the sale start
     * @param hoursDurationS = how many hours the sale stands
     * @param gasStationS = the gas station of the sale
     * @param pictureColorS = the picture in which the sale uses to display itself
     * @param saleCodeS = the unique code of this sale
     */
	
	public void editInitialization(String productNameS, String discountS, String startDateS, String daysDurationS, String startHourS, String hoursDurationS, String gasStationS, String pictureColorS, String saleCodeS)
	{
		// write sales info into the text fields and choiceBoxes
		
		// handle choiceBoxes
		productChoiceBox.setValue(productNameS);
		discountChoiceBox.setValue(discountS);
		daysDurationChoiceBox.setValue(daysDurationS);
		startHourChoiceBox.setValue(startHourS);
		hoursDurationChoiceBox.setValue(hoursDurationS);
		gasStationChoiceBox.setValue(gasStationS);
		
		// handle date
		LocalDate existDate = LocalDate.parse(startDateS);
		startDatePicker.setValue(existDate);
		saleStartDateSS = startDateS;
		
		// keep the picture
		String pictureURL = "@../../pictures/newSaleLogo" + pictureColorS + ".png";
		Image image = new Image(pictureURL);
	    randomPicture.setImage(image);
	    
	    color = pictureColorS;
	    
	    // keep the saleCode
	    saleCodeSS = saleCodeS;
		
		isEdit = true;
		isDateClicked = false;
	}
	
	/**
     * initialize : this functions handle unabled \ disabled fields in the
     * beginning of the form
     * it also set value to every choice box I have in this form
     * the function act differently if the sale is new or existed
     */
	
    @Override
	public void initialize(URL arg0, ResourceBundle arg1)
    {
    	gasStations=FXCollections.observableArrayList( );///
    	gasStations.add(MarketingManagerSalesMainC.userGasStation);////
    	// check : is it a new sale or an existed sale
    	if( !isEdit )
    	{
    		productChoiceBox.setValue("Choose");
        	gasStationChoiceBox.setValue("Choose");
        	discountChoiceBox.setValue("Choose");
        	daysDurationChoiceBox.setValue("1");
        	startHourChoiceBox.setValue("Choose");
        	hoursDurationChoiceBox.setValue("Choose");
    	}
    	
    	color = "Pink1";
    	
    	productChoiceBox.setItems(products);
		gasStationChoiceBox.setItems(gasStations);
		discountChoiceBox.setItems(discounts);
		daysDurationChoiceBox.setItems(daysDuration); 
		startHourChoiceBox.setItems(startHour); 
		hoursDurationChoiceBox.setItems(hoursDuration);
    	
    	discountLabel.setDisable(true);
    	saleLabel1.setDisable(true);
    	saleLabel2.setDisable(true);
    	saleLabel3.setDisable(true);
    	saleLabel4.setDisable(true);
    	saleLabel5.setDisable(true);
	}
    
    /**
     * handleStartDatePickerClick saves the start date that was chosen.
     * @param event - this event occurs when the user clicks on the calendar in the "Start Date" button
     */
    
    @FXML
    void handleStartDatePickerClick(ActionEvent event)
    {
        	timeString = String.valueOf(startDatePicker);
        	
        	isDateClicked = true;
    }
    
    /**
     * handleBluePalePictureButtonClick saves the color of the picture to be color "blue1".
     * it also set the picture to be the blue pale picture in the preview picture
     * @param event - this event occurs when the user clicks on the blue pale button
     */

    @FXML
    void handleBluePalePictureButtonClick(ActionEvent event)
    {
    	
    	greenPicture.setVisible(false);
        orangePicture.setVisible(false);
        blue1picture.setVisible(true);
        Blue2Picture.setVisible(false);
        redPicture.setVisible(false);
        BrownPicture.setVisible(false);
        purpulePicture.setVisible(false);
        randomPicture.setVisible(false);
    	color = "Blue1";
    }

    /**
     * handleBluePictureButtonClick saves the color of the picture to be color "blue2".
     * it also set the picture to be the blue picture in the preview picture
     * @param event - this event occurs when the user clicks on the blue button
     */
    
    @FXML
    void handleBluePictureButtonClick(ActionEvent event) 
    {
    	
    	greenPicture.setVisible(false);
        orangePicture.setVisible(false);
        blue1picture.setVisible(false);
        Blue2Picture.setVisible(true);
        redPicture.setVisible(false);
        BrownPicture.setVisible(false);
        purpulePicture.setVisible(false);
        randomPicture.setVisible(false);
     	color = "Blue2";
    }

    /**
     * handleBrownPictureButtonClick saves the color of the picture to be color "brown".
     * it also set the picture to be the brown picture in the preview picture
     * @param event - this event occurs when the user clicks on the brown button
     */
    
    @FXML
    void handleBrownPictureButtonClick(ActionEvent event)
    {
    	
    	greenPicture.setVisible(false);
        orangePicture.setVisible(false);
        blue1picture.setVisible(false);
        Blue2Picture.setVisible(false);
        redPicture.setVisible(false);
        BrownPicture.setVisible(true);
        purpulePicture.setVisible(false);
        randomPicture.setVisible(false);
     	color = "Brown";
    }
    
    /**
     * handleDarkPictureButtonClick saves the color of the picture to be color "dark".
     * it also set the picture to be the dark picture in the preview picture
     * @param event - this event occurs when the user clicks on the dark button
     */

    @FXML
    void handleDarkPictureButtonClick(ActionEvent event)
    {
    	
    	greenPicture.setVisible(false);
        orangePicture.setVisible(false);
        blue1picture.setVisible(false);
        Blue2Picture.setVisible(false);
        redPicture.setVisible(true);
        BrownPicture.setVisible(false);
        purpulePicture.setVisible(false);
        randomPicture.setVisible(false);
     	color = "Dark";
    }
    
    /**
     * handleGreenPictureButtonClick saves the color of the picture to be color "green1".
     * it also set the picture to be the green picture in the preview picture
     * @param event - this event occurs when the user clicks on the green button
     */

    @FXML
    void handleGreenPictureButtonClick(ActionEvent event) 
    {
    	
    	greenPicture.setVisible(true);
        orangePicture.setVisible(false);
        blue1picture.setVisible(false);
        Blue2Picture.setVisible(false);
        redPicture.setVisible(false);
        BrownPicture.setVisible(false);
        purpulePicture.setVisible(false);
        randomPicture.setVisible(false);
     	color = "Green1";
    }

    /**
     * handleOrangePictureButtonClick saves the color of the picture to be color "orange1".
     * it also set the picture to be the orange1 picture in the preview picture
     * @param event - this event occurs when the user clicks on the orange button
     */
    
    @FXML
    void handleOrangePictureButtonClick(ActionEvent event)
    {
    	
    	greenPicture.setVisible(false);
        orangePicture.setVisible(true);
        blue1picture.setVisible(false);
        Blue2Picture.setVisible(false);
        redPicture.setVisible(false);
        BrownPicture.setVisible(false);
        purpulePicture.setVisible(false);
        randomPicture.setVisible(false);
     	color = "Orange1";
    }
    
    /**
     * handlePerplePictureButtonClick saves the color of the picture to be color "purple1".
     * it also set the picture to be the purple1 picture in the preview picture
     * @param event - this event occurs when the user clicks on the purple button
     */

    @FXML
    void handlePerplePictureButtonClick(ActionEvent event)
    {
    	
    	greenPicture.setVisible(false);
        orangePicture.setVisible(false);
        blue1picture.setVisible(false);
        Blue2Picture.setVisible(false);
        redPicture.setVisible(false);
        BrownPicture.setVisible(false);
        purpulePicture.setVisible(true);
        randomPicture.setVisible(false);
     	color = "Perple1";
    }
    
    /**
     * handlePinkPictureButtonClick saves the color of the picture to be color "pink1".
     * it also set the picture to be the pink1 picture in the preview picture
     * @param event - this event occurs when the user clicks on the pink button
     */

    @FXML
    void handlePinkPictureButtonClick(ActionEvent event) 
    {
    	greenPicture.setVisible(false);
        orangePicture.setVisible(false);
        blue1picture.setVisible(false);
        Blue2Picture.setVisible(false);
        redPicture.setVisible(false);
        BrownPicture.setVisible(false);
        purpulePicture.setVisible(false);
        randomPicture.setVisible(true);
     	color = "Pink1";
    }

    /**
     * handleSaveButtonClick calls to a function that checks inserted data
     * and then calls to a function that saves the information in the sales table in mySql 
     * @param event - this event occurs when the user clicks on the save sale button
     */

    @FXML
    void handleSaveButtonClick(ActionEvent event) 
    {
    	if( !checkFields() )
    	{
    		JOptionPane.showMessageDialog(null,"Error : There are some empty or uncorrect fields","Error",JOptionPane.INFORMATION_MESSAGE);
    		return;
    	}
    	
    	saveInfoIntoMySqlTable();
    }
    
    /**
     * saveInfoIntoMySqlTable saves all the correct data into sales table in mySql. 
     * it also get a unique sale code for this sale and save it too.
     */
    
    public void saveInfoIntoMySqlTable()
    {	
    	String productName, discount, startDate, daysDuration,
    	startHour, hoursDuration, gasStation, pictureColor, saleCode;
    	
    	// part 1 : get all the values form the textFeilds and choiceBoxes
    	productName = productChoiceBox.getValue();
    	discount = discountChoiceBox.getValue();
    	
    	if( !isEdit || (isEdit && isDateClicked) )
    	{
    		startDate = startDatePicker.getValue().toString();
    	}
    	else
    	{
    		startDate = saleStartDateSS;
    	}
    	
    	daysDuration = daysDurationChoiceBox.getValue();
    	startHour = startHourChoiceBox.getValue();
    	hoursDuration = hoursDurationChoiceBox.getValue();
    	gasStation = gasStationChoiceBox.getValue();
    	pictureColor = color;
    	
    	// part 2 : give unique sale code to every sale
    	if(isEdit)
    	{
    		saleCode = saleCodeSS;
    		
    		String deleteQuery ="DELETE FROM sales WHERE saleCode = ?";
     	    ArrayList <String> items = new ArrayList<>();
     	    items.add(saleCodeSS);
     	   
     	    ClientUI.chat.accept("");
       		ChatClient.updateTable(deleteQuery, items);	
    	}
    	else
    	{	
    		String selectQuery = "select saleCode from sales";
        	ResultSet rs = null;
    		int maxSaleCode = 0;

    		try
    		{
    			ClientUI.chat.accept("");
    			rs = ChatClient.getTable(selectQuery);	
    			
    			while(rs.next())
    			{
    				if( Integer.parseInt(rs.getString("saleCode")) > maxSaleCode )
    				{
    					maxSaleCode = Integer.parseInt(rs.getString("saleCode"));
    				}
    			}
    		} 
    		catch (Exception e)
    		{
    			System.err.println("Error : MarketingManagerRates : client server problem");
    			e.printStackTrace();
    		}
    		
    		Integer max = maxSaleCode + 1;
    		saleCode = max.toString();
    	}
    	    	
    	// part 3 : update table
		ArrayList<String> arr = new ArrayList<>();
		String query = "INSERT INTO sales (productName, discount, startDate, "
				+ "daysDuration, startHour, hoursDuration, gasStation, pictureColor, saleCode) VALUES (?,?,?,?,?,?,?,?,?);";
		 
		arr.add(productName);
		arr.add(discount);
		arr.add(startDate);
		arr.add(daysDuration);
		arr.add(startHour);
		arr.add(hoursDuration);
		arr.add(gasStation);
		arr.add(pictureColor);
		arr.add(saleCode);
		
		try
		{
			ClientUI.chat.accept("");
			ChatClient.updateTable(query, arr);
			
			JOptionPane.showMessageDialog(null,"Sale details were saved","Success Message",JOptionPane.INFORMATION_MESSAGE);
			startDatePicker.setValue(null);
			productChoiceBox.getSelectionModel().clearSelection();
			 gasStationChoiceBox.getSelectionModel().clearSelection();
             discountChoiceBox.getSelectionModel().clearSelection();
			   startHourChoiceBox.getSelectionModel().clearSelection();
               daysDurationChoiceBox.getSelectionModel().clearSelection();
               hoursDurationChoiceBox.getSelectionModel().clearSelection();
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		} 
		catch (Exception e)
		{
			System.err.println("Error : MarketingManagerRates : client server problem");
			e.printStackTrace();
		}
    }

    /**
     * handleShowPreviewButtonClick shows how the sale is going to look in the users computer.
     * it shows exactly the way the sale was created (with the pictures, colors, information)
      * @param event - this event occurs when the user clicks on the show preview button
     */
    @FXML
    void handleShowPreviewButtonClick(ActionEvent event)
    {   
    	if( !checkFields() )
    	{
    		JOptionPane.showMessageDialog(null,"Error : There are some empty or uncorrect fields","Error",JOptionPane.INFORMATION_MESSAGE);
    		return;
    	}
    			
    	discountLabel.setText( discountChoiceBox.getValue()+"%" );
    	saleLabel1.setText("Only in " + gasStationChoiceBox.getValue());
    	saleLabel2.setText("We have an amazing discount");
    	saleLabel3.setText("of " + discountChoiceBox.getValue() + "% on " + productChoiceBox.getValue() );
    	
    	if( sum == 5 )
    	{
    		saleLabel4.setText("Only from " + startDatePicker.getValue() + " for " + daysDurationChoiceBox.getValue() + " days");
    		saleLabel5.setText("Start at " + startHourChoiceBox.getValue() + " for " + hoursDurationChoiceBox.getValue() + " hours");
    		System.out.println(startDatePicker.getValue());
    	}

		if( sum == 4 )
		{
			saleLabel4.setText("Only from " + startDatePicker.getValue() + " for " + daysDurationChoiceBox.getValue() + " days");
		}
     	
    	discountLabel.setDisable(false);
    	saleLabel1.setDisable(false);
    	saleLabel2.setDisable(false);
    	saleLabel3.setDisable(false);
    	saleLabel4.setDisable(false);
    	saleLabel5.setDisable(false);
    }
    
    /**
     * checkFields : this function checks that :
     * 1. all important fields are filled 
     * 2. all important fields are filled correctly
     * 3. unimportant fields are filled correctly as well (if they are filled)
     * the function update the "sum" variable to keep the number of rows in the sale
     * @return boolean - the function return true if all check passed successfully and false otherwise
     */
    
    public boolean checkFields()
    {   
    	sum = 0;
    	
    	if( productChoiceBox.getValue().equals("Choose") )
    	{
    		return false;
    	}
    	
    	sum++;
    	
    	if( discountChoiceBox.getValue().equals("Choose") )
    	{
    		return false;
    	}
    	
    	sum++;
    	
    	if( gasStationChoiceBox.getValue().equals("Choose") )
    	{
    		return false;
    	}
    	
    	sum++;
    	
    	if( !isEdit || (isEdit && isDateClicked) )
    	{
    		if( timeString.equals("") )
        	{
        		return false;
        	}
        	
        	if( !checkDate() )
        	{
        		return false;
        	}
    	}
    	else
    	{
    		
        	timeString = saleStartDateSS;
    	}
        	
    	sum++;
    	
    	String hoursDurationChoice =  hoursDurationChoiceBox.getValue();
    	String startHourChoice = startHourChoiceBox.getValue();
    	
    	if( startHourChoice != "Choose" && hoursDurationChoice == "Choose" )
    	{
    		return false;
    	}
    	
    	if( startHourChoice == "Choose" && hoursDurationChoice != "Choose" )
    	{
    		return false;
    	}
    	
    	if(startHourChoice != "Choose" && hoursDurationChoice != "Choose")
    	{
        	sum++;
    	}

    	return true;
    }
    
    /**
     * checkDate : this function checks if the inserted date is correct
     * it will be correct only if the date value is a date after todey's date
     * @return boolean - the function return true if all check passed successfully and false otherwise
     */
    
    public boolean checkDate()
    {
    	// if year is bigger than this year - return true
    	// if year is now and month is bigger then this month - return true
    	// if year is now and month is now and day is bigger then this day - return true
    	// else return false
    	
    	LocalDate date = LocalDate.now();

    	if( date.getYear() < startDatePicker.getValue().getYear() )
    	{
    		return true;
    	}
    	
    	if( date.getYear() == startDatePicker.getValue().getYear() )
    	{
    		if( date.getMonthValue() < startDatePicker.getValue().getMonthValue() )
        	{
    			return true;
        	} 		
    	}
    	
    	if( date.getYear() == startDatePicker.getValue().getYear() )
    	{
    		if( date.getMonthValue() == startDatePicker.getValue().getMonthValue() )
        	{
    			if(date.getDayOfMonth() < startDatePicker.getValue().getDayOfMonth() )
    	    	{
    				return true;
    	    	}
        	} 		
    	}
    	
    	return false;
    }
}
