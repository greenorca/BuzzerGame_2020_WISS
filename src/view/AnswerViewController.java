package view;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import application.Frage;
import application.GameController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AnswerViewController implements Initializable{
	
	GameController gameController;
	@FXML
	Label lblAntwort;
	@FXML
	Label lblFrage;
	
	private IntegerProperty restzeit;
	private static int TIMEOUT = 5;
	private Timer timer;
	long tStart;
	
	public IntegerProperty getRestzeit() {
		if (restzeit == null) 
			restzeit = new SimpleIntegerProperty();
		return restzeit;
	}
	
	
	public void setFrage(Frage f) {
		lblFrage.setText(f.getFrage());
	}
	
	public void setAnswer(Frage f) {
		//lblFrage = new Label();		
		lblAntwort.setText(f.korrekteAntwort());
		
	}
	
	public void setMainController(GameController mainController) {
		this.gameController = mainController;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getRestzeit().setValue(TIMEOUT);
		tStart = new Date().getTime(); //setze Startzeit
		timer = new Timer();
		timer.scheduleAtFixedRate(tTask, 0, 500);
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
}

