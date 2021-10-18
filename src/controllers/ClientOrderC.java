
package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import entities.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
/**
 * this class is the controller of the client order page , it gives the client the option to order fuel for home heating taking consideration all sales that the client has
 *
 */
public class ClientOrderC implements Initializable 
{

	/**
	 * JavaFX parameters
	 */
    @FXML
    private Label userWelcomeLabel;
    
    @FXML
    private Button checkDiscount;
    
    @FXML
    private ComboBox<String> comboboxgasStation;
    
    @FXML
    private TextField saleCodeTextField;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Button btnOrder;
    
    @FXML
    private Label labelfuelprice;

    @FXML
    private DatePicker dateArrivalDate;

    @FXML
    private TextField txtAddress;
    
    @FXML
    private Button showpricebtn;
    
    
    /**
     * variables that saves values like- discount, gas station name, username, password, price
     * and we use them for queries.
     */
    String discount;
    String gasStationName;
    String username,password;
    double totalPrice=0;
    int flag=0;
    Order ord=new Order();
    int orderNum;
    String gasStationConstant;
	String fuelTypeConstant;
	double totalpriceConstant;
	String insertedSaleCode = "";
	String sProductName = "";
	String sDiscount = "";
	String sStartDate = "";
	String sDaysDuration = "";
	String sStartHour = "";
	String sHoursDuration = "";
	String sGasStation = "";
	boolean flag1 =false;
    /**
     * function that checks if str is number
     * @param str
     * @return
     */
    public  boolean isNumericc(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    /**
     * this function handles "check discount" button - it calls to the functions that check validation 
     * if all succeed it shows the total price
     * @param event
     */
    @FXML
    void handleCheckDiscountButtonClick(ActionEvent event) {
    	if( !IsSaleCodeExist() || !IsGasStationMatch() || !IsProductMatch() || !IsDateMatch() || !IsHourMatch())
		{
			JOptionPane.showMessageDialog(null,"Error : Sale Code is not relevant here","Error",JOptionPane.INFORMATION_MESSAGE);
			flag1=true;
		}
		
		// passed all checks successfully
    	if(flag1==false) {
		updateTotalPrice();
		flag1=true;
		// the only specific line that is connected to this class
		labelfuelprice.setText(String.valueOf(totalpriceConstant));
		
    	}
    }
    /**
     * this function checks if the inserted code is exists
     * @return
     */
    public boolean IsSaleCodeExist()
	{
		insertedSaleCode = saleCodeTextField.getText();

		ResultSet rs = null;
		ArrayList<String> arr = new ArrayList<>();
		
		String selectQuery = "select * FROM sales where saleCode = ?";
		arr.add(insertedSaleCode);
		
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, arr);
			
			if(rs.equals(null))
			{
				return false;
			}
			
			while(rs.next())
			{
				sProductName = rs.getString("productName");
				sDiscount = rs.getString("discount");
				sStartDate = rs.getString("startDate");
				sDaysDuration = rs.getString("daysDuration");
				sStartHour = rs.getString("startHour");
				sHoursDuration = rs.getString("hoursDuration");
				sGasStation = rs.getString("gasStation");	
			}
		} 
		catch (Exception e)
		{	
			return false;
			// System.err.println("Error : MarketingRepUpdateFromEmployee : client server problem");
			// e.printStackTrace();	
		}
		
