package com.frez.turretwars;

import com.frez.turretwars.entities.*;
import com.frez.turretwars.resources.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;

public class GameManager {
	
	public static final long TIME_BUILD = TimeUtils.getSeconds(20, false);
	public static final long TIME_PREPARE = TimeUtils.getSeconds(7, false);
	public static final long TIME_FIGHT = TimeUtils.getSeconds(90, false);
	public static final long TIME_COOLDOWN = TimeUtils.getSeconds(5, false);
	
	private static TimeState currentState;
	private static long lastStateTime, nextStateTime;
	
	private static GameManager instance;
	
	private static Player p1, p2;
	
	private GameManager() {
		instance = this;
		
		initWorld();
		
		currentState = TimeState.BUILD;
		lastStateTime = System.currentTimeMillis();
		nextStateTime = TIME_BUILD;
		
		p1 = (Player) EntityManager.createEntity(Player.class);
		p1.restart(Player.TEAM_RED);
		p2 = (Player) EntityManager.createEntity(Player.class);
		p2.restart(Player.TEAM_BLUE);
		
		//EntityManager.createEntity(TestEntity.class);
		
	}
	
	private void initWorld() {
		GameWorld.addFloor(new GameWorld.Floor(Textures.get("ground"), new Vector2(0, 0), new Vector2(100, 150), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(75, 0), new Vector2(50, 250), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(-75, 0), new Vector2(50, 250), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(0, 100), new Vector2(100, 50), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(0,-100), new Vector2(100, 50), 0));
	}
	
	public void update() {
		if (lastStateTime + nextStateTime >= System.currentTimeMillis()) {
			changeState();
		}
		
		GameWorld.update();
		EntityManager.updateEntities();
		
		p1.updateUI();
		p2.updateUI();
	}
	
	public void render() {
		
		Renderer.getSRWorld().begin(ShapeRenderer.ShapeType.Filled);
		Renderer.getSRWorld().setColor(Color.WHITE);
		Renderer.getSRWorld().rect(-100, -100, 200, 200);
		Renderer.getSRWorld().end();
		
		GameWorld.drawFloors();
		EntityManager.drawEntities();
		GameWorld.drawAO();
		GameWorld.drawWalls();
		
		Renderer.getSRWorld().begin(ShapeRenderer.ShapeType.Line);
		Renderer.getSRWorld().setColor(1, 0, 0, 1);
		Renderer.getSRWorld().line(new Vector2(0, 0), new Vector2(50, 0));
		Renderer.getSRWorld().setColor(0, 1, 0, 1);
		Renderer.getSRWorld().line(new Vector2(0, 0), new Vector2(0, 50));
		Renderer.getSRWorld().end();
		
		//GameWorld.drawPhysicsDebug();
		
		p1.drawUI();
		p2.drawUI();
		
	}
	
	private void changeState() {
		lastStateTime = System.currentTimeMillis();
		switch (currentState) {
			case BUILD:
				currentState = TimeState.PREPARE;
				nextStateTime = TIME_PREPARE;
				break;
			case PREPARE:
				currentState = TimeState.FIGHT;
				nextStateTime = TIME_FIGHT;
				break;
			case FIGHT:
				currentState = TimeState.COOLDOWN;
				nextStateTime = TIME_COOLDOWN;
				break;
			case COOLDOWN:
				currentState = TimeState.BUILD;
				nextStateTime = TIME_BUILD;
				break;
		}
	}
	
	public static GameManager newInstance() {
		if (instance != null) {
			throw new RuntimeException("Only one instance of GameManager is allowed!");
		}
		return new GameManager();
	}
	
	public static TimeState getCurrentTimeState() {
		return currentState;
	}
	
	public static enum TimeState {
		BUILD, PREPARE, FIGHT, COOLDOWN
	}
	
}
