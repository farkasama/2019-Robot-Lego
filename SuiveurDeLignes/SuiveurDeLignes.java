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

public class SuiveurDeLignes {

	public static void main(String[] args) throws Exception{
		
		MyColorSensor cs = new MyColorSensor();
		
		System.out.println("Reconnaissance de la couleur");
		
		Button.waitForAnyPress();
		
		cs.calibrerCouleur();
		
		System.out.println("Appuiez sur un bouton");
		Button.waitForAnyPress();
		
		Motor.A.setSpeed(300); 
		Motor.B.setSpeed(300);
		
		MyNewThread t1 = new MyNewThread();
		MyThread t = new MyThread(cs, t1);
		t1.start();
		t.start();
		
		while(t1.getSortir()) {
			if (t.getAvancer()) {
				Motor.A.setSpeed(300); 
				Motor.B.setSpeed(300);
				Motor.A.forward();
				Motor.B.forward();
			}
			else {
				Motor.A.setSpeed(360); 
				Motor.B.setSpeed(100);
				long time = System.currentTimeMillis();
				while (System.currentTimeMillis() - time < 400 && !cs.reconnaissanceCouleur()) {
					Motor.A.forward();
					Motor.B.forward();
				}
				Motor.A.setSpeed(100); 
				Motor.B.setSpeed(360);
				time = System.currentTimeMillis();
				while (System.currentTimeMillis() - time < 400 && !cs.reconnaissanceCouleur()) {
					Motor.A.forward();
					Motor.B.forward();
				}
			}
		}
		
		t1.join();
		t.join();
	}

}