package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball {

    private Circle thisBall;
    private int LEVEL;
    private int BALL_SPEED_X;
    private int BALL_SPEED_Y;

    private final Paint BALL_COLOR = Color.BLACK;
    private int size = 30;
    private static final int SCREEN_SIZE = 600;   //same variable as SIZE in game
    private static final String BALL_ID = "ball";
    private static final int BIGBALL_RAD_SIZE = 20;

    public Ball(int level){
        LEVEL = level;
        createBallFromLevel(level);
    }

    private void createBallFromLevel(int level){
        thisBall = new Circle(SCREEN_SIZE / 2, SCREEN_SIZE / 2, size / 2);
        thisBall.setFill(BALL_COLOR);
        thisBall.setId(BALL_ID);

        BALL_SPEED_X = 70 + 10*(level);
        BALL_SPEED_Y = -1*(200 + 10*(level));
    }

    public void flipDirectionY(){
        BALL_SPEED_Y *= -1;
    }
    public void flipDirectionX(){
        BALL_SPEED_X *=-1;
    }
    public double getCenterX(){
        return thisBall.getCenterX();
    }
    public double getCenterY(){
        return thisBall.getCenterY();
    }

    public int getRadius(){
        return size / 2;
    }
    public void setRadius(int newRad){
          size = newRad * 2;
    }

    public int getSpeedX(){
        return BALL_SPEED_X;
    }
    public int getSpeedY(){
        return BALL_SPEED_Y;
    }

    public void setSpeedY(int newSpeed){
        BALL_SPEED_Y = newSpeed;
    }
    public void setSpeedX(int newSpeed){
        BALL_SPEED_X = newSpeed;
    }

    public void setCenterY(int newY){
        BALL_SPEED_Y = newY;
    }
    public void setCenterX(int newX){
        BALL_SPEED_X = newX;
    }


    public Circle getThisBall(){
        return thisBall;
    }
    public void bigBall(){
        thisBall.setRadius(BIGBALL_RAD_SIZE);
    }


    public void setThisBall(Circle newBall){
        thisBall = newBall;
    }
    public int getLEVEL(){
        return LEVEL;
    }
}
