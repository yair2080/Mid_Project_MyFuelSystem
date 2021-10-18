package controllers;/////////////DONE//////////

import java.awt.HeadlessException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
/**
 * this controller is for MarketingRepVehicleRegistration gui , every client can have many vehicles in our application.
 * The marketingRep inserts vehicle number and pump number according to the fuel type that appropriate to the vehicle , if all details are valid and there are no another vehicle with the 
 * same number ,the system saves/adds the  vehicle to client.
 *
 */
public class MarketingRepVehicleRegistrationC implements Initializable , MainInstanceInterface{
	private ConnectToInstance mainInstance1 = null;
    @FXML
    private Label userWelcomeLabel;

    @FXML
    private Pane singlePane;
    @FXML
    private Button helppumpsingle;

    @FXML
    private Pane companyPane;
    @FXML
    private Button companypumphelp;

    @FXML
    private Button singlevehicle;

    @FXML
    private Button companyvehicle;

    @FXML
    private TextArea helptexts;

    @FXML
    private TextArea helptextc;

    
    @FXML
    private TextField SingleClientVehicleNumberTextField;

    @FXML
    private Button SingleClientAddVehicleButton;

    @FXML
    private TextField SingleClientIDNumberTextField;

    @FXML
    private TextField CompanyVehicleNumberTextField;

    @FXML
    private TextField CompanyIDNumberTextField;

    @FXML
    private Button CompanyAddVehicleButton;
    
    
    
    @FXML
    private ComboBox<String> singlepumpcombo;


    @FXML
    private Button Singleabotherbtn;
    
    @FXML
    private ComboBox<String> companypumpcombo;


    @FXML
    private Button companyabotherbtn;
    
    ObservableList<String> singlepumps=FXCollections.observableArrayList("1","2","3","4","5","6");
    ObservableList<String> companypumps=FXCollections.observableArrayList("1","2","3","4","5","6");
    int numOfVehicles;
    ArrayList<String> companycar=new ArrayList<String>();
   
    /**
     * this function add another vehicle to the same client it sets the username of the client in the text field to be already written(there is no need to re write the name of the client again)after pressing the add single client vehicle
     * @param event , this event occurs when the user clicks on add another vehicle button
     */
    @FXML
    void handleSingleabotherbtn(ActionEvent event) {
    	singlePane.setDisable(false);
    	
    	SingleClientVehicleNumberTextField.setDisable(false);
    	singlepumpcombo.setDisable(false);
    	//SingleClientIDNumberTextField.clear();
    	SingleClientIDNumberTextField.setDisable(true);
    	SingleClientVehicleNumberTextField.clear();
    	singlepumpcombo.setValue(null);
    	SingleClientAddVehicleButton.setVisible(true);
    	Singleabotherbtn.setVisible(false);
    	
    }

    
    /**
     * this function add another vehicle to the same company it sets the username of the company in the text field to be already written 
     * @param event , this event occurs when the user clicks on add another vehicle button
     */
    @FXML
    void handlecompanyabotherbtn(ActionEvent event) {
    	companyPane.setDisable(false);
    	CompanyIDNumberTextField.setDisable(true);
    	CompanyVehicleNumberTextField.setDisable(false);
    	companypumpcombo.setDisable(false);
    	CompanyVehicleNumberTextField.clear();
    	companypumpcombo.setValue(null);
    	CompanyAddVehicleButton.setVisible(true);
    	companyabotherbtn.setVisible(false);
    	
    }

