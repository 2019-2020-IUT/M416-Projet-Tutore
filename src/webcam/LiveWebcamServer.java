package webcam;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
//api de java permettant d'afficher la webcam à l'ecran
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LiveWebcamServer {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
	    //création de la socket du serveur
		ServerSocket server = new ServerSocket(123);
		//connection en mode TCP
		Socket socket = server.accept();
		System.out.println("connected");
		//lit les images reçues sur la socket
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		//affiche la caméra à l'ecran
		JLabel label = new JLabel();
		JFrame frame = new JFrame();
		frame.setSize(1280,720);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		label = new JLabel();
		label.setSize(1280,720);
		label.setVisible(true);
		frame.add(label);
		frame.setVisible(true);

		for(;;)
			label.setIcon((ImageIcon)in.readObject());
	}
}
