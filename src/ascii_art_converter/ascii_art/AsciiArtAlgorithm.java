package ascii_art_converter.ascii_art;

import ascii_art_converter.image.Image;
import ascii_art_converter.image.ImageBrightnessCalculctor;
import ascii_art_converter.image.ImageSegmenter;
import ascii_art_converter.image_char_matching.SubImgCharMatcher;

import java.io.IOException;

public class AsciiArtAlgorithm {

    private final ImageSegmenter imageSegmenter;
    private final ImageBrightnessCalculctor imageBrightnessCalculctor;
    private final SubImgCharMatcher subImgCharMatcher;

    public AsciiArtAlgorithm (ImageSegmenter imageSegmenter,
                              ImageBrightnessCalculctor imageBrightnessCalculctor,
                              SubImgCharMatcher subImgCharMatcher) {

        this.imageSegmenter = imageSegmenter;
        this.imageBrightnessCalculctor = imageBrightnessCalculctor;
        this.subImgCharMatcher = subImgCharMatcher;
    }

    public char [][] run(String imagePath) throws IOException {
        // where exception may be thrown
        Image[][] segmentedImage = imageSegmenter.getSegmentedImage(imagePath);
        double[][] segmentedImageBrightness = imageBrightnessCalculctor.calculateBrightness(segmentedImage);

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
