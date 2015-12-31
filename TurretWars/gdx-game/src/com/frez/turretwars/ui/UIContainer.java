package com.frez.turretwars.ui;
import java.util.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;

public class UIContainer {
	
	private ArrayList<UIObject> obj;
	
	public UIContainer() {
		obj = new ArrayList<UIObject>();
	}
	
	public void addUIObject(UIObject obj) {
		this.obj.add(obj);
	}
	
	public void removeUIObject(UIObject obj) {
		this.obj.remove(obj);
	}
	
	public void update() {
		Vector2 point = new Vector2(Gdx.input.getX() / Gdx.graphics.getDensity(), Gdx.input.getY() / Gdx.graphics.getDensity());
		UIObject target = null;
		for (UIObject o : obj) {
			if (o.testPoint(point)) {
				target = o;
				break;
			}
		}
		for (UIObject o : obj) {
			boolean h = target != null ? target == o : false;
			o.update(h, Gdx.input.isTouched());
		}
	}
	
	public void draw() {
		for (UIObject o : obj) {
			o.draw();
		}
	}
	
}
