package ascii_art_converter.image;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageSegmenter {

    private final Map<String, Map<Integer, Image[][]>> segmentedImageCash;
    private int resolution;

    public ImageSegmenter(int resolution) {
        this.segmentedImageCash = new HashMap<>();
        this.resolution = resolution;
    }

    public Image[][] getSegmentedImage(String imagePath) throws IOException {
        // if we already have some segmented image of this image path in cash:
        if (segmentedImageCash.containsKey(imagePath)) {
            // if the segmented image is of the same resolution:
            if (segmentedImageCash.get(imagePath).containsKey(resolution)) {
                return segmentedImageCash.get(imagePath).get(resolution);
            } else {
                // create segmented image of this resolution and save under the image path key sub-map
                Image[][] segmentedImage = segmentImage(imagePath, resolution);
                segmentedImageCash.get(imagePath).put(resolution, segmentedImage);
                return segmentedImage;
            }
        } else {
            // create new segmented image and register new path:
            segmentedImageCash.put(imagePath, new HashMap<>());
            Image[][] segmentedImage = segmentImage(imagePath, resolution);
            segmentedImageCash.get(imagePath).put(resolution, segmentedImage);

            return segmentedImage;
        }
    }

    private Image[][] segmentImage(String imagePath, int resolution) throws IOException {
        ImagePadder imagePadder = new ImagePadder();
        Image image = imagePadder.addPadding(new Image(imagePath));

        if (image.getWidth() < resolution || image.getHeight() < resolution) {
            throw new IOException("resolution is too high for the image");
        }

        int subImageSize = image.getWidth() / resolution;
        Image[][] subImages = new Image[image.getHeight()/subImageSize][resolution];


        for (int i = 0; i < image.getHeight() / subImageSize; i++) {
            for (int j = 0; j < resolution; j++) {
                Color[][] subPixelArray = new Color[subImageSize][subImageSize];
                for (int x = 0; x < subImageSize; x++) {
                    for (int y = 0; y < subImageSize; y++) {
                        subPixelArray[x][y] =
                                image.getPixel(i * subImageSize + y, j * subImageSize + x);
                    }
                }

                subImages[i][j] = new Image(subPixelArray, subImageSize, subImageSize);
            }
        }

        return subImages;
    }

    public int getResolution() {
        return this.resolution;
    }

    public void setResolution(int newRes) {
        this.resolution = newRes;
    }
}
