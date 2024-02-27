package ascii_art_converter.ascii_art;

import ascii_art_converter.ascii_output.AsciiOutput;
import ascii_art_converter.ascii_output.ConsoleAsciiOutput;
import ascii_art_converter.ascii_output.HtmlAsciiOutput;
import ascii_art_converter.image.ImageBrightnessCalculator;
import ascii_art_converter.image.ImageSegmenter;
import ascii_art_converter.image_char_matching.SubImgCharMatcher;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static ascii_art_converter.ascii_art.Constants.*;

public class Shell {

    public static final AsciiOutput HTML = new HtmlAsciiOutput(HTML_OUT_FILE, DEFAULT_FONT);
    public static final AsciiOutput CONSOLE = new ConsoleAsciiOutput();
    public static boolean printToConsole = DEFAULT_PRINT_TO_CONSOLE;
    public static String imagePath = DEFAULT_IMAGE_PATH;
    public static int resolution = DEFAULT_RESOLUTION;
    public static SubImgCharMatcher subImgCharMatcher;
    public static AsciiArtAlgorithm asciiArtAlgorithm;

    public static void run() {
        initAsciiAlg();

        while (processInput(askInput()));
    }

    private static String askInput() {
        System.out.print(INPUT_MESSAGE);
        return KeyboardInput.readLine();
    }

    private static boolean processInput(String input) {
        String command = input.split(" ")[0];
        String arg = input.split(" ").length == 2 ? input.split(" ")[1] : "";
        switch (command) {
            case "chars":
                printCharSet();
                break;
            case "add":
                addChar(arg);
                break;
            case "remove":
                removeChar(arg);
                break;
            case "res":
                // TODO: add resolution change
                break;
            case "image":
                changeImage(arg);
                break;
            case "output":
                changeOutput(arg);
                break;
            case "asciiArt":
                generateAsciiArt();
                break;
            default:
                if (input.equals("exit")) {
                    return false;
                } else {
                    System.out.println("Did not execute due to incorrect format.");
                }
        }
        return true;
    }

    private static void initAsciiAlg() {
        subImgCharMatcher = new SubImgCharMatcher(DEFAULT_CHAR_SET);
        ImageBrightnessCalculator imageBrightnessCalculator = new ImageBrightnessCalculator();
        ImageSegmenter imageSegmenter = new ImageSegmenter(resolution);
        asciiArtAlgorithm =
                new AsciiArtAlgorithm(imageSegmenter, imageBrightnessCalculator, subImgCharMatcher);
    }

    private static void printCharSet() {
        for (char c : subImgCharMatcher.getCharset()) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    private static void addChar(String arg) {
        if (arg.equals("all")) {
            for (char c = ASCII_START; c < ASCII_END; c++) {
                subImgCharMatcher.addChar(c);
            }
        } else if (arg.equals("space")) {
            subImgCharMatcher.addChar(' ');
        } else if (arg.length() == 1) {
            if (charIsValid(arg.charAt(0))) {
                subImgCharMatcher.addChar(arg.charAt(0));
            } else {
                System.out.println(ADD_FAIL_MESSAGE);
            }
        } else if (arg.length() == 3) {
            if (arg.charAt(1) == '-') {
                char first = (char) Math.min(arg.charAt(0), arg.charAt(2));
                char second = (char) Math.max(arg.charAt(0), arg.charAt(2));
                if (charIsValid(first) && charIsValid(second)) {
                    for (char c = first; c <= second; c++) {
                        subImgCharMatcher.addChar(c);
                    }
                } else {
                    System.out.println(ADD_FAIL_MESSAGE);
                }
            }
        }
        else {
            System.out.println(ADD_FAIL_MESSAGE);
        }
    }

    private static void removeChar(String arg) {
        if (arg.equals("all")) {
            for (char c = ASCII_START; c < ASCII_END; c++) {
                subImgCharMatcher.removeChar(c);
            }
        } else if (arg.equals("space")) {
            subImgCharMatcher.removeChar(' ');
        } else if (arg.length() == 1) {
            if (charIsValid(arg.charAt(0))) {
                subImgCharMatcher.removeChar(arg.charAt(0));
            } else {
                System.out.println(REMOVE_FAIL_MESSAGE);
            }
        } else if (arg.length() == 3) {
            if (arg.charAt(1) == '-') {
                char first = (char) Math.min(arg.charAt(0), arg.charAt(2));
                char second = (char) Math.max(arg.charAt(0), arg.charAt(2));
                if (charIsValid(first) && charIsValid(second)) {
                    for (char c = first; c <= second; c++) {
                        subImgCharMatcher.removeChar(c);
                    }
                } else {
                    System.out.println(REMOVE_FAIL_MESSAGE);
                }
            }
        }
        else {
            System.out.println(REMOVE_FAIL_MESSAGE);
        }
    }

    private static boolean charIsValid(char c) {
        return c >= ASCII_START && c < ASCII_END;
    }

    private static void changeImage(String arg) {
        try {   // check if path is valid
            ImageIO.read(new File(arg));
        } catch (IOException e) {
            System.out.println(IMAGE_FAIL_MESSAGE);
            return;
        }
        imagePath = arg;
    }

    private static void changeOutput(String arg) {
        if (arg.equals("console")) {
            printToConsole = true;
        } else if (arg.equals("html")) {
            printToConsole = false;
        } else {
            System.out.println(OUTPUT_FAIL_MESSAGE);
        }
    }

    private static void generateAsciiArt() {
        if (subImgCharMatcher.getCharset().length == 0) {
            System.out.println(CHAR_SET_EMPTY_MESSAGE);
            return;
        }
        try {
            char[][] asciiArt = asciiArtAlgorithm.run(imagePath);
            if (printToConsole) {
                CONSOLE.out(asciiArt);
            } else {
                HTML.out(asciiArt);
            }
        } catch (IOException e) {
            System.out.println(IMAGE_FAIL_MESSAGE);
        }
    }

    public static void main(String[] args) {
        run();
    }
}
