package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.nonameddevelopers.congressdefense.CongressDefense;

public class Proyectile {
	
	protected final CongressDefense game;	

	private Texture image;
	private TextureRegion texture;
	
	private Protester target;
	
	private Circle boundingCircle;
	
	private float x, y;
	
	private boolean isDestroyed;
	
	private Sound ballHit;
	
	public Proyectile(final CongressDefense game, float x, float y, Protester protester) {
		this.game = game;
		this.target = protester;		
		this.x = x;
		this.y = y;
		
		boundingCircle = new Circle();				
		isDestroyed = false;		
		
		image = new Texture(Gdx.files.internal("sprites/copgun/ball.png"));
		texture = new TextureRegion(image);
		ballHit = Gdx.audio.newSound(Gdx.files.internal("sounds/ball_hit.mp3"));
	}
	
	public void draw(SpriteBatch batch) {
		if (!isDestroyed) 
			batch.draw(texture, x, y);		
	}
	
	public void update()
	{
		if (isDestroyed)
			return;
		
		Vector2 direction = new Vector2();
		direction.set(target.x, target.y).sub(this.x, this.y).nor();
		x += direction.x * 5;
		y += direction.y * 5;

		boundingCircle.set(x + 16, y + 16, 10f);
		if (Intersector.overlaps(target.getBoundingCircle(), boundingCircle)) {
			target.hurt(40);
			ballHit.play();
			destroy();
		}
	}
	
	
	private void destroy() {
		isDestroyed = true;
		dispose();
	}
	
	public void dispose() {
		image.dispose();		
	}

}
