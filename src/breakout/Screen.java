package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public abstract class Screen {
   // Button myButton;
//    public Screen(){
//        createScreen();
//    }
    private Scene myScene;
    private Button myButton;
    private static final int VERTICAL_OFFSET = 50;


    public void createScreen(String buttonMessage, String gameOverMessage, Paint backgroundPaint){

        myButton = new Button(buttonMessage);
        myButton.setLayoutX(Game.SIZE/2 - 30);
        myButton.setLayoutY(Game.SIZE/2);
        Text text = new Text();
        text.setText(gameOverMessage);
        text.setX(Game.SIZE/2 - 30);
        text.setY(Game.SIZE/2 - VERTICAL_OFFSET);

        Group root = new Group();
        root.getChildren().add(text);
        root.getChildren().add(myButton);

        myScene = new Scene(root, Game.SIZE, Game.SIZE, backgroundPaint);
    }

    public abstract void createScreen();

    public Scene getMyScene(){
        return myScene;
    }

    public Button getMyButton(){
        return myButton;
    }


}
