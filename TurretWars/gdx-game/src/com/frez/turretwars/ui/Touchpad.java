package com.frez.turretwars.ui;

import com.badlogic.gdx.math.*;
import com.frez.turretwars.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.frez.turretwars.utils.*;

public class Touchpad extends UIObject {
	
	private Vector2 posCenter, btnPos, lastPoint, out;
	private boolean catched, wasPressed;
	private int pointerid;
	
	public Touchpad() {
		posCenter = new Vector2();
		btnPos = new Vector2();
		out = new Vector2();
		catched = false;
		wasPressed = false;
		pointerid = -1;
	}
	
	@Override
	public void update(boolean hovered, boolean pressed) {
		if (!catched) {
			if (hovered && pressed && !wasPressed) {
				wasPressed = true;
				if (Geometry2DUtils.circle(posCenter, Math.min(size.x, size.y), null))
					catched = true;
			} else if (!pressed) {
				wasPressed = false;
			}
		} else {
			if (!pressed) {
				catched = false;
			}
		}
		
		if (catched) {
			
			btnPos.set(InputUtils.getInputPos(pointerid));
			
		} else {
			btnPos.set(pos.x + size.x / 2, pos.y + size.y / 2);
			if (!out.isZero()) out.setZero();
		}
	}

	@Override
	public void draw() {
		ShapeRenderer sr = Renderer.getSRUI();
		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.ellipse(pos.x, pos.y, size.x, size.y, 16);
		sr.circle(lastPoint.x, lastPoint.y, Math.max(size.x, size.y) / 4f, 16);
		sr.end();
	}

	@Override
	public boolean testPoint(Vector2 point) {
		
		return false;
	}

}
