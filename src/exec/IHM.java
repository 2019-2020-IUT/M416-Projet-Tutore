package exec;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//import de mes classes
import webcam.EnregistreurWebcam;
import webcam.ThreadCamera;

import com.github.agomezmoron.multimedia.recorder.configuration.VideoRecorderConfiguration;

import audio.EnregistreurAudio;
import audio.ThreadSon;
import ecran.EnregistreurEcran;
import ecran.ThreadEcran;

public class IHM extends Application {

	ToggleGroup checkButton = new ToggleGroup();
	Boolean boolEcran=false, boolMicro=false, boolCam=false;

	public void start(Stage stage) {
		stage.setTitle("Effectuer un enregistrement");

		final BorderPane root = new BorderPane();
		final VBox verticalBox = new VBox();

		final Text textTitre = new Text();
		final Text textMicro = new Text();
		final Text textEcran = new Text();
		final Text textCam = new Text();

		textTitre.setFont(new Font(20));
		textTitre.setTextAlignment(TextAlignment.CENTER);
		textTitre.setText("Choisissez les enregistrements à réaliser:\n\n");
		textMicro.setTextAlignment(TextAlignment.CENTER);
		textEcran.setTextAlignment(TextAlignment.CENTER);
		textCam.setTextAlignment(TextAlignment.CENTER);
		textMicro.setVisible(false);
		textCam.setVisible(false);
		textEcran.setVisible(false);

		final RadioButton rbEcran = new RadioButton("Enregistrer l'écran");
		final RadioButton rbMicro = new RadioButton("Enregistrer l'audio du micro");
		final RadioButton rbCam = new RadioButton("Enregistrer la webcam");

		rbEcran.setToggleGroup(checkButton);

		final Button btnGo = new Button();
		btnGo.setText("C'est parti!");

		final Button btnFin = new Button();
		btnFin.setText("STOP");
		btnFin.setVisible(false);
		
		final Button btnEnvoi = new Button();
		
		btnGo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				textTitre.setText("Enregistrement en cours\n\n");
				if (rbEcran.isSelected()) {
					ThreadEcran te = new ThreadEcran("ecran");
					te.start();
					textEcran.setVisible(true);
					textEcran.setText("Enregistrement de l'écran en cours...");
					boolEcran = true;
				} if (rbMicro.isSelected()) {
					ThreadSon ts = new ThreadSon("son");
					ts.start();
					textMicro.setVisible(true);
					textMicro.setText("Enregistrement du micro en cours...");
					boolMicro = true;

				} if (rbCam.isSelected()) {
					ThreadCamera tc = new ThreadCamera("camera");
					tc.start();
					textCam.setVisible(true);
					textCam.setText("Enregistrement de la caméra en cours...");
					boolCam = true;
				} 
				if(!boolCam&&!boolEcran&&!boolMicro) {
				}else {
					verticalBox.getChildren().remove(rbEcran);
					verticalBox.getChildren().remove(rbMicro);
					verticalBox.getChildren().remove(rbCam);
					verticalBox.getChildren().remove(btnGo);
					btnFin.setVisible(true);
				}
			}
		});
		btnFin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				textTitre.setText("Enregistrement terminé\n");
				btnFin.setVisible(false);
				if (boolCam) {
					textCam.setText("Caméra enregistrée dans: " + EnregistreurWebcam.saveFile);
					EnregistreurWebcam.iterator = Integer.MAX_VALUE - 1;
					System.out.println("\nL'enregistrement de la webcam se trouve dans: " + EnregistreurWebcam.saveFile);
				}
				if (boolEcran) {
					EnregistreurEcran.i=Integer.MAX_VALUE-1;
					textEcran.setText("Ecran enregistré dans: output\\"+EnregistreurEcran.videoName+".mov");
					System.out.println("\nL'enregistrement de l'ecran se trouve dans: output\\" +EnregistreurEcran.videoName+".mov");
				}
				if (boolMicro) {
					EnregistreurEcran.i=Integer.MAX_VALUE-1;
					System.out.println("\nL'enregistrement du micro se trouve dans: "+EnregistreurAudio.audioFileName);
					textMicro.setText("Micro enregistré dans: " + EnregistreurAudio.audioFileName);
				}
				verticalBox.getChildren().add(btnEnvoi);
				btnEnvoi.setText("envoyer les enregistrements à OpenShot");
			}
		});
		btnEnvoi.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//TODO coder l'envoi à Open Shot
				Platform.exit();
				System.exit(0);
			}
		});

		verticalBox.getChildren().addAll(textTitre, rbEcran, rbMicro, rbCam, btnGo, textEcran, textMicro, textCam, btnFin);
		verticalBox.setAlignment(Pos.CENTER);
		verticalBox.setSpacing(10);

		root.setCenter(verticalBox);
		Scene scene = new Scene(root, 900, 400); // x + y
		stage.setScene(scene);
		stage.show();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) throws InterruptedException {
		Application.launch(IHM.class, args);
	}
}
