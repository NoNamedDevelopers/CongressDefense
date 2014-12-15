package com.nonameddevelopers.congressdefense.characters.cops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Crowd;
import com.nonameddevelopers.congressdefense.characters.Protester;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;

public class SwatCop extends Cop {

	private Animation runUAnimation, runDAnimation, runLAnimation,
			runRAnimation, runUlAnimation, runDlAnimation, runUrAnimation,
			runDrAnimation;

	protected static GameSound punch;
	private float xDestino;
	private float yDestino;
	private boolean isInCenter;
	private Vector2 direction2;
	private float speed;
	private boolean protestersNear;
	private boolean isComingBack;
	
	private Protester theChosenOne;
	private boolean theChosenOneFinded;
	
	private Circle attackCircle;

	static {
		punch = new GameSound("sounds/punch.mp3", 330);
	}

	public SwatCop(CongressDefense game, float x, float y, float xDestino,
			float yDestino) {
		super(game, x, y, "swatcop", 4, 6, 0.018f);	
		xInit = x;
		yInit = y;
		this.xDestino = xDestino;
		this.yDestino = yDestino;
		speed = 1.8f;
		
		range.set(x,y, 25f);
		attackCircle = new Circle();
		attackCircle.set(xDestino, yDestino, 200f);

		loadsAnimations();

		direction2 = new Vector2();

	}

	private void loadsAnimations() {
		runUAnimation = loadAnimation("sprites/swatcop/run/up.png", 4, 5, 0.015f);
		runDAnimation = loadAnimation("sprites/swatcop/run/down.png", 4, 5, 0.015f);
		runLAnimation = loadAnimation("sprites/swatcop/run/left.png", 4, 5, 0.015f);
		runRAnimation = loadAnimation("sprites/swatcop/run/right.png", 4, 5, 0.015f);
		runUlAnimation = loadAnimation("sprites/swatcop/run/up_left.png", 4, 5,
				0.015f);
		runDlAnimation = loadAnimation("sprites/swatcop/run/down_left.png", 4, 5,
				0.015f);
		runUrAnimation = loadAnimation("sprites/swatcop/run/up_right.png", 4, 5,
				0.015f);
		runDrAnimation = loadAnimation("sprites/swatcop/run/down_right.png", 4, 5,
				0.015f);
	}

	@Override
	public void checkCollision(Array<Crowd> crowds) {
		float distance = 0f;
		Protester prot = null;
		Vector2 pos = new Vector2(this.x, this.y);
		
		isRunning = false;
		protestersNear = false;
		
		
		super.checkCollision(crowds);
		
		if (!isAttacking) {
			theChosenOneFinded = false;
			range.setRadius(10f);
			for (Crowd crowd : crowds) {
				for (Protester protester : crowd.getProtesters()) {
					if (!protester.isGhost() 
						&& Intersector.overlaps(protester.getBoundingCircle(),
							attackCircle)) {	
						
						protestersNear = true;
						if (x - protester.getX() == 0
								&& y - protester.getY() > 0)
							direction = UP;
						else if (x - protester.getX() == 0
								&& y - protester.getY() < 0)
							direction = DOWN;
						else if (x - protester.getX() > 0
								&& y - protester.getY() == 0)
							direction = RIGHT;
						else if (x - protester.getX() < 0
								&& y - protester.getY() == 0)
							direction = LEFT;
						else if (x - protester.getX() > 0
								&& y - protester.getY() > 0)
							direction = DOWN_LEFT;
						else if (x - protester.getX() > 0
								&& y - protester.getY() < 0)
							direction = UP_LEFT;
						else if (x - protester.getX() < 0
								&& y - protester.getY() < 0)
							direction = UP_RIGHT;
						else
							direction = DOWN_RIGHT;
	
	
						
						if (protester.equals(theChosenOne)) {
							theChosenOneFinded = true;
							break;
						}			
						
						Vector2 posProt = new Vector2(protester.getX(),
								protester.getY());
						if (prot == null) {
							prot = protester;
							distance = pos.sub(posProt).len();
						} 
						else if (pos.sub(posProt).len() < distance) {
							prot = protester;
							distance = pos.sub(posProt).len();
							
						}
					}
				}
			}
			
			if (!theChosenOneFinded) {
				theChosenOne = null;
				if (prot != null) {
					theChosenOne = prot;
				}
			}

			if (theChosenOne != null) {
				aproachProtester(theChosenOne);
			}
			else if (!protestersNear && !isInCenter) {
				backCenter();
			}
		}
		else {
			range.setRadius(30f);
		}
	}

	public void attackProtester(Protester protester) {
		punch.play();
		protester.hurt(80);
		stateTime += 0.001f;
	}

	private void aproachProtester(Protester protester) {
		isComingBack = false;
		isRunning = true;
		direction2.set(new Vector2(protester.getX(), protester.getY())).sub(new Vector2(x, y))
		.nor();
		x += direction2.x * 100 * speed*Gdx.graphics.getDeltaTime();
		y += direction2.y * 100 * speed*Gdx.graphics.getDeltaTime();
		isInCenter = false;
	}

	private void backCenter() {	
		isComingBack = true;
		isRunning = true;
		direction2.set(new Vector2(xDestino, yDestino)).sub(new Vector2(x, y))
		.nor();
		x += direction2.x * 100 * speed*Gdx.graphics.getDeltaTime();
		y += direction2.y * 100 * speed*Gdx.graphics.getDeltaTime();
		if(Math.abs(x-xDestino) < 3 && Math.abs(y-yDestino) < 3){
			isRunning = false;
			isInCenter = true;
			direction = DOWN;
		}
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
		super.updateAnimation();
		if (isComingBack) {
			if (Math.abs(x-xDestino) < 20 && yDestino - y > 0)
				direction = UP;
			else if (Math.abs(x-xDestino) < 20 && yDestino - y < 0)
				direction = DOWN;
			else if (xDestino - x > 0 && Math.abs(y-yDestino) < 20)
				direction = RIGHT;
			else if (xDestino - x < 0 && Math.abs(y-yDestino) < 20)
				direction = LEFT;
			else if (xDestino - x > 0 && yDestino - y > 0)
				direction = UP_RIGHT;
			else if (xDestino - x > 0 && yDestino - y < 0)
				direction = DOWN_RIGHT;
			else if (xDestino - x < 0 && yDestino - y < 0)
				direction = DOWN_LEFT;
			else
				direction = UP_LEFT;
		}
		if (isRunning) {		
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
		}
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
	}
}
