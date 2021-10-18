
package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import entities.PurchasePlan;
import entities.SingleClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
/**
 * this class is a controller of the update single clients page , first we can see all the details of each client and then we can change all details except 
 * the ID witch is the key of the single client.
 *
 */
public class MarketingRepUpdateClientC implements Initializable{
    @FXML
    private Pane paneDetails;
    @FXML
    private Label userWelcomeLabel;

    @FXML
    private TextField FirstNameTextField;

    @FXML
    private TextField LastNameTextField;

    @FXML
    private TextField IDNumberTextField;

    @FXML
    private TextField CreditCardTextField;

    @FXML
    private TextField MailTextField;
    @FXML
    private CheckBox singleSonolCheckBox;

    @FXML
    private CheckBox singleTenCheckBox;

    @FXML
    private CheckBox singlePazCheckBox;

    @FXML
    private CheckBox singleDelekCheckBox;

    @FXML
    private TextField VehicleNumberTextField;


    @FXML
    private Button UpdateButtom;

    @FXML
    private TableView<SingleClient> ClientUpdateTableView;

    @FXML
    private TableColumn<SingleClient, String> IDNumberTableColumn;

    @FXML
    private TableColumn<SingleClient,String> FirstNameTableColumn;

    @FXML
    private TableColumn<SingleClient, String> LastNameTableColumn;

    @FXML
    private TableColumn<SingleClient, String> MailTableColumn;

    @FXML
    private TableColumn<SingleClient,String> CreditCardTableColumn;

    @FXML
    private TableColumn<SingleClient,String> usernameTableColumn;
    @FXML
    private ComboBox<String> Vehiclscombo;

    @FXML
    private ComboBox<String> singleClientApproachNumbercombo;

    @FXML
    private ComboBox<String> SingleClientTypeNumbercombo;
    @FXML
    private ComboBox<String> singlepumpcombo;

    @FXML
    private TableColumn<SingleClient,String> passwordunColumn;
    ArrayList<String> cars=new ArrayList<String>();
    ArrayList<String> pumps=new ArrayList<String>();
    int cuurent=-1;
    String pump;
    int j=0;
    ObservableList<String> singlepumps=FXCollections.observableArrayList("1","2","3","4","5","6");
    ObservableList<String> singleapproach=FXCollections.observableArrayList("1","2");
    ObservableList<String> singletype=FXCollections.observableArrayList("1","2","3","4");
    ObservableList<String> vehicles;
    PurchasePlan p1=new PurchasePlan(); 
    /**
     * this function check if all the new details that the user typed are valid and update that details in the data base .
     * we can update also all the vehicles that belong to this client and even the purchase plan if he has one.
     * @param event, this event occurs when the user clicks on the update button 
     */
    @FXML
    void handleUpdateButtom(ActionEvent event) {
    	int[] gasStations= {0,0,0,0};
        int count=0;
       boolean flag=false;
      String numberOfCarsS=null;
        String PurchasePlanNumber;
    	String username=new String();
    	IDNumberTextField.setDisable(true);
    	ArrayList<String> parameters=new ArrayList<String>();
    	ArrayList<String> parametersu=new ArrayList<String>();//for get username
    	ArrayList<String> parametersv=new ArrayList<String>();//for update car
    	ArrayList<String> parametersp=new ArrayList<String>();//for update purchaseplan
    	ArrayList<String> parameters3=new ArrayList<String>();
    	if(FirstNameTextField.getText().equals("")||LastNameTextField.getText().equals("")||IDNumberTextField.getText().equals("")||CreditCardTextField.getText().equals("")||singleClientApproachNumbercombo.getValue().equals("")||SingleClientTypeNumbercombo.getValue().equals("")||VehicleNumberTextField.getText().equals("")||singlepumpcombo.getValue().equals(""))
    	{
        	JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);

    	}
    	 else if(MarketingRepMainC.ischaracters(FirstNameTextField.getText())==false)
         {
         	JOptionPane.showMessageDialog(null,"Invalid first name","error",JOptionPane.INFORMATION_MESSAGE);
         	FirstNameTextField.clear();
         }
    	 else if(MarketingRepMainC.ischaracters(LastNameTextField.getText())==false)
         {
         	JOptionPane.showMessageDialog(null,"Invalid last name","error",JOptionPane.INFORMATION_MESSAGE);
         	LastNameTextField.clear();
         }
    	
