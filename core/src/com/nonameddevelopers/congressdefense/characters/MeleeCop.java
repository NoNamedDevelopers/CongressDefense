package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Intersector;
import com.nonameddevelopers.congressdefense.CongressDefense;

public class MeleeCop extends Cop {
	
	protected Sound punch;

	public MeleeCop(CongressDefense game, float x, float y) {
		super(game, x, y, "cop", 4, 6, 0.018f);
		
		boundingCircle.set(x+16, y+16, 10f);
		
		punch = Gdx.audio.newSound(Gdx.files.internal("sounds/punch.mp3"));
	}	

	
	public void checkCollision(Crowd crowd) {
		for (Protester protester : crowd.getProtesters())
			if (Intersector.overlaps(protester.getBoundingCircle(), boundingCircle)) {
				isAttacking = true;
				if (x-protester.x >0 && y-protester.y>0)
					direction = DOWN_RIGHT;
				else if (x-protester.x > 0 && y-protester.y < 0)
					direction = UP_RIGHT;
				else if (x-protester.x < 0 && y-protester.y < 0)
					direction = UP_LEFT;
				else
					direction = DOWN_LEFT;
	
				if (stateTime == 0f) {
					punch.play();
					protester.hurt(40);
					break;
				}
			}		
	
	}

}
