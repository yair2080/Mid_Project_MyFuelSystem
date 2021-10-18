package entities;
/**
 * this class represents purchase plan object- it has variables that we need to save a purchase plan.
 * we use this classe's instance in order to build the employee table for the use of Marketing rep
 * when updating single client or company client.
 * every variable has its getters and setters, we use this class's constructor in 
 * "MarketingRepUpdateClientC", "MarketingRepUpdateCompanyC".
 */
public class PurchasePlan {
String username;
char approach;
char clienttype;
String gasstation1;
String gasstation2;
String gasstation3;
String purchaseplannumber;
String purchaseplandescription;
String discount;
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public char getApproach() {
	return approach;
}
public void setApproach(char approach) {
	this.approach = approach;
}
public char getClienttype() {
	return clienttype;
}
public void setClienttype(char clienttype) {
	this.clienttype = clienttype;
}

public String getGasstation1(char string) {
	switch(string) {
	case '1':setGasstation1("Paz");
	break;
	case '2':setGasstation1("Delek");
	break;
	case '3':setGasstation1("Sonol");
	break;
	case '4':setGasstation1("Ten");
	break;
	}
	return gasstation1;
}
public void setGasstation1(String gasstation1) {
	this.gasstation1 = gasstation1;
}
public String getGasstation2(char string) {
	switch(string) {
	case '1':setGasstation2("Paz");
	break;
	case '2':setGasstation2("Delek");
	break;
	case '3':setGasstation2("Sonol");
	break;
	case '4':setGasstation2("Ten");
	break;
	default: setGasstation2("");
	}
	return gasstation2;
}
public void setGasstation2(String gasstation2) {
	this.gasstation2 = gasstation2;
}
public String getGasstation3(char string) {
	switch(string) {
	case '1':setGasstation3("Paz");
	break;
	case '2':setGasstation3("Delek");
	break;
	case '3':setGasstation3("Sonol");
	break;
	case '4':setGasstation3("Ten");
	break;
	default: setGasstation3("");
	}
	return gasstation3;
}
public void setGasstation3(String gasstation3) {
	this.gasstation3 = gasstation3;
}
public String getPurchaseplannumber() {
	return purchaseplannumber;
}
public void setPurchaseplannumber(String purchaseplannumber) {
	this.purchaseplannumber = purchaseplannumber;
}
public String getPurchaseplandescription(char num, String numofcars) {
	switch(num) {
	case '1':setPurchaseplandescription("reguler");
	break;
	case '2':setPurchaseplandescription("customary , one car");
	break;
	case '3':setPurchaseplandescription("customary "+numofcars+" cars");
	break;
	case '4':setPurchaseplandescription("monthly , one car");
	break;
	}
	return purchaseplandescription;
}
public void setPurchaseplandescription(String purchaseplandescription) {
	this.purchaseplandescription = purchaseplandescription;
}
public String getDiscount(char string, String numofcars) {
	switch(string) {
	case '1':setDiscount("0%");
	break;
	case '2':setDiscount("4%");
	break;
	case '3':setDiscount(4*(Integer.parseInt(numofcars))+10+"%");
	break;
	case '4':setDiscount("17%");
	break;
	}
	return discount;
}
public void setDiscount(String discount) {
	this.discount = discount;
}

}
