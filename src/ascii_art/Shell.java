package ascii_art;

import ascii_output.HtmlAsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import image.*;
import image_char_matching.SubImgCharMatcher;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ascii_art.Constants.*;

/**
 * Greetings, esteemed ladies and gentlemen, to the grand spectacle that is the Shell class! This marvellous
 * entity orchestrates the ballet of interactions between our humble users and the wondrous world of
 * ASCII art conversion. Tally-ho! Let us embark upon this magnificent journey with grace and mirth.
 */
public class Shell {

    private static final HtmlAsciiOutput HTML = new HtmlAsciiOutput(HTML_OUT_FILE, DEFAULT_FONT);
    private static final ConsoleAsciiOutput CONSOLE = new ConsoleAsciiOutput();
    private static boolean printToConsole = DEFAULT_PRINT_TO_CONSOLE;
    private static SubImgCharMatcher subImgCharMatcher;
    private static ImageSegmenter imageSegmenter;
    private static AsciiArtAlgorithm asciiArtAlgorithm;

    /**
     * Huzzah! The curtain rises on our grand performance,
     * as we venture forth into the realms of ASCII art conversion!
     */
    public static void run() {
        initAsciiAlg();
        while (processInput(askInput()));
    }

    /**
     * A courteous inquiry to prompt the user for their noble command.
     *
     * @return The user's command as a String, ready to be processed with utmost gentility!
     */
    private static String askInput() {
        System.out.print(INPUT_MESSAGE);
        return KeyboardInput.readLine();
    }

    /**
     * A gallant handler to process the user's command with poise and dignity.
     *
     * @param input The user's command to be processed.
     * @return True if the quest continues, false if the user hath chosen to depart.
     */
    private static boolean processInput(String input) {
        String command = input.split(" ")[0];
        String arg = input.split(" ").length == 2 ? input.split(" ")[1] : "";
        // Let's embark on a grand journey through the realm of commands,
        // with wit and charm as our loyal companions!
        switch (command) {
            case "chars":
                printCharSet();        // Ah, the gallant display of characters,
                break;                // like a grand parade of noble knights!
            case "add":
                addChar(arg);          // To the forge! We shall add characters to our noble collection
                break;                // with the determination of a blacksmith at his anvil!
            case "remove":
                removeChar(arg);       // Away with ye! We shall banish characters from our midst
                break;                // with the swiftness of a gale across the moors!
            case "res":
                changeResolution(arg); // Up, up, and away! Let us alter the resolution,
                break;                // ascending to greater heights or descending to verdant valleys!
            case "image":
                changeImage(arg);      // Ah, the image! Let us change it,
                break;                // as one might swap out a tapestry in the halls of Windsor Castle!
            case "output":
                changeOutput(arg);      // Console or HTML? A choice as weighty as the crown upon
                break;                 // the monarch's head, yet made with the ease of a summer breeze!
            case "asciiArt":
                generateAsciiArt();     // Hark! The masterpiece! Let us generate ASCII art,
                break;                 // as if Michelangelo himself were painting the Sistine Chapel ceiling!
            default:
                if (input.equals("exit")) {
                    // Fare thee well, dear user, should you choose to depart from this noble quest!
                    return false;
                } else { // Alas! A command lost in the fog of misunderstanding.
                    System.out.println(COMMAND_FAIL_MESSAGE);
                }
        }
        // Onward, comrades, for there are still commands to be issued and ASCII art to be wrought!
        return true;
    }

    /**
     * Behold! Let us initialize the grand algorithm of ASCII art, a marvel of engineering and creativity!
     */
    private static void initAsciiAlg() {
        subImgCharMatcher = new SubImgCharMatcher(DEFAULT_CHAR_SET);
        imageSegmenter = new ImageSegmenter(DEFAULT_RESOLUTION);
        imageSegmenter.setImagePath(DEFAULT_IMAGE_PATH);
        asciiArtAlgorithm =
                new AsciiArtAlgorithm(imageSegmenter, new ImageBrightnessCalculator(), subImgCharMatcher);
    }

