package entities;

public class Purchase implements Comparable  {
	/**
	 * this class is an entity for displaying the station purchases for purchase report the station manager gets to generate
	 * data from myfuel.requests is displayed in GasStationManagerPurchasesReport.fxml with this entity 
	 * entity used in GasStationManagerPurchasesReports.java
	 */
String username;
String date;
double totalPrice;
String fueltype;
String total;
String productName;

public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getFueltype() {
	return fueltype;
}
public void setFueltype(String fueltype) {
	this.fueltype = fueltype;
}
public String getTotal() {
	return total;
}
public void setTotal(String total) {
	this.total = total;
}
@Override
public String toString() {
	return "Purchase [username=" + username + ", date=" + date + ", totalPrice=" + totalPrice + ", fueltype=" + fueltype + "]";
}
public String getUsename() {
	return username;
}
public void setUsename(String username) {
	this.username = username;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public double getAmount() {
	return totalPrice;
}
public void setAmount(double totalPrice) {
	this.totalPrice = totalPrice;
}
public String getFuelType() {
	return fueltype;
}
public void setFuelType(String fueltype) {
	this.fueltype = fueltype;
}
public Purchase(String username, String date, double totalPrice, String fueltype) {
	super();
	this.username = username;
	this.date = date;
	this.totalPrice = totalPrice;
	this.fueltype = fueltype;
}

public Purchase(String productName, String total, String date) {  //for purchase reports
	this.productName=productName;
	this.total=total;
	this.date=date;
}
@Override
public int compareTo(Object comparestu) {
    double compareage=((Purchase)comparestu).getAmount();
   
    return (int)this.totalPrice-(int)compareage;
}
}
