package ecran;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.agomezmoron.multimedia.recorder.VideoRecorder;
import com.github.agomezmoron.multimedia.recorder.configuration.VideoRecorderConfiguration;

public class EnregistreurEcran {

	public static void main(String[] args) throws IOException {
		enregistrerEcran();
	}

	public static Thread th;
	public static String videoPath;
	public static long DUREE_ENREGISTREMENT = Integer.MAX_VALUE;
	public static int i=0;
	public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
	public static LocalDateTime now = LocalDateTime.now();
    public static String videoName= "ecran-"+dtf.format(now);

	public static File videoFile = new File("output\\");
	
	public static void enregistrerEcran() {
		try {
            // configuration
            VideoRecorderConfiguration.setCaptureInterval(100);
            // 20 frames/sec
            VideoRecorderConfiguration.wantToUseFullScreen(false);
            VideoRecorderConfiguration.setVideoDirectory(videoFile);
            
            VideoRecorderConfiguration.setKeepFrames(true);
            // you can also change the x,y using
            VideoRecorderConfiguration.setCoordinates(10,20);
            VideoRecorder.start(videoName);
			System.out.println("Enregistrement en cours de:\n\t"+"output\\"+videoName+".mov");
            for(i=0;i<DUREE_ENREGISTREMENT;i++) {
    			Thread.sleep(i);
            }
            videoPath = VideoRecorder.stop();

            // EXECUTE ALL YOU WANT TO BE RECORDED String videoPath = VideoRecorder.stop(); // video created 
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(EnregistreurEcran.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

}
