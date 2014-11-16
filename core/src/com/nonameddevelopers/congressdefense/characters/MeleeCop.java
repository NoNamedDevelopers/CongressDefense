package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Intersector;
import com.nonameddevelopers.congressdefense.CongressDefense;

public class MeleeCop extends Cop {
	
	protected Sound punch;
	private float elapsedTime;

	public MeleeCop(CongressDefense game, float x, float y) {
		super(game, x, y, "cop", 4, 6, 0.02f);
		
		boundingCircle.set(x+16, y+16, 10f);
		
		punch = Gdx.audio.newSound(Gdx.files.internal("sounds/punch.mp3"));
	}	

	
	public void checkCollision(Crowd crowd) {
		boolean atLeastOne = false;
		for (Protester protester : crowd.getProtesters())
			if (Intersector.overlaps(protester.getBoundingCircle(), boundingCircle)) {
			atLeastOne = true;
			if (x-protester.x >0 && y-protester.y>0)
				direction = UP_RIGHT;
			else if (x-protester.x > 0 && y-protester.y < 0)
				direction = DOWN_RIGHT;
			else if (x-protester.x < 0 && y-protester.y < 0)
				direction = DOWN_LEFT;
			else
				direction = UP_LEFT;
			updateAnimation();	

				if (Math.abs(stateTime-elapsedTime) >= 0.5) {
					elapsedTime = stateTime;
					punch.play();
					protester.hurt(40);
					
				}
			}		
		if (!atLeastOne) {
			// EL ERROR DE LOS POLICIAS SUPER ASESINO ESTABA AQIO
//			stateTime = 0;
//			elapsedTime = -0.5f;
		}
	}

}
