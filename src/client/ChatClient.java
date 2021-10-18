package client;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import Server.EchoServer;
import common.ChatIF;
import ocsf.client.AbstractClient;
/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 */
public class ChatClient extends AbstractClient
{
	ChatIF clientUI;

	public static ResultSet rs;
	public static boolean awaitResponse = false;
	/**
	   * Constructs an instance of the chat client.
	   *
	   * @param host The server to connect to.
	   * @param port The port number to connect on.
	   * @param clientUI The interface type variable.
	   */
	public ChatClient(String host, int port, ChatIF clientUI) throws IOException 
	{
		super(host, port);
		this.clientUI = clientUI;
		
	}
	
	public static ResultSet getTable(String query)
	{
		rs = EchoServer.getTable(query);
		return rs;
	}
	
	public static void updateTable(String query, ArrayList<String> parameters)
	{
		EchoServer.updateTable(query, parameters);
	}
	
	public static ResultSet selectWithParameters(String query, ArrayList<String> parameters)
	{
		rs = EchoServer.selectWithParameters(query, parameters);
		return rs;
	}


	public void handleMessageFromServer(Object msg) 
	{
		
		
	}

	/**
	   * This method handles all data coming from the UI            
	   *
	   * @param message The message from the UI.    
	   */
	public void handleMessageFromClientUI(Object message)  
	{
		try
	    {
			openConnection();
	    	awaitResponse = true;
	    	
	    	
	    	sendToServer(message);
	    	awaitResponse = false;
	    	
			// wait for response
			while (awaitResponse)
			{
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	        clientUI.display("Could not send message to server: Terminating client." + e);
	        quit();
	    }
	}
	/**
	   * This method terminates the client.
	   */
	public void quit()
	{
		try
	    {
	      closeConnection();
	    }
	    catch(IOException e)
		{
	    	;
		}
		
	    System.exit(0);
	}
}
