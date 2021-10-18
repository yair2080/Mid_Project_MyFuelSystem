package entities;

public class Requests {
	/**
	 * this class is an entity for bringing data from myfuel.requests DB to GasStationManagerOrderRequests.fxml
	 * used in controller GasStationManagerOrderRequestsC.java
	 */
String date;
String supplierName;
String gasStationManager;
String productName;
String productAmount;
String delivered;
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getSupplierName() {
	return supplierName;
}
public void setSupplierName(String supplierName) {
	this.supplierName = supplierName;
}
public String getGasStationManager() {
	return gasStationManager;
}
public void setGasStationManager(String gasStationManager) {
	this.gasStationManager = gasStationManager;
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
public Requests(String date, String supplierName, String gasStationManager, String productName, String productAmount,
		String delivered) {
	super();
	this.date = date;
	this.supplierName = supplierName;
	this.gasStationManager = gasStationManager;
	this.productName = productName;
	this.productAmount = productAmount;
	this.delivered = delivered;
}
public Requests(String date, String productName, String productAmount) {
	// TODO Auto-generated constructor stub
	super();
	this.date = date;
	this.productName = productName;
	this.productAmount = productAmount;
}



}
