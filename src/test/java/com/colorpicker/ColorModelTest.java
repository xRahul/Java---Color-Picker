package com.colorpicker;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Color;
import org.junit.jupiter.api.Test;

public class ColorModelTest {

    @Test
    void defaultValuesAreHalf() {
        ColorModel model = new ColorModel();
        assertThat(model.getHue()).isEqualTo(0.5f);
        assertThat(model.getSaturation()).isEqualTo(0.5f);
        assertThat(model.getBrightness()).isEqualTo(0.5f);
    }

    @Test
    void getColorNotNull() {
        ColorModel model = new ColorModel();
        assertThat(model.getColor()).isNotNull();
    }

    @Test
    void clampHueAbove() {
        ColorModel model = new ColorModel();
        model.setHue(1.5f);
        assertThat(model.getHue()).isEqualTo(1.0f);
    }

    @Test
    void clampHueBelow() {
        ColorModel model = new ColorModel();
        model.setHue(-0.5f);
        assertThat(model.getHue()).isEqualTo(0.0f);
    }

    @Test
    void clampSaturationAbove() {
        ColorModel model = new ColorModel();
        model.setSaturation(2.0f);
        assertThat(model.getSaturation()).isEqualTo(1.0f);
    }

    @Test
    void clampSaturationBelow() {
        ColorModel model = new ColorModel();
        model.setSaturation(-1.0f);
        assertThat(model.getSaturation()).isEqualTo(0.0f);
    }

    @Test
    void clampBrightnessAbove() {
        ColorModel model = new ColorModel();
        model.setBrightness(5.0f);
        assertThat(model.getBrightness()).isEqualTo(1.0f);
    }

    @Test
    void clampBrightnessBelow() {
        ColorModel model = new ColorModel();
        model.setBrightness(-0.2f);
        assertThat(model.getBrightness()).isEqualTo(0.0f);
    }

    @Test
    void acceptsValidHue() {
        ColorModel model = new ColorModel();
        model.setHue(0.7f);
        assertThat(model.getHue()).isEqualTo(0.7f);
    }

    @Test
    void acceptsValidSaturation() {
        ColorModel model = new ColorModel();
        model.setSaturation(0.2f);
        assertThat(model.getSaturation()).isEqualTo(0.2f);
    }

    @Test
    void acceptsValidBrightness() {
        ColorModel model = new ColorModel();
        model.setBrightness(0.9f);
        assertThat(model.getBrightness()).isEqualTo(0.9f);
    }

    @Test
    void getColorChangesWithHue() {
        ColorModel model = new ColorModel();
        model.setHue(0.0f);
        model.setSaturation(1.0f);
        model.setBrightness(1.0f);
        Color c1 = model.getColor();
        model.setHue(0.5f);
        Color c2 = model.getColor();
        assertThat(c2).isNotEqualTo(c1);
    }
}
