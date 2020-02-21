import lejos.hardware.Button;

class MyNewThread extends Thread {
	
	boolean sortir;
	
	public MyNewThread () {
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
