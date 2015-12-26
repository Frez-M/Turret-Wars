package com.frez.turretwars.shaders;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.glutils.*;

public class WorldAOShader {
	
	private ShaderProgram program;

	public WorldAOShader() {
		program = new ShaderProgram(Gdx.files.internal("shaders/world_ao_vertex.glsl"), Gdx.files.internal("shaders/world_ao_frag.glsl"));
		Gdx.app.log("WorldAOShader", program.getLog());
		
	}
	
	public ShaderProgram getProgram() {
		return program;
	}
	
	public void setStage(int stage) {
		program.setUniformf(program.getUniformLocation("stage"), stage);
	}
}
