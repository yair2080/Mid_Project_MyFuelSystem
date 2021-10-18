package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * this is a controller for ChainManagerMain gui.
 * the main form is the first window we see when we login as a chain manager.
 * in the left side of the window we have buttons that lead us to forms that are connected to the chain manager.
 * in the center we have a pane which to it we upload every related form.
 */

public class ChainManagerMainC extends Application implements ConnectToInstance, Initializable
{
	protected Pane view = null;
	
	protected FXMLLoader loader = new FXMLLoader();
	
	
	protected Stage primaryStage;
	protected AnchorPane mainLayout;
	
	@FXML
	protected AnchorPane mainProgramPane;

    @FXML
    protected Label headlineLable;

    @FXML
	protected BorderPane whiteBorderPane;
    
    @FXML
    private BorderPane homePane;
    
    @FXML
    private Label helloLabel;
    
    /**
     * handleHomeButtonClick opens the chain manager home form.
     * @param event - this event occurs when the user clicks on the home button
     */
    
    @FXML
    void handleHomeButtonClick(ActionEvent event)
    {	
    	loadPage("ChainManagerHome", false);
    }
    
    /**
     * handleMessagesButtonClick opens the chain manager messages form.
     * @param event - this event occurs when the user clicks on the messages button
     */
    
    @FXML
    void handleMessagesButtonClick(ActionEvent event) 
    {
    	loadPage("MessagesMain", true);
    }
    
    /**
     * handleRatesButtonClick opens the chain manager rates form.
     * @param event - this event occurs when the user clicks on the rates button
     */

    @FXML
    void handleRatesButtonClick(ActionEvent event)
    {    	
    	loadPage("ChainManagerRates", false);
    }
    
    /**
     * start : opens the chain manager first window after the user login.
     */
    
    @Override
	public void start(Stage primaryStage) throws IOException
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MyFuel App");
		
		initializeView();
	}
    
    /**
     * initializeView : initialize chain manager first window after the user login.
     */
	
	private void initializeView() throws IOException
	{
		// load everything we are doing in the scene builder
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ClientMainC.class.getResource("/gui/ChainManagerMainView.fxml"));
		mainLayout = loader.load();
		
		Scene scene = new Scene(mainLayout);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
     * handleDisconnectButton closes the chain manager window form
     * and opens the login form.
     * it removes the username from the logged table, so it can be login once again.
     * @param event - this event occurs when the user clicks on the disconnect button
     * @author Yara
     */
	
	@FXML
	void handleDisconnectButton(ActionEvent event)
	{
	   String query= "DELETE FROM logged WHERE username = ? and passwordun = ?";
    	
    	ArrayList<String> logout =new ArrayList<String>();
    	
    	logout.add(LoginControllerC.username);
    	logout.add(LoginControllerC.password);
    	
    	ClientUI.chat.accept("");
    	ChatClient.updateTable(query, logout);
     
    	JOptionPane.showMessageDialog(null,"logged out","error",JOptionPane.INFORMATION_MESSAGE);
     
    	try 
    	{
    		((Node) event.getSource()).getScene().getWindow().hide();  
			
			Pane root = FXMLLoader.load(getClass().getResource("gui/LoginView.fxml"));//build the gui
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
	}
	
	/**
     * main : the function is responsible to launch the window
     */

	public static void main(String[] args)
	{
		launch(args);
	}
	
	/**
     * loadPage : the function is responsible to open every form INSIDE the chain manager window
     * @param pageName - the function gets the page name to load
     * @param moreFiles - this is a boolean parameter that says if the form that we want to open
     * has \ has not any other forms that connected to it.
     * this way we make sure that all internal forms will still be opened in the main window
     */
	
	public FXMLLoader loadPage(String pageName, Boolean moreFiles)
	{
		loader = new FXMLLoader();
		loader.setLocation(MarketingManagerMainC.class.getResource("gui/" + pageName + ".fxml"));
		
		try 
		{
			view = loader.load();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		if(moreFiles)
		{
			((MainInstanceInterface)loader.getController()).setMainInstance(this);
		}
		
    	whiteBorderPane.setCenter(view);
    	homePane.toBack();
    	whiteBorderPane.toFront();
    	
		return loader;
	}

	 /**
     * sendMessage : this is the function from the ConnectToInstance interface
     * here we handle opening internal forms from this controller (this window)
     * @param pageName - the function gets the page name to load
     * @return FXMLLoader - the function returns an FXMLLoader object we sometimes used
     * to do actions on this controller instance
     */
	
	@Override
	public FXMLLoader sendMessage(String pageName)
	{
		return loadPage(pageName, false);
	}

	/**
     * initialize : the function is responsible to show the username
     * with a greeting message in the side of the window 
     */
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		helloLabel.setText("Hello "+ LoginControllerC.userin+"!");
	}
}
