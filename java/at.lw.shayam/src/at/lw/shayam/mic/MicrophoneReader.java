package at.lw.shayam.mic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class MicrophoneReader {
	private static final int BUFFER_SIZE = 4094;

	private class MicrophoneReaderThread extends Thread {

		TargetDataLine line;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[BUFFER_SIZE];
		boolean running;

		public MicrophoneReaderThread(TargetDataLine line) {
			this.line = line;
		}

		@Override
		public void run() {
			running = true;
			readInput();
		}

		private ByteArrayOutputStream readInput() {

			long start = System.currentTimeMillis();
			try {
				while (running) {
					int count = line.read(buffer, 0, buffer.length);
					if (count > 0) {
						out.reset();
						out.write(buffer, 0, count);
					}
				}
				out.close();
			} catch (IOException e) {
				System.err.println("I/O problems: " + e);
				System.exit(-1);
			}
			return out;
		}

		public synchronized void stopRecording() {
			running = false;
		}

		public OutputStream getOutputStream() {
			return out;
		}
	}

	public OutputStream read() throws LineUnavailableException {
		final AudioFormat format = getFormat();// Fill AudioFormat with the wanted settings

		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
		line.open(format);
		line.start();

		MicrophoneReaderThread thread = new MicrophoneReaderThread(line);

		OutputStream out = thread.getOutputStream();
		thread.start();

		return out;

	}

	private AudioFormat getFormat() {
		float sampleRate = 44100;
		int sampleSizeInBits = 8;
		int channels = 1; // mono
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

}
