package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.nonameddevelopers.congressdefense.CongressDefense;


public abstract class Cop extends GameCharacter {
	
	protected Circle boundingCircle;
	

	
	protected float elapsedTime;
	
	public Cop(CongressDefense game, float x, float y) {
		super(game, x, y, "cop", 4, 6, 0.02f);
		
		
		direction = UP_LEFT;
		
		boundingCircle = new Circle();
		boundingCircle.set(x+16, y+16, 10f);
		
		updateAnimation();
	}
		
	public void update(float delta) {
		stateTime += delta;		
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
	
	public abstract void checkCollision(Crowd crowd);

	@Override
	public String toString() {
		return "Cop [boundingCircle=" + boundingCircle + ", elapsedTime=" + elapsedTime + ", game=" + game
				+ ", ulAnimation=" + ulAnimation + ", dlAnimation="
				+ dlAnimation + ", urAnimation=" + urAnimation
				+ ", drAnimation=" + drAnimation + ", currentFrame="
				+ currentFrame + ", stateTime=" + stateTime + ", x=" + x
				+ ", y=" + y + ", direction=" + direction + "]";
	}
	
	
}
