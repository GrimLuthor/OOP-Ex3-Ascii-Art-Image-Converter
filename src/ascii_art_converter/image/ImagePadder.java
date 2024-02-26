package ascii_art_converter.image;

import java.awt.*;

public class ImagePadder {

    public Image addPadding(Image image) {
        System.out.println("" + image.getWidth() + " " + image.getHeight());
        int newWidth = getBiggerOrEqualPowerOf2(image.getWidth());
        int newHeight = getBiggerOrEqualPowerOf2(image.getHeight());
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

    private int getBiggerOrEqualPowerOf2(int num) {
        int power = 2;

        while (power < num) {
            power*=2;
        }

        return power;
    }
}
