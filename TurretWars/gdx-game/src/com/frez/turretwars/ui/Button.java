package com.frez.turretwars.ui;

import com.badlogic.gdx.math.*;
import com.frez.turretwars.utils.*;
import com.frez.turretwars.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.frez.turretwars.resources.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

public class Button extends UIObject {
	
	private OnPressListener pressListener;
	private OnClickListener clickListener;
	
	private float fPressed, fEnabled;
	
	public Button() {
		
	}
	
	@Override
	public void update() {
		
		fPressed = pressed ? 1 : 0;
		fEnabled = enabled ? 1 : 0;
	}
	
	@Override
	public void draw() {
		
		ShapeRenderer sr = Renderer.getSRUI();
		SpriteBatch sb = Renderer.getSBUI();
		BitmapFont font = Fonts.get("ui");
		
		sr.begin(ShapeRenderer.ShapeType.Filled);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		sr.setColor(0, 0, 0, 0.5f + 0.25f * (fPressed * fEnabled) - 0.25f * (1 - fEnabled));
		sr.rect(pos.x + 1, pos.y + 1, size.x - 2, size.y - 2);
		float temp1 = (1 + fPressed);
		if (enabled)
			sr.setColor(color.r / temp1, color.g / temp1, color.b / temp1, 1);
		else
			sr.setColor(0.5f, 0.5f / temp1, 0.5f / temp1, 1);
		sr.rect(pos.x + 1, upsideDown ? pos.y + size.y - 5 : pos.y + 1, size.x - 2, 4f);
		sr.end();
		
		sb.begin();
		if (enabled)
			font.setColor(Color.WHITE);
		else
			font.setColor(Color.GRAY);
		
		Matrix4 original = null;
		if (upsideDown) {
			original = new Matrix4(sb.getProjectionMatrix());
			sb.setProjectionMatrix(new Matrix4(original).translate(pos.x + size.x, pos.y + 4, 0).rotate(0, 0, 1, 180));
		}
		
		font.draw(sb, text, upsideDown ? 0 : pos.x, upsideDown ? 0 : pos.y + size.y - 4, size.x, 1, false);
		font.setColor(color.r * 0.5f, color.g * 0.5f, color.b * 0.5f, 1);
		if (upsideDown)
			sb.setProjectionMatrix(new Matrix4(original).translate(pos.x + size.x, pos.y + size.y - 4 - font.getLineHeight(), 0).rotate(0, 0, 1, 180));
		font.draw(sb, subText, upsideDown ? 0 : pos.x, upsideDown ? 0 : pos.y + 4 + font.getLineHeight(), size.x, 1, false); // pos.y + 4 + font.getLineHeight()
		
		if (upsideDown)
			sb.setProjectionMatrix(original);
		
		sb.end();
	}
	
	@Override
	public void onPress() {
		if (enabled && pressListener != null) pressListener.onPress();
	}
	
	@Override
	public void onClick() {
		if (enabled && clickListener != null) clickListener.onClick();
	}
	
	@Override
	public boolean testPoint(Vector2 point) {
		return Geometry2DUtils.rect(pos, new Vector2(pos).add(size), point);
	}
	
	public void setOnPressListener(OnPressListener listener) {
		this.pressListener = listener;
	}
	
	public void setOnClickListener(OnClickListener listener) {
		this.clickListener = listener;
	}
	
	public static interface OnPressListener {
		public void onPress();
	}
	
	public static interface OnClickListener {
		public void onClick();
	}
	
}
