package com.example.stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Platform extends GameObjects {
    private double width;
    private double height;
    private final double middle;
    private Rectangle rectangle;

    public Rectangle getBonusBox() {
        return bonusBox;
    }

    private final Rectangle bonusBox;
    private final AnchorPane anchorPane;

    public Platform(Rectangle rectangle, Rectangle bonusBox, AnchorPane anchorPane) {
        this.rectangle = rectangle;
        this.middle = rectangle.getWidth() / 2;
        this.width = rectangle.getWidth();
        this.setCoordinateX(rectangle.getLayoutX());
        this.setCoordinateY(rectangle.getLayoutY());
        this.bonusBox = bonusBox;
        this.anchorPane = anchorPane;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public double getWidth() {
        return this.rectangle.getWidth();
    }

    public void setWidth(float width) {
        this.rectangle.setWidth(width);
        this.width = rectangle.getWidth();
    }

    public void update() {
        double targetX = this.getCoordinateX() - 110;
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), rectangle);
        transition.setByX(-targetX - this.getWidth()); // Move by the calculated distance
        transition.play();

        transition = new TranslateTransition(Duration.seconds(1), bonusBox);


        transition.setByX(-targetX - this.getWidth()); // Move by the calculated distance
        transition.play();
    }

    public void moveOut() {
        double targetX = -1000;
        double distanceToMove = targetX - rectangle.getLayoutX();

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), rectangle);
        transition.setByX(distanceToMove); // Move by the calculated distance
        transition.play();

        if (bonusBox != null) {
            distanceToMove = targetX - bonusBox.getLayoutX();

            transition = new TranslateTransition(Duration.seconds(1), bonusBox);
            transition.setByX(distanceToMove); // Move by the calculated distance
            GameMusicPlayer musicPlayer = new GameMusicPlayer("whoosh.mp3");
            musicPlayer.play(0);
            transition.play();
        }
    }

    public double getHeight() {
        return this.rectangle.getHeight();
    }

    public void setHeight(float height) {
        this.rectangle.setHeight(height);
        this.height = this.rectangle.getHeight();
    }

    public double getMiddle() {
        return middle;
    }


    public Rectangle spawnPlatform() {
        Random random = new Random();

        // Create a new rectangle with the specified criteria
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(140);
        rectangle.setWidth(random.nextInt(151) + 30); // Width between 20 and 170
        rectangle.setStyle("-fx-fill: radial-gradient(focus-angle 0.0deg, focus-distance 0.0%, center 100.0% 6.4394%, radius 100.0%, #282828 0.0%, #030303 100.0%);");

        // Generate a random X position so that the leftmost point of x is always greater than 120
        double maxWidth = 355 - rectangle.getWidth();
        double minLeftMostX = 120; // Minimum value for the leftmost point of x
        double xPos = (random.nextDouble()) * (maxWidth - minLeftMostX) + minLeftMostX;
        rectangle.setX(xPos + 1000); // Adding 1000 to animate in from the outside

        rectangle.setY(267); // layout y set of the original game rectangle.
        // Add the rectangle to the provided Pane
        anchorPane.getChildren().add(rectangle);
        return rectangle;
    }

    public void moveIn() {

        double distanceToMove = -1000;

        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), rectangle);
        transition.setByX(distanceToMove); // Move by the calculated distance
        transition.play();

        if (bonusBox != null) {
            distanceToMove = -1000 - bonusBox.getLayoutX();

            transition = new TranslateTransition(Duration.seconds(1), bonusBox);
            transition.setByX(distanceToMove); // Move by the calculated distance
            transition.play();
        }
    }

    public Rectangle spawnBonusBox(Rectangle baseRectangle) {
        // Create a new rectangle for the bonus box
        Rectangle bonusBox = new Rectangle();

        // Set the size of the bonus box (adjust these values as needed)
        double boxWidth = 8;
        double boxHeight = 6;
        bonusBox.setWidth(boxWidth);
        bonusBox.setHeight(boxHeight);

        // Calculate the X position to place the bonus box at the top center of the baseRectangle
        double bonusBoxX = baseRectangle.getX() + (baseRectangle.getWidth() - boxWidth) / 2;

        // Set the Y position to place the bonus box just above the baseRectangle
        double bonusBoxY = baseRectangle.getY() - boxHeight + boxHeight;

        // Set the position of the bonus box
        bonusBox.setX(bonusBoxX);
        bonusBox.setY(bonusBoxY);

        // Set the color or other properties of the bonus box as needed
        bonusBox.setFill(Color.web("#ff0202")); // Change the color as desired
        anchorPane.getChildren().add(bonusBox);
        return bonusBox;
    }

    public void move() {
    }
}
