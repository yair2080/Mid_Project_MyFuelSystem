package controllers;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import entities.Requests;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
/**
 * this class is the controller of the supplier requests,a requests table and accept button and reject button 
 *
 */
public class SupplierRequestsC implements Initializable {

    @FXML
    private Label userWelcomeLabel;

    @FXML
    private Button acceptbtn;

    @FXML
    private Button rejectbtn;

    @FXML
    private TableView<Requests> requesteTable;

    @FXML
    private TableColumn<Requests, String> datecolumn;
    @FXML
    private TableColumn<Requests, String> suppliercolumn;
    @FXML
    private TableColumn<Requests, String> gasstationmanager;

    @FXML
    private TableColumn<Requests,String> productname;

    @FXML
    private TableColumn<Requests,String> productamount;
    @FXML
    private TableColumn<Requests,String> deliveredcol;

    @FXML
    private TextArea reqtextarea;
    public static String gasstaman;
    String proname;
    double amount;
    String gasStation=null;
    String manager=null;
    String date=null;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	String dateString = format.format(new Date());
	/**
	 * this function handles the reject button ,it send a message to the person that sent him the request ,that rejects the request
	 * and delete the request from the requests table
	 * @param event,this event occurs when the user clicks on the reject button
	 */
    @FXML
    void handlerejectbtn(ActionEvent event) {
    	acceptbtn.setDisable(true);
    	
    	reqtextarea.clear();
    	 ArrayList<String> sendMessage=new ArrayList<String>();
    	 sendMessage.add(String.valueOf(dateString));
         sendMessage.add(LoginControllerC.userin);
         sendMessage.add(gasstaman);
         sendMessage.add("Order rejection");
         sendMessage.add("Sorry , I can't send you home heating fuel");
         String query= "INSERT INTO messages (date,sentFrom,sendTo,subject,body) VALUES (?,?,?,?,?);";
         ChatClient.updateTable(query, sendMessage);
         JOptionPane.showMessageDialog(null,"message sent successfuly","error",JOptionPane.INFORMATION_MESSAGE);
         reqtextarea.clear();
         String deletequery="DELETE FROM requests WHERE gasStationManager = ? and date = ?";
     	ArrayList<String> delete =new ArrayList<String>();
     	delete.add(gasstaman);
     	delete.add(date);
     	ClientUI.chat.accept("");
     	ChatClient.updateTable(deletequery, delete);
     	refreshrequestsTable();
    }
    @FXML
    void handleRowclick(MouseEvent event) {
int index = requesteTable.getSelectionModel().getSelectedIndex();
acceptbtn.setDisable(false);
rejectbtn.setDisable(false);
		if(index <= -1)
		{
			return;
		}
		
		reqtextarea.setText("Hello "+LoginControllerC.userin+",\nI want to order "+productamount.getCellData(index).toString()+" Liters\nfor "+productname.getCellData(index).toString()+".\n\n"+gasstationmanager.getCellData(index).toString()+","+gasStation);
		gasstaman=gasstationmanager.getCellData(index).toString();
		proname=productname.getCellData(index).toString();
		date=datecolumn.getCellData(index).toString();
		amount=Double.valueOf(productamount.getCellData(index).toString());
		System.out.println(amount);
    }
   /**
    * this function handles the accept button,the user accepts the request and then sent the products to the gas station(the real world)
    * ,update the numbers in the stock of the gas station(in our world-the application) and then sends a confirmation message
    * 
    * @param event,this event occurs when the user clicks on the accept button 
    */
    @FXML
    void handleacceptbtn(ActionEvent event) {
    	rejectbtn.setDisable(true);
    	ArrayList<String> parameters=new ArrayList<String>();
    	String updateQuery = "UPDATE products " + " SET amountInStock = ? WHERE gasStationName = ? and productName=?";

      	 parameters.add(amount+"");
      	 parameters.add(gasStation);
      	parameters.add(proname);
System.out.println(parameters.toString());
      	 ClientUI.chat.accept("");
		    ChatClient.updateTable(updateQuery, parameters);
   	    
		    ArrayList<String> sendMessage=new ArrayList<String>();
		    sendMessage.add(String.valueOf(dateString));
	         sendMessage.add(LoginControllerC.userin);
	         sendMessage.add(gasstaman);
	         sendMessage.add("Order confirmation");
	         sendMessage.add("your home heating stock updated");
	         String query= "INSERT INTO messages (date,sentFrom,sendTo,subject,body) VALUES (?,?,?,?,?);";
	         ChatClient.updateTable(query, sendMessage);
	         JOptionPane.showMessageDialog(null,"message sent successfuly","error",JOptionPane.INFORMATION_MESSAGE);
	         String updateQuery1 = "UPDATE requests " + " SET delivered = ? WHERE gasStationManager = ? and date = ?";
	         ArrayList<String> pa=new ArrayList<String>();
	         pa.add("yes");
	         pa.add(gasstaman);
	         pa.add(date);
	         ClientUI.chat.accept("");
	         ChatClient.updateTable(updateQuery1, pa);
	         reqtextarea.clear();
	         String deletequery="DELETE FROM requests WHERE gasStationManager = ? and date = ?";
	      	ArrayList<String> delete =new ArrayList<String>();
	      	delete.add(gasstaman);
	      	delete.add(date);
	      	ClientUI.chat.accept("");
	      	ChatClient.updateTable(deletequery, delete);
	      	refreshrequestsTable();
    }
    
