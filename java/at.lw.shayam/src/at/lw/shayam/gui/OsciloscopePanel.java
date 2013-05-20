package at.lw.shayam.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.log4j.Logger;

public class OsciloscopePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int BUFFER_SIZE = 1024;
	private static final int REPAINT_DELAY = 200;

	private static final float minmax = 128f;

	OutputStream outputStream;
	byte[] buffer = new byte[BUFFER_SIZE];

	private static Logger logger = Logger.getLogger(OsciloscopePanel.class);

	public OsciloscopePanel(OutputStream outputStream) {
		this.outputStream = outputStream;

		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				repaint();
			}

		};
		new Timer(REPAINT_DELAY, taskPerformer).start();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		try {

			drawOsciloscope(g2d);
		} catch (IOException e) {
			logger.error("Error reading InputStream", e);
		}

	}

	private void drawOsciloscope(Graphics2D g2d) throws IOException {
		g2d.setColor(Color.blue);

		// int w = (getWidth() - getInsets().left - getInsets().right);
		int h = (getHeight() - getInsets().left - getInsets().bottom);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
		for (int count = inputStream.read(buffer); count >= 0; count = inputStream.read(buffer)) {
			for (int offset = 0; offset < BUFFER_SIZE; offset++) {
				float scale = ((h ) / (minmax * 2));
				int y = (int) ((buffer[offset] * scale) + h/2);
				logger.info("scale: " + scale + " h:" + h + " y: " + y + " value: " + buffer[offset]);
				g2d.drawLine(offset, y, offset, y);
			}

		}
	}
}
