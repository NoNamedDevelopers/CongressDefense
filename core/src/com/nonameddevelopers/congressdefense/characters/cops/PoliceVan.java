package com.nonameddevelopers.congressdefense.characters.cops;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.CopManager;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;

public class PoliceVan {

	private final CongressDefense game;

	private int x, y;
	private Sprite sprite;
	private int numCop;
	private GameCamera camera;
	protected float stateTime;
	private float elapsedTime;
	private Random rand;

	public PoliceVan(CongressDefense game, int x, int y, GameCamera camera) {
		this.game = game;
		this.camera = camera;
		this.x = x;
		this.y = y;
		numCop = 0;
		stateTime = 0f;
		sprite = new Sprite(new Texture(Gdx.files.internal("sprites/van/0000.png")));
		sprite.setSize(64, 64);
		sprite.setPosition(x, y);
		rand = new Random();
	}

	public void update(float delta, CopManager copManag) {
		if (numCop < 4) {
			stateTime += delta;	
			if (Math.abs(stateTime-elapsedTime) >= 5) {
				elapsedTime = stateTime;
				int ypos = rand.nextInt(1001)-500;
				int xpos = rand.nextInt(1001)-500;
				Vector2 direccion = new Vector2 (xpos, ypos);
				direccion = direccion.nor();
				Cop cop = new MeleeCop(game, x+direccion.x*35 +20, y+direccion.y*35 +20);
				addPolice(copManag, cop);
				numCop++;
			}
		}
	} 



	public void draw(SpriteBatch batch) {
		sprite.draw(batch);

	}

	public CopManager addPolice(CopManager copManag, Cop cop) {
		copManag.addCop(cop);
		return copManag;
	}
}
