import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.sensor.*;

public class ColorSensor {
	
	EV3ColorSensor cs;
	SampleProvider cp;
	float[] colorSample;
	
	public static void main (String[] args) {
		new ColorSensor();
	}
	
	public ColorSensor() {
		Port s3 = LocalEV3.get().getPort("S3");
		HiTechnicColorSensor cs = new HiTechnicColorSensor(s3);
		cp = cs.getRGBMode();
		colorSample = new float[cp.sampleSize()];
		float[] colorFinal = new float[cp.sampleSize()];
		System.out.println("Ready ?");
		Button.waitForAnyPress();
		for (int i = 0; i < 500; i++) {
			cp.fetchSample(colorSample, 0);
			if (i == 0) {
				colorFinal[0] = colorSample[0];
				colorFinal[1] = colorSample[1];
				colorFinal[2] = colorSample[2];
			}
			else {
				colorFinal[0] = (colorFinal[0]+colorSample[0])/2;
				colorFinal[1] = (colorFinal[1]+colorSample[1])/2;
				colorFinal[2] = (colorFinal[2]+colorSample[2])/2;
			}
		}
		System.out.println("Rouge : " +colorFinal[0]);
		System.out.println("Vert : " +colorFinal[1]);
		System.out.println("Bleu :" +colorFinal[2]);
		Button.waitForAnyPress();
	}
}
