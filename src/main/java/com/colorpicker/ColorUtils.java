package com.colorpicker;

import java.awt.Color;

public class ColorUtils {

    /**
     * Formats the color information into a descriptive string.
     *
     * @param color The color to format.
     * @return A string containing RGB Hex and Decimal values.
     */
    public static String formatColorInfo(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        String redHex = Integer.toHexString(red).toUpperCase();
        String greenHex = Integer.toHexString(green).toUpperCase();
        String blueHex = Integer.toHexString(blue).toUpperCase();

        // Ensure 2 digits for hex if needed (though the original code didn't,
        // standard hex codes usually do, but I should stick to original behavior
        // or improve it? The request said "rewrite according to latest standards".
        // Latest standards definitely imply 2-digit hex codes (e.g. 0A instead of A).
        // The original code: Integer.toHexString(10) -> "a". "a".toUpperCase() -> "A".
        // If I have #A instead of #0A, it's valid in some contexts but weird.
        // I will standardize to 2 digits.

        redHex = String.format("%02X", red);
        greenHex = String.format("%02X", green);
        blueHex = String.format("%02X", blue);

        return String.format("RGB_Hex_Value: #%s%s%s\nRed: %d, %s\nGreen: %d, %s\nBlue: %d, %s",
                redHex, greenHex, blueHex,
                red, redHex,
                green, greenHex,
                blue, blueHex);
    }
}
