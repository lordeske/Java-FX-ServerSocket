package com.example.demo;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {



    TextArea tx;


    @Override
    public void start(Stage stage) throws Exception, IOException {

        stage.setTitle("Server");



        AnchorPane panel=new AnchorPane();
        Scene scena=new Scene(panel,500,500);
        scena.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        Label labela=new Label("Server");
        labela.setFont(new Font("Times New Roman",40.0));
        AnchorPane.setTopAnchor(labela,100.0);
        AnchorPane.setLeftAnchor(labela,200.0);
        panel.getChildren().add(labela);

        tx=new TextArea();
        AnchorPane.setTopAnchor(tx,200.0);
        AnchorPane.setLeftAnchor(tx,10.0);
        panel.getChildren().add(tx);
        panel.setId("Pozadina");

        stage.setScene(scena);
        stage.show();

        new Thread(() -> {
            try {
                ServerSocket ss = new ServerSocket(9000);

                while (true) {
                    Socket s = ss.accept();
                    Komunijacija k = new Komunijacija(s, this, tx);  // THREAD

                }
            } catch (IOException e) {
                // Obrada izuzetka
                e.printStackTrace();
            }
        }).start();
    }




    public static void main(String[] args) {
        launch(args);
    }




}



