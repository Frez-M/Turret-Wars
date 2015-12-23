package com.frez.turretwars;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.frez.turretwars.shaders.*;

public class Renderer {
	
	private static SpriteBatch sbWorld, sbUI;
	private static ShapeRenderer srWorld, srUI;
	
	private static LightingShader shaderWorld;
	
	static {
		
		
		
		sbWorld = new SpriteBatch();
		sbUI = new SpriteBatch();
		
		srWorld = new ShapeRenderer();
		srUI = new ShapeRenderer();
		
	}
	
	public static SpriteBatch getSBWorld() {
		return sbWorld;
	}
	
	public static SpriteBatch getSBUI() {
		return sbUI;
	}
	
	public static ShapeRenderer getSRWorld() {
		return srWorld;
	}
	
	public static ShapeRenderer getSRUI() {
		return srUI;
	}
	
}
