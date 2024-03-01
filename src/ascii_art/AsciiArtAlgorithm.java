package ascii_art;

import image.ImageBrightnessCalculator;
import image.ImageSegmenter;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

/**
 * 'Ello, mate! 'Ere be the AsciiArtAlgorithm, a proper clever algorithm for convertin' images to ASCII art.
 * She's equipped with a grand ImageSegmenter, a splendid ImageBrightnessCalculator,
 * and a mighty SubImgCharMatcher. Quite the trio, ain't it?
 */
public class AsciiArtAlgorithm {

    private final ImageSegmenter imageSegmenter;
    private final ImageBrightnessCalculator imageBrightnessCalculator;
    private final SubImgCharMatcher subImgCharMatcher;

    /**
     * Ah, what a wondrous constructor it initializes this grand algorithm with the necessary tools.
     * @param imageSegmenter The image segmenter used to break down images into smaller parts.
     * @param imageBrightnessCalculator The brightness calculator computes brightness values for each segment.
     * @param subImgCharMatcher The sub-image character matcher that matches characters to image brightness.
     */
    public AsciiArtAlgorithm(ImageSegmenter imageSegmenter,
                             ImageBrightnessCalculator imageBrightnessCalculator,
                             SubImgCharMatcher subImgCharMatcher) {

        this.imageSegmenter = imageSegmenter;
        this.imageBrightnessCalculator = imageBrightnessCalculator;
        this.subImgCharMatcher = subImgCharMatcher;
    }

    /**
     * This function runs the entire ASCII art conversion process with utmost finesse!
     * @return A stunning 2D array of characters representing the ASCII art.
     * @throws IOException Thrown if there's any trouble reading the image file.
     */
    public char[][] run() throws IOException {
        double[][] segmentedImageBrightness =
                imageBrightnessCalculator.calculateBrightness(imageSegmenter.getSegmentedImage());

        char[][] asciiArt = new char[segmentedImageBrightness.length][segmentedImageBrightness[0].length];

        for (int row = 0; row < asciiArt.length; row++) {
            for (int col = 0; col < asciiArt[0].length; col++) {
                asciiArt[row][col] = subImgCharMatcher.getCharByImageBrightness(
                        segmentedImageBrightness[row][col]);
            }
        }

        return asciiArt;
    }
}
