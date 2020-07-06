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

public class ScoreViewController implements Initializable{

	GameController gameController;
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

	private IntegerProperty restzeit;
	private static int TIMEOUT = 5;
	private Timer timer;
	long tStart;

	public IntegerProperty getRestzeit() {
		if (restzeit == null) 
			restzeit = new SimpleIntegerProperty();
		return restzeit;
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

	public void setSpielerInformation(List<Spieler> spielerliste) {

		int S1punkteDavor = (spielerliste.get(0).getPunktestand().getValue()) - (spielerliste.get(0).getRundenpunkte());
		int S2punkteDavor = (spielerliste.get(1).getPunktestand().getValue()) - (spielerliste.get(1).getRundenpunkte());

		lblS1PunkteZuvor.setText(String.valueOf(S1punkteDavor));
		lblS1Name.setText(spielerliste.get(0).getName().toString());
		lblS1PunkteGesamt.setText(spielerliste.get(0).getPunktestand().getValue().toString());
		lblS1PunkteDazu.setText(String.valueOf(spielerliste.get(0).getRundenpunkte()));

		lblS2PunkteZuvor.setText(String.valueOf(S2punkteDavor));
		lblS2Name.setText(spielerliste.get(1).getName().toString());
		lblS2PunkteGesamt.setText(spielerliste.get(1).getPunktestand().getValue().toString());
		lblS2PunkteDazu.setText(String.valueOf(spielerliste.get(1).getRundenpunkte()));
		
		if(spielerliste.size() == 2) {
			hBoxS3.setStyle("-fx-border-color: none");
			lblS3Name.setText("");
			lblS3PunkteDazu.setText("");
			lblS3PunkteGesamt.setText("");
			lblS3PunkteZuvor.setText("");
		}
		
		

		else if(spielerliste.size() == 3) {
			int S3punkteDavor = (spielerliste.get(2).getPunktestand().getValue()) - (spielerliste.get(2).getRundenpunkte());
			System.out.println("Spieler 3 sollte angezeigt werden");
			lblS3PunkteZuvor.setText(String.valueOf(S3punkteDavor));
			lblS3Name.setText(spielerliste.get(2).getName().toString());
			lblS3PunkteGesamt.setText(spielerliste.get(2).getPunktestand().getValue().toString());
			lblS3PunkteDazu.setText(String.valueOf(spielerliste.get(2).getRundenpunkte()));
		}

	}

}
