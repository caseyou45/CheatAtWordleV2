package enums;

public enum WordOrderingStrategy {


    ALPHABETICAL("Alphabetical"),
    REVERSE_ALPHABETICAL("Reverse Alphabetical"),
    RANDOM("Random"),
    BY_LETTER_USAGE_IN_ENGLISH_LANG("<html>By Letter Usage In The <br/> English Language</html>"),
    BY_LETTER_USAGE_IN_REMAINING_WORDS("<html>By Letter Usage In <br/> Remaining Words</html>"),

    REMOVE_AS_MANY_WORDS_AS_POSSIBLE("<html>To Remove As Many <br/> Words As Possible</html>");

    private final String name;

    WordOrderingStrategy(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
