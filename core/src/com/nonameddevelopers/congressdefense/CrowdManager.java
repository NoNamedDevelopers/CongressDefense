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
	private int coef;
	private int [] percentage;
	
	public final static int PROTESTANTS_EASY = 4;
	public final static int PROTESTANTS_NORMAL = 8; 
	public final static int PROTESTANTS_HARD = 12;
	public final static int PROTESTANTS_CHUCK = 20;
	
	public final static int[] PERCENTAGE_EASY = {2000, 0, 0, 0};
	public final static int[] PERCENTAGE_NORMAL = {2000, 0, 0, 0}; 
	public final static int[] PERCENTAGE_HARD = {1900, 20, 40, 60};
	public final static int[] PERCENTAGE_CHUCK = {1500, 100, 150, 250};
	
	public final static int COEF_EASY = 6;
	public final static int COEF_NORMAL = 4; 
	public final static int COEF_HARD = 2;
	public final static int COEF_CHUCK = 1;
	
	public final static int INCREASE_EASY = 1;
	public final static int INCREASE_NORMAL = 2; 
	public final static int INCREASE_HARD = 3;
	public final static int INCREASE_CHUCK = 4;
	
	
	//Para una versión con niveles y no infinita
	private int maxNumWaves;  // Número de oleadas de ese nivel
	private int increaseProtestant; // Cantidad a elevar del número de protestantes por crowd por oleada
	private int[][] composition; // array de tipos de manifestantes por oleada (si llegamos a tener distintos tipos)
	
	

	public CrowdManager(final CongressDefense game) {
		this.game = game;
		wave = 1;
		numCrowds = 1;
		if (game.dificulty == game.EASY)
		{
			protestantsPerWave = PROTESTANTS_EASY;
			increaseProtestant = INCREASE_EASY;
			coef = COEF_EASY;
			percentage = PERCENTAGE_EASY;
		}
		else if (game.dificulty == game.NORMAL)
		{
			protestantsPerWave = PROTESTANTS_NORMAL;
			increaseProtestant = INCREASE_NORMAL;
			coef = COEF_NORMAL;
			percentage = PERCENTAGE_NORMAL;
		}
		else if (game.dificulty == game.HARD)
		{
			protestantsPerWave = PROTESTANTS_HARD;
			increaseProtestant = INCREASE_HARD;
			coef = COEF_HARD;
			percentage = PERCENTAGE_HARD;
		}
		else
		{
			protestantsPerWave = PROTESTANTS_CHUCK;
			increaseProtestant = INCREASE_CHUCK;
			coef = COEF_CHUCK;
			percentage = PERCENTAGE_CHUCK;
		}
		crowds = new Array<Crowd>();
		crowds.add(new Crowd(game, protestantsPerWave, percentage));
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
			setNumProtestants();
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
		numCrowds = (2*wave)/coef +1;
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
	
	public void setNumProtestants()
	{
		protestantsPerWave = protestantsPerWave + increaseProtestant/2;
	}
	
	
}
