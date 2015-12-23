package com.frez.turretwars.shaders;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.*;

public class LightingShader {
	
	private ShaderProgram program;
	
	public LightingShader() {
		program = new ShaderProgram(Gdx.files.internal("shaders/lighting_vertex.glsl"), Gdx.files.internal("shaders/lighting_frag.glsl"));
		
		
	}
	
}
