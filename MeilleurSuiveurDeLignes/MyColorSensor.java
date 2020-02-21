import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.HiTechnicColorSensor;
import lejos.robotics.SampleProvider;

public class MyColorSensor {

	static SampleProvider cp;
	float[]colorFinal;
	float[] colorSample;
	float[] test;
	
	public MyColorSensor() {
		Port s3 = LocalEV3.get().getPort("S3");
		HiTechnicColorSensor cs = new HiTechnicColorSensor(s3);
		cp = cs.getRGBMode();
		colorSample = new float[cp.sampleSize()];
		test = new float[cp.sampleSize()];
	}
	
	
	public void calibrerCouleur() {
		colorFinal = new float[cp.sampleSize()];
		cp.fetchSample(colorFinal, 0);
		System.out.println("Couleur maintenant reconnu");
	}
	
	public boolean reconnaissanceCouleur() {
		cp.fetchSample(test, 0);
		if(test[0]>=(colorFinal[0]-0.50) && test[0]<=(colorFinal[0]+0.50)&&test[1]>=(colorFinal[1]-0.50) && test[1]<=(colorFinal[1]+0.50)&&test[2]>=(colorFinal[2]-0.50) && test[2]<=(colorFinal[2]+0.50))
			return true;
		return false;
	}
}
