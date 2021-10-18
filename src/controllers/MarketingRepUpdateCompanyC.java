package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import entities.Company;
import entities.PurchasePlan;
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
 * this class is a controller of the update company clients page , first we can see all the details of each company and then we can change all details except 
 * the company name witch is the key of the company clients.
 *
 */
public class MarketingRepUpdateCompanyC implements Initializable{
    @FXML
    private Pane paneDetails;
    @FXML
    private Label userWelcomeLabel;

    @FXML
    private TextField CompanyNameTextField;

    @FXML
    private TextField CompanyPhoneTextField;

    @FXML
    private TextField CompanyAddressTextField;

    @FXML
    private TextField CompanyMailTextField;


    @FXML
    private TextField CompanyVehicleNumberTextField;

    @FXML
    private Button UpdateButton;

    @FXML
    private TableView<Company> CompanyUpdateTableView;

    @FXML
    private TableColumn<Company, String> CompanyNameTableColumn;

    @FXML
    private TableColumn<Company, String> MailTableColumn;

    @FXML
    private TableColumn<Company, String> PhoneNumberTableColumn;

    @FXML
    private TableColumn<Company, String> AddressTableColumn;

    @FXML
    private TableColumn<Company, String> usernameTableColumn;

    @FXML
    private TableColumn<Company, String> passwordunTableColumn;
    
    @FXML
    private ComboBox<String> Vehiclscombo;

    @FXML
    private CheckBox companyPazCheckBox;

    @FXML
    private CheckBox companySonolCheckBox;

    @FXML
    private CheckBox companyTenCheckBox;

    @FXML
    private CheckBox companyDelekCheckBox;

    @FXML
    private ComboBox<String> companyApproachNumbercombo;

    @FXML
    private ComboBox<String> companyClientTypeNumbercombo;

    @FXML
    private ComboBox<String> companypumpcombo;
    ArrayList<String> cars=new ArrayList<String>();
    ArrayList<String> pumps=new ArrayList<String>();
    int cuurent=-1;
    String pump;
    String numofcars=null;
    int j=0;
    ObservableList<String> companypumps=FXCollections.observableArrayList("1","2","3","4","5","6");
    ObservableList<String> companyapproach=FXCollections.observableArrayList("1","2");
    ObservableList<String> companytype=FXCollections.observableArrayList("1","2","3","4");
    ObservableList<String> vehicles;
    PurchasePlan p1=new PurchasePlan(); 
    /**
     * this function check if all the new details that the user typed are valid and update that details in the data base .
     * we can update also all the vehicles that belong to this company and even the purchase plan if it has one.
     * @param event, this event occurs when the user clicks on the update button 
     */
    @FXML
    void handleUpdateButton(ActionEvent event) {
    	String username=new String();
    	int[] gasStations= {0,0,0,0};
        int count=0;
        boolean flag=false;
      String numberOfCarsS=null;
        String PurchasePlanNumber;
    	CompanyNameTextField.setDisable(true);
    	ArrayList<String> parameters=new ArrayList<String>();
    	ArrayList<String> parametersu=new ArrayList<String>();
    	ArrayList<String> parametersv=new ArrayList<String>();
    	ArrayList<String> parametersp=new ArrayList<String>();
    	ArrayList<String> parameters3=new ArrayList<String>();

    	if(CompanyNameTextField.getText().equals("")||CompanyPhoneTextField.getText().equals("")||companyClientTypeNumbercombo.getValue().equals("")||companyApproachNumbercombo.getValue().equals("")||CompanyVehicleNumberTextField.getText().equals("")||companypumpcombo.getValue().equals(""))
    	{
        	JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);

    	}
         else {
        	 if(MarketingRepMainC.isNumeric(CompanyPhoneTextField.getText())==false)
        		 JOptionPane.showMessageDialog(null,"Invalid phone number","error",JOptionPane.INFORMATION_MESSAGE);
        	 else {
 			String updateQuery = "UPDATE companyclients " + " SET mail = ?, phoneNumber = ?, address = ? WHERE companyName = ?";

        	 
        	 parameters.add(CompanyMailTextField.getText());
        	 parameters.add(CompanyPhoneTextField.getText());
        	 parameters.add(CompanyAddressTextField.getText());
        	 parameters.add(CompanyNameTextField.getText());
        	 ClientUI.chat.accept("");
 		    ChatClient.updateTable(updateQuery, parameters);
        	 }
 		   if(CompanyVehicleNumberTextField.getText().length()<6||CompanyVehicleNumberTextField.getText().length()>8||MarketingRepMainC.isNumeric(CompanyVehicleNumberTextField.getText())==false)
		    	{
 			   JOptionPane.showMessageDialog(null,"uncorrect vehicle number","error",JOptionPane.INFORMATION_MESSAGE);
 			  flag=true;
		    	}
		    else {
		        	 String sql="SELECT username FROM companyclients WHERE companyName = ?";
		        	 parametersu.add(CompanyNameTextField.getText());
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
		    
		   String updateQuery1 = "UPDATE vehicles " + " SET vehicleNumber = ?, pumpNumber = ? WHERE username = ?";////////////////////////

     	 parametersv.add(CompanyVehicleNumberTextField.getText());
     	 parametersv.add(companypumpcombo.getValue());
     	 parametersv.add(username);
     	 

     	 ClientUI.chat.accept("");
		    ChatClient.updateTable(updateQuery1, parametersv);
		    }
		
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
          	flag=true;
          	
      	}
      	else if(companyApproachNumbercombo.getValue().equals("2")&&count<2||count>3)
      	{
          	JOptionPane.showMessageDialog(null,"you have to choose 2 or 3 gas stations","error",JOptionPane.INFORMATION_MESSAGE);
          	companyPazCheckBox.setSelected(false);
          	companySonolCheckBox.setSelected(false);
          	companyTenCheckBox.setSelected(false);
          	companyDelekCheckBox.setSelected(false);
          	flag=true;
          	
      	}
     
