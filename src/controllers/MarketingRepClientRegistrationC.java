package controllers;//////////////DONE/////////////

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
/**
 * this controller is for MarketingRepClientRegistration gui , every client can have only one account in our app.
 * The marketingRep insert all client or company details , if all details are valid and there are no another client with the 
 * same ID or the same company name ,system give this client an username and pass word to login our app
 *
 */
public class MarketingRepClientRegistrationC implements MainInstanceInterface{
	private ConnectToInstance mainInstance = null;
    @FXML
    private Label userWelcomeLabel;
    @FXML
    private Pane singlePane;
    @FXML
    private Button singleclientbtn;

    @FXML
    private Button companybtn;

    @FXML
    private Pane companyPane;

    @FXML
    private TextField FirstNameSingleClientTextField;

    @FXML
    private TextField LastNameSingleClientTextField;

    @FXML
    private TextField IDNumberSingleClientTextField;

    @FXML
    private TextField CreditCardSingleClientTextField;

    @FXML
    private TextField MailSingleClientTextField;

    @FXML
    private Button SingleClientRegisterButton;

    @FXML
    private TextField FirstNameCompanyTextField;

    @FXML
    private TextField PhoneNumberCompanyTextField;

    @FXML
    private TextField MailCompanyTextField;
    @FXML
    private Button singleAddVehiclebtn;
    @FXML
    private Button companyAddVehiclebtn;
    @FXML
    private TextField AddressCompanyTextField;
    @FXML
    private Button CompanyRegisterButton;
    /**
     * @static parameter reg , this parameter help us to know if we register single client or company
     */
    public static int reg = 0;
    boolean flag=false;
    public static String username,password;
    ArrayList<String> client=new ArrayList<String>();
    ArrayList<String> company=new ArrayList<String>();
    /**
     * this function handles the add Vehicle button for a private client
     * after pressing the button it takes us to the MarketingRepVehicleRegistrationC
     * @param event , this event occurs when user click on add vehicle button
     */
    @FXML
    void handlesingleAddVehiclebtn(ActionEvent event) {
    	 reg=2;
    	 if(mainInstance != null)
     	{
     		mainInstance.sendMessage("MarketingRepVehicleRegistration");
     	}
    }
    /**
     * this function handles the add vehicle button for a company client
     * after pressing the button it takes us to MarketingRepVehicleRegistration
     * @param event, this event occurs when user click on add vehicle button
     */
    @FXML
    void handlecompanyAddVehiclebtn(ActionEvent event) {
    	reg=1;
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("MarketingRepVehicleRegistration");
    	}
    }
    /**
     * this function lights the company registration(turns the add company button on) and turns of the add single button 
     * @param event ,this event occurs when user click on register company button
     */
    @FXML
    void handlecompanybtn(ActionEvent event) {
    	singlePane.setDisable(true);
    	companyPane.setDisable(false);
    }
    /**
     * this function turns on the add single client button and turn of the add company button
     * @param event,this event occurs when user click on register single client button
     */
    @FXML
    void handlesingleclientbtn(ActionEvent event) {
    	companyPane.setDisable(true);
    	singlePane.setDisable(false);
    }
    
    /**
     * after turning on the add company button
     * this function handles the whole process of adding a company client to the data base
     * adding a new company client is saving the name of the company , its phone number ,mail and the address
     * and also testing every situation (leaving some fields empty...) 
     * @param event,this event occurs when user click on register button on company pane
     */
    @FXML
    void handleCompanyRegisterButton(ActionEvent event) {
    	boolean flag=false;
    
    	ArrayList<String> parameters=new ArrayList<String>();
    	
        if(FirstNameCompanyTextField.getText().equals("")||PhoneNumberCompanyTextField.getText().equals("")/*||MailCompanyTextField.getText().equals("")||AddressCompanyTextField.getText().equals("")*/)
        {
        	JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(MarketingRepMainC.isNumeric(PhoneNumberCompanyTextField.getText())==false||PhoneNumberCompanyTextField.getText().length()>10)
        {
        	JOptionPane.showMessageDialog(null,"Invalid phone number","error",JOptionPane.INFORMATION_MESSAGE);
        }
    
        else {
        company.add(FirstNameCompanyTextField.getText());
    	company.add(MailCompanyTextField.getText());
    	company.add(PhoneNumberCompanyTextField.getText());
    	company.add(AddressCompanyTextField.getText());
    	
        username=FirstNameCompanyTextField.getText()+"00";
        password=username;
        company.add(username);
        company.add(password);
        company.add("0");
        	if(flag==false) {
        		parameters.add(username);
        		parameters.add(password);
        		String query="SELECT * FROM companyclients WHERE username = ? and passwordun = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs = ChatClient.selectWithParameters(query, parameters);	
        	   
        			
        				try {
        					if(rs.next())
        					   {
        						JOptionPane.showMessageDialog(null,"client does already exist ","error",JOptionPane.INFORMATION_MESSAGE);
        						try {
        							flag=true;
        						
        							FirstNameCompanyTextField.clear();
        							MailCompanyTextField.clear();
        							PhoneNumberCompanyTextField.clear();
        							AddressCompanyTextField.clear();
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
        	
        	String sql4 = "INSERT INTO companyclients (companyName,mail,phoneNumber,address,username,passwordun,numOfVehicles) VALUES (?,?,?,?,?,?,?);";
        	ChatClient.updateTable(sql4, company);
       
        JOptionPane.showMessageDialog(null,"successfuly registration","success message",JOptionPane.INFORMATION_MESSAGE);
        try {
			
        	companyAddVehiclebtn.setDisable(false);
        	CompanyRegisterButton.setDisable(true);
     	}
	catch(Exception e)
	  {
		e.printStackTrace();
	  }
        }
        }
    }
    /**
     * after turning the add single client button
     * this function makes sure to add the single client with the right details to the data base
     * it adds his full name (first and last name ) ,id number ,mail and credit card number 
     * after testing every thing (leaving empty fields ,adding illegal id number ...)
     * @param event,this event occurs when user click on register button on single client pane
     */
    @FXML
    void handleSingleClientRegisterButton(ActionEvent event) {

    	boolean flag=false;
    	
    	ArrayList<String> parameters=new ArrayList<String>();
    	ArrayList<String> parameters1=new ArrayList<String>();
        if(FirstNameSingleClientTextField.getText().equals("")||LastNameSingleClientTextField.getText().equals("")||IDNumberSingleClientTextField.getText().equals("")/*||MailSingleClientTextField.getText().equals("")*/||CreditCardSingleClientTextField.getText().equals(""))
        {
        	JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);
           
        }
        else if(MarketingRepMainC.ischaracters(FirstNameSingleClientTextField.getText())==false)
        {
        	JOptionPane.showMessageDialog(null,"Invalid first name","error",JOptionPane.INFORMATION_MESSAGE);
        	FirstNameSingleClientTextField.clear();
        	
        }
        else if(MarketingRepMainC.ischaracters(LastNameSingleClientTextField.getText())==false)
        {
        	JOptionPane.showMessageDialog(null,"Invalid last name","error",JOptionPane.INFORMATION_MESSAGE);
        	LastNameSingleClientTextField.clear();
        	
        }
        else if(IDNumberSingleClientTextField.getText().length()!=9||MarketingRepMainC.isNumeric(IDNumberSingleClientTextField.getText())==false)
        {
        	JOptionPane.showMessageDialog(null,"Invalid ID number","error",JOptionPane.INFORMATION_MESSAGE);
        	IDNumberSingleClientTextField.clear();
        	
        }
     
        else if(CreditCardSingleClientTextField.getText().length()<=8||CreditCardSingleClientTextField.getText().length()>16||MarketingRepMainC.isNumeric(CreditCardSingleClientTextField.getText())==false)
        {
        	JOptionPane.showMessageDialog(null,"Invalid Credit Card number","error",JOptionPane.INFORMATION_MESSAGE);
        	 CreditCardSingleClientTextField.clear();
        	 
        	 }
     
        else {
        	client.add(FirstNameSingleClientTextField.getText());
            client.add(LastNameSingleClientTextField.getText());
            client.add(IDNumberSingleClientTextField.getText());
            client.add(MailSingleClientTextField.getText());
            client.add(CreditCardSingleClientTextField.getText());
            username=FirstNameSingleClientTextField.getText()+LastNameSingleClientTextField.getText();
            password=username;
            
            if(flag==false) {
        		parameters.add(IDNumberSingleClientTextField.getText());
        		String query="SELECT * FROM singleclient WHERE IdNumber = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs = ChatClient.selectWithParameters(query, parameters);	
        	   
        			
        				try {
        					if(rs.next())
        					   {
        						JOptionPane.showMessageDialog(null,"client is already exist","error",JOptionPane.INFORMATION_MESSAGE);
        						try {
        							flag=true;
        						
        							FirstNameSingleClientTextField.clear();
        							LastNameSingleClientTextField.clear();
        							IDNumberSingleClientTextField.clear();
        							MailSingleClientTextField.clear();
        							CreditCardSingleClientTextField.clear();
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
        		parameters1.add(username);
        		parameters1.add(password);
        		String query="SELECT * FROM singleclient WHERE username = ? and passwordun = ?";
        		ClientUI.chat.accept("");
        	    ResultSet rs = ChatClient.selectWithParameters(query, parameters1);	
        				try {
        					if(rs.next())
        					   {
        						username=FirstNameSingleClientTextField.getText()+LastNameSingleClientTextField.getText()+IDNumberSingleClientTextField.getText().charAt(0);
        						System.out.println(username);
        						
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
        		client.add(username);
                client.add(password);
                client.add("0");
        	String sql4 = "INSERT INTO singleclient (firstName,lastName,IdNumber,mail,creditCard,username,passwordun,numOfVehicles) VALUES (?,?,?,?,?,?,?,?);";
        	ChatClient.updateTable(sql4, client);
        	 JOptionPane.showMessageDialog(null,"successfuly registration","success message",JOptionPane.INFORMATION_MESSAGE);
             try {
     			
            	 singleAddVehiclebtn.setDisable(false);
            	 SingleClientRegisterButton.setDisable(true);
            	
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
   		this.mainInstance=main_instance;
   	}
}
