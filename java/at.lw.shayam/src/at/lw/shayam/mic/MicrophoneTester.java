package at.lw.shayam.mic;

import javax.sound.sampled.LineUnavailableException;

public class MicrophoneTester {
	public static void main(String[] args) throws LineUnavailableException {
		MicrophoneReader reader = new MicrophoneReader();
		reader.read();
	}
}
