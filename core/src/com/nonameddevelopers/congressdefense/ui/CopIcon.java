package com.nonameddevelopers.congressdefense.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.ObjectMap;
import com.nonameddevelopers.congressdefense.CongressDefense;
import com.nonameddevelopers.congressdefense.characters.cops.BazookaCop;
import com.nonameddevelopers.congressdefense.characters.cops.Bomb;
import com.nonameddevelopers.congressdefense.characters.cops.MeleeCop;
import com.nonameddevelopers.congressdefense.characters.cops.PoliceVan;
import com.nonameddevelopers.congressdefense.gameItems.CopSetter;

public class CopIcon {
	private static final Color TRANSPARENT = new Color(1,1,1,0.5f);

	public static final float RADIUS = 80;
	public static final short MELEE = 0;
	public static final short BAZOOKA = 1;
	public static final short SWAT = 2;
	public static final short BOMB = 3;
	
	private static int quadrantSelected;
	

	private CongressDefense game;
	
	public Circle circle;
	
	private static ObjectMap<String, Texture> textures;
	private static Texture textureSelected;
	private Texture textureNoSelected;
	private Sprite sprite;
	
	private int quadrant;
	private float centerX;
	private float centerY;
	private short type;
	private int cost;
	private float scale;
	private CopSetter parent;	
	
	static {
		textures = new ObjectMap<String,Texture>();
		textureSelected = new Texture(Gdx.files.internal("ui/iconselected.png"));
	}
	
	public CopIcon(CongressDefense game, CopSetter parent, int cost, float centerX, float centerY,String src, short type, int quadrant) {
		this.game = game;
		this.parent = parent;
		this.cost = cost;
		this.centerX = centerX;
		this.centerY = centerY;
		this.quadrant = quadrant;
		this.type = type;
		this.scale = 1f;
		circle = new Circle();
		if (!textures.containsKey(src)) {			
			textures.put(src, new Texture(Gdx.files.internal(src)));
		}
		textureNoSelected = textures.get(src);
		
		sprite = new Sprite(textureNoSelected);
		quadrantSelected = 0;
	}
	
	public void update(float delta, float currentRadius) {
		float auxX = centerX;
		float auxY = centerY;
		switch (quadrant) {
		case 1:
			auxX -= (currentRadius-RADIUS/2)*scale;
			break;
		case 2:
			auxY += (currentRadius-RADIUS/2)*scale;
			break;
		case 3:
			auxX += (currentRadius-RADIUS/2)*scale;
			break;
		case 4:
			auxY -= (currentRadius-RADIUS/2)*scale;
			break;
		}
		sprite.setSize(RADIUS*2*scale,RADIUS*2*scale);
		circle.set(auxX, auxY, RADIUS*scale);	
		sprite.setCenter(auxX, auxY);	
	}
	
	public void draw(SpriteBatch batch) {
		if (!canBuy())
			sprite.setColor(TRANSPARENT);
		else
			sprite.setColor(Color.WHITE);
		if (isSelected()) {
			sprite.setTexture(textureSelected);
		}
		else {
			sprite.setTexture(textureNoSelected);
		}
		sprite.draw(batch);	
	}
	
	
	public void click(float x, float y) {
		if (circle.contains(x,y)
			&& canBuy()) {
			if (!isSelected()) {
				game.playTouch();
				setSelected();				
			}
			else {
				switch (type) {
				case (CopIcon.BOMB):
					parent.getCopManager().addCop(new Bomb(game, centerX, centerY));
					break;
				case (CopIcon.SWAT):
					parent.getCopManager().addCop(new PoliceVan(game, 190, 1500, centerX, centerY));
					break;
				case (CopIcon.BAZOOKA):
					parent.getCopManager().addCop(new BazookaCop(game,centerX,centerY));
					break;
				case (CopIcon.MELEE):
				default:
					parent.getCopManager().addCop(new MeleeCop(game,centerX,centerY));
				}
				parent.release();
				game.playCoin();
				game.money -= getCost();				
			}
		}
	}
	
	
	public void setSelected() {
		quadrantSelected = quadrant;
	}
	
	public boolean isSelected() {
		return quadrantSelected == quadrant;
	}
	
	public boolean canBuy() {
		return cost<=game.money;
	}
	
	public int getCost() {
		return cost;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scaleXY) {
		this.scale = scaleXY;
	}
	
	public short getType() {
		return type;
	}
}
