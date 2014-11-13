package com.nonameddevelopers.congressdefense;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Proyectile;

public class ProyectileLauncher {

	private final CongressDefense game;
	
	private GameCamera camera;
	private ArrayList<Proyectile> proyectiles;
	
	public ProyectileLauncher(CongressDefense game, GameCamera camera) {
		this.game = game;
		this.camera = camera;
		this.proyectiles = new ArrayList<Proyectile>();
	}
	
	public void update(SpriteBatch batch)
	{
		for (Proyectile proyectile: proyectiles)
		{
			proyectile.update();
			proyectile.draw(batch);
		}
	}

	public void addProyectile(Proyectile proyectile) {
		proyectiles.add(proyectile);	
	}

}
