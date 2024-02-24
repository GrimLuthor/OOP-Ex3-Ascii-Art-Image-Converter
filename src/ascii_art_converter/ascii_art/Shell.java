package ascii_art_converter.ascii_art;

import ascii_art_converter.image_char_matching.SubImgCharMatcher;

public class Shell {

    public void run() {

    }

    public static void main(String[] args) {
        SubImgCharMatcher charMatcher = new SubImgCharMatcher(new char[] {'.','▲','■'});
        System.out.println("---------------------------------");
        charMatcher.addChar('_');
        System.out.println("---------------------------------");
        charMatcher.removeChar('.');

    }
}
