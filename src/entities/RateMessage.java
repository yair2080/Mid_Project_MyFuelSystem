package entities;

/**
 * this is a RateMessage entity
 * we created this entity to handle RateMessages information that is meant
 * to be in our rate request for the chain manager
 * we have here only a constructor, getter and setter to every component
 * all the variables are corresponding to the RateMessage table from the mySql
 */

public class RateMessage
{
	String date, chainManagerID, marketingManagerID, productName, requestedRate;

	public RateMessage(String date, String chainManagerID, String marketingManagerID, String productName, String requestedRate) 
	{
		this.date = date;
		this.chainManagerID = chainManagerID;
		this.marketingManagerID = marketingManagerID;
		this.productName = productName;
		this.requestedRate = requestedRate;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getChainManagerID()
	{
		return chainManagerID;
	}

	public void setChainManagerID(String chainManagerID)
	{
		this.chainManagerID = chainManagerID;
	}

	public String getMarketingManagerID()
	{
		return marketingManagerID;
	}

	public void setMarketingManagerID(String marketingManagerID)
	{
		this.marketingManagerID = marketingManagerID;
	}

	public String getProductName() 
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getRequestedRate()
	{
		return requestedRate;
	}

	public void setRequestedRate(String requestedRate)
	{
		this.requestedRate = requestedRate;
	}
}
