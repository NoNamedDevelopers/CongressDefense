package com.nonameddevelopers.congressdefense.characters.protesters;

import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.characters.Protester;

public class PabloIglesias extends Protester {
	
	private int wave;
	
	public PabloIglesias(CongressDefense game, float x, float y, float appearTime, int wave) {
		super(game, x, y, "pabloiglesias", 4, 5, appearTime,0.02f);
		this.wave = wave;
		life = 1;
		for (int i=0; i<3; i++)
			life *= wave;
		initialLife = life;
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	
	@Override
	public void addScoreAndMoney() {
		game.score += 500*wave;		
		game.money += 2*wave;
	}	

}