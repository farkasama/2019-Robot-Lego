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

public class ColorSensor {
	
	EV3ColorSensor cs;
	static SampleProvider cp;
	float[]colorFinal1;
	float[]colorFinal2;
	float[]colorFinal3;

	float[] colorSample;
	float[] test;

	
	public static void main (String[] args) {
		new ColorSensor();
	}
	
	public ColorSensor() {
		Port s3 = LocalEV3.get().getPort("S3");
		HiTechnicColorSensor cs = new HiTechnicColorSensor(s3);
		cp = cs.getRGBMode();
		colorSample = new float[cp.sampleSize()];
		
		System.out.println("Ready ?");
		Button.waitForAnyPress();
		
	/************************************calibrage des trois couleurs*****************************/
		
		colorFinal1= new float[cp.sampleSize()];
		colorFinal2= new float[cp.sampleSize()];
		colorFinal3= new float[cp.sampleSize()];

		scanCalibragex3(colorFinal1, colorFinal2, colorFinal3);
		
		/******************************************************************************************/
		
		while(true) {
			if(Button.getButtons() == 8) { //Si on actionne le bouton droit, on recupere une nouvelle couleur pour un nouveau test

				test = new float[cp.sampleSize()];
				testCouleur(test);
				System.out.println("scan fini");
				Button.waitForAnyPress();
				reconnaissanceIntervalle(colorFinal1, colorFinal2, colorFinal3, test);
				Button.waitForAnyPress();

			}else if(Button.getButtons() == 1)  //si on actionne le bouton du bas, on essaye de suivre une ligne de la couleur1
				suivreLigne();
			else 
				break;
			
		}

	}
	



	
/*************************************methode qui recupere le RGB d'une couleur*********************************/
	
	private static float[] nouvelleCouleur(float []colorSam) {
		float[] tab = new float[cp.sampleSize()];
		for(int i = 0; i<10; i++) {
			cp.fetchSample(colorSam, 0);
			tab[0]+=colorSam[0];
			tab[1]+=colorSam[1];
			tab[2]+=colorSam[2];
	}
		tab[0] = tab[0]/10;
		tab[1] = tab[1]/10;
		tab[2] = tab[2]/10;
		//cp.fetchSample(tab, 0);
		return tab;
	}
	

	private static void testCouleur(float[]colorSam) {
		cp.fetchSample(colorSam, 0);

	}
	
	/************************methode qui regarde dans quelle intervalle est la nouvelle couleur scannée******************************/
	 
	private static void reconnaissanceIntervalle(float[]colorFinal1, float[]colorFinal2, float[]colorFinal3, float[]colorFinalTest) {
		if(colorFinalTest[0]>=(colorFinal1[0]-0.50) && colorFinalTest[0]<=(colorFinal1[0]+0.50)&&colorFinalTest[1]>=(colorFinal1[1]-0.50) && colorFinalTest[1]<=(colorFinal1[1]+0.50)&&colorFinalTest[2]>=(colorFinal1[2]-0.50) && colorFinalTest[2]<=(colorFinal1[2]+0.50))
			System.out.println("Se rapproche de la couleur 1");
		else if(colorFinalTest[0]>=(colorFinal2[0]-0.50) && colorFinalTest[0]<=(colorFinal2[0]+0.50)&&colorFinalTest[1]>=(colorFinal2[1]-0.50) && colorFinalTest[1]<=(colorFinal2[1]+0.50)&&colorFinalTest[2]>=(colorFinal2[2]-0.50) && colorFinalTest[2]<=(colorFinal2[2]+0.50)) 
			System.out.println("Se rapproche de la couleur 2");
		else if(colorFinalTest[0]>=(colorFinal3[0]-0.50) && colorFinalTest[0]<=(colorFinal3[0]+0.50)&&colorFinalTest[1]>=(colorFinal3[1]-0.50) && colorFinalTest[1]<=(colorFinal3[1]+0.50)&&colorFinalTest[2]>=(colorFinal3[2]-0.50) && colorFinalTest[2]<=(colorFinal3[2]+0.50)) 
			System.out.println("Se rapproche de la couleur 3");
		else 
			System.out.println("Se rapproche d'aucune couleur");
	}
	
	
	
	/****************************methode qui renvoie le numero de la couleur scannée******************************************/
	
