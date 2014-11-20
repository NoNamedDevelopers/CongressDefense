package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;

public class Proyectile {
	
	protected final CongressDefense game;	

	private static Texture image;
	private TextureRegion texture;
	
	private Protester target;
	
	private Circle boundingCircle;
	
	private Vector2 direction;
	
	private float x, y;
	
	private boolean isDestroyed;
	
	private static GameSound ballHit;
	
	static {
		image = new Texture(Gdx.files.internal("sprites/copgun/ball.png"));
		ballHit = new GameSound("sounds/ball_hit.mp3", 170);
	}
	
	public Proyectile(final CongressDefense game, float x, float y, Protester protester) {
		this.game = game;
		this.target = protester;		
		this.x = x;
		this.y = y;
		

		
		direction = new Vector2();
		boundingCircle = new Circle();				
		isDestroyed = false;		
		
		texture = new TextureRegion(image);
	}
	
	public void draw(SpriteBatch batch) {
		if (!isDestroyed) 
			batch.draw(texture, x, y);		
	}
	
	public void update(float delta)
	{
		if (target.isDead()) {
			destroy();
			return;
		}
		direction.set(target.x, target.y).sub(this.x, this.y).nor();
		x += direction.x * 8 * delta * 50;
		y += direction.y * 8 * delta * 50;

		boundingCircle.set(x + 16, y + 16, 10f);
		
		if (Intersector.overlaps(target.getBoundingCircle(), boundingCircle)) {
			target.hurt(40);
			ballHit.play();
			destroy();
		}
	}
		
	private void destroy() {
		isDestroyed = true;
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
}
