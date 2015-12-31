package com.frez.turretwars.utils;

import com.badlogic.gdx.math.*;

public class Geometry2DUtils {
	
	public static final boolean rect(Vector2 min, Vector2 max, Vector2 point) {
		if (min.x <= point.x && point.x <= max.x) {
			if (min.y <= point.y && point.y <= max.y) {
				return true;
			}
		}
		return false;
	}
	
	public static final float sign(Vector2 p, Vector2 a, Vector2 b) {
		return (p.x - b.x) * (a.y - b.y) - (a.x - b.x) * (p.y - b.y);
	}
	
	public static final boolean triangle(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 point) {
		boolean b1 = sign(point, p1, p2) < 0.0f;
		boolean b2 = sign(point, p2, p3) < 0.0f;
		boolean b3 = sign(point, p3, p1) < 0.0f;
		
		return ((b1 == b2) && (b2 == b3));
	}
	
	public static final boolean circle(Vector2 pos, float radius, Vector2 point) {
		return pos.dst(point) <= radius;
	}
	
}
