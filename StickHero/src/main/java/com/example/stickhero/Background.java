package com.example.stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Background {
    private ImageView mybackground;

    public Background(ImageView imageView) {
        double desiredHeight = 450;
        imageView.setFitHeight(desiredHeight);
        imageView.setPreserveRatio(true);
        this.mybackground = imageView;
    }

    public void moveForward(double shiftAmount) { // Move position as the hero is walking

        double speed = 1;
        double newX = mybackground.getTranslateX() - shiftAmount;
        if (newX <= -mybackground.getImage().getWidth()) {
            newX = 0;
        }
        TranslateTransition transition = new TranslateTransition(Duration.seconds(speed), mybackground);
        transition.setToX(newX);
        transition.play();
    }
}
