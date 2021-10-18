package entities;
/**
 * this class represents income object- we use this variables to save income.
 * we use this classe's instance in order to save the income and the gas station manager
 * use it when making the reports.
 * we use this constructor to build the income table.
 * we use it in "GasStationManagerIncomeReports".
 */
public class Income {
	 
	String fuelType; 
	String total; 
	String date;
	public Income(String fuelType, String total, String date) {
		super();
		this.fuelType = fuelType;
		this.total = total;
		this.date = date;
	}
	public Income(String total)
	{
		this.total=total;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	} 
	
}
