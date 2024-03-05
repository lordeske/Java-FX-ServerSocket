package com.example.zadatak2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Klijent extends Application {

    private final static String SERVER_ADDRESS = "localhost";
    private final static int SERVER_PORT = 12345;

    private Button odaberiDatotekuButton;
    private TextField odgovorTextField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Klijent za Slanje Datoteke");

        odaberiDatotekuButton = new Button("Odaberi Datoteku");
        odaberiDatotekuButton.setOnAction(e -> handleOdaberiDatoteku());  /// Pozivamo funkciju

        odgovorTextField = new TextField();
        odgovorTextField.setEditable(false);
        odgovorTextField.setPrefWidth(300);

        Pane pane = new Pane();
        odaberiDatotekuButton.setLayoutX(150);
        odaberiDatotekuButton.setLayoutY(20);
        odgovorTextField.setLayoutX(50);
        odgovorTextField.setLayoutY(70);

        pane.setStyle("-fx-background-color: rgb(157, 188, 152);");

        pane.getChildren().addAll(odaberiDatotekuButton, odgovorTextField);

        Scene scene = new Scene(pane, 400, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleOdaberiDatoteku() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String datotekaKlijent = selectedFile.getAbsolutePath();

            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                 DataInputStream dis = new DataInputStream(socket.getInputStream())) {

                // Šalje informacije o datoteci serveru
                dos.writeUTF(datotekaKlijent);

                // Čeka odgovor od servera
                String odgovor = dis.readUTF();
                odgovorTextField.setText(odgovor);

            } catch (IOException e) {
                e.printStackTrace();
                odgovorTextField.setText("Greška prilikom slanja datoteke na server.");
            }
        }
    }
}
