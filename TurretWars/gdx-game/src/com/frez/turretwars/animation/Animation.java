package com.frez.turretwars.animation;

import com.badlogic.gdx.math.*;
import java.util.*;

public class Animation {
	
	private Keyframe[] keys;
	private boolean loop;
	//private float playSpeed;
	
	//private ArrayList<AnimationListener> listeners;
	//private ArrayList<AnimationOnTimeCallback> callbacks;
	
	public final long totalTime;
	
	public Animation(Keyframe[] keys, boolean loop) {
		this.keys = keys;
		
		this.keys = keys;
		this.loop = loop;
		
		long tempTotalTime = 0;
		for (int i = 0; i < keys.length; i++) {
			tempTotalTime += keys[i].delay;
		}
		totalTime = tempTotalTime;
	}
	
	public Vector2 get(long time) {
		if (keys.length == 1 || time < 0) {
			return keys[0].value;
		}
		long currentTime = 0;
		long currentTime2 = 0;
		for (int i = 0; i < keys.length - 1; i ++) {
			if (i > 0)
				currentTime += keys[i-1].delay;

			currentTime2 = currentTime + keys[i].delay;
			if (time > currentTime2) continue;

			final Keyframe k1 = keys[i];
			final Keyframe k2 = keys[i+1];
			
			if (k1.cp1.isZero() && k1.cp2.isZero())
				return mix(k1.value, k2.value, getInterpolation(smoothstep(currentTime, currentTime2, time), k1));
			else
				return bezierCurveCubic(k1.value, k1.value.cpy().add(k1.cp1), k2.value.cpy().add(k1.cp2), k2.value, getInterpolation(smoothstep(currentTime, currentTime2, time), k1));
			
			/*
			switch (k1.mode) {
				case NONE:{
						return k1.value;
					}
				case LINEAR:{
						return mix(k1.value, k2.value, smoothstep(currentTime, currentTime2, time));
					}
				case EASE_IN:{
						float k = smoothstep(currentTime, currentTime2, time);
						k = (float) Math.pow(k, k1.interpolationParams[0]);
						return mix(k1.value, k2.value, k);
					}
				case EASE_OUT:{
						float k = smoothstep(currentTime, currentTime2, time);
						k = (float) Math.pow(1-k, k1.interpolationParams[0]);
						return mix(k1.value, k2.value, 1 - k);
					}
				case EASE_IN_OUT:{
						float k = smoothstep(currentTime, currentTime2, time);
						float m = 0;
						if (k < 0.5) {
							k = (float) Math.pow(k*2, k1.interpolationParams[0]);
							m = 0.5f * k;
						} else {
							k = (float) Math.pow(1-(k*2-1), k1.interpolationParams[1]);
							m = 0.5f + (0.5f * (1-k));
						}
						return mix(k1.value, k2.value, m);
					}
			}*/
			//break;
		}
		return keys[keys.length-1].value;
	}
	
	private Vector2 bezierCurveCubic(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4, float t) {
		return new Vector2(
			pow(1-t, 3) * p1.x + 3 * pow(1-t, 2) * t * p2.x + 3 * (1-t) * pow(t, 2) * p3.x + pow(t, 3) * p4.x,
			pow(1-t, 3) * p1.y + 3 * pow(1-t, 2) * t * p2.y + 3 * (1-t) * pow(t, 2) * p3.y + pow(t, 3) * p4.y
		);
	}
	
	private float pow(float a, float b) {
		return (float) Math.pow(a, b);
	}
	
	private float getInterpolation(float time, Keyframe k1) {
		switch (k1.mode) {
			case NONE:{
					return 0;
				}
			case LINEAR:{
					return time;
				}
			case EASE_IN:{
					return (float) Math.pow(time, k1.interpolationParams[0]);
				}
			case EASE_OUT:{
					return 1 - (float) Math.pow(1-time, k1.interpolationParams[0]);
				}
			case EASE_IN_OUT:{
					if (time < 0.5) {
						return 0.5f * (float) Math.pow(time*2, k1.interpolationParams[0]);
					} else {
						return 0.5f + (0.5f * (1-(float) Math.pow(1-(time*2-1), k1.interpolationParams[1])));
					}
				}
		}
		
		return 0;
	}
	
	private Vector2 mix(Vector2 a, Vector2 b, float m) {
		float x = ((1f - m) * a.x) + (m * b.x);
		float y = ((1f - m) * a.y) + (m * b.y);
		return new Vector2(x, y);
	}
	
	private float smoothstep(float a, float b, float v) {
		float r = (v - a) / (b - a);
		if (r < 0) r = 0;
		if (r > 1) r = 1;
		return r;
	}
	
	public boolean doesLoop() {
		return loop;
	}
	
	public static class Keyframe {
		
		public final Vector2 value, cp1, cp2;
		public final InterpolationMode mode;
		public final float[] interpolationParams;
		public final long delay;

		public Keyframe(Vector2 value, Vector2 cp1, Vector2 cp2, InterpolationMode mode, float[] interpolationParams, long delay) {
			this.value = value;
			this.cp1 = cp1;
			this.cp2 = cp2;
			this.mode = mode;
			this.interpolationParams = interpolationParams;
			this.delay = delay;
		}

		public static enum InterpolationMode {
			NONE,
			LINEAR,
			EASE_IN, EASE_OUT, EASE_IN_OUT
		}
		
	}
	
}
