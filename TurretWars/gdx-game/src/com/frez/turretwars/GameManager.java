package com.frez.turretwars;
import com.frez.turretwars.entities.*;
import com.frez.turretwars.resources.*;

public class GameManager {
	
	public static final long TIME_BUILD = TimeUtils.getSeconds(20, false);
	public static final long TIME_PREPARE = TimeUtils.getSeconds(5, false);
	public static final long TIME_FIGHT = TimeUtils.getSeconds(90, false);
	
	private static TimeState currentState;
	private static long lastStateTime, nextStateTime;
	
	private static GameManager instance;
	
	private GameManager() {
		instance = this;
		
		lastStateTime = System.currentTimeMillis();
		nextStateTime = TIME_BUILD;
		
		TestEntity te = (TestEntity) EntityManager.createEntity(TestEntity.class);
		//te.pos.set(50, 50);
	}
	
	public void update() {
		
		EntityManager.updateEntities();
		
	}
	
	public void render() {
		Renderer.getSBWorld().begin();
		Renderer.getSBWorld().draw(Textures.get("ground"), 0, 0, 10, 10);
		Renderer.getSBWorld().end();
		
		EntityManager.drawEntities();
		
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
