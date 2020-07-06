package view;

import application.GameController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;




public class StartupViewController{

	//application getter setter
	GameController gameController;
	
	@FXML
	public void btnLobbyPressed(ActionEvent event) {
		gameController.showLobbyView();
		//gameController.spielrundeStarten();
	}
	
	public void setMainController(GameController gameController) {
		this.gameController = gameController;
	}
	
}
