package com.example.stickhero;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class Stick extends GameObjects {
    final public double MAX_HEIGHT = 250.0; // Maximum height
    final public double MIN_HEIGHT = 0.0; // Minimum height
    final private float WIDTH = 10.0F;
    @FXML
    private Label myBonusLabel;
    private final Hero hero;
    private float rotation = 0;
    private final AnimationTimer timer;
    private final double delta = 2; // Increment in height
    private final Rectangle rectangle;
    private float height = 0;
    private boolean growing = true; // Flag to track if the stick is growing or shrinking
    private final Platform platform;
    private final Player player = Player.getInstance(null, null, null, null, null, null);
    private final Background background;
    private final Platform currentPlatform;
    private final AnchorPane anchorPane;
    private final Button growButton;
    public void setHeight(float height) {
        this.height = height;
    }
    public float getWidth() {
        return (float) rectangle.getWidth();
    }

    public Stick(Rectangle rectangle, Hero hero, Platform platform, Background background, Platform currentPlatform, AnchorPane anchorPane, Button growButton) {
        this.rectangle = rectangle;
        this.hero = hero;
        this.platform = platform;
        this.currentPlatform = currentPlatform;
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                extendStick();
            }
        };
        this.setCoordinateY(this.rectangle.getLayoutY());
        this.setCoordinateX(this.rectangle.getLayoutX());
        this.background = background;
        this.anchorPane = anchorPane;
        this.growButton = growButton;
        growButton.setOnMousePressed(e -> this.startExtending());
        growButton.setOnMouseReleased(e -> this.stopExtending());
    }

    public void extendStick() { // REPLACE WITH GETTER SETTER

        int i = 0;
        if (growing) {
            if (height < MAX_HEIGHT) {
                height += delta;
            } else {
                growing = false; // Start shrinking
                if (i == 0) {
                    height += delta;
                    i = -1;
                }
            }
            rectangle.setY(rectangle.getY() - delta); // Move up as the rectangle grows
        } else {
            if (height > MIN_HEIGHT) {
                height -= delta;
            } else {
                growing = true; // Start growing
                height -= delta;

            }
            rectangle.setY(rectangle.getY() + delta); // Move down as the rectangle shrinks
        }

        // Now set the height
        rectangle.setHeight(height);
        this.setHeight(height);
    }

    public void startExtending() {
        timer.start();
    }

    public void stopExtending() {
        timer.stop();
        rotateStick(hero);
        growButton.setDisable(true);
        // Call rotateStick when the button is released
    }

    public void rotateStick(Hero hero) {
        double bottomRightX = rectangle.getX() + this.getWidth();
        double bottomRightY = rectangle.getY() + rectangle.getHeight();

        Rotate rotate = new Rotate(0, bottomRightX, bottomRightY); // Initial angle is 0 degrees
        rectangle.getTransforms().add(rotate);

        Duration duration = Duration.seconds(0.5); // Total animation duration (2 seconds in this example)
        int totalFrames = 90; // Total number of frames for smooth animation

        // Create a Timeline for animation
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1); // Play the animation once

        // Add key frames to gradually increase the angle over the animation duration
        for (int i = 0; i <= totalFrames; i++) {
            Duration frameTime = Duration.millis(duration.toMillis() * i / totalFrames);
            double frameAngle = 90 * i / totalFrames; // Rotate by 90 degrees over the animation
            timeline.getKeyFrames().add(new KeyFrame(frameTime, event -> rotate.setAngle(frameAngle)));
        }


        // Add an event handler for when the animation completes
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Call hero.moveForward after the animation is complete
                if (checkPlatformFall()) {
                    try {
                        checkFallOnPlatform();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    // replace 42 in the next line with position of hero??
                    double distance = platform.getCoordinateX() - 42 + platform.getWidth() - hero.getImageView().getFitWidth() * 0.5;
                    hero.moveForward(distance, background, hero.getFlipButton(), growButton);
                } else {
                    hero.moveForward(rectangle.getHeight(), Stick.this, background);
                }

            }
        });

        // Play the animation
        timeline.play();
    }

    public Rectangle spawnStick() {
        Random random = new Random();

        // Create a new rectangle with the specified criteria
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(0);
        rectangle.setWidth(3); // Width between 20 and 170
        rectangle.setFill(Color.web("#131313")); // Hex color code #131313

        rectangle.setX(107);
        rectangle.setY(267); // layout y set of the original game rectangle.

        // Add the rectangle to the provided Pane
        anchorPane.getChildren().add(rectangle);
        return rectangle;
    }

    public void fallStick() {
        double bottomRightX = rectangle.getX() + this.getWidth();
        double bottomRightY = rectangle.getY() + rectangle.getHeight();

        Rotate rotate = new Rotate(0, bottomRightX, bottomRightY); // Initial angle is 0 degrees
        rectangle.getTransforms().add(rotate);

        Duration duration = Duration.seconds(0.5); // Total animation duration (2 seconds in this example)
        int totalFrames = 90; // Total number of frames for smooth animation

        // Create a Timeline for animation
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1); // Play the animation once

        // Add key frames to gradually increase the angle over the animation duration
        for (int i = 0; i <= totalFrames; i++) {
            Duration frameTime = Duration.millis(duration.toMillis() * i / totalFrames);
            double frameAngle = 90 * i / totalFrames; // Rotate by 90 degrees over the animation
            timeline.getKeyFrames().add(new KeyFrame(frameTime, event -> rotate.setAngle(frameAngle)));
        }


        // Add an event handler for when the animation completes

        // Play the animation
        timeline.play();
    }

    public boolean checkPlatformFall() {
        double distanceToCover = platform.getCoordinateX() - this.getCoordinateX();
        // checks fo fall
        return !(this.height < distanceToCover) && !(this.height > distanceToCover + platform.getWidth());
    }

    public void moveOut() {
        double targetX = 1000;
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), rectangle);
        transition.setByX(-targetX - this.getWidth()); // Move by the calculated distance
        transition.play();
    }

    public void checkFallOnPlatform() throws IOException {
        Player player = Player.getInstance(null, null, null, null, null, null);


        double distanceToCover = platform.getCoordinateX() - this.getCoordinateX();
        if (this.height < distanceToCover || this.height > distanceToCover + platform.getWidth()) { // checks fo fall
            fallStick();
            hero.fallDown();

        } else if ((this.height <= distanceToCover + platform.getMiddle() + 4) && this.height >= distanceToCover + platform.getMiddle() - 5) { //checks for bonus
            player.setCurrentScore(player.getCurrentScore() + 2);
            GameMusicPlayer musicPlayer = new GameMusicPlayer("bonus.mp3");
            musicPlayer.play(0);
            player.animateLabelBonus(player.getLabelBonus(), platform.getCoordinateX() + platform.getMiddle() - 10, 1);
            player.getScoreLabel().setText(player.getCurrentScore() + "");
            if (player.getCurrentScore() > player.getHighScore()) {
                player.setHighScore(player.getCurrentScore());
            }

        } else {
            player.setCurrentScore(player.getCurrentScore() + 1); // levelup
            player.getScoreLabel().setText(player.getCurrentScore() + "");


            background.moveForward(15);
            if (player.getCurrentScore() > player.getHighScore()) {
                player.setHighScore(player.getCurrentScore());
            }
        }
    }

}
