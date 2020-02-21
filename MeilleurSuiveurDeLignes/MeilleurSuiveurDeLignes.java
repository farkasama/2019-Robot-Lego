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

public class MeilleurSuiveurDeLignes {

	public static void main(String[] args) throws Exception{

		//MyColorSensor cs = new MyColorSensor();
		StopThread t1 = new StopThread();

		System.out.println("Reconnaissance de la couleur");

		Button.waitForAnyPress();

		//cs.calibrerCouleur();
		ColorSensorThread t2 = new ColorSensorThread(t1);

		System.out.println("Appuiez sur un bouton");
		Button.waitForAnyPress();
		//System.out.println(Button.getButtons());

		//480 et 100
		//500 et 140
		//450 et 200 ET 600 et 267 et 650 et 289
		//600 et 267 -> 25sec
		int Speed1;
		int Speed2;

		System.out.println("Choisissez votre vitesse : ");
		System.out.println("600 et 267 F H");
		System.out.println("480 et 100 F D");
		System.out.println("450 et 200 F G");
		System.out.println("Pour choisir F B");

		if(Button.getButtons() == 1) { //a voir
		//	System.out.println("haut");
			Button.waitForAnyPress();

			Speed1 = 600;
			Speed2 = 267;
		}else if(Button.getButtons() == 8) { //
		//	System.out.println("droite");
			System.out.println(Button.getButtons());
			Button.waitForAnyPress();


			Speed1 = 480;
			Speed2 = 100;
		}else if(Button.getButtons() == 16) {
		//	System.out.println("gauche");
			Button.waitForAnyPress();

			Speed1 = 450;
			Speed2 = 200;
		}else { //touche bas a regler
		//	System.out.println("bas");
			Button.waitForAnyPress();

			System.out.println("Choisissez speed1, speed2 sera +200");
			int cpt = 0;
			int nb = Button.getButtons();
			while(nb != 2) { //a definir touche milieu
				if(nb == 1) //touche haut
					cpt+=50;
				else if(nb == 8) //touche bas
					cpt -=50;
				nb = Button.getButtons();
			}
			Speed1 = cpt;
			Speed2 = cpt+200;
		}

		System.out.println("Appuiez sur un bouton");
		Button.waitForAnyPress();

		Motor.A.setSpeed(Speed1);
		Motor.B.setSpeed(Speed2);

		t1.start();
		t2.start();

		Motor.A.forward();
		Motor.B.forward();

		while(t1.getSortir()) {
			if (/*cs.reconnaissanceCouleur()*/ t2.couleur) {
				Motor.A.setSpeed(Speed2);
				Motor.B.setSpeed(Speed1);
			}
			else {
				Motor.A.setSpeed(Speed1);
				Motor.B.setSpeed(Speed2);
			}
		}

		t1.join();
		t2.join();
	}

}
