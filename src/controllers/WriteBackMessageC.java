package controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import entities.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class WriteBackMessageC implements Initializable{
	/**
	 * this class is a controller for WriteBackMessage.fxml that we get to when user clicks on write back button in MessagesMain.fxml
	 * dateTextField contains automatically the current date 
	 * mailToTextField and mailSubjectTextField are filled automatically 
	 * the user gets to write the message body 
	 * when the user clicks on the save button the message is added to the messages table in DB 
	 */
    @FXML
    private Label userWelcomeLabel;
    @FXML
    private Button sendButton;
    @FXML
    private TextArea mailBodyTextArea;
    @FXML
    private TextField mailToTextField;
    @FXML
    private TextField mailSubjectTextField;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField dateTextField;

    Message msg=new Message();
    Alert alert = new Alert(AlertType.ERROR);
	/**
	 * fills automatically who the user replays to 	
	 */
    @Override
   	public void initialize(URL arg0, ResourceBundle arg1)
   	{ 
    	mailToTextField.setText(MessagesMainC.sendTo);
    	mailSubjectTextField.setText("Re:>" + MessagesMainC.subject);
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	    LocalDateTime now = LocalDateTime.now();  
	    String date= dtf.format(now); 
		dateTextField.setText(date);
		  msg.setDate(date);
   	}
    @FXML
    void handleCancleButton(ActionEvent event) {
    	try {
    		mailBodyTextArea.clear();
        	mailToTextField.clear();
        	mailSubjectTextField.clear();
        	 JOptionPane.showMessageDialog(null,"message is canceled","error",JOptionPane.INFORMATION_MESSAGE);
  	}
	catch(Exception e)
	  {
		e.printStackTrace();
	  }
    }
/**
 * sends message 
 * add the message to messages table in DB
 */
    @FXML
    void handleSendButton(ActionEvent event) {
     msg.setSentFrom(LoginControllerC.userin);
     msg.setSendTo(mailToTextField.getText());
     msg.setSubject(mailSubjectTextField.getText());
     msg.setBody(mailBodyTextArea.getText());
  ///   DateTimeFormatter dtf = DateTimeFormatter.ofPattern(" yyyy-MM-dd");  
	// LocalDateTime now = LocalDateTime.now();  
	// String date= dtf.format(now);  
	 // msg.setDate(date);
     if(msg.getSendTo().equals(""))
    	 {
    	 	//System.out.println("send To can't be empty");
    	 	alert.setTitle("Warning");
    	 	alert.setHeaderText("");
    		alert.setContentText("send To can't be empty");
    		alert.show();
    	 }
     else {
     ArrayList<String> sendMessage=new ArrayList<String>();
     sendMessage.add(msg.getDate());
     sendMessage.add(msg.getSentFrom());
     sendMessage.add(msg.getSendTo());
     sendMessage.add(msg.getSubject());
     sendMessage.add(msg.getBody());
     String query= "INSERT INTO messages (date,sentFrom,sendTo,subject,body) VALUES (?,?,?,?,?);";
     ChatClient.updateTable(query, sendMessage);
     JOptionPane.showMessageDialog(null,"message sent successfuly","error",JOptionPane.INFORMATION_MESSAGE);
     try {
    	 mailBodyTextArea.clear();
      	mailToTextField.clear();
      	mailSubjectTextField.clear();
      	//back to home
  	}
	catch(Exception e)
	  {
		e.printStackTrace();
	  }
    }
    }
  
}
