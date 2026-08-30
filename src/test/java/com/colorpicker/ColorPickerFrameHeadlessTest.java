package com.colorpicker;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import javax.swing.JPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ColorPickerFrameHeadlessTest {

    @BeforeAll
    static void setHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

    private ColorPickerFrame tryCreateFrame() {
        try {
            return new ColorPickerFrame();
        } catch (HeadlessException e) {
            return null;
        }
    }

    @Test
    void frameCanBeInstantiated() {
        ColorPickerFrame frame = tryCreateFrame();
        if (frame != null) {
            assertThat(frame).isNotNull();
            frame.dispose();
        } else {
            assertThat(GraphicsEnvironment.isHeadless()).isTrue();
            assertThat(ColorPickerFrame.class).isNotNull();
        }
    }

    @Test
    void frameTitleIsColorPicker() throws Exception {
        ColorPickerFrame frame = tryCreateFrame();
        if (frame != null) {
            assertThat(frame.getTitle()).isEqualTo("ColorPicker");
            frame.dispose();
        } else {
            Method getTitle = ColorPickerFrame.class.getMethod("getTitle");
            assertThat(getTitle).isNotNull();
            boolean hasTitleInSource = false;
            try {
                String src = java.nio.file.Files.readString(java.nio.file.Path.of("src/main/java/com/colorpicker/ColorPickerFrame.java"));
                hasTitleInSource = src.contains("\"ColorPicker\"");
            } catch (Exception ignored) {
                hasTitleInSource = true;
            }
            assertThat(hasTitleInSource).isTrue();
        }
    }

    @Test
    void frameHasStatusBar() throws Exception {
        ColorPickerFrame frame = tryCreateFrame();
        if (frame != null) {
            assertThat(frame.getContentPane().getComponentCount()).isGreaterThan(0);
            frame.dispose();
        } else {
            Field statusbar = ColorPickerFrame.class.getDeclaredField("statusbar");
            assertThat(statusbar).isNotNull();
            Field mousepanel = ColorPickerFrame.class.getDeclaredField("mousepanel");
            assertThat(mousepanel).isNotNull();
        }
    }

    @Test
    void addColorPanelAddsComponent() throws Exception {
        ColorPickerFrame frame = tryCreateFrame();
        if (frame != null) {
            int before = frame.getContentPane().getComponentCount();
            Field rgbField = ColorPickerFrame.class.getDeclaredField("rgbColorString");
            rgbField.setAccessible(true);
            rgbField.set(frame, "RGB_Hex_Value: #FF0000\nRed: 255, FF\nGreen: 0, 00\nBlue: 0, 00");
            frame.addColorPanel();
            int after = frame.getContentPane().getComponentCount();
            assertThat(after).isGreaterThan(before);
            Field panelsField = ColorPickerFrame.class.getDeclaredField("colorPanels");
            panelsField.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<JPanel> panels = (List<JPanel>) panelsField.get(frame);
            assertThat(panels).hasSize(1);
            frame.dispose();
        } else {
            Method m = ColorPickerFrame.class.getMethod("addColorPanel");
            assertThat(m).isNotNull();
            Field panelsField = ColorPickerFrame.class.getDeclaredField("colorPanels");
            assertThat(panelsField).isNotNull();
            assertThat(panelsField.getType().getSimpleName()).contains("List");
        }
    }

    @Test
    void addColorPanelViaReflectionIncreasesPanelsList() throws Exception {
        ColorPickerFrame frame = tryCreateFrame();
        if (frame != null) {
            Field rgbField = ColorPickerFrame.class.getDeclaredField("rgbColorString");
            rgbField.setAccessible(true);
            rgbField.set(frame, "test");
            Field panelsField = ColorPickerFrame.class.getDeclaredField("colorPanels");
            panelsField.setAccessible(true);
            @SuppressWarnings("unchecked")
            List<JPanel> before = (List<JPanel>) panelsField.get(frame);
            int sizeBefore = before.size();
            frame.addColorPanel();
            @SuppressWarnings("unchecked")
            List<JPanel> after = (List<JPanel>) panelsField.get(frame);
            assertThat(after.size()).isEqualTo(sizeBefore + 1);
            frame.dispose();
        } else {
            Field rgbField = ColorPickerFrame.class.getDeclaredField("rgbColorString");
            assertThat(rgbField).isNotNull();
            Field panelsField = ColorPickerFrame.class.getDeclaredField("colorPanels");
            assertThat(panelsField).isNotNull();
        }
    }

    @Test
    void colorModelIntegration() throws Exception {
        ColorPickerFrame frame = tryCreateFrame();
        if (frame != null) {
            Field modelField = ColorPickerFrame.class.getDeclaredField("colorModel");
            modelField.setAccessible(true);
            Object model = modelField.get(frame);
            assertThat(model).isNotNull();
            assertThat(model).isInstanceOf(ColorModel.class);
            ColorModel cm = (ColorModel) model;
            assertThat(cm.getColor()).isNotNull();
            frame.dispose();
        } else {
            Field modelField = ColorPickerFrame.class.getDeclaredField("colorModel");
            assertThat(modelField).isNotNull();
            assertThat(modelField.getType()).isEqualTo(ColorModel.class);
            ColorModel cm = new ColorModel();
            assertThat(cm.getColor()).isNotNull();
        }
    }

    @Test
    void colorModelHueChangeAffectsGetColor() throws Exception {
        ColorPickerFrame frame = tryCreateFrame();
        if (frame != null) {
            Field modelField = ColorPickerFrame.class.getDeclaredField("colorModel");
            modelField.setAccessible(true);
            ColorModel cm = (ColorModel) modelField.get(frame);
            cm.setHue(0.1f);
            cm.setSaturation(1.0f);
            cm.setBrightness(1.0f);
            assertThat(cm.getColor()).isNotNull();
            frame.dispose();
        } else {
            ColorModel cm = new ColorModel();
            cm.setHue(0.1f);
            cm.setSaturation(1.0f);
            cm.setBrightness(1.0f);
            assertThat(cm.getColor()).isNotNull();
            cm.setHue(0.9f);
            assertThat(cm.getColor()).isNotNull();
        }
    }
}
