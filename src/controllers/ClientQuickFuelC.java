package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * this class is the controller of the client quick fuel page , it's reachable by an automatic process that simulate the job of CNF sensor of the system 
 *
 */
public class ClientQuickFuelC implements Initializable{
	
	/**
	 * JavaFX variables
	 */
	 @FXML
	 private TextField saleCodeTextField;
	
	 @FXML
	 private Label lblgasS;
	 
    @FXML
    private Label userWelcomeLabel;

    @FXML
    private Label lblClientSubc;

    @FXML
    private Label lblClientDiscount;

    @FXML
    private CheckBox chkBoxFullTank;

    @FXML
    private Button checkDiscount;
    
    @FXML
    private TextField txtMoneyAmount;

    @FXML
    private CheckBox chcBoxMoneyAmount;

    @FXML
    private Label lblPriceFuelToday;
    
    @FXML
    private Label lblPriceFuelToday1;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Label usernamewelcome;
    
    @FXML
    private Button startFuelbtn;
    
    @FXML
    private Button btncredit;

    @FXML
    private Button btncash;
    
   

    /**
     * variables that saves the values of gasstaionname,price,fuel type..
     * flag money and flag full is for the check boxes
     */
    int flag=0;
    int flagMoney=0;
     int flagFull=0;
    private static String gasStationName;
    double totalprice;
    private String fuelType;
    private double full;
    Double quan;
    String gasStationConstant;
	String fuelTypeConstant;
	double totalpriceConstant;
	boolean flag1 =false;
	String insertedSaleCode = "";
	String sProductName = "";
	String sDiscount = "";
	String sStartDate = "";
	String sDaysDuration = "";
	String sStartHour = "";
	String sHoursDuration = "";
	String sGasStation = "";
    
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
     * when choosing paying with cash-it will send you to the register and disconnect you
     * it also enters your purchase to the purchases table, updates stock.
     * @param event
     */
    @FXML
    void handleCashButton(ActionEvent event)
    {
    	ArrayList<String>order2=new ArrayList<String>();
		ArrayList<String>stock2=new ArrayList<String>();
		ArrayList<String>req=new ArrayList<String>();
		ArrayList<String>parameters=new ArrayList<String>();
		double amountInStock=0,threshold=0;
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	String gasStationMan=null;
		String dateString = format.format( new Date()   );
		
		ArrayList<String>order1=new ArrayList<String>();
		order1.add(LoginControllerC.userin);
		order1.add(String.valueOf(dateString));
		order1.add(String.valueOf(totalprice));
		order1.add(String.valueOf(fuelType));
		order1.add(gasStationName);
		
		String query3= "INSERT INTO purchases (username,date,totalPrice,fuelType,stationName) VALUES (?,?,?,?,?);";
	    ChatClient.updateTable(query3, order1);
	    order2.add(String.valueOf(fuelType));/////////////////////////////////////////////////////////////////////////////////////
        order2.add(gasStationName);
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
	    	 
	    	 amountInStock-=quan;////////////////////*******/////////////////
	    	 if(amountInStock<=threshold)
	    	 {
	    		 
	    		 stock2.add(gasStationName);
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
	    		 req.add(String.valueOf(dateString));
	    		req.add("null");
	    		 req.add(gasStationMan);
	    		 req.add(String.valueOf(fuelType));
	    		 req.add(1000*threshold+"");
	    		req.add("null");
	    		 String request= "INSERT INTO requests (date,supplierName,gasStationManager,productName,productAmount,delivered) VALUES (?,?,?,?,?,?);";
	    		 ChatClient.updateTable(request, req);
	        	    
	    	    }
	    	String updateQuery = "UPDATE products " + " SET amountInStock = ? WHERE gasStationName = ? and productName=?";

   	 parameters.add(amountInStock+"");
   	 parameters.add(gasStationName);
   	parameters.add(String.valueOf(fuelType));

   	 ClientUI.chat.accept("");
	    ChatClient.updateTable(updateQuery, parameters);
	    
		JOptionPane.showMessageDialog(null,"ordered successfully","Success",JOptionPane.INFORMATION_MESSAGE);
	     
	    ArrayList<String> arr = new ArrayList<>();
	    String queryreport = "INSERT INTO salesreport(saleCode, username, cost, gasStation) VALUES (?,?,?,?);";
	    	if(saleCodeTextField.getText().isEmpty())	  arr.add("0");
	    	else  arr.add(saleCodeTextField.getText());
	    arr.add(LoginControllerC.userin);
	    arr.add(String.valueOf(totalprice));
	    arr.add(gasStationName);
	    		
	    ClientUI.chat.accept("");
	    ChatClient.updateTable(queryreport, arr);
	    
    	JOptionPane.showMessageDialog(null,"Thank you for buying our fuel!\n please go and make your payment at the register","Cash",JOptionPane.INFORMATION_MESSAGE);
    	ArrayList<String> logout =new ArrayList<String>();
    	//logout.add("Logout");
    	logout.add(LoginControllerC.userin);
    	
    	logout.add(LoginControllerC.vehiclenum);
    	
    	String query="DELETE FROM loggedquickfuel WHERE username = ? and vehiclenum = ?";
    	
    	ClientUI.chat.accept("");
    	ChatClient.updateTable(query, logout);
     
  	JOptionPane.showMessageDialog(null,"logged out","logout",JOptionPane.INFORMATION_MESSAGE);
      try {
			((Node) event.getSource()).getScene().getWindow().hide(); 
			Pane root = FXMLLoader.load(getClass().getResource("gui/LoginView.fxml"));//build the gui
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
   	}
	catch(Exception e)
	  {
		e.printStackTrace();
	  }
    }

