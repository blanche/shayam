package at.lw.shayam.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.apache.commons.math3.complex.Complex;

public class SpectrumPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int blockSizeY = 10;
	private static final int blockSizeX = 10;
	private static final boolean logModeEnabled = false;

	Complex[][] results;

	private int size = 50;

	public SpectrumPanel() {
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		drawSpectrum(g2d);

	}

	@SuppressWarnings("unused")
	public void drawSpectrum(Graphics2D g2d) {

		for (int i = 0; i < results.length; i++) {
			int freq = 1;
			for (int line = 1; line < size; line++) {
				// To get the magnitude of the sound at a given frequency slice
				// get the abs() from the complex number.
				// In this case I use Math.log to get a more manageable number (used for color)
				double magnitude = Math.log(results[i][freq].abs() + 1);

				// The more blue in the color the more intensity for a given frequency point:
				g2d.setColor(new Color(0, (int) magnitude * 10, (int) magnitude * 20));
				// Fill:
				g2d.fillRect(i * blockSizeX, (size - line) * blockSizeY, blockSizeX, blockSizeY);

				// I used a improvised logarithmic scale and normal scale:
				if (logModeEnabled && (Math.log10(line) * Math.log10(line)) > 1) {
					freq += (int) (Math.log10(line) * Math.log10(line));
				} else {
					freq++;
				}
			}
		}
	}

	public Complex[][] getResults() {
		return results;
	}

	public void setResults(Complex[][] results) {
		this.results = results;
		repaint();
	}

}
