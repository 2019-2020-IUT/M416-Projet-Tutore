package audio;

public class ThreadSon extends Thread {
	public ThreadSon(String name) {
		super(name);
	}

	public void run() {
		EnregistreurAudio.enregistrerSon();
	}
}