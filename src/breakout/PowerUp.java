package breakout;

import breakout.PowerUps.BallGrower;
import breakout.PowerUps.OneUp;
import breakout.PowerUps.PlatformGrower;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import java.util.concurrent.ThreadLocalRandom;

public abstract class PowerUp {
    private Circle thisPowerUp;
    private int speed = 70;
    private String type;
    private final int POWER_UP_SIZE = 10;
    private static final PowerUp[] listOfPossiblePowerUps = new PowerUp[]{new PlatformGrower(), new BallGrower(), new OneUp()};

    public static PowerUp chooseRandomPowerUP(){
        int randomInt = ThreadLocalRandom.current().nextInt(0, listOfPossiblePowerUps.length);
        PowerUp chosenPowerUp = listOfPossiblePowerUps[randomInt];
        return chosenPowerUp;
    }

    public Circle getThisPowerUp() {
        return thisPowerUp;
    }
    public int getSpeed(){
        return speed;
    }
    public void setSpeed(int newSpeed){
        speed = newSpeed;
    }
    public int getSize(){
        return POWER_UP_SIZE;
    }
    public String getType(){
        return type;
    }
    public void setType(String newType){
        type = newType;
    }
    public void setThisPowerUp(Circle newPowerUp){
        thisPowerUp = newPowerUp;
    }

    public abstract void createPowerUpAtPoint(int x, int y);
    public abstract void doThePowerUp(Player myPlayer, Ball myBall, Platform myPlatform);
}