    @Override
   	public void initialize(URL arg0, ResourceBundle arg1)
   	{
    	acceptbtn.setDisable(true);
    	rejectbtn.setDisable(true);
   		refreshrequestsTable();
   	}
       /**
        * this function up loads the requests table to the page 
        */
       public void refreshrequestsTable()
   	{
    	 	ResultSet rs1 = null;
    	 
           	ArrayList<String> param1=new ArrayList<String>();
           
          String selectQuery3 = "SELECT gasStation FROM myfuel.employees WHERE username=?";
            param1.add(LoginControllerC.username);
            System.out.println("param1= "+param1);
    		try
    		{
    			ClientUI.chat.accept("");
    			rs1 = ChatClient.selectWithParameters(selectQuery3, param1);
    			 try {
    					while(rs1.next())
    					{
    						 gasStation=rs1.getString("gasStation");
    						 System.out.println("gasStation= "+gasStation);
    					}
    				} catch (SQLException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    	    	 }
    		} 
    		catch (Exception e)
    		{
    			System.err.println("Error : get gas station name : client server problem");
    			e.printStackTrace();
    		}
    	
       
    		
    			
    		
       	ResultSet rs = null;
       	ArrayList<String> param=new ArrayList<String>();
   		String selectQuery = "SELECT * FROM myfuel.requests WHERE supplierName=?";
           param.add(LoginControllerC.userin);
           System.out.println("param= "+param);
   		try
   		{
   			ClientUI.chat.accept("");
   			rs = ChatClient.selectWithParameters(selectQuery, param);
   		} 
   		catch (Exception e)
   		{
   			System.err.println("Error : get requests table : client server problem");
   			e.printStackTrace();
   		}
   		ObservableList<Requests> oblist = FXCollections.observableArrayList();

   		try
   		{
   			while(rs.next())
   			{
   				oblist.add(new Requests(rs.getString("date"),rs.getString("supplierName"),rs.getString("gasStationManager"), rs.getString("productName"), rs.getString("productAmount"), rs.getString("delivered")));
   			}
   		} 
   		catch (SQLException e)
   		{
   			e.printStackTrace();
   		}
   		
   		datecolumn.setCellValueFactory(new PropertyValueFactory<Requests, String>("date"));
   		suppliercolumn.setCellValueFactory(new PropertyValueFactory<Requests, String>("supplierName"));
   		gasstationmanager.setCellValueFactory(new PropertyValueFactory<Requests, String>("gasStationManager"));
   		productname.setCellValueFactory(new PropertyValueFactory<Requests, String>("productName"));
   		productamount.setCellValueFactory(new PropertyValueFactory<Requests, String>("productAmount"));
   		deliveredcol.setCellValueFactory(new PropertyValueFactory<Requests, String>("delivered"));
   		
   		requesteTable.setItems(oblist);
   		
   	}
   		

}
