package DBconnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * this is the mysqlConnection class
 * here we perform the design pattern of the singleton pattern to create
 * only one connection to the mySql. and we use this same object (instance) 
 * over and over again.
 * other then the connection itself, we are also a here 3 general functions
 * that handle every mySQL query that we needed
 * 1. select from table (handle select queries without parameters)
 * 2. select with parameters (handle select queries with parameters)
 * 3. update table (handle queries with a PreparedStatement)
 */

public class mysqlConnection 
{
	private static Connection con = null;
	public static boolean awaitResponse2 = true;
	private static Connection getConnection()
	{
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeed");
        }
		catch (Exception ex)
		{
        	 System.out.println("Driver definition failed");
		}
        
        try 
        {
        	con = DriverManager.getConnection("jdbc:mysql://localhost/myfuel?serverTimezone=Asia/Jerusalem","root","Yara_314626961");
        	
            System.out.println("SQL connection succeed");
            return con;
     	}
        catch (SQLException ex)
        {
        	System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        return null;
   	}
	
	public static Connection getInstance() 
    { 
        if (con == null) 
        {
        	con = getConnection();
        }
        return con; 
    }
	
	/**
	 * this main function IS NOT part of this class but
	 * I have used this main as an example for all the members in our team
	 * for how to use all of this (mySql) functions that i write
	 */
	public static void main(String[] args) 
	{
		
	}
	
	/**
	 * selectFromTable : this function is going threw the process of
	 * a regular select query
	 * @param query = a specific query that we sent
	 * (example :"select * form employees")
	 * @return ResultSet (as usual)
	 */

	public static ResultSet selectFromTable(String query)
	{
		Connection con = getInstance();
		
		try 
		{
			ResultSet rs = con.createStatement().executeQuery(query);
			return rs;	
		} 
		catch (SQLException e) 
		{
			System.err.println("Error : there is a problem with selectFromTable function");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * updateTable : this function is going threw the process of
	 * update query with a prepareStatement
	 * @param query = a specific query that we sent
	 * (example :"update employees set workerID = ? where workerID = ?")
	 * @param ArrayList that contains all the parameters for the ? of the query
	 */
	
	public static void updateTable(String query, ArrayList<String> parameters)
	{
		Connection con = getInstance();
		
	    try
	    {
	    	PreparedStatement ps = con.prepareStatement(query);
	    	
	    	for(int i = 1; i <= parameters.size(); i++)
	    	{
	    		ps.setString(i, parameters.get(i-1));
	    	}
		    
	    	
		    ps.executeUpdate();
		    
		    
		}
	    catch (SQLException e)
	    {
	    	System.err.println("Error : there is a problem with updateTable function");
			e.printStackTrace();
		}
	    awaitResponse2=false;
	}
	
	/**
	 * SelectWithParameters : this function is going threw the process of
	 * a select query with parameters
	 * @param query = a specific query that we sent
	 * (example :"select * from employees where workerID = ?")
	 * @param ArrayList that contains all the parameters for the ? of the query
	 */
	
	public static ResultSet SelectWithParameters(String query, ArrayList<String> parameters)
	{
		Connection con = getInstance();
		
	    try
	    {
	    	PreparedStatement ps = con.prepareStatement(query);
	    	
	    	for(int i = 1; i <= parameters.size(); i++)
	    	{
	    		ps.setString(i, parameters.get(i-1));
	    	}
		    
	    	
		    ResultSet rs = ps.executeQuery();
		    return rs;
		}
	    catch (SQLException e)
	    {
	    	System.err.println("Error : there is a problem with updateTable function");
			e.printStackTrace();
		}
	    
	    return null;
	}
}


