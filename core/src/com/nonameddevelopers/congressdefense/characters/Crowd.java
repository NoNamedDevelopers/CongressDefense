package com.nonameddevelopers.congressdefense.characters;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.characters.protesters.AnarquistProtester;
import com.nonameddevelopers.congressdefense.characters.protesters.FatProtester;
import com.nonameddevelopers.congressdefense.characters.protesters.PabloIglesias;
import com.nonameddevelopers.congressdefense.characters.protesters.PacificProtester;

public class Crowd {

	private final CongressDefense game;
	private Array<Protester> crowd;
	private int[] percentage;
	
	public static final short PABLO_IGLESIAS = 0;
	
	private int wave;
	
	public Crowd(final CongressDefense game, int size, int[] percentage) {
		this.game = game;
		crowd = new Array<Protester>(); 
		this.percentage = percentage;
		populateCrowd(size);
	}
	
	public Crowd(final CongressDefense game, short bonusType, int wave) {
		this.game = game;
		this.wave = wave;
		crowd = new Array<Protester>(); 
		switch (bonusType) {
		case PABLO_IGLESIAS:
			crowd.add(new PabloIglesias(game, 0,0, 1f, wave));
			break;
		}
		
	}
	
	private Array<Protester> populateCrowd(int size) {
		
		
		Random r = new Random();
		int lado = r.nextInt(1000);
		if (lado%2==0)
		{
			int otroLado = r.nextInt(900)-100;
			for (int i = 0; i<size; i++)
			{
				float x = r.nextInt(100)-50;
				float y = r.nextInt(100)-50;
				int prob = r.nextInt(2000);
				if (prob < percentage[1])
					crowd.add(new FatProtester(game, -100 + x, otroLado + y, i*0.25f)); 	
				else if (prob < percentage[1]+percentage[2])
					crowd.add(new AnarquistProtester(game, -100 + x, otroLado + y, i*0.25f)); 	
				else
					crowd.add(new PacificProtester(game, -100 + x, otroLado + y, i*0.25f)); 
			}
			return crowd;
		}
		else
		{
			int otroLado = r.nextInt(900)-100;
			for (int i = 0; i<size; i++)
			{
				float x = r.nextInt(100)-50;
				float y = r.nextInt(100)-50;
				int prob = r.nextInt(2000);
				if (prob < percentage[1])
					crowd.add(new FatProtester(game, -100 + x, otroLado + y, i*0.25f)); 	
				else if (prob < percentage[1]+percentage[2])
					crowd.add(new AnarquistProtester(game, -100 + x, otroLado + y, i*0.25f)); 	
				else
					crowd.add(new PacificProtester(game, -100 + x, otroLado + y, i*0.25f)); 
	
			}
			return crowd;
		}
		
	}
	
	public void update(float delta) {
		Iterator<Protester> iter = crowd.iterator();
		while (iter.hasNext()) {
			Protester protester = iter.next();
			protester.update(delta);
			if (protester.isDead()) {
				iter.remove();
				protester = null;
			}
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
		return crowd.size;
	}

}
