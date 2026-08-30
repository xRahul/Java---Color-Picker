package com.colorpicker;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ColorUtilsTest {

    @Test
    void testFormatColorInfoWhite() {
        Color white = Color.WHITE;
        String result = ColorUtils.formatColorInfo(white);
        assertThat(result).contains("RGB_Hex_Value: #FFFFFF");
        assertThat(result).contains("Red: 255, FF");
        assertThat(result).contains("Green: 255, FF");
        assertThat(result).contains("Blue: 255, FF");
    }

    @Test
    void testFormatColorInfoBlack() {
        Color black = Color.BLACK;
        String result = ColorUtils.formatColorInfo(black);
        assertThat(result).contains("RGB_Hex_Value: #000000");
        assertThat(result).contains("Red: 0, 00");
        assertThat(result).contains("Green: 0, 00");
        assertThat(result).contains("Blue: 0, 00");
    }

    @ParameterizedTest
    @MethodSource("colorProvider")
    void testFormatColorInfoParameterized(int r, int g, int b, String expectedHex) {
        Color color = new Color(r, g, b);
        String result = ColorUtils.formatColorInfo(color);
        assertThat(result).contains("RGB_Hex_Value: #" + expectedHex);
        assertThat(result).contains("Red: " + r + ", " + String.format("%02X", r));
        assertThat(result).contains("Green: " + g + ", " + String.format("%02X", g));
        assertThat(result).contains("Blue: " + b + ", " + String.format("%02X", b));
    }

    static Stream<Arguments> colorProvider() {
        return Stream.of(
                Arguments.of(10, 20, 30, "0A141E"),
                Arguments.of(255, 0, 0, "FF0000"),
                Arguments.of(0, 255, 0, "00FF00"),
                Arguments.of(0, 0, 255, "0000FF"),
                Arguments.of(128, 128, 128, "808080"),
                Arguments.of(17, 34, 51, "112233"),
                Arguments.of(100, 200, 250, "64C8FA")
        );
    }

    @Test
    void testFormatColorInfoEdgeCaseAllMax() {
        Color color = new Color(255, 255, 255);
        String result = ColorUtils.formatColorInfo(color);
        assertThat(result).contains("RGB_Hex_Value: #FFFFFF");
        assertThat(result).contains("Red: 255, FF");
        assertThat(result).contains("Green: 255, FF");
        assertThat(result).contains("Blue: 255, FF");
    }
}
