package view;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import application.Frage;
import application.GameController;
import application.Spieler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class AnswerViewController implements Initializable{

	@FXML
	Label lblS1Name;
	@FXML
	Label lblS2Name;
	@FXML
	Label lblS3Name;
	@FXML
	Label lblS1PunkteZuvor;
	@FXML
	Label lblS2PunkteZuvor;
	@FXML
	Label lblS3PunkteZuvor;
	@FXML
	Label lblS1PunkteDazu;
	@FXML
	Label lblS2PunkteDazu;
	@FXML
	Label lblS3PunkteDazu;
	@FXML
	Label lblS1PunkteGesamt;
	@FXML
	Label lblS2PunkteGesamt;
	@FXML
	Label lblS3PunkteGesamt;
	@FXML
	HBox hBoxS3;
	
	@FXML Label lblFrage;
	@FXML Label lblAntwort;
	

	private IntegerProperty restzeit;
	private static int TIMEOUT = 15;
	private Timer timer;
	long tStart;

	public IntegerProperty getRestzeit() {
		if (restzeit == null) 
			restzeit = new SimpleIntegerProperty(TIMEOUT);
		return restzeit;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getRestzeit().setValue(TIMEOUT);
		tStart = new Date().getTime(); //setze Startzeit
		timer = new Timer();
		timer.scheduleAtFixedRate(tTask, 100, 1000);

		lblAntwort.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		    //final KeyCombination keyCombinationSave = new KeyCodeCombination(KeyCode.S,
		    //                                                      KeyCodeCombination.CONTROL_DOWN);
		    
		    
			public void handle(KeyEvent ke) {
		        if (ke.getCode() == KeyCode.SPACE) {
		            System.out.println("Key Pressed: " + ke.getCode());
		            restzeit.setValue(0);
		            ke.consume(); // <-- stops passing the event to next node
		        }
		    }
		});
		
	}

	TimerTask tTask = new TimerTask() {
		@Override
		public void run() {
			getRestzeit().set(getRestzeit().get()-1);
			if (getRestzeit().intValue()==0) {
				timer.cancel();
			}

		}
	};

	public void setInformation(Frage frage, Set<Spieler> spielerSet) {
		
		lblFrage.setText(frage.getFrage());
		lblAntwort.setText(frage.getAntworten().get(frage.korrekteAntwortInt()-1).getAntwort());
		
		List<Spieler> spielerliste = spielerSet.stream().collect(Collectors.toList());
		Collections.sort(spielerliste, new Comparator<Spieler>() {

			@Override
			public int compare(Spieler arg0, Spieler arg1) {
				return arg1.getPunktestand().get() - arg0.getPunktestand().get();
			}
		});
		
		int S1punkteDavor = (spielerliste.get(0).getPunktestand().getValue()) - (spielerliste.get(0).getRundenpunkte());
		int S2punkteDavor = (spielerliste.get(1).getPunktestand().getValue()) - (spielerliste.get(1).getRundenpunkte());

		//lblS1PunkteZuvor.setText(String.valueOf(S1punkteDavor));
		lblS1Name.setText(spielerliste.get(0).getName().toString());
		lblS1PunkteGesamt.setText(spielerliste.get(0).getPunktestand().getValue().toString());
		lblS1PunkteDazu.setText(String.valueOf(spielerliste.get(0).getRundenpunkte()));

		//lblS2PunkteZuvor.setText(String.valueOf(S2punkteDavor));
		lblS2Name.setText(spielerliste.get(1).getName().toString());
		lblS2PunkteGesamt.setText(spielerliste.get(1).getPunktestand().getValue().toString());
		lblS2PunkteDazu.setText(String.valueOf(spielerliste.get(1).getRundenpunkte()));
		
		if(spielerliste.size() == 2) {
			lblS3Name.setText("");
			lblS3PunkteDazu.setText("");
			lblS3PunkteGesamt.setText("");
			//lblS3PunkteZuvor.setText("");
		} 
		else if(spielerliste.size() == 3) {
			int S3punkteDavor = (spielerliste.get(2).getPunktestand().getValue()) - (spielerliste.get(2).getRundenpunkte());
			System.out.println("Spieler 3 sollte angezeigt werden");
			//lblS3PunkteZuvor.setText(String.valueOf(S3punkteDavor));
			lblS3Name.setText(spielerliste.get(2).getName().toString());
			lblS3PunkteGesamt.setText(spielerliste.get(2).getPunktestand().getValue().toString());
			lblS3PunkteDazu.setText(String.valueOf(spielerliste.get(2).getRundenpunkte()));
		}

	}

}
