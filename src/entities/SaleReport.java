package entities;

/**
 * this is a SaleReport entity
 * we created this entity to handle Sales information that is meant
 * to be in our sale reports
 * we have here only a constructor, getter and setter to every component
 * all the variables are corresponding to the saleReport table from the mySql
 */

public class SaleReport
{
	String saleCode, username;
	Integer cost;
	String gasStation;

	public SaleReport(String saleCode, String username, Integer cost, String gasStation) 
	{
		this.saleCode = saleCode;
		this.username = username;
		this.cost = cost;
		this.gasStation = gasStation;
	}

	public String getSaleCode() 
	{
		return saleCode;
	}

	public void setSaleCode(String saleCode)
	{
		this.saleCode = saleCode;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username) 
	{
		this.username = username;
	}

	public Integer getCost() 
	{
		return cost;
	}

	public void setCost(Integer cost) 
	{
		this.cost = cost;
	}

	public String getGasStation() 
	{
		return gasStation;
	}

	public void setGasStation(String gasStation)
	{
		this.gasStation = gasStation;
	}	
}
