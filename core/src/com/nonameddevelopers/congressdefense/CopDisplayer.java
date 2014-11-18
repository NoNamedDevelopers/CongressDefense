package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.MeleeCop;

public class CopDisplayer {
	
	private CopManager copMan;
	private CongressDefense game;
	private GameInputListener inputListener;

	private Circle availableCircle;
	
	public CopDisplayer(CopManager copMan, CongressDefense game, GameInputListener inputListener) {
		this.game = game;
		this.inputListener = inputListener;
		this.copMan = copMan;
	}
	
	public void update()
	{
		if (inputListener.isCopIconPressed()) {
			
			
		}
		
		if (inputListener.wasCopIconPressed()) {
			boolean available = true;
			availableCircle = new Circle();
			availableCircle.set(inputListener.getX(), inputListener.getY(), 20f);
			
			if(!Intersector.overlaps(inputListener.getCopIconCircle(), availableCircle)) {
				for (Cop cop: copMan.getCops())
					if (Intersector.overlaps(cop.getBoundingCircle(), availableCircle)) 
						available= false;
				
				if (available) 
					if (game.money>=20)
					{
						game.money -= 20;
						Cop police = new MeleeCop(game, inputListener.getX(), inputListener.getY());
						copMan.addCop(police);
					}
				
			}
		}
	}

}