         else if(CreditCardTextField.getText().length()<=8||CreditCardTextField.getText().length()>16||MarketingRepMainC.isNumeric(CreditCardTextField.getText())==false)
         {
         	JOptionPane.showMessageDialog(null,"Invalid Credit Card number","error",JOptionPane.INFORMATION_MESSAGE);
         	CreditCardTextField.clear();
         	 }
         else {
        	 String sql="SELECT username FROM singleclient WHERE IdNumber = ?";
        	 parametersu.add(IDNumberTextField.getText());
        	 ClientUI.chat.accept("");
 			ResultSet rs1 = ChatClient.selectWithParameters(sql, parametersu);
 			try {
				if(rs1.next()) {
					 username=rs1.getString("username");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			String updateQuery = "UPDATE singleclient " + " SET firstName = ?, lastName = ?, mail = ?, creditCard = ? WHERE IdNumber = ?";

        	 parameters.add(FirstNameTextField.getText());
        	 parameters.add(LastNameTextField.getText());
        	 parameters.add(MailTextField.getText());
        	 parameters.add(CreditCardTextField.getText());
        	 parameters.add(IDNumberTextField.getText());

        	 ClientUI.chat.accept("");
 		    ChatClient.updateTable(updateQuery, parameters);
 		    if(VehicleNumberTextField.getText().length()<6||VehicleNumberTextField.getText().length()>8||MarketingRepMainC.isNumeric(VehicleNumberTextField.getText())==false)
 		    {
 		    	JOptionPane.showMessageDialog(null,"Invalid vehicle number","error",JOptionPane.INFORMATION_MESSAGE);
 		    	flag=true;
 		    }
 		    else {
 		   String updateQuery1 = "UPDATE vehicles " + " SET vehicleNumber = ?, pumpNumber = ? WHERE username = ?";////////////////////////

      	 parametersv.add(VehicleNumberTextField.getText());
      	 parametersv.add(singlepumpcombo.getValue());
      	 parametersv.add(username);
      	 

      	 ClientUI.chat.accept("");
		    ChatClient.updateTable(updateQuery1, parametersv);
 		    }
		
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
		    	flag=true;

       	}
       	else if(singleClientApproachNumbercombo.getValue().equals("2")&&count<2||count>3)
       	{
           	JOptionPane.showMessageDialog(null,"you have to choose 2 or 3 gas stations","error",JOptionPane.INFORMATION_MESSAGE);
           	singlePazCheckBox.setSelected(false);
           	singleSonolCheckBox.setSelected(false);
           	singleTenCheckBox.setSelected(false);
           	singleDelekCheckBox.setSelected(false);
		    	flag=true;

       	}
       	else {

       		parametersp.add(singleClientApproachNumbercombo.getValue());
       		parametersp.add(SingleClientTypeNumbercombo.getValue());
       		if(gasStations[0]==1)parametersp.add("Paz");
       		if(gasStations[1]==1)parametersp.add("Delek");
       		if(gasStations[2]==1)parametersp.add("Sonol");
       		if(gasStations[3]==1)parametersp.add("Ten");
       		if(count==1)
       		{
       			parametersp.add("");
       			parametersp.add("");
       		}
       		if(count==2)
       		{
       			parametersp.add("");
       		}
       		PurchasePlanNumber=singleClientApproachNumbercombo.getValue()+SingleClientTypeNumbercombo.getValue();
       		for(int i=0;i<gasStations.length;i++)
       		{
       			if(gasStations[i]==1)
       				PurchasePlanNumber+=(i+1);
       		}
       		parametersp.add(PurchasePlanNumber);
       		System.out.println(PurchasePlanNumber);////for check
       		//for(int i=0;i<clientPurchasePlan.size();i++)System.out.println(clientPurchasePlan.get(i));
       		
       		if(SingleClientTypeNumbercombo.getValue().equals("1"))
   			{
       			parametersp.add("reguler");
       			parametersp.add("0%");
   			}
   		/////////////////////////////////////////////////////////////////////////////////////////////////////
   		if(SingleClientTypeNumbercombo.getValue().equals("3"))
   			{
   			
   			parameters3.add(username);
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
    					if(Integer.valueOf(numberOfCarsS)<=1) {
    						JOptionPane.showMessageDialog(null,"You have just one car","error",JOptionPane.INFORMATION_MESSAGE);
    		            	flag=true;
    					}
    					else {
       				
       					parametersp.add("customary "+numberOfCarsS+" cars");
       					parametersp.add("14%");
   			}
   			}
   	/////////////////////////////////////////////////////////////////////////////////////
   		if(SingleClientTypeNumbercombo.getValue().equals("2"))
   			{
   			parametersp.add("customary , one car");
   			parametersp.add("4%");
   			}
   		if(SingleClientTypeNumbercombo.getValue().equals("4"))
   			{
   			parametersp.add("monthly , one car");
   			parametersp.add("17%");
   			}
   		if(flag==false) {
   		parametersp.add(username);
		    String sq="UPDATE purchaseplan "+" SET approach = ?, clienttype = ?, gasstation1 = ?, gasstation2 = ?, gasstation3 = ?, purchaseplannumber = ?, purchaseplandescription = ?, discount = ? WHERE username =?";
		    ClientUI.chat.accept(""); 
		    ChatClient.updateTable(sq, parametersp);
		    JOptionPane.showMessageDialog(null,"Client edited successfuly","success message",JOptionPane.INFORMATION_MESSAGE);
 		   
 		    refreshSingleClientTable();
         }
         }
         }
    }
    /**
     * this function turn off all the fields in the page until the user clicks on one of the table rows and call the refreshclienttable function 
     */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	singlepumpcombo.setValue("Choose");
    	singlepumpcombo.setItems(singlepumps);
    	paneDetails.setDisable(true);
    	singleClientApproachNumbercombo.setValue("Choose");
    	singleClientApproachNumbercombo.setItems(singleapproach);
     	SingleClientTypeNumbercombo.setValue("Choose");
    	SingleClientTypeNumbercombo.setItems(singletype);
		refreshSingleClientTable();
		
	}
    /**
     * this function bring from the data base the table of the single client and initialize the first page with all the clients details. 
     */
	public void refreshSingleClientTable() {
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM myfuel.singleclient ";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.getTable(selectQuery);
		} 
		catch (Exception e)
		{
			System.err.println("Error : get singleclient table : client server problem");
			e.printStackTrace();
		}
		ObservableList<SingleClient> oblist = FXCollections.observableArrayList();
		

		try
		{
			while(rs.next())
			{
				oblist.add(new SingleClient(rs.getString("firstName"), rs.getString("lastName"), rs.getString("IdNumber"), rs.getString("mail"), rs.getString("creditCard"), rs.getString("username"), rs.getString("passwordun")));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	

		FirstNameTableColumn.setCellValueFactory(new PropertyValueFactory<SingleClient, String>("firstName"));
		LastNameTableColumn.setCellValueFactory(new PropertyValueFactory<SingleClient, String>("lastName"));
		IDNumberTableColumn.setCellValueFactory(new PropertyValueFactory<SingleClient, String>("IdNumber"));
		MailTableColumn.setCellValueFactory(new PropertyValueFactory<SingleClient, String>("mail"));
		CreditCardTableColumn.setCellValueFactory(new PropertyValueFactory<SingleClient, String>("creditCard"));
		usernameTableColumn.setCellValueFactory(new PropertyValueFactory<SingleClient, String>("username"));
		passwordunColumn.setCellValueFactory(new PropertyValueFactory<SingleClient, String>("passwordun"));
		
		
		ClientUpdateTableView.setItems(oblist);
		

	}
	/**
	 * this function puts in all the fields the details of the single client and bring the vehicle list of this specific client
	 * @param event, this event occurs when the user clicks on one of the table rows 
	 */
	@FXML
    void handleRowClick(MouseEvent event) {
		boolean flag=false;
		paneDetails.setDisable(false);
		Vehiclscombo.setValue("Choose");
		Vehiclscombo.setDisable(false);
		singleClientApproachNumbercombo.setDisable(false);
		SingleClientTypeNumbercombo.setDisable(false);
		singlePazCheckBox.setDisable(false);
		singleDelekCheckBox.setDisable(false);
		singleSonolCheckBox.setDisable(false);
		singleTenCheckBox.setDisable(false);
		singlepumpcombo.setDisable(false);
		singlepumpcombo.setValue(null);
		singleTenCheckBox.setSelected(false);
		singlePazCheckBox.setSelected(false);
		singleSonolCheckBox.setSelected(false);
		singleDelekCheckBox.setSelected(false);
		singleClientApproachNumbercombo.setValue(null);
		SingleClientTypeNumbercombo.setValue(null);
		VehicleNumberTextField.clear();
        int index = ClientUpdateTableView.getSelectionModel().getSelectedIndex();
		
		if(index <= -1)
		{
			return;
		}
		FirstNameTextField.setText(FirstNameTableColumn.getCellData(index).toString());
		LastNameTextField.setText(LastNameTableColumn.getCellData(index).toString());
		IDNumberTextField.setText(IDNumberTableColumn.getCellData(index).toString());
		MailTextField.setText(MailTableColumn.getCellData(index).toString());
		CreditCardTextField.setText(CreditCardTableColumn.getCellData(index).toString());
	//if(cuurent!=index) {
		cars=new ArrayList<String>();
		pumps=new ArrayList<String>();
		vehicles =FXCollections.observableArrayList( );
		j=0;
	//}
    	
		ArrayList<String> param=new ArrayList<String>();
		ArrayList<String> param2=new ArrayList<String>();
		
		String selectQuery = "SELECT * FROM myfuel.vehicles WHERE username=?";
        param.add(usernameTableColumn.getCellData(index).toString());
		System.out.println(param);
			ClientUI.chat.accept("");
			ResultSet rscar = ChatClient.selectWithParameters(selectQuery, param);
		
		try
		{
			
			while(rscar.next())
			{
				cars.add(rscar.getString("vehicleNumber"));
				//pumps.add(rscar.getString("pumpNumber"));
				if(!vehicles.contains(rscar.getString("vehicleNumber")))vehicles.add(rscar.getString("vehicleNumber"));
				
			}
			if(cars.size()==0)
				{
				VehicleNumberTextField.setText("No vehicle");
				Vehiclscombo.setValue(null);
				Vehiclscombo.setItems(null);
				singlepumpcombo.setDisable(true);
				Vehiclscombo.setDisable(true);
				}
			else {
				Vehiclscombo.setItems(vehicles);
				
		
				
			}
	
				
			
			cuurent=index;
				j++;
				
		} 
	
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		String selectQuery2 = "SELECT * FROM myfuel.purchaseplan WHERE username=?";
        param2.add(usernameTableColumn.getCellData(index).toString());
		System.out.println(param2);
			ClientUI.chat.accept("");
			ResultSet rs1 = ChatClient.selectWithParameters(selectQuery2, param2);
		
		try
		{
			singleClientApproachNumbercombo.setValue(null);
			SingleClientTypeNumbercombo.setValue(null);
		
			while(rs1.next())
			{
				singleClientApproachNumbercombo.setValue(rs1.getString("approach"));
				SingleClientTypeNumbercombo.setValue(rs1.getString("clienttype"));
				if(rs1.getString("gasstation1").equals("Paz")||rs1.getString("gasstation2").equals("Paz")||rs1.getString("gasstation3").equals("Paz"))
					singlePazCheckBox.setSelected(true);
				if(rs1.getString("gasstation1").equals("Delek")||rs1.getString("gasstation2").equals("Delek")||rs1.getString("gasstation3").equals("Delek"))
					singleDelekCheckBox.setSelected(true);
				if(rs1.getString("gasstation1").equals("Sonol")||rs1.getString("gasstation2").equals("Sonol")||rs1.getString("gasstation3").equals("Sonol"))
					singleSonolCheckBox.setSelected(true);
				if(rs1.getString("gasstation1").equals("Ten")||rs1.getString("gasstation2").equals("Ten")||rs1.getString("gasstation3").equals("Ten"))
					singleTenCheckBox.setSelected(true);
				flag=true;
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		if(flag==false) {
			singleClientApproachNumbercombo.setDisable(true);
			SingleClientTypeNumbercombo.setDisable(true);
			singlePazCheckBox.setDisable(true);
			singleDelekCheckBox.setDisable(true);
			singleSonolCheckBox.setDisable(true);
			singleTenCheckBox.setDisable(true);
		}
		IDNumberTextField.setDisable(true);
    }
	/**
	 * this function bring from DB the pump number for each vehicle
	 * @param event , this event occurs when the user selects one of the vehicles number in the comboBox 
	 */
	@FXML
    void handleVehiclesCombo(ActionEvent event) {
		VehicleNumberTextField.setText(Vehiclscombo.getValue());
		ArrayList<String> param=new ArrayList<String>();
		String selectQuery = "SELECT * FROM myfuel.vehicles WHERE vehicleNumber=?";
        param.add(VehicleNumberTextField.getText());
			ClientUI.chat.accept("");
			ResultSet rscar = ChatClient.selectWithParameters(selectQuery, param);
		
		try
		{
		
			while(rscar.next())
			{
				singlepumpcombo.setValue(rscar.getString("pumpNumber"));
				System.out.println(rscar.getString("pumpNumber"));
			}
			
		
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
    }
    }






