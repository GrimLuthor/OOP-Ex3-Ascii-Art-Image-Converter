package ascii_art_converter.image;

import java.awt.*;

public class ImagePadder {

    public Image addPadding(Image image) {
        int newWidth = Integer.highestOneBit(image.getWidth() - 1) * 2;
        int newHeight = Integer.highestOneBit(image.getHeight() - 1) * 2;
        Color[][] newPixelArray = new Color[newHeight][newWidth];

        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                // calculating how much padding was added and finding where the original pixel was
                int originalI = i - (newHeight - image.getHeight()) / 2;
                int originalJ = j - (newWidth - image.getWidth()) / 2;
                if (originalI >= 0 && originalJ >= 0 &&
                        originalI < image.getHeight() && originalJ < image.getWidth()) {
                    newPixelArray[i][j] = image.getPixel(originalI, originalJ);
                } else {
                    newPixelArray[i][j] = Color.WHITE;
                }
            }
        }

        return new Image(newPixelArray, newWidth, newHeight);
    }
}
