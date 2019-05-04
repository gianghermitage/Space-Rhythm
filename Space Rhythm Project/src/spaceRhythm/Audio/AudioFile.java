package spaceRhythm.Audio;

import javax.sound.sampled.*;
import java.io.File;

public class AudioFile {
    private File soundFile;
    private AudioInputStream ais;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip clip;
    private FloatControl gainControl;
    private boolean playing;

    public AudioFile(String filename) {
        soundFile = new File(filename);
        try {
            ais = AudioSystem.getAudioInputStream(soundFile);
            format = ais.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);
            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void play() {
        gainControl.setValue(-10);
        clip.start();
        playing = true;
    }

}
