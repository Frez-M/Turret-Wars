package com.frez.turretwars.resources;

import com.frez.turretwars.*;
import com.frez.turretwars.animation.*;
import java.util.*;
import com.badlogic.gdx.graphics.*;

public class Animations {
	
	private static Map<String, Animation> anims;
	
	static {
		anims = new HashMap<String, Animation>();
	}
	
	public static void load(String name, Animation anim) {
		GameLoop.log("loading animation \"" + name + "\"...", Color.WHITE, TimeUtils.getRawSeconds(5));
		anims.put(name, anim);
	}
	
	public static Animation get(String name) {
		return anims.get(name);
	}
	
	public static void unloadAll() {
		anims.clear();
	}
	
}
