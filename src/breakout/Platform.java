package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Platform {
    private Rectangle thisPlatform;
    public static int thisLevel;
    public static int platformSpeed;

    public static final Paint PLATFORM_COLOR = Color.GRAY;
    public static int platformSize = 80;
    public static final int SCREEN_SIZE = 600;
    public static final int VERTICAL_OFFSET = 40;

    public Platform(int level){
        thisLevel = level;
        createPlatformFromLevel(thisLevel);
    }

    private void createPlatformFromLevel(int level){
        thisPlatform = new Rectangle(SCREEN_SIZE / 2 - platformSize / 2, SCREEN_SIZE - VERTICAL_OFFSET, platformSize - 10 * level, platformSize / 4);
        thisPlatform.setFill(PLATFORM_COLOR);
        thisPlatform.setId("platform");
        platformSpeed = 20 + (level * 10);
    }

    public void flipPlatDirection(){
        platformSpeed *= -1;
    }
    public int getPlatSpeed(){
        return platformSpeed;
    }
    public void changePlatSpeed(int newSpeed){
        platformSpeed = newSpeed;
    }

    public Rectangle getThisPlatform(){
        return thisPlatform;
    }
}
