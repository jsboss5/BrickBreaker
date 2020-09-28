package breakout;
import javafx.scene.Group;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BlockMaker {
    final String LEVEL_PATH = "data/maps/level";
    final String TEST_PATH = "data/maps/testMaps/test";
    final String EXTENSION = ".txt";
    private Scanner input;
    //public static final int SIZE = 600;
    public static final int BLOCK_WIDTH = 100;
    public static final int BLOCK_HEIGHT = 20;
    public static final int BLOCK_ROWS = 18;
    public static final int FIRST_TEST_LEVEL=10;
    public static final int DEFAULT_LEVEL = 1;
    public static final String SPACE = " ";
    public BlockMaker() throws FileNotFoundException {
        input = new Scanner(new File(LEVEL_PATH + DEFAULT_LEVEL + EXTENSION));
    }

    public BlockMaker(int level) throws FileNotFoundException {
        String pathName;
        if(level< FIRST_TEST_LEVEL){
            pathName = LEVEL_PATH + level + EXTENSION;
        }
        else{
            pathName = TEST_PATH + level%10 + EXTENSION;
        }
        input = new Scanner(new File(pathName));
    }

    //TODO - might want to split adding to list and adding to root into different methods but for now this works
    public List<Block> createBlocks(javafx.scene.Group root){
        List<Block> blockList = new ArrayList<>();
        while(input.hasNextLine()){
            for(int y = 0; y < BLOCK_ROWS * BLOCK_HEIGHT; y += BLOCK_HEIGHT) {
                createBlockRow(root, blockList, y);
            }
        }

    return blockList;
    }

    private void createBlockRow(Group root, List<Block> blockList, int y) {
        String[] numbers = input.nextLine().split(SPACE);
        int i = 0;
        for (int x = 0; x < Game.SIZE; x += BLOCK_WIDTH) {
            if(Integer.parseInt(numbers[i])!=0) {

                addBlockToRootAndList(root, blockList, new Block(Integer.parseInt(numbers[i]), x, y));
            }
            i++;
        }
    }

    private void addBlockToRootAndList(Group root, List<Block> blockList, Block newBlock1) {
        Block newBlock = newBlock1;
        blockList.add(newBlock);
        root.getChildren().add(newBlock.getThisBlock());
    }
    //input = new Scanner(new File(LEVEL_PATH));
}
