package com.nonamedevelopers.congressdefense.characters;

import com.nonamedevelopers.congressdefense.CongressDefense;


public class Cop extends GameCharacter {

	public Cop(CongressDefense game, float x, float y) {
		super(game, x, y, "cop", 4, 6, 0.02f);
		direction = UP_LEFT;
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
	
	
}
