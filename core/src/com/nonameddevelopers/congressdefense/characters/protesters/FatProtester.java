package com.nonameddevelopers.congressdefense.characters.protesters;

import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.characters.Protester;

public class FatProtester extends Protester {

	public FatProtester(CongressDefense game, float x, float y, float appearTime) {
		super(game, x, y, "fat", 4, 5, appearTime, 0.02f);
		life = 400;
		initialLife = life;
		speedFactor = 0.9f;
	}
	
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	

	@Override
	public void approach(float delta) {
		super.approach(delta);
	}

}
