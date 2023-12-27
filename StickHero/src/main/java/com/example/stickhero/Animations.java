package com.example.stickhero;

import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Animations {
    private static Rectangle createBlackOverlay() { // Helper function
        Rectangle blackOverlay = new Rectangle();
        blackOverlay.setWidth(600);
        blackOverlay.setHeight(400);
        blackOverlay.setFill(Color.BLACK);
        return blackOverlay;
    }

    public static void transitionToScene(Parent newRoot, Stage stage, AnchorPane anchorPane) { // Creates a fadeout - fadein animation for switching scenes
        Rectangle blackOverlay = createBlackOverlay();
        newRoot.setOpacity(0);
        blackOverlay.setOpacity(1);

        AnchorPane container = new AnchorPane();
        container.getChildren().addAll(blackOverlay, newRoot);

        Scene newScene = new Scene(container);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), anchorPane);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            stage.setScene(newScene); // Set the new scene after fading out the current scene
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

}
