package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Key {
    private Circle thisKey;
    private int LEVEL;
    private int KEY_POS_X = 500;
    private int KEY_POS_Y = 500;

    private final Paint KEY_COLOR = Color.GOLD;
    private int size = 10;
    private static final String KEY_ID = "key";

    public Key(int level){
        LEVEL = level;
        createKeyFromLevel(level);
    }

    private void createKeyFromLevel(int level){
        thisKey = new Circle(KEY_POS_X, KEY_POS_Y, size / 2);
        thisKey.setFill(KEY_COLOR);
        thisKey.setId(KEY_ID);
    }

    public Circle getThisKey(){
        return thisKey;
    }

    public int getRadius(){
        return size / 2;
    }
    public void setRadius(int newRad){
        size = newRad * 2;
    }

    public void setCenterY(int newY){
        KEY_POS_Y = newY;
    }
    public void setCenterX(int newX){
        KEY_POS_X = newX;
    }
}
