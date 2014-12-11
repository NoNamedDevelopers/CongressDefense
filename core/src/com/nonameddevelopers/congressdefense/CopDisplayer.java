package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.gameItems.GameInputListener;
import com.nonameddevelopers.congressdefense.ui.CopIcon;

public class CopDisplayer {

	private CopManager copMan;
	private CongressDefense game;
	private GameInputListener inputListener;

	private Circle availableCircle;
	private boolean available;

	public CopDisplayer(CopManager copMan, CongressDefense game,
			GameInputListener inputListener) {
		this.game = game;
		this.inputListener = inputListener;
		this.copMan = copMan;
	}

	public void update() {
		/*if (inputListener.menu.get(CopIcon.getIconSelected()) != null
			&& inputListener.menu.get(CopIcon.getIconSelected()).isSetting()) {
			availableCircle = new Circle();
			available = true;

			availableCircle.set(inputListener.getX(),
								inputListener.getY(), 20f);
			
			for (Cop cop : copMan.getCops()) {
				if (Intersector.overlaps(cop.getBoundingCircle(),
								availableCircle)) {
					available = false;
					break;
				}
			}
			if (available && inputListener.menu.get(CopIcon.getIconSelected()).canBuy()) {
				inputListener.menu.get(CopIcon.getIconSelected()).newCopSetter(copMan, inputListener.getX(),
								  inputListener.getY());
			}				
		}*/
	}

}
