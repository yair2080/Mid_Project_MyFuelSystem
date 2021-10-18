package entities;
/**
 * this class represents rate object - we use this variables to give rate to clients ,
 *  we use this classe's interface in order to rate clients by the amount of their purchases .
 *   we use this constructor to build the rate table in "MarketingRepRate"
 *
 */
public class ClientsRate {
int rate;
String username;
String clienttype;
public int getRate() {
	return rate;
}
public void setRate(int rate) {
	this.rate = rate;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getClientType() {
	return clienttype;
}
public void setClientType(String clientType) {
	this.clienttype = clientType;
}
public ClientsRate(int rate, String username, String clientType) {
	super();
	this.rate = rate;
	this.username = username;
	this.clienttype = clientType;
}
}
