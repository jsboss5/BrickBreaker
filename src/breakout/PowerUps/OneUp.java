package breakout.PowerUps;

import breakout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class OneUp extends PowerUp {
    private final Paint ONE_UP_COLOR = Color.SPRINGGREEN;
    private static final String ONE_UP_TEXT = "1 UP";
    private Circle thisOneUp;
    public OneUp(){
        setType(ONE_UP_TEXT);
        setSpeed(this.getSpeed());
        //setPowerUpColor(ONE_UP_COLOR);
    }

    public void createPowerUpAtPoint(int x, int y){
        OneUp ou = this;
        thisOneUp = new Circle(x,y,this.getSize(), ONE_UP_COLOR);
        setThisPowerUp(thisOneUp);
        thisOneUp.setId(ONE_UP_TEXT);
        ou.setThisPowerUp(thisOneUp);  //made this powerup
    }

    @Override
    public void doThePowerUp(Player myPlayer, Ball myBall, Platform myPlatform){
        myPlayer.addLife();
    }

}

