package com.nonameddevelopers.congressdefense;

import java.util.Comparator;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Crowd;
import com.nonameddevelopers.congressdefense.characters.GameCharacter;
import com.nonameddevelopers.congressdefense.characters.Protester;

public class EntityManager {
	
	private Array<GameCharacter> elementsToRender;
	
	private CrowdManager crowdMan;
	private CopManager copManager;
	private ProyectileLauncher proyectileL;
	private static EntityManager instance;
	
	
	private EntityManager(final CongressDefense game, GameCamera camera) {
		crowdMan = new CrowdManager(game, 10);
		copManager = new CopManager();
		proyectileL = new ProyectileLauncher(game, camera);
		instance = this;
		elementsToRender = new Array<GameCharacter>();
	}

	public static EntityManager getInstance(final CongressDefense game,
			GameCamera camera) {
		return (instance == null ? instance = new EntityManager(game, camera)
				: instance);

	}

	public static EntityManager getInstance() {
			return instance;
	}

	public void update(float delta) {
		crowdMan.update(delta);
		checkCollisions();
		copManager.update(delta);
		proyectileL.update(delta);
	}


	public void draw(SpriteBatch batch) {
		for (Crowd crowd : crowdMan.getCrowds())
			for (Protester protester : crowd.getProtesters())
				elementsToRender.add(protester);
		for (Cop cop : copManager.getCops())
			elementsToRender.add(cop);		
		
		elementsToRender.sort(new Comparator<GameCharacter>() {
			@Override
			public int compare(GameCharacter gm0, GameCharacter gm1) {		
				return (int) (gm1.getY()-gm0.getY());
			}			
		});
		
		proyectileL.draw(batch);
		

		Iterator<GameCharacter> iter = elementsToRender.iterator();
		while (iter.hasNext()) {
			iter.next().draw(batch);
			iter.remove();
		}
	}
	
	public CrowdManager getCrowdMan() {
		return crowdMan;
	}

	public void setCrowdMan(CrowdManager crowdMan) {
		this.crowdMan = crowdMan;
	}

	public CopManager getCopManager() {
		return copManager;
	}

	public void setCopManager(CopManager copManager) {
		this.copManager = copManager;
	}

	public ProyectileLauncher getProyectileL() {
		return proyectileL;
	}

	public void setProyectileL(ProyectileLauncher proyectileL) {
		this.proyectileL = proyectileL;
	}
	
	public void checkCollisions() {
		for (Crowd crowd : getCrowdMan().getCrowds()) 
			for (Cop cop : getCopManager().getCops()) 
				if (cop.isPlanted())
					cop.checkCollision(crowd);
	}

}
