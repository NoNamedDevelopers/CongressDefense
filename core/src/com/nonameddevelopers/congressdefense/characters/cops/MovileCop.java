package com.nonameddevelopers.congressdefense.characters.cops;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.gameItems.GameSound;
import com.nonameddevelopers.congressdefense.characters.Cop;
import com.nonameddevelopers.congressdefense.characters.Crowd;
import com.nonameddevelopers.congressdefense.characters.Protester;

public class MovileCop extends Cop {
	
	private Circle range;
	private boolean inRange = false;
	
	protected static GameSound punch;

	static {
		punch = new GameSound("sounds/punch.mp3", 330);
	}
	

	public MovileCop(CongressDefense game, float x, float y) {
		super(game, x, y, "cop", 4, 6, 0.018f);
		
		boundingCircle.set(x, y, 10f);		
		
		xInit = x; //redefinido en copdisp
		yInit = y;
		
		range = new Circle();
		range.set(xInit+16, yInit+16, 100f);
	}
	
	
	@Override
	public void checkCollision(Crowd crowd) {
		range.set(xInit+16, yInit+16, 100f);
		boundingCircle.set(x, y, 10f);		
		Protester prot = null;
		Vector2 pos = new Vector2(this.x, this.y);
		float distance = 0;
		for (Protester protester : crowd.getProtesters())
		{
			if (Intersector.overlaps(protester.getBoundingCircle(), boundingCircle))
			{
				inRange = true;
				isAttacking = true;
				if (x-protester.getX() >0 && y-protester.getY()>0)
					direction = DOWN_RIGHT;
				else if (x-protester.getX() > 0 && y-protester.getY() < 0)
					direction = UP_RIGHT;
				else if (x-protester.getX() < 0 && y-protester.getY() < 0)
					direction = UP_LEFT;
				else
					direction = DOWN_LEFT;
	
				if (stateTime == 0f) {
					punch.play();
					protester.hurt(40);
					stateTime += 0.001f;
				}
			}	
			break;
		}
		if (!inRange)
		{
			for (Protester protester : crowd.getProtesters())
			{
				if (Intersector.overlaps(protester.getBoundingCircle(), range)) {
					Vector2 posProt = new Vector2(protester.getX(), protester.getY());
					System.out.println("hay intersección");
					if (prot == null)
					{
						prot = protester;
						distance = pos.sub(posProt).len();
						System.out.println("cambiado el protester " + protester);
					}
					else{
						if (pos.sub(posProt).len() < distance)
						{
							prot = protester;
							distance = pos.sub(posProt).len();
						}
					}
				}
			}	
			if (prot != null){
				aproachProtester(prot);
				//incluir animacion de caminar
			}
			else
			{
				//backCenter();
			}
		}
		//else{
		//	backCenter();
			//incluir animacion de caminar
		//}
	}
	
	private void aproachProtester(Protester protester)
	{
		Vector2 position = new Vector2(x, y);
		Vector2 direction2 = new Vector2();
		Vector2 Goal = new Vector2(protester.getX(),protester.getY());
		direction2.set(Goal).sub(position).nor();
		x += direction2.x * 1.5f;
		y += direction2.y * 1.5f;
	}
	
	private void backCenter()
	{
		Vector2 position = new Vector2(x, y);
		Vector2 direction2 = new Vector2();
		Vector2 Goal = new Vector2(xInit,yInit);
		direction2.set(Goal).sub(position).nor();
		x += direction2.x * 1.5f;
		y += direction2.y * 1.5f;
	}


	public float getxInit() {
		return xInit;
	}


	public void setxInit(float xInit) {
		this.xInit = xInit;
	}


	public float getyInit() {
		return yInit;
	}


	public void setyInit(float yInit) {
		this.yInit = yInit;
	}

	
	

}
