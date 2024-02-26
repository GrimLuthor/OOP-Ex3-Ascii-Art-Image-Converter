package ascii_art_converter.ascii_art;

import ascii_art_converter.ascii_output.AsciiOutput;
import ascii_art_converter.ascii_output.ConsoleAsciiOutput;
import ascii_art_converter.ascii_output.HtmlAsciiOutput;
import ascii_art_converter.image.Image;
import ascii_art_converter.image.ImageBrightnessCalculctor;
import ascii_art_converter.image.ImageSegmenter;
import ascii_art_converter.image_char_matching.SubImgCharMatcher;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Shell {

    public static final AsciiOutput HTML_OUT = new HtmlAsciiOutput("out.html","Courier New");
    public static final AsciiOutput CONSOLE_OUT = new ConsoleAsciiOutput();
    public static char[] charset = {'0','1','2','3','4','5','6','7','8','9'};
    public static String imagePath = "cat.jpeg";
    public static boolean printToConsole = true;
    public static String input = "";
    public static int resolution = 128;
    public static ImageBrightnessCalculctor imageBrightnessCalculctor = new ImageBrightnessCalculctor();
    public static SubImgCharMatcher subImgCharMatcher = new SubImgCharMatcher(charset);
    public static ImageSegmenter imageSegmenter = new ImageSegmenter(resolution);
    public static AsciiArtAlgorithm asciiArtAlgorithm =
            new AsciiArtAlgorithm(imageSegmenter, imageBrightnessCalculctor, subImgCharMatcher);
    public static void run() throws IOException {

        askInput();
        while (processInput()) {
            askInput();
        }
    }
    private static void askInput() {
        System.out.print(">>> ");
        input = KeyboardInput.readLine();
    }

    private static boolean processInput() {
        if (input.split(" ").length > 2) {
            // TODO: throw exception
            return true;
        }
        String command = input.split(" ")[0];
        String arg = input.split(" ").length == 2 ? input.split(" ")[1] : "";
        switch (command) {
            case "exit":
                if(input.equals("exit")) {
                    return false;
                }
            case "chars":
                for (char c : subImgCharMatcher.getCharset()) {
                    System.out.print(c + " ");
                }
                System.out.println();
                break;
            case "add":
                if (!addChar(arg)) {
                    System.out.println("Did not add due to incorrect format.");
                }
                break;
            case "remove":
                if (!removeChar(arg)) {
                    System.out.println("Did not remove due to incorrect format.");
                }
                break;
            case "res":
                break;
            case "image":
                try {   // check if path is valid
                    ImageIO.read(new File(arg));
                } catch (IOException e) {
                    System.out.println("Did not execute due to problem with image file.");
                    break;
                }
                imagePath = arg;
                break;
            case "output":
                if (arg.equals("console")) {
                    printToConsole = true;
                }
                else if (arg.equals("html")) {
                    printToConsole = false;
                }
                else {
                    // TODO: throw exception
                }
                break;
            case "asciiArt":
                generateAsciiArt();
                break;
            default:
                // TODO: throw exception
        }
        return true;
    }

    private static void generateAsciiArt() {
        if (subImgCharMatcher.getCharset().length == 0) {
            System.out.println("Did not execute. Charset is empty.");
            return;
        }
        try {
            char[][] asciiArt = asciiArtAlgorithm.run(imagePath);
            if (printToConsole) {
                CONSOLE_OUT.out(asciiArt);
            }
            else {
                HTML_OUT.out(asciiArt);
            }
        } catch (IOException e) {
            System.out.println("Did not execute due to problem with image file.");
        }
    }

    private static boolean addChar(String arg){
        if (arg.equals("all")) {
            for (char c = 32; c < 127; c++) {
                subImgCharMatcher.addChar(c);
            }
        }
        if (arg.equals("space")) {
            subImgCharMatcher.addChar(' ');
        }
        if (arg.length() == 1) {
            if (charIsValid(arg.charAt(0))) {
                subImgCharMatcher.addChar(arg.charAt(0));
            }
            else {
                // TODO: throw exception
                return false;
            }
        }
        if (arg.length() == 3) {
            if (arg.charAt(1) == '-') {
                char first  = (char) Math.min(arg.charAt(0),arg.charAt(2));
                char second = (char) Math.max(arg.charAt(0),arg.charAt(2));
                if (charIsValid(first) && charIsValid(second)) {
                    for (char c = first; c <= second; c++) {
                        subImgCharMatcher.addChar(c);
                    }
                }
                else {
                    // TODO: throw exception
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean removeChar(String arg){
        if (arg.equals("all")) {
            for (char c = 32; c < 127; c++) {
                subImgCharMatcher.removeChar(c);
            }
        }
        if (arg.equals("space")) {
            subImgCharMatcher.removeChar(' ');
        }
        if (arg.length() == 1) {
            if (charIsValid(arg.charAt(0))) {
                subImgCharMatcher.removeChar(arg.charAt(0));
            }
            else {
                // TODO: throw exception
                return false;
            }
        }
        if (arg.length() == 3) {
            if (arg.charAt(1) == '-') {
                char first  = (char) Math.min(arg.charAt(0),arg.charAt(2));
                char second = (char) Math.max(arg.charAt(0),arg.charAt(2));
                if (charIsValid(first) && charIsValid(second)) {
                    for (char c = first; c <= second; c++) {
                        subImgCharMatcher.removeChar(c);
                    }
                }
                else {
                    // TODO: throw exception
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean charIsValid(char c) {
        return c >= 32 && c < 127;
    }
    public static void main(String[] args) throws IOException {
        run();
    }
}
