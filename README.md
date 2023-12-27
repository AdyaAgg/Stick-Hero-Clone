## Stick Hero Clone in JavaFX
Description
This project is a JavaFX-based clone of the popular mobile game Stick Hero. The game challenges players to help a hero cross platforms by extending a stick to the right length. It is developed using robust object-oriented programming principles in Java, showcasing the effective use of design patterns and providing a dynamic and engaging gameplay experience.

![Loading Screenshot](https://github.com/AdyaAgg/Stick-Hero-Clone/assets/119076165/62e63667-87af-4556-b80d-005393f8ff05)

## Design Patterns
Singleton Pattern in Player Class: This pattern ensures a single instance of the player throughout the game, managing the player's state and actions consistently.
Adapter Pattern in Cherries Class: Cherries in the game act as power-ups. The Adapter pattern is used to integrate these elements seamlessly into the game environment, enhancing gameplay and interaction.

## Game Mechanics
Stick Extension: Players extend a stick by holding a button, aiming to reach the right length to connect to the next platform.
Platform Variation: Each platform varies in distance, requiring careful estimation of the stick length.
Cherries as Power-Ups: Collecting cherries gives players bonus points or special abilities.

## Gameplay Features
New Game: Start a new adventure and try to score as high as possible.
Save & Load Game: Players can save their progress and resume their game at a later time, offering flexibility in gameplay.
End Game: Option to end the current game session and view scores.

## User Interface
The game features a simple, intuitive interface designed for easy navigation and a seamless gaming experience.
The visuals are vibrant and engaging, keeping players entertained and immersed.

## JUnit Tests
The project includes comprehensive JUnit tests:
Player Test: Tests player behavior, stick extension, and platform interaction.
Cherry Test: Ensures proper functioning of cherries as power-ups.
Scene Controller Test: Assesses the scene controller's management of game scenes and transitions

![Playing Screenshot](https://github.com/AdyaAgg/Stick-Hero-Clone/assets/119076165/59c98861-7649-4041-b53c-59e90fec375a)

## Features
Design Patterns
The Stick Hero game incorporates design patterns to enhance code structure and maintainability. Notable design patterns used in the project include the Singleton pattern for the Player class and the Adapter pattern for resizing fruit images.


JUnit Testing
The project includes JUnit tests to ensure the reliability and correctness of the implemented functionalities. Test cases cover critical aspects of the game logic, ensuring robustness and facilitating future modifications.

Multithreading
Multithreading is employed in the SceneControllerLoading class to simulate loading tasks. The loading process runs on a separate thread, preventing the game's UI from freezing and providing a smooth user experience.

Sound Effects
Sound effects are integrated into the game to enhance the overall gaming atmosphere. These effects contribute to a more immersive experience for players, providing feedback for various in-game actions.

Animations
JavaFX animations are seamlessly integrated into the SceneControllerGameOver class to create dynamic and visually appealing game elements, such as fading labels and image transitions.

Changing Backgrounds
Players can experience diverse environments as the game allows for changing backgrounds. This feature adds variety to the gaming experience, making each level visually distinct.
Revive and Save Progress
The game over screen provides players with options to revive, update, and save their progress. Reviving requires a minimum score and cherry count, providing strategic depth to the gameplay.

![Game Over Screenshot](https://github.com/AdyaAgg/Stick-Hero-Clone/assets/119076165/b5a6c4ba-aab5-4067-9039-4422229de24e)
