package at.lw.shayam.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.OutputStream;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;

import at.lw.shayam.mic.MicrophoneReader;

public class ShayamMain {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Sayam");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addOsciloscopePanel(frame);

		// Center Window
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
		frame.setBackground(Color.WHITE);
	    frame.setSize(250, 200);
		// Display the window.
		frame.setVisible(true);
	}

	private static void addOsciloscopePanel(JFrame frame) {
		try {
			MicrophoneReader microphoneReader = new MicrophoneReader();
			OutputStream microphoneOutputStream;
			microphoneOutputStream = microphoneReader.read();
			OsciloscopePanel osciloscopePanel = new OsciloscopePanel(microphoneOutputStream);
			frame.getContentPane().add(osciloscopePanel);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
