package com.frez.turretwars;

import com.frez.turretwars.entities.*;
import com.badlogic.gdx.graphics.*;
import com.frez.turretwars.resources.*;
import com.badlogic.gdx.math.*;
import com.frez.turretwars.ui.*;
import com.frez.turretwars.animation.Animator;
import com.frez.turretwars.animation.Animation;
import com.frez.turretwars.box2dutils.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.*;

public class Player extends Entity {
	
	public static final Team TEAM_RED = new Team(0, new Color(1, 0, 0, 1));
	public static final Team TEAM_BLUE = new Team(1, new Color(0, 0, 1, 1));
	
	private Team team;
	
	private SpriteModel.Part partTorso, partHead;
	
	private Body body;
	
	private UIContainer ui;
	private Button btnAction, btnNext, btnPrev;
	private Touchpad touchpad;
	
	private int money, moneyEarned, moneyEarnedTotal;
	private float walkSpeed;
	
	private Vector2 turretSpawnPos;
	
	private Box boxHolding;
	
	public Player() {
		super("player", null);
		
		maxHealth = 100;
		money = 3400;
		
		turretSpawnPos = new Vector2();
		
		partTorso = new SpriteModel.Part("torso", SpriteModel.Part.DRAWLEVEL_MIDDLE, Textures.get("player_torso_blue"), new Vector2(), new Vector2(20, 10), new Vector2(0, 0), new Vector2(1, 1));
		partTorso.animAng = new Animator(new Vector2(), true);
		partTorso.animAng.addAnimation("walk", Animations.get("player_torso_walk"));
		partTorso.animAng.addAnimation("walk_box", Animations.get("player_torso_walk_holding_box"));
		
		partHead = new SpriteModel.Part("head", SpriteModel.Part.DRAWLEVEL_UPPER, Textures.get("player_head_blue"), new Vector2(), new Vector2(10, 10), new Vector2(0, 0), new Vector2(1, 1));
		partHead.animPos = new Animator(new Vector2(), true);
		partHead.animPos.addAnimation("walk", Animations.get("player_head_walk"));
		partHead.animPos.addAnimation("idle", Animations.get("player_head_idle"));
		partHead.animPos.addAnimation("walk_box", Animations.get("player_head_walk_holding_box"));
		partHead.animPos.addAnimation("idle_box", Animations.get("player_head_idle_holding_box"));
		
		SpriteModel mdl = new SpriteModel();
		mdl.addPart(partTorso);
		mdl.addPart(partHead);
		
		mdl.playAnimation("idle", false);
		
		setModel(mdl);
		
		
		ui = new UIContainer();
		
		touchpad = new Touchpad();
		touchpad.setSize(new Vector2(128, 128));
		
		btnAction = new Button();
		btnAction.setText("Buy MV42 | 1200$");
		btnAction.setSize(new Vector2(128, 32));
		btnAction.setOnClickListener(new Button.OnClickListener() {
			public void onClick() {
				//GameLoop.debug("btnAction onClick()", true);
				if (GameManager.isBuildState()) {
					if (takeMoney(1200)) {
						Turret temp = (Turret) EntityManager.createEntity(TurretMV42.class);
						temp.pos.set(turretSpawnPos).add(pos);
						temp.getModel().pos.set(turretSpawnPos).add(pos);
					}
				}
			}
		});
		
		btnNext = new Button();
		btnNext.setText("Next");
		btnNext.setSubText("SP6");
		btnNext.setSize(new Vector2(64, 32));
		btnNext.setOnClickListener(new Button.OnClickListener() {
			public void onClick() {
				
			}
		});
		
		btnPrev = new Button();
		btnPrev.setText("Previous");
		btnPrev.setSubText("P13");
		btnPrev.setSize(new Vector2(64, 32));
		btnPrev.setOnClickListener(new Button.OnClickListener() {
			public void onClick() {
				
			}
		});
		
		ui.addUIObject(touchpad);
		ui.addUIObject(btnAction);
		ui.addUIObject(btnNext);
		ui.addUIObject(btnPrev);
		
		body = BodyCreator.circle(GameWorld.getPhysicsWorld(), new Vector2(), 0, 6, 0, 0, true);
		body.setFixedRotation(true);
		body.setUserData(this);
		
	}
	
	public void restart(Team team) {
		this.team = team;
		restart();
		if (team == TEAM_RED) {
			touchpad.setPos(new Vector2(Renderer.getUIWidth() - 128 - 32, 32));
			btnAction.setPos(new Vector2(32, 32));
			btnPrev.setPos(new Vector2(32, 64));
			btnNext.setPos(new Vector2(64+32, 64));
			
			partTorso.texture = Textures.get("player_torso_red");
			partHead.texture = Textures.get("player_head_red");
		} else if (team == TEAM_BLUE) {
			angle = 180;
			getModel().angle = 180;
			
			touchpad.setPos(new Vector2(32, Renderer.getUIHeight() - 128 - 32));
			btnAction.setPos(new Vector2(Renderer.getUIWidth() - 128 - 32, Renderer.getUIHeight() - 32 - 32));
			btnPrev.setPos(new Vector2(Renderer.getUIWidth() - 128 - 32, Renderer.getUIHeight() - 32 - 64));
			btnNext.setPos(new Vector2(Renderer.getUIWidth() - 128 + 32, Renderer.getUIHeight() - 32 - 64));
			
			touchpad.setUpsideDown(true);
			btnAction.setUpsideDown(true);
			btnPrev.setUpsideDown(true);
			btnNext.setUpsideDown(true);
			
			partTorso.texture = Textures.get("player_torso_blue");
			partHead.texture = Textures.get("player_head_blue");
		}
	}
	
