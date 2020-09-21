//package breakout;
//
//import javafx.scene.Scene;
//import javafx.scene.input.KeyCode;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.Test;
//import org.testfx.service.query.NodeQuery;
//
//import java.awt.*;
//import java.io.FileNotFoundException;
//import java.time.temporal.TemporalAccessor;
//import java.util.concurrent.TimeUnit;
//import util.DukeApplicationTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.testfx.util.WaitForAsyncUtils.sleep;
//
//public class GameTest extends DukeApplicationTest {
//    // create an instance of our game to be able to call in tests (like step())
//    private final Game myGame = new Game();
//    // keep created scene to allow mouse and keyboard events
//    private Scene myScene;
//    public static final int PLATFORM_SIZE = 80;
//    public static final int BALL_SIZE = 30;
//    // keep any useful elements whose values you want to test directly in multiple tests
//    private Circle myBall;
//    private final int SIZE = 600;
//    private Rectangle myPlatform;
//    public static final int VERTICAL_OFFSET = 40;
//
//    private static Stage STAGE;
//
//    /**
//     * Start special test version of application that does not animate on its own before each test.
//     *
//     * Automatically called @BeforeEach by TestFX.
//     */
//
//    //TODO
//    // 1. actually pass ball objects instaed of ball shapes
//    // 2. Figure out how to do different sc
//    // 3. REset
//
//    @Override
//    public void start(Stage stage) throws FileNotFoundException {
//        // create game's scene with all shapes in their initial positions and show it
//
//        STAGE = stage;
//        myScene = myGame.setupScene(Game.SIZE, Game.SIZE, Game.BACKGROUND,1);
//        stage.setScene(myScene);
//        stage.show();
//
//        // find individual items within game by ID (must have been set in your code using setID())
//        myBall = lookup("#ball").query();
//        myPlatform = lookup("#platform").query();
//
//    }
//
//    @Test
//    public void testInitialPositions () {
//
//        // GIVEN, start of the game
//        // WHEN, no events have happened
//        // THEN, check elements are correctly positioned (to as much detail as yuo need)
//        assertEquals(300, myBall.getCenterX());
//        assertEquals(300, myBall.getCenterY());
//        assertEquals(15, myBall.getRadius());
//
//        assertEquals(260, myPlatform.getX());
//        assertEquals(560, myPlatform.getY());
//        assertEquals(80, myPlatform.getWidth());
//        assertEquals(20, myPlatform.getHeight());
//
//        sleep(1, TimeUnit.SECONDS);    // PAUSE: not typically recommended in tests, but helps "see" results
//    }
//
//    @Test
//    public void testPlatformMove () {
//        /**
//         * Test to make sure platform moves according to key request
//         */
//        int initialX = SIZE / 2 - PLATFORM_SIZE / 2;
//        int initialY = SIZE - VERTICAL_OFFSET;
//        myPlatform.setX(initialX);
//        myPlatform.setY(initialY);
//
//        sleep(1, TimeUnit.SECONDS);
//        press(myScene, KeyCode.LEFT);
//        sleep(1, TimeUnit.SECONDS);
//
//        assertTrue(initialX - 31 < myPlatform.getX() && myPlatform.getX() < initialX - 28);
//        assertEquals(initialX - 30, myPlatform.getX());
//        assertEquals(initialY, myPlatform.getY());
//
//        sleep(1, TimeUnit.SECONDS);
//        press(myScene, KeyCode.RIGHT);
//        sleep(1, TimeUnit.SECONDS);
//
//        assertEquals(initialX, myPlatform.getX());
//        assertEquals(initialY, myPlatform.getY());
//    }
//    @Test
//    public void testKeyBlocks () {
//        /**
//         * Test to make a large portion of blocks are in the correct place
//         */
//        assertEquals((int)myGame.getBlockList().get(0).thisBlock.getX(), 0);
//        assertEquals((int)myGame.getBlockList().get(0).thisBlock.getY(), 0);
//
//        assertEquals((int)myGame.getBlockList().get(1).thisBlock.getX(), 100);
//        assertEquals((int)myGame.getBlockList().get(1).thisBlock.getY(), 20);
//
//        assertEquals((int)myGame.getBlockList().get(2).thisBlock.getX(), 200);
//        assertEquals((int)myGame.getBlockList().get(2).thisBlock.getY(), 40);
//
//        assertEquals((int)myGame.getBlockList().get(3).thisBlock.getX(), 300);
//        assertEquals((int)myGame.getBlockList().get(3).thisBlock.getY(), 60);
//
//        assertEquals((int)myGame.getBlockList().get(6).thisBlock.getX(), 400);
//        assertEquals((int)myGame.getBlockList().get(6).thisBlock.getY(), 120);
//
//        //assertEquals();
//    }
//
//    @Test
//    public void testBallInCorner () {
//        /**
//         * Tests to see if ball bounces back in corner
//         */
//        int initialX = SIZE - BALL_SIZE/2;
//        int initialY = BALL_SIZE/2;
//        myBall.setCenterX(initialX - 2);
//        myBall.setCenterY(initialY - 2);
//
//
//        sleep(1, TimeUnit.SECONDS);
//        myGame.step(10/Game.FRAMES_PER_SECOND);
//
//
//        sleep(1, TimeUnit.SECONDS);
//
//        assertEquals(initialX - 2, myBall.getCenterX());
//        assertEquals(initialY - 2, myBall.getCenterY());
//
//        sleep(1, TimeUnit.SECONDS);
//        myGame.step(Game.SECOND_DELAY);
//        sleep(1, TimeUnit.SECONDS);
//
//        assertEquals(initialX - 0.83333333333333, myBall.getCenterX());
//        assertEquals(initialY - 0.8333333333333333, myBall.getCenterY());
//    }
//
//    @Test
//    public void testBallBounceBack () {
//        /**
//         * Tests to make srue ball bounces off block correctly
//         */
//        int ballInitialX = BlockMaker.BLOCK_WIDTH + BALL_SIZE;
//        int ballInitialY = BlockMaker.BLOCK_HEIGHT + BALL_SIZE+BlockMaker.BLOCK_HEIGHT;
//        myBall.setCenterX(ballInitialX);
//        myBall.setCenterY(ballInitialY);
//
//        sleep(1, TimeUnit.SECONDS);
//        int i = 0;
//        boolean lowerYCoord = false;
//
//        while(i < 100) {
//            myGame.step(Game.SECOND_DELAY);
//            sleep(100, TimeUnit.MILLISECONDS);
//            i++;
//            if(myBall.getCenterY()> ballInitialY){
//                lowerYCoord = true;
//                break;
//            }
//        }
//        assertTrue(lowerYCoord);
//
//    }
//    @Test
//    public void testOffScreen(){
//        /**
//         * Tests to see if the ball resets position when it goes off screen
//         */
//        int ballInitialX = SIZE/4;
//        int ballInitialY = 12*(BlockMaker.BLOCK_HEIGHT);
//        myBall.setCenterX(ballInitialX);
//        myBall.setCenterY(ballInitialY);
//
//        int i = 0;
//        boolean reset = false;
//
//        while(i < 1000) {
//            myGame.step(Game.SECOND_DELAY);
//            sleep(10, TimeUnit.MILLISECONDS);
//            i++;
//            if(myBall.getCenterX() == SIZE/2 && myBall.getCenterY() == SIZE/2){
//                reset = true;
//                sleep(2, TimeUnit.SECONDS);
//                break;
//            }
//        }
//        assertTrue(reset);
//    }
//
//}