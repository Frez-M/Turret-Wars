package com.frez.turretwars.shaders;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.glutils.*;

public class WorldAOShader {
	
	private ShaderProgram program;
	
	private int u_stage;
	private int u_imgSize;
	
	public WorldAOShader() {
		program = new ShaderProgram(Gdx.files.internal("shaders/world_ao_vertex.glsl"), Gdx.files.internal("shaders/world_ao_frag.glsl"));
		Gdx.app.log("WorldAOShader", program.getLog());
		
		u_stage = program.getUniformLocation("stage");
		u_imgSize = program.getUniformLocation("imgSize");
	}
	
	public ShaderProgram getProgram() {
		return program;
	}
	
	public void setStage(int stage) {
		program.setUniformi(u_stage, stage);
	}
	
	
	public void setImageSize(int width, int height) {
		program.setUniformf(u_imgSize, width, height);
	}
	
}