		return true;
	}
    /**
	 * this function check if the gas station  that client inserted is valid
	 * @return
	 */
	public boolean IsGasStationMatch()
	{
		if( gasStationConstant.equals(sGasStation) )
		{
			return true;
		}
		
		return false;
	}
	/**
	 * this function check if the product  that client inserted is valid
	 * @return
	 */
	public boolean IsProductMatch()
	{
		if( fuelTypeConstant.equals(sProductName) )
		{
			return true;
		}
		
		return false;
	}
	/**
	 * this function check if the date duration that client inserted is valid
	 * @return
	 */
	public boolean IsDateMatch()
	{
		LocalDate date = LocalDate.now();
			
    	LocalDate ssStartDate = null;
    	ssStartDate =  LocalDate.parse(sStartDate);
		
		// check 1 : it is this year, this month, this day
    	if( date.getYear() == ssStartDate.getYear())
    	{
    		if( date.getMonthValue() == ssStartDate.getMonthValue() )
        	{
    			if(date.getDayOfMonth() == ssStartDate.getDayOfMonth() )
    	    	{
    				return true;
    	    	}
        	} 	
    	}
    	
    	// check 2 : it is this year, this month and this days duration
    	if( date.getYear() == ssStartDate.getYear() )
    	{
    		if( date.getMonthValue() == ssStartDate.getMonthValue() )
        	{
    			int totalSaleDate = ssStartDate.getDayOfMonth() + Integer.parseInt(sDaysDuration) - 1;
				
				if(date.getDayOfMonth() < totalSaleDate )
				{
					return true;
				}
        	} 		
    	}
    	
		return false;
	}
	/**
	 * this function check if the hours duration that client inserted is valid
	 * @return
	 */
	public boolean IsHourMatch()
	{
		if(sStartHour.equals("Choose"))return true;
		// part 1 : handle hour right now
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		
		System.out.println("hour : " + hour);
		
		// part 2 : handle sale hour
		char hour1 = sStartHour.charAt(0);
		char hour2 = sStartHour.charAt(1);
		
		int hourS1 = Character.getNumericValue(hour1) * 10; 
		int hourS2 = Character.getNumericValue(hour2); 
		int hourS12 = hourS1 + hourS2;
		int hoursS123 = hourS12 + Integer.parseInt(sHoursDuration) - 1;
		
		System.out.println("hourS12 : " + hourS12);
		
		if(hour == hourS12)
		{
			return true;
		}
		
		if(hour > hourS12 && hour < hoursS123)
		{
			return true;
		}
		
		return false;
	}
	/**
	 * this function updates total price using the client discount
	 */
	public void updateTotalPrice()
	{
		double totalDiscount =  totalpriceConstant * Integer.parseInt(sDiscount);
		totalDiscount = totalDiscount / 100;
		totalpriceConstant -= totalDiscount;
	}

    
    
    /**
     * after choosing gasStation- it enables all button and text fields
     * @param event
     */
    @FXML
    void handleComboBox(ActionEvent event) {
    	dateArrivalDate.setDisable(false);
    	showpricebtn.setDisable(false);
    	txtQuantity.setDisable(false);
		txtAddress.setDisable(false);
		saleCodeTextField.setDisable(false);
    }
    
    /**
     * handle order button-
     * function that checks if all fields are okay or missing
     * if everything is okay- you can order successfully and your order will be added to the DB(orders),(purchases)
     * and automatically will update the stock (products table)
     * if there's missing fields or incorrect fields- the system will throw error message
     * @param event
     */
    @FXML
    void handleOrderButton(ActionEvent event) 

    {
    	String query1="Select * From Orders";
    	ClientUI.chat.accept("");
    	ResultSet r=ChatClient.getTable(query1);
    	try {
    		if(r!=null)
    			r.last();
    			orderNum=r.getRow();
    			orderNum++;
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	
    	if((txtQuantity.getText().equals("")) || ( txtAddress.getText().equals("")) || (dateArrivalDate.getValue()==null))
    	{
    		JOptionPane.showMessageDialog(null,"fields are empty","error",JOptionPane.INFORMATION_MESSAGE);
    	}
    	else if((txtQuantity.getText().equals("")) || ( txtAddress.getText().equals("")))
    			JOptionPane.showMessageDialog(null,"fields are empty","error",JOptionPane.INFORMATION_MESSAGE);
    	else if((txtAddress.getText().equals("")) || (dateArrivalDate.getValue()==null))
    			JOptionPane.showMessageDialog(null,"fields are empty","error",JOptionPane.INFORMATION_MESSAGE);
    	else if((txtQuantity.getText().equals("")) || (dateArrivalDate.getValue()==null))
    			JOptionPane.showMessageDialog(null,"fields are empty","error",JOptionPane.INFORMATION_MESSAGE);
    	
    	else if(txtAddress.getText().equals(""))
    	{
    		JOptionPane.showMessageDialog(null,"Please enter address","error",JOptionPane.INFORMATION_MESSAGE);
    	}
    	else if(dateArrivalDate.getValue()==null)
    	{
    		JOptionPane.showMessageDialog(null,"Please enter arival date","error",JOptionPane.INFORMATION_MESSAGE);
    	}
    	else if((dateArrivalDate.getValue().compareTo(java.time.LocalDate.now()))<0) 
    	{
    		JOptionPane.showMessageDialog(null,"Please enter  a vaild arival date","error",JOptionPane.INFORMATION_MESSAGE);
    	}
    	else if(txtQuantity.getText().equals(""))
    	{
    		JOptionPane.showMessageDialog(null,"Please enter quantity","error",JOptionPane.INFORMATION_MESSAGE);
    	}
    	else
    	{//if everything is okay
    		ord.setAddress(txtAddress.getText());
        	ord.setQuantity(Integer.parseInt(txtQuantity.getText()));
        	ord.setArrivalDate(dateArrivalDate.getValue());
        	String gasStation=null;
        	String gasStationMan=null;
    		ArrayList<String>order=new ArrayList<String>();
    		ArrayList<String>order1=new ArrayList<String>();
    		ArrayList<String>order2=new ArrayList<String>();
    		ArrayList<String>stock=new ArrayList<String>();
    		
    		ArrayList<String>stock2=new ArrayList<String>();
    		ArrayList<String>req=new ArrayList<String>();
    		ArrayList<String>parameters=new ArrayList<String>();
    		double amountInStock=0,threshold=0;
    		order.add(String.valueOf(orderNum));
    		order.add(String.valueOf(ord.getOrderDate()));
    		order.add(ord.getAddress());
    		order.add(String.valueOf(ord.getArrivalDate()));
    		order.add(String.valueOf(ord.getQuantity()));
    		order.add(LoginControllerC.userin);
    		gasStationName=comboboxgasStation.getValue();
    		//gasStationConstant=gasStationName;
    		
    	
    		    
    	    String query= "INSERT INTO orders (orderNumber,orderDate,address,arrivalDate,quantity,username) VALUES (?,?,?,?,?,?);";
    	    ChatClient.updateTable(query, order);
    	    order1.add(LoginControllerC.userin);
    	    order1.add(String.valueOf(ord.getOrderDate()));
    	    order1.add(totalPrice+"");
    	    order1.add("homeHeatingFuel");
    	    
    	    order1.add(gasStationName);
    	    String query3= "INSERT INTO purchases (username,date,totalPrice,fuelType,stationName) VALUES (?,?,?,?,?);";
    	    ChatClient.updateTable(query3, order1);
    	    
    	    stock.add(LoginControllerC.userin);
 	    	
    		 String sql="SELECT gasStation1 FROM purchaseplan WHERE username=?";
   		 ResultSet rs2=ChatClient.selectWithParameters(sql, stock);
   		 try {
				while(rs2.next())
				{
					gasStation=rs2.getString("gasStation1");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
    	 }
    	 
	        order2.add("homeHeatingFuel");
	        order2.add(gasStation);
    	    String getAmount="SELECT amountInStock,thresholdLevel FROM products WHERE productName=? and gasStationName=?";
    	    
 	    	   ClientUI.chat.accept("");
 	    	   ResultSet rs1 = ChatClient.selectWithParameters(getAmount, order2);
 	    	   try {
				rs1.next();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 	    	  try {
				amountInStock=Double.valueOf(rs1.getString("amountInStock"));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 	    	 try {
				threshold=Double.valueOf(rs1.getString("thresholdLevel"));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 	    	 amountInStock-=ord.getQuantity();
 	    	 if(amountInStock<=threshold)
 	    	 {
 	    		 
 	    		 stock2.add(gasStation);
 	    		stock2.add("gas station manager");
 	    		 String getman="SELECT username FROM employees WHERE gasStation=? and part=?";
 	    	    
 	 	    	   ClientUI.chat.accept("");
 	 	    	   ResultSet rs3 = ChatClient.selectWithParameters(getman, stock2);
 	 	    	   try {
					rs3.next();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 	 	    	  try {
					gasStationMan=rs3.getString("username");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 	    		 req.add(String.valueOf(ord.getOrderDate()));
 	    		 req.add("null");
 	    		 req.add(gasStationMan);
 	    		 req.add("Home Heating");
 	    		 req.add(1000*threshold+"");
 	    		req.add("null");
 	    		String request= "INSERT INTO requests (date,supplierName,gasStationManager,productName,productAmount,delivered) VALUES (?,?,?,?,?,?);";
 	    		 ChatClient.updateTable(request, req);
 	        	    
 	    	    }
 	    	ArrayList<String> a = new ArrayList<>();
 		    String queryreportt = "INSERT INTO salesreport(saleCode, username, cost, gasStation) VALUES (?,?,?,?);";
 		    		 if(saleCodeTextField.getText().isEmpty())	  a.add("0");
 		    	else  a.add(saleCodeTextField.getText());
 		    a.add(LoginControllerC.userin);
 		    a.add(String.valueOf(totalPrice));
 		    a.add(gasStationConstant);
 		    		
 		    ClientUI.chat.accept("");
 		    ChatClient.updateTable(queryreportt, a);
 	    	 
 	    	String updateQuery = "UPDATE products " + " SET amountInStock = ? WHERE gasStationName = ? and productName=?";

       	 parameters.add(amountInStock+"");
       	 parameters.add(gasStation);
       	parameters.add("homeHeatingFuel");

       	 ClientUI.chat.accept("");
		    ChatClient.updateTable(updateQuery, parameters);
    	    
    		JOptionPane.showMessageDialog(null,"ordered successfully","Success",JOptionPane.INFORMATION_MESSAGE);
    	     try {
    	    	 labelfuelprice.setText("");
    	    	 txtAddress.clear();
    	    	 txtQuantity.clear();
    	    	 dateArrivalDate.setValue(null);
    	    	 saleCodeTextField.clear();
    	     	//back to home
    	  	}
    		catch(Exception e)
    		  {
    			e.printStackTrace();
    		  }
    	}
    	
    }
    
    /**
     * handle show price button- will show the price after inserting the quantity(if okay)
     * this fuction gets the home heating price from DB
     * @param event
     */
    @FXML
    void handleshowpricebutton(ActionEvent event)
    {
    	
    	gasStationConstant=comboboxgasStation.getValue();
    	if(isNumericc(this.txtQuantity.getText())==false)
		{
	JOptionPane.showMessageDialog(null,"quantity is illegal","error",JOptionPane.INFORMATION_MESSAGE);
	txtQuantity.clear();
		}
    	if((Integer.parseInt(txtQuantity.getText()))<0 || (Integer.parseInt(txtQuantity.getText())>10000000))
		{
			JOptionPane.showMessageDialog(null,"quantity is either under 0 or below 1500","error",JOptionPane.INFORMATION_MESSAGE);
			txtQuantity.clear();
		}
    	else if(txtQuantity.getText().equals(""))
    	{
    		JOptionPane.showMessageDialog(null,"enter quantity","error",JOptionPane.INFORMATION_MESSAGE);
    	}
    	
    	else {
    	String getFuelPrice="SELECT rate FROM products WHERE productName=\"homeHeatingFuel\" ";
   	 
	    try {
	    	
	    	   ClientUI.chat.accept("");
	    	   ResultSet rs = ChatClient.getTable(getFuelPrice);
	    	   rs.next();
	    	   totalPrice=(Integer.parseInt(txtQuantity.getText()))*(Integer.parseInt(rs.getString("rate"))*2/100);
	    	   if(Integer.parseInt(txtQuantity.getText())>0 && Integer.parseInt(txtQuantity.getText())<600)
	    	   {
	      			totalPrice=(Double.parseDouble(txtQuantity.getText()))*(Double.parseDouble(rs.getString("rate")));
	      			totalPrice=totalPrice*98/100;
	      			System.out.println("fff");
	      			totalpriceConstant=totalPrice;
	    	   }
	    	   else if(Integer.parseInt(txtQuantity.getText())>=600 && Integer.parseInt(txtQuantity.getText())<800)
	    	   {
	    		   totalPrice=(Double.parseDouble(txtQuantity.getText()))*(Double.parseDouble(rs.getString("rate")));	
	    		   totalPrice=totalPrice*97/100;
	    		   totalpriceConstant=totalPrice;
	    	   }
	    	   else if(Integer.parseInt(txtQuantity.getText())>=800 && Integer.parseInt(txtQuantity.getText())<10000000)
	    	   {
	    		   totalPrice=(Double.parseDouble(txtQuantity.getText()))*(Double.parseDouble(rs.getString("rate")));
	    		   totalPrice=totalPrice*96/100;
	    		   totalpriceConstant=totalPrice;
	    	   }
	    	   
	    	   labelfuelprice.setText("Total price : "+totalpriceConstant+" ils");
	    	   btnOrder.setDisable(false);
   	}
   	catch(Exception e)
   	{
   		e.printStackTrace();
   	}
	    
	 
    	}
    }

    /**
     * disabling all buttons until you choose gas station
     * 
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnOrder.setDisable(true);
		txtQuantity.setDisable(true);
		txtAddress.setDisable(true);
		showpricebtn.setDisable(true);
		dateArrivalDate.setDisable(true);
		saleCodeTextField.setDisable(true);
		fuelTypeConstant="homeHeatingFuel";
		
		
		ObservableList<String> gasstaionlist=FXCollections.observableArrayList();
    	String querygas="SELECT gasstation1,gasstation2,gasstation3 FROM purchaseplan WHERE username=?";
    	ArrayList<String>arr=new ArrayList<String>();
    	arr.add(LoginControllerC.userin);
    	ResultSet r=ChatClient.selectWithParameters(querygas,arr);
    	try {
    		while(r.next())
    			{
    				gasstaionlist.add(r.getString("gasstation1"));
    				gasstaionlist.add(r.getString("gasstation2"));
    				gasstaionlist.add(r.getString("gasstation3"));
    				
    			}
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	comboboxgasStation.setItems(gasstaionlist);
		
		
	}

}
