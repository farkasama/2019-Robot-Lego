import lejos.hardware.Button;

public class HelloWorld {
	public static void main (String[] args) {
		System.out.print("Hello world !");
		Button.waitForAnyPress();
	}
}