	private static int reconnaissanceNumCouleur(float[]colorFinal1, float[]colorFinal2, float[]colorFinal3, float[]colorFinalTest) {
		if(colorFinalTest[0]>=(colorFinal1[0]-0.50) && colorFinalTest[0]<=(colorFinal1[0]+0.50)&&colorFinalTest[1]>=(colorFinal1[1]-0.50) && colorFinalTest[1]<=(colorFinal1[1]+0.50)&&colorFinalTest[2]>=(colorFinal1[2]-0.50) && colorFinalTest[2]<=(colorFinal1[2]+0.50)) 
			return 1;
		else if(colorFinalTest[0]>=(colorFinal2[0]-0.50) && colorFinalTest[0]<=(colorFinal2[0]+0.50)&&colorFinalTest[1]>=(colorFinal2[1]-0.50) && colorFinalTest[1]<=(colorFinal2[1]+0.50)&&colorFinalTest[2]>=(colorFinal2[2]-0.50) && colorFinalTest[2]<=(colorFinal2[2]+0.50)) 
			return 2;
		else if(colorFinalTest[0]>=(colorFinal3[0]-0.50) && colorFinalTest[0]<=(colorFinal3[0]+0.50)&&colorFinalTest[1]>=(colorFinal3[1]-0.50) && colorFinalTest[1]<=(colorFinal3[1]+0.50)&&colorFinalTest[2]>=(colorFinal3[2]-0.50) && colorFinalTest[2]<=(colorFinal3[2]+0.50)) 
			return 3;
		else
			return -1;

	}
	
	
	
	/*********************************methode qui calibre trois couleur*********************************************/
	
	private void scanCalibragex3(float[]colorFinal1, float[]colorFinal2, float[]colorFinal3) {
		
		/*******************************calibrage de la premiere couleur****************************/
		cp.fetchSample(colorFinal1, 0);
		System.out.println("Couleur 1 finie");
	/*	System.out.println("r "+colorFinal1[0]);
		System.out.println("g "+colorFinal1[1]);
		System.out.println("b "+colorFinal1[2]);*/
		Button.waitForAnyPress();
		
		/*******************************calibrage de la deuxieme couleur****************************/

		cp.fetchSample(colorFinal2, 0);
		System.out.println("Couleur 2 finie");
/*		System.out.println("r "+colorFinal2[0]);
		System.out.println("g "+colorFinal2[1]);
		System.out.println("b "+colorFinal2[2]); */
		Button.waitForAnyPress();
		
		/*******************************calibrage de la troisieme couleur****************************/

		cp.fetchSample(colorFinal3, 0);
		System.out.println("Couleur 3 finie");
/*		System.out.println("r "+colorFinal3[0]);
		System.out.println("g "+colorFinal3[1]);
		System.out.println("b "+colorFinal3[2]); */
		Button.waitForAnyPress();

	}

	
	/************************************methode qui permet de suivre une ligne**********************************/
	
	private void suivreLigne() {

		/*********reglage de la vitesse des deux moteurs*********************/
		
		Motor.A.setSpeed(100); 
		Motor.B.setSpeed(100);

		/*************scanne la couleur de la ligne*******************/
		
		test = new float[cp.sampleSize()];
		testCouleur(test);
		
		/**************met en route les deux moteurs********************/
		
		Motor.A.forward();
		Motor.B.forward();
		
		while(true) {
			if(Button.getButtons() == 8) {
				break;
			}else {
				
				/******************tant qu'on est sur la bonne couleur, le robot avance***********************/
				
				while(reconnaissanceNumCouleur(colorFinal1, colorFinal2, colorFinal3, test) == 1) {
					Motor.A.forward();
					Motor.B.forward();
					testCouleur(test);

				}
				int i = 10;
				//	Motor.A.stop();
				//	Motor.B.stop();
				int a = 0;
				/*******************une fois que le robot a scanné une couleur differente, il fait des rotations jusqu'à re scanner la bonne couleur**********/
				
				while(reconnaissanceNumCouleur(colorFinal1, colorFinal2, colorFinal3, test) != 1) {
			
					if(a%2 == 0)
						Motor.A.rotateTo(i);
					else {
						Motor.B.rotateTo(i*2);
						i +=10;
					}
					a++;

					testCouleur(test);

				}
			}
		}	
	}
	
}
