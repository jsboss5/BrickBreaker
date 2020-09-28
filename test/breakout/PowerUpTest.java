package breakout;

import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PowerUpTest extends DukeApplicationTest{
    // create an instance of our game to be able to call in tests (like step())
    private final Game myGame = new Game();
    // keep created scene to allow mouse and keyboard events
    private Scene myScene;
    private Stage STAGE;
    public static final int PLATFORM_SIZE = 80;
    public static final int BALL_SIZE = 30;
    // keep any useful elements whose values you want to test directly in multiple tests
    private Ball myBall;
    private PowerUp myPowerUp;
    private final int SIZE = 600;
    private Platform myPlatform;
    public static final int VERTICAL_OFFSET = 40;
    public Player myPlayer;

    Game br = new Game();

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        // create game's scene with all shapes in their initial positions and show it

        STAGE = stage;
        myScene = myGame.setupScene(1);
        STAGE.setScene(myScene);
        STAGE.setTitle("POWER UP TESTS");
        STAGE.show();
        STAGE.show();

        myBall = new Ball(1);
        myPowerUp = PowerUp.chooseRandomPowerUP();
        myPlatform = new Platform(1);
        myBall.setThisBall(lookup("#ball").query());
        myPowerUp.setThisPowerUp(lookup("#ball").query());
        myPlatform.setThisPlatform(lookup("#platform").query());
        // find individual items within game by ID (must have been set in your code using setID())

    }

    @Test
    void testPowerUpStillWhileBlockIsPresent() throws IOException {
        /*
        Tests if powerups are still while "inside" a block"
         */
        myPowerUp.createPowerUpAtPoint(100, 100);
        myPowerUp.createPowerUpAtPoint(200, 100);

        sleep(1, TimeUnit.SECONDS);
        myGame.step(Game.SECOND_DELAY);
        sleep(1, TimeUnit.SECONDS);

        assertEquals(200, myPowerUp.getThisPowerUp().getCenterX());
        assertEquals(100, myPowerUp.getThisPowerUp().getCenterY());
    }
}