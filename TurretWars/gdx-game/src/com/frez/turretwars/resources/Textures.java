package com.frez.turretwars.resources;

import com.badlogic.gdx.graphics.*;
import java.util.*;
import com.badlogic.gdx.*;
import com.frez.turretwars.*;

public class Textures {
	
	private static Map<String, Texture> textures;
	
	static {
		textures = new HashMap<String, Texture>();
	}
	
	public static void load(String name, String file) {
		GameLoop.log("loading texture \"" + name + "\"...", Color.WHITE, TimeUtils.getRawSeconds(5));
		Texture temp = new Texture(Gdx.files.internal("sprites/" + file));
		temp.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		temp.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		textures.put(name, temp);
		
	}
	
	public static Texture get(String name) {
		return textures.get(name);
	}
	
	public static void unloadAll() {
		for (Map.Entry<String, Texture> e : textures.entrySet()) {
			e.getValue().dispose();
		}
	}
	
}
