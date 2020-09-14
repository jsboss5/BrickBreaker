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
    public Rectangle thisBlock;
    public static Paint BLOCK_COLOR;
    public static final Paint BLOCK_COLOR_1 = Color.GREEN;
    public static final Paint BLOCK_COLOR_2 = Color.GREENYELLOW;
    public static final Paint BLOCK_COLOR_3 = Color.YELLOW;
    public static final Paint BLOCK_COLOR_4 = Color.ORANGE;
    public static final Paint BLOCK_COLOR_5 = Color.RED;
    public HashMap<Integer, Paint> colorMap = new HashMap<>();

    public Block(int numberHits, int x, int y){
        numberOfHitsLeft = numberHits;
        colorMap.put(1, BLOCK_COLOR_1);
        colorMap.put(2, BLOCK_COLOR_2);
        colorMap.put(3, BLOCK_COLOR_3);
        colorMap.put(4, BLOCK_COLOR_4);
        colorMap.put(5, BLOCK_COLOR_5);
        BLOCK_COLOR = colorMap.get(numberHits);
        thisBlock = new Rectangle(x, y, 100, 20);
        thisBlock.setFill(BLOCK_COLOR);
        thisBlock.setId("block");
    }

    public void updateBlocks(){
        numberOfHitsLeft -- ;
        BLOCK_COLOR = colorMap.get(numberOfHitsLeft);
        thisBlock.setFill(BLOCK_COLOR);
    }




}
