package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

public abstract class Screen {

    private Scene myScene;
    private Button myButton;
    private static final int VERTICAL_OFFSET = 20;
    private static final int BUTTON_SIZE = 90;
    private static final int BUTTON_TEXT_SIZE = 15;
    private static final String FONT = "verdana";

    public void createNewScreen(String buttonMessage, List<Text> textArray, Paint backgroundPaint){
        myButton = setSingleButton(buttonMessage);
        Group root = new Group();
        root.getChildren().add(myButton);
        for(Text text: textArray){
            root.getChildren().add(text);
        }
        myScene = new Scene(root, Game.SIZE, Game.SIZE, backgroundPaint);

    }

    public Text createTextAboveButton(String textAboveButton) {
        Text text = new Text();
        text.setText(textAboveButton);
        text.setFont(Font.font(FONT, BUTTON_TEXT_SIZE ));

        double width = text.getLayoutBounds().getWidth();
        text.setX(Game.SIZE/2 - width/2);
        text.setY(Game.SIZE/2 - VERTICAL_OFFSET);
        return text;
    }


    public Button setSingleButton(String buttonMessage) {
        Button tempButton = new Button(buttonMessage);
        double buttonWidth = tempButton.getLayoutBounds().getCenterX();
        System.out.println(buttonWidth);
        tempButton.setLayoutX(Game.SIZE/2 - BUTTON_SIZE/2);
        tempButton.setLayoutY(Game.SIZE/2);
        return tempButton;
    }

    public abstract void createScreen();

    public Scene getMyScene(){
        return myScene;
    }

    public Button getMyButton(){
        return myButton;
    }


}
