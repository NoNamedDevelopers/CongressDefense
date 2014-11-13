package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
			boolean libre = true;
			for (Cop cop: copMan.getCops())
			{
				if (touchPos.x < (cop.getX()+10) && touchPos.x > (cop.getX()-10) && touchPos.y < (cop.getY()+10) && touchPos.y > (cop.getY()-10))
					libre = false;
			}
			if (libre){
				if (game.money>=20)
				{
					game.money -= 20;
					Cop police = new MeleeCop(game, touchPos.x, touchPos.y);
					copMan.addCop(police);
					System.out.println(game.collisionManager);
					game.collisionManager.addCop(police);
				}
			}
		}
	}

}
