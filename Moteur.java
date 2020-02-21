import lejos.hardware.Button;
import lejos.hardware.motor.Motor;

public class Moteur {
	public static void main (String[] args) throws Exception {
		Motor.A.setSpeed(100);
		Motor.A.forward();
		Motor.B.setSpeed(1000);
		Motor.B.forward();
		Button.waitForAnyPress();
	}
}
