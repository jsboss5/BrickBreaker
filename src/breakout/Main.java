package breakout;

import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {
    /**
     * Start of the program.
     */
    public static void main (String[] args) throws FileNotFoundException {
        Game game = new Game();
        Stage gameStage = new Stage();
        game.start(gameStage);
    }
}
