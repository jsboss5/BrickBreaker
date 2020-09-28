package breakout.PowerUps;

import breakout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class BallGrower extends PowerUp {
    private final Paint BALL_GROWER_COLOR = Color.MEDIUMPURPLE;
    private static final String GROWBALL_TEXT = "BG";
    private Circle thisBallGrower;
    public BallGrower(){
        setType(GROWBALL_TEXT);
        setSpeed(this.getSpeed());
    }

    public void createPowerUpAtPoint(int x, int y){
        BallGrower bg = this;
        thisBallGrower = new Circle(x,y,this.getSize(), BALL_GROWER_COLOR);
        setThisPowerUp(thisBallGrower);
        thisBallGrower.setId(GROWBALL_TEXT);
        bg.setThisPowerUp(thisBallGrower);
    }

    @Override
    public void doThePowerUp(Player myPlayer, Ball myBall, Platform myPlatform) {
        myBall.bigBall();
    }

}

