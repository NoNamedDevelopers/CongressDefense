package com.nonameddevelopers.congressdefense.characters.cops;

import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Protester;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;

public class MeleeCop extends Cop {
	
	protected static GameSound punch;

	static {
		punch = new GameSound("sounds/punch.mp3", 330);
	}
	
	public MeleeCop(CongressDefense game, float x, float y) {
		super(game, x, y, "cop", 4, 6, 0.018f);
		range.set(x,y, 25f);
	}	
	
	public void attackProtester(Protester protester) {
		punch.play();
		protester.hurt(40);
		stateTime += 0.001f;		
	}

}
