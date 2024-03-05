package com.example.zadatak2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Server extends Application {

    private final static int SERVER_PORT = 12345;
    private Label odgovorLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Server za Primanje Datoteka");

        odgovorLabel = new Label("Čekam klijentske zahteve...");
        odgovorLabel.setWrapText(true);
        odgovorLabel.setPrefWidth(400);
        odgovorLabel.setStyle("-fx-text-fill:rgb(255,255,255); ");

        Pane pane = new Pane();
        odgovorLabel.setLayoutX(10);
        odgovorLabel.setLayoutY(10);

        pane.getChildren().add(odgovorLabel);
        pane.setStyle("-fx-background-color: rgb(157, 188, 152);-fx-font-weight: bold;");

        Scene scene = new Scene(pane, 400, 150);
        primaryStage.setScene(scene);
        primaryStage.show();

        startServer();
    }

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClient(Socket clientSocket) {
        try (DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream())) {

            // Čita informacije o datoteci od klijenta
            String datotekaServer = dis.readUTF();

            // Kopira datoteku iz Klijenta foldera u Server folder
            kopirajDatotekuNaServer(datotekaServer);

            // Šalje odgovor klijentu
            dos.writeUTF("Datoteka uspešno preuzeta.");

            // Ažurira UI na JavaFX Application Thread-u
            updateUI("Datoteka uspešno preuzeta.");

        } catch (IOException e) {
            e.printStackTrace();
            updateUI("Greška prilikom obrade klijentskog zahteva.");
        }
    }

    private void kopirajDatotekuNaServer(String datotekaServer) {
        try {
            // Dobija ime datoteke iz putanje
            String imeDatoteke = Path.of(datotekaServer).getFileName().toString();

            // Kopira datoteku iz serverskog foldera u klijentski folder
            Files.copy(Path.of(datotekaServer), Path.of("src/main/resources/com/example/zadatak2/SERVER-FOLDER/" + imeDatoteke), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
            updateUI("Greška prilikom kopiranja datoteke na server.");
        }
    }

    private void updateUI(String message) {
        // Ažurira UI na JavaFX Application Thread-u
        Platform.runLater(() -> {
            odgovorLabel.setText(message);
        });
    }
}