    /**
     * when choosing paying with credit-it will send your details to the external system and disconnect you
     * it also enters your purchase to the purchases table, updates stock.
     * @param event
     */
    @FXML
    void handleCreditCardButton(ActionEvent event)
    {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		String dateString = format.format( new Date()   );
		String gasStationMan=null;
		ArrayList<String>order1=new ArrayList<String>();
		ArrayList<String>order2=new ArrayList<String>();
		ArrayList<String>stock2=new ArrayList<String>();
		ArrayList<String>req=new ArrayList<String>();
		ArrayList<String>parameters=new ArrayList<String>();
		double amountInStock=0,threshold=0;
		order1.add(LoginControllerC.userin);
		order1.add(String.valueOf(dateString));
		order1.add(String.valueOf(totalprice));
		order1.add(String.valueOf(fuelType));
		order1.add(gasStationName);
		
		String query3= "INSERT INTO purchases (username,date,totalPrice,fuelType,stationName) VALUES (?,?,?,?,?);";
	    ChatClient.updateTable(query3, order1);
	    order2.add(String.valueOf(fuelType));/////////////////////////////////////////////////////////////////////////////////////
        order2.add(gasStationName);
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
	    	 
	    	 amountInStock-=quan;////////////////////*******/////////////////
	    	 if(amountInStock<=threshold)
	    	 {
	    		 
	    		 stock2.add(gasStationName);
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
	    		 req.add(String.valueOf(dateString));
	    		req.add("null");
	    		 req.add(gasStationMan);
	    		 req.add(String.valueOf(fuelType));
	    		 req.add("1000000");
	    		req.add("null");
	    		 String request= "INSERT INTO requests (date,supplierName,gasStationManager,productName,productAmount,delivered) VALUES (?,?,?,?,?,?);";
	    		 ChatClient.updateTable(request, req);
	        	    
	    	    }
	    	String updateQuery = "UPDATE products " + " SET amountInStock = ? WHERE gasStationName = ? and productName=?";

   	 parameters.add(amountInStock+"");
   	 parameters.add(gasStationName);
   	parameters.add(String.valueOf(fuelType));

   	 ClientUI.chat.accept("");
	    ChatClient.updateTable(updateQuery, parameters);
	    
		JOptionPane.showMessageDialog(null,"ordered successfully","Success",JOptionPane.INFORMATION_MESSAGE);
	     
	////////////////////////////////////////////////////////////////////////////////////////
	    ArrayList<String> arr = new ArrayList<>();
	    String queryreport = "INSERT INTO salesreport(saleCode, username, cost, gasStation) VALUES (?,?,?,?);";
	    		 if(saleCodeTextField.getText().isEmpty())	  arr.add("0");
	    	else  arr.add(saleCodeTextField.getText());
	    arr.add(LoginControllerC.userin);
	    arr.add(String.valueOf(totalprice));
	    arr.add(gasStationName);
	    		
	    ClientUI.chat.accept("");
	    ChatClient.updateTable(queryreport, arr);

	    
    	JOptionPane.showMessageDialog(null,"Your Credit card details has sent to the system successfully!\n Thank you for buying our fuel!","Credit",JOptionPane.INFORMATION_MESSAGE);
    	ArrayList<String> logout =new ArrayList<String>();
    	//logout.add("Logout");
    	logout.add(LoginControllerC.userin);
    	
    	logout.add(LoginControllerC.vehiclenum);
    	
    	String query="DELETE FROM loggedquickfuel WHERE username = ? and vehiclenum = ?";
    	
    	ClientUI.chat.accept("");
    	ChatClient.updateTable(query, logout);
     
  	JOptionPane.showMessageDialog(null,"logged out","logout",JOptionPane.INFORMATION_MESSAGE);
      try {
			((Node) event.getSource()).getScene().getWindow().hide(); 
			Pane root = FXMLLoader.load(getClass().getResource("gui/LoginView.fxml"));//build the gui
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
   	}
	catch(Exception e)
	  {
		e.printStackTrace();
	  }
    }

