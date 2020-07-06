package view;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import application.GameController;
import application.Spieler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class EndViewController implements Initializable{
	
	
	GameController gameController;
	
	@FXML
	Label lblS1Name;
	@FXML
	Label lblS2Name;
	@FXML
	Label lblS3Name;
	@FXML
	Label lblS1PunkteGesamt;
	@FXML
	Label lblS2PunkteGesamt;
	@FXML
	Label lblS3PunkteGesamt;
	@FXML
	HBox hBoxEndstand;
	@FXML
	Label lblPlatz3;
	
	private IntegerProperty restzeit;
	private static int TIMEOUT = 10;
	private Timer timer;
	long tStart;
	
	public void setMainController(GameController mainController) {
		this.gameController = mainController;
	}
	
	public void endGameRound(){
		gameController.showLobbyView();
	}
	
	public IntegerProperty getRestzeit() {
		if (restzeit == null) 
			restzeit = new SimpleIntegerProperty();
		return restzeit;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getRestzeit().setValue(TIMEOUT);
		tStart = new Date().getTime(); //setze Startzeit
		timer = new Timer();
		timer.scheduleAtFixedRate(tTask, 0, 1000);		
	}
	
	TimerTask tTask = new TimerTask() {
		@Override
		public void run() {
			long deltaT = (new Date().getTime()-tStart)/1000;
			getRestzeit().setValue((int)TIMEOUT-deltaT);
			if (getRestzeit().intValue()==0) {
				timer.cancel();
			}
			
		}
	};

	
public void setSpielerInformation(List<Spieler> spielerliste) {		
		
		lblS1Name.setText(spielerliste.get(0).getName().toString());
		lblS1PunkteGesamt.setText(spielerliste.get(0).getPunktestand().getValue().toString() + " Punkte");		
		
		lblS2Name.setText(spielerliste.get(1).getName().toString());
		lblS2PunkteGesamt.setText(spielerliste.get(1).getPunktestand().getValue().toString() + " Punkte");
		
		if(spielerliste.size() == 2) {
			hBoxEndstand.setStyle("-fx-border-color: none");
			lblS3Name.setText("");
			lblS3PunkteGesamt.setText("");
			lblPlatz3.setStyle("-fx-text-fill: white");
		}
		
		else if(spielerliste.size() == 3) {
			
			System.out.println("Spieler 3 sollte angezeigt werden");
			
			lblS3Name.setText(spielerliste.get(2).getName().toString());
			lblS3PunkteGesamt.setText(spielerliste.get(2).getPunktestand().getValue().toString() + " Punkte");
			lblPlatz3.setStyle("-fx-text-fill: black");
			
	}
	
	}


}
