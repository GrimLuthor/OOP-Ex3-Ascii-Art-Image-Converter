package image;

import java.awt.*;

/**
 * 'Ere we've got a real cracker of a class, the ImagePadder!
 * This one's all about givin' your images a bit of space, y'know?
 */
public class ImagePadder {

    /**
     * Adds padding to an image, makin' it all nice and snug-like. Proper job!
     *
     * @param image The image you wanna pad, innit?
     * @return A freshly padded image, ready to take on the world. Top stuff!
     */
    public image.Image addPadding(image.Image image) {
        // Calculate the new dimensions for the padded image. 'Cos who likes a cramped image, right?
        int newWidth = Integer.highestOneBit(image.getWidth() - 1) * 2;
        int newHeight = Integer.highestOneBit(image.getHeight() - 1) * 2;

        Color[][] newPixelArray = new Color[newHeight][newWidth];

        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                // Calculate where the original pixel should be after paddin'.
                int originalI = i - (newHeight - image.getHeight()) / 2;
                int originalJ = j - (newWidth - image.getWidth()) / 2;

                // If the pixel is within the original image bounds, copy its color.
                // Otherwise, give it a nice clean white color, like a fresh sheet of paper.
                if (originalI >= 0 && originalJ >= 0 &&
                        originalI < image.getHeight() && originalJ < image.getWidth()) {
                    newPixelArray[i][j] = image.getPixel(originalI, originalJ);
                } else {
                    newPixelArray[i][j] = Color.WHITE; // 'Cos who doesn't love a bit of white space?
                }
            }
        }

        return new Image(newPixelArray, newWidth, newHeight);
    }
}
