package breakout;

import breakout.screens.GameOverScreen;
import breakout.screens.LevelFinishedScreen;
import breakout.screens.WinningScreen;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game extends Application {
    public static final String TITLE = "Breakout Beta Version";
    public static final String scoreHeader = "Score: ";
    public static final String levelHeader = "LEVEL ";
    public static final String livesHeader = "Lives left: ";
    private static final int START_LEVEL = 1;
    private static final int FINAL_LEVEL = 3;
    private static final int MIN_LIVES = 1;
    private static final int MIN_BLOCK_HITS = 1;

    //TODO - go through and make most of these private likely

    public static final int SIZE = 600;
    private static final int FRAMES_PER_SECOND = 60;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.BLUE;
    private static final int PLATFORM_SIZE = 80;
    private static final int PLATFORM_SPEED = 30;

    private Level myLevel;   //level object
    private int currentLevelNum;


    private Stage myStage;

    // some things needed to remember during game
    private Scene[] levels;
    //private Scene myScene;
    private Platform myPlatform;
    private Ball myBall;
    private Player myPlayer = new Player();
    private Text scoreText;
    private Text levelText;
    private Text livesText;


    //Todo - added this blockList and animation instance variables - can also pass to functions if need be
    private List<Block> startBlockList;
    private List<Block> currentBlockList;
    private Timeline animation;

    public void start (Stage stage) throws FileNotFoundException {
        // attach scene to the stage and display it
            currentLevelNum = START_LEVEL;
            myStage = stage;
            Scene myScene = setupScene(currentLevelNum);
            connectSceneToStage(myScene);
            runGame(myScene);
    }
    private void connectSceneToStage(Scene currentScene){
        myStage.setScene(currentScene);
        myStage.setTitle(TITLE);
        myStage.show();
    }
    public void runGame(Scene currentScene){
        currentScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            try {
                step(SECOND_DELAY);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }
//TODO- figure out how to stop running the step function - then go back and do tests

//    public void stopGameOnScreen(Scene){
//
//    }

    Scene setupScene(int level) throws FileNotFoundException {
        myLevel = new Level(level, myPlayer);
        Scene scene = myLevel.getScene();
        myBall = myLevel.getMyBall();
        myPlatform = myLevel.getMyPlatform();
        startBlockList = myLevel.getBlockList();
        currentBlockList = new ArrayList<>();
        currentBlockList.addAll(startBlockList); // order added to the group is the order in which they are drawn (so last one is on top)

        scoreText = myLevel.getScoreText();
        levelText = myLevel.getLevelText();
        livesText = myLevel.getLivesText();

        return scene;
    }
    void step (double elapsedTime) throws FileNotFoundException {
        // update "actors" attributes

        System.out.println(1);
        updateShapes(elapsedTime);
        updateText();
        // check for collisions (order may matter! and should be its own method if lots of kinds of collisions)
        checkBallPlatformCollision(myPlatform.getThisPlatform());
        checkBallBlockCollision();
        checkGameStatus();    //

        checkStageBoundary();
    }


    private void checkGameStatus() throws FileNotFoundException {
        /**should check if game is over or if new level should be created*/

        if(myPlayer.getLivesLeft()<MIN_LIVES){
            gameOver();

        }
        else if(noBlocksLeft()){
            levelComplete();
        }
    }
    private void gameOver(){
        animation.pause();
        currentLevelNum = START_LEVEL;
        Screen gameOverObject = new GameOverScreen();
        Scene gameOverScene = gameOverObject.getMyScene();
        connectSceneToStage(gameOverScene);
        checkButtonToPlay(gameOverObject,START_LEVEL);
        //TODO - write something that shows a game over screen and add a button to do this.... For Now I will just reset to level 1
    }

    private void levelComplete() throws FileNotFoundException {
        animation.pause();
        currentLevelNum++;

        Screen screenObject;
        if(currentLevelNum>FINAL_LEVEL){
            screenObject = new WinningScreen();
            currentLevelNum = START_LEVEL;
        }
        else{
            screenObject = new LevelFinishedScreen();
        }
        Scene currentScene = screenObject.getMyScene();
        connectSceneToStage(currentScene);
        checkButtonToPlay(screenObject,currentLevelNum);

    }



    private void checkButtonToPlay(Screen currentScreen, int nextLevel){

        Button button = currentScreen.getMyButton();
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
                if(nextLevel == START_LEVEL){
                    myPlayer = new Player();
                }
                try {
                    generateNextLevel(nextLevel);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });

    }


    private void generateNextLevel(int newLevel) throws FileNotFoundException {
        Scene myScene = setupScene(newLevel);
        connectSceneToStage(myScene);
        runGame(myScene);
    }


    public boolean noBlocksLeft(){
        return currentBlockList.size() == 0;
    }

    private void updateShapes (double elapsedTime) {
        // there are more sophisticated ways to animate shapes, but these simple ways work fine to start
        myBall.getThisBall().setCenterX(myBall.getThisBall().getCenterX() + myBall.getSpeedX() * elapsedTime);
        myBall.getThisBall().setCenterY(myBall.getThisBall().getCenterY() + myBall.getSpeedY() * elapsedTime);
    }

    private void updateText(){
        scoreText.textProperty().bind(Bindings.createStringBinding(() -> (scoreHeader + myPlayer.getScore())));
        levelText.textProperty().bind(Bindings.createStringBinding(() -> (levelHeader + myLevel.getCurrentLevel())));
        livesText.textProperty().bind(Bindings.createStringBinding(() -> (livesHeader + myPlayer.getLivesLeft())));
    }

    private void checkBallBlockCollision(){
        for(Block block : startBlockList){
            if(checkBallPlatformCollision(block.getThisBlock())){
                block.updateBlocks();
                myPlayer.addScore();
                if(block.getNumberOfHitsLeft() < MIN_BLOCK_HITS){
                    currentBlockList.remove(block);
                }
            }
        }
    }

    private boolean checkBallPlatformCollision (Shape hitter) {
        // with shapes, can check precisely
        Shape intersection = Shape.intersect(hitter, myBall.getThisBall());
        if (intersection.getBoundsInLocal().getWidth() != -1) {
            myBall.flipDirectionY();
            return true;
        }
            return false;
    }

   //TODO - refactor this ish

    private void checkStageBoundary(){
        Circle ballShape = myBall.getThisBall();
        Rectangle platformShape = myPlatform.getThisPlatform();

        checkSideBoundary(ballShape);
        checkTopBoundary(ballShape);
        checkBottomBoundary(ballShape);
        checkPlatformBoundary(platformShape);
    }
    private void checkTopBoundary(Circle ball){
        if(ball.getCenterY() - ball.getRadius() <= 0){
            myBall.flipDirectionY();
        }
    }
    private void checkSideBoundary(Circle ball){
        if(ball.getCenterX() + ball.getRadius() >= SIZE ||ball.getCenterX() - ball.getRadius() <= 0){
            myBall.flipDirectionX();
        }
    }
    private void checkBottomBoundary(Circle ball){
        if(ball.getCenterY() - ball.getRadius() >= SIZE){
            ball.setCenterX(SIZE/2);
            ball.setCenterY(SIZE/2);
            myPlayer.loseLife();
            myBall.flipDirectionY();
        }
    }

    public void checkPlatformBoundary(Rectangle platform){
        if(platform.getX() + platform.getWidth() >= SIZE){
            platform.setX(SIZE - platform.getWidth());
        }
        else if(platform.getX() <= 0){
            platform.setX(0);
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
        myBall.getThisBall().setCenterX(SIZE/2);
        myBall.getThisBall().setCenterY(SIZE/2);
        myPlatform.getThisPlatform().setX(SIZE/2 - PLATFORM_SIZE/2);
    }

    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case LEFT -> myPlatform.getThisPlatform().setX(myPlatform.getThisPlatform().getX() - PLATFORM_SPEED);
            case RIGHT -> myPlatform.getThisPlatform().setX(myPlatform.getThisPlatform().getX() + PLATFORM_SPEED);
            case SPACE -> pauseGame();
            case R -> resetPaddleAndBall();
            case L -> myPlayer.addLife();
        }
    }

    public List<Block> getBlockList(){
        return startBlockList;
    }

}
