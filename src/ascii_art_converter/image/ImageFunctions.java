package ascii_art_converter.image;

import java.awt.*;
import java.io.IOException;

// TODO: CREATE CONSTANTS
public class ImageFunctions {
    public static Image[][] subImages(Image image, int resolution) throws IOException{
        image = padding(image);
        if (image.getWidth() < resolution || image.getHeight() < resolution) {
            throw new IOException("resolution is too high for the image");
        }
        Image[][] subImages = new Image[resolution][resolution];
        int subImageWidth = image.getWidth() / resolution;
        int subImageHeight = image.getHeight() / resolution;

        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                Color[][] subPixelArray = new Color[subImageWidth][subImageHeight];
                for (int x = 0; x < subImageHeight; x++) {
                    for (int y = 0; y < subImageWidth; y++) {
                        subPixelArray[x][y] =
                                image.getPixel(i * subImageWidth + y, j * subImageHeight + x);
                    }
                }

                subImages[i][j] = new Image(subPixelArray, subImageWidth, subImageHeight);
            }
        }

        return subImages;
    }

    public static double brightness(Image image) {
        double brightness = 0;
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                brightness += grayScale(image.getPixel(i, j));
            }
        }
        brightness = (brightness / (image.getHeight() * image.getWidth())) / 255;
        return brightness;
    }

    private static Image padding(Image image) {
        int newWidth = Integer.highestOneBit(image.getWidth()) * 2;
        int newHeight = Integer.highestOneBit(image.getHeight()) * 2;
        Color[][] newPixelArray = new Color[newHeight][newWidth];

        for (int i = 0 ; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                // calculating how much padding was added and finding where the original pixel was
                int originalI = i - (newHeight - image.getHeight()) / 2;
                int originalJ = j - (newWidth - image.getWidth()) / 2;
                if (originalI >= 0 && originalJ >= 0 &&
                        originalI < image.getHeight() && originalJ < image.getWidth()){
                    newPixelArray[i][j] = image.getPixel(originalI, originalJ);
                } else {
                    newPixelArray[i][j] = Color.WHITE;
                }
            }
        }

        return new Image(newPixelArray, newWidth, newHeight);
    }

    private static double grayScale(Color color) {
        return color.getRed() * 0.2126 + color.getGreen() * 0.7152 + color.getBlue() * 0.0722;
    }
}
