package com.nonameddevelopers.congressdefense.characters.protesters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.EntityManager;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Protester;

public class AnarquistProtester extends Protester {
	
	private Circle range;
	private float elapsedTime = 0;
	private Animation attackAnimation;
	private boolean isAttacking;
	
	public AnarquistProtester(CongressDefense game, float x, float y, float appearTime) {
		super(game, x, y, "anarquist", 4, 5, appearTime, 0.02f);
		
		range = new Circle();
		speedFactor = 1.2f;
		
		isAttacking = false;
		attackAnimation = loadAnimation("sprites/anarquist/attack.png", 4, 5, 0.02f);
	}
	


	@Override
	public void update(float delta) {
		super.update(delta);
		if (isAttacking && elapsedTime < attackAnimation.getAnimationDuration()) {
			elapsedTime += delta;
			currentFrame = attackAnimation.getKeyFrame(stateTime, true);
		} 
		else {
			isAttacking = false;
			elapsedTime = 0;
		}
	}
	
	
	
	@Override
	public void approach(float delta) {
		if (!isAttacking) {
			super.approach(delta);
			range.set(x+32, y+32, 20f);
		}
		checkCollitions(delta);
	}
	
	public void checkCollitions(float delta) {
		for (Cop cop : EntityManager.getInstance().getCopManager().getCops()) {
			if (Intersector.overlaps(getBoundingCircle(), cop.getBoundingCircle())) {
				attack(cop);
				break;
			}
		}
	}
	
	private void attack(Cop cop) {
		if (!isAttacking && !isGhost) {
			cop.hurt(10);
			isAttacking = true;
		}
	}
	
	

}
