package cheatatwordle;

/**
 * @author casey
 */
public enum LetterColors {
    GRAY(120, 124, 126),
    YELLOW(201, 180, 88),
    GREEN(106, 170, 100);

    private final int r;
    private final int g;
    private final int b;

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    LetterColors(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
}
