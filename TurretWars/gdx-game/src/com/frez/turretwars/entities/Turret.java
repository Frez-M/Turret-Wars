package com.frez.turretwars.entities;
import com.frez.turretwars.*;
import com.badlogic.gdx.physics.box2d.*;
import com.frez.turretwars.box2dutils.*;
import com.badlogic.gdx.math.*;

public class Turret extends Entity {
	
	public static final int DAMAGE_SIMPLE = 0;
	public static final int DAMAGE_RELATIVE_ON_DISTANCE = 1;
	public static final int DAMAGE_RANDOM = 2;
	
	protected float shootRate, reloadTime, damageMin, damageMax, damageMaxDistance;
	protected float currentShootTime, currentReloadTime;
	protected float detectingRadius, shootAngleLimit, rotateSpeed;
	protected float targetAngle;
	protected int bullets, bulletsPerShot, bulletsInOneMagazine, damageType;
	
	protected Class bullet;
	protected Body body;
	
	public Turret(int bulletsPerShot, int bulletsInOneMagazine, int damageType, float shootRate, float reloadTime, float damageMin, float damageMax, float damageMaxDistance, float detectingRadius, float shootAngleLimit, Class bulletType) {
		super("turret", null);
		this.shootRate = shootRate;
		this.reloadTime = reloadTime;
		this.damageMin = damageMin;
		this.damageMax = damageMax;
		this.damageMaxDistance = damageMaxDistance;
		this.detectingRadius = detectingRadius;
		this.shootAngleLimit = shootAngleLimit;
		this.bulletsPerShot = bulletsPerShot;
		this.bulletsInOneMagazine = bulletsInOneMagazine;
		this.damageType = damageType;
		this.bullet = bulletType;
		
		this.body = BodyCreator.rect(GameWorld.getPhysicsWorld(), new Vector2(), 0, new Vector2(4, 4), 0.4f, 0.1f, false);
	}

	@Override
	public void start() {
		currentShootTime = 0;
		currentReloadTime = 0;
		
	}

	@Override
	public void update() {
		getModel().pos.set(pos);
		getModel().angle = angle;
		if (!body.getPosition().epsilonEquals(pos, 0)) {
			body.setTransform(pos, angle);
		}
	}

	@Override
	public void drawLower() {
		Renderer.draw(getModel(), SpriteModel.Part.DRAWLEVEL_LOWER);
	}

	@Override
	public void draw() {
		Renderer.draw(getModel(), SpriteModel.Part.DRAWLEVEL_MIDDLE);
	}
	
	@Override
	public void drawUpper() {
		Renderer.draw(getModel(), SpriteModel.Part.DRAWLEVEL_UPPER);
	}
	
	@Override
	public void drawOver() {
		Renderer.draw(getModel(), SpriteModel.Part.DRAWLEVEL_OVER);
	}
	
	@Override
	public void destroy() {
		
	}
	
}
