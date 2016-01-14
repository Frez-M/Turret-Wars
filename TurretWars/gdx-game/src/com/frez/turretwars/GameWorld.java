package com.frez.turretwars;

import java.util.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.physics.box2d.*;
import com.frez.turretwars.box2dutils.*;
import com.badlogic.gdx.*;

public class GameWorld {
	
	private static ArrayList<Floor> floors;
	private static ArrayList<Wall> walls;
	
	private static World w;
	
	private static Box2DDebugRenderer dr;
	
	private static boolean refreshAO = false;
	
	private static boolean inited = false;
	public static void init() {
		if (inited) return;
		inited = true;
		floors = new ArrayList<Floor>();
		walls = new ArrayList<Wall>();
		w = new World(new Vector2(), false);
		w.setVelocityThreshold(10000);
		dr = new Box2DDebugRenderer();
		dr.setDrawVelocities(true);
	}
	
	private GameWorld() {}
	
	public static void update() {
		if (refreshAO) {
			refreshAO = false;
			for (int i = 0; i < 10; i ++)
				renderAO();
		}
		
		w.step(Gdx.graphics.getDeltaTime(), 10, 10);
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
	
	public static World getPhysicsWorld() {
		return w;
	}
	
	public static void drawPhysicsDebug() {
		dr.render(w, Renderer.getSRWorld().getProjectionMatrix());
	}
	
	public static void addFloor(Floor f) {
		floors.add(f);
	}
	
	public static void addWall(Wall w) {
		w.init();
		walls.add(w);
		refreshAO = true;
	}
	
	public static void removeFloor(Floor f) {
		floors.remove(f);
	}
	
	public static void removeWall(Wall w) {
		w.destroy();
		walls.remove(w);
		refreshAO = true;
	}
	
	public static void dispose() {
		inited = false;
		w.dispose();
		w = null;
		dr.dispose();
		dr = null;
		floors.clear();
		walls.clear();
		floors = null;
		walls = null;
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
			
			b = BodyCreator.rect(GameWorld.w, pos, angle, new Vector2(size).scl(0.5f), 0.5f, 0.2f, false);
		}
		
		public void destroy() {
			GameWorld.w.destroyBody(b);
			b = null;
		}
		
	}
	
}
