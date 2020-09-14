package breakout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BlockMaker {
    final String LEVEL_PATH = "src/breakout/maps/level";
    private Scanner input;
    public static final int SIZE = 600;
    public static final int BLOCK_WIDTH = 100;
    public static final int BLOCK_HEIGHT = 20;
    public static final int BLOCK_ROWS = 18;

    public BlockMaker() throws FileNotFoundException {
        input = new Scanner(new File(LEVEL_PATH + "1.txt"));
    }

    public BlockMaker(int level) throws FileNotFoundException {
        input = new Scanner(new File(LEVEL_PATH + level + ".txt"));
    }

    //TODO - might want to split adding to list and adding to root into different methods but for now this works
    public List<Block> createBlocks(javafx.scene.Group root){
        List<Block> blockList = new ArrayList<>();
        while(input.hasNextLine()){
            for(int y = 0; y < BLOCK_ROWS * BLOCK_HEIGHT; y += BLOCK_HEIGHT) {
                String[] numbers = input.nextLine().split(" ");
                int i = 0;
                for (int x = 0; x < SIZE; x += BLOCK_WIDTH) {
                    Block newBlock = new Block(Integer.parseInt(numbers[i]), x, y);
                    blockList.add(newBlock);
                    root.getChildren().add(newBlock.thisBlock);
                    i++;
                }
            }
        }

    return blockList;
    }
    //input = new Scanner(new File(LEVEL_PATH));
}
