package com.frez.turretwars.entities;
import com.frez.turretwars.*;
import com.frez.turretwars.resources.*;
import com.badlogic.gdx.math.*;
import com.frez.turretwars.box2dutils.*;
import com.badlogic.gdx.physics.box2d.*;
import com.frez.turretwars.animation.*;
import com.badlogic.gdx.*;

public class SlidingDoors extends Entity {
	
	private Body bodyLeft, bodyRight;
	private SpriteModel.Part partLeft, partRight;
	
	public SlidingDoors() {
		super("sliding_doors", null);
		
		SpriteModel mdl = new SpriteModel();
		
		partLeft = new SpriteModel.Part("left", SpriteModel.Part.DRAWLEVEL_UPPER, Textures.get("sliding_door_left"), new Vector2(0, 0), new Vector2(50, 12.5f), new Vector2(0, 0), new Vector2(1, 1));
		partRight = new SpriteModel.Part("right", SpriteModel.Part.DRAWLEVEL_UPPER, Textures.get("sliding_door_right"), new Vector2(0, 0), new Vector2(50, 12.5f), new Vector2(0, 0), new Vector2(1, 1));
		
		partLeft.animPos = new Animator(new Vector2(-25, 0), 1, false);
		partLeft.animPos.addAnimation("open", Animations.get("sliding_door_open_left"));
		partLeft.animPos.addAnimation("close", Animations.get("sliding_door_close_left"));
		partRight.animPos = new Animator(new Vector2(25, 0), 1, false);
		partRight.animPos.addAnimation("open", Animations.get("sliding_door_open_right"));
		partRight.animPos.addAnimation("close", Animations.get("sliding_door_close_right"));
		
		mdl.addPart(partLeft);
		mdl.addPart(partRight);
		
		setModel(mdl);
		
		bodyLeft = BodyCreator.rect(GameWorld.getPhysicsWorld(), new Vector2(-25, 0), 0, new Vector2(25, 6.25f), 0.4f, 0.1f, false);
		bodyRight = BodyCreator.rect(GameWorld.getPhysicsWorld(), new Vector2(25, 0), 0, new Vector2(25, 6.25f), 0.4f, 0.1f, false);
		bodyLeft.setType(BodyDef.BodyType.KinematicBody);
		bodyRight.setType(BodyDef.BodyType.KinematicBody);
		bodyLeft.setUserData(this);
		bodyRight.setUserData(this);
		
	}
	
	public void open() {
		getModel().stopAnimation("close");
		getModel().playAnimation("open", true);
		
	}
	
	public void close() {
		getModel().stopAnimation("open");
		getModel().playAnimation("close", true);
		
	}
	
	@Override
	public void drawUpper() {
		Renderer.draw(getModel(), SpriteModel.Part.DRAWLEVEL_UPPER);
	}
	
	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		getModel().updateAnimations();
		
		Vector2 left = partLeft.animPos.get();
		bodyLeft.setLinearVelocity(left.cpy().sub(bodyLeft.getPosition()).scl(1f/Gdx.graphics.getDeltaTime()));
		
		Vector2 right = partRight.animPos.get();
		bodyRight.setLinearVelocity(right.cpy().sub(bodyRight.getPosition()).scl(1f/Gdx.graphics.getDeltaTime()));
	}

	@Override
	public void destroy() {
		
	}
	
}
