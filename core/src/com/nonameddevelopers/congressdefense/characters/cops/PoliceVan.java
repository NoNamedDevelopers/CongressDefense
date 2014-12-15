package com.nonameddevelopers.congressdefense.characters.cops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.CopManager;
import com.nonameddevelopers.congressdefense.EntityManager;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Protester;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;

public class PoliceVan extends Cop {

	public static int TOP_WIDTH = 1054;
	public static int BOT_WIDTH = 440;

	private Vector2 direction2;

	private float placedTime = 0f;
	private float speed;
	private boolean placed;
	private float targetX;
	private float targetY;
	
	private boolean sirenPlayed;
	
	private static GameSound siren;

	static {
		siren = new GameSound("sounds/siren.mp3", 2900);
	}

	public PoliceVan(CongressDefense game, int x, int y, float targetX, float targetY) {
		super(game, x, y, "van", 4, 5, 0.05f);
		this.targetX = targetX;
		this.targetY = targetY;
		placed = false;
		speed = 2.5f;
		direction2 = new Vector2();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (y <1300 && !sirenPlayed) {
			sirenPlayed = true;
			siren.play(0.2f);
		}
		float initialX = x;
		float initialY = y;
		isRunning = true;
		if (!placed) {
			if (Math.abs(targetY-y) > 5f) {
				//speed -= 0.2f*delta;
				direction2.set(new Vector2((1600-targetY)*1.5f, targetY)).sub(new Vector2(x, y))
				.nor();
				x += direction2.x * 100 * speed*Gdx.graphics.getDeltaTime();
				y += direction2.y * 100 * speed*Gdx.graphics.getDeltaTime();
			}
			else {
				if (placedTime < 0.5f) {
					placedTime += delta;
				}
				else {
					releaseCops();
				}				
			}
		} else {
			if (placedTime < 1f) {
				placedTime += delta;
			}
			else {
				//speed += 0.05f;
				direction2.set(new Vector2(2000, 190)).sub(new Vector2(x, y))
				.nor();
				x += direction2.x * 100 * speed*Gdx.graphics.getDeltaTime();
				y += direction2.y * 100 * speed*Gdx.graphics.getDeltaTime();
				if (Math.abs(0-y) <5f) {
					isDead = true;
				}
			}
		}
		if ( Math.abs(x-initialX) < 1 && initialY-y < 0)
			direction = UP;
		else if (  Math.abs(x-initialX)  < 1 && initialY-y > 0)
			direction = DOWN;
		else if (initialX-x < 0 && Math.abs(initialY-y) < 1)
			direction = RIGHT;
		else if (initialX-x > 0 &&  Math.abs(initialY-y) < 1)
			direction = LEFT;
		else if (initialX-x > 0 && initialY-y > 0)
			direction = DOWN_LEFT;
		else if (initialX-x > 0 && initialY-y < 0)
			direction = UP_LEFT;
		else if (initialX-x < 0 && initialY-y < 0)
			direction = UP_RIGHT;
		else
			direction = DOWN_RIGHT;

	}

	private void releaseCops() {
		EntityManager.getInstance().getCopManager().getCopsToAdd().add(new SwatCop(game,x,y, targetX, targetY));
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
