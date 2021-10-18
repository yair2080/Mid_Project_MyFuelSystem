package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import entities.ClientsRate;
import entities.Purchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
/**
 * this class is a controller of rates page , it brings all purchases of all clients and counts for each client the total price that he payed and sort 
 * clients from the highest total price to the lower and display them in a table view 
 *
 */
public class MarketingRepRatesC implements Initializable{

    @FXML
    private Label userWelcomeLabel;

    @FXML
    private TableView<ClientsRate> ratesTableView;

    @FXML
    private TableColumn<ClientsRate,Integer> RateTableColmn;

    @FXML
    private TableColumn<ClientsRate, String> UsernameTableColmn;

    @FXML
    private TableColumn<ClientsRate, String> ClientTypeTableColmn;

    @FXML
    private TableView<Purchase> purchasesTableView;

    @FXML
    private TableColumn<Purchase, String> DateTableColmn;

    @FXML
    private TableColumn<Purchase, String> AmountTableColmn;

    @FXML
    private TableColumn<Purchase,String> FuelTypeTableColmn;

    @FXML
    private TableColumn<Purchase,String> usernamecolumn;
    @FXML
    private ImageView imageRate;

   
    @FXML
    private TextField firstTextField;
/**
 * this function hide the name of the client in the first place and display all purchases in a table view of the selected client
 * @param event , this event occurs when the user clicks on one of the table rows
 */
    @FXML
    void handleRowClick(MouseEvent event) {
    	imageRate.setVisible(false);
    	firstTextField.setVisible(false);
    	
    	purchasesTableView.setVisible(true);///need to show purchase for client[index]
int index = ratesTableView.getSelectionModel().getSelectedIndex();
ObservableList<Purchase> oblist2 = FXCollections.observableArrayList();
		if(index <= -1)
		{
			return;
		}
		ArrayList<String> parameters=new ArrayList<String>();
		String query="SELECT * FROM myfuel.purchases WHERE username=?";
    	parameters.add(UsernameTableColmn.getCellData(index));
    	ResultSet r=ChatClient.selectWithParameters(query, parameters);
    	try {
			while(r.next()) {
				oblist2.add(new Purchase(r.getString("username"),r.getString("date"),Double.valueOf(r.getString("totalPrice")),r.getString("fueltype")));
				
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	usernamecolumn.setCellValueFactory(new PropertyValueFactory<Purchase,String>("username"));
    	DateTableColmn.setCellValueFactory(new PropertyValueFactory<Purchase,String>("date"));
    	AmountTableColmn.setCellValueFactory(new PropertyValueFactory<Purchase,String>("amount"));
    	FuelTypeTableColmn.setCellValueFactory(new PropertyValueFactory<Purchase,String>("fuelType"));
    	purchasesTableView.setItems(oblist2);
    }
    
    @Override
   	public void initialize(URL arg0, ResourceBundle arg1) {
   		
   		refreshRateTable();
   		
   	}
    /**
     * this function bring from the data base the table of the purchases , count for each client the total price that he payed , sort the clients and
     * display them from the highest total to the lower. 
     */
   	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	public void refreshRateTable() {
   		System.out.println("1");
   		ResultSet rs = null;

   		ArrayList<Purchase> pur=new ArrayList<Purchase>();
   		
   		
   		String selectQuery = "SELECT * FROM myfuel.purchases ";
   		try
   		{
   			ClientUI.chat.accept("");
   			rs = ChatClient.getTable(selectQuery);
   		} 
   		catch (Exception e)
   		{
   			System.err.println("Error : get purchases table : client server problem");
   			e.printStackTrace();
   		}
   		
   		try
   		{
   			
   			while(rs.next())
   			{   
   				pur.add(new Purchase(rs.getString("username"), rs.getString("date"), Double.valueOf(rs.getString("totalPrice")), rs.getString("fuelType")));
   				//System.err.println(pur.toString());
   			}
   		} 
   		catch (SQLException e)
   		{
   			e.printStackTrace();
   		}

   		
   			for(int j=0;j<pur.size();j++)
   			{
   				for(int i=j+1;i<pur.size();i++)
   				{
   		        	if(pur.get(j).getUsename().equals(pur.get(i).getUsename()))
   		        	{
   		    		pur.get(j).setAmount(pur.get(j).getAmount()+pur.get(i).getAmount());
   		    		pur.remove(i);
   		    		i--;
   		    		System.out.println("i= "+i);
   				    }
   			    }
   		
   			}
   		
   		
   			Collections.sort(pur);//need to give rate and upload table with rate
   			System.out.println("tot 2 "+pur);

   			ObservableList<ClientsRate> oblist1 = FXCollections.observableArrayList();
   			int k=1;
   			int range=pur.size()/10;
   			for(int i=10;i>0;i--)
            {
           	for(int j=i*range-1;j>=range*(i-1);j--)
           	
           	 {
           		 ArrayList<String> gettype=new ArrayList<String>();
           		 gettype.add(pur.get(j).getUsename());
           		 String sql="SELECT clienttype FROM purchaseplan WHERE username=?";
           		 ResultSet rs2=ChatClient.selectWithParameters(sql, gettype);
           		 try {
						if(rs2.next()) {
							try {
								oblist1.add(new ClientsRate(k,pur.get(j).getUsename(),rs2.getString("clienttype")));
								System.out.println(rs2.getString("clienttype")+"size"+pur.size()+"      "+j+" "+i);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							pur.remove(j);
							j++;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//need clienttype
           	 }
           	 k++;
            }
 
             RateTableColmn.setCellValueFactory(new PropertyValueFactory<ClientsRate,Integer>("rate"));
             UsernameTableColmn.setCellValueFactory(new PropertyValueFactory<ClientsRate,String>("username"));
             ClientTypeTableColmn.setCellValueFactory(new PropertyValueFactory<ClientsRate,String>("clientType"));
             ratesTableView.setItems(oblist1);
             firstTextField.setText(UsernameTableColmn.getCellData(0).toString());
             firstTextField.setAlignment(Pos.CENTER);
   	}
   	

}
