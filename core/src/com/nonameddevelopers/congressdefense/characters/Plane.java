package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;

public class Plane {
	
	private CongressDefense game;
	private GameCamera camera;
	private static GameSound fly;
	
	private Texture planeTexture;
	private Sprite plane;
	private boolean isPlayedSound = false;
	private float scale = 1f;
	private float x;
	private float y;
	private String text;
	
	static {
		fly = new GameSound("sounds/plane.mp3", 7200);		
	}
	
	public Plane(CongressDefense game, GameCamera camera, String text) {
		this.game = game;
		this.camera = camera;
		planeTexture = new Texture("sprites/plane.png");
		plane = new Sprite(planeTexture);
		x = -516;
		this.text = text;
	}
	
	public void update(float delta) {
		if (camera.worldWidth>=x) {
			y = camera.position.y + camera.effectiveViewportHeight/2 - 300*scale;
			x += 400*delta; 
			plane.setSize(516*scale, 128*scale);
			plane.setPosition(x, y);
			if (!isPlayedSound) {
				isPlayedSound = true;
				fly.play(0.15f);
			}
		}
	}
	
	public void draw(SpriteBatch batch) {
		plane.draw(batch);
		game.font.setScale(scale*1.2f);
		game.font.draw(batch, text, x+60*scale, y+80*scale);
		game.font.setScale(1f);
	}
	
	public void dispose() {
		planeTexture.dispose();
	}
	
	public void setScale(float scaleXY) {
		this.scale = scaleXY;
	}
	
	public boolean end() {
		return camera.worldWidth<x;
	}
	
	public void setCamera(GameCamera camera) {
		this.camera = camera;
	}

}
