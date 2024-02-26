package ascii_art_converter.ascii_output;

/**
 * Output a 2D array of chars to the console.
 *
 * @author Dan Nirel
 */
public class ConsoleAsciiOutput implements AsciiOutput {
    @Override
    public void out(char[][] chars) {
        for (char[] aChar : chars) {
            for (char c : aChar) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }
}
