package com.nonameddevelopers.congressdefense;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.characters.Crowd;
import com.nonameddevelopers.congressdefense.characters.Plane;

public class CrowdManager {
	
	
	private final CongressDefense game;
	private Array<Crowd> crowds;
	private int wave;
	private int numCrowds;
	private int protestantsPerWave;
	private int coef;
	private int [] percentage;

	public final static int PROTESTANTS_TUTORIAL = 1;
	public final static int PROTESTANTS_EASY = 4;
	public final static int PROTESTANTS_NORMAL = 8; 
	public final static int PROTESTANTS_HARD = 12;
	public final static int PROTESTANTS_CHUCK = 20;
	
	public final static int[] PERCENTAGE_EASY = {2000, 0, 0};
	public final static int[] PERCENTAGE_NORMAL = {2000, 0, 0}; 
	public final static int[] PERCENTAGE_HARD = {1900, 40, 20};
	public final static int[] PERCENTAGE_CHUCK = {1500, 150, 100};
	

	public final static int COEF_TUTORIAL = 6;
	public final static int COEF_EASY = 6;
	public final static int COEF_NORMAL = 4; 
	public final static int COEF_HARD = 2;
	public final static int COEF_CHUCK = 1;
	
	public final static int INCREASE_EASY = 1;
	public final static int INCREASE_NORMAL = 2; 
	public final static int INCREASE_HARD = 3;
	public final static int INCREASE_CHUCK = 4;
	
	
	//Para una versi�n con niveles y no infinita
	private int maxNumWaves;  // N�mero de oleadas de ese nivel
	private int increaseProtestant; // Cantidad a elevar del n�mero de protestantes por crowd por oleada
	private int[][] composition; // array de tipos de manifestantes por oleada (si llegamos a tener distintos tipos)
	
	

	public CrowdManager(final CongressDefense game) {
		this.game = game;
		game.plane = null;
		wave = 0;
		numCrowds = 1;
		switch (game.dificulty) {
		case CongressDefense.TUTORIAL:
			protestantsPerWave = PROTESTANTS_TUTORIAL;
			increaseProtestant = INCREASE_EASY;
			coef = COEF_TUTORIAL;
			percentage = PERCENTAGE_EASY;
			break;
		case CongressDefense.EASY:
			protestantsPerWave = PROTESTANTS_EASY;
			increaseProtestant = INCREASE_EASY;
			coef = COEF_EASY;
			percentage = PERCENTAGE_EASY;
			break;
		case CongressDefense.NORMAL:		
			protestantsPerWave = PROTESTANTS_NORMAL;
			increaseProtestant = INCREASE_NORMAL;
			coef = COEF_NORMAL;
			percentage = PERCENTAGE_NORMAL;
			break;
		case CongressDefense.HARD:
			protestantsPerWave = PROTESTANTS_HARD;
			increaseProtestant = INCREASE_HARD;
			coef = COEF_HARD;
			percentage = PERCENTAGE_HARD;
			break;
		case CongressDefense.CHUCK_NORRIS:
			protestantsPerWave = PROTESTANTS_CHUCK;
			increaseProtestant = INCREASE_CHUCK;
			coef = COEF_CHUCK;
			percentage = PERCENTAGE_CHUCK;
			break;
		}
		crowds = new Array<Crowd>();
	}

	public void update(float delta)
	{
		if (!game.isCrowdPaused) {
			if (!isCrowdsDead())
			{
				for (Crowd crowd: crowds)
				{
					crowd.update(delta);
				}
			}
			else
			{
				wave++;
				if (wave%10 == 0) {
					crowds.add(new Crowd(game, Crowd.PABLO_IGLESIAS, wave));
					if (game.plane == null) {
						game.plane = new Plane(game, null, "BONUS ROUND");
					}
				}
				else {
					if (game.plane == null) {
						game.plane = new Plane(game, null, "ROUND: "+wave);
					}
					setNumCrowds();
					setNumProtestants();
					setPercentage();
					for (int i = 0; i<numCrowds;i++)
						crowds.add(new Crowd(game, protestantsPerWave, percentage));
				}
			}
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
			percentage[1] += 10;
		if (percentage[2] <150)
			percentage[2] += 5;
		percentage [0] = 2000 - percentage[1] - percentage[2];
		
	}
	
	public void setNumProtestants()
	{
		if (game.dificulty == game.TUTORIAL)
		{
			protestantsPerWave = PROTESTANTS_TUTORIAL;
		}
		else if (game.dificulty == game.EASY)
		{
			protestantsPerWave = PROTESTANTS_EASY + wave*INCREASE_EASY/2;
		}
		else if (game.dificulty == game.NORMAL)
		{
			protestantsPerWave = PROTESTANTS_NORMAL  + wave*INCREASE_NORMAL/2;
		}
		else if (game.dificulty == game.HARD)
		{
			protestantsPerWave = PROTESTANTS_HARD  + wave*INCREASE_HARD/2;
		}
		else
		{
			protestantsPerWave = PROTESTANTS_CHUCK  + wave*INCREASE_CHUCK/2;
		}
	}
	
	
}
