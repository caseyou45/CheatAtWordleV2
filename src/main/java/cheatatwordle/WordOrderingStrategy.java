package cheatatwordle;

public enum WordOrderingStrategy {


    ALPHABETICAL("Alphabetical"),
    REVERSE_ALPHABETICAL("Reverse Alphabetical"),
    RANDOM("Random"),
    BY_LETTER_USAGE_IN_ENGLISH_LANG("By Letter Usage In English Lang");


    private final String name;

    WordOrderingStrategy(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
