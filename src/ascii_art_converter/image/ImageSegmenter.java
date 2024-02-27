package ascii_art_converter.image;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageSegmenter {

    private final Map<String,Map<Integer,Image[][]>> segmentedImageCash;
    private int resolution;

    public ImageSegmenter(int resolution) {
        this.segmentedImageCash = new HashMap<>();
        this.resolution = resolution;
    }

    public Image[][] getSegmentedImage(String imagePath) throws IOException {

        if (segmentedImageCash.containsKey(imagePath)) {
            if (segmentedImageCash.get(imagePath).containsKey(resolution)) {
                return segmentedImageCash.get(imagePath).get(resolution);
            }
            else {
                // where exception may be thrown
                Image[][] segmentedImage = segmentImage(imagePath, resolution);
                segmentedImageCash.get(imagePath).put(resolution, segmentedImage);
                return segmentedImage;
            }
        }
        else {
            segmentedImageCash.put(imagePath,new HashMap<>());
            // where exception may be thrown
            Image[][] segmentedImage = segmentImage(imagePath,resolution);
            segmentedImageCash.get(imagePath).put(resolution,segmentedImage);

            return segmentedImage;
        }
    }

    private Image[][] segmentImage (String imagePath, int resolution) throws IOException {
        ImagePadder imagePadder = new ImagePadder();
        Image image = imagePadder.addPadding(new Image(imagePath));

        if (image.getWidth() < resolution || image.getHeight() < resolution) {
            throw new IOException("resolution is too high for the image");
        }
        Image[][] subImages = new Image[resolution][resolution];
        int subImageWidth = image.getWidth() / resolution;

        for (int i = 0; i < image.getHeight() / subImageWidth; i++) {
            for (int j = 0; j < resolution; j++) {
                Color[][] subPixelArray = new Color[subImageWidth][subImageWidth];
                for (int x = 0; x < subImageWidth; x++) {
                    for (int y = 0; y < subImageWidth; y++) {
                        subPixelArray[x][y] =
                                image.getPixel(i * subImageWidth + y, j * subImageWidth + x);
                    }
                }

                subImages[i][j] = new Image(subPixelArray, subImageWidth, subImageWidth);
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
