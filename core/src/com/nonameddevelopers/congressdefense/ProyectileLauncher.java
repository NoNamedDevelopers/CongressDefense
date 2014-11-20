package com.nonameddevelopers.congressdefense;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.nonameddevelopers.congressdefense.characters.Proyectile;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;

public class ProyectileLauncher {

	private final CongressDefense game;
	
	private GameCamera camera;
	private Array<Proyectile> proyectiles;
	
	public ProyectileLauncher(CongressDefense game, GameCamera camera) {
		this.game = game;
		this.camera = camera;
		this.proyectiles = new Array<Proyectile>();
	}
	
	public void draw(SpriteBatch batch)
	{
		for (Proyectile proyectile: proyectiles)	
			proyectile.draw(batch);
	}
	
	public void update(float delta) {
		Iterator<Proyectile> iter = proyectiles.iterator();
		while(iter.hasNext()) {
			Proyectile proyectile = iter.next();
			proyectile.update(delta);
			if (proyectile.isDestroyed()) {
				iter.remove();
				proyectile = null;
			}
		}
	}
	
	public void addProyectile(Proyectile proyectile) {
		proyectiles.add(proyectile);	
	}

}
