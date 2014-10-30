package com.nonamedevelopers.congressdefense.characters;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.nonamedevelopers.congressdefense.CongressDefense;

public class Crowd {

	private final CongressDefense game;
	private Array<Protester> crowd;
	
	public Crowd(final CongressDefense game, int size) {
		this.game = game;
		crowd = new Array<Protester>(); 
		populateCrowd(size);
	}
	
	private Array<Protester> populateCrowd(int size) {
		float x = -32f;
		float y = 490f;
		
		
		for (int i = 0; i<size; i++)
			crowd.insert(0,new Protester(game, x, y, i*0.5f)); 		
		return crowd;
	}
	
	public void update(float delta) {
		Iterator<Protester> iter = crowd.iterator();
		while (iter.hasNext()) {
			Protester protester = iter.next();
			protester.update(delta);
			if (protester.isDead())
				iter.remove();				
		}
			
	}
	
	public void draw(SpriteBatch batch) {
		for (Protester man : crowd)
			man.draw(batch);
	}
}
