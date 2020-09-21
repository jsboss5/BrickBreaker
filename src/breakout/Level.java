package breakout;

import breakout.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.util.List;

public class Level {
    private static final int DEFAUL_LEVEL = 1;



    private Scene currentScene;
    private int currentLevel;

    private List<Block> blockList;
    private Ball myBall;
    private Platform myPlatform;
    private Player myPlayer;
    private Text scoreText;
    private Text levelText;
    private Text livesText;

    public Level(int level, Player player) throws FileNotFoundException {
        myPlayer = player;
        currentLevel = level;
        currentScene = setUpScene();
    }

    public Level(Player player) throws FileNotFoundException {
        this(DEFAUL_LEVEL, player);
    }

    private Scene setUpScene() throws FileNotFoundException {
        Group root = new Group();

        System.out.println(currentLevel);

        BlockMaker blockSetup = new BlockMaker(currentLevel);
        //it makes sense that ball, platfor, blocklist are all inherent to the level and change with new level

        myBall = new Ball(currentLevel);
        myPlatform = new Platform(currentLevel);
        blockList = blockSetup.createBlocks(root);  //Todo - add level as a paramter for new blocklist

        // order added to the group is the order in which they are drawn (so last one is on top)
        scoreText = new Text(10, 20, "Score: " + myPlayer.getScore());
        levelText = new Text(250, 20, "LEVEL " + currentLevel);
        livesText = new Text(500, 20, "Lives left: " + myPlayer.getLivesLeft());

        // respond to input
        return addToRoot(root);
    }

    private Scene addToRoot(Group root){
        root.getChildren().add(myBall.getThisBall());
        root.getChildren().add(scoreText);
        root.getChildren().add(levelText);
        root.getChildren().add(livesText);
        root.getChildren().add(myPlatform.getThisPlatform());
        // create a place to see the shapes
        Scene scene = new Scene(root, Game.SIZE, Game.SIZE, Game.BACKGROUND);
        return scene;
    }

    public Scene getScene(){
        return currentScene;
    }

    public Ball getMyBall(){
        return myBall;
    }

    public Platform getMyPlatform(){
        return myPlatform;
    }

    public List<Block> getBlockList(){
        return blockList;
    }

    public Text getScoreText() {
        return scoreText;
    }
    public Text getLevelText(){
        return levelText;
    }
    public Text getLivesText(){
        return livesText;
    }

    public int getCurrentLevel(){
        return currentLevel;
    }
}
