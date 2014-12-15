package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.CongressDefense;


public abstract class Cop extends GameCharacter {
			
	protected boolean isAttacking = false;
	
	protected boolean isRunning = false;
	
	protected boolean isPressed = false;
	
	protected float xInit;
	
	protected float yInit;
	
	protected Circle range;

	public Cop(final CongressDefense game, float x, float y, String type, int columns, int rows, float animationSpeed) {
		super(game, x, y, type, columns, rows, animationSpeed);	
		range = new Circle();
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		boundingCircle.set(x+32,y+32, 20f);
		range.setPosition(x+32,y+32);
		if (isAttacking) {
			stateTime += delta;	
			if (stateTime>=currentAnimation.getAnimationDuration()) {
				stateTime = 0;
				isAttacking = false;
			}
			if (isRunning) {
				stateTime = 0;
				isRunning = false;
			}
		}	
		else if (isRunning) {
			stateTime += delta;	
		}
		updateAnimation();
	}
	
	public void checkCollision(Array<Crowd> crowds) {
		for (Crowd crowd : crowds) {
			for (Protester protester : crowd.getProtesters()) {
				if (!protester.isGhost()) {
					if (Intersector.overlaps(protester.getBoundingCircle(), range)) {
						isAttacking = true;
						if (stateTime == 0f) {
							if ( Math.abs(x-protester.getX()) < 20 && y-protester.getY() < 0)
								direction = UP;
							else if (  Math.abs(x-protester.getX()) < 20 && y-protester.getY() > 0)
								direction = DOWN;
							else if (x-protester.getX() < 0 && Math.abs(y-protester.getY()) < 20)
								direction = RIGHT;
							else if (x-protester.getX() > 0 &&  Math.abs(y-protester.getY()) < 20)
								direction = LEFT;
							else if (x-protester.getX() > 0 && y-protester.getY() > 0)
								direction = DOWN_LEFT;
							else if (x-protester.getX() > 0 && y-protester.getY() < 0)
								direction = UP_LEFT;
							else if (x-protester.getX() < 0 && y-protester.getY() < 0)
								direction = UP_RIGHT;
							else
								direction = DOWN_RIGHT;
						
							attackProtester(protester);
							break;
						}
						
					}
				}
			}
		}
	}
	
	public abstract void attackProtester(Protester protester);

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
		range.x = x;
		range.y = y;
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
		super.hurt(damage);
	}
	
	@Override
	public void kill() {
		super.kill();
		
	}

}
