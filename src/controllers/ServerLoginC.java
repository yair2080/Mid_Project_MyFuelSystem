package controllers;

import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import DBconnector.mysqlConnection;
import Server.ServerUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * this class is the controller of the server login page , it connect to the data base by using the details that the user entered(port,localhost...)
 *
 */
public class ServerLoginC implements Initializable
{	
	@FXML
	private Button Connect = null;
	@FXML
	private Button disConnect = null;
	@FXML
	private TextField hostTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private TextField schemeTextField;
	@FXML
	private TextField databasePortTextField;
	@FXML
	private TextField serverPortTextField;
	@FXML
    private TextArea serverLogTextArea;	
	
	String ConnectionSring;
	
	/**
	 * this function opens the server login page
	 * @param primaryStage
	 * @throws Exception
	 */
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("gui/ServerLoginView.fxml"));
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("Server Configure");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
		/**
		 * this function initialize the fields with specific details
		 */
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		hostTextField.setText("localhost");
		usernameTextField.setText("root");
		passwordTextField.setText("Yara_314626961"); 
		schemeTextField.setText("myfuel"); 		 
		databasePortTextField.setText("3306");
		serverPortTextField.setText("5555");
	}
	
	
	/**
	 * this method handles the connect button , it turns the server on and builds an instance of the mysql connection and use it every time
	 * while testing the validation of every field
	 * @param event , this event occurs when the user clicks on the connect button
	 * @throws Exception
	 */
	public void handleConnectButtonClick(ActionEvent event) throws Exception 
	{
		try
		{
		    String port = serverPortTextField.getText();
		    
		    if(port.trim().isEmpty())
			{
				System.out.println("error : serverLoginC : You must enter a port number");			
			}
			else
			{
			    ServerUI.runServer(port);
			    sendMessageToLog("server started listening");
			} 
		}
		catch(Exception e)
		{
			sendMessageToLog("Error : port is taken or unavalable.\nserver did not connect");
		}
		
		
		try
		{
			Connection con = null;
			con =mysqlConnection.getInstance();
			
			if(con != null)
			{
				sendMessageToLog("server connected to mySql");
			}
		}
		catch(Exception e)
		{
			System.err.println("Error : serverLoginC : con = null");
		}
	
	}
	
	     /**
	      * this function handle the disconnect button by disconnecting the user from database 
	      * @param event, this event occurs when the user clicks on the disconnect button
	      * @throws Exception
	      */
    public void handleDisconnectButtonClick(ActionEvent event) throws Exception
 	{
 		sendMessageToLog("You are disconnected");
 		System.exit(0);
 	}
    /**
     * this function shows the messages that we get from the server
     * @param message
     */
    public void sendMessageToLog(String message)
    {
    	String timeStamp = new SimpleDateFormat("[hh:mm:ss]").format(Calendar.getInstance().getTime());
    	String fullMessage = timeStamp + " " + message + System.lineSeparator();
    	
    	Platform.runLater(new Runnable()
    	{
    		@Override
    		public void run()
    		{
    			serverLogTextArea.appendText(fullMessage);	
    		}
    	});
    }
}
