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
	
	public void addFloor(Floor f) {
		floors.add(f);
	}
	
	public void addWall(Wall w) {
		w.init();
		walls.add(w);
	}
	
	public void removeFloor(Floor f) {
		floors.remove(f);
	}
	
	public void removeWall(Wall w) {
		w.destroy();
		walls.remove(w);
	}
	
	public static class Floor {
		
		public float[] pos;
		public Texture texture;
		
		public Floor(Texture texture, float[] pos) {
			this.texture = texture;
			this.pos = pos;
		}
		
		public void draw() {
			Renderer.getSBWorld().draw(texture, pos, 0, pos.length / 2);
		}
		
	}
	
	public static class Wall {
		
		public float[] pos;
		public Texture texture;
		
		private Body b;

		public Wall(Texture texture, float[] pos) {
			this.texture = texture;
			this.pos = pos;
		}

		public void draw() {
			Renderer.getSBWorld().draw(texture, pos, 0, pos.length);
		}
		
		public void init() {
			float[] v = new float[pos.length / 2];
			for (int i = 0; i < pos.length; i += 2) {
				v[i] = pos[i * 4];
				v[i+1] = pos[i*4+1];
			}
			b = BodyCreator.poly(GameWorld.w, v, new Vector2(), 0, 0.5f, 0.2f);
		}
		
		public void destroy() {
			GameWorld.w.destroyBody(b);
			b = null;
		}
		
	}
	
}
