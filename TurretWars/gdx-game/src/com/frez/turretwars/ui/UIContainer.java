package com.frez.turretwars.ui;
import java.util.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.frez.turretwars.utils.*;

public class UIContainer {
	
	private ArrayList<UIObject> obj;
	
	public UIContainer() {
		obj = new ArrayList<UIObject>();
		
		InputUtils.addInputProcessor(new InputProcessor() {
			public boolean keyDown(int keycode) {

				return false;
			}
			public boolean keyUp(int keycode) {

				return false;
			}
			public boolean keyTyped(char character) {

				return false;
			}
			public boolean touchDown(int x, int y, int pointer, int button) {
				for (UIObject o : obj) {
					if (o.lastPointer() != -1) continue;
					if (o.testPoint(new Vector2(x / Gdx.graphics.getDensity(), (Gdx.graphics.getHeight() - y) / Gdx.graphics.getDensity()))) {
						o.changeState(UIObject.STATE_PRESSED, pointer);
						break;
					}
				}
				return true;
			}
			public boolean touchUp(int x, int y, int pointer, int button) {
				for (UIObject o : obj) {
					if (o.lastPointer() == pointer) {
						o.changeState(UIObject.STATE_IDLE, -1);
						break;
					}
				}
				return true;
			}
			public boolean touchDragged(int x, int y, int pointer) {

				return false;
			}
			public boolean mouseMoved(int x, int y) {

				return false;
			}
			public boolean scrolled(int amount) {

				return false;
			}
		});
	}
	
	public void addUIObject(UIObject obj) {
		this.obj.add(obj);
	}
	
	public void removeUIObject(UIObject obj) {
		this.obj.remove(obj);
	}
	
	public void update() {
		//Vector2 point = new Vector2(Gdx.input.getX() / Gdx.graphics.getDensity(), (Gdx.graphics.getHeight() - Gdx.input.getY()) / Gdx.graphics.getDensity());
		
		//UIObject target = null;
		/*for (UIObject o : obj) {
			if (o.testPoint(point)) {
				target = o;
				break;
			}
		}*/
		
		for (UIObject o : obj) {
			o.updateRaw();
			o.update();
		}
	}
	
	public void draw() {
		for (UIObject o : obj) {
			o.draw();
		}
	}
	
}
