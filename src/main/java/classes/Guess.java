package classes;

import enums.LetterColors;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author casey
 */
public class Guess {

    private char firstLetterChar, secondLetterChar, thirdLetterChar, fourthLetterChar, fifthLetterChar;

    private LetterColors firstLetterColor, secondLetterColor, thirdLetterColor, fourthLetterColor, fifthLetterColor;


    public Guess() {
    }

    public Guess(String word,
                 LetterColors firstLetterColor,
                 LetterColors secondLetterColor,
                 LetterColors thirdLetterColor,
                 LetterColors fourthLetterColor,
                 LetterColors fifthLetterColor) {
        char[] wordSplit = word.toCharArray();
        this.firstLetterChar = wordSplit[0];
        this.secondLetterChar = wordSplit[1];
        this.thirdLetterChar = wordSplit[2];
        this.fourthLetterChar = wordSplit[3];
        this.fifthLetterChar = wordSplit[4];
        this.firstLetterColor = firstLetterColor;
        this.secondLetterColor = secondLetterColor;
        this.thirdLetterColor = thirdLetterColor;
        this.fourthLetterColor = fourthLetterColor;
        this.fifthLetterColor = fifthLetterColor;
    }


    public HashMap<Integer, Character> allYellowLetters() {
        HashMap<Integer, Character> arr = new HashMap<>();

        if (firstLetterColor == LetterColors.YELLOW) {
            arr.put(0, firstLetterChar);
        }
        if (secondLetterColor == LetterColors.YELLOW) {
            arr.put(1, secondLetterChar);
        }
        if (thirdLetterColor == LetterColors.YELLOW) {
            arr.put(2, thirdLetterChar);
        }
        if (fourthLetterColor == LetterColors.YELLOW) {
            arr.put(3, fourthLetterChar);
        }
        if (fifthLetterColor == LetterColors.YELLOW) {
            arr.put(4, fifthLetterChar);
        }

        return arr;

    }

    public HashMap<Integer, Character> allGreenLetters() {
        HashMap<Integer, Character> arr = new HashMap<>();

        if (firstLetterColor == LetterColors.GREEN) {
            arr.put(0, firstLetterChar);
        }
        if (secondLetterColor == LetterColors.GREEN) {
            arr.put(1, secondLetterChar);
        }
        if (thirdLetterColor == LetterColors.GREEN) {
            arr.put(2, thirdLetterChar);
        }
        if (fourthLetterColor == LetterColors.GREEN) {
            arr.put(3, fourthLetterChar);
        }
        if (fifthLetterColor == LetterColors.GREEN) {
            arr.put(4, fifthLetterChar);
        }

        return arr;

    }

    //Position of Gray Letter Tiles is Not Important. That is why this method
    //returns an ArrayList as opposed to a HashMap

    public ArrayList<Character> allGrayLetters() {
        ArrayList<Character> arr = new ArrayList<>();
        if (firstLetterColor == LetterColors.GRAY) {
            arr.add(firstLetterChar);
        }
        if (secondLetterColor == LetterColors.GRAY) {
            arr.add(secondLetterChar);
        }
        if (thirdLetterColor == LetterColors.GRAY) {
            arr.add(thirdLetterChar);
        }
        if (fourthLetterColor == LetterColors.GRAY) {
            arr.add(fourthLetterChar);
        }
        if (fifthLetterColor == LetterColors.GRAY) {
            arr.add(fifthLetterChar);
        }

        return arr;
    }

    public String getGuessAsWord() {
        String word = String.valueOf(firstLetterChar) +
                secondLetterChar +
                thirdLetterChar +
                fourthLetterChar +
                fifthLetterChar;
        return word;
    }

    public char getFirstLetterChar() {
        return firstLetterChar;
    }

    public void setFirstLetterChar(char firstLetterChar) {
        this.firstLetterChar = firstLetterChar;
    }

    public char getSecondLetterChar() {
        return secondLetterChar;
    }

    public void setSecondLetterChar(char secondLetterChar) {
        this.secondLetterChar = secondLetterChar;
    }

    public char getThirdLetterChar() {
        return thirdLetterChar;
    }

    public void setThirdLetterChar(char thirdLetterChar) {
        this.thirdLetterChar = thirdLetterChar;
    }

    public char getFourthLetterChar() {
        return fourthLetterChar;
    }

    public void setFourthLetterChar(char fourthLetterChar) {
        this.fourthLetterChar = fourthLetterChar;
    }

    public char getFifthLetterChar() {
        return fifthLetterChar;
    }

    public void setFifthLetterChar(char fifthLetterChar) {
        this.fifthLetterChar = fifthLetterChar;
    }

    public LetterColors getFirstLetterColor() {
        return firstLetterColor;
    }

    public void setFirstLetterColor(LetterColors firstLetterColor) {
        this.firstLetterColor = firstLetterColor;
    }

    public LetterColors getSecondLetterColor() {
        return secondLetterColor;
    }

    public void setSecondLetterColor(LetterColors secondLetterColor) {
        this.secondLetterColor = secondLetterColor;
    }

    public LetterColors getThirdLetterColor() {
        return thirdLetterColor;
    }

    public void setThirdLetterColor(LetterColors thirdLetterColor) {
        this.thirdLetterColor = thirdLetterColor;
    }

    public LetterColors getFourthLetterColor() {
        return fourthLetterColor;
    }

    public void setFourthLetterColor(LetterColors fourthLetterColor) {
        this.fourthLetterColor = fourthLetterColor;
    }

    public LetterColors getFifthLetterColor() {
        return fifthLetterColor;
    }

    public void setFifthLetterColor(LetterColors fifthLetterColor) {
        this.fifthLetterColor = fifthLetterColor;
    }


}
