package com.frez.turretwars.animation;

import com.badlogic.gdx.math.*;
import java.util.*;

public class Animator {
	
	private Map<String, PlayingAnimation> anims;
	private ArrayList<PlayingAnimation> currentlyPlaying;

	private Vector2 lastCurrent, defaultState;
	private boolean resetCurrentWhenFinished;
	
	public Animator(Vector2 defaultState, boolean resetWhenFinished) {
		anims = new HashMap<String, PlayingAnimation>();
		currentlyPlaying = new ArrayList<PlayingAnimation>();
		this.defaultState = defaultState;
		this.resetCurrentWhenFinished = resetWhenFinished;
	}
	
	public void play(String name) {
		currentlyPlaying.add(anims.get(name).start());
	}
	
	public void pause(String name) {
		anims.get(name).playing = false;
	}
	
	public void resume(String name) {
		anims.get(name).resume();
	}
	
	public void stop(String name) {
		currentlyPlaying.remove(anims.get(name));
	}
	
	public void update() {
		
		if (!currentlyPlaying.isEmpty()) lastCurrent.setZero();
		else if (resetCurrentWhenFinished) lastCurrent = new Vector2(defaultState);
		
		for (PlayingAnimation a : currentlyPlaying) {
			
			if (a.playing) {
				a.timePos = System.currentTimeMillis() - a.timeStarted;
				
				if (a.timePos >= a.anim.totalTime) {
					if (a.anim.doesLoop()) {
						a.start();
					} else {
						currentlyPlaying.remove(a);
					}
				}
			}
			lastCurrent.add(a.anim.get(a.timePos));
			
		}
		
	}
	
	public Vector2 get() {
		return lastCurrent;
	}
	
	private static class PlayingAnimation {
		
		public long timeStarted, timePos;
		public boolean playing;
		public Animation anim;
		
		public PlayingAnimation(Animation anim) {
			this.timeStarted = 0;
			this.timePos = 0;
			this.playing = false;
			this.anim = anim;
		}
		
		public PlayingAnimation start() {
			timeStarted = System.currentTimeMillis();
			timePos = 0;
			playing = true;
			return this;
		}
		
		public PlayingAnimation resume() {
			timeStarted = System.currentTimeMillis();
			playing = true;
			return this;
		}
		
	}
	
}
