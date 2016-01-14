package com.frez.turretwars;

import com.frez.turretwars.resources.*;
import com.frez.turretwars.animation.*;
import com.badlogic.gdx.math.*;

public class Loader {
	
	private static boolean loaded = false;
	
	public static void load() {
		if (loaded) return;
		loaded = true;
		
		textures();
		fonts();
		animations();
		
	}
	
	private static void textures() {
		Textures.load("ground", "textures/ground/diffuse.png");
		Textures.load("wall", "textures/wall/diffuse.png");
		
		Textures.load("sliding_door_left", "sliding_door_left.png");
		Textures.load("sliding_door_right", "sliding_door_right.png");
		
		Textures.load("player_red", "p1.png");
		Textures.load("player_blue", "p2.png");
		Textures.load("player_head_red", "player_red.png");
		Textures.load("player_torso_red", "player_torso_red.png");
		Textures.load("player_head_blue", "player_blue.png");
		Textures.load("player_torso_blue", "player_torso_blue.png");
		
		Textures.load("turret_base", "turret_base.png");
		
		Textures.load("turret_mv42_head", "turret_mv42_head.png");
		Textures.load("turret_mv42_arm", "turret_mv42_arm.png");
		
	}
	
	private static void fonts() {
		Fonts.load("default", "WarfaceRegularRussian.ttf", 32);
		Fonts.load("default_small", "WarfaceRegularRussian.ttf", 16);
		Fonts.load("debug", "consola.ttf", 8);
		
		Fonts.load("ui", "WarfaceRegularRussian.ttf", 8);
		//Fonts.load("ui_small", "WarfaceRegularRussian.ttf", 4);
		
	}
	
