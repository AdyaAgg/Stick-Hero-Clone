package com.example.stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Cherry extends Fruit {
    public static final String CHERRY_IMAGE_PATH = "coin.gif"; // Replace with actual image path
    public static final int CHERRY_SIZE = 20;
    public static final int Y_OFFSET_FROM_BOTTOM = 110;
    private boolean isReaped = false;
    private final AnchorPane anchorPane;

    // Constructor
    public Cherry(AnchorPane anchorPane) {
        super(new Image(Cherry.class.getResourceAsStream(CHERRY_IMAGE_PATH)));
        this.setFitWidth(CHERRY_SIZE);
        this.setFitHeight(CHERRY_SIZE);
        this.anchorPane = anchorPane;
    }

    // Helper functions
    public boolean isReaped() {
        return isReaped;
    }
    public void setReaped(boolean reaped) {
        isReaped = reaped;
    }


    // Main functions
    public static Cherry spawnCherry(double minX, Rectangle platform, AnchorPane anchorPane) { // Spawns a new cherry
        Random random = new Random();
        Cherry cherry = null;
        double maxX = platform.getX() - 20;
        minX = minX + 1000;

        if (maxX - minX > 30) {
            if (true) {
                cherry = new Cherry(anchorPane);
                double xPos = minX + (maxX - minX) * random.nextDouble();
                cherry.setLayoutX(xPos);
                double yPos = anchorPane.getHeight() - Y_OFFSET_FROM_BOTTOM - CHERRY_SIZE;
                cherry.setLayoutY(yPos);
            }
        }
        return cherry;
    }

    public void moveOut() { // Animates movement of the cherry outwards
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), this);
        transition.setByX(-1000);
        transition.play();
    }

}