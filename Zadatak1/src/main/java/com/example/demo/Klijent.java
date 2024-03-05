package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class Klijent extends Application {

    Button dugme;
    TextField ta;
    InetAddress addr;
    TextArea tx;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Klijent");
        addr = InetAddress.getByName("127.0.0.1");


        AnchorPane panel = new AnchorPane();
        Scene scena = new Scene(panel, 500, 500);
        scena.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        panel.setId("Pozadina");


        dugme = new Button("Klikni me");
        AnchorPane.setTopAnchor(dugme, 68.0);
        AnchorPane.setRightAnchor(dugme, 30.0);
        panel.getChildren().add(dugme);

        ta = new TextField();
        ta.setPrefWidth(350);
        AnchorPane.setTopAnchor(ta, 70.0);
        AnchorPane.setLeftAnchor(ta, 20.0);
        panel.getChildren().add(ta);

        tx = new TextArea();
        AnchorPane.setTopAnchor(tx, 200.0);
        AnchorPane.setLeftAnchor(tx, 10.0);
        panel.getChildren().add(tx);

        stage.setScene(scena);
        stage.show();

        dugme.setOnAction(event -> {
            try (Socket s = new Socket(addr, 9000);
                 BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                 PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true)) {

                // Kod koji se izvršava kada se dugme pritisne
                String tekst = ta.getText(); // Preuzimamo tekst iz TextField-a
                out.println(tekst); // Ispisujemo klijentu

                String dobitak = in.readLine();
                tx.appendText(dobitak + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
