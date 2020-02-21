import lejos.hardware.Button;

class StopThread extends Thread {
	
	boolean sortir;
	
	public StopThread () {
		sortir = true;
	}
	
	public boolean getSortir() {
		return sortir;
	}
	
	public void run () {
		Button.waitForAnyPress();
		sortir = false;
	}
}