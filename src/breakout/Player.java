package breakout;


public class Player {
    private int livesLeft;
    private int score;

    public Player(){
        livesLeft = 3;
        score = 0;
    }

    public int getScore(){
        return score;
    }
    public int getLivesLeft(){
        return livesLeft;
    }

    public void addLife(){
        livesLeft++;
    }
    public void loseLife(){
        livesLeft--;
    }
    public void resetScore(){
        score = 0;
    }
    public void addScore(){
        score += 10;
    }
}
