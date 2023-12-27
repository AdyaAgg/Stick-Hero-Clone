package com.example.stickhero;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SceneControllerLoading {

    @FXML
    private Label loadingLabel;

    @FXML
    private Rectangle loadingRectangle;

    @FXML
    private ImageView messageLabel;
    private Stage stage;
    public void initialize(Stage stage) {
        this.stage = stage;
        Platform.runLater(this::startLoadingAnimation);
    }

    public void animateLabelBonus(ImageView label, double durationInSeconds) {
        label.setOpacity(0);
        double originalYPosition = label.getLayoutY();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), label);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(durationInSeconds / 2), label);
        moveUp.setByY(-5);
        TranslateTransition moveDown = new TranslateTransition(Duration.seconds(durationInSeconds / 2), label);
        moveDown.setByY(5);

        SequentialTransition fadeInSequence = new SequentialTransition(fadeIn, moveUp);
        SequentialTransition fadeOutSequence = new SequentialTransition(moveDown);
        SequentialTransition combinedSequence = new SequentialTransition(fadeInSequence, fadeOutSequence);

        combinedSequence.play();
        combinedSequence.setOnFinished(event -> label.setLayoutY(originalYPosition));
    }


    private void startLoadingAnimation() {
        // Create a thread for music playback
        Thread musicThread = new Thread(() -> {
            GameMusicPlayer musicPlayer2 = new GameMusicPlayer("Music.mp3");
            musicPlayer2.play(1);
        });

        // Start the music playback thread
        musicThread.start();

        final double defaultvalue = loadingRectangle.getWidth();

        Duration duration = Duration.millis(10000);
        loadingRectangle.setWidth(0);

        double fullwidth = loadingRectangle.getWidth();
        if (fullwidth <= 0) {
            fullwidth = 465;
        }

        KeyValue startWidth = new KeyValue(loadingRectangle.widthProperty(), 0);
        KeyValue endWidth = new KeyValue(loadingRectangle.widthProperty(), fullwidth);

        KeyFrame keyFrame = new KeyFrame(duration, endWidth);

        Timeline timeline = new Timeline(keyFrame);

        timeline.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            double progress = newValue.toMillis() / duration.toMillis() * 100;
            // Update the loading label from the JavaFX Application Thread
            Platform.runLater(() -> loadingLabel.setText(String.format("%.0f%%", progress)));
        });

        timeline.setOnFinished(event -> {
            // Update the loading label from the JavaFX Application Thread
            Platform.runLater(() -> loadingLabel.setText("100!"));
            animateLabelBonus(messageLabel, 0.5);
            enableSpacebarPress();
        });

        timeline.play();
    }




    private void fadeOutCurrentScene(Runnable onFinished) {
        Scene currentScene = loadingLabel.getScene();
        if (currentScene != null) {
            Rectangle blackOverlay = createBlackOverlay();
            ((AnchorPane) currentScene.getRoot()).getChildren().add(blackOverlay);

            FadeTransition fadeToBlack = new FadeTransition(Duration.seconds(0.5), blackOverlay);
            fadeToBlack.setFromValue(0);
            fadeToBlack.setToValue(1);
            fadeToBlack.setOnFinished(event -> {

                ((AnchorPane) currentScene.getRoot()).getChildren().remove(blackOverlay); // Remove the overlay after the transition
                onFinished.run();
            });
            fadeToBlack.play();
        } else {
            onFinished.run();
        }
    }

    private void enableSpacebarPress() {
        Scene scene = loadingLabel.getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                GameMusicPlayer musicPlayer = new GameMusicPlayer("click.mp3");
                musicPlayer.play(0);

                fadeOutCurrentScene(this::loadPlayingPageWithFadeIn);
            }
        });
    }

    private void loadPlayingPageWithFadeIn() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Opening Screen.fxml"));
            Parent playingRoot = loader.load();

            Rectangle blackOverlay = createBlackOverlay();

            playingRoot.setOpacity(0);
            blackOverlay.setOpacity(1);

            AnchorPane container = new AnchorPane();
            container.getChildren().addAll(blackOverlay, playingRoot);

            Scene playingScene = new Scene(container);

            Stage stage = (Stage) loadingLabel.getScene().getWindow();
            if (stage != null) {
                stage.setScene(playingScene);
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), playingRoot);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Rectangle createBlackOverlay() {
        Rectangle blackOverlay = new Rectangle();
        blackOverlay.setWidth(600);
        blackOverlay.setHeight(400);
        blackOverlay.setFill(Color.BLACK);
        return blackOverlay;
    }

}



