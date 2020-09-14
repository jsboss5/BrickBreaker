package breakout;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.List;

public class Game extends Application {
    public static final String TITLE = "Breakout Beta Version";
    public static final int SIZE = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.BLUE;
    public static final Paint BALL_COLOR = Color.GREEN;
    public static final int BALL_SIZE = 30;
    public static int BALL_SPEED_X = 35;
    public static int BALL_SPEED_Y = -300;
    public static final Paint PLATFORM_COLOR = Color.RED;
    public static final int PLATFORM_SIZE = 80;
    public static final int PLATFORM_SPEED = 30;
    public static final int VERTICAL_OFFSET = 40;



    // some things needed to remember during game
    private Scene myScene;
    private Rectangle myPlatform;
    private Circle myBall;


    //Todo - added this blockList and animation instance variables - can also pass to functions if need be
    private List<Block> blockList;
    private Timeline animation;

    public void start (Stage stage) throws FileNotFoundException {
        // attach scene to the stage and display it
        myScene = setupScene(SIZE, SIZE, BACKGROUND, 1);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    Scene setupScene(int width, int height, Paint background, int level) throws FileNotFoundException {
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        BlockMaker blockSetup = new BlockMaker(level);
        // make some shapes and set their properties
        myBall = new Circle(width / 2, height / 2, BALL_SIZE / 2);
        myBall.setFill(BALL_COLOR);
        myBall.setId("ball");
        // x and y represent the top left corner, so center it in window
        myPlatform = new Rectangle(width / 2 - PLATFORM_SIZE / 2, height - VERTICAL_OFFSET, PLATFORM_SIZE, PLATFORM_SIZE / 4);
        myPlatform.setFill(PLATFORM_COLOR);
        myPlatform.setId("platform");
        blockList = blockSetup.createBlocks(root);
        // order added to the group is the order in which they are drawn (so last one is on top)
        root.getChildren().add(myBall);
        root.getChildren().add(myPlatform);
        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }
    void step (double elapsedTime) {
        // update "actors" attributes
        updateShapes(elapsedTime);
        // check for collisions (order may matter! and should be its own method if lots of kinds of collisions)
        checkBallPlatformCollision(myPlatform, myBall);
        checkBallBlockCollision(myBall);
        checkStageBoundary(myBall);
    }

    private void updateShapes (double elapsedTime) {
        // there are more sophisticated ways to animate shapes, but these simple ways work fine to start
        myBall.setCenterX(myBall.getCenterX() + BALL_SPEED_X * elapsedTime);
        myBall.setCenterY(myBall.getCenterY() + BALL_SPEED_Y * elapsedTime);
    }


    private void checkBallBlockCollision(Shape hittee){
        for(Block block : blockList){
            if(checkBallPlatformCollision(block.thisBlock,hittee)){
                block.updateBlocks();
            }
        }
    }


    private boolean checkBallPlatformCollision (Shape hitter, Shape hittee) {
        // with shapes, can check precisely
        Shape intersection = Shape.intersect(hitter, hittee);
        if (intersection.getBoundsInLocal().getWidth() != -1) {
            BALL_SPEED_Y *= -1;
            return true;
        }
            return false;
    }

    void checkStageBoundary(Circle myBall){
        if(myBall.getCenterX() + myBall.getRadius() > SIZE ||myBall.getCenterX() - myBall.getRadius() < 0){
            BALL_SPEED_X *= -1;
        }
        else if(myBall.getCenterY() - myBall.getRadius() < 0){
            BALL_SPEED_Y *= -1;
        }
        else if(myBall.getCenterY() - myBall.getRadius() > SIZE){
            myBall.setCenterX(SIZE/2);
            myBall.setCenterY(SIZE/2);
            BALL_SPEED_Y *= -1;
        }
        if(myPlatform.getX() + myPlatform.getWidth() > SIZE){
            myPlatform.setX(SIZE - myPlatform.getWidth());
        }
        else if(myPlatform.getX() < 0){
            myPlatform.setX(0);
        }

    }
    private void pauseGame(){
        if(animation.getStatus() == Animation.Status.RUNNING){
            animation.pause();
        }
        else{
            animation.play();
        }
    }
    private void resetPaddleAndBall(){
        myBall.setCenterX(SIZE/2);
        myBall.setCenterY(SIZE/2);
        myPlatform.setX(SIZE/2 - PLATFORM_SIZE/2);
    }

    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case LEFT -> myPlatform.setX(myPlatform.getX() - PLATFORM_SPEED);
            case RIGHT -> myPlatform.setX(myPlatform.getX() + PLATFORM_SPEED);
            case SPACE -> pauseGame();
            case R -> resetPaddleAndBall();
        }
    }


}
