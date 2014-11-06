package com.nonameddevelopers.congressdefense.characters;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.CongressDefense;

public class Crowd {

	private final CongressDefense game;
	private Array<Protester> crowd;
	
	public Crowd(final CongressDefense game, int size) {
		this.game = game;
		crowd = new Array<Protester>(); 
		populateCrowd(size);
	}
	
	private Array<Protester> populateCrowd(int size) {
		
		
		Random r = new Random();
		float x = r.nextFloat()*100;
		float y = r.nextFloat()*100;
		for (int i = 0; i<size; i++)
			crowd.insert(0,new Protester(game, r.nextFloat()*100 + x, r.nextFloat()*100+y, i*0.5f)); 		
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
	
	public Array<Protester> getProtesters() {
		return crowd;
	}
}
