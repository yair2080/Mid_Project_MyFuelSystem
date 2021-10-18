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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GasStationManagerMain extends Application implements ConnectToInstance,Initializable 
{
	protected Pane view = null;
	protected FXMLLoader loader = new FXMLLoader();
	protected Stage primaryStage;
	protected AnchorPane mainLayout; 
	@FXML
	private Button messagesImageButton;
	@FXML
	private Button stockAlertImageButton;
    @FXML
    private AnchorPane mainProgramPane;
    @FXML
    private ImageView logoImage;
    @FXML
    private Label headlineLable;
    @FXML
    private VBox menuVbox;
    @FXML
    private Button homeButton;
    @FXML
    private ImageView homePicture;
    @FXML
    private Button stockButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button messagesButton;
    @FXML
    private Label helloName;
    @FXML
    private BorderPane whiteBorderPane;
    @FXML
    private BorderPane homePane;
    @FXML
    private Label userWelcomeLabel;
    @FXML
    private Label messageNoteLabel;
    @FXML
    private Label ordersNoteLabel; 
   ///////
   /**
    *the five functions bellow displays the appropriate fxml file 
    */
    @FXML
    void handleHomeButtonClick(ActionEvent event) 
    {
    	loadPage("GasStationManagerHome",false);
    } 
    @FXML
    void handleMessagesButtonClick(ActionEvent event)
    {
    	loadPage("MessagesMain",true);
    } 
    @FXML
    void handleReportsButtonClick(ActionEvent event)
    {
    	loadPage("GasStationManagerReports",true); 
    } 
    @FXML
    void handleStockButtonClick(ActionEvent event)
    {
    	loadPage("GasStationManagerStock",false);
    }
    @FXML
    void handleRequestsButtonClick(ActionEvent event) {
    	loadPage("GasStationManagerOrderRequests",false);
    }
    @FXML
    void handleDisconnectButton(ActionEvent event)
    {
    	ArrayList<String> logout =new ArrayList<String>();
    
    	logout.add(LoginControllerC.username);
    	logout.add(LoginControllerC.password);
    	String query="DELETE FROM logged WHERE username = ? and passwordun = ?";
    	
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
	@Override
	public void start(Stage primaryStage) throws IOException
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("MyFuel App");
		initializeView();
		
	} 
	private void initializeView() throws IOException
	{ 
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(GasStationManagerMain.class.getResource("gui/GasStationManagerMainView.fxml"));
		mainLayout = loader.load();
		
		Scene scene = new Scene(mainLayout);

		primaryStage.setScene(scene);
		primaryStage.show();
	} 
	public static void main(String[] args)
	{ 
		launch(args); 
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
	public void initialize(URL arg0, ResourceBundle arg1) { 
		helloName.setText("Hello " + LoginControllerC.userin + "!");
		helloName.setAlignment(Pos.CENTER);
		
	}
}
