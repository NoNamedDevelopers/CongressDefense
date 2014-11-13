package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityManager {
	private CrowdManager crowdMan;
	private CopManager copManager;
	private ProyectileLauncher proyectileL;
	private final CongressDefense game;
	private GameCamera camera;
	
	public EntityManager(final CongressDefense game, GameCamera camera){
		this.game=game;
		this.camera=camera;
		crowdMan = new CrowdManager(game, 10);
		copManager = new CopManager(game, camera);
		proyectileL = new ProyectileLauncher(game, camera);
	}
	
	public void update(float delta, SpriteBatch batch){
		crowdMan.update(delta);
		copManager.update(delta, batch);
		proyectileL.update(batch);
		draw(batch);
	}
	
	public void draw(SpriteBatch batch){
		crowdMan.draw(batch);
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
	
	
}
