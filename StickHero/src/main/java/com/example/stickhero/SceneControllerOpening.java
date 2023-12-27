package com.example.stickhero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class SceneControllerOpening {
    private Stage stage;
    private Scene scene;

    @FXML
    private Button playButton;
    @FXML
    private Button resumeGame;
    @FXML
    private ImageView backgroundImageView;
    @FXML
    private AnchorPane anchorPane;
    public Image randomBackground(){
        Random random = new Random();
        int randomNumber = random.nextInt(9) + 1;
        Image image =  new Image(getClass().getResource( randomNumber + ".png").toExternalForm());
        return image;
    }
    public void initializeMyScene() {
        Player player = Player.getInstance(null, null, null, null, null, null);
        playButton.setOnAction(event -> {
            try {
                newGame(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        playButton.setOnAction(event -> {
            try {
                newGame(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        resumeGame.setOnAction(event -> {
            loadGame(event);
        });

        player.setCherryCount(Player.deserializeInt("cherryCount.txt"));
        player.setHighScore(Player.deserializeInt("highscore.txt"));
        player.getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));
        player.getScoreLabel().setText("0");
        player.setCurrentScore(0);
        backgroundImageView.setImage(randomBackground());
    }


    public void loadGame(ActionEvent event) {
        try {
            GameMusicPlayer musicPlayer1 = new GameMusicPlayer("click.mp3");
            musicPlayer1.play(0);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Playing.fxml"));
            Parent root = loader.load();

            // Getting the controller for the 'Playing' scene
            SceneControllerPlaying playingController = loader.getController();

            // Initialize the new scene
            playingController.initializeMyScene(backgroundImageView.getImage());
            Player player = Player.getInstance(null, null, null, null, null, null);
            player.setCurrentScore(Player.deserializeInt("currentScore.txt"));
            player.setCherryCount(Player.deserializeInt("cherryCount.txt"));
            player.setHighScore(Player.deserializeInt("highscore.txt"));
            player.getScoreLabel().setText(Integer.toString(player.getCurrentScore()));
            player.getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));
            String imageName = Player.deserializeString("background.txt");
            Image image =   new Image(getClass().getResource(imageName).toExternalForm());
            playingController.myBackground.setImage(image);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Animations.transitionToScene(root,stage, anchorPane);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void newGame(ActionEvent event) throws IOException {
        GameMusicPlayer musicPlayer1 = new GameMusicPlayer("click.mp3");
        musicPlayer1.play(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Playing.fxml"));
        Parent root;
        root = loader.load();
        Player player = Player.getInstance(null, null, null, null, null, null);
        SceneControllerPlaying sceneControllerPlaying = loader.getController();
        sceneControllerPlaying.initializeMyScene(backgroundImageView.getImage());
        sceneControllerPlaying.getPlayer().setCurrentScore(0);
        player.getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));
        sceneControllerPlaying.getPlayer().getCherryCountLabel().setText(Integer.toString(player.getCherryCount()));
        sceneControllerPlaying.getPlayer().setCherryCount(player.getCherryCount());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Animations.transitionToScene(root,stage, anchorPane);
    }

}
