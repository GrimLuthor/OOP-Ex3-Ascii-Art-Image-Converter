package ascii_art_converter.image;

import java.awt.*;
import java.io.IOException;

public class ImageSegmenter {
    public Image[][] segmentImage(Image image, int resolution) throws IOException {
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
}
