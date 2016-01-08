package com.frez.turretwars;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.frez.turretwars.resources.*;
import com.frez.turretwars.utils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.glutils.*;

public class GameLoop implements ApplicationListener {
	
	private GameManager manager;
	
	private static String[] debug;
	private static long[] debugTime;
	private static int debugLast;
	private static String debugText;
	
	@Override
	public void create() {
		debug = new String[64];
		debugTime = new long[64];
		
		Textures.load("ground", "textures/ground/diffuse.png");
		Textures.load("wall", "textures/wall/diffuse.png");
		
		Textures.load("player_red", "p1.png");
		Textures.load("player_blue", "p2.png");
		Textures.load("player", "player.png");
		Textures.load("player_torso", "player_torso.png");
		
		Textures.load("turret_base", "turret_base.png");
		
		Textures.load("turret_mv42_head", "turret_mv42_head.png");
		Textures.load("turret_mv42_arm", "turret_mv42_arm.png");
		
		Fonts.load("default", "WarfaceRegularRussian.ttf", 32);
		Fonts.load("default_small", "WarfaceRegularRussian.ttf", 16);
		Fonts.load("debug", "consola.ttf", 8);
		
		Fonts.load("ui", "WarfaceRegularRussian.ttf", 8);
		//Fonts.load("ui_small", "WarfaceRegularRussian.ttf", 4);
		
		manager = GameManager.newInstance();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Renderer.update();
		
		manager.update();
		
		manager.render();
		
		BitmapFont font = Fonts.get("debug");
		
		debug("Screen density: " + Gdx.graphics.getDensity(), false);
		/*
		for (int i = 0; i < 10; i ++) {
			Vector2 p = InputUtils.getInputPos(i);
			debug = debug + "\n#" + i + ": " + p.toString();
		}*/
		float dHeight = font.getLineHeight() * debugText.split("\n").length;
		
		Renderer.getSRUI().begin(ShapeRenderer.ShapeType.Filled);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Renderer.getSRUI().setColor(0, 0, 0, 0.5f);
		Renderer.getSRUI().rect(8, Renderer.getUIHeight() - dHeight - 8, 128, dHeight);
		Renderer.getSRUI().setColor(Color.WHITE);
		Renderer.getSRUI().end();
		
		Renderer.getSBUI().begin();
		font.draw(Renderer.getSBUI(), debugText, 8, Renderer.getUIHeight() - 8);
		Renderer.getSBUI().end();
		
		debugText = "---* DEBUG *---";
		
	}

	@Override
	public void dispose() {
		Fonts.unloadAll();
		Textures.unloadAll();
		Texture.clearAllTextures(Gdx.app);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
	public static void debug(String text, boolean oneTimeInfo) {
		if (oneTimeInfo) {
			debug[debugLast] = text;
			debugTime[debugLast] = System.currentTimeMillis();
				
			debugLast ++;
			if (debugLast == 64)
				debugLast = 0;
		} else {
			debugText = debugText + "\n" + text;
		}
	}
}
