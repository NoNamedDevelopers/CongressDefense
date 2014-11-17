package com.nonameddevelopers.congressdefense;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.nonameddevelopers.congressdefense.characters.Cop;

public class CopManager {
	
	private final CongressDefense game;
	
	private GameCamera camera;
	private ArrayList<Cop> cops;

	public CopManager(CongressDefense game, GameCamera camera) {
		this.game = game;
		this.camera = camera;
		this.cops = new ArrayList<Cop>();
	}
	
	public void update(float delta)
	{
		for (Cop cop: cops)
			cop.update(delta);
	}

	public void draw(SpriteBatch batch) {
		for (Cop cop: cops)
			cop.draw(batch);		
	}

	public void addCop(Cop cop) {
		cops.add(cop);	
	}

	public ArrayList<Cop> getCops() {
		return cops;
	}

	public void setCops(ArrayList<Cop> cops) {
		this.cops = cops;
	}
	
	

}
