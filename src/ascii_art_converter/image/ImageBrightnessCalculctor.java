package ascii_art_converter.image;

import java.awt.*;
import java.io.IOException;

public class ImageBrightnessCalculctor {

    public double calculateSegmentBrightness(Image image) {
        double brightness = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                brightness += grayScale(image.getPixel(i, j));
            }
        }
        brightness = (brightness / (image.getHeight() * image.getWidth())) / 255;
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
        return color.getRed() * 0.2126 + color.getGreen() * 0.7152 + color.getBlue() * 0.0722;
    }
}
