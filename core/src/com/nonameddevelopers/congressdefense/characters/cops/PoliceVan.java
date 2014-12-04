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
import com.nonameddevelopers.congressdefense.EntityManager;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Protester;
import com.nonameddevelopers.congressdefense.gameItems.GameCamera;

public class PoliceVan extends Cop {

	private int numCop;
	private GameCamera camera;
	protected float stateTime;
	private float elapsedTime;
	private Random rand;

	public static int TOP_WIDTH = 1054;
	public static int BOT_WIDTH = 440;

	private Vector2 direction2;

	private double speed;
	private float distance1;
	private float distance2;
	private boolean placed;
	private Vector2 position;

	public PoliceVan(CongressDefense game, int x, int y, GameCamera camera) {
		super(game, x, y, "van", 4, 5, 0.018f);
		this.camera = camera;
		xInit = 1000;
		yInit = 750;
		placed = false;
		numCop = 0;
		stateTime = 0f;
		speed = 1.7;
		rand = new Random();
		direction2 = new Vector2();
		direction2.set(new Vector2(BOT_WIDTH, 0))
				.sub(new Vector2(TOP_WIDTH, 1500)).nor();
		position = new Vector2();
		position.set(new Vector2(xInit, yInit)).sub(new Vector2(x, y));
		distance2 = position.len();
		distance1 = 5000;
	}

	@Override
	public void update(float delta) {
		// if (numCop < 4) {
		// stateTime += delta;
		// if (Math.abs(stateTime-elapsedTime) >= 5) {
		// elapsedTime = stateTime;
		// int ypos = rand.nextInt(1001)-500;
		// int xpos = rand.nextInt(1001)-500;
		// Vector2 direccion = new Vector2 (xpos, ypos);
		// direccion = direccion.nor();
		// Cop cop = new MovileCop(game, x+direccion.x*35 +20, y+direccion.y*35
		// +20);
		// addPolice(copManag, cop);
		// numCop++;
		// }
		// }
		if (!placed) {
			if (distance1 > distance2) {
				x += direction2.x * delta * 100 * speed;
				y += direction2.y * delta * 100 * speed;
				distance1 = distance2;
				position.set(new Vector2(xInit, yInit)).sub(new Vector2(x, y));
				distance2 = position.len();

				if (Math.abs(direction2.x) < 0.1f && direction2.y >= 0)
					this.direction = UP;
				else if (Math.abs(direction2.x) < 0.1f && direction2.y < 0)
					this.direction = DOWN;
				else if (direction2.x > 0 && Math.abs(direction2.y) < 0.1f)
					this.direction = RIGHT;
				else if (direction2.x < 0 && Math.abs(direction2.y) < 0.1f)
					this.direction = LEFT;
				else if (direction2.x > 0 && direction2.y > 0)
					this.direction = UP_RIGHT;
				else if (direction2.x > 0 && direction2.y < 0)
					this.direction = DOWN_RIGHT;
				else if (direction2.x < 0 && direction2.y > 0)
					this.direction = UP_LEFT;
				else
					this.direction = DOWN_LEFT;

				updateAnimation();
			} else {
				releaseCops();
				
			}
		} else {
			x += direction2.x * delta * 100 * speed;
			y += direction2.y * delta * 100 * speed;
			position.set(new Vector2(xInit, yInit)).sub(new Vector2(x, y));
			distance2 = position.len();

			if (Math.abs(direction2.x) < 0.1f && direction2.y >= 0)
				this.direction = UP;
			else if (Math.abs(direction2.x) < 0.1f && direction2.y < 0)
				this.direction = DOWN;
			else if (direction2.x > 0 && Math.abs(direction2.y) < 0.1f)
				this.direction = RIGHT;
			else if (direction2.x < 0 && Math.abs(direction2.y) < 0.1f)
				this.direction = LEFT;
			else if (direction2.x > 0 && direction2.y > 0)
				this.direction = UP_RIGHT;
			else if (direction2.x > 0 && direction2.y < 0)
				this.direction = DOWN_RIGHT;
			else if (direction2.x < 0 && direction2.y > 0)
				this.direction = UP_LEFT;
			else
				this.direction = DOWN_LEFT;

			updateAnimation();
		}

	}

	// public void draw(SpriteBatch batch) {
	// sprite.draw(batch);
	//
	// }

	private void releaseCops() {
		EntityManager.getInstance().getCopManager().getCopsToAdd().add(new SwatCop(game,x,y,xInit,yInit));
		placed=true;
		
	}

	public CopManager addPolice(CopManager copManag, Cop cop) {
		copManag.addCop(cop);
		return copManag;
	}

	@Override
	public void attackProtester(Protester protester) {
		// TODO Auto-generated method stub

	}
}
