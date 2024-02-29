package ascii_art_converter.image;

import java.awt.*;

import ascii_art_converter.ascii_art.Constants;

public class ImageBrightnessCalculator {

    public double calculateSegmentBrightness(Image image) {
        double brightness = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                brightness += grayScale(image.getPixel(i, j));
            }
        }
        brightness = (brightness / (image.getHeight() * image.getWidth())) / Constants.RGB_MAX;
        return brightness;
    }

    public double[][] calculateBrightness(Image[][] segmentedImage) {

        double[][] segmentedImageBrightness = new double[segmentedImage.length][segmentedImage[0].length];

        for (int row = 0; row < segmentedImage.length; row++) {
            for (int col = 0; col < segmentedImage[0].length; col++) {
                segmentedImageBrightness[row][col] = calculateSegmentBrightness(segmentedImage[row][col]);
            }
        }

        return segmentedImageBrightness;
    }

    private static double grayScale(Color color) {
        return color.getGreen() * Constants.GREEN_COEFFICIENT +
                color.getBlue() * Constants.BLUE_COEFFICIENT +
                color.getRed() * Constants.RED_COEFFICIENT;
    }
}
