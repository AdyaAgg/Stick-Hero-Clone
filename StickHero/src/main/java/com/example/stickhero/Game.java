package com.example.stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Game extends Application {

    private void showLoadingScreen(Stage stage) throws IOException { // Displys the loading screen animation
        FXMLLoader loader = new FXMLLoader(Game.class.getResource("Loading.fxml"));
        Scene loadingScene = new Scene(loader.load());
        SceneControllerLoading loadingController = loader.getController();
        loadingScene.getStylesheets().add(getClass().getResource("stickHero.css").toExternalForm());
        stage.setScene(loadingScene);
        stage.setTitle("Loading Screen");
        stage.show();
        loadingController.initialize(stage);

    }
    @Override
    public void start(Stage stage) throws IOException {
        showLoadingScreen(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}