package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.nonameddevelopers.congressdefense.characters.BazookaCop;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.MeleeCop;
import com.nonameddevelopers.congressdefense.ui.CopIcon;

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
	
	public void update() {
		for (CopIcon icon : inputListener.menu) 
			if (icon.wasPressed()) {
				boolean available = true;
				availableCircle = new Circle();
				availableCircle.set(inputListener.getX(), inputListener.getY(), 20f);
				
				if(!Intersector.overlaps(icon.circle, availableCircle)) {
					for (Cop cop: copMan.getCops())
						if (Intersector.overlaps(cop.getBoundingCircle(), availableCircle)) 
							available= false;
					
					if (available && icon.canBuy()) {
							game.money -= icon.getCost();
							Cop police = icon.newCop(game, inputListener.getX(), inputListener.getY());
							copMan.addCop(police);
					}
					
				}
			}
	}

}
