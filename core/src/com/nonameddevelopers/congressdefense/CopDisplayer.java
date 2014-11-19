package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.ui.CopIcon;

public class CopDisplayer {
	
	private CopManager copMan;
	private CongressDefense game;
	private GameInputListener inputListener;

	private Circle availableCircle;
	private boolean available;
	
	private Cop copToPlant;
	
	public CopDisplayer(CopManager copMan, CongressDefense game, GameInputListener inputListener) {
		this.game = game;
		this.inputListener = inputListener;
		this.copMan = copMan;
	}
	
	public void update() {
		for (CopIcon icon : inputListener.menu) {
			if (icon.isPressed() && icon.canBuy()) {
				if (copToPlant == null) {
					copToPlant = icon.newCop(game, inputListener.getX(), inputListener.getY()+40);
					copMan.addCopToPlant(copToPlant);					
					availableCircle = new Circle();
				}
				available = true;
				
				copToPlant.setPosition(inputListener.getX(), inputListener.getY()+40);

				availableCircle.set(inputListener.getX(), inputListener.getY()+40, 20f);
				
				copToPlant.tint(Color.GREEN);
				
				if(!Intersector.overlaps(icon.circle, availableCircle)) {
					for (Cop cop: copMan.getCops())
						if (cop!=copToPlant 							
							&& Intersector.overlaps(cop.getBoundingCircle(), availableCircle))  {
							available= false;
							copToPlant.tint(Color.RED);
							break;
						}
				}
				
			}
			
			if (icon.wasPressed()) {				
				if (available  && icon.canBuy()) {
					game.money -= icon.getCost();
					copToPlant.plant();
				}
				else {
					copMan.deleteCopToPlant();
				}		
					
				copToPlant = null;
			}
		}
	}

}
