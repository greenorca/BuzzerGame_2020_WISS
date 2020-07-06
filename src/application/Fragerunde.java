package application;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Fragerunde {

	//wenn fragerunde gestartet, abonniert sich an den BuzzerPressedProperties von den Spielern
	// sobald sich buzzerpressed ändert, bekommt die fragerunde die möglichkeit sich zu ändern bzw den Punktestand anzupassen

	long timeStart;
	private Frage frage;
	//int runde;
	private List<Spieler> spielerListe;
	int antwort = 0;
	private int maxZeit, restZeit = 10;
	private Timer myTimer = new Timer();
	private IntegerProperty answers;
	
	private TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			restZeit = restZeit -1;
			if(restZeit == 0) {
				myTimer.cancel();
			}
		}		
	};

	public Fragerunde(Frage frage, List<Spieler> spielerListe, int maxZeit) {
		answers = new SimpleIntegerProperty();
		this.maxZeit = maxZeit;
		restZeit = maxZeit;
		timeStart = System.currentTimeMillis();
		this.frage = frage;
		this.spielerListe = spielerListe;		
		buzzerAbonnieren();
		start();
	}
	
	public IntegerProperty answers(){
		return answers;
	}


	private void buzzerAbonnieren() {
		//auf den Buzzerzustandsänderungen abonnieren (solange wie Fragerunde aktiv ist)
		//sobald zustandständerung erkannt, Buzzer blockieren → für den einten Spieler alle Buzzer
		//
		System.out.println("'Fragerunde: ' Anzahl Buzzer abonniert: " + spielerListe.size());
		spielerListe.forEach(spieler -> {		
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
					answers().set(answers.get()+1);
				}

			});
			System.out.println("'Fragerunde: '" + spieler.getName().toString() + " Listener added");
			spieler.setRundenpunkte(0);
		});
	}


	private void start() {
		myTimer.scheduleAtFixedRate(timerTask, 0, 1000);	    
	}


	public Frage getFrage() {
		return frage;
	}


	public void setFrage(Frage frage) {
		this.frage = frage;
	}

}
