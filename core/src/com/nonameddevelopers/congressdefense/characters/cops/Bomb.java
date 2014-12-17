package com.nonameddevelopers.congressdefense.characters.cops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Crowd;
import com.nonameddevelopers.congressdefense.characters.Protester;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;

public class Bomb extends Cop{
	
	private static GameSound bombSound;
	private static Texture bombTexture;
	private static Texture smokeTexture;
	private Sprite smoke;
	
	
	private float targetX;
	private float targetY;
	
	private boolean secondTime;	
	private boolean inTarget;
	
	private float alpha = 1f;
	
	private float speed;
	
	private Vector2 direction;

	static {
		bombTexture = new Texture(Gdx.files.internal("sprites/bomb.png"));
		smokeTexture = new Texture(Gdx.files.internal("sprites/cloud.png"));
		bombSound = new GameSound("sounds/bomb.mp3", 2100);
		

	}
	
	public Bomb(CongressDefense game, float x, float y) {
		super(game, x-32, y-32, "", 0, 0, 0);
		range.set(x,y, 300f);
		this.x = 2000;
		this.y = 1500;
		this.targetX = x;
		this.targetY = y;
		currentFrame = new TextureRegion(bombTexture);
		smoke = new Sprite(smokeTexture);
		smoke.setSize(1000, 1000);
		direction = new Vector2();
	}	
	
	@Override
	public void update(float delta) {
		if (inTarget || Math.abs(targetX-x) < 50 && Math.abs(targetY-y) < 50 ) {
			if (!inTarget) {
				inTarget = true;
				currentFrame = new TextureRegion(smokeTexture);
				x = targetX-currentFrame.getRegionWidth()/2;
				y = 0;
				bombSound.play(0.2f);	
			}
			else {
				targetY += 50*delta;
				alpha -= delta;
				if (alpha <= 0f) {
					isDead = true;
				}
			}
		}		
		else {
			speed =30f/MathUtils.clamp(direction.set(new Vector2(targetX, targetY))
									   .sub(new Vector2(x, y))
									   .len()*delta, 1f, 2f);
			direction.set(new Vector2(targetX, targetY)).sub(new Vector2(x, y))
			.nor();
			x += direction.x * 100 * speed*Gdx.graphics.getDeltaTime();
			y += direction.y * 100 * speed*Gdx.graphics.getDeltaTime();
		}
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		if (inTarget) {
			y = targetY-currentFrame.getRegionHeight()/2;
			smoke.setAlpha(alpha);
			smoke.setPosition(x, y);
			smoke.draw(batch);
			y = 0;
		}
		else {
			super.draw(batch);
		}
	}
	
	@Override
	public void updateAnimation() {
		
	}
	
	@Override
	public void checkCollision(Array<Crowd> crowds) {
		if (inTarget && !secondTime) {
			for (Crowd crowd : crowds) {
				for (Protester protester : crowd.getProtesters()) {
					if (!protester.isGhost()) {
						if (Intersector.overlaps(protester.getBoundingCircle(), range)) {
							attackProtester(protester);
						}
					}
				}
			}
			secondTime = true;
		}
					
	}
	
	public void attackProtester(Protester protester) {
		protester.hurt(70);		
	}

}
