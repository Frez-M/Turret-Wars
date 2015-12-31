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
	
	@Override
	public void create() {
		Textures.load("ground", "textures/ground/diffuse.png");
		Textures.load("wall", "textures/wall/diffuse.png");
		
		Textures.load("player_red", "p1.png");
		Textures.load("player_blue", "p2.png");
		Textures.load("player", "player.png");
		
		Textures.load("turret_base", "turret_base.png");
		
		Textures.load("turret_mv42_head", "turret_mv42_head.png");
		Textures.load("turret_mv42_arm", "turret_mv42_arm.png");
		
		Fonts.load("default", "WarfaceRegularRussian.ttf", 32);
		Fonts.load("default_small", "WarfaceRegularRussian.ttf", 16);
		Fonts.load("debug", "consola.ttf", 8);
		
		manager = GameManager.newInstance();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Renderer.update();
		
		manager.update();
		
		manager.render();
		
		Renderer.getSRUI().begin(ShapeRenderer.ShapeType.Filled);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Renderer.getSRUI().setColor(0, 0, 0, 0.25f);
		Renderer.getSRUI().rect(0, 256 - 80, 128, 80 + 64);
		Renderer.getSRUI().setColor(Color.WHITE);
		Renderer.getSRUI().end();
		
		Renderer.getSBUI().begin();
		BitmapFont font = Fonts.get("debug");
		font.draw(Renderer.getSBUI(), "Screen density: " + Gdx.graphics.getDensity() + "\n\nPointers:", 0, 256 + 64);
		for (int i = 0; i < 10; i ++) {
			Vector2 p = InputUtils.getInputPos(i);
			font.draw(Renderer.getSBUI(), "#" + i + ": " + p.toString(), 0, 256 - 8 * i);
		}
		Renderer.getSBUI().end();
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
}
