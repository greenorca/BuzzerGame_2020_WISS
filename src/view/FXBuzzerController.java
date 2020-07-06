package view;


import application.IBuzzer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FXBuzzerController implements IBuzzer{
	
	@FXML
	Label lblPunkte;
	
	private IntegerProperty answer = new SimpleIntegerProperty();

	
	
	
	
	public void b1Pressed() {
		getAnswer().setValue(1);
		System.out.println("Button1 pressed");
	}
	
	public void b2Pressed() {
		getAnswer().setValue(2);
	}

	public void b3Pressed() {
		getAnswer().setValue(3);
}

	@Override
	public IntegerProperty getAnswer() {
		if(answer == null) {
			answer = new SimpleIntegerProperty();
		}
		return answer;
	}
}
