
package application;

import com.pi4j.io.gpio.Pin;
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
import application.IBuzzer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RaspiBuzzer implements IBuzzer {
	
	private IntegerProperty answer = new SimpleIntegerProperty();
	
	
	GpioController gpio;
        GpioPinDigitalInput btnA, btnB, btnC;
        public BooleanProperty btnAState;
        public BooleanProperty btnBState;
        public BooleanProperty btnCState;
        
        public RaspiBuzzer(Pin p1, Pin p2, Pin p3){
	
	    btnAState = new SimpleBooleanProperty();
            btnBState = new SimpleBooleanProperty();
            btnCState = new SimpleBooleanProperty();
            gpio = GpioFactory.getInstance();
            
            btnA = gpio.provisionDigitalInputPin(p1, PinPullResistance.PULL_DOWN);
            btnB = gpio.provisionDigitalInputPin(p2, PinPullResistance.PULL_DOWN);
            btnC = gpio.provisionDigitalInputPin(p3, PinPullResistance.PULL_DOWN);
            
	
	
	
	btnA.addListener(new GpioPinListenerDigital() {
                @Override   
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    // when button is pressed, speed up the blink rate on LED #2
                    System.out.println("GPIO-PIN: "+event.getPin() + ": " + event.getState());
                    btnAState.set(event.getState().isHigh());
                    if(btnA.getState().isHigh()) {
                        getAnswer().setValue(1);
                    }
                    else {
                        getAnswer().setValue(0);
                        
                   
                    }
                }
            });
            
            
	btnB.addListener(new GpioPinListenerDigital() {
                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    // when button is pressed, speed up the blink rate on LED #2
                    System.out.println("GPIO-PIN: "+event.getPin() + ": " + event.getState());
                    btnBState.set(event.getState().isHigh());
                    if(btnB.getState().isHigh()) {
                        getAnswer().setValue(2);
                    }
                    else {
                        getAnswer().setValue(0);
                        
                   
                    }
                }
            });
            
            
	btnC.addListener(new GpioPinListenerDigital() {
                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    // when button is pressed, speed up the blink rate on LED #2
                    System.out.println("GPIO-PIN: "+event.getPin() + ": " + event.getState());
                    btnCState.set(event.getState().isHigh());
                    if(btnC.getState().isHigh()) {
                        getAnswer().setValue(3);
                    }
                    else {
                        getAnswer().setValue(0);
                        
                   
                    }
                }
            });
	
	}

	@Override
	public IntegerProperty getAnswer() {
		if(answer == null) {
			answer = new SimpleIntegerProperty();
		}
		return answer;
	
	}

}


	
	
	
