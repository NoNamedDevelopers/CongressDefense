package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Crowd;

public class EntityManager {
	private CrowdManager crowdMan;
	private CopManager copManager;
	private ProyectileLauncher proyectileL;
	private final CongressDefense game;
	private GameCamera camera;
	private static EntityManager instance;
	
	
	public EntityManager(final CongressDefense game, GameCamera camera) {
		this.game = game;
		crowdMan = new CrowdManager(game, 10);
		copManager = new CopManager(game, camera);
		proyectileL = new ProyectileLauncher(game, camera);
		instance = this;
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

	public void setCamera(GameCamera camera) {
		this.camera = camera;
	}

	public void draw(SpriteBatch batch) {
		crowdMan.draw(batch);
		copManager.draw(batch);
		proyectileL.draw(batch);
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
				cop.checkCollision(crowd);
	}

}
