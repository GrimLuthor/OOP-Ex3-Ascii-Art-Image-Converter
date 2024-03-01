package ascii_art_converter.image;

import java.awt.*;

import ascii_art_converter.ascii_art.Constants;

/**
 * A proper clever tool for calculating brightness in images. It's all about seeing the light, innit?
 */
public class ImageBrightnessCalculator {

    /**
     * Calculates the brightness of a single image segment. Proper job!
     *
     * @param image The image segment to calculate brightness for, mate.
     * @return The brightness of the image segment, right proper.
     */
    private double calculateSegmentBrightness(Image image) {
        double brightness = 0;
        // Loop through each pixel in the image segment, calculating the brightness.
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                brightness += grayScale(image.getPixel(i, j));
            }
        }
        // Normalize the brightness and return the result.
        brightness = (brightness / (image.getHeight() * image.getWidth())) / Constants.RGB_MAX;
        return brightness;
    }

    /**
     * Calculates the brightness for each segment in a 2D array of images. Top stuff!
     *
     * @param segmentedImage The 2D array of image segments, ready for brightness calculation.
     * @return A 2D array containing the brightness of each image segment, proper clever.
     */
    public double[][] calculateBrightness(Image[][] segmentedImage) {
        // Array to hold brightness values for each segment.
        double[][] segmentedImageBrightness = new double[segmentedImage.length][segmentedImage[0].length];

        // Loop through each segment and calculate its brightness.
        for (int row = 0; row < segmentedImage.length; row++) {
            for (int col = 0; col < segmentedImage[0].length; col++) {
                segmentedImageBrightness[row][col] = calculateSegmentBrightness(segmentedImage[row][col]);
            }
        }

        return segmentedImageBrightness;
    }

    /**
     * Calculates the grayscale value of a given color. Dead simple!
     *
     * @param color The color to calculate grayscale for, mate.
     * @return The grayscale value of the color, proper straightforward.
     */
    private static double grayScale(Color color) {
        // Calculate grayscale using coefficients for each color component.
        return color.getGreen() * Constants.GREEN_COEFFICIENT +
                color.getBlue() * Constants.BLUE_COEFFICIENT +
                color.getRed() * Constants.RED_COEFFICIENT;
    }
}
