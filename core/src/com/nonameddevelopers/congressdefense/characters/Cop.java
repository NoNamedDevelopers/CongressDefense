package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.math.Circle;
import com.nonameddevelopers.congressdefense.CongressDefense;


public abstract class Cop extends GameCharacter {
	
	protected float elapsedTime;
	protected boolean isAttacking;
	
	public Cop(final CongressDefense game, float x, float y, String type, int columns, int rows, float animationSpeed) {
		super(game, x, y, type, columns, rows, animationSpeed);		
		
		elapsedTime = -0.5f;
		isAttacking = false;
		boundingCircle.set(x+16, y+16, 10f);
	}
		
	public void update(float delta) {
		if (isAttacking)
			stateTime += delta;		
		else
			stateTime = 0;
		updateAnimation();
	}
	
	@Override
	protected void updateAnimation() {
		switch (direction) {
		case UP_RIGHT:
			currentFrame = urAnimation.getKeyFrame(stateTime, true);
			break;
		case DOWN_RIGHT:
			currentFrame = drAnimation.getKeyFrame(stateTime, true);
			break;
		case UP_LEFT:
			currentFrame = ulAnimation.getKeyFrame(stateTime, true);
			break;
		case DOWN_LEFT:
			currentFrame = dlAnimation.getKeyFrame(stateTime, true);
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

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public void setBoundingCircle(Circle boundingCircle) {
		this.boundingCircle = boundingCircle;
	}
	
	
	
	
}
