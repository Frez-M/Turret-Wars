package com.frez.turretwars.utils;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import java.util.*;

public class InputUtils {
	

	private static Vector2[] pointers;
	private static ArrayList<InputProcessor> ipArray;
	
	private static boolean inited = false;
	public static void init() {
		if (inited) return;
		inited = true;
		
		pointers = new Vector2[10];
		for (int i = 0; i < 10; i ++)
			pointers[i] = new Vector2();
		
		ipArray = new ArrayList<InputProcessor>();
		Gdx.input.setInputProcessor(new InputProcessor() {
			public boolean keyDown(int keycode) {
				for (InputProcessor ip : ipArray) {
					ip.keyDown(keycode);
				}
				return true;
			}
			public boolean keyUp(int keycode) {
				for (InputProcessor ip : ipArray) {
					ip.keyUp(keycode);
				}
				return true;
			}
			public boolean keyTyped(char character) {
				for (InputProcessor ip : ipArray) {
					ip.keyTyped(character);
				}
				return true;
			}
			public boolean touchDown(int x, int y, int pointer, int button) {
				for (InputProcessor ip : ipArray) {
					ip.touchDown(x, y, pointer, button);
				}
				return true;
			}
			public boolean touchUp(int x, int y, int pointer, int button) {
				for (InputProcessor ip : ipArray) {
					ip.touchUp(x, y, pointer, button);
				}
				return true;
			}
			public boolean touchDragged(int x, int y, int pointer) {
				for (InputProcessor ip : ipArray) {
					ip.touchDragged(x, y, pointer);
				}
				return true;
			}
			public boolean mouseMoved(int x, int y) {
				for (InputProcessor ip : ipArray) {
					ip.mouseMoved(x, y);
				}
				return true;
			}
			public boolean scrolled(int amount) {
				for (InputProcessor ip : ipArray) {
					ip.scrolled(amount);
				}
				return true;
			}
		});
	}
	
	public static Vector2 getRawInputPos(int pointer) {
		pointers[pointer].set(Gdx.input.getX(pointer), Gdx.input.getY(pointer));
		return pointers[pointer];
	}
	
	public static Vector2 getInputPos(int pointer) {
		Vector2 temp = new Vector2(getRawInputPos(pointer));
		temp.y = Gdx.graphics.getHeight() - temp.y;
		temp.scl(1f / Gdx.graphics.getDensity());
		return temp;
	}
	
	public static void addInputProcessor(InputProcessor ip) {
		ipArray.add(ip);
	}
	
	public static void removeInputProcessor(InputProcessor ip) {
		ipArray.remove(ip);
	}
	
	public static void dispose() {
		inited = false;
		pointers = null;
		ipArray = null;
	}
	
	
}
