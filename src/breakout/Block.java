package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;

public class Block {
    private int numberOfHitsLeft;
    private int centerX;
    private int centerY;
    private Rectangle thisBlock;
    private static final Paint BLOCK_COLOR_1 = Color.GREEN;
    private static final Paint BLOCK_COLOR_2 = Color.GREENYELLOW;
    private static final Paint BLOCK_COLOR_3 = Color.YELLOW;
    private static final Paint BLOCK_COLOR_4 = Color.ORANGE;
    private static final Paint BLOCK_COLOR_5 = Color.RED;
    private static final String BLOCK_ID = "block";

    private HashMap<Integer, Paint> colorMap = new HashMap<>();

    private Paint BLOCK_COLOR;


    public Block(int numberHits, int x, int y){
        numberOfHitsLeft = numberHits;
        createColorMap();
        BLOCK_COLOR = colorMap.get(numberHits);
        centerX = x + BlockMaker.BLOCK_WIDTH / 2;
        centerY = y + BlockMaker.BLOCK_HEIGHT / 2;
        thisBlock = new Rectangle(x, y, BlockMaker.BLOCK_WIDTH, BlockMaker.BLOCK_HEIGHT);
        thisBlock.setFill(BLOCK_COLOR);
        thisBlock.setId(BLOCK_ID);
    }

    //todo - add update to make sure that if it hits 0 it actually deletes the block from the blockList
    public void updateBlocks(){
        numberOfHitsLeft -- ;
        BLOCK_COLOR = colorMap.get(numberOfHitsLeft);
        thisBlock.setFill(BLOCK_COLOR);
    }

    private void createColorMap(){
        colorMap.put(1, BLOCK_COLOR_1);
        colorMap.put(2, BLOCK_COLOR_2);
        colorMap.put(3, BLOCK_COLOR_3);
        colorMap.put(4, BLOCK_COLOR_4);
        colorMap.put(5, BLOCK_COLOR_5);
    }

    public Rectangle getThisBlock(){
        return thisBlock;
    }

    public int getNumberOfHitsLeft(){
        return numberOfHitsLeft;
    }

    public int getCenterX(){ return centerX;}
    public int getCenterY(){ return centerY;}


}
