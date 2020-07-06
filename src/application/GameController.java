package application;


import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;



import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.*;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.AnswerViewController;
import view.FXBuzzerController;
import view.EndViewController;
import view.LobbyViewController;
import view.QuestionViewController;
import view.ScoreViewController;
import view.StartupViewController;

public class GameController extends Application {

	Stage myStage = null;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private double screenHeight = screenSize.getHeight();
	private double screenWidth = screenSize.getWidth();

	static GameController gamecontroller;
	private StartupViewController startupController;
	private int rundenCounter;
	private List<Frage> eingeleseneFragen;
	private List<Frage> tenQuestions;
	Spielrunde spielrunde;
	Frage f;
	private Spieler spieler1;
	Spieler spieler2;
	Spieler spieler3;
	List<Spieler> spielerliste = new ArrayList<Spieler>();
	private RaspiBuzzer buzzer1, buzzer2, buzzer3;


	public static void main(String[] args) {

		gamecontroller = new GameController();

		List<Frage> alleFragen = EinAuslesenFragen.einlesenFragen("/home/pi/Desktop/fragenBuzzerGame_20180925.csv");
		gamecontroller.eingeleseneFragen = alleFragen;
		System.out.println("Size eingelesene Fragen: " + gamecontroller.eingeleseneFragen.size());
		

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			myStage = primaryStage;
			myStage.setTitle("Buzzer Game");
			myStage.setFullScreenExitHint("");
			myStage.setFullScreen(true);
			showStartupView();

		} catch (Exception e) {
			e.printStackTrace();
			Platform.exit();
		}

	}

	public void showStartupView() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/StartupView.fxml"));
		
		buzzer1 = new RaspiBuzzer(RaspiPin.GPIO_27, RaspiPin.GPIO_28, RaspiPin.GPIO_29);
		buzzer2 = new RaspiBuzzer(RaspiPin.GPIO_00, RaspiPin.GPIO_02, RaspiPin.GPIO_03);
		buzzer3 = new RaspiBuzzer(RaspiPin.GPIO_23, RaspiPin.GPIO_24, RaspiPin.GPIO_25);
		
		
		try {
			Scene startupScene = new Scene(loader.load(), screenWidth, screenHeight);
			startupScene.getStylesheets().add(getClass().getResource("buzzerStyle.css").toExternalForm());
			startupController = loader.getController();
			startupController.setMainController(this);
			
			myStage.setScene(startupScene);
			myStage.setFullScreen(true);
			myStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			Platform.exit();
		}
	}


	public void showLobbyView() {
		gamecontroller.spielerliste.clear();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/LobbyView.fxml"));
		try {
			Scene lobbyScene = new Scene(loader.load(), screenWidth, screenHeight);
			
			lobbyScene.getStylesheets().add(getClass().getResource("buzzerStyle.css").toExternalForm());
			LobbyViewController lobbyController = loader.getController();
			lobbyController.setMainController(this);
			
			
		buzzer1.getAnswer().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				Spieler s = new Spieler("Spieler 1", buzzer1);
				for(int i = 0; i < spielerliste.size(); i++) {
					if(spielerliste.get(i).getName().equals("Spieler 1")) {
						return;
					}
									
				}
				gamecontroller.spielerliste.add(s);
				buzzer1.getAnswer().removeListener(this);
				System.out.println("Spieler1 erstellt");
				lobbyController.setReady1();
				
				
			}			
		});
		
		buzzer2.getAnswer().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stubbuzzer2.getAnswer().addListener(
					Spieler s = new Spieler("Spieler 2", buzzer2);
					
					for(int i = 0; i < spielerliste.size(); i++) {
					if(spielerliste.get(i).getName().equals("Spieler 2")) {
						return;
					}
									
				}
				gamecontroller.spielerliste.add(s);
					buzzer2.getAnswer().removeListener(this);
					System.out.println("Spieler2 erstellt");
					lobbyController.setReady2();
			}			
		});
		
		buzzer3.getAnswer().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stubbuzzer2.getAnswer().addListener(
					Spieler s = new Spieler("Spieler 3", buzzer3);
					for(int i = 0; i < spielerliste.size(); i++) {
					if(spielerliste.get(i).getName().equals("Spieler 3")) {
						return;
					}
									
				}
				
					gamecontroller.spielerliste.add(s);
					buzzer3.getAnswer().removeListener(this);
					System.out.println("Spieler3 erstellt");
					lobbyController.setReady3();
			}			
		});
			
			Collections.shuffle(gamecontroller.eingeleseneFragen);
			System.out.println("Size eingelesene Fragen: " + gamecontroller.eingeleseneFragen.size());
			
			gamecontroller.tenQuestions = gamecontroller.eingeleseneFragen.subList(0, 10);
			spielrunde = new Spielrunde(gamecontroller.tenQuestions);
			System.out.println("Spielrunde erstellt");
			
			myStage.setScene(lobbyScene);
			myStage.setFullScreen(true);
			myStage.show();	
			
								
		} catch (Exception e) {
			e.printStackTrace();
			Platform.exit();
		}
		
		

		
		
	}

	public void createBuzzerView(String playername, double yPosition, double xPosition) {

		try {
			FXMLLoader root = new FXMLLoader(getClass().getResource("../view/FXBuzzer.fxml"));
			Parent parent = root.load();
			Stage stage = new Stage();
			Scene scene = new Scene(parent);
			stage.setTitle(playername);
			
			FXBuzzerController buzzerSet1Controller = root.getController();
			
			Spieler s = new Spieler(playername, buzzerSet1Controller);

			if(gamecontroller.spielerliste.size() == 0) {
				System.out.println("Erster Spieler hinzugefügt");
				gamecontroller.spielerliste.add(s);
				stage.setScene(scene);
				stage.setY(yPosition);
				stage.setX(xPosition);
				stage.show();				
			}
			else {
				boolean enthalten = false;
				for (int i = 0; i < gamecontroller.spielerliste.size(); i++) {					
					if(gamecontroller.spielerliste.get(i).getName().equals(s.getName())) {
						System.out.println("Enthalten");
						enthalten = true;						
					}					
				}	
				if(enthalten == false) {
					gamecontroller.spielerliste.add(s);
					System.out.println(s.getName() + " hinzugefügt");
					stage.setScene(scene);
					stage.setY(yPosition);
					stage.setX(xPosition);
					stage.show();
				}
			}						

		} catch (IOException e) {
			e.printStackTrace();
			Platform.exit();
		}

	}

	public void showQuestionView(Frage question) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/QuestionView.fxml"));
		try {

			Scene questionScene = new Scene(loader.load(), screenWidth, screenHeight - 200);
			questionScene.getStylesheets().add(getClass().getResource("buzzerStyle.css").toExternalForm());

			QuestionViewController questionController = loader.getController();
			Fragerunde fragerunde = new Fragerunde(question, gamecontroller.spielerliste);
			questionController.setMainController(this);

			questionController.setQuestion(fragerunde.getFrage());

			/*for (int i = 0; i < gamecontroller.spielerliste.size(); i++) {
				gamecontroller.spielerliste.get(i).setAntwortNr(new SimpleIntegerProperty());
				System.out.println("AntwortNr von " + gamecontroller.spielerliste.get(i).getName().toString() + " ist " + gamecontroller.spielerliste.get(i).getAntwortNr().getValue().toString());
			}*/

			List<Antwort> shuffledAntworten = fragerunde.getFrage().getAntworten();
			Collections.shuffle(shuffledAntworten);			
			questionController.setAnswer(shuffledAntworten);			

			questionController.getRestzeit().addListener(showAnswerSceneListener);

			myStage.setScene(questionScene);
			myStage.setFullScreen(true);
			myStage.show();

		} catch(Exception e) {
			e.printStackTrace();
			Platform.exit();
		}
	}

	private void showAnswerScene(Frage question) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AnswerView.fxml"));
		try {			
			Scene answerScene = new Scene(loader.load(), screenWidth, screenHeight);
			answerScene.getStylesheets().add(getClass().getResource("buzzerStyle.css").toExternalForm());

			AnswerViewController answerController = loader.getController();
			answerController.setMainController(this);
			answerController.setFrage(question);
			answerController.setAnswer(question);



			//answerController.setSpielerInformation(gamecontroller.spielerliste);
			answerController.getRestzeit().addListener(showScoreSceneListener);
			myStage.setScene(answerScene);
			myStage.setFullScreen(true);
			myStage.show();

			//(getRundenCounter() + 1);

		} catch(Exception e) {

			e.printStackTrace();
			Platform.exit();
		}
	}

	//XD added showScoreScene()
	public void showScoreScene() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/scoreView2.fxml"));
		try {
			Scene scoreScene = new Scene(loader.load(), screenWidth, screenHeight);
			scoreScene.getStylesheets().add(getClass().getResource("buzzerStyle.css").toExternalForm());
			ScoreViewController scoreController = loader.getController();
			scoreController.setMainController(this);

			Collections.sort(gamecontroller.spielerliste, new Comparator<Spieler>() {

				@Override
				public int compare(Spieler arg0, Spieler arg1) {

					return arg1.getPunktestand().get() - arg0.getPunktestand().get();
				}});
			scoreController.setSpielerInformation(gamecontroller.spielerliste);


			scoreController.getRestzeit().addListener(showNextQuestionListener);

			myStage.setScene(scoreScene);
			myStage.setFullScreen(true);
			myStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			Platform.exit();
		}
	}

	public void showEndScene() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/EndView.fxml"));
		try {
			Scene endScene = new Scene(loader.load(), screenWidth, screenHeight);
			endScene.getStylesheets().add(getClass().getResource("buzzerStyle.css").toExternalForm());

			EndViewController endController = loader.getController();
			endController.setMainController(this);

			Collections.sort(gamecontroller.spielerliste, new Comparator<Spieler>() {

				@Override
				public int compare(Spieler arg0, Spieler arg1) {

					return arg1.getPunktestand().get() - arg0.getPunktestand().get();
				}});
			endController.setSpielerInformation(gamecontroller.spielerliste);

			//XD added Listener
			//endController.getRestzeit().addListener(showLobbySceneListener);

			
			myStage.setScene(endScene);
			myStage.setFullScreen(true);
			myStage.show();
		} catch (Exception e) {
			e.printStackTrace();
			Platform.exit();
		}
	}





	public void answerNotifyDone() {
		if(rundenCounter <=4 ) {
			showScoreScene();
		}
		else {
			rundenCounter = 0;
			showEndScene();
		}

		//		if(rundenCounter == 9) {			
		//		}
		//		rundenCounter++;
		//		f = spielrunde.naechsteFrage();
		//		showQuestionView(f);
		//showBuzzerView("../view/BuzzerSet1.fxml");
	}

	public void lobbyNotifyDone() {
		rundenCounter++;
		f = spielrunde.naechsteFrage();
		showQuestionView(f);

	}

	//von Score zu nächste Frage oder Ende
	public void scoreNotifyDone() {
		//if(rundenCounter <= 9) {
		System.out.println("'Gamecontroller: ' Runden gespielt: " + rundenCounter);
		rundenCounter++;
		f = spielrunde.naechsteFrage();
		showQuestionView(f);
		//} else {
		//showEndScene();
		//}

	}

	public void endNotifyDone() {
		System.out.println("Spielerliste grösse: "+ gamecontroller.spielerliste.size());
		showLobbyView();
		
	}

	private ChangeListener<Number> showAnswerSceneListener = (o, a, newValue) -> {
		if (newValue.intValue() <= 0) {			
			Platform.runLater(() -> showAnswerScene(f));

		}
	};

	private ChangeListener<Number> showNextQuestionListener = (o, a, newValue) -> {
		if (newValue.intValue() <= 0) {
			Platform.runLater(() -> scoreNotifyDone());
		}
	};

	private ChangeListener<Number> showScoreSceneListener = (o, a, newValue) -> {
		if (newValue.intValue() <= 0) {
			Platform.runLater(() -> answerNotifyDone());
		}
	};

	/*private ChangeListener<Number> showLobbySceneListener = (o, a, newValue) -> {
		if (newValue.intValue() <= 0) {
			Platform.runLater(() -> endNotifyDone());
		}
	};
	* */




	public int getRundenCounter() {
		return rundenCounter;
	}

	public void setRundenCounter(int rundenCounter) {
		this.rundenCounter = rundenCounter;
	}

	public Spieler getSpieler1() {
		return spieler1;
	}

	public void setSpieler1(Spieler spieler1) {
		this.spieler1 = spieler1;
	}

	public List<Spieler> getSpielerliste() {
		return spielerliste;
	}

	public static GameController getGamecontroller() {
		return gamecontroller;
	}
	
	

}
