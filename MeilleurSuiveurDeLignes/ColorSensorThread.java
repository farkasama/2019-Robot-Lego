
public class ColorSensorThread extends Thread {
	
	private StopThread t;
	private MyColorSensor cs;
	boolean couleur; 
	
	public ColorSensorThread(StopThread st) {
		t = st;
		cs = new MyColorSensor();
		cs.calibrerCouleur();
		couleur = true;
	}
	
	public void run() {
		while(t.getSortir()) {
			if (cs.reconnaissanceCouleur()) {
				couleur = true;
			}
			else {
				couleur = false;
			}
		}
	}

}
