package entities;
/**
 * this class represents Company object- it has variables that we need to save a Company.
 * we use this classe's instance in order to build the Company table for the use of Marketing rep
 * when updating Company.
 * every variable has its getters and setters, we use this class's constructor in 
 * "MarketingRepUpdateCompanyC".
 */
public class Company {
	String CompanyName;

	String Mail;

	String PhoneNumber;

	String Address;

	String username;

	String passwordun;

	public Company(String companyName, String mail, String phoneNumber, String address, String username,
			String passwordun) {
		super();
		CompanyName = companyName;
		Mail = mail;
		PhoneNumber = phoneNumber;
		Address = address;
		this.username = username;
		this.passwordun = passwordun;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public String getMail() {
		return Mail;
	}

	public void setMail(String mail) {
		Mail = mail;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
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
