
import classes.Guess;
import enums.LetterColors;
import logic.GameLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class TestGameLogic {

    GameLogic gameLogic;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic();
    }

    @Test
    @DisplayName("Constructor with Prep Methods")
    public void TestGuessConstructor() {

        ArrayList<String> allPreviousWords = gameLogic.getAllPreviousWords();
        ArrayList<String> allWords = gameLogic.getAllRemainingWords();
        int[] lettersByEngLangUsage = gameLogic.getLettersByEngUsage();
        int[] testLettersByEngLangUsage = {25, 10, 17, 15, 26, 9, 11, 12, 23, 2, 6, 18, 13, 20, 22, 14, 1, 24, 19, 21, 16, 5, 7, 4, 8, 3};
        int[] letterAmounts = gameLogic.getLetterAmounts();

        assertTrue(allPreviousWords.size() > 400, "All previous words needs to be an ArrayList of at least 400 words");
        assertTrue(allWords.size() > 12600, "All words needs to be an ArrayList of at least 12600 words");
        assertEquals(allWords.size(), gameLogic.getWordsRemainingCount(), "All words need to be have loaded from file to play");

        for (int i = 0; i < lettersByEngLangUsage.length; i++) {
            assertEquals(lettersByEngLangUsage[i], testLettersByEngLangUsage[i],
                    "All letters by Eng. Lang usage need to have been loaded from file. " +
                            "The file failed at letter " + lettersByEngLangUsage[i]);
        }

        for (int i = 0; i < letterAmounts.length; i++) {
            assertTrue(letterAmounts[i] > 1, "Amount of letters in each words needs to have been tallied. Process failed around letter " + letterAmounts[i]);
        }


    }
}