    /**
     * this function handles "check discount" button - it calls to the functions that check validation 
     * if all succeed it shows the total price
     * @param event
     */
    @FXML
    void handleCheckDiscountButtonClick(ActionEvent event) {
        
    	if( !IsSaleCodeExist() || !IsGasStationMatch() || !IsProductMatch() || !IsDateMatch() || !IsHourMatch() )
		{
			JOptionPane.showMessageDialog(null,"Error : Sale Code is not relevant here","Error",JOptionPane.INFORMATION_MESSAGE);
			flag1=true;
			
		}
		
		// passed all checks successfully
		if(flag1==false) {
		updateTotalPrice();
		flag1=true;
		// the only specific line that is connected to this class
		lblTotalPrice.setText(String.valueOf(totalpriceConstant));
		
		
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
     * start button- will start you fuel
     * first checking if all fields are okay
     * if it's a company client- then he wont go to pay money, the system sends 
     * a message that the money amount has sent to his company and disconnect him 
     * and also his purchase added to the right tables.
     * if it's a regular client- he proceeds to the paying part.
     * @param event
     */
    @FXML
    void handleStartButton(ActionEvent event)
    {
    	if(flagFull==0 && flagMoney==0)
    	{
    		JOptionPane.showMessageDialog(null,"choose one way","error",JOptionPane.INFORMATION_MESSAGE);
    	}
    	else if(flagMoney==1)
    	{
    		if(txtMoneyAmount.getText().equals(""))
    		{
    			JOptionPane.showMessageDialog(null,"enter money","error",JOptionPane.INFORMATION_MESSAGE);
    		}
    		if(isNumericc(this.txtMoneyAmount.getText())==false)
    		{
    			JOptionPane.showMessageDialog(null,"money is illegal","error",JOptionPane.INFORMATION_MESSAGE);
    			txtMoneyAmount.clear();
    		}
    		else 
    		{
    			String dis=lblClientDiscount.getText();
    			String newdis=dis.substring(0, dis.length()-1);
    			Double discount=(100-Double.valueOf(newdis))/100;
    			quan=(Double.parseDouble(txtMoneyAmount.getText())/(Double.parseDouble(lblPriceFuelToday.getText()))*discount);
    			totalprice=Double.parseDouble(txtMoneyAmount.getText());
    			System.out.println("tp"+totalprice); 
		    	lblTotalPrice.setText(txtMoneyAmount.getText());
		    	//System.out.println(Double.parseDouble(lblPriceFuelToday.getText()));
		    	//startFuelbtn.setDisable(false);
		    	saleCodeTextField.setDisable(false);
    		}
    	}
    	else if(flagFull==1)
    	{
    		String dis=lblClientDiscount.getText();
			String newdis=dis.substring(0, dis.length()-1);
			Double discount=(100-Double.valueOf(newdis))/100;
			totalprice=(Double.parseDouble(lblPriceFuelToday.getText())*full*discount);
    		System.out.println("tp"+totalprice);
    		quan=full;
			/*if(!newdis.equals("0"))
			{
    		totalprice=(Double.parseDouble(lblPriceFuelToday.getText())*full*discount);
    		System.out.println("tp"+totalprice);
    		quan=full;
			}
			else {
				quan=full;
				totalprice=(Double.parseDouble(lblPriceFuelToday.getText())*full);
				System.out.println("tp"+totalprice);
			}*/
    		
    		lblTotalPrice.setText(String.valueOf(totalprice));
    		saleCodeTextField.setDisable(false);
    		//startFuelbtn.setDisable(false);
    		//System.out.println(quan);
    	}
    	
    	
    	if(flag==2 || flag==0)
    	{
    		JOptionPane.showMessageDialog(null,"you have to choose one","error",JOptionPane.INFORMATION_MESSAGE);
    		System.out.println(flag);
    	}
		
		else
		{
			if(flagFull==1) //full tank was selected!
			{
				//JOptionPane.showMessageDialog(null,"Fuel Started- full tank","Start",JOptionPane.INFORMATION_MESSAGE);
				ArrayList<String>parameters=new ArrayList<String>();
				parameters.add(LoginControllerC.userin);
				String query="SELECT * FROM companyclients WHERE username=?";////////////if its company
				ResultSet rs1 = ChatClient.selectWithParameters(query, parameters);
				try
				{
					if(rs1.next())
					{
						JOptionPane.showMessageDialog(null,"Fuel Started\nyour receipt sent to the company","Start",JOptionPane.INFORMATION_MESSAGE);
						ArrayList<String> logout =new ArrayList<String>();
				    	//logout.add("Logout");
				    	logout.add(LoginControllerC.userin);
				    	
				    	logout.add(LoginControllerC.vehiclenum);
				    	
				    	String query1="DELETE FROM loggedquickfuel WHERE username = ? and vehiclenum = ?";
				    	
				    	ClientUI.chat.accept("");
				    	ChatClient.updateTable(query1, logout);
				     
				  	JOptionPane.showMessageDialog(null,"logged out","logout",JOptionPane.INFORMATION_MESSAGE);
				      try {///////////////////////////////////////////////////
				    	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				  		String dateString = format.format( new Date()   );
				    	  String gasStationMan=null;
				  		ArrayList<String>order1=new ArrayList<String>();
				  		ArrayList<String>order2=new ArrayList<String>();
				  		ArrayList<String>stock2=new ArrayList<String>();
				  		ArrayList<String>req=new ArrayList<String>();
				  		ArrayList<String>parameters1=new ArrayList<String>();
				  		double amountInStock=0,threshold=0;
				  		order1.add(LoginControllerC.userin);
				  		order1.add(String.valueOf(dateString));
				  		order1.add(String.valueOf(totalprice));
				  		order1.add(String.valueOf(fuelType));
				  		order1.add(gasStationName);
				  		
				  		String query3= "INSERT INTO purchases (username,date,totalPrice,fuelType,stationName) VALUES (?,?,?,?,?);";
				  	    ChatClient.updateTable(query3, order1);
				  	    order2.add(String.valueOf(fuelType));/////////////////////////////////////////////////////////////////////////////////////
				          order2.add(gasStationName);
				  	    String getAmount="SELECT amountInStock,thresholdLevel FROM products WHERE productName=? and gasStationName=?";
				  	    
				  	    	   ClientUI.chat.accept("");
				  	    	   ResultSet rs11 = ChatClient.selectWithParameters(getAmount, order2);
				  	    	   try {
				  			rs11.next();
				  		} catch (SQLException e1) {
				  			// TODO Auto-generated catch block
				  			e1.printStackTrace();
				  		}
				  	    	  try {
				  			amountInStock=Double.valueOf(rs11.getString("amountInStock"));
				  		} catch (NumberFormatException e1) {
				  			// TODO Auto-generated catch block
				  			e1.printStackTrace();
				  		} catch (SQLException e1) {
				  			// TODO Auto-generated catch block
				  			e1.printStackTrace();
				  		}
				  	    	 try {
				  			threshold=Double.valueOf(rs11.getString("thresholdLevel"));
				  		} catch (NumberFormatException e1) {
				  			// TODO Auto-generated catch block
				  			e1.printStackTrace();
				  		} catch (SQLException e1) {
				  			// TODO Auto-generated catch block
				  			e1.printStackTrace();
				  		}
				  	    	 
				  	    	 amountInStock-=quan;////////////////////*******/////////////////
				  	    	 if(amountInStock<=threshold)
				  	    	 {
				  	    		 
				  	    		 stock2.add(gasStationName);
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
				  	    		 req.add(String.valueOf(dateString));
				  	    		req.add("null");
				  	    		 req.add(gasStationMan);
				  	    		 req.add(String.valueOf(fuelType));
				  	    		 req.add("1000000");
				  	    		req.add("null");
				  	    		 String request= "INSERT INTO requests (date,supplierName,gasStationManager,productName,productAmount,delivered) VALUES (?,?,?,?,?,?);";
				  	    		 ChatClient.updateTable(request, req);
				  	        	    
				  	    	    }
				  	    	String updateQuery = "UPDATE products " + " SET amountInStock = ? WHERE gasStationName = ? and productName=?";

				     	 parameters1.add(amountInStock+"");
				     	 parameters1.add(gasStationName);
				     	parameters1.add(String.valueOf(fuelType));

				     	 ClientUI.chat.accept("");
				  	    ChatClient.updateTable(updateQuery, parameters1);
				  	    
				  		JOptionPane.showMessageDialog(null,"ordered successfully","Success",JOptionPane.INFORMATION_MESSAGE);////////////////
							((Node) event.getSource()).getScene().getWindow().hide(); 
							Pane root = FXMLLoader.load(getClass().getResource("gui/LoginView.fxml"));//build the gui
							Scene scene = new Scene(root);
							Stage stage = new Stage();
							stage.setScene(scene);
							stage.show();
				   	}
					catch(Exception e)
					  {
						e.printStackTrace();
					  }
					}
					else
					JOptionPane.showMessageDialog(null,"Fuel Started","Start",JOptionPane.INFORMATION_MESSAGE);
				}
				catch(Exception e)
				  {
			       	e.printStackTrace();
				  }
			}
			else if(flagMoney==1) //money was selected!
			{
				if(txtMoneyAmount.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null,"Enter money amount","Error",JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					ArrayList<String>parameters=new ArrayList<String>();
					parameters.add(LoginControllerC.userin);
					String query="SELECT * FROM companyclients WHERE username=?";////////////if its company
					ResultSet rs1 = ChatClient.selectWithParameters(query, parameters);
					try
					{
						if(rs1.next())
						{
							JOptionPane.showMessageDialog(null,"Fuel Started\nyour receipt sent to the company","Start",JOptionPane.INFORMATION_MESSAGE);
							ArrayList<String> logout =new ArrayList<String>();
					    	//logout.add("Logout");
					    	logout.add(LoginControllerC.userin);
					    	
					    	logout.add(LoginControllerC.vehiclenum);
					    	
					    	String query1="DELETE FROM loggedquickfuel WHERE username = ? and vehiclenum = ?";
					    	
					    	ClientUI.chat.accept("");
					    	ChatClient.updateTable(query1, logout);
					     
					  	JOptionPane.showMessageDialog(null,"logged out","logout",JOptionPane.INFORMATION_MESSAGE);
					      try {  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

					  		String dateString = format.format( new Date()   );
					    	  String gasStationMan=null;
					  		ArrayList<String>order1=new ArrayList<String>();
					  		ArrayList<String>order2=new ArrayList<String>();
					  		ArrayList<String>stock2=new ArrayList<String>();
					  		ArrayList<String>req=new ArrayList<String>();
					  		ArrayList<String>parameters1=new ArrayList<String>();
					  		double amountInStock=0,threshold=0;
					  		order1.add(LoginControllerC.userin);
					  		order1.add(String.valueOf(dateString));
					  		order1.add(String.valueOf(totalprice));
					  		order1.add(String.valueOf(fuelType));
					  		order1.add(gasStationName);
					  		
					  		String query3= "INSERT INTO purchases (username,date,totalPrice,fuelType,stationName) VALUES (?,?,?,?,?);";
					  	    ChatClient.updateTable(query3, order1);
					  	    order2.add(String.valueOf(fuelType));/////////////////////////////////////////////////////////////////////////////////////
					          order2.add(gasStationName);
					  	    String getAmount="SELECT amountInStock,thresholdLevel FROM products WHERE productName=? and gasStationName=?";
					  	    
					  	    	   ClientUI.chat.accept("");
					  	    	   ResultSet rs11 = ChatClient.selectWithParameters(getAmount, order2);
					  	    	   try {
					  			rs11.next();
					  		} catch (SQLException e1) {
					  			// TODO Auto-generated catch block
					  			e1.printStackTrace();
					  		}
					  	    	  try {
					  			amountInStock=Double.valueOf(rs11.getString("amountInStock"));
					  		} catch (NumberFormatException e1) {
					  			// TODO Auto-generated catch block
					  			e1.printStackTrace();
					  		} catch (SQLException e1) {
					  			// TODO Auto-generated catch block
					  			e1.printStackTrace();
					  		}
					  	    	 try {
					  			threshold=Double.valueOf(rs11.getString("thresholdLevel"));
					  		} catch (NumberFormatException e1) {
					  			// TODO Auto-generated catch block
					  			e1.printStackTrace();
					  		} catch (SQLException e1) {
					  			// TODO Auto-generated catch block
					  			e1.printStackTrace();
					  		}
					  	    	 
					  	    	 amountInStock-=quan;////////////////////*******/////////////////
					  	    	 if(amountInStock<=threshold)
					  	    	 {
					  	    		 
					  	    		 stock2.add(gasStationName);
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
					  	    		 req.add(String.valueOf(dateString));
					  	    		req.add("null");
					  	    		 req.add(gasStationMan);
					  	    		 req.add(String.valueOf(fuelType));
					  	    		 req.add("1000000");
					  	    		req.add("null");
					  	    		 String request= "INSERT INTO requests (date,supplierName,gasStationManager,productName,productAmount,delivered) VALUES (?,?,?,?,?,?);";
					  	    		 ChatClient.updateTable(request, req);
					  	        	    
					  	    	    }
					  	    	String updateQuery = "UPDATE products " + " SET amountInStock = ? WHERE gasStationName = ? and productName=?";

					     	 parameters1.add(amountInStock+"");
					     	 parameters1.add(gasStationName);
					     	parameters1.add(String.valueOf(fuelType));

					     	 ClientUI.chat.accept("");
					  	    ChatClient.updateTable(updateQuery, parameters1);
					  	    
					  		JOptionPane.showMessageDialog(null,"ordered successfully","Success",JOptionPane.INFORMATION_MESSAGE);////////////////
								((Node) event.getSource()).getScene().getWindow().hide(); 
								Pane root = FXMLLoader.load(getClass().getResource("gui/LoginView.fxml"));//build the gui
								Scene scene = new Scene(root);
								Stage stage = new Stage();
								stage.setScene(scene);
								stage.show();
					   	}
						catch(Exception e)
						  {
							e.printStackTrace();
						  }
						}
						else
						JOptionPane.showMessageDialog(null,"Fuel Started","Start",JOptionPane.INFORMATION_MESSAGE);
					}
					catch(Exception e)
					  {
				       	e.printStackTrace();
					  }
					
				}
				//JOptionPane.showMessageDialog(null,"Fuel Started","Start",JOptionPane.INFORMATION_MESSAGE);
				txtMoneyAmount.clear();
				txtMoneyAmount.setDisable(true);
				
			}

	    	btncash.setDisable(false);
			btncredit.setDisable(false);

		}	
    	
    	gasStationConstant = gasStationName;
    	fuelTypeConstant = fuelType;
    	totalpriceConstant = totalprice;
    }
    
    /**
     * if chose Full tank- changing flag
     * @param event
     */
    @FXML
    void handlecheckBoxFullTank(ActionEvent event) {
    	flag++; //2
    	flagFull=1;
    }
    
    /**
     * if chose money- changing flag
     * @param event
     * @author Shirin
     */
    @FXML
    void handlecheckBoxMoneyAmount(ActionEvent event) {
    	flag++; //1
    	flagMoney=1;
    	txtMoneyAmount.setDisable(false);
    	
    }
    
   
    
    /**
     * initializing all the client's details-name,vehicles,purchase plan,discount
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> parameters=new ArrayList<String>();
    	String que="SELECT gasstation1 FROM purchaseplan WHERE username=? ORDER BY RAND() LIMIT 1;";
    	parameters.add(LoginControllerC.userin);
    	try {
	    	   ClientUI.chat.accept("");
	    	   ResultSet rs = ChatClient.selectWithParameters(que, parameters);
	    	   
	    	 
	    	  if( rs.next()) {
	    	   
	    	   gasStationName=rs.getString("gasstation1");
	    	   lblgasS.setText(gasStationName);
	    	   System.out.println(gasStationName);
	    	  }
    	}
    	catch(Exception e)
		 	{
		 		e.printStackTrace();
		 	}
		
		
		
		//disabling all buttons
		txtMoneyAmount.setDisable(true);
	  //  startFuelbtn.setDisable(true);
		btncash.setDisable(true);
		btncredit.setDisable(true);
		saleCodeTextField.setDisable(true);
		//checkDiscount

		
		 usernamewelcome.setText("Welcome "+LoginControllerC.userin+" "+LoginControllerC.vehiclenum);
		String query="SELECT purchaseplandescription FROM purchaseplan WHERE username=?";
		ClientUI.chat.accept("");
		ArrayList<String>param=new ArrayList<String>();
		param.add(LoginControllerC.userin);
		System.out.println(LoginControllerC.userin);
    	ResultSet rgas=ChatClient.selectWithParameters(query,param);
    	try {
    		while(rgas.next())
    			lblClientSubc.setText(rgas.getString("purchaseplandescription"));
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	////////////////////////////////////////////////////////////////////////
    	
    	String query2="SELECT discount FROM purchaseplan WHERE username=?";
		ClientUI.chat.accept("");
		ArrayList<String>param2=new ArrayList<String>();
		param2.add(LoginControllerC.userin);
    	ResultSet r2=ChatClient.selectWithParameters(query2,param2);
    	try {
    		while(r2.next())
    			lblClientDiscount.setText(r2.getString("discount"));
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	////////////////////////////////////////////////////////////////////////
    	
    	String query3="SELECT pumpNumber FROM vehicles WHERE username=?"; // 2 2
		ClientUI.chat.accept("");
		ArrayList<String>pump=new ArrayList<String>();
		pump.add(LoginControllerC.userin); 
    	ResultSet r3=ChatClient.selectWithParameters(query3,pump);
    	try {
    		while(r3.next())
    		{
    			if(r3.getInt("pumpNumber")%3==0)//scooter
    			{
    				String getFuelPrice="SELECT rate FROM products WHERE productName=\"scooterFuel\" ";
    		    	// totalPrice=0;
    			    try {
    			    	   ClientUI.chat.accept("");
    			    	   ResultSet rs = ChatClient.getTable(getFuelPrice);
    			    	   rs.next();
    			    	   fuelType="scooterFuel";
    			    	   lblPriceFuelToday1.setText("  scooter fuel: ");
    			    	   lblPriceFuelToday.setText((rs.getString("rate")));
    			    	   full=9;
 
    		    	}
    		    	catch(Exception e)
    		    	{
    		    		e.printStackTrace();
    		    	}
    				
    			}
    			if(r3.getInt("pumpNumber")%3==1)//gasoline
    			{
    				String getFuelPrice="SELECT rate FROM products WHERE productName=\"gasoline\" ";
    		    	// totalPrice=0;
    			    try {
    			    	   ClientUI.chat.accept("");
    			    	   ResultSet rs2 = ChatClient.getTable(getFuelPrice);
    			    	   rs2.next();
    			    	   fuelType="gasoline";
    			    	   lblPriceFuelToday1.setText("  gasoline fuel: ");
    			    	   lblPriceFuelToday.setText((rs2.getString("rate")));
    			    	   full=34;
 
    		    	}
    		    	catch(Exception e)
    		    	{
    		    		e.printStackTrace();
    		    	}
    				
    			}
    			if(r3.getInt("pumpNumber")%3==2)//diesel
    			{
    				String getFuelPrice="SELECT rate FROM products WHERE productName=\"diesel\" ";
    		    	// totalPrice=0;
    			    try {
    			    	   ClientUI.chat.accept("");
    			    	   ResultSet rs3 = ChatClient.getTable(getFuelPrice);
    			    	   rs3.next();
    			    	   fuelType="diesel";
    			    	   lblPriceFuelToday1.setText("  diesel fuel: ");
    			    	   lblPriceFuelToday.setText((rs3.getString("rate")));
    			    	   full=40;
 
    		    	}
    		    	catch(Exception e)
    		    	{
    		    		e.printStackTrace();
    		    	}
    			}
    		}
    			
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	///////////////////////////////////////////////////////////////////////
    	
	}
	
	
	/**
	 * disconnect client from system- delete all info from "loggedquickfuel" table
	 * @param event
	 */
	@FXML
    void handleDisconnectButton(ActionEvent event) {

	 	ArrayList<String> logout =new ArrayList<String>();
    	//logout.add("Logout");
    	logout.add(LoginControllerC.userin);
    	
    	logout.add(LoginControllerC.vehiclenum);
    	
    	String query="DELETE FROM loggedquickfuel WHERE username = ? and vehiclenum = ?";
    	
    	ClientUI.chat.accept("");
    	ChatClient.updateTable(query, logout);
     
  	JOptionPane.showMessageDialog(null,"logged out","logout",JOptionPane.INFORMATION_MESSAGE);
      try {
			((Node) event.getSource()).getScene().getWindow().hide(); 
			Pane root = FXMLLoader.load(getClass().getResource("gui/LoginView.fxml"));//build the gui
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
   	}
	catch(Exception e)
	  {
		e.printStackTrace();
	  }
    }


}