      	else {

      		parametersp.add(companyApproachNumbercombo.getValue());
      		parametersp.add(companyClientTypeNumbercombo.getValue());
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
      		PurchasePlanNumber=companyApproachNumbercombo.getValue()+companyClientTypeNumbercombo.getValue();
      		for(int i=0;i<gasStations.length;i++)
      		{
      			if(gasStations[i]==1)
      				PurchasePlanNumber+=(i+1);
      		}
      		parametersp.add(PurchasePlanNumber);
      		System.out.println(PurchasePlanNumber);////for check
      		if(companyClientTypeNumbercombo.getValue().equals("1"))
      			{
      			parametersp.add("reguler");
      			parametersp.add("0%");
      			}
      		/////////////////////////////////////////////////////////////////////////////////////////////////////
      		if(companyClientTypeNumbercombo.getValue().equals("3"))
      			{
      			
      			parameters3.add(username);
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
          					if(Integer.valueOf(numberOfCarsS)<=1) {
        						JOptionPane.showMessageDialog(null,"You have just one car","error",JOptionPane.INFORMATION_MESSAGE);
        		            	flag=true;
        					}
        					else {
          					parametersp.add("customary"+numberOfCarsS+" cars");	
          					parametersp.add("14%*");
      			}
      			}
      	/////////////////////////////////////////////////////////////////////////////////////
      		if(companyClientTypeNumbercombo.getValue().equals("2"))
      			{
      			parametersp.add("customary , one car");
      			parametersp.add("4%");
      			}
      		if(companyClientTypeNumbercombo.getValue().equals("4"))
      			{
      			parametersp.add("monthly , one car");
      			parametersp.add("17%");
      			}
  	if(flag==false) {
      		parametersp.add(username);
 		   String sq="UPDATE purchaseplan "+" SET approach = ?, clienttype = ?, gasstation1 = ?, gasstation2 = ?, gasstation3 = ?, purchaseplannumber = ?, purchaseplandescription = ?, discount = ? WHERE username =?";
		    ClientUI.chat.accept(""); 
		    ChatClient.updateTable(sq, parametersp);
 		    
         	JOptionPane.showMessageDialog(null,"company edited successfuly","success message",JOptionPane.INFORMATION_MESSAGE);
 		   
         	refreshCompanyTable();
  	}
         }
         }
    	
    	
    }
    /**
     * this function turn off all the fields in the page until the user click on one of the table rows and call the refreshcompanytable function 
     */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	companypumpcombo.setValue("Choose");
    	companypumpcombo.setItems(companypumps);
    	paneDetails.setDisable(true);
    	companyApproachNumbercombo.setValue("Choose");
    	companyApproachNumbercombo.setItems(companyapproach);
    	companyClientTypeNumbercombo.setValue("Choose");
    	companyClientTypeNumbercombo.setItems(companytype);
		refreshCompanyTable();
		
	}
    /**
     * this function bring from the data base the table of the company clients and initialize the first page with all the companies details. 
     */
	public void refreshCompanyTable() {
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM myfuel.companyclients ";
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.getTable(selectQuery);
		} 
		catch (Exception e)
		{
			System.err.println("Error : get company table : client server problem");
			e.printStackTrace();
		}
		ObservableList<Company> oblist = FXCollections.observableArrayList();

		try
		{
			while(rs.next())
			{
				oblist.add(new Company(rs.getString("companyName"), rs.getString("mail"), rs.getString("phoneNumber"), rs.getString("address"), rs.getString("username"), rs.getString("passwordun")));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		CompanyNameTableColumn.setCellValueFactory(new PropertyValueFactory<Company, String>("companyName"));
		MailTableColumn.setCellValueFactory(new PropertyValueFactory<Company, String>("mail"));
		PhoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<Company, String>("phoneNumber"));
		AddressTableColumn.setCellValueFactory(new PropertyValueFactory<Company, String>("address"));
		usernameTableColumn.setCellValueFactory(new PropertyValueFactory<Company, String>("username"));
		passwordunTableColumn.setCellValueFactory(new PropertyValueFactory<Company, String>("passwordun"));
		
		
		CompanyUpdateTableView.setItems(oblist);

	}
	/**
	 * this function puts in all the fields the details of the company and bring the vehicle list of this specific company
	 * @param event, this event occurs when the user clicks on one of the table rows 
	 */
	@FXML
    void handleRowClick(MouseEvent event) {
		boolean flag=false;
		paneDetails.setDisable(false);
		Vehiclscombo.setValue("Choose");
		Vehiclscombo.setDisable(false);
		companyApproachNumbercombo.setDisable(false);
		companyClientTypeNumbercombo.setDisable(false);
		companyPazCheckBox.setDisable(false);
		companyDelekCheckBox.setDisable(false);
		companySonolCheckBox.setDisable(false);
		companyTenCheckBox.setDisable(false);
		companypumpcombo.setDisable(false);
		companypumpcombo.setValue(null);
		companyTenCheckBox.setSelected(false);
		companyPazCheckBox.setSelected(false);
		companySonolCheckBox.setSelected(false);
		companyDelekCheckBox.setSelected(false);
		companyApproachNumbercombo.setValue(null);
		companyClientTypeNumbercombo.setValue(null);
		CompanyVehicleNumberTextField.clear();
        int index = CompanyUpdateTableView.getSelectionModel().getSelectedIndex();
		
		if(index <= -1)
		{
			return;
		}
		CompanyNameTextField.setText(CompanyNameTableColumn.getCellData(index).toString());
		CompanyMailTextField.setText(MailTableColumn.getCellData(index).toString());
		CompanyPhoneTextField.setText(PhoneNumberTableColumn.getCellData(index).toString());
		CompanyAddressTextField.setText(AddressTableColumn.getCellData(index).toString());
	
			cars=new ArrayList<String>();
			pumps=new ArrayList<String>();
			vehicles =FXCollections.observableArrayList( );
			j=0;
	
    	
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
			
				if(!vehicles.contains(rscar.getString("vehicleNumber")))vehicles.add(rscar.getString("vehicleNumber"));
			}
			if(cars.size()==0)
				{
				CompanyVehicleNumberTextField.setText("No vehicle");
				Vehiclscombo.setValue(null);
				Vehiclscombo.setItems(null);
				companypumpcombo.setDisable(true);
				Vehiclscombo.setDisable(true);
				}
			else Vehiclscombo.setItems(vehicles);
			
				
			
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
			companyApproachNumbercombo.setValue(null);
			companyClientTypeNumbercombo.setValue(null);
				
			while(rs1.next())
			{
				companyApproachNumbercombo.setValue(rs1.getString("approach"));
				companyClientTypeNumbercombo.setValue(rs1.getString("clienttype"));
				if(rs1.getString("gasstation1").equals("Paz")||rs1.getString("gasstation2").equals("Paz")||rs1.getString("gasstation3").equals("Paz"))
					companyPazCheckBox.setSelected(true);
				if(rs1.getString("gasstation1").equals("Delek")||rs1.getString("gasstation2").equals("Delek")||rs1.getString("gasstation3").equals("Delek"))
					companyDelekCheckBox.setSelected(true);
				if(rs1.getString("gasstation1").equals("Sonol")||rs1.getString("gasstation2").equals("Sonol")||rs1.getString("gasstation3").equals("Sonol"))
					companySonolCheckBox.setSelected(true);
				if(rs1.getString("gasstation1").equals("Ten")||rs1.getString("gasstation2").equals("Ten")||rs1.getString("gasstation3").equals("Ten"))
					companyTenCheckBox.setSelected(true);	
				flag=true;
			}
	//	} 
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		if(flag==false) {
			companyApproachNumbercombo.setDisable(true);
			companyClientTypeNumbercombo.setDisable(true);
			companyPazCheckBox.setDisable(true);
			companyDelekCheckBox.setDisable(true);
			companySonolCheckBox.setDisable(true);
			companyTenCheckBox.setDisable(true);
		}
		CompanyNameTextField.setDisable(true);
    }
	/**
	 * this function bring from DB the pump number for each vehicle
	 * @param event , this event occurs when the user selects one of the vehicles number in the comboBox 
	 */
	@FXML
    void handleVehiclesCombo(ActionEvent event) {
		CompanyVehicleNumberTextField.setText(Vehiclscombo.getValue());
		ArrayList<String> param=new ArrayList<String>();
		String selectQuery = "SELECT * FROM myfuel.vehicles WHERE vehicleNumber=?";
        param.add(CompanyVehicleNumberTextField.getText());
			ClientUI.chat.accept("");
			ResultSet rscar = ChatClient.selectWithParameters(selectQuery, param);
		
		try
		{
		
			while(rscar.next())
			{
				companypumpcombo.setValue(rscar.getString("pumpNumber"));
				System.out.println(rscar.getString("pumpNumber"));

			}
			
		
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
}
}
