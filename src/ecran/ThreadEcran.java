package ecran;

public class ThreadEcran extends Thread {
	public ThreadEcran(String name) {
		super(name);
	}

	public void run() {
		EnregistreurEcran.enregistrerEcran();
	}
}