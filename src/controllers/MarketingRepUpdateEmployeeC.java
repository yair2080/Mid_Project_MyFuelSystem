
package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import entities.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
/**
 * this class is a controller of the update employee page , first we can see all the details of each employee and then we can change all details except 
 * the worker ID witch is the key of the employees.
 *
 */
public class MarketingRepUpdateEmployeeC implements Initializable{
	@FXML
    private Pane paneDetails;
    @FXML
    private Label userWelcomeLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField workerIDField;

    @FXML
    private TextField partField;

    @FXML
    private TextField mailField;

    @FXML
    private Button updateButton;

    @FXML
    private TextField gasStationField;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> firstNameHeadline;

    @FXML
    private TableColumn<Employee, String> lastNameHeadline;

    @FXML
    private TableColumn<Employee, String> workerIDHeadline;

    @FXML
    private TableColumn<Employee, String> mailHeadline;

    @FXML
    private TableColumn<Employee, String> partHeadline;

    @FXML
    private TableColumn<Employee, String> gasStationHeadline;
    @FXML
    private TableColumn<Employee, String> usernameHeadline;

    @FXML
    private TableColumn<Employee, String> passwordunHeadline;
/**
 * this function get the index of the selected row and show all the details of this specific employee.
 * @param event, this event occurs when the user clicks on one of the the table rows  
 */
    @FXML
    void handleEmployeeRowClick(MouseEvent event) {
		paneDetails.setDisable(false);

    	 int index = employeeTable.getSelectionModel().getSelectedIndex();
 		
 		if(index <= -1)
 		{
 			return;
 		}
 		firstNameField.setText(firstNameHeadline.getCellData(index).toString());
 		lastNameField.setText(lastNameHeadline.getCellData(index).toString());
 		workerIDField.setText(workerIDHeadline.getCellData(index).toString());
 		mailField.setText(mailHeadline.getCellData(index).toString());
 		partField.setText(partHeadline.getCellData(index).toString());
 		gasStationField.setText(gasStationHeadline.getCellData(index).toString());

 		workerIDField.setDisable(true);
    }
/**
 * this function check if all the new details that the user typed are valid and update that details in the data base .
 * @param event, this event occurs when the user clicks on the update button 
 */
    @FXML
    void handleUpdateButtonClick(ActionEvent event) {
    	workerIDField.setDisable(true);
    	ArrayList<String> parameters=new ArrayList<String>();
    	if(firstNameField.getText().equals("")||lastNameField.getText().equals("")||mailField.getText().equals("")||partField.getText().equals("")||gasStationField.getText().equals(""))
    	{
        	JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);

    	}
         else {
        	 
 			String updateQuery = "UPDATE employees " + " SET firstName = ?, lastName = ?, mail = ?, part = ?, gasStation = ? WHERE workerID = ?";

        	 parameters.add(firstNameField.getText());
        	 parameters.add(lastNameField.getText());
        	 parameters.add(mailField.getText());
        	 parameters.add(partField.getText());
        	 parameters.add(gasStationField.getText());
        	 parameters.add(workerIDField.getText());

        	 ClientUI.chat.accept("");
 		    ChatClient.updateTable(updateQuery, parameters);
 		    
         	JOptionPane.showMessageDialog(null,"Client edited successfuly","error",JOptionPane.INFORMATION_MESSAGE);
 		   
         	refreshEmployeeTable();
         }
    }
    /**
     * in this function we turn off all the fields until the user click on one of the table rows , and we call the refresh function  
     */
    @Override
   	public void initialize(URL arg0, ResourceBundle arg1) {
		paneDetails.setDisable(true);

   		refreshEmployeeTable();
   		
   	}
    /**
     * this function bring from the data base the table of the employees and initialize the first page with all the employees details. 
     * 
     */
   	public void refreshEmployeeTable() {
   		ResultSet rs = null;
   		
   		String selectQuery = "SELECT * FROM myfuel.employees ";
   		try
   		{
   			ClientUI.chat.accept("");
   			rs = ChatClient.getTable(selectQuery);
   		} 
   		catch (Exception e)
   		{
   			System.err.println("Error : get employees table : client server problem");
   			e.printStackTrace();
   		}
   		ObservableList<Employee> oblist = FXCollections.observableArrayList();

   		try
   		{
   			while(rs.next())
   			{
   				oblist.add(new Employee(rs.getString("firstName"), rs.getString("lastName"), rs.getString("workerID"), rs.getString("mail"), rs.getString("part"),rs.getString("gasStation"),rs.getString("username"), rs.getString("passwordun")));
   			}
   		} 
   		catch (SQLException e)
   		{
   			e.printStackTrace();
   		}

   		firstNameHeadline.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
   		lastNameHeadline.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
   		workerIDHeadline.setCellValueFactory(new PropertyValueFactory<Employee, String>("workerID"));
   		mailHeadline.setCellValueFactory(new PropertyValueFactory<Employee, String>("mail"));
   		partHeadline.setCellValueFactory(new PropertyValueFactory<Employee, String>("part"));
   		gasStationHeadline.setCellValueFactory(new PropertyValueFactory<Employee, String>("gasStation"));
   		usernameHeadline.setCellValueFactory(new PropertyValueFactory<Employee, String>("username"));
   		passwordunHeadline.setCellValueFactory(new PropertyValueFactory<Employee, String>("passwordun"));

   		
   		employeeTable.setItems(oblist);

   	}
   	
}

