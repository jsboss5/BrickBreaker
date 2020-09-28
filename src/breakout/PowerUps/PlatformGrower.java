package breakout.PowerUps;

import breakout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class PlatformGrower extends PowerUp {
    private final Paint PLATFORM_GROWER_COLOR = Color.GRAY;
    private static final String PLATFORM_GROWER_TEXT = "PG";
    private Circle thisPlatformGrower;
    public PlatformGrower(){
        setType(PLATFORM_GROWER_TEXT);
        setSpeed(this.getSpeed());
    }

    @Override
    public void createPowerUpAtPoint(int x, int y) {
        PlatformGrower pg = this;
        thisPlatformGrower = new Circle(x,y,this.getSize(), PLATFORM_GROWER_COLOR);
        setThisPowerUp(thisPlatformGrower);
        thisPlatformGrower.setId(PLATFORM_GROWER_TEXT);
        pg.setThisPowerUp(thisPlatformGrower);
    }

    @Override
    public void doThePowerUp(Player myPlayer, Ball myBall, Platform myPlatform) {
        myPlatform.grow();
    }

}