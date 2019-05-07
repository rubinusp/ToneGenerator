import javax.sound.sampled.AudioSystem;

public class ToneGenerator {
    public static void main(String[] args) {
        System.out.println(AudioSystem.getMixerInfo()[0]);
    }
}
