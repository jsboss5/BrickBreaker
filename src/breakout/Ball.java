package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball {

    private Circle thisBall;
    public static int LEVEL;
    public static int BALL_SPEED_X;
    public static int BALL_SPEED_Y;

    public static final Paint BALL_COLOR = Color.GREEN;
    public static final int BALL_SIZE = 30;
    public static final int SCREEN_SIZE = 600;   //same variable as SIZE in game



    public Ball(int level){
        LEVEL = level;
        createBallFromLevel(level);
    }

    private void createBallFromLevel(int level){
        thisBall = new Circle(SCREEN_SIZE / 2, SCREEN_SIZE / 2, BALL_SIZE / 2);
        thisBall.setFill(BALL_COLOR);
        thisBall.setId("ball");

        BALL_SPEED_X = 130 + 10*(level);
        BALL_SPEED_Y = -1*(130 + 10*(level));
    }

    public void flipDirectionY(){
        BALL_SPEED_Y *= -1;
    }
    public void flipDirectionX(){
        BALL_SPEED_X *=-1;
    }

    public int getSpeedX(){
        return BALL_SPEED_X;
    }
    public int getSpeedY(){
        return BALL_SPEED_Y;
    }

    public void changeSpeedY(int newSpeed){
        BALL_SPEED_Y = newSpeed;
    }
    public void changeSpeedX(int newSpeed){
        BALL_SPEED_X = newSpeed;
    }


    public Circle getThisBall(){
        return thisBall;
    }

}
