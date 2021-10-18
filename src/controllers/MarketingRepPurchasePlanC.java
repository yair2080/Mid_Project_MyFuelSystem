
package controllers;

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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * this class is a controller for MarketingRepPurchasePlan gui , every client can have one purchase plan that give him a discount for his purchases.
 * The marketingRep chooses a purchase plan to client, first he choose one approach from two, the first one let him buy fuel from one gas station,
 * the second one let him buy fuel from 2-3 gas stations.
 * then he chooses one from four client types and select gas stations according to the approach number that he picked.
 *
 */
public class MarketingRepPurchasePlanC implements Initializable{

    @FXML
    private Label userWelcomeLabel;
    @FXML
    private Pane singlePane;
    @FXML
    private Pane companyPane;
    @FXML
    private Button singleplan;

    @FXML
    private Button companyplan;
    
    @FXML
    private TextField singleClientIDTextField;


    @FXML
    private CheckBox singleSonolCheckBox;

    @FXML
    private CheckBox singleTenCheckBox;

    @FXML
    private CheckBox singlePazCheckBox;

    @FXML
    private CheckBox singleDelekCheckBox;

    @FXML
    private TextField companyIDTextField;


    @FXML
    private CheckBox companyPazCheckBox;

    @FXML
    private CheckBox companySonolCheckBox;

    @FXML
    private CheckBox companyTenCheckBox;

    @FXML
    private CheckBox companyDelekCheckBox;

    @FXML
    private Button singleClientFinishButton;

    @FXML
    private Button companyFinishButton;
    
    @FXML
    private ComboBox<String> singleClientApproachNumbercombo;

    @FXML
    private ComboBox<String> SingleClientTypeNumbercombo;
    @FXML
    private ComboBox<String> companyApproachNumbercombo;

    @FXML
    private ComboBox<String> companyClientTypeNumbercombo;
    
    ObservableList<String> singleapproach=FXCollections.observableArrayList("1","2");
    ObservableList<String> companyapproach=FXCollections.observableArrayList("1","2");
    ObservableList<String> singletype=FXCollections.observableArrayList("1","2","3","4");
    ObservableList<String> companytype=FXCollections.observableArrayList("1","2","3","4");
    /**
     * this function initialize every thing in the gui
     */
    @Override
   	public void initialize(URL arg0, ResourceBundle arg1)
   	{
    	singleClientApproachNumbercombo.setValue("Choose");
    	singleClientApproachNumbercombo.setItems(singleapproach);
    	companyApproachNumbercombo.setValue("Choose");
    	companyApproachNumbercombo.setItems(companyapproach);
    	SingleClientTypeNumbercombo.setValue("Choose");
    	SingleClientTypeNumbercombo.setItems(singletype);
    	companyClientTypeNumbercombo.setValue("Choose");
    	companyClientTypeNumbercombo.setItems(companytype);
   		
   			singlePane.setDisable(true);
   			companyPane.setDisable(true);
   		
   	}
    /**
     * this function turns on the company purchase plan and turns of the one for the private client
     * @param event , this event occurs when the user clicks company purchase plan button
     */
    @FXML
    void handlecompanyplan(ActionEvent event) {
    	singlePane.setDisable(true);
    	companyPane.setDisable(false);
    }
    /**
     * this function turns of the company purchase plan and turns on the one for the client
     * @param event, this event occurs when the  user clicks single client purchase plan button
     */
    @FXML
    void handlesingleplan(ActionEvent event) {
    	singlePane.setDisable(false);
    	companyPane.setDisable(true);
    }

    
    /**
     * this method add a purchase plan to a company client, first it checks if the company is already has one else it adds it and saves it in purchaseplan table in the data base
     * @param event when the  user clicks on finish button
     */
    @FXML
    void handlecompanyFinishButton(ActionEvent event) {
        ArrayList<String> companyPurchasePlan=new ArrayList<String>();

    	boolean flag=false;
    	int[] gasStations= {0,0,0,0};
        int count=0;
       
      String numberOfCarsS=null;
        String PurchasePlanNumber;
    	ArrayList<String> parameters=new ArrayList<String>();
    	ArrayList<String> parameters2=new ArrayList<String>();
    	ArrayList<String> parameters3=new ArrayList<String>();
        if(companyIDTextField.getText().equals("")||companyClientTypeNumbercombo.getValue().equals("")||companyApproachNumbercombo.getValue().equals(""))
        {
        	JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);
        	
        
        }
        else if(!companyPazCheckBox.isSelected()&&!companySonolCheckBox.isSelected()&&!companyTenCheckBox.isSelected()&&!companyDelekCheckBox.isSelected())
        {
        	JOptionPane.showMessageDialog(null,"No Gas Station is selected","error",JOptionPane.INFORMATION_MESSAGE);
        	
        }
      
