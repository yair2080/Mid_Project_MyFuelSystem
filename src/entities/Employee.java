package entities;
/**
 * this class represents Employee object- it has variables that we need to save an employee details.
 * we use this classe's instance in order to build the employee table for the use of Marketing rep
 * when updating employee.
 * every variable has its getters and setters, we use this class's constructor in 
 * "MarketingRepUpdateEmployeeC".
 */
public class Employee
{
	String firstName, lastName, workerID, mail, part, gasStation,username,password;

	public Employee(String firstName, String lastName, String workerID, String mail, String part, String gasStation, String username, String password)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.workerID = workerID;
		this.mail = mail;
		this.part = part;
		this.gasStation = gasStation;
		this.username=username;
		this.password=password;
	}

	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getWorkerID()
	{
		return workerID;
	}

	public void setWorkerID(String workerID) 
	{
		this.workerID = workerID;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public String getPart()
	{
		return part;
	}

	public void setPart(String part)
	{
		this.part = part;
	}

	public String getGasStation()
	{
		return gasStation;
	}

	public void setGasStation(String gasStation) 
	{
		this.gasStation = gasStation;
	}
}


