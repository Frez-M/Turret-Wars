package com.frez.turretwars;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import java.util.*;
import com.frez.turretwars.animation.*;

public class SpriteModel {
	
	public Vector2 pos, scl;
	public float angle;
	
	private ArrayList<Part> parts;
	
	public SpriteModel() {
		pos = new Vector2();
		scl = new Vector2(1, 1);
		
		parts = new ArrayList<Part>();
		
		
	}
	
	public void addPart(Part part) {
		parts.add(part);
	}
	
	public ArrayList<Part> getParts() {
		return parts;
	}
	
	public Part getPart(String name) {
		for (Part p : parts) {
			if (p.name.equals(name)) return p;
		}
		return null;
	}
	
	public void removePart(Part part) {
		parts.remove(part);
	}
	
	public static class Part {
		
		public final String name;
		
		public Vector2 pos, size, scl;
		public float angle;
		
		public Texture texture;
		public final Vector2 uv1, uv2;
		
		public Animator animPos, animAng, animScl;
		
		public ArrayList<Part> subParts;
		public boolean drawSubPartsUnder = false;
		
		public Part(String name, Texture texture, Vector2 pos, Vector2 size, Vector2 uv1, Vector2 uv2) {
			this.pos = pos;
			this.size = size;
			this.scl = new Vector2(1, 1);
			this.name = name;
			this.texture = texture;
			this.uv1 = uv1;
			this.uv2 = uv2;
			subParts = new ArrayList<Part>();
		}
		
		public void addSubPart(Part part) {
			subParts.add(part);
		}
		
		public void removeSubPart(Part part) {
			subParts.remove(part);
		}
	}
	
}
