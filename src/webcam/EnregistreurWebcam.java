package webcam;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.*;
import com.xuggle.xuggler.video.*;

 public class EnregistreurWebcam {
	// date du jour (=nom du fichier)
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
	static LocalDateTime now = LocalDateTime.now();
	public static File saveFile = new File("output\\webcam-" + dtf.format(now) + ".mp4");
	public static int iterator;
	public static Webcam webcam;
	
	public static void main(String[] args) throws InterruptedException {
		enregistrerWebcam();
	}
	
	//encode la video
	public static void enregistrerWebcam() throws InterruptedException {
		// Initialise l'écriture du media
		IMediaWriter writer = ToolFactory.makeWriter(saveFile.getAbsolutePath());
		// ajuste la taille de l'enregistrement 
		Dimension size = WebcamResolution.VGA.getSize();

		writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG4, size.width, size.height);

		long start = System.currentTimeMillis();
		System.out.println("Enregistrement en cours de:\n"+"\t"+saveFile);
		Webcam webcam = openWebcam(size);
			
		for (iterator = 0; iterator < Integer.MAX_VALUE; iterator++) {
			//si je clique sur stop, i= Integer.Max_Value-1
			BufferedImage image = ConverterFactory.convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
			IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

			IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
			frame.setKeyFrame(iterator == 0);
			frame.setQuality(100);
			writer.encodeVideo(0, frame);  
			Thread.sleep(20);
		}
		
		writer.close();
	}

	private static Webcam openWebcam(Dimension size) {
		webcam = Webcam.getDefault();
		webcam.setViewSize(size);
		webcam.open();
		return webcam;
	}
}