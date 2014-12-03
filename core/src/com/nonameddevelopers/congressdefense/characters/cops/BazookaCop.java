package com.nonameddevelopers.congressdefense.characters.cops;

import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.EntityManager;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Protester;
import com.nonameddevelopers.congressdefense.characters.Proyectile;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;

public class BazookaCop extends Cop {
	
	private static GameSound shoot;

	static {
		shoot = new GameSound("sounds/shoot.mp3", 2000);
	}
	
	public BazookaCop(CongressDefense game, float x, float y) {
		super(game, x, y, "copgun", 5, 5, 0.02f);
		range.set(x,y, 200f);
	}
		
	public void attackProtester(Protester protester) {
		shoot.play(0.3f);
		shoot(protester);
		stateTime += 0.001f;
	}
	
	private void shoot(Protester protester)	{
		EntityManager.getInstance().getProyectileL().addProyectile(new Proyectile(game, x, y, protester));
	}

	
}
