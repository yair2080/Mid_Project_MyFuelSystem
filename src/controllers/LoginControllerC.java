package controllers;

import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * this class handles login gui , it logs in user according to his role, and make sure that user is not logged already
 *
 */
public class LoginControllerC { 

	/**
	 * JavaFX parameters
	 */
    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passWordTextField;

    @FXML
    private Button loginClientButton;

    @FXML
    private Button loginEmployeeButton;
    
    /**
     * static parameters so we can call them in other classes
     */
	public static String username,password;
	public static String vehiclenum;
	public static String vehiclenumUser;
	public static String userin=new String();
	
	/**
	 * function -get Userin
	 * @return userin
	 */
	public String getUserin() {
		return userin;
	}
	/**
	 * 
	 * @return vehicle number
	 */
	public static String getvehiclenumUser() {
		return vehiclenumUser;
	}
	public void setUserin(String userin) {
		LoginControllerC.userin = userin;
	}
	
	public String getvehicle() {
		return vehiclenum;
	}
	public void setvehicle(String vehiclenum) {
		LoginControllerC.vehiclenum = vehiclenum;
	}
	
	/**
	 * handle button "login client" - this button handles the case where you want to
	 * login as a client. If your username is not in the DB it will throw error message,
	 * and if your username is in the DB it will connect you to the "ClientMainView".
	 * and if youre a client then you can enjoy buying\ordering home fuel, getting sale code(whenever 
	 * there's a sale) and sending messages.
	 * @param event
	 */
    @FXML
    void handleloginClientButton(ActionEvent event)
    {
    	boolean flag=false;
    	ArrayList<String> parameters=new ArrayList<String>();
		 username = userNameTextField.getText();
		 password = passWordTextField.getText();
		if(username.equals("")||password.equals(""))
		{
			System.out.println("empty Fields");//need pop up
			JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);
			userNameTextField.clear();
			passWordTextField.clear();
		}
		else 
		{
			setUserin(username);
			parameters.add(username);
			parameters.add(password);
	    String query="SELECT * FROM logged WHERE username = ? and passwordun = ?";
	    ClientUI.chat.accept("");
	    ResultSet rs = ChatClient.selectWithParameters(query, parameters);	
	   
			
				try {
					if(rs.next())
					   {
						JOptionPane.showMessageDialog(null,"user is already logged","error",JOptionPane.INFORMATION_MESSAGE);
						try {
							flag=true;
						
							userNameTextField.clear();
							passWordTextField.clear();
					 	}
					catch(Exception e)
					  {
						e.printStackTrace();
					  }
					   }
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				if(flag==false) {
					    String query2 = "SELECT * FROM singleclient WHERE username = ? and passwordun = ?";
					    ResultSet rs1 = ChatClient.selectWithParameters(query2, parameters);	
                        
		             		try {
								if(rs1.next())
								{
									flag=true;
									try
									{
										((Node) event.getSource()).getScene().getWindow().hide(); 
								    	Pane root = FXMLLoader.load(getClass().getResource("gui/ClientMainView.fxml"));//build the gui
								    	Scene scene = new Scene(root);
								   		Stage stage = new Stage();
								   		stage.setScene(scene);
										stage.show();
								 	}
								    catch(Exception e)
									  {
								       	e.printStackTrace();
									  }		
								   String sql = "INSERT INTO logged (username,passwordun) VALUES (?,?);";
								   ChatClient.updateTable(sql, parameters);
     	   }
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				}

            if(flag==false)
             {
				String sql1 = "SELECT * FROM companyclients WHERE username = ? and passwordun = ?";
			    ResultSet rs2 = ChatClient.selectWithParameters(sql1, parameters);	
                
				try {
					if(rs2.next()) 
					{
						flag=true;
						try {
							((Node) event.getSource()).getScene().getWindow().hide(); 
							Pane root = FXMLLoader.load(getClass().getResource("gui/ClientMainView.fxml"));//build the gui
							Scene scene = new Scene(root);
							Stage stage = new Stage();
							stage.setScene(scene);
							stage.show();
					 	}
					catch(Exception e)
					  {
						e.printStackTrace();
					  }
						String sql4 = "INSERT INTO logged (username,passwordun) VALUES (?,?);";
						ChatClient.updateTable(sql4, parameters);
   }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
         
            }

          if(flag==false)
          {

	           	JOptionPane.showMessageDialog(null,"login is failed","error",JOptionPane.INFORMATION_MESSAGE);

	        	try {
		        	userNameTextField.clear();
		        	passWordTextField.clear();
                   	}
              	catch(Exception e)
	                {
	            	e.printStackTrace();
	                }
	        }
			
		}
    }

    /**
     *  handle button "login employee" - this button handles the case where you want to
	 * login as an employee. If your username is not in the DB it will throw error message,
	 * and if your username is in the DB it will connect you to the page that's right for the employee-
	 * this function checks all types of roles that we have in the DB and handles each one individually.
	 * say that you are a "supplier" then it will connect you to the supplier's page where you can do all 
	 * the supplier's functionality.. or "gas station manager" .....
	 * and if you're a client then you can enjoy buying\ordering home fuel, getting sale code(whenever 
	 * there's a sale) and sending messages.
	 * @param event
     */
    @FXML
    void handleloginEmployeeButton(ActionEvent event) 
    {
    	ArrayList<String> parameters=new ArrayList<String>();
    	boolean flag=false;
		username = userNameTextField.getText();
		password = passWordTextField.getText();
		if(username.equals("")||password.equals("")) {
			System.out.println("empty Fields");//need pop up
			JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);
			userNameTextField.clear();
			passWordTextField.clear();		
		}
		else {
			setUserin(username);
			parameters.add(username);
			parameters.add(password);
			String query="SELECT * FROM logged WHERE username = ? and passwordun = ?";
		    ResultSet rs = ChatClient.selectWithParameters(query, parameters);	
		
				
					try {
						if(rs.next())
						   {
							flag=true;
							JOptionPane.showMessageDialog(null,"user is already logged","error",JOptionPane.INFORMATION_MESSAGE);
							try {
								userNameTextField.clear();
								passWordTextField.clear();
						 	}
						catch(Exception e)
						  {
							e.printStackTrace();
						  }
						   }
					} catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
				}
					if(flag==false) {
			String sql2 = "SELECT * FROM Employees WHERE username = ? and passwordun = ?";
		    ResultSet rs2 = ChatClient.selectWithParameters(sql2, parameters);	
	
            try {
				if(rs2.next())
				{
					String role=rs2.getString(5);
					if(role.equals("marketing rep"))
					{
						try {
							((Node) event.getSource()).getScene().getWindow().hide(); 
							Pane root = FXMLLoader.load(getClass().getResource("gui/MarketingRepMainView.fxml"));//build the gui
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
					else if(role.equals("gas station manager"))
					{
						try {
							((Node) event.getSource()).getScene().getWindow().hide(); 
							Pane root = FXMLLoader.load(getClass().getResource("gui/GasStationManagerMainView.fxml"));//build the gui
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
					else if(role.equals("marketing manager"))
					{System.out.println("1");
						try {
							((Node) event.getSource()).getScene().getWindow().hide(); 
							Pane root = FXMLLoader.load(getClass().getResource("gui/MarketingManagerMainView.fxml"));//build the gui
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
					else if(role.equals("chain manager"))
					{
						try {
							((Node) event.getSource()).getScene().getWindow().hide(); 
							Pane root = FXMLLoader.load(getClass().getResource("gui/ChainManagerMainView.fxml"));//build the gui
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
					else if(role.equals("supplier"))
					{
						try {
							((Node) event.getSource()).getScene().getWindow().hide(); 
							Pane root = FXMLLoader.load(getClass().getResource("gui/SupplierMainView.fxml"));//build the gui
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
					String sql = "INSERT INTO logged (username,passwordun) VALUES (?,?);";
					   ChatClient.updateTable(sql, parameters);
				}

					   else {
						   JOptionPane.showMessageDialog(null,"login is failed","error",JOptionPane.INFORMATION_MESSAGE);

							try {
								userNameTextField.clear();
								passWordTextField.clear();
					     	}
						catch(Exception e)
						  {
							e.printStackTrace();
						  }
					   }
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
    }
    
    /**
     *  handle button "Quick Fuel" - this button handles the case where you press the-
     *  sensor picture. this button simulates that sensor's work- it loggs in some user with
     *  his vehicle number,checking if the client with this vehicle number has a purchase plan then he can fuel his car without doing all the work-
     *  the sensor automatically identifies the username's vehicle number, his pump number,
     *  his subscription and all his details.
	 * @param event
     */
    @FXML
    void handleQuickFuelButton(ActionEvent event)
    {
    
    	
    	boolean flag=false;
    	while(flag==false) {
    		ArrayList<String> parameters1=new ArrayList<String>();
    	String query="SELECT vehicleNumber FROM vehicles ORDER BY RAND() LIMIT 1;";
    	try {
	    	   ClientUI.chat.accept("");
	    	   ResultSet rs = ChatClient.getTable(query);
	    	   
	    	 
	    	  if( rs.next()) {
	    	   
	    	   vehiclenum=rs.getString("vehicleNumber");
	    	   //System.out.println(rs.getString("vehicleNumber"));
	    	  // System.out.println(vehiclenum);
	    	   String getnamequery="SELECT username FROM vehicles WHERE vehicleNumber=?";
	       	ArrayList<String>par1=new ArrayList<String>();
	       	par1.add(vehiclenum);

	       	try {
	       			ClientUI.chat.accept("");
		       	 	ResultSet rs3 = ChatClient.selectWithParameters(getnamequery, par1);
		   	        rs3.next();
	   	    	    userin=rs3.getString("username");
		   	        setUserin(userin); 
		   	    	System.out.println(userin);
	   		 	}
	   		 	catch(Exception e)
	   		 	{
	   		 		e.printStackTrace();
	   		 	}
	    	parameters1.add(userin);
    		String query1="SELECT * FROM purchaseplan WHERE username = ?";
    		ClientUI.chat.accept("");
    	    ResultSet rs1 = ChatClient.selectWithParameters(query1, parameters1);	

    					if(rs1.next())
    					   {
    							flag=true;
    					   }
    					
    					if(flag==true) {

	       	// insert into log the vehicle number
	       	String sql = "INSERT INTO loggedquickfuel (username,vehiclenum) VALUES (?,?);";
	       	ArrayList<String>par=new ArrayList<String>();
	       	par.add(userin);
	       	par.add(vehiclenum);
	       	try {
	   	    	   ClientUI.chat.accept("");
	   	    	   ChatClient.updateTable(sql, par); 
	   		 	}
	   		 	catch(Exception e)
	   		 	{
	   		 		e.printStackTrace();
	   		 	}

	    	   ((Node) event.getSource()).getScene().getWindow().hide(); 
				Pane root = FXMLLoader.load(getClass().getResource("gui/ClientQuickFuel.fxml"));//build the gui
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				stage.show();
    	}
	    	  }
	    	   else if(!rs.next())
	    		   JOptionPane.showMessageDialog(null,"no vehicles registered","error",JOptionPane.INFORMATION_MESSAGE);
		 	}
		 	catch(Exception e)
		 	{
		 		e.printStackTrace();
		 	}
    	
    	}
    	
    }
    
    /**
     * Loading the "Login View"
     * @param primaryStage
     */
	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
Parent root;
try {
	root = FXMLLoader.load(getClass().getResource("gui/LoginView.fxml"));
	Scene scene = new Scene(root);
	primaryStage.setTitle("MyFuel APP");
	primaryStage.setScene(scene);
	primaryStage.show();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		
		
	}
}