package ascii_art_converter.image_char_matching;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SubImgCharMatcher {

    private final Map<Character, Double> plainBrightnessMap;
    private Map<Character, Double> normalizedBrightnessMap;

    private double minBrightness = Integer.MAX_VALUE;
    private double maxBrightness = Integer.MIN_VALUE;


    public SubImgCharMatcher(char[] charset) {
        plainBrightnessMap = new HashMap<>();

        for (char c : charset) {
            double charBrightness = calculatePlainCharBrightness(c);
            if (charBrightness > maxBrightness) {
                maxBrightness = charBrightness;
            }

            if (charBrightness < minBrightness) {
                minBrightness = charBrightness;
            }
            plainBrightnessMap.put(c, charBrightness);
        }

        generateNormalizedBrightnessMap();
    }

    public char getCharByImageBrightness(double brightness) {
        char closestChar = '\0';
        double minDifference = Double.MAX_VALUE;

        for (Map.Entry<Character, Double> entry : normalizedBrightnessMap.entrySet()) {
            char character = entry.getKey();
            double charBrightness = entry.getValue();
            double difference = Math.abs(charBrightness - brightness);

            if (difference < minDifference || (difference == minDifference && character < closestChar)) {
                closestChar = character;
                minDifference = difference;
            }
        }
        return closestChar;
    }

    public void addChar(char c) {
        if (plainBrightnessMap.containsKey(c)) {
            return;
        }
        double charBrightness = calculatePlainCharBrightness(c);

        plainBrightnessMap.put(c, charBrightness);

        if (charBrightness > maxBrightness) {
            maxBrightness = charBrightness;
            generateNormalizedBrightnessMap();
        } else if (charBrightness < minBrightness) {
            minBrightness = charBrightness;
            generateNormalizedBrightnessMap();
        } else {
            // if the plain char brightness is in the range, then there is no need to normalize the map again.
            double normalizedBrightness = (charBrightness - minBrightness) / (maxBrightness - minBrightness);
            normalizedBrightnessMap.put(c, normalizedBrightness);
        }
    }

    public void removeChar(char c) throws NullPointerException {
        if (!plainBrightnessMap.containsKey(c)) {
            return;
        }
        double charBrightness = plainBrightnessMap.get(c);
        plainBrightnessMap.remove(c);

        if (charBrightness == minBrightness) {
            minBrightness = findMinBrightness();
            generateNormalizedBrightnessMap();
            // no need to remove since it's generated based on plain brightness from which we already removed.
        } else if (charBrightness == maxBrightness) {
            maxBrightness = findMaxBrightness();
            generateNormalizedBrightnessMap();
            // no need to remove since it's generated based on plain brightness from which we already removed.
        } else {
            // we can remove from normalized map with no need to generate new map since normalization is
            // based on min and max values which haven't been changed.
            normalizedBrightnessMap.remove(c);
        }
    }

    private double findMinBrightness() {
        double minBrightness = Double.MAX_VALUE;

        for (double brightness : plainBrightnessMap.values()) {
            if (brightness < minBrightness) {
                minBrightness = brightness;
            }
        }
        return minBrightness;
    }

    private double findMaxBrightness() {
        double maxBrightness = Double.MIN_VALUE;

        for (double brightness : plainBrightnessMap.values()) {
            if (brightness > maxBrightness) {
                maxBrightness = brightness;
            }
        }
        return maxBrightness;
    }

    private double calculatePlainCharBrightness(char c) {
        boolean[][] boolImg = CharConverter.convertToBoolArray(c);

        int brightnessCount = 0;

        for (int row = 0; row < CharConverter.DEFAULT_PIXEL_RESOLUTION; row++) {
            for (int col = 0; col < CharConverter.DEFAULT_PIXEL_RESOLUTION; col++) {
                if (boolImg[row][col]) {
                    brightnessCount++;
                }
            }
        }

        return (double) brightnessCount / (CharConverter.DEFAULT_PIXEL_RESOLUTION *
                CharConverter.DEFAULT_PIXEL_RESOLUTION);
    }

    private void generateNormalizedBrightnessMap() {

        normalizedBrightnessMap = new HashMap<>();

        for (Map.Entry<Character, Double> entry : plainBrightnessMap.entrySet()) {
            double newBrightness = (entry.getValue() - minBrightness) / (maxBrightness - minBrightness);
            normalizedBrightnessMap.put(entry.getKey(), newBrightness);
        }
    }

    public char[] getCharset() {
        Set<Character> charSet = plainBrightnessMap.keySet();
        char[] charset = new char[charSet.size()];
        int i = 0;
        for (char c : charSet) {
            charset[i] = c;
            i++;
        }
        return charset;
    }
}
