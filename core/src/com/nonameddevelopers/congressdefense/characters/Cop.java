package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Circle;
import com.nonameddevelopers.congressdefense.CongressDefense;


public abstract class Cop extends GameCharacter {
	
	protected Animation currentAnimation;
	
	protected boolean isAttacking;
	
	public Cop(final CongressDefense game, float x, float y, String type, int columns, int rows, float animationSpeed) {
		super(game, x-16, y-16, type, columns, rows, animationSpeed);		
		
		isAttacking = false;
		boundingCircle.set(x+16, y+16, 10f);
	}
		
	public void update(float delta) {
		if (isAttacking) {
			stateTime += delta;	
			if (stateTime>=currentAnimation.getAnimationDuration()) {
				stateTime = 0;
				isAttacking = false;
			}
		}
		
		updateAnimation();
	}
	
	@Override
	protected void updateAnimation() {
		switch (direction) {
		case UP_RIGHT:
			currentAnimation = urAnimation;
			break;
		case DOWN_RIGHT:
			currentAnimation = drAnimation;
			break;
		case UP_LEFT:
			currentAnimation = ulAnimation;
			break;
		case DOWN_LEFT:
			currentAnimation = dlAnimation;
			break;
		}
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
	}
	
	public abstract void checkCollision(Crowd crowd);

	@Override
	public String toString() {
		return "Cop [boundingCircle=" + boundingCircle +", game=" + game
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
