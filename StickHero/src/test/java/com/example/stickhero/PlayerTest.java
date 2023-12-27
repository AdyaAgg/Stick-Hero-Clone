package com.example.stickhero;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void testSerializationDeserialisation() {
        int originalValue = 12345; // Example value to test
        String testFilename = "testfile.dat"; // Temporary file for testing

        // Serialize the original value
        Player.serializeInt(originalValue, testFilename);

        // Deserialize the value
        int deserializedValue = Player.deserializeInt(testFilename);

        // Check if the original and deserialized values are the same
        assertEquals(originalValue, deserializedValue, "Deserialized value should be equal to the original value");

        // Optionally, delete the test file after test is complete
        new File(testFilename).delete();
    }

}