package com.frez.turretwars.entities;

import com.frez.turretwars.*;
import com.frez.turretwars.resources.*;
import com.badlogic.gdx.math.*;
import com.frez.turretwars.animation.*;

public class TestEntity extends Entity {

	//private SpriteModel mdl;
	
	public TestEntity() {
		super("test", null);
		
		SpriteModel mdl = new SpriteModel();
		SpriteModel.Part base = new SpriteModel.Part("base", Textures.get("turret_base"), new Vector2(), new Vector2(10, 10), new Vector2(0, 0), new Vector2(1, 1));
		SpriteModel.Part head = new SpriteModel.Part("head", Textures.get("turret_mv42_head"), new Vector2(), new Vector2(30, 30), new Vector2(0, 0), new Vector2(1, 1));
		SpriteModel.Part arm = new SpriteModel.Part("arm", Textures.get("turret_mv42_arm"), new Vector2(), new Vector2(30, 30), new Vector2(0, 0), new Vector2(1, 1));
		
		Animation a = new Animation(new Animation.Keyframe[] {
			new Animation.Keyframe(new Vector2(0, 0), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 5f }, 100),
			new Animation.Keyframe(new Vector2(0, -5), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 2f }, 400),
			new Animation.Keyframe(new Vector2(0, 0), Animation.Keyframe.InterpolationMode.NONE, null, 500),
			new Animation.Keyframe(new Vector2(0, 0), Animation.Keyframe.InterpolationMode.NONE, null, 0),
		}, true);
		arm.animPos = new Animator(new Vector2(0, 0), true);
		arm.animPos.addAnimation("shoot", a);
		arm.animPos.play("shoot");
		
		mdl.addPart(base);
		mdl.addPart(head);
		head.addSubPart(arm);
		head.drawSubPartsUnder = true;
		
		setModel(mdl);
	}
	
	@Override
	public void start() {
		
	}
	
	@Override
	public void update() {
		getModel().getPart("head").getSubPart("arm").animPos.update();
		
		getModel().pos.set(pos);
		getModel().getPart("head").angle += 0.1f;
	}
	
	@Override
	public void draw() {
		Renderer.draw(getModel());
	}
	
	@Override
	public void destroy() {
		
	}
	
}
