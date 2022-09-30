import classes.Guess;
import enums.LetterColors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import logic.GameLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.assertEquals;

public class TestGuessClass {

    Guess guess;
    GameLogic gameLogic;

    @BeforeEach
    void setUp() {
        guess = new Guess();
        gameLogic = new GameLogic();
    }

    @Test
    @DisplayName("Guess object Constructor")
    public void TestGuessConstructor() {
        guess = new Guess("tests", LetterColors.YELLOW, LetterColors.GRAY, LetterColors.GREEN, LetterColors.YELLOW, LetterColors.GRAY);

        assertEquals("tests", guess.getGuessAsWord());
        assertEquals(LetterColors.YELLOW.toString(), guess.getFirstLetterColor().toString());
        assertEquals(LetterColors.GRAY.toString(), guess.getSecondLetterColor().toString());
        assertEquals(LetterColors.GREEN.toString(), guess.getThirdLetterColor().toString());
        assertEquals(LetterColors.YELLOW.toString(), guess.getFourthLetterColor().toString());
        assertEquals(LetterColors.GRAY.toString(), guess.getFifthLetterColor().toString());

    }

    @Test
    @DisplayName("Guess object Character Setters and Getters")
    public void TestGuessCharSetters() {
        guess.setFirstLetterChar('t');
        guess.setSecondLetterChar('e');
        guess.setThirdLetterChar('s');
        guess.setFourthLetterChar('t');
        guess.setFifthLetterChar('s');

        assertEquals((Character) 't', (Character) guess.getFirstLetterChar());
        assertEquals((Character) 'e', (Character) guess.getSecondLetterChar());
        assertEquals((Character) 's', (Character) guess.getThirdLetterChar());
        assertEquals((Character) 't', (Character) guess.getFourthLetterChar());
        assertEquals((Character) 's', (Character) guess.getFifthLetterChar());

    }

    @Test
    @DisplayName("Guess object Color Setters and Getters")
    public void TestGuessColorSettersAndGetters() {
        guess.setFirstLetterColor(LetterColors.YELLOW);
        guess.setSecondLetterColor(LetterColors.GRAY);
        guess.setThirdLetterColor(LetterColors.GREEN);
        guess.setFourthLetterColor(LetterColors.YELLOW);
        guess.setFifthLetterColor(LetterColors.GRAY);

        assertEquals(LetterColors.YELLOW.toStringForTest(), guess.getFirstLetterColor().toStringForTest());
        assertEquals(LetterColors.GRAY.toStringForTest(), guess.getSecondLetterColor().toStringForTest());
        assertEquals(LetterColors.GREEN.toStringForTest(), guess.getThirdLetterColor().toStringForTest());
        assertEquals(LetterColors.YELLOW.toStringForTest(), guess.getFourthLetterColor().toStringForTest());
        assertEquals(LetterColors.GRAY.toStringForTest(), guess.getFifthLetterColor().toStringForTest());


    }

    @Test
    @DisplayName("Guess All Color Letters Methods")
    public void TestAllColorLettersMethods() {
        guess = new Guess("tests", LetterColors.YELLOW, LetterColors.GRAY, LetterColors.GREEN, LetterColors.YELLOW, LetterColors.GRAY);

        assertEquals((Character) 't', guess.allYellowLetters().get(0));
        assertEquals((Character) 'e', guess.allGrayLetters().get(0));
        assertEquals((Character) 's', guess.allGreenLetters().get(2));
        assertEquals((Character) 't', guess.allYellowLetters().get(3));
        assertEquals((Character) 's', guess.allGrayLetters().get(1));


    }


}
