package com.colorpicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ColorPickerFrame extends JFrame {

	private static final long serialVersionUID = 8898424205695958845L;

	private static final ImageIcon infoIcon;

	static {
		URL imgUrl = ColorPickerFrame.class.getResource("/qwerty.png");
		if (imgUrl != null) {
			infoIcon = new ImageIcon(imgUrl);
		} else {
			infoIcon = null;
		}
	}

	private JLabel statusbar, devName;
	private JPanel mousepanel;

	private float hue, saturation;
	private float brightness = 0.5f;
	private String rgbColorString;

	private List<JPanel> colorPanels = new ArrayList<>();

	public ColorPickerFrame() {
		super("ColorPicker");
		mousepanel = new JPanel();
		mousepanel.setLayout(new BorderLayout());

		add(mousepanel, BorderLayout.CENTER);

		statusbar = new JLabel("◄ HUE ►  ||  ▲ SATURATION ▼  ||  MouseWheelRotation/Scrolling: BRIGHTNESS  ||  Left_Click: RGB Value of Selected Color");
	    statusbar.setHorizontalAlignment(SwingConstants.CENTER);

	    devName = new JLabel("by:- Rahul Jain");
	    devName.setHorizontalAlignment(SwingConstants.RIGHT);

		mousepanel.add(statusbar, BorderLayout.NORTH);
		mousepanel.add(devName, BorderLayout.SOUTH);

		ColorMouseListener mc = new ColorMouseListener();
		mousepanel.addMouseListener(mc);
		mousepanel.addMouseMotionListener(mc);
		mousepanel.addMouseWheelListener(mc);
	}

	public void addColorPanel() {
        JPanel newPanel = new JPanel();
        newPanel.setBackground(Color.getHSBColor(hue, saturation, brightness));
        newPanel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(rgbColorString);
		textArea.setEditable(false);
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(Color.getHSBColor(hue, saturation, brightness));
		newPanel.add(textArea, BorderLayout.CENTER);

        colorPanels.add(newPanel);

        // Update layout to include the new panel in the grid
        // The grid has 1 row, and columns = number of color panels + 1 (for mousepanel)
        if (getContentPane().getLayout() instanceof GridLayout) {
            ((GridLayout) getContentPane().getLayout()).setColumns(colorPanels.size() + 1);
        } else {
            GridLayout gl = new GridLayout(1, colorPanels.size() + 1);
            this.setLayout(gl);
        }

        add(newPanel);

        validate();
        repaint();
    }

	private class ColorMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

	    public void updateColor(MouseEvent e) {
            // Update hue and saturation based on mouse position relative to the panel
            int width = mousepanel.getWidth();
            int height = mousepanel.getHeight();

            if (width > 0 && height > 0) {
                hue = (float)e.getX() / width;
                saturation = (float)e.getY() / height;

                // Clamp values to 0.0 - 1.0 just in case
                hue = Math.max(0.0f, Math.min(1.0f, hue));
                saturation = Math.max(0.0f, Math.min(1.0f, saturation));
            }

			Color c = Color.getHSBColor(hue, saturation, brightness);
			mousepanel.setBackground(c);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			updateColor(e);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
            // Adjust brightness
            // e.getWheelRotation() returns negative for "up" (away from user) and positive for "down" (towards user).
            // Typically, scrolling up (away) might increase a value, but original code:
            // direction UP (rot < 0) -> b += change.
            // direction DOWN (rot > 0) -> b -= change.
            // Wait, original code:
            // if (direction == UP) b += changeInB;
            // UP was defined as abs(rot) > 0 ? UP : DOWN. Wait.
            // Original:
            // int direction = (Math.abs(countWheelRotations) > 0) ? UP : DOWN;
            // changeInB = (float)countWheelRotations/100;
            // if (direction == UP) b += changeInB; else b -= changeInB;

            // If rot = -1. abs(-1) = 1 > 0 -> UP. change = -0.01. b += -0.01. b decreases.
            // If rot = 1. abs(1) = 1 > 0 -> UP. change = 0.01. b += 0.01. b increases.
            // So scrolling up (away) decreases brightness. Scrolling down (towards) increases brightness.

            float change = (float)e.getWheelRotation() / 100.0f;
            brightness += change;

            // Clamp brightness between 0.0 and 1.0
            brightness = Math.max(0.0f, Math.min(1.0f, brightness));

	        updateColor(e);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int rgb = Color.HSBtoRGB(hue, saturation, brightness);
			Color properRGB = new Color(rgb);

			rgbColorString = ColorUtils.formatColorInfo(properRGB);

			addColorPanel();

			JTextArea textArea = new JTextArea(rgbColorString);
			String devInfo = "\n\n\t©SLX";
			textArea.append(devInfo);

			textArea.setEditable(false);
			textArea.setBackground(new Color(236,236,236));

			JOptionPane.showMessageDialog(null,
										textArea,
										"Color Info",
										JOptionPane.INFORMATION_MESSAGE,
										infoIcon );
		}

		@Override
		public void mouseDragged(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

	}
}
