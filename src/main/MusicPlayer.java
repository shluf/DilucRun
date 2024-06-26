
package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private Clip audioClip;
    private FloatControl volumeControl;
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private long clipPosition = 0;

    public MusicPlayer(String filePath) {
        try {
            // Open the audio file
            File audioFile = new File("src/assets/bgm/" + filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Get a clip resource
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);

            // Get the volume control
            volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play the music
    public void play() {
        if (isPaused) {
            audioClip.setMicrosecondPosition(clipPosition);
            isPaused = false;
        }
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        audioClip.start();
        isPlaying = true;
    }

    // Pause the music
    public void pause() {
        if (isPlaying) {
            clipPosition = audioClip.getMicrosecondPosition();
            audioClip.stop();
            isPaused = true;
            isPlaying = false;
        }
    }

    // Stop the music
    public void stop() {
        if (isPlaying || isPaused) {
            audioClip.stop();
            audioClip.setMicrosecondPosition(0);
            isPaused = false;
            isPlaying = false;
        }
    }

    // Set the volume (0.0 to 1.0)
    public void setVolume(float volume) {
        if (volume < 0.0f || volume > 1.0f) {
            throw new IllegalArgumentException("Volume not valid: " + volume);
        }
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        volumeControl.setValue(min + (max - min) * volume);
    }

    // Close resources
    public void close() {
        audioClip.close();
    }
}
