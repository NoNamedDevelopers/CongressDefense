package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.MeleeCop;

public class CopDisplayer {

	private CopManager copMan;
	private CongressDefense game;
	private GameCamera camera;
	
	public CopDisplayer(CopManager copMan, CongressDefense game, GameCamera camera) {
		this.game = game;
		this.camera = camera;
		this.copMan = copMan;
	}
	
	public void update()
	{
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			boolean available = true;
			Circle availableCircle = new Circle();
			availableCircle.set(touchPos.x, touchPos.y, 20f);

			for (Cop cop: copMan.getCops())
			{
				if (Intersector.overlaps(cop.getBoundingCircle(), availableCircle)) {
					available= false;
					
				}
			}
			if (available) {
				if (game.money>=20)
				{
					game.money -= 20;
					Cop police = new MeleeCop(game, touchPos.x, touchPos.y);
					copMan.addCop(police);
				}
			}
		}
	}

}
