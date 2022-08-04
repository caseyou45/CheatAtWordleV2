package cheatatwordle;

public enum WordOrderingStrategy {


    ALPHABETICAL("Alphabetical"),
    REVERSE_ALPHABETICAL("Reverse Alphabetical"),
    RANDOM("Random");


    private final String name;

    WordOrderingStrategy(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
