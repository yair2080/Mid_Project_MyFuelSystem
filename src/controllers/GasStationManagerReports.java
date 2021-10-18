package controllers;

import java.net.URL;
import java.util.Calendar; 
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  
/**
 * this class is a controller for GasStationManagerReports.fxml 
 * displays the date, checks if the manager can generate reports and displays an appropriate message
 * manager can generate reports if its the end of a quarter and the three report buttons are enabled
 * if its not the end of a quarter then he cannot generate reports and the reports buttons are disabled 
 * for the sake of the defense we have in the function initializeLabelcanOrCanNotGenerateReports we changed the date to 1/7 so that the report buttons are enabled
 */
public class GasStationManagerReports implements Initializable , MainInstanceInterface{
	
	private ConnectToInstance mainInstance = null;
    @FXML 
    private Label todaysDate;
    @FXML
    private Label userWelcomeLabel;
    @FXML
    private Label canOrCanNotGenerateReports;
    @FXML
    private Button incomeReportButton;
    @FXML
    private Button stockReportButton;
    @FXML
    private Button purchasesReportButton;
 /**
  * all calendar variables are used for checking if manager can generate reports
  * station manager can generate all reports only at the end of every quarter
  * for example ,at the start of April 1/4 or 1/7 or 1/10 or 1/1
  */
    String date;
    Calendar todayDate ;
    Calendar month1 = new GregorianCalendar(2020, Calendar.JANUARY, 01);
    Calendar month3 = new GregorianCalendar(2020, Calendar.MARCH, 01);
    Calendar month4 = new GregorianCalendar(2020, Calendar.APRIL, 01);
    Calendar month6 = new GregorianCalendar(2020, Calendar.JUNE, 01);
    Calendar month7 = new GregorianCalendar(2020, Calendar.JULY, 01);
    Calendar month8 = new GregorianCalendar(2020, Calendar.AUGUST, 01);
    Calendar month9 = new GregorianCalendar(2020, Calendar.SEPTEMBER, 01);
    Calendar month10 = new GregorianCalendar(2020, Calendar.OCTOBER, 01);
    Calendar month11 = new GregorianCalendar(2020, Calendar.NOVEMBER, 01);
    Calendar month12 = new GregorianCalendar(2020, Calendar.DECEMBER, 01);
    Calendar month2021 = new GregorianCalendar(2021, Calendar.JANUARY, 01);
/**
 * handle the buttons 
 * each button opens the relevant report for the manager
 */
    @FXML
    void handleincomeReportButton(ActionEvent event) {
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("GasStationManagerIncomeReport");
    	}
    }
    @FXML
    void handlepurchasesReportButton(ActionEvent event) {
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("GasStationManagerPurchaseReport");
    	}
    }
    @FXML
    void handlestockReportButton(ActionEvent event) {
    	if(mainInstance != null)
    	{
    		mainInstance.sendMessage("GasStationManagerStockReport");
    	}
    }
    public void setMainInstance(ConnectToInstance main_instance)
   	{
   		this.mainInstance=main_instance;
   	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		incomeReportButton.setDisable(true);
		stockReportButton.setDisable(true);
		purchasesReportButton.setDisable(true);
		initalizeDateLabel(); 
		initializeLabelcanOrCanNotGenerateReports();
	}
	/**
	 * get the date today to check if the manager can generate reports 
	 * also sets the text for the date label
	 */
	public void initalizeDateLabel()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
	    LocalDateTime now = LocalDateTime.now();  
	    date= dtf.format(now);
		//System.out.println(date);  
		todaysDate.setText(date);
		todayDate = new GregorianCalendar(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
	}
	public void initializeLabelcanOrCanNotGenerateReports()
	{
		todayDate= new GregorianCalendar(2020, Calendar.JULY, 01);  //////
		
		if(todayDate.equals(month4) || todayDate.equals(month7) || todayDate.equals(month10) || todayDate.equals(month2021))
		{
			canOrCanNotGenerateReports.setText("You may generate reports");
			incomeReportButton.setDisable(false);
			stockReportButton.setDisable(false);
			purchasesReportButton.setDisable(false);
		}
	 	else
			canOrCanNotGenerateReports.setText("You may generate reports at the end of the quarter");
		canOrCanNotGenerateReports.setAlignment(Pos.CENTER);
	}

	
}
