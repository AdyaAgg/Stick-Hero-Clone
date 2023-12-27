package com.example.stickhero;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.LightBase;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.*;

public class Player implements Serializable {


    private static Player instance;
    private Hero hero;
    private int currentScore = 0;
    private int highScore = 0;
    private int background;
    private int cherryCount = 0;
    private Label bonusLabel;
    private Label scoreLabel;
    private Label messageLabel;
    private Label perfectLabel;
    private int cherryCountNumber;
    private Label cherryCountLabel;

    //Singleton class instance
    private Player(Label bonusLabel, Label scoreLabel, Label messageLabel, Label perfectLabel, Label cherryCountLabel, Hero hero) {
        this.bonusLabel = bonusLabel;
        this.scoreLabel = scoreLabel;
        this.messageLabel = messageLabel;
        this.perfectLabel = perfectLabel;
        this.cherryCountLabel = cherryCountLabel;
        this.hero = hero;
    }

    public static Player getInstance(Label bonusLabel, Label scoreLabel, Label messageLabel, Label perfectLabel, Label cherryCountLabel, Hero hero) {
        if (instance == null) {
            instance = new Player(bonusLabel, scoreLabel, messageLabel, perfectLabel, cherryCountLabel, hero);
        }
        return instance;
    }


    public static void serializeInt(int value, String filename) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            dos.writeInt(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int deserializeInt(String filename) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            return dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0; // Or any other default value or error handling
    }

    public static String deserializeString(String filename) {
        String result = null;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filename))) {
            result = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void serializeString(String string, String filename) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))) {
            dos.writeUTF(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Static method to get the single instance of the class


    public int getCherryCount() {
        return cherryCount;
    }

    public void setCherryCount(int cherryCount) {
        this.cherryCount = cherryCount;
    }

    public Label getCherryCountLabel() {
        return cherryCountLabel;
    }

    public void setCherryCountLabel(Label cherryCountLabel) {
        this.cherryCountLabel = cherryCountLabel;
    }

    public Label getLabelBonus() {
        return this.bonusLabel;
    }

    public Label getScoreLabel() {
        return this.scoreLabel;
    }

    public void setScoreLabel(Label scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public Label getPerfectLabel() {
        return this.perfectLabel;
    }

    public void setPerfectLabel(Label perfectLabel) {
        this.perfectLabel = perfectLabel;
    }

    public Label getMessageLabel() {
        return this.messageLabel;
    }

    public void setMessageLabel(Label messageLabel) {
        this.messageLabel = messageLabel;
    }

    public void setBonusLabel(Label bonusLabel) {
        this.bonusLabel = bonusLabel;
    }

    public void animateLabelBonus(Label label, double xPosition, double durationInSeconds) {
        // Set the initial opacity to 0
        label.setOpacity(0);

        // Save the original Y position
        double originalYPosition = label.getLayoutY();

        if (xPosition != -1) {
            label.setLayoutX(xPosition);
        }

        // Create the fade in transition
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Create the fade out transition
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), label);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        // Create translate transition for moving up
        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(durationInSeconds / 2), label);
        moveUp.setByY(-20);

        // Create translate transition for moving down
        TranslateTransition moveDown = new TranslateTransition(Duration.seconds(durationInSeconds / 2), label);
        moveDown.setByY(20);

        // Create a sequential transition to play fade in and move up in sequence
        SequentialTransition fadeInSequence = new SequentialTransition(fadeIn, moveUp);

        // Create a sequential transition to play move down and fade out in sequence
        SequentialTransition fadeOutSequence = new SequentialTransition(moveDown, fadeOut);

        // Combine both sequences
        SequentialTransition combinedSequence = new SequentialTransition(fadeInSequence, fadeOutSequence);

        // Start the animation
        combinedSequence.play();

        // Reset position after animation ends
        combinedSequence.setOnFinished(event -> label.setLayoutY(originalYPosition));
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }


}
