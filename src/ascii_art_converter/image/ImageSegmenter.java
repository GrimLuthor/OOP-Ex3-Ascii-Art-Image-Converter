package ascii_art_converter.image;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 'Ere we've got the ImageSegmenter, a proper genius at breakin' down images into bits and pieces!
 */
public class ImageSegmenter {

    // A stash for keepin' segmented images handy, y'know?
    private final Map<String, Map<Integer, Image[][]>> segmentedImageCash;
    // The resolution at which we're slicin' and dicin' these images.
    private int resolution;

    /**
     * Constructs a new ImageSegmenter with the given resolution. 'Cause we gotta start somewhere, right?
     *
     * @param resolution The resolution at which to segment images, mate.
     */
    public ImageSegmenter(int resolution) {
        this.segmentedImageCash = new HashMap<>();
        this.resolution = resolution;
    }

    /**
     * Gets the segmented image for the given image path.
     * 'Cause why start from scratch when you've got somethin' already sliced, eh?
     *
     * @param imagePath The path of the image to segment, innit?
     * @return The segmented image, all ready to go!
     * @throws IOException If somethin' goes pear-shaped while readin' the image, we're in a right pickle.
     */
    public Image[][] getSegmentedImage(String imagePath) throws IOException {
        if (segmentedImageCash.containsKey(imagePath)) {
            if (segmentedImageCash.get(imagePath).containsKey(resolution)) {
                return segmentedImageCash.get(imagePath).get(resolution);
            } else {
                Image[][] segmentedImage = segmentImage(imagePath, resolution);
                segmentedImageCash.get(imagePath).put(resolution, segmentedImage);
                return segmentedImage;
            }
        } else {
            segmentedImageCash.put(imagePath, new HashMap<>());
            Image[][] segmentedImage = segmentImage(imagePath, resolution);
            segmentedImageCash.get(imagePath).put(resolution, segmentedImage);
            return segmentedImage;
        }
    }

    /**
     * Segments the image at the specified path into smaller sub-images, like choppin' veggies for a stew!
     *
     * @param imagePath  The path of the image to segment, proper job.
     * @param resolution The resolution at which to segment the image, mate.
     * @return An array of segmented images, all ready for action.
     * @throws IOException If there's a spot of bother readin' the image, we're in for a right 'mare.
     */
    private Image[][] segmentImage(String imagePath, int resolution) throws IOException {
        ImagePadder imagePadder = new ImagePadder();
        Image image = imagePadder.addPadding(new Image(imagePath));

        int subImageSize = image.getWidth() / resolution;
        Image[][] subImages = new Image[image.getHeight() / subImageSize][resolution];

        // Loop through each row and column of sub-images, like layin' out a grid.
        for (int i = 0; i < image.getHeight() / subImageSize; i++) {
            for (int j = 0; j < resolution; j++) {
                Color[][] subPixelArray = new Color[subImageSize][subImageSize];
                // Extract the pixels for each sub-image from the main image, like carvin' out slices of cake.
                for (int x = 0; x < subImageSize; x++) {
                    for (int y = 0; y < subImageSize; y++) {
                        subPixelArray[x][y] =
                                image.getPixel(i * subImageSize + y, j * subImageSize + x);
                    }
                }
                // Create a new sub-image with the extracted pixels and stash it in our array.
                subImages[i][j] = new Image(subPixelArray, subImageSize, subImageSize);
            }
        }

        return subImages;
    }

    /**
     * Gets the current resolution of the ImageSegmenter.
     *
     * @return The current resolution setting, mate.
     */
    public int getResolution() {
        return this.resolution;
    }

    /**
     * Sets the resolution of the ImageSegmenter to a new value.
     *
     * @param newRes The new resolution to set, proper job.
     */
    public void setResolution(int newRes) {
        this.resolution = newRes;
    }
}
