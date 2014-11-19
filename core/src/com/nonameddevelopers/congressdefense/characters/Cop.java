package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Circle;
import com.nonameddevelopers.congressdefense.CongressDefense;


public abstract class Cop extends GameCharacter {
	
	protected Animation currentAnimation;
	
	private boolean isPlanted = false;
	
	protected boolean isAttacking = false;
	
	public Cop(final CongressDefense game, float x, float y, String type, int columns, int rows, float animationSpeed) {
		super(game, x-16, y-16, type, columns, rows, animationSpeed);		
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

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public void setBoundingCircle(Circle boundingCircle) {
		this.boundingCircle = boundingCircle;
	}
	
	public void setPosition(float x, float y) {
		this.x = x-16;
		this.y = y-16;
		this.boundingCircle.x = x;
		this.boundingCircle.y = y;
	}
	
	public void plant() {
		isPlanted = true;
	}
	
	public boolean isPlanted() {
		return isPlanted;
	}
	
	
	
}
