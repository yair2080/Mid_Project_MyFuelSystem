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
 * this controller is for the client main , it shows the options that client can do  
 *
 */
public class ClientMainC extends Application implements ConnectToInstance,Initializable
{
	private Stage primaryStage;
	private AnchorPane mainLayout;
	protected Pane view=null;
	protected FXMLLoader loader=new FXMLLoader();
	
	/** 
	 * JavaFX parameters
	 */
	@FXML
	private BorderPane homePane;
	
	@FXML
    private AnchorPane mainProgramPane;

    @FXML
    private Label headlineLable;

    @FXML
    private BorderPane whiteBorderPane;
    
    @FXML
    private Label userWelcomeLabel;
    
    @FXML
    private Label HomeLabel;
    
    
    @FXML
    void handleMoveToHomePageButtonClick(ActionEvent event) {
    	
    	loadPage("ClientHome",true);
    	
    }
    /**
     * handle button for entering home heating 
     * @param event
     */
    @FXML
    void handleHomeHeatingButtonClick(ActionEvent event)
    {
    	loadPage("ClientHomeHeatingMain",true);
    }
    
    /**
     * handle button for entering messages
     * @param event
     */
    @FXML
    void handleMessagesButtonClick(ActionEvent event)
    {
    	loadPage("MessagesMain",true);
    }

    /**
     * handle button for entering home 
     * @param event
     */
    @FXML
    void handleHomeButtonClick(ActionEvent event)
    {
    	loadPage("ClientHome",true);
    }
    
    /**
     * disconnect- deleting the username and password from the DB
     * @param event
     */
    @FXML
    void handleDisconnectButton(ActionEvent event) {
    	ArrayList<String> logout =new ArrayList<String>();
    	//logout.add("Logout");
    	logout.add(LoginControllerC.username);
    	logout.add(LoginControllerC.password);
    	String query="DELETE FROM logged WHERE username = ? and passwordun = ?";
    	
    	ClientUI.chat.accept("");
    	ChatClient.updateTable(query, logout);
     
  	JOptionPane.showMessageDialog(null,"logged out","logout",JOptionPane.INFORMATION_MESSAGE);
      try {
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
     * loadPage : the function is responsible to open every form INSIDE the marketing manager window
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
    
	@Override
	public void start(Stage primaryStage) throws IOException
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MyFuel App");
		
		initializeView();
	}
	
	/**
	 * initialize View
	 * @throws IOException
	 */
	private void initializeView() throws IOException
	{
		// load everything we are doing in the scene builder
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ClientMainC.class.getResource("gui/ClientMainView.fxml"));
		mainLayout = loader.load();
		
		Scene scene = new Scene(mainLayout);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userWelcomeLabel.setText("Hello "+LoginControllerC.userin+"!");
		
	}

}
