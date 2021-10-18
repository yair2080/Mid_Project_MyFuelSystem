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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * this class is the main controller of all the forms of marketingRep
 *
 */
public class MarketingRepMainC extends Application implements ConnectToInstance,Initializable 
{
	protected Stage primaryStage;
	protected AnchorPane mainLayout;
	protected Pane view=null;
	protected FXMLLoader loader=new FXMLLoader();
	@FXML
    protected AnchorPane mainProgramPane;
	@FXML
    private Label helloLabel;
    @FXML
    protected Label headlineLable;
    @FXML
    private Label userWelcomeLabel;
    @FXML
    protected BorderPane whiteBorderPane;
    @FXML
    protected BorderPane homePane;
   
/**
 * this function open the MarketingRep home form
 * @param event,this event occurs when the user clicks on home button
 */
    @FXML
    void handleHomeButtonClick(ActionEvent event)
    {
    	loadPage("MarketingRepHome",false);
    }
    /**
     * this function open the MarketingRep client vehicle registration form
     * @param event,this event occurs when the user clicks on vehicle registration button
     */
    @FXML
    void handleCarRegistrationButtonClick(ActionEvent event)
    {
    	loadPage("MarketingRepVehicleRegistration",true);
    }
    /**
     * this function open the MarketingRep client registration form
     * @param event,this event occurs when the user clicks on client registration button
     */
    @FXML
    void handleClientRegistrationButtonClick(ActionEvent event)
    {
    	loadPage("MarketingRepClientRegistration",true);
    }
    /**
     * this function open the MarketingRep purchase plan registration form
     * @param event,this event occurs when the user clicks on purchase plan registration button
     */
    @FXML
    void handlePurchasePlanButtonClick(ActionEvent event)
    {
    	loadPage("MarketingRepPurchasePlan",false);
    }
    /**
     * this function open the MarketingRep rates form
     * @param event,this event occurs when the user clicks on rates button
     */
    @FXML
    void handleRatesButtonClick(ActionEvent event)
    {
    	loadPage("MarketingRepRates",false);
    }
    /**
     * this function open the MarketingRep main update form
     * @param event,this event occurs when user click on update button
     */
    @FXML
    void handleUpdatePersonalDetailsButtonClick(ActionEvent event)
    {
    	loadPage("MarketingRepUpdateMain",true);
    }
    /**
     * this function open the message main form
     * @param event,this event occurs when the user clicks on message button
     */
    @FXML
    void handleMessagesButtonClick(ActionEvent event) 
    {
    	loadPage("MessagesMain",true);
    }
    @FXML
    void handlemessageNoteLabel(MouseEvent event) {

    }
    /**
     * this function log out the user from application
     * @param event,this event occurs when the  user clicks on logout button
     */
    @FXML
    void handleDisconnectButton(ActionEvent event) {
    	
    	String query="DELETE FROM logged WHERE username = ? and passwordun = ?";/////NOT Working!!
    	ArrayList<String> logout =new ArrayList<String>();
    	logout.add(LoginControllerC.username);
    	logout.add(LoginControllerC.password);
    	ClientUI.chat.accept("");
    	ChatClient.updateTable(query, logout);
     
  	JOptionPane.showMessageDialog(null,"logged out","error",JOptionPane.INFORMATION_MESSAGE);
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
  /**
   * this function check if all chars in the input are numbers
   * @param str , input string that we need to check
   * @return yes if all chars are numbers else it returns false
   */
  public static boolean isNumeric(String str)
  {
      for (char c : str.toCharArray())
      {
          if (!Character.isDigit(c)) return false;
      }
      return true;
  }
  /**
   * this function check if all chars in the input are characters
   * @param str , input string that we need to check
   * @return yes if all chars are characters else it returns false
   */
  public static boolean ischaracters(String str)
  {
      for (char c : str.toCharArray())
      {
          if (!Character.isLetter(c)) return false;
      }
      return true;
  }
 
    
    @Override
	public void start(Stage primaryStage) throws IOException
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MyFuel App");
		
		initializeView();
	}
	
	private void initializeView() throws IOException
	{ 
		
		// load everything we are doing in the scene builder
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ClientMainC.class.getResource("gui/MarketingRepMainView.fxml"));
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
		helloLabel.setText("Hello "+LoginControllerC.userin+"!");
		
	}
}