package com.frez.turretwars.entities;

public class Bullet extends Entity {
	
	protected float damageMin, damageMax, damageMaxDistance;
	protected float speed;
	protected boolean isRay;
	
	public Bullet(String type) {
		super("bullet_" + type, null);
		
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void destroy() {
		
	}
	
}
