package com.frez.turretwars.utils;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;

public class InputUtils {
	
	private static Vector2[] pointers;
	public static Vector2 getInputPos(int pointer) {
		if (pointers == null) {
			pointers = new Vector2[10];
			for (int i = 0; i < 10; i ++)
				pointers[i] = new Vector2();
		}
		
		pointers[pointer].set(Gdx.input.getX(pointer), Gdx.input.getY(pointer));
		return pointers[pointer];
	}
	
	public static void d() {
		InputProcessor ip = new InputProcessor() {
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
				
				return false;
			}
			public boolean touchUp(int x, int y, int pointer, int button) {
				
				return false;
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
		};
	}
	
}
