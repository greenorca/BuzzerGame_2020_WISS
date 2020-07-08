package view;

import application.GameController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;




public class StartupViewController{

	//application getter setter
	GameController gameController;
	
	@FXML Button btnPlay;
	
	@FXML
	public void btnLobbyPressed(ActionEvent event) {
		gameController.showLobbyView();
		//gameController.spielrundeStarten();
	}
	
	@FXML public void btnSettingsPressed() {
		gameController.editSettings();
	}
	
	public void setMainController(GameController gameController) {
		this.gameController = gameController;
		btnPlay.requestFocus();
	}
	
}