	private static void animations() {
		Animations.load("player_torso_walk", new Animation(new Animation.Keyframe[] {
															   new Animation.Keyframe(new Vector2(0, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 1.5f }, 150),
															   new Animation.Keyframe(new Vector2(5, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN_OUT, new float[] { 1.5f, 1.5f }, 300),
															   new Animation.Keyframe(new Vector2(-5, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 1.5f }, 150),
															   new Animation.Keyframe(new Vector2(0, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.NONE, null, 0),
														   }, true));
		Animations.load("player_torso_walk_holding_box", new Animation(new Animation.Keyframe[] {
															   new Animation.Keyframe(new Vector2(2, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 1.5f }, 150),
															   new Animation.Keyframe(new Vector2(7, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN_OUT, new float[] { 1.5f, 1.5f }, 300),
															   new Animation.Keyframe(new Vector2(-3, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 1.5f }, 150),
															   new Animation.Keyframe(new Vector2(2, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.NONE, null, 0),
														   }, true));
		Animations.load("player_torso_holding_box", new Animation(new Animation.Keyframe[] {
																	  new Animation.Keyframe(new Vector2(2, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.NONE, null, 0),
																	  }, true));
		Animations.load("player_head_walk", new Animation(new Animation.Keyframe[] {
															  new Animation.Keyframe(new Vector2(0, 0.5f), new Vector2(0.25f, 0), new Vector2(0, -0.5f), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 1.5f }, 150),
															  new Animation.Keyframe(new Vector2(0.25f, 1f), new Vector2(0, -0.5f), new Vector2(0.25f, 0), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 1.5f }, 150),
															  new Animation.Keyframe(new Vector2(0, 0.5f), new Vector2(-0.25f, 0), new Vector2(0, -0.5f), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 1.5f }, 150),
															  new Animation.Keyframe(new Vector2(-0.25f, 1f), new Vector2(0, 0.5f), new Vector2(-0.25f, 0), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 1.5f }, 150),
															  new Animation.Keyframe(new Vector2(0, 0.5f), new Vector2(0, 0), new Vector2(0, 0),Animation.Keyframe.InterpolationMode.NONE, null, 0),
														  }, true));
		Animations.load("player_head_idle", new Animation(new Animation.Keyframe[] {
															  new Animation.Keyframe(new Vector2(0, -0.25f), new Vector2(), new Vector2(),  Animation.Keyframe.InterpolationMode.EASE_IN_OUT, new float[] { 1.9f, 1.9f }, 1300),
															  new Animation.Keyframe(new Vector2(0, 0.25f), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN_OUT, new float[] { 1.9f, 1.9f }, 1300),
															  new Animation.Keyframe(new Vector2(0, -0.25f), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.NONE, null, 0),
														  }, true));
		Animations.load("player_head_walk_holding_box", new Animation(new Animation.Keyframe[] {
																		  new Animation.Keyframe(new Vector2(-0.1f, 0.4f), new Vector2(0.25f, 0), new Vector2(0, -0.5f), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 1.5f }, 150),
																		  new Animation.Keyframe(new Vector2(0.15f, 0.9f), new Vector2(0, -0.5f), new Vector2(0.25f, 0), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 1.5f }, 150),
																		  new Animation.Keyframe(new Vector2(-0.1f, 0.4f), new Vector2(-0.25f, 0), new Vector2(0, -0.5f), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 1.5f }, 150),
																		  new Animation.Keyframe(new Vector2(-0.35f, 0.9f), new Vector2(0, 0.5f), new Vector2(-0.25f, 0), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 1.5f }, 150),
																		  new Animation.Keyframe(new Vector2(-0.1f, 0.4f), new Vector2(0, 0), new Vector2(0, 0),Animation.Keyframe.InterpolationMode.NONE, null, 0),
																	  }, true));
		Animations.load("player_head_idle_holding_box", new Animation(new Animation.Keyframe[] {
															  new Animation.Keyframe(new Vector2(-0.1f, -0.3f), new Vector2(), new Vector2(),  Animation.Keyframe.InterpolationMode.EASE_IN_OUT, new float[] { 1.9f, 1.9f }, 1300),
															  new Animation.Keyframe(new Vector2(0.0f, 0.1f), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN_OUT, new float[] { 1.9f, 1.9f }, 1300),
															  new Animation.Keyframe(new Vector2(-0.1f, -0.3f), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.NONE, null, 0),
														  }, true));
		
		Animations.load("turret_base_shoot", new Animation(new Animation.Keyframe[] {
															   new Animation.Keyframe(new Vector2(0, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 5 }, 100),
															   new Animation.Keyframe(new Vector2(0, -2), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 4 }, 600),
															   new Animation.Keyframe(new Vector2(0, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.NONE, null, 0),
														   }, false));
		
		
		Animations.load("sliding_door_open_left", new Animation(new Animation.Keyframe[] {
																	 new Animation.Keyframe(new Vector2(-25, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.LINEAR, null, 100),
																	 new Animation.Keyframe(new Vector2(-26, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 2f }, 4900),
																	 new Animation.Keyframe(new Vector2(-69, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 1.5f }, 400),
																	 new Animation.Keyframe(new Vector2(-71, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 1.5f }, 400),
																	 new Animation.Keyframe(new Vector2(-71.5f, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.NONE, null, 0)
																 }, false));
		Animations.load("sliding_door_open_right", new Animation(new Animation.Keyframe[] {
																	 new Animation.Keyframe(new Vector2(25, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.LINEAR, null, 100),
																	 new Animation.Keyframe(new Vector2(26, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 2f }, 4900),
																	 new Animation.Keyframe(new Vector2(69, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 1.5f }, 400),
																	 new Animation.Keyframe(new Vector2(71, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 1.5f }, 400),
																	 new Animation.Keyframe(new Vector2(71.5f, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.NONE, null, 0)
																 }, false));
		Animations.load("sliding_door_close_left", new Animation(new Animation.Keyframe[] {
																	 new Animation.Keyframe(new Vector2(-71.5f, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.LINEAR, null, 100),
																	 new Animation.Keyframe(new Vector2(-70.5f, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 2f }, 4900),
																	 new Animation.Keyframe(new Vector2(-26.5f, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 1.5f }, 400),
																	 new Animation.Keyframe(new Vector2(-25.5f, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 1.5f }, 400),
																	 new Animation.Keyframe(new Vector2(-25, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.NONE, null, 0)
																  }, false));
		Animations.load("sliding_door_close_right", new Animation(new Animation.Keyframe[] {
																	  new Animation.Keyframe(new Vector2(71.5f, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.LINEAR, null, 100),
																	  new Animation.Keyframe(new Vector2(70.5f, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 2f }, 4900),
																	  new Animation.Keyframe(new Vector2(26.5f, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_OUT, new float[] { 1.5f }, 400),
																	  new Animation.Keyframe(new Vector2(25.5f, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.EASE_IN, new float[] { 1.5f }, 400),
																	  new Animation.Keyframe(new Vector2(25, 0), new Vector2(), new Vector2(), Animation.Keyframe.InterpolationMode.NONE, null, 0)
																  }, false));
		
	}
	
	public static void invalidate() {
		loaded = false;
	}
	
}
