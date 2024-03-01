package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package ascii_art_converter.image.
 *
 * @author Dan Nirel
 */
public class Image {

    private final Color[][] pixelArray;
    private final int width;
    private final int height;

    /**
     * Constructs an Image object by reading it from a file, proper job.
     *
     * @param filename The name of the file to read the image from, innit?
     * @throws IOException If there's a problem reading the file, we're in a right pickle, aren't we?
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();

        // Populate the pixel array with colours from the image
        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j] = new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Constructs an Image object with the provided pixel array, width, and height. Top-notch stuff, really.
     *
     * @param pixelArray The array representing the pixels in the image, proper job.
     * @param width      The width of the image, mate.
     * @param height     The height of the image, bruv.
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of the image, ain't that handy?
     *
     * @return The width of the image, mate.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the image, top stuff.
     *
     * @return The height of the image, bruv.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the colour of the pixel at the specified coordinates, proper clever.
     *
     * @param x The x-coordinate of the pixel, innit?
     * @param y The y-coordinate of the pixel, bruv.
     * @return The colour of the pixel at the specified coordinates, top-notch.
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /**
     * Saves the image to a file with the provided filename. Dead easy.
     *
     * @param fileName The name of the file to save the image to, innit?
     */
    public void saveImage(String fileName) {
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName + ".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
