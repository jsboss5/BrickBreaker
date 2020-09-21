package breakout.screens;

import breakout.Screen;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.prefs.BackingStoreException;

public class LevelFinishedScreen extends Screen {
    private static final String GAME_OVER_MESSAGE = "LEVEL COMPLETE";
    private static final String BUTTON_MESSAGE = "NEXT LEVEL";
    private static final Paint BACKGROUND_PAINT = Color.AQUAMARINE;

    public LevelFinishedScreen(){
        createScreen();
    }

    public void createScreen() {
        super.createScreen(BUTTON_MESSAGE,GAME_OVER_MESSAGE, BACKGROUND_PAINT);


    }

}
