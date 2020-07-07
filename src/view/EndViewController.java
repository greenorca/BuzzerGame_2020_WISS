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
import java.util.stream.Collectors;

import application.GameController;
import application.Spieler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;

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
	
	@FXML AnchorPane rectPlatz1;
	@FXML AnchorPane rectPlatz2;
	@FXML AnchorPane rectPlatz3;
	
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

	
	public void setSpielerInformation(Set<Spieler> spielerSet) {	
		List<Spieler> spielerliste = spielerSet.stream().collect(Collectors.toList());
		Collections.sort(spielerliste, new Comparator<Spieler>() {

			@Override
			public int compare(Spieler arg0, Spieler arg1) {
				return arg0.getPunktestand().get() - arg1.getPunktestand().get();
			}
		});
		
		String platz1Style = spielerliste.get(1).getName().toString().toLowerCase().replace(" ","");
		//lblS1Name.getStyleClass().add(platz1Style);
		rectPlatz1.getStyleClass().add(platz1Style);
		lblS1Name.setText(spielerliste.get(1).getName().toString());
		lblS1PunkteGesamt.setText(spielerliste.get(1).getPunktestand().getValue().toString() + " Punkte");		
		
		String platz2Style = spielerliste.get(0).getName().toString().toLowerCase().replace(" ","");
		//lblS2Name.getStyleClass().add(platz2Style);
		rectPlatz2.getStyleClass().add(platz2Style);
		lblS2Name.setText(spielerliste.get(0).getName().toString());
		lblS2PunkteGesamt.setText(spielerliste.get(0).getPunktestand().getValue().toString() + " Punkte");
		
		if(spielerliste.size() == 2) {
			lblS3Name.setText("");
			lblS3PunkteGesamt.setText("");
			lblS3Name.setStyle("-fx-border-color: none;");
		}
		
		else if(spielerliste.size() == 3) {
			
			System.out.println("Spieler 3 sollte angezeigt werden");
			
			String platz3Style = spielerliste.get(2).getName().toString().toLowerCase().replace(" ","");
			//lblS3Name.getStyleClass().add(platz3Style);
			rectPlatz3.getStyleClass().add(platz3Style);
		
			lblS3Name.setText(spielerliste.get(2).getName().toString());
			lblS3PunkteGesamt.setText(spielerliste.get(2).getPunktestand().getValue().toString() + " Punkte");
			
		}
	
	}


}
