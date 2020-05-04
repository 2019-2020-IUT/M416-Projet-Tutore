package audio;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.sound.sampled.*;

public class EnregistreurAudio {
	// date du jour (=nom du fichier)
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
	public static LocalDateTime now = LocalDateTime.now();
	public static String audioFileName="output\\" +"audio-"+ dtf.format(now) + ".wav";
	public static File audioFile = new File(audioFileName);
	public static long DUREE_ENREGISTREMENT = Integer.MAX_VALUE;
	public static Thread t;
	public static TargetDataLine targetLine;
	public static void main(String[] args) throws InterruptedException {
		enregistrerSon();
	}
	
	public static void enregistrerSon() {
		try {
			//permet l'encodage du son
			AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			// en cas d'erreur (pas de micro sur l'odinateur)
			if (!AudioSystem.isLineSupported(info)) {
				System.err.println("Ligne non supportée");
				System.err.println("Impossible d'enregistrer");
				System.exit(0);
			}
			targetLine = (TargetDataLine) AudioSystem.getLine(info);
			targetLine.open();
			System.out.println("Enregistrement en cours de:\n"+"\t"+audioFile);
			targetLine.start();
			t = new Thread() {
				@Override
				public void run() {
					AudioInputStream audioStream = new AudioInputStream(targetLine);
					try {
						AudioSystem.write(audioStream, AudioFileFormat.Type.WAVE, audioFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
			Thread.sleep(DUREE_ENREGISTREMENT);
			//Stop l'enregistrement (ferme la ligne)
			targetLine.stop();
			targetLine.close();

		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
}