        else {
        	count=0;
        	if(companyPazCheckBox.isSelected())gasStations[0]=1;
        	else gasStations[0]=0;
        	if(companySonolCheckBox.isSelected())gasStations[2]=1;
        	else gasStations[2]=0;
        	if(companyTenCheckBox.isSelected())gasStations[3]=1;
        	else gasStations[3]=0;
        	if(companyDelekCheckBox.isSelected())gasStations[1]=1;
        	else gasStations[1]=0;
        	for(int i=0;i<gasStations.length;i++)
        	{
        		count+=gasStations[i];
        	}
        	if(companyApproachNumbercombo.getValue().equals("1")&&count!=1)
        	{
            	JOptionPane.showMessageDialog(null,"you have to choose only one gas station","error",JOptionPane.INFORMATION_MESSAGE);
            	companyPazCheckBox.setSelected(false);
            	companySonolCheckBox.setSelected(false);
            	companyTenCheckBox.setSelected(false);
            	companyDelekCheckBox.setSelected(false);
            	
            	
        	}
        	else if(companyApproachNumbercombo.getValue().equals("2")&&count<2||count>3)
        	{
            	JOptionPane.showMessageDialog(null,"you have to choose 2 or 3 gas stations","error",JOptionPane.INFORMATION_MESSAGE);
            	companyPazCheckBox.setSelected(false);
            	companySonolCheckBox.setSelected(false);
            	companyTenCheckBox.setSelected(false);
            	companyDelekCheckBox.setSelected(false);
            
            	
        	}
       
        	else {
        		parameters3.add(companyIDTextField.getText());
        		String query1="SELECT numOfVehicles FROM companyclients WHERE username = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs3 = ChatClient.selectWithParameters(query1, parameters3);	
        	   
        			
        			
        					try {
								if(rs3.next())
								   {
									numberOfCarsS=rs3.getString("numOfVehicles");
 			                        	}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
        		

        		companyPurchasePlan.add(companyIDTextField.getText());
        		companyPurchasePlan.add(companyApproachNumbercombo.getValue());
        		companyPurchasePlan.add(companyClientTypeNumbercombo.getValue());
        		if(gasStations[0]==1)companyPurchasePlan.add("Paz");
        		if(gasStations[1]==1)companyPurchasePlan.add("Delek");
        		if(gasStations[2]==1)companyPurchasePlan.add("Sonol");
        		if(gasStations[3]==1)companyPurchasePlan.add("Ten");
        		if(count==1)
        		{
        			companyPurchasePlan.add("");
        			companyPurchasePlan.add("");
        		}
        		if(count==2)
        		{
        			companyPurchasePlan.add("");
        		}
        		PurchasePlanNumber=companyApproachNumbercombo.getValue()+companyClientTypeNumbercombo.getValue();
        		for(int i=0;i<gasStations.length;i++)
        		{
        			if(gasStations[i]==1)
        				PurchasePlanNumber+=(i+1);
        		}
        		companyPurchasePlan.add(PurchasePlanNumber);
        		System.out.println(PurchasePlanNumber);////for check
        		if(companyClientTypeNumbercombo.getValue().equals("1"))
        			{
        			companyPurchasePlan.add("reguler");
        			companyPurchasePlan.add("0%");
        			}
        		/////////////////////////////////////////////////////////////////////////////////////////////////////
        		if(companyClientTypeNumbercombo.getValue().equals("3")&&Integer.valueOf(numberOfCarsS)<=1)
        			{
                	JOptionPane.showMessageDialog(null,"You have just one car","error",JOptionPane.INFORMATION_MESSAGE);
                	flag=true;
        			}
        		else if(companyClientTypeNumbercombo.getValue().equals("3")){
        			
            					companyPurchasePlan.add("customary"+numberOfCarsS+" cars");	
            					companyPurchasePlan.add("14%*");
        		}
        	/////////////////////////////////////////////////////////////////////////////////////
        		if(companyClientTypeNumbercombo.getValue().equals("2"))
        			{
        			companyPurchasePlan.add("customary , one car");
        			companyPurchasePlan.add("4%");
        			}
        		if(companyClientTypeNumbercombo.getValue().equals("4"))
        			{
        			companyPurchasePlan.add("monthly , one car");
        			companyPurchasePlan.add("17%");
        			}
    	
        	if(flag==false) {
        		parameters.add(companyIDTextField.getText());
        		String query="SELECT * FROM purchaseplan WHERE username = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs = ChatClient.selectWithParameters(query, parameters);	
        	   
        			
        				try {
        					if(rs.next())
        					   {
        						JOptionPane.showMessageDialog(null,"client already has a purchase plan","error",JOptionPane.INFORMATION_MESSAGE);
        						try {
        							flag=true;
        						
        							companyIDTextField.clear();
        							companyApproachNumbercombo.setValue(null);
        							companyClientTypeNumbercombo.setValue(null);
        							companyPazCheckBox.setSelected(false);
        			            	companySonolCheckBox.setSelected(false);
        			            	companyTenCheckBox.setSelected(false);
        			            	companyDelekCheckBox.setSelected(false);
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
        		if(numberOfCarsS.equals("0")) {
        			JOptionPane.showMessageDialog(null,"client dose not have vehicles","error",JOptionPane.INFORMATION_MESSAGE);
						flag=true;
					
						companyIDTextField.clear();
						companyApproachNumbercombo.setValue(null);
						companyClientTypeNumbercombo.setValue(null);
						companyPazCheckBox.setSelected(false);
		            	companySonolCheckBox.setSelected(false);
		            	companyTenCheckBox.setSelected(false);
		            	companyDelekCheckBox.setSelected(false);
				 	}
        	}
        	if(flag==false) {
        		parameters2.add(companyIDTextField.getText());
        		String query="SELECT * FROM companyclients WHERE username = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs = ChatClient.selectWithParameters(query, parameters);	
        	   
        			
        				try {
        					if(!rs.next())
        					   {
        						JOptionPane.showMessageDialog(null,"company does not exist","error",JOptionPane.INFORMATION_MESSAGE);
        						try {
        							flag=true;
        						
        							companyIDTextField.clear();
        							companyApproachNumbercombo.setValue(null);
        							companyClientTypeNumbercombo.setValue(null);
        							companyPazCheckBox.setSelected(false);
        			            	companySonolCheckBox.setSelected(false);
        			            	companyTenCheckBox.setSelected(false);
        			            	companyDelekCheckBox.setSelected(false);
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
        	if(flag==false)
        	{
        	
        	String sql4 = "INSERT INTO purchaseplan (username,approach,clienttype,gasstation1,gasstation2,gasstation3,purchaseplannumber,purchaseplandescription,discount) VALUES (?,?,?,?,?,?,?,?,?);";
        	ChatClient.updateTable(sql4, companyPurchasePlan);
       
        JOptionPane.showMessageDialog(null,"Purchase plan successfully associated","success message",JOptionPane.INFORMATION_MESSAGE);
        try {
        	companyPane.setDisable(true);
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
        }
    }
    }
    /**
     * this method adds a purchase plan to a single client, first it checks if the client is already has one else it adds it and saves it in purchaseplan table in the data base
     * @param event when the user clicks on finish button
     */
    @FXML
    void handlesingleClientFinishButton(ActionEvent event) {
        ArrayList<String> clientPurchasePlan=new ArrayList<String>();
     
        String numberOfCarsS=null;
    	int[] gasStations= {0,0,0,0};
        int count=0;
        String PurchasePlanNumber;
    	boolean flag=false;
    	ArrayList<String> parameters=new ArrayList<String>();
    	ArrayList<String> parameters2=new ArrayList<String>();
    	ArrayList<String> parameters3=new ArrayList<String>();

        if(singleClientIDTextField.getText().equals("")||SingleClientTypeNumbercombo.getValue().equals("")||singleClientApproachNumbercombo.getValue().equals(""))
        {
        	JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);
        	
        
        }
        else if(!singlePazCheckBox.isSelected()&&!singleSonolCheckBox.isSelected()&&!singleTenCheckBox.isSelected()&&!singleDelekCheckBox.isSelected())
        {
        	JOptionPane.showMessageDialog(null,"No Gas Station is selected","error",JOptionPane.INFORMATION_MESSAGE);
        	
        }
       
       
        else {
        	count=0;
        	if(singlePazCheckBox.isSelected())gasStations[0]=1;
        	else gasStations[0]=0;
        	if(singleSonolCheckBox.isSelected())gasStations[2]=1;
        	else gasStations[2]=0;
        	if(singleTenCheckBox.isSelected())gasStations[3]=1;
        	else gasStations[3]=0;
        	if(singleDelekCheckBox.isSelected())gasStations[1]=1;
        	else gasStations[1]=0;
        	for(int i=0;i<gasStations.length;i++)
        	{
        		count+=gasStations[i];
        	}
        	if(singleClientApproachNumbercombo.getValue().equals("1")&&count!=1)
        	{
            	JOptionPane.showMessageDialog(null,"you have to choose only one gas station","error",JOptionPane.INFORMATION_MESSAGE);
            	singlePazCheckBox.setSelected(false);
            	singleSonolCheckBox.setSelected(false);
            	singleTenCheckBox.setSelected(false);
            	singleDelekCheckBox.setSelected(false);
        	}
        	else if(singleClientApproachNumbercombo.getValue().equals("2")&&count<2||count>3)
        	{
            	JOptionPane.showMessageDialog(null,"you have to choose 2 or 3 gas stations","error",JOptionPane.INFORMATION_MESSAGE);
            	singlePazCheckBox.setSelected(false);
            	singleSonolCheckBox.setSelected(false);
            	singleTenCheckBox.setSelected(false);
            	singleDelekCheckBox.setSelected(false);
        	}
        	else {
        		parameters3.add(singleClientIDTextField.getText());
        		String query1="SELECT numOfVehicles FROM singleclient WHERE username = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs3 = ChatClient.selectWithParameters(query1, parameters3);	
        	   
        			
        			
        					try {
        						if(rs3.next())
								   {
									numberOfCarsS=rs3.getString("numOfVehicles");
			                        	}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

        		clientPurchasePlan.add(singleClientIDTextField.getText());
        		clientPurchasePlan.add(singleClientApproachNumbercombo.getValue());
        		clientPurchasePlan.add(SingleClientTypeNumbercombo.getValue());
        		if(gasStations[0]==1)clientPurchasePlan.add("Paz");
        		if(gasStations[1]==1)clientPurchasePlan.add("Delek");
        		if(gasStations[2]==1)clientPurchasePlan.add("Sonol");
        		if(gasStations[3]==1)clientPurchasePlan.add("Ten");
        		if(count==1)
        		{
        			clientPurchasePlan.add("");
        			clientPurchasePlan.add("");
        		}
        		if(count==2)
        		{
        			clientPurchasePlan.add("");
        		}
        		PurchasePlanNumber=singleClientApproachNumbercombo.getValue()+SingleClientTypeNumbercombo.getValue();
        		for(int i=0;i<gasStations.length;i++)
        		{
        			if(gasStations[i]==1)
        				PurchasePlanNumber+=(i+1);
        		}
        		clientPurchasePlan.add(PurchasePlanNumber);
        		System.out.println(PurchasePlanNumber);////for check
        		//for(int i=0;i<clientPurchasePlan.size();i++)System.out.println(clientPurchasePlan.get(i));
        		
        		if(SingleClientTypeNumbercombo.getValue().equals("1"))
    			{
        			clientPurchasePlan.add("reguler");
        			clientPurchasePlan.add("0%");
    			}
    		/////////////////////////////////////////////////////////////////////////////////////////////////////
        		if(SingleClientTypeNumbercombo.getValue().equals("3")&&Integer.valueOf(numberOfCarsS)<=1)
    			{
            	JOptionPane.showMessageDialog(null,"You have just one car","error",JOptionPane.INFORMATION_MESSAGE);
            	flag=true;
    			}
    		else if(SingleClientTypeNumbercombo.getValue().equals("3")){
    			
    			
        				
        					clientPurchasePlan.add("customary "+numberOfCarsS+" cars");
        					clientPurchasePlan.add("14%");
    			}
    	/////////////////////////////////////////////////////////////////////////////////////
    		if(SingleClientTypeNumbercombo.getValue().equals("2"))
    			{
    			clientPurchasePlan.add("customary , one car");
    			clientPurchasePlan.add("4%");
    			}
    		if(SingleClientTypeNumbercombo.getValue().equals("4"))
    			{
    			clientPurchasePlan.add("monthly , one car");
    			clientPurchasePlan.add("17%");
    			}
     
    	
        	if(flag==false) {
        		parameters.add(singleClientIDTextField.getText());
        		String query="SELECT * FROM purchaseplan WHERE username = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs = ChatClient.selectWithParameters(query, parameters);	
        	   
        			
        				try {
        					if(rs.next())
        					   {
        						JOptionPane.showMessageDialog(null,"client already has a purchase plan","error",JOptionPane.INFORMATION_MESSAGE);
        						try {
        							flag=true;
        						
        							singleClientIDTextField.clear();
        							singleClientApproachNumbercombo.setValue(null);
        							SingleClientTypeNumbercombo.setValue(null);
        							singlePazCheckBox.setSelected(false);
        			            	singleSonolCheckBox.setSelected(false);
        			            	singleTenCheckBox.setSelected(false);
        			            	singleDelekCheckBox.setSelected(false);
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
        		if(numberOfCarsS.equals("0")) {
        			JOptionPane.showMessageDialog(null,"client dose not have vehicles","error",JOptionPane.INFORMATION_MESSAGE);
						flag=true;
					
						singleClientIDTextField.clear();
						singleClientApproachNumbercombo.setValue(null);
						SingleClientTypeNumbercombo.setValue(null);
						singlePazCheckBox.setSelected(false);
		            	singleSonolCheckBox.setSelected(false);
		            	singleTenCheckBox.setSelected(false);
		            	singleDelekCheckBox.setSelected(false);
				 	}
        	}
        	if(flag==false) {
        		parameters2.add(companyIDTextField.getText());
        		String query="SELECT * FROM singleclient WHERE username = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs = ChatClient.selectWithParameters(query, parameters);	
        	   
        			
        				try {
        					if(!rs.next())
        					   {
        						JOptionPane.showMessageDialog(null,"client does not exist","error",JOptionPane.INFORMATION_MESSAGE);
        						try {
        							flag=true;
        						
        							singleClientIDTextField.clear();
        							singleClientApproachNumbercombo.setValue(null);
        							SingleClientTypeNumbercombo.setValue(null);
        							singlePazCheckBox.setSelected(false);
        			            	singleSonolCheckBox.setSelected(false);
        			            	singleTenCheckBox.setSelected(false);
        			            	singleDelekCheckBox.setSelected(false);
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
        	if(flag==false)
        	{
        	
        	String sql4 = "INSERT INTO purchaseplan (username,approach,clienttype,gasstation1,gasstation2,gasstation3,purchaseplannumber,purchaseplandescription,discount) VALUES (?,?,?,?,?,?,?,?,?);";
        	ChatClient.updateTable(sql4, clientPurchasePlan);
       
        JOptionPane.showMessageDialog(null,"Purchase plan successfully associated","success message",JOptionPane.INFORMATION_MESSAGE);
        try {
        	singlePane.setDisable(true);
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
        }
    }
    }

}
