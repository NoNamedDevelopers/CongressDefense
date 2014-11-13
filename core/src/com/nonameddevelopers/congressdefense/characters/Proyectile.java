package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.nonameddevelopers.congressdefense.CongressDefense;

public class Proyectile {

	protected final CongressDefense game;	

	protected Animation ulAnimation, dlAnimation, urAnimation, drAnimation;
	protected TextureRegion currentFrame;
	protected float stateTime;
	
	private Protester target;
	
	private Circle boundingCircle;
	
	protected float x, y;
	protected short direction;
	
	public Proyectile(final CongressDefense game, float x, float y, Protester protester) {
		this.game = game;

		this.target = protester;
		
		this.x = x;
		this.y = y;
		
		boundingCircle.set(x + 16, y + 16, 20f);
		
		stateTime = 0f;
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(currentFrame, x, y);
	}
	
	public void update()
	{
		Vector2 direction = new Vector2();
		direction.set(target.x, target.y).sub(this.x, this.y).nor();
		x += direction.x * 20;
		y += direction.y * 20;
	}
	
	public void checkCollision(Crowd crowd) {
		if (Intersector.overlaps(target.getBoundingCircle(), boundingCircle)) {
			//hit.play();
			target.hurt(40);
			//this.dispose();
		}
	}


}