	@Override
	public void start() {
		health = maxHealth;
		if (team == TEAM_RED) {
			pos.set(0, -50);
			body.setTransform(pos, 0);
		} else if (team == TEAM_BLUE) {
			pos.set(0, 50);
			body.setTransform(pos, 0);
		} else {
			pos.set(0, 0);
		}
		
		walkSpeed = 1f;
	}
	
	private boolean walking;
	
	@Override
	public void update() {
		pos.set(body.getPosition());
		
		Vector2 c = touchpad.getControl();
		if (GameManager.isPrepareState() || GameManager.isCooldownState())
			c.setZero();
			
		if (!c.isZero()) {

			angle = (float) Math.toDegrees(Math.atan2(-c.x, c.y));
			//partTorso.animAng.setPlaySpeed("walk", Math.max(Math.abs(c.x), Math.abs(c.y)));
			
			if (!walking) {
				getModel().playAnimation(boxHolding == null ? "walk" : "walk_box", false);
				walking = true;
			}
		} else {
			if (walking) {
				getModel().stopAnimation(boxHolding == null ? "walk" : "walk_box");
				walking = false;
			}
		}
		
		body.applyForceToCenter(new Vector2(c).nor().scl(body.getMass() * 120), true);
		body.setLinearVelocity(new Vector2(body.getLinearVelocity()).scl(0.9f));
		
		getModel().updateAnimations();
		getModel().pos.set(pos);
		
		float tempDeltaAngle = getModel().angle - angle;
		if (tempDeltaAngle > 180) {
			getModel().angle -= 360;
		} else if (tempDeltaAngle < -180) {
			getModel().angle += 360;
		}
		getModel().angle += (angle - getModel().angle) * (Gdx.graphics.getDeltaTime() * 6f);
		
		if (GameManager.getCurrentTimeState() == GameManager.TimeState.BUILD) {
			turretSpawnPos.set(-MathUtils.sinDeg(angle) * 12, MathUtils.cosDeg(angle) * 12);
		}
	}
	
	@Override
	public void draw() {
		Renderer.draw(getModel(), SpriteModel.Part.DRAWLEVEL_MIDDLE);
	}
	
	@Override
	public void drawUpper() {
		Renderer.draw(getModel(), SpriteModel.Part.DRAWLEVEL_UPPER);
	}
	
	@Override
	public void drawOver() {
		if (GameManager.getCurrentTimeState() == GameManager.TimeState.BUILD) {
			Renderer.setAdditiveRenderingWorld();
			
			SpriteBatch sb = Renderer.getSBWorld();
			sb.begin();
			sb.setColor(0.1f, 1.0f, 0.1f, 1f);
			sb.draw(Textures.get("turret_base"), pos.x + turretSpawnPos.x - 5, pos.y + turretSpawnPos.y - 5, 10, 10);
			sb.setColor(Color.WHITE);
			sb.end();
			Renderer.setMixingRenderingWorld();
			Renderer.setMixingRendering();
			
		}
	}
	
	public void setBox(Box box) {
		boxHolding = box;
		if (box != null) {
			getModel().stopAnimation("idle");
			getModel().playAnimation("idle_box", false);
			if (walking) {
				getModel().stopAnimation("walk");
				getModel().playAnimation("walk_box", false);
			}
		} else {
			getModel().stopAnimation("idle_box");
			getModel().playAnimation("idle", false);
			if (walking) {
				getModel().stopAnimation("walk_box");
				getModel().playAnimation("walk", false);
			}
		}
	}
	
	public void updateUI() {
		btnAction.setSubText(money + "$");
		ui.update();
	}
	
	public void drawUI() {
		ui.draw();
	}
	
	@Override
	public void destroy() {
		
	}
	
	public void setWalkSpeed(float speed) {
		walkSpeed = speed;
	}
	
	public float getWalkSpeed() {
		return walkSpeed;
	}
	
	public void giveMoney(int money) {
		this.money += money;
		this.moneyEarned += money;
		this.moneyEarnedTotal += money;
	}
	
	public boolean takeMoney(int money) {
		if (this.money - money >= 0) {
			this.money -= money;
			return true;
		}
		return false;
	}
	
	public void returnMoney(int money) {
		this.money += money;
	}
	
	public int getMoney() {
		return money;
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
