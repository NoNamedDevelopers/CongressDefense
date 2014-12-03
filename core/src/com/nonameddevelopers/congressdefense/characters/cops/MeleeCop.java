package com.nonameddevelopers.congressdefense.characters.cops;

import com.badlogic.gdx.math.Intersector;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Crowd;
import com.nonameddevelopers.congressdefense.characters.Protester;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;

public class MeleeCop extends Cop {
	
	protected static GameSound punch;

	static {
		punch = new GameSound("sounds/punch.mp3", 330);
	}
	
	public MeleeCop(CongressDefense game, float x, float y) {
		super(game, x, y, "cop", 4, 6, 0.018f);
		
		boundingCircle.set(x, y, 20f);		
	}	

	
	public void checkCollision(Crowd crowd) {
		for (Protester protester : crowd.getProtesters())
			if (Intersector.overlaps(protester.getBoundingCircle(), boundingCircle)) {
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
					break;
				}
			}		
	}

}
