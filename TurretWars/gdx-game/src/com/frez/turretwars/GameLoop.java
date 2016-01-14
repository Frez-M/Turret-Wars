package com.frez.turretwars;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.frez.turretwars.resources.*;
import com.frez.turretwars.utils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.glutils.*;
import java.util.*;
import com.badlogic.gdx.utils.*;

public class GameLoop implements ApplicationListener {
	
	private GameManager manager;
	
	private static ArrayList<Debug> debugs;
	private static ArrayList<Log> logs;
	
	private static String errorOnCreate;
	private static boolean error;
	
	private long timeStart;
	private float fps;
	
	@Override
	public void create() {
		timeStart = System.currentTimeMillis();
		
		Gdx.input.setCatchBackKey(true);
		
		debugs = new ArrayList<Debug>();
		logs = new ArrayList<Log>();
		
		errorOnCreate = "";
		
		fps = 0;
		
		error = false;
		Renderer.init();
		
		try {
			EntityManager.init();
			InputUtils.init();
			
			Loader.load();
			
			manager = GameManager.newInstance();
		} catch (Exception e) {
			errorOnCreate = e.getMessage();
			error = true;
		}
	}

	@Override
	public void render() {
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		fps += (1f/Gdx.graphics.getDeltaTime() - fps) / 10f;
		
		debug("main_time", "Time: " + TimeUtils.getTimeText(System.currentTimeMillis() - timeStart, true), Color.WHITE);
		debug("main_delta", "Delta: " + Gdx.graphics.getDeltaTime(), Color.WHITE);
		debug("main_fps", "FPS: " + Gdx.graphics.getFramesPerSecond(), Color.WHITE);
		debug("main_fps_fast", "Fast FPS: " + Math.round(fps*10)/10f, Color.WHITE);
		
		Renderer.update();
		
		if (!error)
			try {
				
				manager.update();
				
				manager.render();
			} catch (Exception e) {
				debug("main_render_error", "error: " + e, Color.RED);
			}
		
		// Debug displaying
		
		BitmapFont font = Fonts.get("debug");
		
		debug("main_screen_density", "Screen density: " + Gdx.graphics.getDensity(), Color.WHITE);
		if (error) {
			debug("main_on_create_error_text", "Error on create:", Color.RED);
			debug("main_on_create_error", errorOnCreate, Color.ORANGE);
		}
		String finalDebug = "";
		if (!debugs.isEmpty())
			finalDebug = debugs.get(0).text;
		for (Debug d : debugs)
			finalDebug = finalDebug + "\n" + d.text;
		GlyphLayout glDebug = new GlyphLayout(font, finalDebug, Color.WHITE, Renderer.getUIWidth() - 16, Align.left, true);
		
		Renderer.getSRUI().begin(ShapeRenderer.ShapeType.Filled);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Renderer.getSRUI().setColor(0, 0, 0, 0.5f);
		Renderer.getSRUI().rect(8, Renderer.getUIHeight() - glDebug.height - 8, glDebug.width, glDebug.height);
		Renderer.getSRUI().setColor(0.25f, 0.25f, 0.25f, 0.75f);
		Renderer.getSRUI().rect(8, Renderer.getUIHeight() - glDebug.height - 8 - 4, glDebug.width, 4);
		Renderer.getSRUI().setColor(Color.WHITE);
		Renderer.getSRUI().end();
		
		for (int i = 0; i < debugs.size(); i ++) {
			Debug d = debugs.get(i);
			int t = (int) (System.currentTimeMillis() - d.time);
			if (t >= 2000) {
				debugs.remove(d);
				i --;
			}
		}
		
		float lHeight = 0;
		for (int i = 0; i < logs.size(); i ++) {
			Log l = logs.get(i);
			int d = (int) (System.currentTimeMillis() - l.time);
			GlyphLayout temp = new GlyphLayout(font, l.text, Color.WHITE, Renderer.getUIWidth() - 16, Align.left, true);
			l.targetPos = lHeight += temp.height * 2;
			//lHeight -= temp.height;
			l.pos += (l.targetPos - l.pos) / 10f;
			if (d >= l.lasts) {
				logs.remove(l);
				i --;
			}
		}
		
		Renderer.getSBUI().begin();
		float dl = 0;
		Renderer.getSBUI().enableBlending();
		for (Debug d : debugs) {
			int t = (int) (System.currentTimeMillis() - d.time);
			if (t >= 2000) continue;
			
			Color c = d.color.cpy();
			c.a = (t / 2000f);
			c.a = (float) (1-Math.pow(c.a, 2));
			
			font.setColor(c);
			dl += font.draw(Renderer.getSBUI(), d.text, 8, Renderer.getUIHeight() - 8 - dl * 2, Renderer.getUIWidth() - 16, Align.left, true).height;
		}
		Renderer.getSBUI().end();
		Renderer.getSBUI().begin();
		int p = 0;
		for (Log l : logs) {
			int t = (int) (System.currentTimeMillis() - l.time);
			if (t >= l.lasts) continue;
			
			Color c = new Color(l.color);
			float a = ((float) t / (float) l.lasts);
			c.a = (float) (1-Math.pow(a, 8));
			font.setColor(c);
			p += font.draw(Renderer.getSBUI(), l.text, 8, Renderer.getUIHeight() - 8 - 4 - glDebug.height - l.pos, Renderer.getUIWidth() - 16, Align.left, true).height;
		}
		Renderer.getSBUI().end();
		
		
		if (Gdx.input.isKeyPressed(Input.Keys.BACK)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		manager.dispose();
		manager = null;
		InputUtils.dispose();
		EntityManager.dispose();
		Renderer.dispose();
		Fonts.unloadAll();
		Textures.unloadAll();
		Loader.invalidate();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}
	
	public static void debug(String id, String text, Color color) {
		for (Debug d : debugs) {
			if (d.id.equals(id)) {
				d.time = System.currentTimeMillis();
				d.text = text;
				d.color = color;
				return;
			}
		}
		debugs.add(new Debug(id, text, color));
	}
	
	public static void log(String text, Color color, long time) {
		logs.add(new Log(text, color, time));
	}
	
	private static class Debug {
		public String id, text;
		public long time;
		public Color color;
		public Debug(String id, String text, Color color) {
			this.id = id;
			this.text = text;
			this.color = color;
			time = System.currentTimeMillis();
		}
	}
	
	private static class Log {
		public String text;
		public long time, lasts;
		public Color color;
		public float pos, targetPos;
		public Log(String text, Color color, long lasts) {
			this.text = text;
			this.color = color;
			this.lasts = lasts;
			time = System.currentTimeMillis();
			pos = Gdx.graphics.getHeight();
		}
	}
	
}
