package entities;

/**
 * this is a sale entity
 * we created this entity to handle sales details in our forms
 * we have here only a constructor, getter and setter to every component
 * all the variables are corresponding to the sales table from the mySql
 */

public class Sale
{
	String productName, discount, startDate, daysDuration,
	startHour, hoursDuration, gasStation, pictureColor, saleCode;

	public Sale(String productName, String discount, String startDate, String daysDuration, String startHour,
			String hoursDuration, String gasStation, String pictureColor, String saleCode)
	{
		this.productName = productName;
		this.discount = discount;
		this.startDate = startDate;
		this.daysDuration = daysDuration;
		this.startHour = startHour;
		this.hoursDuration = hoursDuration;
		this.gasStation = gasStation;
		this.pictureColor = pictureColor;
		this.saleCode = saleCode;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getDiscount() 
	{
		return discount;
	}

	public void setDiscount(String discount) 
	{
		this.discount = discount;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate) 
	{
		this.startDate = startDate;
	}

	public String getDaysDuration() 
	{
		return daysDuration;
	}

	public void setDaysDuration(String daysDuration)
	{
		this.daysDuration = daysDuration;
	}

	public String getStartHour() 
	{
		return startHour;
	}

	public void setStartHour(String startHour) 
	{
		this.startHour = startHour;
	}

	public String getHoursDuration()
	{
		return hoursDuration;
	}

	public void setHoursDuration(String hoursDuration)
	{
		this.hoursDuration = hoursDuration;
	}

	public String getGasStation() 
	{
		return gasStation;
	}

	public void setGasStation(String gasStation)
	{
		this.gasStation = gasStation;
	}

	public String getPictureColor()
	{
		return pictureColor;
	}

	public void setPictureColor(String pictureColor)
	{
		this.pictureColor = pictureColor;
	}

	public String getSaleCode() 
	{
		return saleCode;
	}

	public void setSaleCode(String saleCode)
	{
		this.saleCode = saleCode;
	}
	
	
	
}
