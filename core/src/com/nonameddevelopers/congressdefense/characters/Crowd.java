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
		int lado = r.nextInt(1000);
		if (lado%2==0)
		{
			int otroLado = r.nextInt(900)-100;
			float x = r.nextInt(200)-100;
			float y = r.nextInt(200)-100;
			for (int i = 0; i<size; i++)
				crowd.insert(0,new Protester(game, -100 + x, otroLado + y, i*0.25f)); 		
			return crowd;
		}
		else
		{
			int otroLado = r.nextInt(900)-100;
			float x = r.nextInt(200)-100;
			float y = r.nextInt(200)-100;
			for (int i = 0; i<size; i++)
				crowd.insert(0,new Protester(game, otroLado + x, -100 + y, i*0.25f)); 		
			return crowd;
		}
		
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
	
	public int getNumberOfProtesters()
	{
		int i = 0;
		for (Protester man : crowd)
			i++;
		return i;
		
	}
}
