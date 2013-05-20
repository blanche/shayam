package at.lw.shayam;

import java.io.ByteArrayOutputStream;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

public class AudioAnalysis {

	

	public Complex[][] fft(ByteArrayOutputStream out) {
		byte audio[] = out.toByteArray();

		final int totalSize = audio.length;

		int amountPossible = totalSize / Harvester.CHUNK_SIZE;

		// When turning into frequency domain we'll need complex numbers:
		Complex[][] results = new Complex[amountPossible][];

		// For all the chunks:
		for (int times = 0; times < amountPossible; times++) {
			Complex[] complex = new Complex[Harvester.CHUNK_SIZE];
			for (int i = 0; i < Harvester.CHUNK_SIZE; i++) {
				// Put the time domain data into a complex number with imaginary part as 0:
				complex[i] = new Complex(audio[(times * Harvester.CHUNK_SIZE) + i], 0);
			}
			// Perform FFT analysis on the chunk:
			FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);
			results[times] = fft.transform(complex, TransformType.FORWARD);
		}
		return results;

	}

}
