package breakout;

import breakout.screens.GameOverScreen;
import breakout.screens.LevelFinishedScreen;
import breakout.screens.SplashScreen;
import breakout.screens.WinningScreen;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends Application {
    public static final String TITLE = "Breakout";
    public static final String SCORE_HEADER = "Score: ";
    public static final String HIGH_SCORE_HEADER = "High Score: ";
    public static final String LEVEL_HEADER = "LEVEL ";
    public static final String LIVES_HEADER = "Lives left: ";
    public static final String HIGHSCORE_PATH = "data/scores.dat";
    public static final String NEW_HIGH_SCORE_HEADER = "NEW HIGH SCORE!!!";
    private static final String ZERO_STRING = "0";
    private static final int START_LEVEL = 1;
    private static final int FINAL_LEVEL = 3;
    private static final int MIN_LIVES = 1;
    private String highScore = "";
    private static final int MIN_BLOCK_HITS = 1;
    private static final int LEVEL_ONE_INT = 1;
    private static final int LEVEL_TWO_INT = 2;
    private static final int LEVEL_THREE_INT = 3;


    public static final int SIZE = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.BLUE;
    private static final int PLATFORM_SIZE = 80;
    private static final int PLATFORM_SPEED = 30;
    private static final double POWER_UP_LIKELIHOOD = 0.5;
    private static final int INTERSECTED = -1;

    private Level myLevel;   //level object
    private int currentLevelNum;


    private Stage myStage;

    private Platform myPlatform;
    private PowerUp myPowerUp;

    private Ball myBall;
    private Key myKey;
    private Player myPlayer = new Player();
    private Text scoreText;
    private Text levelText;
    private Text livesText;
    private static final  Text highScoreText = new Text(10, 50, Game.HIGH_SCORE_HEADER + getHighScore());
    private static final Text NEW_HIGH_SCORE = new Text(10, 80, NEW_HIGH_SCORE_HEADER);

    private List<Block> startBlockList;
    private List<Block> currentBlockList;
     Timeline animation;

    public void start (Stage stage) throws FileNotFoundException {
        // attach scene to the stage and display it
            currentLevelNum = START_LEVEL;
            myStage = stage;
            if(highScore.equals("")){
                highScore = this.getHighScore();
            }

            Screen splashScreenObject = new SplashScreen();
            Scene splashScreenScreen = splashScreenObject.getMyScene();
            connectSceneToStage(splashScreenScreen);
            checkButtonToPlay(splashScreenObject,START_LEVEL);
    }


    private void connectSceneToStage(Scene currentScene){
        myStage.setScene(currentScene);
        myStage.setTitle(TITLE);
        myStage.show();
    }
    public void runGame(Scene currentScene){
        KeyFrame frame = getKeyFrame(currentScene);
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }


    private KeyFrame getKeyFrame(Scene currentScene){
        currentScene.setOnKeyPressed(e -> {
            try {
                handleKeyInput(e.getCode());
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            try {
                step(SECOND_DELAY);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        return frame;
    }
//TODO- figure out how to stop running the step function - then go back and do tests


    Scene setupScene(int level) throws FileNotFoundException {
        myLevel = new Level(level, myPlayer);
        Scene scene = myLevel.getScene();
        myBall = myLevel.getMyBall();
        myKey = myLevel.getMyKey();
        myPlatform = myLevel.getMyPlatform();
        myPowerUp = myLevel.getPowerUp();
        startBlockList = myLevel.getBlockList();
        currentBlockList = new ArrayList<>();
        currentBlockList.addAll(startBlockList); // order added to the group is the order in which they are drawn (so last one is on top)
        setTextProperties();

        return scene;
    }

    private void setTextProperties() {
        scoreText = myLevel.getScoreText();
        levelText = myLevel.getLevelText();
        livesText = myLevel.getLivesText();
        highScoreText.setFill(Color.GOLD);
        NEW_HIGH_SCORE.setFill(Color.RED);
        NEW_HIGH_SCORE.setVisible(false);
    }

    void step (double elapsedTime) throws IOException {
        // update "actors" attributes

        updateShapes(elapsedTime);
        checkScore();
        updateText();
        // check for collisions (order may matter! and should be its own method if lots of kinds of collisions)
        checkBallPlatformCollision(myPlatform.getThisPlatform());
        checkPowerUpPlatformCollision(myPlatform.getThisPlatform());
        checkBallKeyCollision(myKey.getThisKey());
        checkBallBlockCollisionAndPowerUp();
        checkGameStatus();    //

        checkStageBoundary();
    }


    private void checkGameStatus() throws IOException {
        /**should check if game is over or if new level should be created*/

        if(myPlayer.getLivesLeft()<MIN_LIVES){
            gameOver();

        }
        else if(noBlocksLeft()){
            levelComplete();
        }
    }
    private void gameOver() throws IOException {
        animation.pause();
        currentLevelNum = START_LEVEL;
        Screen gameOverObject = new GameOverScreen();
        Scene gameOverScene = gameOverObject.getMyScene();
        connectSceneToStage(gameOverScene);
        checkButtonToPlay(gameOverObject,START_LEVEL);
    }
    private void levelComplete() throws FileNotFoundException {
        animation.pause();
        currentLevelNum++;

        Screen screenObject = checkLevelFinished();
        Scene currentScene = screenObject.getMyScene();
        connectSceneToStage(currentScene);
        checkButtonToPlay(screenObject,currentLevelNum);

    }

    private Screen checkLevelFinished() {
        Screen screenObject;
        if(currentLevelNum>FINAL_LEVEL){
            screenObject = new WinningScreen();
            currentLevelNum = START_LEVEL;
        }
        else{
            screenObject = new LevelFinishedScreen();
        }
        return screenObject;
    }


    private void checkButtonToPlay(Screen currentScreen, int nextLevel){

        Button button = currentScreen.getMyButton();
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
                checkIfNextLevel(nextLevel);
            }
        });

    }

    private void checkIfNextLevel(int nextLevel) {
        if(nextLevel == START_LEVEL){
            myPlayer = new Player();
        }
        try {
            generateNextLevel(nextLevel);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
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
        if(myPowerUp!= null && myLevel.getRoot().getChildren().contains(myPowerUp.getThisPowerUp())){
            myPowerUp.getThisPowerUp().setCenterY(myPowerUp.getThisPowerUp().getCenterY() + myPowerUp.getSpeed() * elapsedTime);
        }
    }

    private void updateText(){
        scoreText.textProperty().bind(Bindings.createStringBinding(() -> (SCORE_HEADER + myPlayer.getScore())));
        levelText.textProperty().bind(Bindings.createStringBinding(() -> (LEVEL_HEADER + myLevel.getCurrentLevel())));
        livesText.textProperty().bind(Bindings.createStringBinding(() -> (LIVES_HEADER + myPlayer.getLivesLeft())));
        highScoreText.textProperty().bind(Bindings.createStringBinding(() -> (HIGH_SCORE_HEADER + getHighScore())));
    }

    private void checkBallBlockCollisionAndPowerUp(){
        for(Block block : startBlockList){
                updateBlock(block);
        }
    }

    private void updateBlock(Block block) {
        if (checkBallPlatformCollision(block.getThisBlock())) {
               if(block.isLocked()){
                   block.updateBlocks(myBall);
               }
               else{
                    block.updateBlocks(myBall);
                    myPlayer.addScore();
                    checkIfBlockBroken(block, myBall);
                }
        }
    }

    private void checkIfBlockBroken(Block block, Ball myBall) {
        if(block.isExplosive() || block.getNumberOfHitsLeft() < MIN_BLOCK_HITS && (myBall.hasKey() || !block.isLocked())){
            if(block.isExplosive()) {
                Block.explode(currentBlockList, block, myBall);
                currentBlockList.remove(block);
            }
            else {
                chanceAtPowerUp(block);
            }
        }
    }

    private void chanceAtPowerUp(Block block) {
        double myRandom = Math.random();
        if(myRandom < POWER_UP_LIKELIHOOD && myPowerUp == null){
           createPowerUp(block);
        }
    }


    private void createPowerUp(Block block){
        myPowerUp = PowerUp.chooseRandomPowerUP();
        myPowerUp.createPowerUpAtPoint(block.getCenterX(), block.getCenterY());
        myLevel.getRoot().getChildren().add(myPowerUp.getThisPowerUp());
    }


    public boolean checkBallPlatformCollision (Shape hitter) {
        // with shapes, can check precisely
        Shape intersection = Shape.intersect(hitter, myBall.getThisBall());
        if (intersection.getBoundsInLocal().getWidth() != INTERSECTED) {
            myBall.flipDirectionY();
            if(myPlatform.getPlatSpeed() > 0) {
                myBall.setSpeedX(myBall.getSpeedX() + 2);
            }
            else if(myPlatform.getPlatSpeed() < 0) {
                myBall.setSpeedX(myBall.getSpeedX() - 2);
            }
            return true;
        }
            return false;
    }

    public boolean checkBallKeyCollision (Circle key) {
        // with shapes, can check precisely
        Shape intersection = Shape.intersect(myKey.getThisKey(), myBall.getThisBall());
        if (intersection.getBoundsInLocal().getWidth() != INTERSECTED) {
            executeKey();
            return true;
        }
        return false;
    }

    private void executeKey() {
        myBall.gotKey();
        myLevel.getRoot().getChildren().remove(myKey.getThisKey());
    }

    private boolean checkPowerUpPlatformCollision (Shape hitter) {
        // with shapes, can check precisely
        if(myPowerUp!= null && myPowerUp.getThisPowerUp() != null) {
            Shape intersection = Shape.intersect(hitter, myPowerUp.getThisPowerUp());
            if (intersection.getBoundsInLocal().getWidth() != INTERSECTED) {
                executePowerUp();
                return true;
            }
        }
        return false;
    }

    private void executePowerUp() {
        myPowerUp.doThePowerUp(myPlayer, myBall, myPlatform);
        myLevel.getRoot().getChildren().remove(myPowerUp.getThisPowerUp());
        myPowerUp = null;
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
    public void checkBottomBoundary(Circle ball){
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
    public void resetPaddleAndBall(){
        myBall.getThisBall().setCenterX(SIZE/2);
        myBall.getThisBall().setCenterY(SIZE/2);
        myPlatform.getThisPlatform().setX(SIZE/2 - PLATFORM_SIZE/2);
    }

    private Block chooseRandomBlock(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, startBlockList.size());
        return startBlockList.get(randomNum);
    }
    public List<Block> getBlockList(){
        return startBlockList;
    }

    public Level getMyLevel(){
        return myLevel;
    }
    public Player getMyPlayer(){
        return myPlayer;
    }

    public void checkScore() throws IOException {
        if(highScore== ""){
            return;
        }
        if(myPlayer.getScore() > Integer.parseInt(highScore)){
            highScore = Integer.toString(myPlayer.getScore());
            NEW_HIGH_SCORE.setVisible(true);
            writeHighScoreToFile();

        }
    }

    private void writeHighScoreToFile() throws IOException {
        File scoreFile = new File(HIGHSCORE_PATH);
        if(!scoreFile.exists()){
            scoreFile.createNewFile();
        }

        FileWriter writeFile;
        BufferedWriter writer = null;
        writeFile = new FileWriter(scoreFile);
        writer = new BufferedWriter(writeFile);
        writer.write(this.highScore);
        if(writer != null){
            writer.close();
        }
    }

    public static Text getHighScoreText() {
        return highScoreText;
    }

    public static Text getNewHighScore(){
        return NEW_HIGH_SCORE;
    }
    public static String getHighScore() {
        FileReader readFile;
        BufferedReader reader = null;
        try {
            readFile = new FileReader(HIGHSCORE_PATH);
            reader = new BufferedReader(readFile);
            return reader.readLine();
        }
        catch (Exception e){
            return ZERO_STRING;
        }
        finally
        {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e){
                return ZERO_STRING;
            }

        }
    }
    public void destroyFirstBlock(){ //TODO this doesnt work
        if(!currentBlockList.isEmpty()){
            Block deletedBlock = currentBlockList.get(0);
            while(deletedBlock.getNumberOfHitsLeft()>0){
            deletedBlock.updateBlocks(myBall);
            }
            myLevel.getRoot().getChildren().remove(deletedBlock);
            currentBlockList.remove(deletedBlock);
        }
    }

    private void jumpLevel(int newLevel) throws FileNotFoundException {
        if(newLevel>FINAL_LEVEL){
            return;
        }
        animation.pause();
        generateNextLevel(newLevel);
    }
    public void handleKeyInput (KeyCode code) throws FileNotFoundException {
        switch (code) {
            case LEFT -> myPlatform.getThisPlatform().setX(myPlatform.getThisPlatform().getX() - PLATFORM_SPEED);
            case RIGHT -> myPlatform.getThisPlatform().setX(myPlatform.getThisPlatform().getX() + PLATFORM_SPEED);
            case SPACE -> pauseGame();
            case R -> resetPaddleAndBall();
            case L -> myPlayer.addLife();
            case P -> createPowerUp(chooseRandomBlock());
            case D -> destroyFirstBlock();
            case DIGIT1 -> jumpLevel(LEVEL_ONE_INT);
            case DIGIT2 -> jumpLevel(LEVEL_TWO_INT);
            case DIGIT3-> jumpLevel(LEVEL_THREE_INT);
            case S -> myPlayer.addScore();
            case W -> myPlatform.grow();
        }
    }
    public Platform getMyPlatform() {
        return myPlatform;
    }

    public List<Block> getCurrentBlockList(){
        return currentBlockList;
    }

}
