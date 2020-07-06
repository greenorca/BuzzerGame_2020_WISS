package application;

import java.util.Observable;

import javafx.beans.property.IntegerProperty;

import javafx.beans.property.SimpleIntegerProperty;

//Wenn BuzzerObjekt gedrückt wird, 

public class Buzzer extends Observable {

	private IntegerProperty buzzerNr;


	public Buzzer() {
		super();
		buzzerNr = new SimpleIntegerProperty();
	}

	/*public int uebermittelterWert() {
		return 0;		
	}*/

	public int getBuzzerNr() {
		return buzzerNr.get();

	}

	public void setBuzzer1() {
		this.buzzerNr.setValue(1);
		this.setChanged();
		this.notifyObservers();
		System.out.println("Buzzer 1 gedrückt");

	}

	public void setBuzzer2() {
		this.buzzerNr.setValue(2);
		this.setChanged();
		this.notifyObservers();
		System.out.println("Buzzer 2 gedrückt");
	}

	public void setBuzzer3() {
		this.buzzerNr.setValue(3);
		this.setChanged();
		this.notifyObservers();
		System.out.println("Buzzer 3 gedrückt");
	}










}
