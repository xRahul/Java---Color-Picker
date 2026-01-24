package com.colorpicker;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColorUtilsTest {

    @Test
    public void testFormatColorInfo_White() {
        Color white = Color.WHITE; // 255, 255, 255
        String result = ColorUtils.formatColorInfo(white);

        assertTrue(result.contains("RGB_Hex_Value: #FFFFFF"));
        assertTrue(result.contains("Red: 255, FF"));
        assertTrue(result.contains("Green: 255, FF"));
        assertTrue(result.contains("Blue: 255, FF"));
    }

    @Test
    public void testFormatColorInfo_Black() {
        Color black = Color.BLACK; // 0, 0, 0
        String result = ColorUtils.formatColorInfo(black);

        assertTrue(result.contains("RGB_Hex_Value: #000000"));
        assertTrue(result.contains("Red: 0, 00"));
        assertTrue(result.contains("Green: 0, 00"));
        assertTrue(result.contains("Blue: 0, 00"));
    }

    @Test
    public void testFormatColorInfo_Arbitrary() {
        Color color = new Color(10, 20, 30); // 0A, 14, 1E
        String result = ColorUtils.formatColorInfo(color);

        assertTrue(result.contains("RGB_Hex_Value: #0A141E"));
        assertTrue(result.contains("Red: 10, 0A"));
        assertTrue(result.contains("Green: 20, 14"));
        assertTrue(result.contains("Blue: 30, 1E"));
    }
}
