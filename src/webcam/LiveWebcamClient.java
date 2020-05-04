package webcam;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
//api java qui permettent d'afficher la webcam sur l'ecran
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
//utilise la librairie webcam-capture
import com.github.sarxos.webcam.Webcam;

public class LiveWebcamClient {
	public static void main(String[] args) throws IOException {
	    //prend la webcam du client
		Webcam webcam = Webcam.getDefault();
		webcam.open();
        //se connecte à la socket du serveur créée  au préalable sur localhost
		Socket socket = new Socket("localhost",123);
        //prend toutes les images de la webcam ouverte
		BufferedImage bm = webcam.getImage();
		//envoie les images sur la socket du serveur
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		ImageIcon ii = new ImageIcon(bm);

		JFrame frame = new JFrame();
		frame.setSize(1280,720);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

		JLabel label = new JLabel();
		label.setVisible(true);
		frame.add(label);
		for(;;) {
			bm=webcam.getImage();
			ii= new ImageIcon(bm);
			out.writeObject(ii);
			label.setIcon(ii);
			out.flush();
		}
	}
}
