
package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import entities.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * this class is the controller of the client follow order page , it shows a table of the orders that this user ordered 
 *
 */
public class ClientFollowOrdersC implements Initializable{
 
	/**
	 * JavaFX parameters
	 */
    @FXML
    private Label userWelcomeLabel;

    @FXML
    private TableView<Order> tblFollowOrders;

    @FXML
    private TableColumn<Order, Integer> ordernumberCol;

    @FXML
    private TableColumn<Order, LocalDate> arrivaldateCol;
    
    @FXML
    private TableColumn<Order, Date> orderDateCol;

    @FXML
    private TableColumn<Order, Integer> quantityCol;
    @FXML
    private TableColumn<Order, String> Statuscol;
    
    /**
     * initializing view
     */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		refreshOrdersTable();
		
	}
	
	/**
	 * refreshing orders table
	 * this function builds the table "follow orders"
	 */
	public void refreshOrdersTable()
	{
		Order or=new Order();
		boolean flag=false;
		
    	ResultSet rs = null;
    	ArrayList<String> param=new ArrayList<String>();
		String selectQuery = "SELECT orderNumber,orderDate,arrivalDate,quantity FROM myfuel.orders WHERE username=?";
        param.add(LoginControllerC.userin);
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
			
		} 
		catch (Exception e)
		{
			System.err.println("Error : get orders table : client server problem");
			e.printStackTrace();
		}
		ObservableList<Order> oblist = FXCollections.observableArrayList();
        
		try
		{
			while(rs.next())
			{
				flag=false;
				
				if((rs.getDate("arrivalDate").toLocalDate().compareTo(java.time.LocalDate.now()))<0) 
					{
						flag=true;
					}
				 or=new Order(rs.getInt("orderNumber"),rs.getDate("orderDate"),rs.getDate("arrivalDate").toLocalDate(), rs.getInt("quantity"),flag);
				 
				oblist.add(or);
				
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		ordernumberCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderNumber"));
		orderDateCol.setCellValueFactory(new PropertyValueFactory<Order, Date>("orderDate"));
		arrivaldateCol.setCellValueFactory(new PropertyValueFactory<Order, LocalDate>("arrivalDate"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("quantity"));
		Statuscol.setCellValueFactory(new PropertyValueFactory<Order, String>("Status"));
		System.out.println(or.Status);
		tblFollowOrders.setItems(oblist);
		
	}
		

}
