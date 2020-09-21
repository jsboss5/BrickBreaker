package breakout.screens;

import breakout.Screen;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class WinningScreen extends Screen {
    private static final String GAME_OVER_MESSAGE = "CONGRATS, YOU WON";
    private static final String BUTTON_MESSAGE = "Play Again";
    private static final Paint BACKGROUND_PAINT = Color.AQUAMARINE;


    public WinningScreen(){
        createScreen();
    }

    public void createScreen(){
        super.createScreen(BUTTON_MESSAGE, GAME_OVER_MESSAGE, BACKGROUND_PAINT);
    }

}
