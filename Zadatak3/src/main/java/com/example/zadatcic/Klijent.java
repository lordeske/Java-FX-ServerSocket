package com.example.zadatcic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Klijent extends Application {

    private static final int GRID_SIZE = 3;

    private List<Button> buttons;
    private List<Integer> sequence;
    private int sequenceIndex = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sequence 3x3");

        buttons = new ArrayList<>();
        sequence = new ArrayList<>();

        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            Button button = new Button();
            button.setMinSize(50, 50);
            button.setStyle("-fx-base: #00FF00;"); // Zeleno dugme
            button.setOnAction(e -> handleButtonClick(button));
            buttons.add(button);
        }

        Button startButton = new Button("START");
        startButton.setOnAction(e -> startNewGame());

        // Dodao GridPane i postavio raspored dugmadi
        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setAlignment(javafx.geometry.Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            grid.add(buttons.get(i), i % GRID_SIZE, i / GRID_SIZE);
        }

        grid.add(startButton, GRID_SIZE / 2, GRID_SIZE);

        Scene scene = new Scene(grid, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startNewGame() {
        sequence.clear();
        sequenceIndex = 0;

        // Generišemo novu sekvencu boja
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            sequence.add(i);
        }
        Collections.shuffle(sequence);

        // Pokrećemo animaciju bojenja dugmadi
        Timeline timeline = new Timeline();
        for (int i = 0; i < GRID_SIZE * GRID_SIZE; i++) {
            int buttonIndex = sequence.get(i);
            Button button = buttons.get(buttonIndex);

            KeyFrame keyFrame = new KeyFrame(Duration.seconds(i * 0.25), event -> {
                button.setStyle("-fx-base: #FFFF00;"); // Žuto dugme
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        // Postavljanje poslednjeg KeyFrame-a kako bi se dugmad vratila u zeleno
        KeyFrame lastKeyFrame = new KeyFrame(Duration.seconds(GRID_SIZE * GRID_SIZE * 0.25), event -> {
            for (Button button : buttons) {
                button.setStyle("-fx-base: #00FF00;");
            }
        });
        timeline.getKeyFrames().add(lastKeyFrame);



        timeline.play();
    }

    private void handleButtonClick(Button button) {
        int buttonIndex = buttons.indexOf(button);

        if (buttonIndex == sequence.get(sequenceIndex)) {
            // Bojenje dugmeta u žuto kada je tačno kliknuto
            button.setStyle("-fx-base: #FFFF00;");

            sequenceIndex++;

            if (sequenceIndex == GRID_SIZE * GRID_SIZE) {
                prikaziPorukuOGresci("Čestitamo! Osvojili ste!");
                startNewGame();
            }
        } else {
            prikaziPorukuOGresci("Pogrešan redosled! Igra se resetuje.");
            startNewGame();
        }
    }

    private void prikaziPorukuOGresci(String poruka) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Greška");
        alert.setHeaderText(null);
        alert.setContentText(poruka);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
