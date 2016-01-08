package com.frez.turretwars.animation;

import com.badlogic.gdx.math.*;
import java.util.*;

public class Animator {
	
	private Map<String, PlayingAnimation> anims;
	private ArrayList<PlayingAnimation> currentlyPlaying;

	private Vector2 lastRawCurrent, lastCurrent, defaultState;
	private float delay;
	private boolean resetCurrentWhenFinished;
	
	public Animator(Vector2 defaultState, float delay, boolean resetWhenFinished) {
		anims = new HashMap<String, PlayingAnimation>();
		currentlyPlaying = new ArrayList<PlayingAnimation>();
		this.defaultState = defaultState;
		this.delay = delay;
		this.resetCurrentWhenFinished = resetWhenFinished;
		lastRawCurrent = new Vector2(defaultState);
		lastCurrent = new Vector2(defaultState);
		
	}
	
	public Animator(Vector2 defaultState, boolean resetWhenFinished) {
		anims = new HashMap<String, PlayingAnimation>();
		currentlyPlaying = new ArrayList<PlayingAnimation>();
		this.defaultState = defaultState;
		this.delay = 5;
		this.resetCurrentWhenFinished = resetWhenFinished;
		lastRawCurrent = new Vector2(defaultState);
		lastCurrent = new Vector2(defaultState);
		
	}
	
	public void play(String name) {
		PlayingAnimation pa = anims.get(name);
		if (pa != null && !currentlyPlaying.contains(pa))
			currentlyPlaying.add(pa.start());
	}
	
	public void pause(String name) {
		PlayingAnimation pa = anims.get(name);
		if (pa != null) pa.playing = false;
	}
	
	public void resume(String name) {
		PlayingAnimation pa = anims.get(name);
		if (pa != null) pa.resume();
	}
	
	public void stop(String name) {
		currentlyPlaying.remove(anims.get(name));
	}
	
	public void setPlaySpeed(String name, float playSpeed) {
		PlayingAnimation pa = anims.get(name);
		if (pa != null)
			pa.playSpeed = playSpeed;
	}
	
	public void setPlaySpeed(float playSpeed) {
		for (Map.Entry<String, PlayingAnimation> e : anims.entrySet())
			if (e.getValue() != null)
				e.getValue().playSpeed = playSpeed;
	}
	
	public void update() {
		
		if (!currentlyPlaying.isEmpty()) lastRawCurrent.setZero();
		else if (resetCurrentWhenFinished) lastRawCurrent = new Vector2(defaultState);
		
		for (PlayingAnimation a : currentlyPlaying) {
			if (a.playing) {
				a.timePos = System.currentTimeMillis() - a.timeStarted;
				if (a.playSpeed != 1) {
				//	a.timeStarted += a.timePos * (1 - a.playSpeed);
				}
				if (a.timePos >= a.anim.totalTime) {
					if (a.anim.doesLoop()) {
						a.start();
						//a.timePos -= a.anim.totalTime;
						//a.timeStarted = System.currentTimeMillis() - a.timePos;
					} else {
						currentlyPlaying.remove(a);
					}
				}
			}
			try {
				lastRawCurrent.add(a.anim.get(a.timePos));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		lastCurrent.add(new Vector2(lastRawCurrent).sub(lastCurrent).scl(1/delay));
	}
	
	public Vector2 get() {
		return lastCurrent;
	}
	
	public void addAnimation(String name, Animation anim) {
		anims.put(name, new PlayingAnimation(anim));
	}
	
	public void removeAnimation(String name) {
		stop(name);
		anims.remove(name);
	}
	
	private static class PlayingAnimation {
		
		public long timeStarted, timePos;
		public float playSpeed;
		public boolean playing;
		public Animation anim;
		
		public PlayingAnimation(Animation anim) {
			this.timeStarted = 0;
			this.timePos = 0;
			this.playSpeed = 1f;
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
