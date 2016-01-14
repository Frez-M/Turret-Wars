package com.frez.turretwars.entities;

import com.badlogic.gdx.math.*;
import com.frez.turretwars.*;
import com.badlogic.gdx.graphics.*;

public abstract class Entity {
	
	public Vector2 pos, vel;
	public float angle, angleVel;
	public float health, maxHealth, armor, maxArmor;
	public String name;
	public Color color;
	private Identity id;
	private Entity owner;
	
	private SpriteModel mdl;
	
	public Entity(String id, Entity owner) {
		pos = new Vector2();
		vel = new Vector2();
		angle = 0;
		angleVel = 0;
		health = 0;
		maxHealth = 0;
		armor = 0;
		maxArmor = 0;
		color = new Color(1, 1, 1, 1);
		
		this.id = new Identity(id);
		this.owner = owner;
		
		start();
	}
	
	public abstract void start();
	public void restart() {
		start();
	}
	public abstract void update();
	public abstract void destroy();
	
	public void drawLower() {}
	public void draw() {}
	public void drawUpper() {}
	public void drawOver() {}
	
	public void drawModel(int level) {
		if (mdl != null)
			Renderer.draw(mdl, level);
	}
	
	public Identity getID() {
		return id;
	}

	public void setOwner(Entity e) {
		if (owner == null) owner = e; 
	}

	public Entity getOwner() {
		return owner;
	}

	public boolean isAlive() {
		return health > 0;
	}
	
	public SpriteModel getModel() {
		return mdl;
	}
	
	public void setModel(SpriteModel model) {
		mdl = model;
	}
	
	public static class Identity {
		
		public final String id;
		
		public Identity(String id) {
			this.id = id;
		}
		
	}
	
}
