package CoreGame;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayer {

	// Fields
	private Clip clip;
	private Long currentFrame;
	private String status;
    private static final String filePath = "/assets/ost/raceToTheTop.wav";

	// Constructor to initialize streams and clip
	public SoundPlayer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		// Load the audio file as a resource stream
		InputStream audioSrc = getClass().getResourceAsStream(filePath);

		if (audioSrc == null) {
			System.out.println("raceToTheTop.wav not found in resources.");
			return;
		}

		// Convert the input stream to AudioInputStream
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioSrc);

		// Create and open the clip
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	// Play method
	public void play() {
		if (clip != null) {
			clip.start();
			status = "play";
		}
	}

	// Pause method
	public void pause() {
		if (status.equals("paused")) {
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}

	// Resume method
	public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (status.equals("play")) {
			System.out.println("Audio is already being played");
			return;
		}
		clip.setMicrosecondPosition(currentFrame);
		this.play();
	}

	// Restart method
	public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
		clip.stop();
		currentFrame = 0L;
		clip.setMicrosecondPosition(0);
		this.play();
	}

	// Stop method
	public void stop() {
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}

	// Jump to specific position
	public void jump(long c) {
		if (c > 0 && c < clip.getMicrosecondLength()) {
			clip.setMicrosecondPosition(c);
			this.play();
		}
	}
}
