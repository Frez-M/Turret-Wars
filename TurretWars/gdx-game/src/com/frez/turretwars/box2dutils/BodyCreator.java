package com.frez.turretwars.box2dutils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.math.*;

public class BodyCreator {
	
	public static Body rect(World world, Vector2 pos, float angle, Vector2 size, float friction, float restitution) {
		BodyDef def = new BodyDef();
		FixtureDef f = new FixtureDef();
		f.friction = friction;
		f.restitution = restitution;
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(size.x, size.y);
		f.shape = ps;
		Body b = world.createBody(def);
		b.createFixture(f);
		b.setTransform(pos, angle);
		return b;
	}
	
	public static Body poly(World world, float poly[], Vector2 pos, float angle, float friction, float restitution) {
		BodyDef def = new BodyDef();
		FixtureDef f = new FixtureDef();
		f.friction = friction;
		f.restitution = restitution;
		PolygonShape ps = new PolygonShape();
		ps.set(poly);
		f.shape = ps;
		Body b = world.createBody(def);
		b.createFixture(f);
		b.setTransform(pos, angle);
		return b;
	}
	
}
