package com.frez.turretwars;
import com.frez.turretwars.entities.*;
import com.frez.turretwars.resources.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;

public class GameManager {
	
	public static final long TIME_BUILD = TimeUtils.getSeconds(20, false);
	public static final long TIME_PREPARE = TimeUtils.getSeconds(5, false);
	public static final long TIME_FIGHT = TimeUtils.getSeconds(90, false);
	
	private static TimeState currentState;
	private static long lastStateTime, nextStateTime;
	
	private static GameManager instance;
	
	private GameManager() {
		instance = this;
		
		initWorld();
		
		lastStateTime = System.currentTimeMillis();
		nextStateTime = TIME_BUILD;
		
		EntityManager.createEntity(TestEntity.class);
		//te.pos.set(50, 50);
	}
	
	private void initWorld() {
		GameWorld.addFloor(new GameWorld.Floor(Textures.get("ground"), new Vector2(0, 0), new Vector2(100, 150), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(75, 0), new Vector2(50, 250), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(-75, 0), new Vector2(50, 250), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(0, 100), new Vector2(100, 50), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(0,-100), new Vector2(100, 50), 0));
	}
	
	public void update() {
		GameWorld.update();
		EntityManager.updateEntities();
		
	}
	
	public void render() {
		
		//GameWorld.renderAO();
		
		Renderer.getSRWorld().begin(ShapeRenderer.ShapeType.Filled);
		Renderer.getSRWorld().setColor(Color.WHITE);
		Renderer.getSRWorld().rect(-100, -100, 200, 200);
		Renderer.getSRWorld().end();
		
		GameWorld.drawFloors();
		GameWorld.drawAO();
		EntityManager.drawEntities();
		GameWorld.drawWalls();
		
		Renderer.getSRWorld().begin(ShapeRenderer.ShapeType.Line);
		Renderer.getSRWorld().setColor(1, 0, 0, 1);
		Renderer.getSRWorld().line(new Vector2(0, 0), new Vector2(50, 0));
		Renderer.getSRWorld().setColor(0, 1, 0, 1);
		Renderer.getSRWorld().line(new Vector2(0, 0), new Vector2(0, 50));
		Renderer.getSRWorld().end();
		
	}
	
	private void changeState() {
		switch (currentState) {
			case BUILD:
				
				break;
			case PREPARE:

				break;
			case FIGHT:

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
