package com.frez.turretwars;

import com.frez.turretwars.entities.*;
import java.util.*;

public class EntityManager {
	
	private static ArrayList<Entity> entities;
	
	static {
		entities = new ArrayList<Entity>();
	}
	
	public static Entity createEntity(Class entity) {
		try {
			Entity e = (Entity) entity.newInstance();
			entities.add(e);
			return e;
		} catch (Exception e) {
			System.err.println("Tried to create invalid entity!");
			return null;
		}
	}
	
	public static Entity createEntity(String entity) {
		try {
			Entity e = (Entity) Class.forName(entity).newInstance();
			entities.add(e);
			return e;
		} catch (Exception e) {
			System.err.println("Tried to create invalid entity!");
			return null;
		}
	}
	
	public static void updateEntities() {
		for (Entity e : entities) {
			e.update();
		}
	}
	
	public static void drawEntities() {
		for (Entity e : entities) {
			e.draw();
		}
	}
	
}
