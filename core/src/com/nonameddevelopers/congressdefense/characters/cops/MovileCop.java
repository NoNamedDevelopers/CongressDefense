package com.nonameddevelopers.congressdefense.characters.cops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Crowd;
import com.nonameddevelopers.congressdefense.characters.Protester;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;

public class MovileCop extends Cop {

	
	private Animation runUAnimation,runDAnimation,runLAnimation,runRAnimation,runUlAnimation,runDlAnimation,runUrAnimation,runDrAnimation;

	protected static GameSound punch;

	static {
		punch = new GameSound("sounds/punch.mp3", 330);
	}

	public MovileCop(CongressDefense game, float x, float y) {
		super(game, x, y, "cop", 4, 6, 0.018f);

		boundingCircle.set(x, y, 10f);

		xInit = x; // redefinido en copdisp
		yInit = y;

		range = new Circle();
		range.set(xInit + 16, yInit + 16, 100f);
		

		runUAnimation = loadAnimation("sprites/coprun/up.png", 4, 5, 0.018f);
		runDAnimation = loadAnimation("sprites/coprun/down.png", 4, 5, 0.018f);
		runLAnimation = loadAnimation("sprites/coprun/left.png", 4, 5, 0.018f);
		runRAnimation = loadAnimation("sprites/coprun/right.png", 4, 5, 0.018f);
		runUlAnimation = loadAnimation("sprites/coprun/up_left.png", 4, 5, 0.018f);
		runDlAnimation = loadAnimation("sprites/coprun/down_left.png", 4, 5, 0.018f);	
		runUrAnimation = loadAnimation("sprites/coprun/up_right.png", 4, 5, 0.018f);	
		runDrAnimation = loadAnimation("sprites/coprun/down_right.png",4, 5, 0.018f);
	}

	@Override
	public void checkCollision(Crowd crowd) {
		range.set(xInit, yInit, 100f);
		boundingCircle.set(x, y, 10f);
		Protester prot = null;
		Vector2 pos = new Vector2(this.x, this.y);
		float distance = 0;
		inRange = false;
		for (Protester protester : crowd.getProtesters()) {
			if (Intersector.overlaps(protester.getBoundingCircle(),
					boundingCircle)) {
				inRange = true;
				isAttacking = true;
				
				if (x-protester.getX() == 0 && y-protester.getY() > 0)
					direction = UP;
				else if (x-protester.getX() == 0 && y-protester.getY() < 0)
					direction = DOWN;
				else if (x-protester.getX() > 0 && y-protester.getY() == 0)
					direction = RIGHT;
				else if (x-protester.getX() < 0 && y-protester.getY() == 0)
					direction = LEFT;
				else if (x-protester.getX() > 0 && y-protester.getY() > 0)
					direction = DOWN_LEFT;
				else if (x-protester.getX() > 0 && y-protester.getY() < 0)
					direction = UP_LEFT;
				else if (x-protester.getX() < 0 && y-protester.getY() < 0)
					direction = UP_RIGHT;
				else
					direction = DOWN_RIGHT;

				if (stateTime == 0f) {
					punch.play();
					protester.hurt(40);
					stateTime += 0.001f;
				}
				break;
			}
		}
		if (!inRange) {
			for (Protester protester : crowd.getProtesters()) {
				if (Intersector.overlaps(protester.getBoundingCircle(), range)) {

					if (x-protester.getX() == 0 && y-protester.getY() > 0)
						direction = UP;
					else if (x-protester.getX() == 0 && y-protester.getY() < 0)
						direction = DOWN;
					else if (x-protester.getX() > 0 && y-protester.getY() == 0)
						direction = RIGHT;
					else if (x-protester.getX() < 0 && y-protester.getY() == 0)
						direction = LEFT;
					else if (x-protester.getX() > 0 && y-protester.getY() > 0)
						direction = DOWN_LEFT;
					else if (x-protester.getX() > 0 && y-protester.getY() < 0)
						direction = UP_LEFT;
					else if (x-protester.getX() < 0 && y-protester.getY() < 0)
						direction = UP_RIGHT;
					else
						direction = DOWN_RIGHT;
					
					Vector2 posProt = new Vector2(protester.getX(),
							protester.getY());
					if (prot == null) {
						prot = protester;
						distance = pos.sub(posProt).len();
					} else {
						if (pos.sub(posProt).len() < distance) {
							prot = protester;
							distance = pos.sub(posProt).len();
						}
					}
				}
			}
			// Random random = new Random();
			if (crowd.getProtesters().size != 0) {

				// int indice = random.nextInt(crowd.getProtesters().size);
				// prot = crowd.getProtesters().get(indice);}
				if (prot != null) {
					aproachProtester(prot);
					isRunning = true;
					return;
				} 
			}
			isRunning = Math.abs(x-xInit) > 5 ||  Math.abs(y-yInit) > 5;
			if (isRunning)
				backCenter();
			else 
				isComingBack = false;
		}
	
	}
	
	public void attackProtester(Protester protester) {

	}

	private void aproachProtester(Protester protester) {
		Vector2 position = new Vector2(x, y);
		Vector2 direction2 = new Vector2();
		Vector2 Goal = new Vector2(protester.getX(), protester.getY());
		direction2.set(Goal).sub(position).nor();
		x += direction2.x * 1.2f * Gdx.graphics.getDeltaTime() * 50;
		y += direction2.y * 1.2f * Gdx.graphics.getDeltaTime() * 50;
	}

	private void backCenter() {
		isComingBack = true;
		Vector2 position = new Vector2(x, y);
		Vector2 direction2 = new Vector2();
		Vector2 Goal = new Vector2(xInit, yInit);
		direction2.set(Goal).sub(position).nor();
		x += direction2.x * 1.5f * Gdx.graphics.getDeltaTime() * 50;
		y += direction2.y * 1.5f * Gdx.graphics.getDeltaTime() * 50;
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
	protected void updateAnimation() {
		if (!isRunning)
			super.updateAnimation();
		else {
			if (isComingBack) {
				if (xInit-x == 0 && yInit-y > 0)
					direction = UP;
				else if (xInit-x == 0 && yInit-y < 0)
					direction = DOWN;
				else if (xInit-x > 0 && yInit-y == 0)
					direction = RIGHT;
				else if (xInit-x < 0 && yInit-y == 0)
					direction = LEFT;
				else if (xInit-x > 0 && yInit-y > 0)
					direction =  UP_RIGHT;
				else if (xInit-x > 0 && yInit-y < 0)
					direction = DOWN_RIGHT;
				else if (xInit-x < 0 && yInit-y < 0)
					direction = DOWN_LEFT;
				else
					direction = UP_LEFT;
			}
			
			switch (direction) {
			case UP:
				currentAnimation = runUAnimation;
				break;
			case DOWN:
				currentAnimation = runDAnimation;
				break;
			case LEFT:
				currentAnimation = runLAnimation;
				break;
			case RIGHT:
				currentAnimation = runRAnimation;
				break;
			case UP_RIGHT:
				currentAnimation = runUrAnimation;
				break;
			case DOWN_RIGHT:
				currentAnimation = runDrAnimation;
				break;
			case UP_LEFT:
				currentAnimation = runUlAnimation;
				break;
			case DOWN_LEFT:
				currentAnimation = runDlAnimation;
				break;
			}
			currentFrame = currentAnimation.getKeyFrame(stateTime, true);
			}
		}

}
