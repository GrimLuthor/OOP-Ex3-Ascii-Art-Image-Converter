package ascii_art_converter.ascii_art;

import ascii_art_converter.image.Image;
import ascii_art_converter.image.ImageBrightnessCalculctor;
import ascii_art_converter.image.ImagePadder;
import ascii_art_converter.image.ImageSegmenter;
import ascii_art_converter.image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.Arrays;

public class AsciiArtAlgorithm {
    private String imagePath;
    private int resolution;
    private char[] charSet;


    public AsciiArtAlgorithm (String imagePath, int resolution, char[] charSet) {
        this.imagePath = imagePath;
        this.charSet = charSet;
        this.resolution = resolution;
    }

    public char [][] run() throws IOException {
        ImagePadder imagePadder = new ImagePadder();
        ImageSegmenter imageSegmenter = new ImageSegmenter();
        ImageBrightnessCalculctor imageBrightnessCalculctor = new ImageBrightnessCalculctor();
        SubImgCharMatcher subImgCharMatcher = new SubImgCharMatcher(charSet);

        Image paddedImage = imagePadder.addPadding(new Image(imagePath));

        Image[][] segmentedImage = imageSegmenter.segmentImage(paddedImage,resolution);
        double[][] segmentedImageBrightness = imageBrightnessCalculctor.calculateBrightness(segmentedImage);

//        System.out.println(Arrays.deepToString(segmentedImageBrightness));

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
