package com.colorpicker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.Serial;
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

	@Serial
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

	private final ColorModel colorModel = new ColorModel();
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
        newPanel.setBackground(colorModel.getColor());
        newPanel.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(rgbColorString);
		textArea.setEditable(false);
		textArea.setForeground(Color.BLACK);
		textArea.setBackground(colorModel.getColor());
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
            int width = mousepanel.getWidth();
            int height = mousepanel.getHeight();

            if (width > 0 && height > 0) {
                colorModel.setHue((float)e.getX() / width);
                colorModel.setSaturation((float)e.getY() / height);
            }

			Color c = colorModel.getColor();
			mousepanel.setBackground(c);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			updateColor(e);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
            float change = (float)e.getWheelRotation() / 100.0f;
            colorModel.setBrightness(colorModel.getBrightness() + change);

	        updateColor(e);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int rgb = Color.HSBtoRGB(colorModel.getHue(), colorModel.getSaturation(), colorModel.getBrightness());
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
