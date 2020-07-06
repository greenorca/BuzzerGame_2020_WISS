package view;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;



import application.Antwort;
import application.Frage;
import application.GameController;
import application.Spieler;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class QuestionViewController implements Initializable {

	GameController gameController;
	private Frage frage;
	@FXML
	private Label lblRestzeit;
	@FXML
	private Label lblFrage;
	@FXML
	private Label lblAntwort1;
	@FXML
	private Label lblAntwort2;
	@FXML
	private Label lblAntwort3;
	
	private IntegerProperty restzeit;
	private int btnClickCounter = 0;
	private static int TIMEOUT = 10;
	private Timer timer;
	long timeStart;
	int maxZeit;
	int antworten = 0;
	
	
	public IntegerProperty getRestzeit() {
		if (restzeit == null)
			restzeit = new SimpleIntegerProperty(TIMEOUT);
		return restzeit;
	}
	
	public void setMainController(GameController mainController) {
		this.gameController = mainController;
	}
	
	public void initFrage(Frage frage, Set<Spieler> spielerliste, int maxZeit) {
		this.frage = frage;
		lblFrage.setText(frage.getFrage());
		setAnswers(frage.getAntworten());
		this.maxZeit = maxZeit;
		this.timeStart = System.currentTimeMillis();
		initPlayers(spielerliste);
		
		
		getRestzeit().setValue(TIMEOUT);		
		timer = new Timer();
		timer.scheduleAtFixedRate(tTask, 0, 1000); //aktiviere zyklische Wiederholung
		
	}
	
	private void initPlayers(Set<Spieler> spielerliste) {
		spielerliste.forEach(spieler -> {		
			spieler.reset();
			spieler.getAntwortNr().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number alt, Number neu) {	
					
					if((int)neu == frage.korrekteAntwortInt()) {
						long pressedTime = System.currentTimeMillis();
						int punkte = (maxZeit*1000 - (int)(pressedTime - timeStart))/100;

						spieler.addPunkte(punkte);
						spieler.setRundenpunkte(punkte);

						System.out.println("'Fragerunde: '" + spieler.getName() + " hat " + punkte + " Punkte");
						
					}
					spieler.getAntwortNr().removeListener(this);
					antworten++;
					if (antworten >= spielerliste.size()) {
						restzeit.set(0);
					}
				}

			});
			System.out.println("'Fragerunde: '" + spieler.getName().toString() + " Listener added");
			spieler.setRundenpunkte(0);
		});
	}
	
	private void setAnswers(List<Antwort> antworten) {		
		//TODO: proper shuffling
		lblAntwort1.setText(antworten.get(0).getAntwort());
		lblAntwort2.setText(antworten.get(1).getAntwort());
		lblAntwort3.setText(antworten.get(2).getAntwort());
		
	}
	
	
	/**
	 * initialize Scene and start countdown timer
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	/**
	 * verantwortlich für das saubere Herunterzählen der Zeit
	 */
	TimerTask tTask = new TimerTask() {
		@Override
		public void run() {
			long deltaT = (new Date().getTime()-timeStart)/1000;
			getRestzeit().setValue((int)TIMEOUT-deltaT);
			Platform.runLater(updateRestzeitLabel); // 
			if (getRestzeit().intValue()<=0) {
				timer.cancel();
			}
		}
	};
	
	
	private Runnable updateRestzeitLabel = new Runnable() {			
		@Override
		public void run() {
			lblRestzeit.setText(String.valueOf(getRestzeit().intValue()));
		}
	};

	
	
}
	
