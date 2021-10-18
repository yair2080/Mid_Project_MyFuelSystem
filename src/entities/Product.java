package entities;
/**
 * this class is the entity of the product it has :product name ,rate,amount in stock,status,threshold level,max price and the gas station name
 * for every one we have a setter and a getter
 * it has three constructors:one that gets every thing and second one  get every thing except the gas station name and the last 
 * one gets two strings 
 *
 */
public class Product {
String productName;
String Rate;
String amountInStock;
String Status;
String thresholdLevel;
String maxPrice;
String gasStationName;
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getRate() {
	return Rate;
}
public void setRate(String rate) {
	Rate = rate;
}
public String getAmountInStock() {
	return amountInStock;
}
public void setAmountInStock(String amountInStock) {
	this.amountInStock = amountInStock;
}
public String getStatus() {
	return Status;
}
public void setStatus(String status) {
	Status = status;
}
public String getThresholdLevel() {
	return thresholdLevel;
}
public void setThresholdLevel(String thresholdLevel) {
	this.thresholdLevel = thresholdLevel;
}
public String getMaxPrice() {
	return maxPrice;
}
public void setMaxPrice(String maxPrice) {
	this.maxPrice = maxPrice;
}
public String getGasStationName() {
	return gasStationName;
}
public void setGasStationName(String gasStationName) {
	this.gasStationName = gasStationName;
}
public Product(String productName, String rate, String amountInStock, String status, String thresholdLevel,
		String maxPrice, String gasStationName) {
	super();
	this.productName = productName;
	Rate = rate;
	this.amountInStock = amountInStock;
	Status = status;
	this.thresholdLevel = thresholdLevel;
	this.maxPrice = maxPrice;
	this.gasStationName = gasStationName;
}
public Product(String productName, String rate, String amountInStock, String status, String thresholdLevel,
		String maxPrice) {
	super();
	this.productName = productName;
	Rate = rate;
	this.amountInStock = amountInStock;
	Status = status;
	this.thresholdLevel = thresholdLevel;
	this.maxPrice = maxPrice;
}
public Product(String string, String string2) {
	// TODO Auto-generated constructor stub
}

}
