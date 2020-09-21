package breakout.screens;

import breakout.Game;
import breakout.Screen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class GameOverScreen extends Screen {
    private static final String GAME_OVER_MESSAGE = "GAME OVER";
    private static final String BUTTON_MESSAGE = "Play Again";
    private static final Paint BACKGROUND_PAINT = Color.AQUAMARINE;


    public GameOverScreen(){
        createScreen();
    }

    public void createScreen(){
       super.createScreen(BUTTON_MESSAGE, GAME_OVER_MESSAGE, BACKGROUND_PAINT);
    }

}
