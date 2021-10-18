package controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

public class MessagesCreationC implements Initializable{
/**
 * this class is a controller for MessagesCreation.fxml that we get to when user clicks on create new message button in MessagesMain.fxml
 * dateTextField contains automatically the current date 
 * user gets to fill texts who to send the message to, subject and body 
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
    private TextField dateTextField;
    @FXML
    private Button cancelButton;
    Message msg=new Message();
    Alert alert = new Alert(AlertType.ERROR);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

   	String dateString = format.format(new Date());
/**
 * when the user clicks on cancel button resets the message fields
 */
    @FXML
    void handleCancleButton(ActionEvent event) {
    	mailBodyTextArea.clear();
    	mailToTextField.clear();
    	mailSubjectTextField.clear();
    	 JOptionPane.showMessageDialog(null,"message is canceld","error",JOptionPane.INFORMATION_MESSAGE);
    }
/**
 * adds to the messages table in DB 
 * checks if the fields are empty and notify the user accordingly
 */
    @FXML
    void handleSendButton(ActionEvent event) {
     msg.setSentFrom(LoginControllerC.userin);
     msg.setSendTo(mailToTextField.getText());
     msg.setSubject(mailSubjectTextField.getText());
     msg.setBody(mailBodyTextArea.getText());
     if(msg.getSendTo().equals("")) {
    	 System.out.println("send To can't be empty");
    	 JOptionPane.showMessageDialog(null,"There are empty fields","error",JOptionPane.INFORMATION_MESSAGE);
     }
     else {
     ArrayList<String> sendMessage=new ArrayList<String>();
     sendMessage.add(String.valueOf(dateString));
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
    /**
     * adds the date today automatically
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
	    LocalDateTime now = LocalDateTime.now();  
	    String date= dtf.format(now); 
		dateTextField.setText(date);		
	}

}



