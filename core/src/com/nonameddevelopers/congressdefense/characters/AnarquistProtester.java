package com.nonameddevelopers.congressdefense.characters;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.EntityManager;

public class AnarquistProtester extends Protester {

	public AnarquistProtester(CongressDefense game, float x, float y, float appearTime) {
		super(game, x, y, appearTime);
		ulAnimation = loadAnimation("sprites/anarkProt/up_left.png", 5, 5, 0.02f);
		dlAnimation = loadAnimation("sprites/anarkProt/down_left.png", 5, 5, 0.02f);	
		urAnimation = loadAnimation("sprites/anarkProt/up_right.png", 5, 5, 0.02f);	
		drAnimation = loadAnimation("sprites/anarkProt/down_right.png", 5, 5, 0.02f);
	}
	
	

	@Override
	public void update(float delta) {
		stateTime += delta;
		if (stateTime < appearTime)
			return;

		checkCollitions();
		approach();
		updateAnimation();

		boundingCircle.set(x + 16, y + 16, 20f);
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
	
	public void checkCollitions() {
			for (Cop cop : EntityManager.getInstance().getCopManager().getCops()) {
				if (Intersector.overlaps(getBoundingCircle(), cop.getBoundingCircle())) {
					//Animacion pegar();
					//sonidoPegar.play();
					//cop.dispose();
					//this.dispose();
				}
			}
		
	}
	
	

}
