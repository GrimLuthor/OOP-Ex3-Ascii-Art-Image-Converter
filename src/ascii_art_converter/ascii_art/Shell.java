package ascii_art_converter.ascii_art;

import ascii_art_converter.ascii_output.AsciiOutput;
import ascii_art_converter.ascii_output.ConsoleAsciiOutput;

import java.io.IOException;

public class Shell {

    public void run() {

    }

    public static void main(String[] args) throws IOException {

        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm("src/ascii_art_converter/examples/board.jpeg",
                2,new char[] {'m','o'});

        AsciiOutput consoleOut = new ConsoleAsciiOutput();
        consoleOut.out(asciiArtAlgorithm.run());
    }
}
