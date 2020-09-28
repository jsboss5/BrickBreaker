package breakout;



public class Player {
    private int livesLeft;
    private int score;
    private static final int SCORE_INCREMENT = 10;
    private static final int OG_SCORE = 0;
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
        score = OG_SCORE;
    }
    public void addScore(){
        score += SCORE_INCREMENT;
    }
}
