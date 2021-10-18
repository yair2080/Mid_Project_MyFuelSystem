package entities;

public class ItemInStock {
	/**
	 * this class is an entity for item in stock in DB myfuel.quartelystock
	 * it has productName,productAmount 
	 * date is for the month 
	 * this entity is used for displaying the item in the table stock report the station manager gets to generate
	 * quartelystock table in DB stores every end of a month the stock thats left in the station
	 * used in GasStationManagerStockReports.java 
	 */
	String productName,productAmount,date;

	public ItemInStock(String productName, String productAmount, String date) {
		super();
		this.productName = productName;
		this.productAmount = productAmount;
		this.date = date;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(String productAmount) {
		this.productAmount = productAmount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
