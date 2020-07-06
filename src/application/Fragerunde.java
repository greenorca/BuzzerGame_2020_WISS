package application;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Fragerunde {

	//wenn fragerunde gestartet, abonniert sich an den BuzzerPressedProperties von den Spielern
	// sobald sich buzzerpressed ändert, bekommt die fragerunde die möglichkeit sich zu ändern bzw den Punktestand anzupassen

	long timeStart;
	private Frage frage;
	//int runde;
	private List<Spieler> spielerListe;
	int antwort = 0;
	private int restZeit = 10;
	private Timer myTimer = new Timer();
	private TimerTask timerTask = new TimerTask() {

		@Override
		public void run() {
			restZeit = restZeit -1;
			if(restZeit == 0) {
				myTimer.cancel();
			}
		}		
	};


	//private List<Frage> ausgeleseneFragen = eaF.leseFragen("src/res/fragen.ser");	

	public Fragerunde(Frage frage, List<Spieler> spielerListe) {
		super();
		timeStart = System.currentTimeMillis();
		
		//this.runde = 0;
		this.frage = frage;
		this.spielerListe = spielerListe;		
		buzzerAbonnieren();
		start();
	}


	private void buzzerAbonnieren() {
		//auf den Buzzerzustandsänderungen abonnieren (solange wie Fragerunde aktiv ist)
		//sobald zustandständerung erkannt, Buzzer blockieren → für den einten Spieler alle Buzzer
		//
		System.out.println("'Fragerunde: ' Anzahl Buzzer abonniert: " + spielerListe.size());
		spielerListe.forEach(spieler -> {		
			//spieler.setAntwortNr(new SimpleIntegerProperty());
			//spieler.aboErneuern();
			spieler.reset();
			System.out.println("'Fragerunde: ' AntwortNr: " + spieler.getAntwortNr().getValue());
			spieler.getAntwortNr().addListener(new ChangeListener<Number>() {




				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number alt, Number neu) {	
					long pressedTime = System.currentTimeMillis();
					long restTime = (pressedTime - timeStart)/100;
					int restTime2 = (int)restTime;

					System.out.println("'Fragerunde: ' SpielerAntwortNr: " + neu);

					if((int)neu == frage.korrekteAntwortInt()) {
						//restZeit = 10 - myTimer.getTimeInSeconds();
						//System.out.println("Observer:Restzeit: " + restZeit);
						//spieler.setPunktestand(spieler.getPunktestand().add((int)neu));
						spieler.addPunkte(restTime2);
						spieler.setRundenpunkte(restTime2);

						System.out.println("'Fragerunde: '" + spieler.getName() + " Restzeit: " + restZeit);
						System.out.println("'Fragerunde: '" + spieler.getName() + " hat " + spieler.getPunktestand().getValue() + " Punkte");
						
					}
					else {
						//spieler.setRundenpunkte(0);
					}

					spieler.getAntwortNr().removeListener(this);

					//spieler.setAntwortNr(new SimpleIntegerProperty());
					//System.out.println("'Fragerunde: '" + spieler.getName().toString() + " BUtton " + spieler.antwortNr.getValue() + " gedrückt");



				}

			});
			System.out.println("'Fragerunde: '" + spieler.getName().toString() + " Listener added");
			//spieler.setAntwortNr(new SimpleIntegerProperty());
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

	//a) Buzzer für Spieler aktivieren
	//b) eigentliche Frage anzeigen
	//c) Timer starten
	//wenn timmer abgelaufen ist, Buzzer werden deaktiviert
	//wenn Timer abgelaufen ist, Antwort anzeigen
}



//behandelt nur genau 1 Frage
// bekommt im Konstruktor genau 1 Frage

//muss in GameController
/*public Frage frageZufaelligAuswaehlen(List<Frage> f) {			
		int zufallszahl = (int) (Math.random() * f.size());
		return f.get(zufallszahl);
	}*/






