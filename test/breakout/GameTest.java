package breakout;

import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.temporal.TemporalAccessor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.sleep;

class GameTest extends Game {
    // create an instance of our game to be able to call in tests (like step())
    private final Game myGame = new Game();
    // keep created scene to allow mouse and keyboard events
    private Scene myScene;
    // keep any useful elements whose values you want to test directly in multiple tests
    private Circle myBall;
    private Rectangle myPlatform;


    /**
     * Start special test version of application that does not animate on its own before each test.
     *
     * Automatically called @BeforeEach by TestFX.
     */

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        // create game's scene with all shapes in their initial positions and show it
        myScene = myGame.setupScene(Game.SIZE, Game.SIZE, Game.BACKGROUND, 1);
        stage.setScene(myScene);
        stage.show();

        // find individual items within game by ID (must have been set in your code using setID())
       // myBall = lookup("#ball").query();
        //myPlatform = lookup("#platform").query();

    }

    @Test
    public void testInitialPositions () {
        // GIVEN, start of the game
        // WHEN, no events have happened
        // THEN, check elements are correctly positioned (to as much detail as yuo need)
        assertEquals(300, myBall.getCenterX());
        assertEquals(300, myBall.getCenterY());
        assertEquals(15, myBall.getRadius());

        assertEquals(260, myPlatform.getX());
        assertEquals(560, myPlatform.getY());
        assertEquals(80, myPlatform.getWidth());
        assertEquals(20, myPlatform.getHeight());

        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests, but helps "see" results
    }

}