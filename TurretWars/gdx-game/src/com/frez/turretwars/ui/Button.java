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
	
	private OnClickListener listener;
	private boolean wasPressed;
	
	public Button() {
		
	}
	
	@Override
	public void update(boolean hovered, boolean pressed) {
		
		if (hovered && pressed && !wasPressed) {
			wasPressed = true;
		} else if (!pressed && wasPressed) {
			wasPressed = false;
			if (listener != null) listener.onClick();
		}
		
	}
	
	@Override
	public void draw() {
		
		ShapeRenderer sr = Renderer.getSRUI();
		SpriteBatch sb = Renderer.getSBUI();
		BitmapFont font = Fonts.get("default_small");
		
		sr.begin(ShapeRenderer.ShapeType.Filled);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		sr.setColor(0, 0, 0, 0.25f);
		sr.rect(pos.x, pos.y, size.x, size.y);
		sr.setColor(color.r, color.g, color.b, 1);
		sr.rect(pos.x, pos.y, size.x, 4f);
		sr.end();
		
		sb.begin();
		font.setColor(Color.WHITE);
		font.draw(sb, text, pos.x, pos.y + size.y / 2);
		font.setColor(color.r * 0.5f, color.g * 0.5f, color.b * 0.5f, 1);
		font.draw(sb, text, pos.x, pos.y + size.y / 2);
		sb.end();
	}
	
	@Override
	public boolean testPoint(Vector2 point) {
		return Geometry2DUtils.rect(pos, new Vector2(pos).add(size), point);
	}
	
	public void setOnClick(OnClickListener listener) {
		this.listener = listener;
	}
	
	public static interface OnClickListener {
		public void onClick();
	}
	
}
