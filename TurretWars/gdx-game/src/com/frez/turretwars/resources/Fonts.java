package com.frez.turretwars.resources;

import com.badlogic.gdx.graphics.g2d.*;
import java.util.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.*;

public class Fonts {
	
	private static Map<String, BitmapFont> fonts;

	static {
		fonts = new HashMap<String, BitmapFont>();
	}

	public static void load(String name, String file, int size) {
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/" + file));
		FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
		params.size = (int) (size * Gdx.graphics.getDensity());
		params.minFilter = Texture.TextureFilter.Linear;
		params.magFilter = Texture.TextureFilter.Linear;
		params.genMipMaps = true;
		params.borderColor = Color.BLACK;
		params.borderWidth = Gdx.graphics.getDensity();
		params.borderStraight = false;
		
		BitmapFont temp = gen.generateFont(params);
		temp.getData().setScale(1f/Gdx.graphics.getDensity());
		fonts.put(name, temp);
	}

	public static BitmapFont get(String name) {
		return fonts.get(name);
	}
	
	public static void unloadAll() {
		for (Map.Entry<String, BitmapFont> e : fonts.entrySet()) {
			e.getValue().dispose();
		}
	}
}
