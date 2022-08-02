package classes;

/**
 *
 * @author casey wilson
 */
public class Word {

    private char firstLetter;
    private char secondLetter;
    private char thirdLetter;
    private char fourthLetter;
    private char fifthLetter;

    public Word() {
    }

    public Word(char firstLetter, char secondLetter, char thirdLetter, char fourthLetter, char fifthLetter) {
        this.firstLetter = firstLetter;
        this.secondLetter = secondLetter;
        this.thirdLetter = thirdLetter;
        this.fourthLetter = fourthLetter;
        this.fifthLetter = fifthLetter;
    }

    public Word(String word) {
        char[] wordSplit = word.toCharArray();
        this.firstLetter = wordSplit[0];
        this.secondLetter = wordSplit[2];
        this.thirdLetter = wordSplit[3];
        this.fourthLetter = wordSplit[4];
        this.fifthLetter = wordSplit[5];
    }

    public char getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(char firstLetter) {
        this.firstLetter = firstLetter;
    }

    public char getSecondLetter() {
        return secondLetter;
    }

    public void setSecondLetter(char secondLetter) {
        this.secondLetter = secondLetter;
    }

    public char getThirdLetter() {
        return thirdLetter;
    }

    public void setThirdLetter(char thirdLetter) {
        this.thirdLetter = thirdLetter;
    }

    public char getFourthLetter() {
        return fourthLetter;
    }

    public void setFourthLetter(char fourthLetter) {
        this.fourthLetter = fourthLetter;
    }

    public char getFifthLetter() {
        return fifthLetter;
    }

    public void setFifthLetter(char fifthLetter) {
        this.fifthLetter = fifthLetter;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstLetter);
        sb.append(secondLetter);
        sb.append(thirdLetter);
        sb.append(fourthLetter);
        sb.append(fifthLetter);
        return sb.toString();
    }

}