    /**
     * this function initialize every thing in the gui , it checks if we got this page from client registration then username of client will written else all 
     * fields will be empty
     */
    @Override
   	public void initialize(URL arg0, ResourceBundle arg1)
   	{
    	singlepumpcombo.setValue("Choose");
    	singlepumpcombo.setItems(singlepumps);
    	companypumpcombo.setValue("Choose");
    	companypumpcombo.setItems(companypumps);
   		if(MarketingRepClientRegistrationC.reg==1)
   			{companyPane.setDisable(false);
   			CompanyIDNumberTextField.setText(MarketingRepClientRegistrationC.username);
   			MarketingRepClientRegistrationC.reg=0;
   			}
   		else if(MarketingRepClientRegistrationC.reg==2)
   			{
   			singlePane.setDisable(false);
   			SingleClientIDNumberTextField.setText(MarketingRepClientRegistrationC.username);
   			MarketingRepClientRegistrationC.reg=0;
   			}
   		else {
   			singlePane.setDisable(true);
   			companyPane.setDisable(true);
   		}
   	}
    /**
     * this function turns on the add company vehicle button and turns of the add single client vehicle 
     * @param event , this event occurs when the user clicks on add company vehicle
     */
    @FXML
    void handlecompanyvehicle(ActionEvent event) {
    	singlePane.setDisable(true);
    	companyPane.setDisable(false);

    }
    /**
     * this function turns on the add single client vehicle button and turns of the add company vehicle
     * @param event, this event occurs when the user clicks on add client vehicle
     */
    @FXML
    void handlesinglevehicle(ActionEvent event) {
    	singlePane.setDisable(false);
    	companyPane.setDisable(true);
    }
    /**
     * this function hides the explanation of the pumps number that company vehicle can use 
     * @param event , this event occurs when the user moves the mouse from the text area
     */
    @FXML
    void showhelpce(MouseEvent event) {
    	helptextc.setVisible(false);
    }
    /**
     * this function shows the explanation of the pumps number that single client vehicle can use 
     * @param event , this event occurs when the user moves the mouse on the text area
     */
    @FXML
    void showhelps(MouseEvent event) {
    	helptexts.setVisible(true);
    }
    /**
     * this function hides the explanation of the pumps number that single client vehicle can use 
     * @param event , this event occurs when the user moves the mouse from the text area
     */
    @FXML
    void showhelpse(MouseEvent event) {
    	helptexts.setVisible(false);
    }
    /**
     * this function shows the explanation of the pumps number that company vehicle can use 
     * @param event , this event occurs when the user moves the mouse on the text area
     */
    @FXML
    void showhelpc(MouseEvent event) {
    	helptextc.setVisible(true);
    }
    /**
     * this function adds  vehicle to a single client(to the data base) 
     * it adds the vehicle number and pumps number
     * and also tests every possible situation (leaving empty fields, entering illegal vehicle number..)   
     * @param event,this event occurs when the user clicks on the add vehicle button
     */
    @FXML
    void handleSingleClientAddVehicleButton(ActionEvent event) {
        ArrayList<String> clientcar=new ArrayList<String>();

    	boolean flag=false;
    	ArrayList<String> parameters=new ArrayList<String>();
    	ArrayList<String> parameters2=new ArrayList<String>();//for check if client exist

        if(SingleClientVehicleNumberTextField.getText().equals("")||singlepumpcombo.getValue().equals("")||SingleClientIDNumberTextField.getText().equals(""))
        {
        	JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);
        	flag=true;
        	

        
        }
        else if(SingleClientVehicleNumberTextField.getText().length()<6||SingleClientVehicleNumberTextField.getText().length()>8||MarketingRepMainC.isNumeric(SingleClientVehicleNumberTextField.getText())==false)
        {
        	JOptionPane.showMessageDialog(null,"Invalid vehicle number","error",JOptionPane.INFORMATION_MESSAGE);
        	SingleClientVehicleNumberTextField.clear();
        	flag=true;
        }
        
