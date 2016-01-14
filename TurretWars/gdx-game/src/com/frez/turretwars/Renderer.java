package com.frez.turretwars;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.frez.turretwars.shaders.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import java.util.*;

public class Renderer {
	
	private static float uiWidth, uiHeight;
	
	private static OrthographicCamera camWorld, camUI;
	
	private static SpriteBatch sbWorld, sbUI;
	private static ShapeRenderer srWorld, srUI;
	
	private static LightingShader shaderWorld;
	private static WorldAOShader worldAOShader;
	
	private static FrameBuffer worldAOfbo1, worldAOfbo2, worldAOfboFinal;
	
	private static boolean inited = false;
	public static void init() {
		if (inited) return;
		inited = true;
		
		float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
		Gdx.graphics.getDensity();
		camWorld = new OrthographicCamera(100f, 100f * aspectRatio);
		camWorld.zoom = 1.25f;
		uiWidth = Gdx.graphics.getWidth() / Gdx.graphics.getDensity();
		uiHeight = Gdx.graphics.getHeight() / Gdx.graphics.getDensity();
		camUI = new OrthographicCamera(uiWidth, uiHeight);
		
		camUI.position.set(uiWidth/2, uiHeight/2, 0);
		camUI.update();
		
		shaderWorld = new LightingShader();
		
		worldAOShader = new WorldAOShader();
		int div = 8;
		worldAOfbo1 = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth() / div, Gdx.graphics.getHeight() / div, false);
		worldAOfbo2 = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth() / div, Gdx.graphics.getHeight() / div, false);
		worldAOfboFinal = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth() / (div/2), Gdx.graphics.getHeight() / (div/2), false);
		
		sbWorld = new SpriteBatch(/*1000, shaderWorld.getProgram()*/);
		sbUI = new SpriteBatch();
		
		srWorld = new ShapeRenderer();
		srUI = new ShapeRenderer();
		
	}
	
	public static float getUIWidth() {
		return uiWidth;
	}
	
	public static float getUIHeight() {
		return uiHeight;
	}
	
	public static void setCamPos(Vector2 pos) {
		camWorld.position.set(pos, 0);
	}
	
	public static void setZoom(float zoom) {
		camWorld.zoom = zoom;
	}
	
	public static void setAngle(float zAngle) {
		camWorld.view.idt();
		camWorld.view.rotate(new Vector3(0, 0, 1), zAngle);
	}
	
	public static void update() {
		camWorld.update();
		camUI.update();
		sbWorld.setProjectionMatrix(camWorld.combined);
		sbUI.setProjectionMatrix(camUI.combined);
		srWorld.setProjectionMatrix(camWorld.combined);
		srUI.setProjectionMatrix(camUI.combined);
	}
	
	public static LightingShader getLightingShader() {
		return shaderWorld;
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
	
	public static void renderWorldAO(ArrayList<GameWorld.Wall> walls) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sbWorld.setShader(worldAOShader.getProgram());
		
		worldAOfbo1.begin();
		sbWorld.begin();
		worldAOShader.setStage(1);
		for (GameWorld.Wall wall : walls) {
			wall.draw();
		}
		sbWorld.setShader(null);
		sbWorld.end();
		worldAOfbo1.end();
		
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sbWorld.setShader(worldAOShader.getProgram());
		
		sbWorld.setProjectionMatrix(new Matrix4().idt());
		worldAOfbo2.begin();
		sbWorld.begin();
		worldAOShader.setStage(2);
		worldAOShader.setImageSize(worldAOfboFinal.getWidth(), worldAOfboFinal.getHeight());
		
		sbWorld.draw(worldAOfbo1.getColorBufferTexture(), -1, -1, 2, 2);
		sbWorld.end();
		worldAOfbo2.end();
		
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sbWorld.setShader(worldAOShader.getProgram());
		
		worldAOfboFinal.begin();
		sbWorld.begin();
		worldAOShader.setStage(3);
		worldAOShader.setImageSize(worldAOfboFinal.getWidth(), worldAOfboFinal.getHeight());
		
		sbWorld.draw(worldAOfbo2.getColorBufferTexture(), -1, -1, 2, 2);
		sbWorld.end();
		worldAOfboFinal.end();
		
		sbWorld.setShader(null);
		
		sbWorld.setProjectionMatrix(camWorld.combined);
		
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public static void drawWorldAO() {
		sbWorld.setProjectionMatrix(new Matrix4().idt());
		sbWorld.begin();
		//sbWorld.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		sbWorld.setColor(1, 1, 1, 0.75f);
		sbWorld.draw(worldAOfboFinal.getColorBufferTexture(), -1, -1, 2, 2);
		sbWorld.setColor(Color.WHITE);
		sbWorld.end();
		//sbWorld.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sbWorld.setProjectionMatrix(camWorld.combined);
	}
	
	public static void draw(SpriteModel model, int level) {
		if (model == null) return;
		
		sbWorld.begin();
		for (SpriteModel.Part p : model.getParts()) {
			drawPart(p, model.pos, model.angle, model.scl, level);
		}
		sbWorld.end();
	}
	
	private static void drawPart(SpriteModel.Part part, Vector2 originPos, float originAngle, Vector2 originScl, int level) {
		if (part == null) return;
		
		float angle = originAngle + part.angle;
		if (part.animAng != null) angle += part.animAng.get().x;
		Vector2 size = new Vector2(part.size).scl(part.scl).scl(originScl);
		if (part.animScl != null) size.scl(part.animScl.get());
		Vector2 pos = new Vector2(part.pos);
		if (part.animPos != null) pos.add(part.animPos.get());
		pos.rotate(originAngle);
		pos.add(originPos);
		Vector2 lPos = new Vector2(pos).sub(new Vector2(size).scl(0.5f));
		
		if (part.animAng != null) angle += part.animAng.get().x;
		int uv1x = (int) (part.texture.getWidth() * part.uv1.x);
		int uv1y = (int) (part.texture.getHeight() * part.uv1.y);
		int uv2x = (int) (part.texture.getWidth() * part.uv2.x);
		int uv2y = (int) (part.texture.getHeight() * part.uv2.y);
		
		if (part.drawSubPartsUnder) {
			for (int i = 0; i < part.subParts.size(); i ++) {
				drawPart(part.subParts.get(i), pos, angle, part.scl, level);
			}
		}
		
		if (part.texture != null && checkSprMdlLevel(level, part.drawLevel))
			sbWorld.draw(part.texture, lPos.x, lPos.y, size.x/2f, size.y/2f, size.x, size.y, 1f, 1f, angle, uv1x, uv1y, uv2x, uv2y, false, false);
		
		if (!part.drawSubPartsUnder) {
			for (int i = 0; i < part.subParts.size(); i ++) {
				drawPart(part.subParts.get(i), pos, angle, size, level);
			}
		}
	}
	
	private static boolean checkSprMdlLevel(int levelAt, int levelCurrent) {
		if (levelCurrent == SpriteModel.Part.DRAWLEVEL_UNDEFINED) return true;
		if (levelAt == levelCurrent) return true;
		if (levelAt != levelCurrent) return false;
		return true;
	}
	
	public static void enableBlending() {
		Gdx.gl.glEnable(GL20.GL_BLEND);
	}
	
	public static void disableBlending() {
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	public static void setMixingRendering() {
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void setAdditiveRendering() {
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
	}
	
	public static void setMixingRenderingUI() {
		sbUI.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void setAdditiveRenderingUI() {
		sbUI.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
	}
	
	public static void setMixingRenderingWorld() {
		sbWorld.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public static void setAdditiveRenderingWorld() {
		sbWorld.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
	}
	
	public static void dispose() {
		inited = false;
	}
	
}
