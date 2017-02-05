//Animation manages animations and frame information
import java.awt.image.*;

public class Animation {
	
	private BufferedImage[] frames;
	private int currentFrame; //the current frame of animation your character is in
	
	private long startTime; //reference point for time
	private long delay;// amount of time between frames
	
	public Animation() {}
	
	public void setFrames(BufferedImage[] images) { //set the animation you are using
		frames = images;
		if(currentFrame >= frames.length) currentFrame = 0; //if you hit the number of total frames reset it
	}
	
	public void setDelay(long d) { //set the delay between frames
		delay = d;
	}
	
	public void update() {//update the time and frame of the animation
		
		if(delay == -1) return;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000; // triggers every second
		if(elapsed > delay) {//once elapsed time reaches your delay reset the reference and time 
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length) { //reset
			currentFrame = 0;
		}
		
	}
	
	public BufferedImage getImage() {
		return frames[currentFrame];
	}
	
}