package entities;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
/**
 * this class represents order object- it has variables that we need to save an order.
 * we use this classe's instance in order to build the orders table- in "follow orders".
 * every variable has its getters and setters, we call this class's constructor in 
 * "ClientFollowOrdersC".
 *
 */
public class Order {
	
	public int orderNumber;
	public String Address;
	public LocalDate arrivalDate;
	public int quantity;
	public String Status; 
	 
	public String getStatus(boolean flag) {
		if(flag)return "Done";
		 return "On the way";
	}
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	public Date orderDate = new Date();
	  
	public Order()
	{super();}
	
	public Order(int orderNumber,Date orderDate,LocalDate arrivalDate,int quantity,boolean flag)
	{
		this.orderNumber=orderNumber;
		this.quantity=quantity;
		this.arrivalDate = arrivalDate;
		this.orderDate = orderDate;
		if(flag)this.Status= "Done";
		else this.Status= "On the way";
	}
	 /*public Order(int orderNumber,String address, String arrivalDate, int quantity, SimpleDateFormat formatter, Date orderDate) {
			super();
			this.orderNumber=orderNumber;
			Address = address;
			this.arrivalDate = arrivalDate;
			this.quantity = quantity;
			this.formatter = formatter;
			this.orderDate = orderDate;
		}*/
	
	public int getOrderNumber() {
		return orderNumber;
	}


	public SimpleDateFormat getFormatter() {
		return formatter;
	}
	public void setFormatter(SimpleDateFormat formatter) {
		this.formatter = formatter;
	}
	public String getOrderDate() {
		return formatter.format(orderDate);
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	
	public LocalDate getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
