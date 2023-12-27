package com.example.stickhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.example.stickhero.Cherry.CHERRY_IMAGE_PATH;

public class Fruit extends ImageView {
    public Fruit(Image image) {
        super(new Image(Cherry.class.getResourceAsStream(CHERRY_IMAGE_PATH)));
    }
}
