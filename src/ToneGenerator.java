import javax.sound.sampled.*;

public class ToneGenerator {
    private static final int duration = 3;

    private static final float sampleRate = 44100;
    private static final int sampleSize = 8;
    private static final int channels = 1;          // monophonic channel
    private static final boolean signed = true;     // allows positive and negative signal amplitude
    private static final boolean endian = false;    // use big-endian byte order

    public static void main(String[] args) {
        try {
            generatorTone(440, 100);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void generatorTone(int frequency, int volume) throws LineUnavailableException {
        AudioFormat audioFormat = new AudioFormat(sampleRate, sampleSize, channels, signed, endian);

        SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();

        writeToLine(sourceDataLine, frequency, volume);

        sourceDataLine.drain();
        sourceDataLine.stop();
        sourceDataLine.close();
    }

    private static void writeToLine(SourceDataLine sourceDataLine, int frequency, int volume) {
        byte[] buff = new byte[1];       // write() requires buffer array

        // write samples for a given amount of seconds
        for (int k = 0; k < duration; k++) {
            for (int i = 0; i < sampleRate; i++) {
                double time = i / sampleRate;
                // x = A * sin(wt), where w is the angular frequency
                buff[0] = (byte) (volume * Math.sin(2 * Math.PI * frequency * time));

                // buff, offset, length
                sourceDataLine.write(buff, 0, 1);
            }
        }
    }
}
