package com.example.stickhero;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class SceneControllerGameOver {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label scoreLabelFinal;
    @FXML
    private Label highscoreLabel;
    @FXML
    private Label cherryCountFinalLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private ImageView cherriesTooFew;
    @FXML
    private AnchorPane anchorPane;
    public void updateGame(String score, String cherryCount, String highscore) throws IOException {
        scoreLabelFinal.setText(score);
        cherryCountFinalLabel.setText(cherryCount);
        highscoreLabel.setText(highscore);
        Player player = Player.getInstance(null , null, null, null, null, null);
        Player.serializeInt(player.getCherryCount(), "cherryCount.txt");
        player.setCherryCount(Integer.parseInt(cherryCount));
    }


    public Image randomBackground(){
        Random random = new Random();
        int randomNumber = random.nextInt(9) + 1;
        Image image =  new Image(getClass().getResource( randomNumber + ".png").toExternalForm());
        return image;
    }

    public void animateLabel(ImageView label, double durationInSeconds) {
        label.setOpacity(0);
        double originalYPosition = label.getLayoutY();

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(durationInSeconds / 2), label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(durationInSeconds / 2), label);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        TranslateTransition moveUp = new TranslateTransition(Duration.seconds(durationInSeconds / 2), label);
        moveUp.setByY(-20);
        TranslateTransition moveDown = new TranslateTransition(Duration.seconds(durationInSeconds / 2), label);
        moveDown.setByY(20);
        SequentialTransition fadeInSequence = new SequentialTransition(fadeIn, moveUp);

        SequentialTransition fadeOutSequence = new SequentialTransition(moveDown, fadeOut);
        SequentialTransition combinedSequence = new SequentialTransition(fadeInSequence, fadeOutSequence);
        combinedSequence.play();
        combinedSequence.setOnFinished(event -> label.setLayoutY(originalYPosition));
    }


    public void revivePlayer(ActionEvent event) {
        try {
            GameMusicPlayer musicPlayer1 = new GameMusicPlayer("click.mp3");
            musicPlayer1.play(0);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Playing.fxml"));
            root = loader.load();
            SceneControllerPlaying sceneControllerPlaying = loader.getController();
            sceneControllerPlaying.initializeMyScene(sceneControllerPlaying.myBackground.getImage());
            sceneControllerPlaying.resetAfter(sceneControllerPlaying.myBackground.getImage());
            Player player = Player.getInstance(null, null, null, null, null, null);
            System.out.println(player.getCurrentScore());
            System.out.println(player.getCherryCount());
            if (player.getCurrentScore() >= 5 && (player.getCherryCount()>= 2)){
                System.out.println(player.getCurrentScore() + "Score");
                player.setCherryCount(player.getCherryCount() - 2);
                player.setCurrentScore(player.getCurrentScore() - 5);

                player.getScoreLabel().setText(Integer.toString(player.getCurrentScore()));
                player.getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));

                sceneControllerPlaying.setBackground(sceneControllerPlaying.myBackground.getImage());
                sceneControllerPlaying.resetAfter(sceneControllerPlaying.myBackground.getImage());// Assuming initializeMyScene is the method you want to call

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Animations.transitionToScene(root,stage, anchorPane);

            } else {
                animateLabel(cherriesTooFew, 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toHome(ActionEvent event) {
        GameMusicPlayer musicPlayer1 = new GameMusicPlayer("click.mp3");
        musicPlayer1.play(0);
        Player player = Player.getInstance(null, null, null, null, null, null);
        player.setCurrentScore(0);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Opening Screen.fxml"));
            Parent root = loader.load();
            // Getting the controller for the 'Playing' scene
            SceneControllerOpening openingController = loader.getController();
            openingController.initializeMyScene();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Animations.transitionToScene(root, stage, anchorPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newGame(Event event) throws IOException {
        GameMusicPlayer musicPlayer1 = new GameMusicPlayer("click.mp3");
        musicPlayer1.play(0);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Playing.fxml"));
        Parent root;
        root = loader.load();
        Player player = Player.getInstance(null, null, null, null, null, null);
        SceneControllerPlaying sceneControllerPlaying = loader.getController();
        sceneControllerPlaying.initializeMyScene(randomBackground());
        sceneControllerPlaying.getPlayer().setCurrentScore(0);
        player.getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));
        sceneControllerPlaying.getPlayer().getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));
        sceneControllerPlaying.getPlayer().setCherryCount(player.getCherryCount());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Animations.transitionToScene(root, stage, anchorPane);
    }
}


