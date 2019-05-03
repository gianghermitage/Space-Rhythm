package spaceRhythm.Audio;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;

public class Sound implements Runnable {

    private ArrayList<String> playList;
    private int currentSongIndex;
    private int currentSongSeek;

    public Sound (String... files) {
        playList = new ArrayList<>();
        for (String file: files) {
            playList.add("./Audio/" + file + ".wav");
        }
    }

    private void playAudio(String filename) {
        try {
            File soundFile = new File(filename);
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10);
            clip.start();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        playAudio(playList.get(currentSongIndex));
    }
}
