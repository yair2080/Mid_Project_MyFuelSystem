package controllers;

import javafx.fxml.FXMLLoader;

/**
 * this is ConnectToInstance interface 
 * 
 * sendMessage : this is the function of ConnectToInstance interface
 * here we handle opening internal forms from a chosen controller (chosen window)
 * for actions with this controller instance
 */

public interface ConnectToInstance
{
	public FXMLLoader sendMessage(String pageName);
}
