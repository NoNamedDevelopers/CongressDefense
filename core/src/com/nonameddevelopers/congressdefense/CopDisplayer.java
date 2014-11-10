package com.nonameddevelopers.congressdefense;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.nonameddevelopers.congressdefense.characters.Cop;

public class CopDisplayer {
	
	private final CongressDefense game;
	
	private GameCamera camera;
	private ArrayList<Cop> cops;

	public CopDisplayer(CongressDefense game, GameCamera camera) {
		this.game = game;
		this.camera = camera;
		this.cops = new ArrayList<Cop>();
	}
	
	public void update(float delta, SpriteBatch batch)
	{
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			boolean libre = true;
			for (Cop cop: cops)
			{
				if (touchPos.x < (cop.getX()+10) && touchPos.x > (cop.getX()-10) && touchPos.y < (cop.getY()+10) && touchPos.y > (cop.getY()-10))
					libre = false;
			}
			if (libre){
				Cop police = new Cop(game, touchPos.x, touchPos.y);
				addCop(police);
				System.out.println(game.collisionManager);
				game.collisionManager.addCop(police);
			}
		}
		
		for (Cop cop: cops)
		{
			cop.update(delta);
			cop.draw(batch);
		}
	}

	private void addCop(Cop cop) {
		cops.add(cop);
		
	}

	public ArrayList<Cop> getCops() {
		return cops;
	}

	public void setCops(ArrayList<Cop> cops) {
		this.cops = cops;
	}
	
	

}