        else {
        	clientcar.add(SingleClientVehicleNumberTextField.getText());
        	clientcar.add(singlepumpcombo.getValue());
        	clientcar.add(SingleClientIDNumberTextField.getText());
    	
    	
        	if(flag==false) {
        		parameters.add(SingleClientVehicleNumberTextField.getText());
        		String query="SELECT * FROM vehicles WHERE vehicleNumber = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs = ChatClient.selectWithParameters(query, parameters);	
        	   
        			
        				try {
        					if(rs.next())
        					   {
        						JOptionPane.showMessageDialog(null,"vehicle is already exist","error",JOptionPane.INFORMATION_MESSAGE);
        						try {
        							flag=true;
        						
        							SingleClientVehicleNumberTextField.clear();
        							singlepumpcombo.setValue(null);//check
        				        	SingleClientIDNumberTextField.clear();
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
        				}
        	if(flag==false) {
        		parameters2.add(SingleClientIDNumberTextField.getText());
        		String query="SELECT * FROM singleclient WHERE username = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs = ChatClient.selectWithParameters(query, parameters2);	
        	   
        					try {
								if(rs.next()==false)
								   {
									System.out.println(rs.first());
									JOptionPane.showMessageDialog(null,"client does not not exist","error",JOptionPane.INFORMATION_MESSAGE);
									try {
										flag=true;
									
										SingleClientVehicleNumberTextField.clear();
										singlepumpcombo.setValue(null);//check
								    	SingleClientIDNumberTextField.clear();
								 	}
								catch(Exception e)
								  {
									e.printStackTrace();
								  }
								   }
								else {
									
									 numOfVehicles=Integer.parseInt(rs.getString("numOfVehicles"));
									 numOfVehicles+=1;
									System.out.println(numOfVehicles);
									String updateQuery = "UPDATE singleclient " + " SET numOfVehicles = ? WHERE username = ?";
						        	ArrayList<String> clientnumcar=new ArrayList<String>();
						        	clientnumcar.add(String.valueOf(numOfVehicles));
						        	clientnumcar.add(SingleClientIDNumberTextField.getText());
						        	ChatClient.updateTable(updateQuery, clientnumcar);
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
        	if(flag==false)
        	{
        	
        	String sql4 = "INSERT INTO vehicles (vehicleNumber,pumpNumber,username) VALUES (?,?,?);";
        	ChatClient.updateTable(sql4, clientcar);
        	
       //update num of vehicles
        JOptionPane.showMessageDialog(null,"successfuly registration","success message",JOptionPane.INFORMATION_MESSAGE);
        try {
        	SingleClientIDNumberTextField.setDisable(true);
        	SingleClientVehicleNumberTextField.setDisable(true);
        	singlepumpcombo.setDisable(true);
        	SingleClientAddVehicleButton.setVisible(false);
        	
        	Singleabotherbtn.setVisible(true);
        	
        	
     	}
	catch(Exception e)
	  {
		e.printStackTrace();
	  }
        }
        }
    
    /**
     * this function adds  vehicle to a company(to the data base) 
     * it adds the vehicle number and pumps number
     * and also tests every possible situation (leaving empty fields, entering illegal vehicle number..)   
     * @param event,this event occurs when the user clicks on the add company vehicle button
     */
    @FXML
    void handleCompanyAddVehicleButton(ActionEvent event) {
        ArrayList<String> companycar=new ArrayList<String>();

    	boolean flag=false;
    	ArrayList<String> parameters=new ArrayList<String>();//for check if vehicle is already exist 
    	ArrayList<String> parameters2=new ArrayList<String>();//for check if client exist
        if(CompanyVehicleNumberTextField.getText().equals("")||companypumpcombo.getValue().equals("")||CompanyIDNumberTextField.getText().equals(""))
        {
        	JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);
        	flag=true;
        }
        else if(CompanyVehicleNumberTextField.getText().length()<6||CompanyVehicleNumberTextField.getText().length()>8||MarketingRepMainC.isNumeric(CompanyVehicleNumberTextField.getText())==false)
        {
        	JOptionPane.showMessageDialog(null,"Invalid vehicle number","error",JOptionPane.INFORMATION_MESSAGE);
        	CompanyVehicleNumberTextField.clear();
        	flag=true;
        }
      
        else {
        	companycar.add(CompanyVehicleNumberTextField.getText());
        	companycar.add(companypumpcombo.getValue());
        	companycar.add(CompanyIDNumberTextField.getText());
    	
    	
        	if(flag==false) {
        		parameters.add(CompanyVehicleNumberTextField.getText());
        		String query="SELECT * FROM vehicles WHERE vehicleNumber = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs = ChatClient.selectWithParameters(query, parameters);	
        	   
        			
        				try {
        					if(rs.next())
        					   {
        						JOptionPane.showMessageDialog(null,"vehicle is already exist","error",JOptionPane.INFORMATION_MESSAGE);
        						try {
        							flag=true;
        						
        							CompanyVehicleNumberTextField.clear();
        							companypumpcombo.setValue(null);
        							CompanyIDNumberTextField.clear();
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
        				}
        	if(flag==false) {
        		parameters2.add(CompanyIDNumberTextField.getText());
        		String query="SELECT * FROM companyclients WHERE username = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs = ChatClient.selectWithParameters(query, parameters2);	
        	   
        			
        				try {
        					if(!rs.next())
        					   {
        						JOptionPane.showMessageDialog(null,"company does not exist","error",JOptionPane.INFORMATION_MESSAGE);
        						try {
        							flag=true;
        						
        							CompanyVehicleNumberTextField.clear();
        							companypumpcombo.setValue(null);
        							CompanyIDNumberTextField.clear();
        					 	}
        					catch(Exception e)
        					  {
        						e.printStackTrace();
        					  }
        					   }
        					else {
						
        						numOfVehicles=Integer.parseInt(rs.getString("numOfVehicles"));
								 numOfVehicles+=1;
								System.out.println(numOfVehicles);
								String updateQuery = "UPDATE companyclients " + " SET numOfVehicles = ? WHERE username = ?";
								ArrayList<String> clientnumcar=new ArrayList<String>();
					        	clientnumcar.add(String.valueOf(numOfVehicles));
					        	clientnumcar.add(CompanyIDNumberTextField.getText());
					        	
					    		ClientUI.chat.accept("");

					        	ChatClient.updateTable(updateQuery, clientnumcar);
							}
        				} catch (HeadlessException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				} catch (SQLException e1) {
        					// TODO Auto-generated catch block
        					e1.printStackTrace();
        				}
        				}
        	if(flag==false)
        	{
        	
        	String sql4 = "INSERT INTO vehicles (vehicleNumber,pumpNumber,username) VALUES (?,?,?);";
        	ChatClient.updateTable(sql4, companycar);
        	
        JOptionPane.showMessageDialog(null,"successfuly registration","success message",JOptionPane.INFORMATION_MESSAGE);
        try {
        	CompanyIDNumberTextField.setDisable(true);
        	CompanyVehicleNumberTextField.setDisable(true);
        	companypumpcombo.setDisable(true);
        	
        	companyabotherbtn.setVisible(true);
        
        	CompanyAddVehicleButton.setVisible(false);
        	
     	}
	catch(Exception e)
	  {
		e.printStackTrace();
	  }
        }
        }
        
    }
    public void setMainInstance(ConnectToInstance main_instance)
	{
		this.mainInstance1=main_instance;
	}

}
