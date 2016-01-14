package com.frez.turretwars;

import com.frez.turretwars.entities.*;
import com.frez.turretwars.resources.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;

public class GameManager {
	
	public static final long TIME_BUILD = TimeUtils.getRawSeconds(20);
	public static final long TIME_PREPARE = TimeUtils.getRawSeconds(7);
	public static final long TIME_FIGHT = TimeUtils.getRawSeconds(90);
	public static final long TIME_COOLDOWN = TimeUtils.getRawSeconds(5);
	
	private static TimeState currentState;
	private static long lastStateTime, nextStateTime;
	
	private static GameManager instance;
	
	private static Player p1, p2;
	private static SlidingDoors doors;
	
	private GameManager() {
		instance = this;
		
		GameWorld.init();
		
		initWorld();
		
		currentState = TimeState.BUILD;
		lastStateTime = System.currentTimeMillis();
		nextStateTime = TIME_BUILD;
		
		doors = (SlidingDoors) EntityManager.createEntity(SlidingDoors.class);
		
		p1 = (Player) EntityManager.createEntity(Player.class);
		p1.restart(Player.TEAM_RED);
		p2 = (Player) EntityManager.createEntity(Player.class);
		p2.restart(Player.TEAM_BLUE);
		
	}
	
	private void initWorld() {
		GameWorld.addFloor(new GameWorld.Floor(Textures.get("ground"), new Vector2(0, 0), new Vector2(100, 150), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(75, 0), new Vector2(50, 250), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(-75, 0), new Vector2(50, 250), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(0, 100), new Vector2(100, 50), 0));
		GameWorld.addWall(new GameWorld.Wall(Textures.get("wall"), new Vector2(0,-100), new Vector2(100, 50), 0));
	}
	
	public void update() {
		if (lastStateTime + nextStateTime <= System.currentTimeMillis()) {
			changeState();
		}
		
		GameLoop.debug("manager_time_state", "Time state: " + currentState, Color.CYAN);
		GameLoop.debug("manager_time_state_time_left", "Time left: " + TimeUtils.getTimeText((lastStateTime + nextStateTime) - System.currentTimeMillis(), true), Color.CYAN);
		
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
		EntityManager.drawEntitiesLower();
		GameWorld.drawAO();
		EntityManager.drawEntitiesMiddle();
		EntityManager.drawEntitiesUpper();
		GameWorld.drawWalls();
		EntityManager.drawEntitiesOver();
		
		/*
		Renderer.getSRWorld().begin(ShapeRenderer.ShapeType.Line);
		Renderer.getSRWorld().setColor(1, 0, 0, 1);
		Renderer.getSRWorld().line(new Vector2(0, 0), new Vector2(50, 0));
		Renderer.getSRWorld().setColor(0, 1, 0, 1);
		Renderer.getSRWorld().line(new Vector2(0, 0), new Vector2(0, 50));
		Renderer.getSRWorld().end();
		*/
		
		GameWorld.drawPhysicsDebug();
		
		p1.drawUI();
		p2.drawUI();
		
		
	}
	
	private void changeState() {
		lastStateTime = System.currentTimeMillis();
		switch (currentState) {
			case BUILD:
				currentState = TimeState.PREPARE;
				nextStateTime = TIME_PREPARE;
				
				doors.open();
				
				p1.start();
				p2.start();
				
				break;
			case PREPARE:
				currentState = TimeState.FIGHT;
				nextStateTime = TIME_FIGHT;
				
				break;
			case FIGHT:
				currentState = TimeState.COOLDOWN;
				nextStateTime = TIME_COOLDOWN;
				
				doors.close();
				
				p1.start();
				p2.start();
				
				break;
			case COOLDOWN:
				currentState = TimeState.BUILD;
				nextStateTime = TIME_BUILD;
				
				break;
		}
		GameLoop.log("Changed time state to: " + currentState, Color.CYAN, 5000);
	}
	
	public void dispose() {
		instance = null;
		EntityManager.dispose();
		GameWorld.dispose();
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
	
	public static boolean isBuildState() {
		return currentState == TimeState.BUILD;
	}
	
	public static boolean isPrepareState() {
		return currentState == TimeState.PREPARE;
	}
	
	public static boolean isFightState() {
		return currentState == TimeState.FIGHT;
	}
	
	public static boolean isCooldownState() {
		return currentState == TimeState.COOLDOWN;
	}
	
	public static enum TimeState {
		BUILD, PREPARE, FIGHT, COOLDOWN
	}
	
}
