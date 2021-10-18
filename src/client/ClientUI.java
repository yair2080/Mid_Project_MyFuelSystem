package client;
import controllers.LoginControllerC;
import javafx.application.Application;
import javafx.stage.Stage;
/**
 * this class is the main of the client,to turn on the client witch mean open the first form we have to execute the client UI  
 *
 */
public class ClientUI extends Application
{
	public static ClientController chat;
	
	public static void main( String args[] ) throws Exception
	{
		launch(args);  
	} 
	
	public void start(Stage primaryStage) throws Exception
	{
		chat = new ClientController("localhost", 5555);
		
		LoginControllerC aFrame = new LoginControllerC();
		
		aFrame.start(primaryStage);
	}
}
