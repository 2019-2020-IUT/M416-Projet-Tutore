package webcam;

public class ThreadCamera extends Thread {
	public ThreadCamera(String name) {
		super(name);
	}

	public void run() {
		try {
			EnregistreurWebcam.enregistrerWebcam();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}