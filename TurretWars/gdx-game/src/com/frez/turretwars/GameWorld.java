package com.frez.turretwars;

import java.util.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.physics.box2d.*;
import com.frez.turretwars.box2dutils.*;

public class GameWorld {
	
	private static ArrayList<Floor> floors;
	private static ArrayList<Wall> walls;
	
	private static World w;
	
	static {
		floors = new ArrayList<Floor>();
		walls = new ArrayList<Wall>();
		w = new World(new Vector2(), false);
	}
	
	private GameWorld() {}
	
	public static void update() {
		
	}
	
	public static void renderAO() {
		Renderer.renderWorldAO(walls);
	}
	
	public static void drawAO() {
		Renderer.drawWorldAO();
	}
	
	public static void drawFloors() {
		Renderer.getSBWorld().begin();
		for (Floor f : floors) {
			f.draw();
		}
		Renderer.getSBWorld().end();
	}
	
	public static void drawWalls() {
		Renderer.getSBWorld().begin();
		for (Wall w : walls) {
			w.draw();
		}
		Renderer.getSBWorld().end();
	}
	
	public static void addFloor(Floor f) {
		floors.add(f);
	}
	
	public static void addWall(Wall w) {
		w.init();
		walls.add(w);
	}
	
	public static void removeFloor(Floor f) {
		floors.remove(f);
	}
	
	public static void removeWall(Wall w) {
		w.destroy();
		walls.remove(w);
	}
	
	public static class Floor {
		
		public Vector2 pos, size;
		public float angle;
		public Texture texture;
		
		public Floor(Texture texture, Vector2 pos, Vector2 size, float angle) {
			this.texture = texture;
			this.pos = pos;
			this.size = size;
			this.angle = angle;
		}
		
		public void draw() {
			if (texture != null) Renderer.getSBWorld().draw(texture, pos.x - size.x / 2, pos.y - size.y / 2, size.x/2f, size.y/2f, size.x, size.y, 1f, 1f, angle, 0, 0, (int) (texture.getWidth() * size.x / 50f), (int) (texture.getHeight() * size.y / 50f), false, false);
		}
		
	}
	
	public static class Wall {
		
		public Vector2 pos, size;
		public float angle;
		public Texture texture;
		
		private Body b;

		public Wall(Texture texture, Vector2 pos, Vector2 size, float angle) {
			this.texture = texture;
			this.pos = pos;
			this.size = size;
			this.angle = angle;
		}

		public void draw() {
			if (texture != null) Renderer.getSBWorld().draw(texture, pos.x - size.x / 2, pos.y - size.y / 2, size.x/2f, size.y/2f, size.x, size.y, 1f, 1f, angle, 0, 0, (int) (texture.getWidth() * size.x / 50f), (int) (texture.getHeight() * size.y / 50f), false, false);
		}
		
		public void init() {
			
			b = BodyCreator.rect(GameWorld.w, pos, angle, new Vector2(size).scl(0.5f), 0.5f, 0.2f);
		}
		
		public void destroy() {
			GameWorld.w.destroyBody(b);
			b = null;
		}
		
	}
	
}
