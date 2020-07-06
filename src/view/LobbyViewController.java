package view;



import application.GameController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class LobbyViewController {
	
	
	@FXML
	Label lblS1, lblS2, lblS3, lblS1Ready, lblS2Ready, lblS3Ready;
	
	@FXML
	ImageView imgS1, imgS2, imgS3;
	
	@FXML
	Button btnSpieler1, btnSpieler2, btnSpieler3;
	
	
	
	
	GameController gameController;
	public GameController setMainController;

	public void setMainController(GameController gameController) {
		this.gameController = gameController;
	}
	
	public void btnSpieler1Pressed() {
		
		//imgS1.setImage(img);
		gameController.createBuzzerView("Spieler 1", 800, 400);
	}
	
	public void btnSpieler2Pressed() {
		//imgS2.setImage(img);
		
		gameController.createBuzzerView("Spieler 2", 800, 710);
	}
	
	public void btnSpieler3Pressed() {
		//imgS3.setImage(img);
		gameController.createBuzzerView("Spieler 3", 800, 1020);
	}
	
	@FXML
	public void btnQuestionPressed(ActionEvent event) {	
		if(gameController.getGamecontroller().getSpielerliste().size() > 1) {
			gameController.lobbyNotifyDone();		
		}
	}
	
	public void setReady1(){
		lblS1Ready.setText("Ready");
		lblS1Ready.setStyle("-fx-border-color: #c10a27");
		lblS1Ready.setStyle("-fx-text-fill: black");
	}
	
	public void setReady2(){
		lblS2Ready.setText("Ready");
		lblS2Ready.setStyle("-fx-border-color: #c10a27");
		lblS2Ready.setStyle("-fx-text-fill: black");
	}
	
	public void setReady3(){
		lblS3Ready.setText("Ready");
		lblS3Ready.setStyle("-fx-border-color: #c10a27");
		lblS3Ready.setStyle("-fx-text-fill: black");
	}
}
