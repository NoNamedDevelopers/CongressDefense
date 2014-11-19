package com.nonameddevelopers.congressdefense;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.characters.Crowd;

public class CrowdManager {
	
	
	private final CongressDefense game;
	private Array<Crowd> crowds;
	private int wave;
	private int numCrowds;
	private int protestantsPerWave;
	private final static int coef = 2;
	private int [] percentage;
	
	
	
	
	//Para una versión con niveles y no infinita
	private int maxNumWaves;  // Número de oleadas de ese nivel
	private int increaseProtestant; // Cantidad a elevar del número de protestantes por crowd por oleada
	private int[][] composition; // array de tipos de manifestantes por oleada (si llegamos a tener distintos tipos)
	
	

	public CrowdManager(final CongressDefense game, int protestants) {
		this.game = game;
		wave = 1;
		numCrowds = 1;
		protestantsPerWave = protestants;
		percentage = new int[4];
		percentage[0] = 2000;
		percentage[1] = 0;
		percentage[2] = 0;
		percentage[3] = 0;
		crowds = new Array<Crowd>();
		crowds.add(new Crowd(game, protestants, percentage));
	}

	public void update(float delta)
	{
		if (!isCrowdsDead())
		{
			for (Crowd crowd: crowds)
			{
				crowd.update(delta);
			}
		}
		else
		{
			// esperar 1-2 segundos
			wave++;
			setNumCrowds();
			//setPercentage();
			for (int i = 0; i<numCrowds;i++)
				crowds.add(new Crowd(game, protestantsPerWave, percentage));
		}
	}
	
	public void draw(SpriteBatch batch)
	{
		for (Crowd crowd: crowds)
		{
			crowd.draw(batch);
		}
	}
	
	public boolean isCrowdsDead() {
		boolean isCrowdsDead = true;
		Iterator<Crowd> iter = crowds.iterator();
		while(iter.hasNext()) {
			Crowd crowd = iter.next();
			if (crowd.getNumberOfProtesters()==0) {
				iter.remove();
				crowd = null;
			}
			else
				isCrowdsDead = false;
				
		}
		return isCrowdsDead;
	}
	
	public void setNumCrowds()
	{
		numCrowds = wave/coef +1;
	}

	public Array<Crowd> getCrowds() {
		return crowds;
	}

	public void setCrowds(Array<Crowd> crowds) {
		this.crowds = crowds;
	}
	
	public void setPercentage()
	{
		if (percentage[1] <300)
			percentage[1] += 4;
		if (percentage[2] <200)
			percentage[2] += 2;
		if (percentage[3] <100)
			percentage[3] += 1;
		percentage [0] = 2000 - percentage[1] - percentage[2] - percentage[3];
		
	}
	
	
}
