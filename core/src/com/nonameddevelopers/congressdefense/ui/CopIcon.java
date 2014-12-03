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
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.cops.BazookaCop;
import com.nonameddevelopers.congressdefense.characters.cops.MeleeCop;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;

public class CopIcon {
	private static final Color TRANSPARENT = new Color(1,1,1,0.5f);

	public static final int MELEE = 0;
	public static final int BAZOOKA = 1;
	
	private CongressDefense game;
	private GameCamera camera;
	
	public Circle circle;
	
	private Texture texture;
	private Sprite sprite;
	
	private float xPosition;
	private float x;
	private float y;
	private int type;
	
	private boolean isPressed;
	private boolean wasPressed;
	
	private int cost;
	
	private float scale;
	
	public CopIcon(CongressDefense game, GameCamera camera, int cost, float xPosition, String src, int type) {
		this.game = game;
		this.camera = camera;
		this.cost = cost;
		this.xPosition = xPosition;
		this.x = 70;
		this.y = 110;
		this.type = type;
		this.scale = 1f;
		circle = new Circle();
		texture = new Texture(Gdx.files.internal(src));
		sprite = new Sprite(texture);
	}
	
	public void update() {
		circle.set(camera.position.x-camera.effectiveViewportWidth/2+(x+110*xPosition+50)*scale, 
	   		   camera.position.y+camera.effectiveViewportHeight/2-(y-50)*scale,
			   50f*scale);	
		sprite.setPosition(camera.position.x-camera.effectiveViewportWidth/2+(x+110*xPosition)*scale, 
			   camera.position.y+camera.effectiveViewportHeight/2-y*scale);
		sprite.setSize(100*scale,100*scale);		
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

			//return new MovileCop(game,x,y);
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
	
	public void setScale(float scaleXY) {
		this.scale = scaleXY;
	}
}
