package com.nonameddevelopers.congressdefense;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Crowd;
import com.nonameddevelopers.congressdefense.characters.GameCharacter;
import com.nonameddevelopers.congressdefense.characters.Protester;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;

public class EntityManager {
	
	private Array<GameCharacter> elementsToRender;
	
	private CrowdManager crowdMan;
	private CopManager copManager;
	private ProyectileLauncher proyectileL;
	private static EntityManager instance;
	
	private Texture  firstBuildingTexture ;
	private Sprite  firstBuilding;
	
	
	private EntityManager(final CongressDefense game, GameCamera camera) {
		crowdMan = new CrowdManager(game);
		copManager = new CopManager();
		proyectileL = new ProyectileLauncher(game, camera);
		instance = this;
		elementsToRender = new Array<GameCharacter>();
		

		firstBuildingTexture = new Texture(Gdx.files.internal("firstbuilding.png"));
		firstBuilding = new Sprite(firstBuildingTexture);
		firstBuilding.setPosition(0, 0);
		firstBuilding.setSize(camera.worldWidth, camera.worldHeight);
	}

	public static EntityManager getInstance(final CongressDefense game,
			GameCamera camera) {
		return (instance == null ? instance = new EntityManager(game, camera)
				: instance);

	}

	public static EntityManager getInstance() {
			return instance;
	}
	
	public static void empty() {
		instance = null;
	}

	public void update(float delta) {
		crowdMan.update(delta);
		copManager.update(delta);
		checkCollisions();
		proyectileL.update(delta);
	}


	public void draw(SpriteBatch batch) {
		for (Crowd crowd : crowdMan.getCrowds())
			for (Protester protester : crowd.getProtesters())
				elementsToRender.add(protester);
		for (Cop cop : copManager.getCops())
			elementsToRender.add(cop);		
		
		elementsToRender.sort();
		
		proyectileL.draw(batch);
		
		boolean buildingDrawn = false;
		Iterator<GameCharacter> iter = elementsToRender.iterator();
		while (iter.hasNext()) {
			GameCharacter gc = iter.next();
			if(gc.getY()<300 && !buildingDrawn) {
				buildingDrawn = true;
				firstBuilding.draw(batch);
			}
			gc.draw(batch);
			iter.remove();
		}
		if(!buildingDrawn){
			buildingDrawn = true;
			firstBuilding.draw(batch);
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
			for (Cop cop : getCopManager().getCops()) 
				cop.checkCollision(getCrowdMan().getCrowds());
	}



}
