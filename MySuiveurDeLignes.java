import java.awt.RenderingHints.Key;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.*;
import lejos.hardware.motor.Motor;

public class MySuiveurDeLignes {

	public static void main(String[] args) {
		
		MyColorSensor cs = new MyColorSensor();
		
		System.out.println("Reconnaissance de la couleur");
		
		Button.waitForAnyPress();
		
		cs.calibrerCouleur();
		
		System.out.println("Appuiez sur un bouton");
		Button.waitForAnyPress();
		
		Motor.A.setSpeed(300); 
		Motor.B.setSpeed(300);
		
		while(true) {
			Motor.A.forward();
			Motor.B.forward();
			
			while (cs.reconnaissanceCouleur());
			
			Motor.A.stop();
			Motor.B.stop();
			
			boolean rot = true;
			
			while(!cs.reconnaissanceCouleur()) {
				if (rot) {
					for (int i = 1; i <= 5; i++) {
						if (cs.reconnaissanceCouleur()) {
							rot = false;
							break;
						}
						else {
							Motor.A.rotate(i*10);
						}
					}
					rot = false;
				}
				else {
					for (int i = 1; i <= 10; i++) {
						if (cs.reconnaissanceCouleur()) {
							rot = true;
							break;
						}
						else {
							Motor.B.rotate(i*10);
						}
					}
					rot = true;
				}
			}
		}
	}

}
