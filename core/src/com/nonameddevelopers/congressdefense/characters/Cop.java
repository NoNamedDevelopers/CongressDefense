package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.math.Circle;
import com.nonameddevelopers.congressdefense.CongressDefense;


public abstract class Cop extends GameCharacter {
		
	private boolean isPlanted = false;
	
	protected boolean isAttacking = false;
	
	protected boolean isRunning = false;
	
	protected boolean isPressed = false;
	
	protected boolean isComingBack = false;
	
	protected float xInit;
	
	protected float yInit;
	
	

	public Cop(final CongressDefense game, float x, float y, String type, int columns, int rows, float animationSpeed) {
		super(game, x-32, y-32, type, columns, rows, animationSpeed);	
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		if (isAttacking || isRunning) {
			stateTime += delta;	
			if (stateTime>=currentAnimation.getAnimationDuration()) {
				stateTime = 0;
				isAttacking = false;
				isRunning = false;
			}
		}
		
		updateAnimation();
	}
	
	public abstract void checkCollision(Crowd crowd);

	public Circle getBoundingCircle() {
		return boundingCircle;
	}

	public void setBoundingCircle(Circle boundingCircle) {
		this.boundingCircle = boundingCircle;
	}
	
	public void setPosition(float x, float y) {
		this.x = x-32;
		this.y = y-32;
		this.boundingCircle.x = x;
		this.boundingCircle.y = y;
	}
	
	public void plant() {
		isPlanted = true;
	}
	
	public boolean isPlanted() {
		return isPlanted;
	}
	
	public void wasPressed(float x, float y) {
		isPressed = boundingCircle.contains(x,y);
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

	public boolean isPressed() {
		return isPressed;
	}
	public float getxInit() {
		return xInit;
	}

	public void setxInit(float xInit) {
		this.xInit = xInit;
	}

	public float getyInit() {
		return yInit;
	}

	public void setyInit(float yInit) {
		this.yInit = yInit;
	}
	
	@Override
	public void hurt(int damage) {
		if (isPlanted) {
			super.hurt(damage);
		}
	}
	
	@Override
	public void kill() {
		if (isPlanted) {
			super.kill();
		}
	}

	public boolean isDead() {
		return isDead;
	}
}
