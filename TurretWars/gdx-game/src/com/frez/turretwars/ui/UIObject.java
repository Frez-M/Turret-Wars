package com.frez.turretwars.ui;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;

public abstract class UIObject {
	
	public Vector2 pos, size;
	public String text, subText;
	public Color color;
	
	public UIObject() {
		pos = new Vector2();
		size = new Vector2();
		text = "";
		subText = "";
	}
	
	public abstract void update(boolean hovered, boolean pressed);
	public abstract void draw();
	
	public abstract boolean testPoint(Vector2 point);
	
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
	
}
