package entities;
/**
 * this class represents Single Client object- it has variables that we need to save a single client details.
 * we use this classe's instance in order to build the single client table for the use of Marketing rep
 * when updating client.
 * every variable has its getters and setters, we use this class's constructor in 
 * "MarketingRepUpdateClientC".
 */
public class SingleClient {
   String firstName;
   String lastName;
   String IdNumber;
   String mail;
   String creditCard;
   String username;
   String passwordun;
   
public SingleClient(String firstName, String lastName, String idNumber, String mail, String creditCard, String username,
		String passwordun) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	IdNumber = idNumber;
	this.mail = mail;
	this.creditCard = creditCard;
	this.username = username;
	this.passwordun = passwordun;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getIdNumber() {
	return IdNumber;
}
public void setIdNumber(String idNumber) {
	IdNumber = idNumber;
}
public String getMail() {
	return mail;
}
public void setMail(String mail) {
	this.mail = mail;
}
public String getCreditCard() {
	return creditCard;
}
public void setCreditCard(String creditCard) {
	this.creditCard = creditCard;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPasswordun() {
	return passwordun;
}
public void setPasswordun(String passwordun) {
	this.passwordun = passwordun;
}
   
   


}
