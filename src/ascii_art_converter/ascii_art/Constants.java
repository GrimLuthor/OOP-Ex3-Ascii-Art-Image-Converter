package ascii_art_converter.ascii_art;

/**
 * Constants used in the program.
 */
public class Constants {

    public static final char[] DEFAULT_CHAR_SET    = {'0','1','2','3','4','5','6','7','8','9'};
    public static final String OUTPUT_FAIL_MESSAGE = "Did not change output method due to incorrect format.";
    public static final String IMAGE_FAIL_MESSAGE  = "Did not execute due to problem with image file.";
    public static final String RES_MODIFY_FAIL_MESSAGE = "Did not change resolution due to exceeding " +
            "boundaries.";
    public static final String RES_FORMAT_FAIL_MESSAGE = "Did not change resolution due to incorrect format";
    public static final String REMOVE_FAIL_MESSAGE = "Did not remove due to incorrect format.";
    public static final String CHAR_SET_EMPTY_MESSAGE = "Did not execute. Charset is empty.";
    public static final String ADD_FAIL_MESSAGE    = "Did not add due to incorrect format.";
    public static final String DEFAULT_IMAGE_PATH  = "cat.jpeg";
    public static final String DEFAULT_FONT        = "Courier New";
    public static final String HTML_OUT_FILE       = "out.html";
    public static final String INPUT_MESSAGE       = ">>> ";
    public static final boolean DEFAULT_PRINT_TO_CONSOLE = true;
    public static final double GREEN_COEFFICIENT   = 0.7152;
    public static final double RED_COEFFICIENT     = 0.2126;
    public static final double BLUE_COEFFICIENT    = 0.0722;
    public static final int DEFAULT_RESOLUTION     = 128;
    public static final int RGB_MAX                = 255;
    public static final int ASCII_END              = 127;
    public static final int ASCII_START            = 32;
}
