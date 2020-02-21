class MyThread extends Thread {
	
	MyColorSensor cs;
	boolean avancer;
	MyNewThread t;
	
	public MyThread (MyColorSensor colors, MyNewThread th) {
		cs = colors;
		avancer = true;
		t = th;
	}
	
	public boolean getAvancer() {
		return avancer;
	}
	
	public void run () {
		while(t.getSortir()) {
			if (cs.reconnaissanceCouleur()) {
				avancer = true;
			}
			else {
				avancer = false;
			}
		}
	}
}