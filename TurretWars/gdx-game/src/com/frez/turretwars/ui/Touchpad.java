package com.frez.turretwars.ui;

import com.badlogic.gdx.math.*;
import com.frez.turretwars.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.frez.turretwars.utils.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;

public class Touchpad extends UIObject {
	
	private Vector2 posCenter, btnPos, out;
	private boolean catched;
	
	public Touchpad() {
		posCenter = new Vector2();
		btnPos = new Vector2();
		out = new Vector2();
		catched = false;
	}
	
	@Override
	public void update() {
		posCenter = new Vector2(getPos().x + getSize().x / 2, getPos().y + getSize().y / 2);
		if (pressed && enabled) {
			
			btnPos.set(InputUtils.getInputPos(pointer));
			float s = Math.min(size.x, size.y);
			if (btnPos.dst(posCenter) > s / 2) {
				float a = MathUtils.atan2(btnPos.x - posCenter.x, btnPos.y - posCenter.y);
				btnPos.x = posCenter.x + MathUtils.sin(a) * (s / 2);
				btnPos.y = posCenter.y + MathUtils.cos(a) * (s / 2);
			}
			
			out.set(btnPos.x - posCenter.x, btnPos.y - posCenter.y);
			out.scl(1/s);
			
		} else {
			btnPos.set(posCenter);
			if (!out.isZero()) out.setZero();
		}
	}
	
	@Override
	public void onPress() {
		
	}
	
	@Override
	public void onClick() {
		
	}
	
	
	@Override
	public void draw() {
		ShapeRenderer sr = Renderer.getSRUI();
		sr.begin(ShapeRenderer.ShapeType.Filled);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		sr.setColor(0, 0, 0, 0.5f);
		sr.circle(posCenter.x, posCenter.y, Math.min(getSize().x, getSize().y) / 2, 32);
		sr.setColor(Color.BLACK);
		sr.circle(posCenter.x, posCenter.y, 12, 32);
		sr.rectLine(posCenter, btnPos, 16);
		sr.setColor(color.r, color.g, color.b, 1f);
		sr.circle(btnPos.x, btnPos.y, Math.max(getSize().x, getSize().y) / 4f, 32);
		sr.end();
	}

	@Override
	public boolean testPoint(Vector2 point) {
		float s = Math.min(size.x, size.y);
		return Geometry2DUtils.circle(new Vector2(pos).add(s / 2, s / 2), s / 2, point);
	}
	
	public Vector2 getControl() {
		return new Vector2(out);
	}
	
}
