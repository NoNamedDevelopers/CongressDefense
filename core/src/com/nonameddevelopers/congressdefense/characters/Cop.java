package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.nonameddevelopers.congressdefense.CongressDefense;


public class Cop extends GameCharacter {
	
	private Circle boundingCircle;
	
	private Sound punch;
	
	private float elapsedTime;
	
	public Cop(CongressDefense game, float x, float y) {
		super(game, x, y, "cop", 4, 6, 0.02f);
		
		
		punch = Gdx.audio.newSound(Gdx.files.internal("sounds/punch.mp3"));
		
		direction = UP_LEFT;
		
		boundingCircle = new Circle();
		boundingCircle.set(x+16, y+16, 10f);
		
		updateAnimation();
	}
		
	public void update(float delta) {		
		stateTime += delta;		
		updateAnimation();
		
	}
	
	private void updateAnimation() {		
		switch(direction) {
			case UP_LEFT:
				currentFrame = ulAnimation.getKeyFrame(stateTime, true);
				break;
			case DOWN_LEFT:
				currentFrame = dlAnimation.getKeyFrame(stateTime, true);
				break;
			case UP_RIGHT:
				currentFrame = urAnimation.getKeyFrame(stateTime, true);
				break;
			case DOWN_RIGHT:
				currentFrame = drAnimation.getKeyFrame(stateTime, true);
				break;
		}
	}
	
	public void checkCollision(Crowd crowd) {
		boolean atLeastOne = false;
		for (Protester protester : crowd.getProtesters())
			if (Intersector.overlaps(protester.getBoundingCircle(), boundingCircle)) {
				atLeastOne = true;
				if (stateTime-elapsedTime >= 0.5f) {
					elapsedTime = stateTime;
					punch.play();
					protester.hurt(40);
				}
			}
		
		if (!atLeastOne) {
			stateTime = 0;
			elapsedTime = -0.5f;
		}
	}
	
	
}