package com.nonameddevelopers.congressdefense.characters;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.CopDisplayer;
import com.nonameddevelopers.congressdefense.GameCamera;

public class PoliceVan {

	private final CongressDefense game;

	private int x, y;
	private Sprite sprite;
	private boolean touched = false;
	private GameCamera camera;
	private ArrayList<Cop> polices;

	public PoliceVan(CongressDefense game, int x, int y, GameCamera camera) {
		this.game = game;
		this.camera = camera;
		this.x = x;
		this.y = y;
		polices = new ArrayList<Cop>();
		sprite = new Sprite(new Texture(Gdx.files.internal("sprites/van/0000.png")));
		sprite.setSize(64, 64);
		sprite.setPosition(x, y);
	}

	public void update(float delta) {
		if (!touched) {
			if (Gdx.input.isTouched()) {
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);

				if (touchPos.x > x && touchPos.x < x + 32 && touchPos.y > y
						&& touchPos.y < y + 32) {
					touched = true;
					addPolices();
					System.out.println(game.collisionManager);
					//setCollisions();
				}
			}
		} else
			updatePolices(delta);
	}

	private void updatePolices(float delta) {
		for (Cop cop : polices)
			cop.update(delta);
	}

	private void setCollisions() {
		for (Cop cop : polices)
			game.collisionManager.addCop(cop);
	}

	private void addPolices() {
		polices.add(new Cop(game, x + 20, y + 20));
		polices.add(new Cop(game, x - 20, y + 20));
		polices.add(new Cop(game, x + 20, y - 20));
	}

	public void draw(SpriteBatch batch) {
		if (!touched)
			sprite.draw(batch);
		else {
			for (Cop cop : polices)
				cop.draw(batch);
			sprite.draw(batch);
		}
	}

	public void checkCollision(Crowd crowd) {
		if (!polices.isEmpty() && touched) {
			for (Cop cop : polices)
				cop.checkCollision(crowd);
		}
	}

	public CopDisplayer updatePolices(CopDisplayer copDisplayer) {
		for (Cop cop : polices)
			copDisplayer.addCop(cop);
		return copDisplayer;
	}
}
