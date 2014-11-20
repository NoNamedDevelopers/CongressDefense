package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.characters.cops.MeleeCop;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;

public class PoliceCaller {

	private final CongressDefense game;
	
	
	private int x ,y ;
	private Sprite sprite;
	private boolean touched = false;
	private GameCamera camera;
	private MeleeCop police;
	
	public PoliceCaller(CongressDefense game, int x, int y, GameCamera camera) {
		this.game = game;
		this.camera = camera;
		this.x = x;
		this.y = y;
		sprite = new Sprite(new Texture(Gdx.files.internal("shield.png")));
		sprite.setSize(32, 32);
		sprite.setPosition(x, y);
	}
	
	public void update(float delta) {
		if (!touched) {
			if (Gdx.input.isTouched()) {
				Vector3 touchPos = new Vector3();
				touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
				camera.unproject(touchPos);
				
				if (touchPos.x > x && touchPos.x < x+32
					&& touchPos.y > y && touchPos.y < y+32) {
					touched = true;
					police = new MeleeCop(game, x+32, y+32);
				}
				
			}
		}
		else
			police.update(delta);
	}
	
	public void draw(SpriteBatch batch) {		
		if (!touched)
			sprite.draw(batch);
		else
			police.draw(batch);
	}
	
	public void checkCollision(Crowd crowd) {
		if (police!=null)
			police.checkCollision(crowd);
	}
}
