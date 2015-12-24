package com.frez.turretwars;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.frez.turretwars.shaders.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;

public class Renderer {
	
	private static OrthographicCamera camWorld, camUI;
	
	private static SpriteBatch sbWorld, sbUI;
	private static ShapeRenderer srWorld, srUI;
	
	private static LightingShader shaderWorld;
	
	static {
		
		float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
		Gdx.graphics.getDensity();
		camWorld = new OrthographicCamera(100f, 100f * aspectRatio);
		float UIx = 100f / Gdx.graphics.getDensity();
		float UIy = 100f * aspectRatio / Gdx.graphics.getDensity();
		camUI = new OrthographicCamera(UIx, UIy);
		
		camUI.position.set(UIx/2, UIy/2, 0);
		camUI.update();
		
		shaderWorld = new LightingShader();
		
		sbWorld = new SpriteBatch();
		sbWorld.setShader(shaderWorld.getProgram());
		sbUI = new SpriteBatch();
		
		srWorld = new ShapeRenderer();
		srUI = new ShapeRenderer();
		
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
	
	public static void draw(SpriteModel model) {
		sbWorld.begin();
		for (SpriteModel.Part p : model.getParts()) {
			
			Vector2 pSize = new Vector2(p.size.scl(p.scl));
			if (p.animScl != null) pSize.scl(p.animScl.get());
			Vector2 pos = new Vector2(model.pos).add(new Vector2(p.pos).rotate(model.angle).scl(model.scl));
			if (p.animPos != null) pos.add(p.animPos.get());
			pos.sub(pSize);
			Vector2 size = new Vector2(pSize.scl(model.scl));
			float angle = model.angle + p.angle;
			if (p.animAng != null) angle += p.animAng.get().x;
			
			drawPart(p, pos, angle, size);
			/*
			int uv1x = (int) (p.texture.getWidth() * p.uv1.x);
			int uv1y = (int) (p.texture.getWidth() * p.uv1.x);
			int uv2x = (int) (p.texture.getWidth() * p.uv1.x);
			int uv2y = (int) (p.texture.getWidth() * p.uv1.x);
			
			
			
			sbWorld.draw(p.texture, pos.x, pos.y, 0f, 0f, size.x, size.y, 1f, 1f, angle, uv1x, uv1y, uv2x, uv2y, false, false);
			*/
			
		}
		sbWorld.end();
	}
	
	private static void drawPart(SpriteModel.Part part, Vector2 originPos, float originAngle, Vector2 originScl) {
		Vector2 pSize = new Vector2(part.size.scl(part.scl));
		if (part.animScl != null) pSize.scl(part.animScl.get());
		Vector2 pos = new Vector2(originPos).add(new Vector2(part.pos).rotate(originAngle).scl(originScl));
		if (part.animPos != null) pos.add(part.animPos.get());
		pos.sub(pSize);
		Vector2 size = new Vector2(pSize.scl(originScl));
		float angle = originAngle + part.angle;
		if (part.animAng != null) angle += part.animAng.get().x;
		int uv1x = (int) (part.texture.getWidth() * part.uv1.x);
		int uv1y = (int) (part.texture.getHeight() * part.uv1.y);
		int uv2x = (int) (part.texture.getWidth() * part.uv2.x);
		int uv2y = (int) (part.texture.getHeight() * part.uv2.y);
		
		if (part.drawSubPartsUnder) {
			for (int i = 0; i < part.subParts.size(); i ++) {
				drawPart(part.subParts.get(i), pos, angle, size);
			}
		}
		
		sbWorld.draw(part.texture, pos.x, pos.y, 0f, 0f, size.x, size.y, 1f, 1f, angle, uv1x, uv1y, uv2x, uv2y, false, false);
		
		if (!part.drawSubPartsUnder) {
			for (int i = 0; i < part.subParts.size(); i ++) {
				drawPart(part.subParts.get(i), pos, angle, size);
			}
		}
	}
	
}
