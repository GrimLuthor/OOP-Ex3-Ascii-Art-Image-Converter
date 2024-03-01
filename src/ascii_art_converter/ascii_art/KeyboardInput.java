package ascii_art_converter.ascii_art;

import java.util.Scanner;

/**
 * Oi there, mate! Welcome to the KeyboardInput class, your one-stop-shop for grabbing user input like a
 * bloomin' pro. This cheeky little class handles all the nitty-gritty of reading user input from the
 * keyboard, so you can focus on creating your ASCII masterpieces without breakin' a sweat.
 * Cheers,
 * Your loyal documentation scribe
 */
class KeyboardInput {
    private static KeyboardInput keyboardInputObject = null;
    private final Scanner scanner;

    private KeyboardInput() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * It's the legendary 'getObject()' method. This little gem conjures up the one-and-only KeyboardInput
     * object, ensuring that you've got the goods when it comes to capturing user input.
     *
     * @return The glorious KeyboardInput object, fit for all your input-snagging needs.
     */
    public static KeyboardInput getObject() {
        if (KeyboardInput.keyboardInputObject == null) {
            KeyboardInput.keyboardInputObject = new KeyboardInput();
        }
        return KeyboardInput.keyboardInputObject;
    }

    /**
     * Hold onto your hats, folks! 'readLine()' is the method you've been waiting for. It swoops in like a
     * knight in shining armor, ready to snatch up whatever the user types in. Once it's got hold of that
     * text, it trims off any excess whitespace, leaving you with a tidy string fit for a king.
     *
     * @return A string containing the user's input, all nice and trimmed for your convenience.
     */
    public static String readLine() {
        return KeyboardInput.getObject().scanner.nextLine().trim();
    }
}
