package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import entities.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MessagesMainC implements Initializable , MainInstanceInterface{
	/**
	 * this class is a controller for MessagesMain.fxml 
	 * opens the messages page where he have table of the messages the user got from the myfuel.messages in DB
	 * this class is a general class for all users that use the messages
	 *  in the messages page we have create new message button that opens the MessagesCreation.fxml file
	 *  when the user clicks on a message -a row in the table the write back button and delete message button are enabled
	 * when the user selects a message, the TextArea messageBodyTextArea show the full message 
	 * delete message button deletes the message from db and refreshes the table in the page 
	 */
	private ConnectToInstance mainInstance = null;
		@FXML
	    private Label userWelcomeLabel;
	   	@FXML
	    private TableView<Message> incomeMessagesTableView;
	    @FXML
	    private TableColumn<Message, String> SentFromTableColumn;
	    @FXML
	    private TableColumn<Message, String> dateTableColumn;
	    @FXML
	    private TableColumn<Message, String> HeadlineTableColumn;
	    @FXML
	    private TableColumn<Message, String> bodyColumn;
	    @FXML
	    private TableColumn<Message, String> sendToColumn;
	    @FXML
	    private TextArea messageBodyTextArea;
	    @FXML
	    private Button WriteBackButton;
	    @FXML
	    private Button createNewMessageButton;
	    @FXML
	    private Button deleteMessageButton;
	    public static String sendTo,subject;
	    Message msg=new Message();
	    ObservableList<Message> oblist2;
	    String body,strMsg,dateStr,sentFromStr;
	    ArrayList<String>listDeleteMsg=new ArrayList<String>();
	    Alert alertError = new Alert(AlertType.ERROR);
	    Alert alertSuccess = new Alert(AlertType.CONFIRMATION);
	    Alert alertAreYouSure = new Alert(AlertType.CONFIRMATION);
  /**
   * disable write back button and delete msg button until user selects a row from the table  
   */
    @Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
    	WriteBackButton.setDisable(true);
    	deleteMessageButton.setDisable(true);
		refreshMessageTable();
	} 
   /**
    * loads the messages table for every user with his relevant messages
    */
    public void refreshMessageTable()
	{
    	ResultSet rs = null;
    	ArrayList<String> param=new ArrayList<String>();
		String selectQuery = "SELECT * FROM myfuel.messages WHERE sendTo=?";
        param.add(LoginControllerC.userin);
		try
		{
			ClientUI.chat.accept("");
			rs = ChatClient.selectWithParameters(selectQuery, param);
		} 
		catch (Exception e)
		{
			System.err.println("Error : get message table : client server problem");
			e.printStackTrace();
		}
		ObservableList<Message> oblist = FXCollections.observableArrayList();
		try
		{
			while(rs.next())
			{
				oblist.add(new Message(rs.getString("date"),rs.getString("sentFrom"), rs.getString("subject"), rs.getString("body"),rs.getString("sendTo")));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		SentFromTableColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("sentFrom"));
		dateTableColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("date"));
		HeadlineTableColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("subject"));
		bodyColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("body"));
		sendToColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("sendTo"));
		incomeMessagesTableView.setItems(oblist);	
	}  
   /**
    * create new message button opens the messagesCreation fxml file
    * and the write back message opens fxml like the message creation but loads the send to and subject automatically
    */
    @FXML
    void handleCreateNewMessageButton(ActionEvent event) {
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("MessagesCreation");
    	}
    } 
    @FXML
    void handleWriteBackButton(ActionEvent event) {
     //open message creation with sendTo is the same with handleRow sentFreom
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("WriteBackMessage");
    	}
    }
 /**
  * when the user selects a row
  * enable the delete button and the write back button
  * also loads the message with body message at the right corner of the file
  */
    @FXML
    void handleMessageRowClick(MouseEvent event) {
	    WriteBackButton.setDisable(false);
	    deleteMessageButton.setDisable(false);
	    int index = incomeMessagesTableView.getSelectionModel().getSelectedIndex();		
		if(index <= -1)
		{
			return;
		} 
		body=bodyColumn.getCellData(index).toString();
		sendTo=SentFromTableColumn.getCellData(index).toString();
		subject=HeadlineTableColumn.getCellData(index).toString();
		dateStr=dateTableColumn.getCellData(index).toString();
		sentFromStr=SentFromTableColumn.getCellData(index).toString();
		strMsg= "Date: "+dateStr + "\nFrom: "+sentFromStr+"\nSubject: "+subject + ",\n" + body ;
		messageBodyTextArea.setText(strMsg); 
    }
    public void setMainInstance(ConnectToInstance main_instance)
	{
		this.mainInstance=main_instance;
	}
   /**
    * removes msg from messages table and refreshs the table
    */
    @FXML
    void handleDeleteMessageButton(ActionEvent event) {   
	    	listDeleteMsg.add(sentFromStr);
			listDeleteMsg.add(LoginControllerC.userin.toString());
			listDeleteMsg.add(subject);
			listDeleteMsg.add(body);
			String selectQuery = "DELETE FROM myfuel.messages WHERE sentFrom=? AND sendTo=? AND subject=? AND body=?";
			try
			{
				ClientUI.chat.accept("");
				ChatClient.updateTable(selectQuery, listDeleteMsg);
			} 
			catch (Exception e)
			{
				System.err.println("could not delete");
				alertSuccess.setTitle("ERROR");
				alertSuccess.setHeaderText("");
				alertSuccess.setContentText("Deleting message failed");
				alertSuccess.show();
				e.printStackTrace();
			}
			listDeleteMsg.clear(); 
			alertSuccess.setTitle("confirmation");
			alertSuccess.setHeaderText("");
			alertSuccess.setContentText("Deleted message successfully");
			alertSuccess.show();
			
			refreshMessageTable(); 
	    	deleteMessageButton.setDisable(true); 
	    	messageBodyTextArea.clear();
    }
}
