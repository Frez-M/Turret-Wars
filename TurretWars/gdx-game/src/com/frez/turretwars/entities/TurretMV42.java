package com.frez.turretwars.entities;

import com.badlogic.gdx.math.*;
import com.frez.turretwars.*;
import com.frez.turretwars.animation.*;
import com.frez.turretwars.resources.*;

public class TurretMV42 extends Turret {
	
	public TurretMV42() {
		super(1, 45, Turret.DAMAGE_SIMPLE, 500, 1500, 8, 12, 200, 50, 20, BulletNormal.class);
		
		SpriteModel mdl = new SpriteModel();
		SpriteModel.Part base = new SpriteModel.Part("base", SpriteModel.Part.DRAWLEVEL_LOWER, Textures.get("turret_base"), new Vector2(), new Vector2(10, 10), new Vector2(0, 0), new Vector2(1, 1));
		SpriteModel.Part head = new SpriteModel.Part("head", SpriteModel.Part.DRAWLEVEL_MIDDLE, Textures.get("turret_mv42_head"), new Vector2(), new Vector2(30, 30), new Vector2(0, 0), new Vector2(1, 1));
		SpriteModel.Part arm = new SpriteModel.Part("arm", SpriteModel.Part.DRAWLEVEL_MIDDLE, Textures.get("turret_mv42_arm"), new Vector2(), new Vector2(30, 30), new Vector2(0, 0), new Vector2(1, 1));
		
		arm.animPos = new Animator(new Vector2(0, 0), true);
		arm.animPos.addAnimation("shoot", Animations.get("turret_base_shoot"));
		
		mdl.addPart(base);
		mdl.addPart(head);
		head.addSubPart(arm);
		head.drawSubPartsUnder = true;
		
		setModel(mdl);
	}
	
}
