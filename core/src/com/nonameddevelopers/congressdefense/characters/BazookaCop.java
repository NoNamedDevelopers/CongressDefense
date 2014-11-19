package com.nonameddevelopers.congressdefense.characters;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.EntityManager;
import com.nonameddevelopers.congressdefense.GameSound;

public class BazookaCop extends Cop {
	
	private static GameSound shoot;
	
	private Circle range;

	static {
		shoot = new GameSound("sounds/shoot.mp3", 2000);
	}
	
	public BazookaCop(CongressDefense game, float x, float y) {
		super(game, x, y, "copgun", 5, 5, 0.02f);

		boundingCircle.set(x, y, 10f);
		range = new Circle();
		range.set(x,y, 100f);
	}
	
	public void checkCollision(Crowd crowd) {
		for (Protester protester : crowd.getProtesters()) {
			if (Intersector.overlaps(protester.getBoundingCircle(), range)) {
				isAttacking = true;
				if (x-protester.x >0 && y-protester.y>0)
					direction = DOWN_RIGHT;
				else if (x-protester.x > 0 && y-protester.y < 0)
					direction = UP_RIGHT;
				else if (x-protester.x < 0 && y-protester.y < 0)
					direction = UP_LEFT;
				else
					direction = DOWN_LEFT;
				
				if (stateTime == 0f) {
					shoot.play(0.3f);
					shoot(protester);
					stateTime += 0.001f;
					break;
				}
				
			}
		}
	}
	
	private void shoot(Protester protester)
	{
		EntityManager.getInstance().getProyectileL().addProyectile(new Proyectile(game, x, y, protester));
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		range.x = x;
		range.y = y;
	}
	
}
