package com.frez.turretwars;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.frez.turretwars.resources.*;

public class GameLoop implements ApplicationListener {
	
	private GameManager manager;
	
	@Override
	public void create() {
		Textures.load("ground", "textures/ground/diffuse.png");
		
		Textures.load("player_red", "p1.png");
		Textures.load("player_blue", "p2.png");
		
		Textures.load("turret_base", "turret_base.png");
		
		Textures.load("turret_mv42_head", "turret_mv42_head.png");
		Textures.load("turret_mv42_arm", "turret_mv42_arm.png");
		
		
		manager = GameManager.newInstance();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		manager.update();
		
		Renderer.update();
		manager.render();
	}

	@Override
	public void dispose() {
		Textures.unloadAll();
		Fonts.unloadAll();
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
