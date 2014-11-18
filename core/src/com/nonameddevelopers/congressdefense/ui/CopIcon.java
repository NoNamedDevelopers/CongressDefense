package com.nonameddevelopers.congressdefense.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.GameCamera;
import com.nonameddevelopers.congressdefense.characters.BazookaCop;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.MeleeCop;

public class CopIcon {
	private static final Color TRANSPARENT = new Color(1,1,1,0.5f);

	public static final int MELEE = 0;
	public static final int BAZOOKA = 1;
	
	private CongressDefense game;
	private GameCamera camera;
	
	public Circle circle;
	
	private Texture texture;
	private Sprite sprite;
	
	private float x;
	private float y;
	private int type;
	
	private boolean isPressed;
	private boolean wasPressed;
	
	private int cost;
	
	public CopIcon(CongressDefense game, GameCamera camera, int cost, float x, float y, String src, int type) {
		this.game = game;
		this.camera = camera;
		this.cost = cost;
		this.x = x;
		this.y = y;
		this.type = type;
		circle = new Circle();
		texture = new Texture(Gdx.files.internal(src));
		sprite = new Sprite(texture);
		sprite.setSize(70,70);
	}
	
	public void update() {
		circle.set(camera.position.x-camera.viewportWidth/2+x+35,
				   camera.position.y+camera.viewportHeight/2-y+35,
				   35f);
		
		sprite.setPosition(camera.position.x-camera.viewportWidth/2+x, 
				   		   camera.position.y+camera.viewportHeight/2-y);
	}
	
	public void draw(SpriteBatch batch) {
		if (!canBuy())
			sprite.setColor(TRANSPARENT);
		else
			sprite.setColor(Color.WHITE);
		sprite.draw(batch);	
	}
	
	public Cop newCop(CongressDefense game, float x, float y) {
		switch(type) {
		case (BAZOOKA):
			return new BazookaCop(game,x,y);
		case (MELEE):
		default:
			return new MeleeCop(game,x,y);
		}
	}
	
	public void setPressed(float x, float y) {
		isPressed = circle.contains(x,y);
	}
	
	public boolean isPressed() {
		return isPressed;
	}
	
	public void release() {
		wasPressed = isPressed;
		isPressed = false;		
	}
	
	public boolean wasPressed() {
		if (wasPressed) {
			wasPressed = false;
			return true;
		}
		return false;
	}
	
	public boolean canBuy() {
		return cost<=game.money;
	}
	
	public int getCost() {
		return cost;
	}
}
