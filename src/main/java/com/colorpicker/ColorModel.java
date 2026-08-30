package com.colorpicker;

import java.awt.Color;

public class ColorModel {

    private float hue = 0.5f;
    private float saturation = 0.5f;
    private float brightness = 0.5f;

    public Color getColor() {
        return Color.getHSBColor(hue, saturation, brightness);
    }

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = Math.max(0, Math.min(1, hue));
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = Math.max(0, Math.min(1, saturation));
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = Math.max(0, Math.min(1, brightness));
    }
}
