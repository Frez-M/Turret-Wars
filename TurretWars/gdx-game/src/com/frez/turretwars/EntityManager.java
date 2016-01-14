package com.frez.turretwars;

import com.frez.turretwars.entities.*;
import java.util.*;
import com.badlogic.gdx.graphics.*;

public class EntityManager {
	
	private static ArrayList<Entity> entities;
	
	private static boolean inited = false;
	public static void init() {
		if (inited) return;
		inited = true;
		entities = new ArrayList<Entity>();
	}
	
	public static Entity createEntity(Class entity) {
		try {
			Entity e = (Entity) entity.newInstance();
			entities.add(e);
			return e;
		} catch (Exception e) {
			String t = "Tried to create an invalid entity! (" + entity.getName() + ")";
			System.err.println(t);
			e.printStackTrace();
			GameLoop.log(t, Color.ORANGE, TimeUtils.getRawSeconds(5));
			GameLoop.log(e.getMessage(), Color.CYAN, TimeUtils.getRawSeconds(5));
			return null;
		}
	}
	
	public static Entity createEntity(String entity) {
		try {
			Entity e = (Entity) Class.forName(entity).newInstance();
			entities.add(e);
			return e;
		} catch (Exception e) {
			String t = "Tried to create an invalid entity! (" + entity + ")";
			System.err.println(t);
			e.printStackTrace();
			GameLoop.log(t, Color.ORANGE, TimeUtils.getRawSeconds(5));
			return null;
		}
	}
	
	public static void updateEntities() {
		for (Entity e : entities) {
			e.update();
		}
	}
	
	public static void drawEntitiesLower() {
		for (Entity e : entities) {
			e.drawLower();
		}
	}
	
	public static void drawEntitiesMiddle() {
		for (Entity e : entities) {
			e.draw();
		}
	}
	
	public static void drawEntitiesUpper() {
		for (Entity e : entities) {
			e.drawUpper();
		}
	}
	
	public static void drawEntitiesOver() {
		for (Entity e : entities) {
			e.drawOver();
		}
	}
	
	public static void dispose() {
		inited = false;
		entities.clear();
		
	}
	
}
