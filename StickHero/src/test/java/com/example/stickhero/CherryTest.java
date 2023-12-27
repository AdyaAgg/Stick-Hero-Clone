package com.example.stickhero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CherryTest {

        @Test
        void testCherryImagePathConstant() {
            assertEquals("cherry.png", Cherry.CHERRY_IMAGE_PATH, "CHERRY_IMAGE_PATH constant has changed.");
        }

        @Test
        void testCherrySizeConstant() {
            assertEquals(20, Cherry.CHERRY_SIZE, "CHERRY_SIZE constant has changed.");
        }

        @Test
        void testYOffsetFromBottomConstant() {
            assertEquals(110, Cherry.Y_OFFSET_FROM_BOTTOM, "Y_OFFSET_FROM_BOTTOM constant has changed.");
        }


}