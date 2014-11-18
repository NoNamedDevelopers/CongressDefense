package com.nonameddevelopers.congressdefense.characters;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.nonameddevelopers.congressdefense.CongressDefense;

public class PacificProtester extends Protester {
	
	private static Random r = new Random();

	public PacificProtester(CongressDefense game, float x, float y, float appearTime) {
		super(game, x, y, "protester" + r.nextInt(3), 4, 5, appearTime);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
		
	@Override
	public void approach() {

		arrived();
		
		Random random = new Random();
		Vector2 Goal = new Vector2(getxGoal(), getyGoal());
		Vector2 position = new Vector2(x, y);
		Vector2 direction2 = new Vector2();
		
		int n = random.nextInt(100);
		
		if(n<60){
			Goal = new Vector2(getxGoal(),getyGoal());
			direction2.set(Goal).sub(position).nor();
			x += direction2.x * (random.nextInt(3));
			y += direction2.y * (random.nextInt(3));
			if (direction2.x>=0 && direction2.y >= 0)
				this.direction = UP_RIGHT;
			else if (direction2.x>=0 && direction2.y< 0)
			{
				this.direction = DOWN_RIGHT;
			}
			else if (direction2.x<0 && direction2.y >= 0)
			{
				this.direction = UP_LEFT;
			}
			else
				this.direction = DOWN_LEFT;
		}

		else if (n < 80){
			//float modif = random.nextFloat();
			Goal = new Vector2(getxGoal(),0);
			position = new Vector2(x, 0);
			direction2.set(Goal).sub(position).nor();
			//direction.set(direction.x, modif).nor();
			x += direction2.x * (random.nextInt(3));
			y += direction2.y * (random.nextInt(3));
			if (direction2.x>=0 && direction2.y >= 0)
				this.direction = UP_RIGHT;
			else if (direction2.x>=0 && direction2.y< 0)
			{
				this.direction = DOWN_RIGHT;
			}
			else if (direction2.x<0 && direction2.y >= 0)
			{
				this.direction = UP_LEFT;
			}
			else
				this.direction = DOWN_LEFT;
		}
		else
		{
			//float modif = random.nextFloat();
			Goal = new Vector2(0, getyGoal());
			position = new Vector2(0, y);
			direction2.set(Goal).sub(position).nor();
			//direction.set(modif, direction.y).nor();
			x += direction2.x * (random.nextInt(3));
			y += direction2.y * (random.nextInt(3));
			if (direction2.x>=0 && direction2.y >= 0)
				this.direction = UP_RIGHT;
			else if (direction2.x>=0 && direction2.y< 0)
			{
				this.direction = DOWN_RIGHT;
			}
			else if (direction2.x<0 && direction2.y >= 0)
			{
				this.direction = UP_LEFT;
			}
			else
				this.direction = DOWN_LEFT;
		}
		
				
			
		

	}

}
