package ascii_art_converter.ascii_art;

import ascii_art_converter.ascii_output.AsciiOutput;
import ascii_art_converter.ascii_output.ConsoleAsciiOutput;
import ascii_art_converter.ascii_output.HtmlAsciiOutput;
import ascii_art_converter.image.ImageBrightnessCalculctor;
import ascii_art_converter.image.ImageSegmenter;
import ascii_art_converter.image_char_matching.SubImgCharMatcher;

import java.io.IOException;

public class Shell {

    public static void run() throws IOException {

        char[] charset = new char[] {'.','-','*','&','+','a','A','b','B','c','C','v','@','1','0','8'};

        ImageSegmenter imageSegmenter = new ImageSegmenter(256);
        ImageBrightnessCalculctor imageBrightnessCalculctor = new ImageBrightnessCalculctor();
        SubImgCharMatcher subImgCharMatcher = new SubImgCharMatcher(charset);

        String imagePath = "src/ascii_art_converter/examples/cat.jpeg";

        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(imageSegmenter,imageBrightnessCalculctor,subImgCharMatcher);

        AsciiOutput HtmlOut = new HtmlAsciiOutput("out.html","Courier New");

        while (true) {
            System.out.println("Write something: ");
            String input = KeyboardInput.readLine();

            if (input.equals("bruh")) {
                return;
            }
            if (input.equals("x2")) {
                imageSegmenter.setResolution(imageSegmenter.getResolution()*2);
            }
            else if (input.equals("/2")) {
                imageSegmenter.setResolution(imageSegmenter.getResolution()/2);
            }


            HtmlOut.out(asciiArtAlgorithm.run(imagePath));
        }
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
