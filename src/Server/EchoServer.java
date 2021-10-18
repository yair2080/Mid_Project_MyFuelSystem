package Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import DBconnector.mysqlConnection;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 */
public class EchoServer extends AbstractServer
{	
	
	public static Connection conn;
	
	public static ResultSet rs;

	public EchoServer(int port) 
	{
		super(port);
	}
	 public static ResultSet getTable(String query)
		{
	    	rs = mysqlConnection.selectFromTable(query);
			return rs;
		}
	    
	    // we added this
	    public static void updateTable(String query, ArrayList<String> parameters)
		{
	    	mysqlConnection.updateTable(query, parameters);
		}
	    
	    // we added this
	    public static ResultSet selectWithParameters(String query, ArrayList<String> parameters)
		{
	    	rs = mysqlConnection.SelectWithParameters(query, parameters);
			return rs;
		}
	    /**
	     * This method handles any messages received from the client.
	     *
	     * @param msg The message received from the client.
	     * @param client The connection from which the message originated.
	     */
	public void handleMessageFromClient  (Object msg, ConnectionToClient client)
	{
	 
		
	}
	
	 /**
	   * This method overrides the one in the superclass.  Called
	   * when the server starts listening for connections.
	   */
	protected void serverStarted()
	{
	    System.out.println ("Server listening for connections on port " + getPort());
	}
	/**
	   * This method overrides the one in the superclass.  Called
	   * when the server stops listening for connections.
	   */
	protected void serverStopped()
	{
		System.out.println ("Server has stopped listening for connections.");
	} 
}
