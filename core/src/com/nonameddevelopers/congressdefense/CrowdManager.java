package com.nonameddevelopers.congressdefense;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nonameddevelopers.congressdefense.characters.Crowd;

public class CrowdManager {
	
	
	private final CongressDefense game;
	private ArrayList<Crowd> crowds;
	private int wave;
	private int numCrowds;
	private int protestantsPerWave;
	private final static int coef = 2;
	
	
	
	
	//Para una versión con niveles y no infinita
	private int maxNumWaves;  // Número de oleadas de ese nivel
	private int increaseProtestant; // Cantidad a elevar del número de protestantes por crowd por oleada
	private int[][] composition; // array de tipos de manifestantes por oleada (si llegamos a tener distintos tipos)
	
	

	public CrowdManager(final CongressDefense game, int protestants) {
		this.game = game;
		wave = 1;
		numCrowds = 1;
		protestantsPerWave = protestants;
		crowds = new ArrayList<Crowd>();
		crowds.add(new Crowd(game, protestants));
		
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
			crowds = new ArrayList<Crowd>();
			for (int i = 0; i<numCrowds;i++)
				crowds.add(new Crowd(game, protestantsPerWave));
		}
	}
	
	public void draw(SpriteBatch Batch)
	{
		for (Crowd crowd: crowds)
		{
			crowd.draw(Batch);
		}
	}
	
	public boolean isCrowdsDead()
	{
		for (Crowd crowd: crowds)
		{
			if (crowd.getNumberOfProtesters()!=0)
				return false;
		}
		return true;
	}
	
	public void setNumCrowds()
	{
		numCrowds = wave/coef +1;
	}
}
