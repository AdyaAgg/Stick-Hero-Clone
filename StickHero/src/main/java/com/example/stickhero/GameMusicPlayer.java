package com.example.stickhero;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameMusicPlayer {

    private MediaPlayer mediaPlayer;

    public GameMusicPlayer(String musicFilePath) {
        Media media = new Media(getClass().getResource(musicFilePath).toExternalForm());
        mediaPlayer = new MediaPlayer(media);
    }

    public void play(int nonstop) { // Plays the music in a loop, or once depending on boolean nonstop
        if (nonstop == 0){
            mediaPlayer.play();
        } else {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
    }
}
