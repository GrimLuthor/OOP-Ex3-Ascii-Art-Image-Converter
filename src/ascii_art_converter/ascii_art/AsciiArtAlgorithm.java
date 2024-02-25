package ascii_art_converter.ascii_art;

import ascii_art_converter.image.Image;
import ascii_art_converter.image.ImageFunctions;
import java.io.IOException;

public class AsciiArtAlgorithm {
    private Image image;
    private Image[][] subImages;
    private char[] charSet;
    public AsciiArtAlgorithm (String imagePath, int resolution, char[] charSet) throws IOException {
        this.image = new Image(imagePath);
        this.subImages = ImageFunctions.subImages(image, resolution);
        this.charSet = charSet;
    }

    public char [][] run() {
        // handle the conversion to chars
        return new char[1][1];
    }
}
