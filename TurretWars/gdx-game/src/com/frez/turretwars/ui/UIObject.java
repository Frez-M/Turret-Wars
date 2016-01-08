package com.frez.turretwars.ui;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;

public abstract class UIObject {
	
	public static final Color COLOR_DEFAULT = new Color(0f, 1f, 0.3f, 1f);
	
	public static final int STATE_IDLE = 0;
	public static final int STATE_PRESSED = 1;
	
	protected Vector2 pos, size;
	protected String text, subText;
	protected Color color;
	protected boolean upsideDown, pressed;
	protected int state, pointer;
	protected boolean  enabled;
	
	public UIObject() {
		pos = new Vector2();
		size = new Vector2();
		color = COLOR_DEFAULT;
		text = "";
		subText = "";
		state = STATE_IDLE;
		enabled = true;
	}
	
	public abstract void update();
	public abstract void draw();
	public abstract void onPress();
	public abstract void onClick();
	
	public abstract boolean testPoint(Vector2 point);
	
	public int lastPointer() {
		return pointer;
	}
	
	public void changeState(int state, int pointer) {
		this.state = state;
		this.pointer = pointer;
	}
	
	public void updateRaw() {
		if (pressed) {
			if (state == STATE_IDLE) {
				pressed = false;
				onClick();
			}
		} else {
			if (state == STATE_PRESSED) {
				pressed = true;
				onPress();
			}
		}
	}
	
	public boolean isPressed() {
		return pressed;
	}
	
	public Vector2 getPos() {
		return pos;
	}
	
	public void setPos(Vector2 pos) {
		this.pos = pos;
	}
	
	public Vector2 getSize() {
		return size;
	}
	
	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getSubText() {
		return subText;
	}
	
	public void setSubText(String subText) {
		this.subText = subText;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean isUpsideDown() {
		return upsideDown;
	}
	
	public void setUpsideDown(boolean upsideDown) {
		this.upsideDown = upsideDown;
	}
	
}
