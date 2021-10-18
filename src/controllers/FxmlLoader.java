package controllers;

import java.net.URL;

import controllers.ClientMainC;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;


public class FxmlLoader
{
	private Pane view;
	
	public Pane getPage(String fileName)
	{
		try
		{
		
			URL fileUrl = ClientMainC.class.getResource("/gui/" + fileName + ".fxml");

			if( fileUrl == null)
			{
				throw new java.io.FileNotFoundException("FXML file can't be found");
			}
			
			view = new FXMLLoader().load(fileUrl);
		}
		catch(Exception e)
		{
			System.out.println("No page " + fileName + "\nPlease check fxmlLoader.");
		}
		
		return view;
	}
	
}
