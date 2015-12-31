package com.frez.turretwars;

import com.frez.turretwars.entities.*;
import com.badlogic.gdx.graphics.*;
import com.frez.turretwars.resources.*;
import com.badlogic.gdx.math.*;
import com.frez.turretwars.ui.*;

public class Player extends Entity {
	
	public static final Team TEAM_RED = new Team(0, new Color(1, 0, 0, 1));
	public static final Team TEAM_BLUE = new Team(1, new Color(0, 0, 1, 1));
	
	private Team team;
	
	private UIContainer ui;
	private Button btnAction, btnNext, btnPrev;
	private Touchpad touchpad;
	
	public Player() {
		super("player", null);
		
		maxHealth = 100;
		
		SpriteModel.Part partHead = new SpriteModel.Part("head", Textures.get("player"), new Vector2(), new Vector2(20, 20), new Vector2(0, 0), new Vector2(1, 1));
		
		SpriteModel mdl = new SpriteModel();
		
		mdl.addPart(partHead);
		
		setModel(mdl);
		
		ui = new UIContainer();
		
		
		touchpad = new Touchpad();
		touchpad.setSize(new Vector2(128, 128));
		
		btnAction = new Button();
		btnAction.setText("BTN_ACTION");
		btnAction.setSize(new Vector2(128, 64));
		
		
		ui.addUIObject(touchpad);
		ui.addUIObject(btnAction);
	}
	
	public void restart(Team team) {
		this.team = team;
		restart();
		if (team == TEAM_RED) {
			touchpad.setPos(new Vector2(Renderer.getUIWidth() - 128 - 32, 32));
			btnAction.setPos(new Vector2(32, 32));
		} else if (team == TEAM_BLUE) {
			touchpad.setPos(new Vector2(32, Renderer.getUIHeight() - 128 - 32));
			btnAction.setPos(new Vector2(Renderer.getUIWidth() - 128 - 32, Renderer.getUIHeight() - 64 - 32));
		}
	}
	
	@Override
	public void start() {
		health = maxHealth;
		if (team == TEAM_RED) {
			pos.set(0, -150);
		} else if (team == TEAM_BLUE) {
			pos.set(0, 150);
		} else {
			pos.set(0, 0);
		}
	}
	
	@Override
	public void update() {
		
		
		getModel().updateAnimations();
	}
	
	@Override
	public void draw() {
		Renderer.draw(getModel());
	}
	
	public void updateUI() {
		ui.update();
	}
	
	public void drawUI() {
		ui.draw();
	}
	
	@Override
	public void destroy() {
		
	}
	
	public Team getTeam() {
		return team;
	}
	
	public static class Team {
		public final int id;
		public final Color color;
		public Team(int id, Color color) {
			this.id = id;
			this.color = color;
		}
	}
	
}
