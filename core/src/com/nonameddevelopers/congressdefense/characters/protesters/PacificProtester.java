package com.nonameddevelopers.congressdefense.characters.protesters;

import java.util.Random;

import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.characters.Protester;

public class PacificProtester extends Protester {
	
	private static Random r = new Random();

	public PacificProtester(CongressDefense game, float x, float y, float appearTime) {
		super(game, x, y, "protester" + 0, 4, 5, appearTime,0.02f);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	

}