    /**
     * By Saint George! Let us display the array of characters with all the majesty of a royal procession!
     */
    private static void printCharSet() {
        for (char c : subImgCharMatcher.getCharset()) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /**
     * By the beard of Neptune! To the forge! We shall add characters to our noble collection.
     *
     * @param arg The character to be added, or a range of characters in the format 'A-B'.
     */
    private static void addChar(String arg) {
        if (arg.equals("all")) {          // A grand decree to add all characters to our collection!
            for (char c = ASCII_START; c < ASCII_END; c++) {
                subImgCharMatcher.addChar(c);
            }
        } else if (arg.equals("space")) { // A humble request to add a single space character to our ranks!
            subImgCharMatcher.addChar(' ');
        } else if (arg.length() == 1) {   // An individual character, a jewel to be added to the crown!
            if (charIsValid(arg.charAt(0))) {
                subImgCharMatcher.addChar(arg.charAt(0));
            } else { // Alas! The character does not belong to the realm of ASCII.
                System.out.println(ADD_FAIL_MESSAGE);
            }
        } else if (arg.length() == 3) {   // A noble range of characters!
            if (arg.charAt(1) == '-') {
                char first = (char) Math.min(arg.charAt(0), arg.charAt(2));
                char second = (char) Math.max(arg.charAt(0), arg.charAt(2));
                if (charIsValid(first) && charIsValid(second)) {
                    for (char c = first; c <= second; c++) {
                        subImgCharMatcher.addChar(c);
                    }
                } else { // Alas! The character does not belong to the realm of ASCII.
                    System.out.println(ADD_FAIL_MESSAGE);
                }
            }
        } else { // A cry of despair for commands gone astray!
            System.out.println(ADD_FAIL_MESSAGE);
        }
    }

    /**
     * Away with ye! Let us banish characters from our midst with the swiftness of a gale across the moors!
     *
     * @param arg The character to be removed, or a range of characters in the format 'A-B'.
     */
    private static void removeChar(String arg) {
        if (arg.equals("all")) {          // A grand decree to remove all characters from our collection!
            for (char c = ASCII_START; c < ASCII_END; c++) {
                subImgCharMatcher.removeChar(c);
            }
        } else if (arg.equals("space")) { // A request to remove a single space character from our ranks!
            subImgCharMatcher.removeChar(' ');
        } else if (arg.length() == 1) {   // An individual character, to be cast into the abyss!
            if (charIsValid(arg.charAt(0))) {
                subImgCharMatcher.removeChar(arg.charAt(0));
            } else {  // Alas! The character does not belong to the realm of ASCII.
                System.out.println(REMOVE_FAIL_MESSAGE);
            }
        } else if (arg.length() == 3) {   // A noble range of characters, to be vanquished from our midst!
            if (arg.charAt(1) == '-') {
                char first = (char) Math.min(arg.charAt(0), arg.charAt(2));
                char second = (char) Math.max(arg.charAt(0), arg.charAt(2));
                if (charIsValid(first) && charIsValid(second)) {
                    for (char c = first; c <= second; c++) {
                        subImgCharMatcher.removeChar(c);
                    }
                } else { // Alas! The character does not belong to the realm of ASCII.
                    System.out.println(REMOVE_FAIL_MESSAGE);
                }
            }
        } else { // A lament for characters lost in the mists of syntax.
            System.out.println(REMOVE_FAIL_MESSAGE);
        }
    }

    private static boolean charIsValid(char c) {
        return c >= ASCII_START && c < ASCII_END;
    }

    /**
     * A courageous adventurer, setting forth to change the resolution of our noble image.
     *
     * @param arg The brave command, whether to scale 'up' for finer detail or 'down' for broader strokes.
     */
    private static void changeResolution(String arg) {
        if (!arg.equals("up") && !arg.equals("down")) {
            System.out.println(RES_FORMAT_FAIL_MESSAGE);
            return;
        }

        try {
            BufferedImage im = ImageIO.read(new File(imageSegmenter.getImagePath()));
            int width = im.getWidth();
            int height = im.getHeight();
            int currentRes = imageSegmenter.getResolution();

            if (arg.equals("up") && currentRes * 2 <= width) {
                // Ascend, brave knights, to finer detail and sharper edges!
                imageSegmenter.setResolution(currentRes * 2);
            } else if (arg.equals("down") && currentRes / 2 >= Math.max(1, width / height)) {
                // Descend, noble souls, to broader strokes and grander vistas!
                imageSegmenter.setResolution(currentRes / 2);
            } else { // A lament for resolutions unattainable, like stars beyond our grasp.
                System.out.println(RES_EXCEED_MESSAGE);
            }

        } catch (IOException e) { // Alas! A failure to load the image, a setback in our noble quest.
            System.out.println(IMAGE_FAIL_MESSAGE);
        }
    }

    /**
     * Fear not, brave traveller, for we shall change the image with the grace
     * and poise of a swan upon the lake!
     *
     * @param arg The path to the new image, a beacon of hope in the darkness of the file system.
     */
    private static void changeImage(String arg) {
        try {   // Let us gaze upon the image, like a knight surveying the battlefield before battle.
            ImageIO.read(new File(arg));
        } catch (IOException e) { // Alas! A failure to load the image, a setback in our noble quest.
            System.out.println(IMAGE_FAIL_MESSAGE);
            return;
        }
        imageSegmenter.setImagePath(arg);
    }

    /**
     * Console or HTML? A choice as weighty as the crown upon the monarch's head,
     * yet made with the ease of a summer breeze!
     *
     * @param arg The desired output format, a realm of infinite possibilities.
     */
    private static void changeOutput(String arg) {
        if (arg.equals("console")) {        // Console, the realm of text and characters!
            printToConsole = true;         // A grand display of ASCII art!
        } else if (arg.equals("html")) {    // HTML, the language of the web! Let our ASCII art shine
            printToConsole = false;        // like the Crown Jewels upon a royal sceptre!
        } else { // Oh dear! Thy command doth not align with the realms of possibility.
            System.out.println(OUTPUT_FAIL_MESSAGE);
        }
    }

    /**
     * Hark! The masterpiece! Let us generate ASCII art,
     * as if Michelangelo himself were painting the Sistine Chapel ceiling!
     */
    private static void generateAsciiArt() {
        if (subImgCharMatcher.getCharset().length == 0) { // Oh woe! The canvas is barren,
            System.out.println(CHAR_SET_EMPTY_MESSAGE);  // devoid of characters to bring forth the art!
            return;
        }
        try {
            char[][] asciiArt = asciiArtAlgorithm.run();
            if (printToConsole) {  // To the console, where ASCII art dances like sprites in the moonlight!
                CONSOLE.out(asciiArt);
            } else {               // To HTML, where ASCII art shall be enshrined for all eternity!
                HTML.out(asciiArt);
            }
        } catch (IOException e) {  // Alas! A failure to load the image, a setback in our noble quest.
            System.out.println(IMAGE_FAIL_MESSAGE);
        }
    }

    /**
     * A noble knight emerges! Let us embark upon our grand adventure with courage and fortitude!
     *
     * @param args Command-line arguments, the heralds of our journey.
     */
    public static void main(String[] args) {
        run();
    }
}
